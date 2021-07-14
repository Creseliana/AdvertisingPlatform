package com.creseliana.service;

import com.creseliana.dto.AdvertisementCreateRequest;
import com.creseliana.dto.AdvertisementEditRequest;
import com.creseliana.dto.AdvertisementShowResponse;
import com.creseliana.model.Advertisement;
import com.creseliana.model.User;
import com.creseliana.repository.AdvertisementRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.ad.AccessException;
import com.creseliana.service.exception.ad.AdvertisementNotFoundException;
import com.creseliana.service.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class BaseAdvertisementService implements AdvertisementService {
    private static final String MSG_ACCESS_DENIED_USER_MISMATCH = "Ad cannot be changed by other user";
    private static final String MSG_ACCESS_DENIED_AD_UNAVAILABLE = "Ad is no longer available";
    private static final String MSG_AD_DETAILS = "Ad ID: '%s', author username: '%s', auth username: '%s'";
    private static final String MSG_USER_NOT_FOUND_BY_USERNAME = "There is no user with username '%s'";
    private static final String MSG_AD_NOT_FOUND_BY_ID = "There is no ad with id '%s'";

    private final UserRepository userRepository;
    private final AdvertisementRepository adRepository;
    private final ModelMapper mapper;

    @Override
    public void create(String username, AdvertisementCreateRequest newAd) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            String msg = String.format(MSG_USER_NOT_FOUND_BY_USERNAME, username);
            log.info(msg);
            return new UserNotFoundException(msg);
        });
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
        checkUser(username, ad);
        mapper.map(adChanges, ad);
        adRepository.update(ad);
    }

    @Override
    public void delete(String username, Long id) {
        Advertisement ad = getAdById(id);
        checkUser(username, ad);
        ad.setDeleted(true);
        adRepository.update(ad);
    }

    @Override
    public void close(String username, Long id) {
        Advertisement ad = getAdById(id);
        checkUser(username, ad);
        ad.setClosed(true);
        adRepository.update(ad);
    }

    @Override
    public AdvertisementShowResponse show(Long id) {
        Advertisement ad = getAdById(id);
        if (ad.isClosed() || ad.isDeleted()) {
            log.info(MSG_ACCESS_DENIED_AD_UNAVAILABLE);
            throw new AccessException(MSG_ACCESS_DENIED_AD_UNAVAILABLE);
        }
        return mapper.map(ad, AdvertisementShowResponse.class);
    }

    private void checkUser(String username, Advertisement ad) {
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
