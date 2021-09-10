package com.creseliana.repository;

import com.creseliana.model.Advertisement;
import com.creseliana.model.Category;
import com.creseliana.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
@AutoConfigureTestEntityManager
public class BaseAdvertisementRepositoryTest {
    private static final String TEST = "test";

    @Autowired
    private BaseAdvertisementRepository adRepository;
    @Autowired
    private BaseUserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void crudMethodsExecution() {
        Iterable<Advertisement> ads;

        Advertisement ad = new Advertisement();
        ad.setCategory(new Category(TEST));
        ad.setTitle(TEST);
        ad.setDescription(TEST);
        ad.setPrice(BigDecimal.valueOf(5.55));
        ad.setAuthor(new User());
        ad.setCreationDate(LocalDateTime.now());
        ad.setClosed(false);
        ad.setDeleted(false);

        Advertisement ad1 = new Advertisement();
        ad1.setCategory(new Category(TEST));
        ad1.setTitle(TEST);
        ad1.setDescription(TEST);
        ad1.setPrice(BigDecimal.valueOf(0));
        ad1.setAuthor(new User());
        ad1.setCreationDate(LocalDateTime.now());
        ad1.setClosed(false);
        ad1.setDeleted(false);

        Advertisement savedAd = adRepository.save(ad);
        assertEquals(adRepository.count(), 1);
        assertNotNull(savedAd);
        assertEquals(ad, savedAd);

        ad.setClosed(true);
        Advertisement updatedAd = adRepository.update(ad);
        assertEquals(ad, updatedAd);

        Advertisement findByIdAd = adRepository.findById(updatedAd.getId()).get();
        assertNotNull(findByIdAd);
        assertEquals(findByIdAd.getId(), updatedAd.getId());

        assertFalse(adRepository.findById(0L).isPresent());

        assertEquals(adRepository.count(), 1);

        savedAd = entityManager.persistAndFlush(ad1);
        assertEquals(adRepository.count(), 2);

        ads = adRepository.findAll();
        assertThat(ads).hasSize(2);

        adRepository.deleteById(findByIdAd.getId());
        assertEquals(adRepository.count(), 1);

        adRepository.delete(savedAd);
        assertEquals(adRepository.count(), 0);

        ads = adRepository.findAll();
        assertThat(ads).isEmpty();
    }

    @Test
    public void getAdsByParams() {
        User user = new User();
        user.setUsername(TEST);
        user.setPassword(TEST);
        user = entityManager.persistAndFlush(user);

        Category category = new Category(TEST);
        category = entityManager.persistAndFlush(category);

        Advertisement ad = new Advertisement();
        ad.setCategory(category);
        ad.setTitle(TEST);
        ad.setDescription(TEST);
        ad.setPrice(BigDecimal.valueOf(5.55));
        ad.setAuthor(user);
        ad.setCreationDate(LocalDateTime.now());
        ad.setClosed(true);
        ad.setDeleted(false);

        Advertisement ad1 = new Advertisement();
        ad1.setCategory(category);
        ad1.setTitle(TEST);
        ad1.setDescription(TEST);
        ad1.setPrice(BigDecimal.valueOf(0));
        ad1.setAuthor(user);
        ad1.setCreationDate(LocalDateTime.now());
        ad1.setClosed(false);
        ad1.setDeleted(false);

        entityManager.persist(ad);
        entityManager.persistAndFlush(ad1);

        List<Advertisement> ads;

        ads = adRepository.getAdsByClosedAndAuthorId(true, user.getId(), 0, 10);
        assertEquals(1, ads.size());

        ads = adRepository.getAdsByClosedAndAuthorId(false, user.getId(), 0, 10);
        assertEquals(1, ads.size());

        ads = adRepository.getAdsByClosedAndAuthorId(true, user.getId(), 10, 10);
        assertEquals(0, ads.size());
    }
}
