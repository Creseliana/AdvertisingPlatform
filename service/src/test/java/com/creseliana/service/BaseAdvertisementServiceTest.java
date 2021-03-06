package com.creseliana.service;

import com.creseliana.dto.AdvertisementCreateRequest;
import com.creseliana.dto.AdvertisementEditRequest;
import com.creseliana.model.Advertisement;
import com.creseliana.model.Category;
import com.creseliana.model.Payment;
import com.creseliana.model.User;
import com.creseliana.repository.AdvertisementRepository;
import com.creseliana.repository.CategoryRepository;
import com.creseliana.repository.PaymentRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.AccessException;
import com.creseliana.service.exception.ad.AdvertisementNotFoundException;
import com.creseliana.service.exception.user.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BaseAdvertisementServiceTest {
    private final ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);

    @InjectMocks
    private BaseAdvertisementService adService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private AdvertisementRepository adRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {
        AdvertisementCreateRequest newAd = new AdvertisementCreateRequest();
        Advertisement ad = new Advertisement();
        Category category = new Category();
        category.setId(1L);
        newAd.setCategory(category);

        when(mapper.map(newAd, Advertisement.class)).thenReturn(ad);
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));
        when(categoryRepository.existsById(anyLong())).thenReturn(true);

        adService.create("test", newAd);

        assertNotNull(ad.getAuthor());
        assertNotNull(ad.getCreationDate());
        assertFalse(ad.isClosed());
        assertFalse(ad.isDeleted());

        verify(adRepository, times(1)).save(ad);
        verify(categoryRepository, times(1)).existsById(anyLong());
    }

    @Test
    void createThrowsExceptionOnUser() {
        AdvertisementCreateRequest newAd = new AdvertisementCreateRequest();
        Category category = new Category();
        category.setId(1L);
        newAd.setCategory(category);

        when(categoryRepository.existsById(anyLong())).thenReturn(true);
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> adService.create("test", newAd));

        verify(adRepository, times(0)).save(any(Advertisement.class));
    }

    @Test
    void edit() {
        String username = "test";
        Advertisement ad = new Advertisement();
        User user = new User();
        user.setUsername(username);
        ad.setAuthor(user);

        when(adRepository.findById(any())).thenReturn(Optional.of(ad));

        adService.edit(username, 1L, new AdvertisementEditRequest());

        assertEquals(username, ad.getAuthor().getUsername());

        verify(adRepository, times(1)).update(ad);
    }

    @Test
    void editThrowsExceptionOnUsername() {
        String username = "test";
        Advertisement ad = new Advertisement();
        User user = new User();
        user.setUsername(username);
        ad.setAuthor(user);

        when(adRepository.findById(any())).thenReturn(Optional.of(ad));

        assertThrows(AccessException.class, () -> adService.edit("test1", any(), new AdvertisementEditRequest()));
    }

    @Test
    void editThrowsExceptionOnAd() {
        when(adRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(AdvertisementNotFoundException.class, () -> adService.edit("test", anyLong(), new AdvertisementEditRequest()));
    }

    @Test
    void delete() {
        String username = "test";
        Advertisement ad = new Advertisement();
        User user = new User();
        user.setUsername(username);
        ad.setAuthor(user);

        when(adRepository.findById(any())).thenReturn(Optional.of(ad));

        adService.delete(username, anyLong());

        assertTrue(ad.isDeleted());

        verify(adRepository, times(1)).update(ad);
    }

    @Test
    void deleteThrowsExceptionOnAd() {
        when(adRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(AdvertisementNotFoundException.class, () -> adService.delete("test", anyLong()));
    }

    @Test
    void deleteThrowsExceptionOnUsername() {
        String username = "test";
        Advertisement ad = new Advertisement();
        User user = new User();
        user.setUsername(username);
        ad.setAuthor(user);

        when(adRepository.findById(any())).thenReturn(Optional.of(ad));

        assertThrows(AccessException.class, () -> adService.delete("test1", anyLong()));
    }

    @Test
    void close() {
        String username = "test";
        Advertisement ad = new Advertisement();
        User user = new User();
        user.setUsername(username);
        ad.setAuthor(user);

        when(adRepository.findById(any())).thenReturn(Optional.of(ad));

        adService.close(username, anyLong());

        assertTrue(ad.isClosed());

        verify(adRepository, times(1)).update(ad);
    }

    @Test
    void closeThrowsExceptionOnAd() {
        when(adRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(AdvertisementNotFoundException.class, () -> adService.close("test", anyLong()));
    }

    @Test
    void closeThrowsExceptionOnUsername() {
        String username = "test";
        Advertisement ad = new Advertisement();
        User user = new User();
        user.setUsername(username);
        ad.setAuthor(user);

        when(adRepository.findById(any())).thenReturn(Optional.of(ad));

        assertThrows(AccessException.class, () -> adService.close("test1", anyLong()));
    }

    @Test
    void pay() {
        String username = "test";
        Payment payment = new Payment();
        Advertisement ad = new Advertisement();
        User author = new User();
        author.setUsername(username);
        ad.setAuthor(author);
        payment.setAd(ad);
        payment.setEndDate(LocalDateTime.now());

        when(adRepository.findById(anyLong())).thenReturn(Optional.of(ad));
        when(paymentRepository.getCurrentPaymentByAdId(anyLong())).thenReturn(Optional.of(payment));

        assertDoesNotThrow(() -> adService.pay(username, anyLong()));

        verify(paymentRepository, times(1)).update(any(Payment.class));
        verify(paymentRepository, times(0)).save(any(Payment.class));
        verify(paymentRepository).update(paymentCaptor.capture());

        Payment capturedPayment = paymentCaptor.getValue();
        assertNotNull(capturedPayment.getEndDate());
    }

    @Test
    void payNoCurrentPayment() {
        String username = "test";
        Advertisement ad = new Advertisement();
        User author = new User();
        author.setUsername(username);
        ad.setAuthor(author);

        when(adRepository.findById(anyLong())).thenReturn(Optional.of(ad));
        when(paymentRepository.getCurrentPaymentByAdId(anyLong())).thenReturn(Optional.empty());

        adService.pay(username, anyLong());

        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(paymentRepository, times(0)).update(any(Payment.class));
    }

    @Test
    void payThrowsExceptionOnAd() {
        when(adRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(AdvertisementNotFoundException.class, () -> adService.pay("test", anyLong()));

        verify(paymentRepository, times(0)).getCurrentPaymentByAdId(anyLong());
        verify(paymentRepository, times(0)).update(any(Payment.class));
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void payThrowsExceptionOnUserAccess() {
        Advertisement ad = new Advertisement();
        User author = new User();
        author.setUsername("test");
        ad.setAuthor(author);
        when(adRepository.findById(anyLong())).thenReturn(Optional.of(ad));

        assertThrows(AccessException.class, () -> adService.pay("test1", anyLong()));

        verify(paymentRepository, times(0)).getCurrentPaymentByAdId(anyLong());
        verify(paymentRepository, times(0)).update(any(Payment.class));
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void getById() {
        Advertisement ad = new Advertisement();
        ad.setClosed(false);
        ad.setDeleted(false);

        when(adRepository.findById(any())).thenReturn(Optional.of(ad));

        assertDoesNotThrow(() -> adService.getById(anyLong()));
    }

    @Test
    void getByIdThrowsWhenClosed() {
        Advertisement ad = new Advertisement();
        ad.setClosed(true);
        ad.setDeleted(false);

        when(adRepository.findById(any())).thenReturn(Optional.of(ad));

        assertThrows(AccessException.class, () -> adService.getById(anyLong()));
    }

    @Test
    void getByIdThrowsWhenDeleted() {
        Advertisement ad = new Advertisement();
        ad.setClosed(false);
        ad.setDeleted(true);

        when(adRepository.findById(any())).thenReturn(Optional.of(ad));

        assertThrows(AccessException.class, () -> adService.getById(anyLong()));
    }

    @Test
    void getByIdThrowsWhenClosedDeleted() {
        Advertisement ad = new Advertisement();
        ad.setClosed(true);
        ad.setDeleted(true);

        when(adRepository.findById(any())).thenReturn(Optional.of(ad));

        assertThrows(AccessException.class, () -> adService.getById(anyLong()));
    }

    @Test
    void getByIdThrowsExceptionOnAd() {
        when(adRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(AdvertisementNotFoundException.class, () -> adService.getById(anyLong()));
    }
}