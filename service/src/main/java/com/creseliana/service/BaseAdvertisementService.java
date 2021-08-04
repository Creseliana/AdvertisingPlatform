package com.creseliana.service;

import com.creseliana.dto.AdvertisementCreateRequest;
import com.creseliana.dto.AdvertisementDetailedResponse;
import com.creseliana.dto.AdvertisementEditRequest;
import com.creseliana.dto.AdvertisementPreviewResponse;
import com.creseliana.dto.AdvertisementShortResponse;
import com.creseliana.model.Advertisement;
import com.creseliana.model.Payment;
import com.creseliana.model.User;
import com.creseliana.repository.AdvertisementRepository;
import com.creseliana.repository.CategoryRepository;
import com.creseliana.repository.PaymentRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.AccessException;
import com.creseliana.service.exception.ad.AdvertisementNotFoundException;
import com.creseliana.service.exception.category.CategoryNotFoundException;
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

@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class BaseAdvertisementService extends BaseModelService implements AdvertisementService {
    private static final String MSG_ACCESS_DENIED_USER_MISMATCH = "Ad cannot be changed or payed by other user";
    private static final String MSG_ACCESS_DENIED_AD_UNAVAILABLE = "Ad is no longer available";
    private static final String MSG_AD_DETAILS = "Ad ID: '%s', author username: '%s', auth username: '%s'";
    private static final String MSG_CATEGORY_NOT_FOUND_BY_ID = "There is no category with id '%s'";
    private static final String MSG_CATEGORY_NOT_FOUND_BY_NAME = "There is no category with name '%s'";
    private static final String MSG_AD_NOT_FOUND_BY_ID = "There is no ad with id '%s'";

    private final UserRepository userRepository;
    private final AdvertisementRepository adRepository;
    private final PaymentRepository paymentRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Value("${day_amount}")
    private int dayAmount;

    @Override
    public void create(String username, AdvertisementCreateRequest newAd) {
        checkCategoryExistsById(newAd.getCategory().getId());
        User user = getUserByUsername(username, userRepository);
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
        checkAdAvailability(ad);
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
    public AdvertisementDetailedResponse getById(Long id) {
        Advertisement ad = getAdById(id);
        checkAdAvailability(ad);
        return mapper.map(ad, AdvertisementDetailedResponse.class);
    }

    @Override
    public List<AdvertisementShortResponse> getCompletedByUsername(String username, int page, int amount) {
        User user = getUserByUsername(username, userRepository);
        int start = StartCount.count(page, amount);
        List<Advertisement> completedAds = adRepository.getAdsByClosedAndAuthorId(true, user.getId(), start, amount);
        return mapList(mapper, completedAds, AdvertisementShortResponse.class);
    }

    @Override
    public List<AdvertisementShortResponse> getIncompleteByUsername(String username, int page, int amount) {
        User user = getUserByUsername(username, userRepository);
        int start = StartCount.count(page, amount);
        List<Advertisement> incompleteAds = adRepository.getAdsByClosedAndAuthorId(false, user.getId(), start, amount);
        return mapList(mapper, incompleteAds, AdvertisementShortResponse.class);
    }

    @Override
    public List<AdvertisementPreviewResponse> getAds(String categoryName, int page, int amount) {
        int start = StartCount.count(page, amount);
        if (categoryName != null) {
            checkCategoryExistsByName(categoryName);
        }
        List<Advertisement> ads = adRepository.getAllAdsOrdered(categoryName, start, amount);
        return mapList(mapper, ads, AdvertisementPreviewResponse.class);
    }

    private Advertisement getAdById(Long id) {
        return adRepository.findById(id).orElseThrow(() -> {
            String msg = String.format(MSG_AD_NOT_FOUND_BY_ID, id);
            log.info(msg);
            return new AdvertisementNotFoundException(msg);
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

    private void checkCategoryExistsById(Long id) {
        if (!categoryRepository.existsById(id)) {
            String msg = String.format(MSG_CATEGORY_NOT_FOUND_BY_ID, id);
            log.info(msg);
            throw new CategoryNotFoundException(msg);
        }
    }

    private void checkCategoryExistsByName(String name) {
        if (!categoryRepository.existsByName(name)) {
            String msg = String.format(MSG_CATEGORY_NOT_FOUND_BY_NAME, name);
            log.info(msg);
            throw new CategoryNotFoundException(msg);
        }
    }

    private void checkAdAvailability(Advertisement ad) {
        if (ad.isClosed() || ad.isDeleted()) {
            log.info(MSG_ACCESS_DENIED_AD_UNAVAILABLE);
            throw new AccessException(MSG_ACCESS_DENIED_AD_UNAVAILABLE);
        }
    }
}
