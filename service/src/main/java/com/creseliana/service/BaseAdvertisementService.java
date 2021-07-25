package com.creseliana.service;

import com.creseliana.dto.AdvertisementCreateRequest;
import com.creseliana.dto.AdvertisementEditRequest;
import com.creseliana.dto.AdvertisementResponse;
import com.creseliana.dto.AdvertisementShowResponse;
import com.creseliana.dto.AdvertisementShowShortResponse;
import com.creseliana.model.Advertisement;
import com.creseliana.model.Payment;
import com.creseliana.model.User;
import com.creseliana.repository.AdvertisementRepository;
import com.creseliana.repository.PaymentRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.ad.AccessException;
import com.creseliana.service.exception.ad.AdvertisementNotFoundException;
import com.creseliana.service.exception.user.UserNotFoundException;
import com.creseliana.service.util.StartCount;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class BaseAdvertisementService implements AdvertisementService {
    private static final String MSG_ACCESS_DENIED_USER_MISMATCH = "Ad cannot be changed/payed by other user";
    private static final String MSG_ACCESS_DENIED_AD_UNAVAILABLE = "Ad is no longer available";
    private static final String MSG_AD_DETAILS = "Ad ID: '%s', author username: '%s', auth username: '%s'";
    private static final String MSG_USER_NOT_FOUND_BY_USERNAME = "There is no user with username '%s'";
    private static final String MSG_AD_NOT_FOUND_BY_ID = "There is no ad with id '%s'";

    private final UserRepository userRepository;
    private final AdvertisementRepository adRepository;
    private final PaymentRepository paymentRepository;
    private final ModelMapper mapper;

    @Value("${day_amount}")
    int dayAmount;

    @Override
    public void create(String username, AdvertisementCreateRequest newAd) {
        User user = getUserByUsername(username);
        Advertisement ad = mapper.map(newAd, Advertisement.class);
        ad.setAuthor(user);
        ad.setCreationDate(LocalDateTime.now());
        ad.setClosed(false);
        ad.setDeleted(false);
        adRepository.save(ad);
    }

    @Override
    public void edit(String username, Long id, AdvertisementEditRequest adChanges) {
        Advertisement ad = getAdById(id);
        checkUserAdAccess(username, ad);
        mapper.map(adChanges, ad);
        adRepository.update(ad);
    }

    @Override
    public void delete(String username, Long id) {
        Advertisement ad = getAdById(id);
        checkUserAdAccess(username, ad);
        ad.setDeleted(true);
        adRepository.update(ad);
    }

    @Override
    public void close(String username, Long id) {
        Advertisement ad = getAdById(id);
        checkUserAdAccess(username, ad);
        ad.setClosed(true);
        adRepository.update(ad);
    }

    @Override
    public void pay(String username, Long id) {
        Payment payment;
        Advertisement ad = getAdById(id);
        checkUserAdAccess(username, ad);
        Optional<Payment> paymentOptional = paymentRepository.getCurrentPaymentByAdId(id);

        if (paymentOptional.isEmpty()) {
            payment = new Payment(ad, LocalDateTime.now(), LocalDateTime.now().plusDays(dayAmount));
            paymentRepository.save(payment);
        } else {
            payment = paymentOptional.get();
            payment.setEndDate(payment.getEndDate().plusDays(dayAmount));
            paymentRepository.update(payment);
        }
    }

    @Override
    public AdvertisementShowResponse getById(Long id) {
        Advertisement ad = getAdById(id);
        if (ad.isClosed() || ad.isDeleted()) {
            log.info(MSG_ACCESS_DENIED_AD_UNAVAILABLE);
            throw new AccessException(MSG_ACCESS_DENIED_AD_UNAVAILABLE);
        }
        return mapper.map(ad, AdvertisementShowResponse.class);
    }

    @Override
    public List<AdvertisementResponse> getCompletedByUsername(String username, int page, int amount) { //todo check count
        User user = getUserByUsername(username);
        int start = StartCount.count(page, amount);
        List<Advertisement> completedAds = adRepository.getAdsByClosedAndAuthorId(true, user.getId(), start, amount);
        return completedAds.stream()
                .map(ad -> mapper.map(ad, AdvertisementResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AdvertisementResponse> getIncompleteByUsername(String username, int page, int amount) {
        User user = getUserByUsername(username);
        int start = StartCount.count(page, amount);
        List<Advertisement> completedAds = adRepository.getAdsByClosedAndAuthorId(false, user.getId(), start, amount);
        return completedAds.stream()
                .map(ad -> mapper.map(ad, AdvertisementResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public AdvertisementShowShortResponse getAll(String category, int page, int amount) {
        int start = StartCount.count(page, amount);
        //todo check category
        //1 - 24 - (0, 10)(10, 10)(20, 4)
        //         (0, 10)(10, 10)(20, 10)
        //2 - 26 - (0, 6)  (6, 10) (16, 10)
        //         (20, 10)(30, 10)(40, 10)
        //3 - 15 - (0, 10) (10, 5)
        //         (50, 10)(60, 10)

        //todo count amount and compare

        //if(count < amount + start) { //24 < 10+20
        //int newAmount = amount + start - count = 10+20-24 = 6
        //int newStart = newAmount / amount
        //get(newStart, newAmount)
        //
        //
        //


        //if(newAmount < amount) {
        //int newStep = 0
        //get(newStep, newAmount)
        //} else {
        //newStep =
        //
        //
        //


        //todo call required method


        return null;
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            String msg = String.format(MSG_USER_NOT_FOUND_BY_USERNAME, username);
            log.info(msg);
            return new UserNotFoundException(msg);
        });
    }

    private void checkUserAdAccess(String username, Advertisement ad) {
        String authorUsername = ad.getAuthor().getUsername();
        if (!username.equals(authorUsername)) {
            log.info(MSG_ACCESS_DENIED_USER_MISMATCH);
            log.debug(String.format(MSG_AD_DETAILS, ad.getId(), authorUsername, username));
            throw new AccessException(MSG_ACCESS_DENIED_USER_MISMATCH);
        }
    }

    private Advertisement getAdById(Long id) {
        return adRepository.findById(id).orElseThrow(() -> {
            String msg = String.format(MSG_AD_NOT_FOUND_BY_ID, id);
            log.info(msg);
            return new AdvertisementNotFoundException(msg);
        });
    }
}
