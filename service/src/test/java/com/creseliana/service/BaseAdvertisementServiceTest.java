package com.creseliana.service;

import com.creseliana.dto.AdvertisementCreateRequest;
import com.creseliana.dto.AdvertisementEditRequest;
import com.creseliana.model.Advertisement;
import com.creseliana.model.User;
import com.creseliana.repository.AdvertisementRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.ad.AccessException;
import com.creseliana.service.exception.ad.AdvertisementNotFoundException;
import com.creseliana.service.exception.user.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BaseAdvertisementServiceTest {

    @InjectMocks
    private BaseAdvertisementService adService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private AdvertisementRepository adRepository;
    @Mock
    private ModelMapper mapper;

//    private ModelMapper testMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
//        testMapper = new ModelMapper();
    }

    @Test
    void create() {
        AdvertisementCreateRequest newAd = new AdvertisementCreateRequest();
        Advertisement ad = new Advertisement();
        when(mapper.map(newAd, Advertisement.class)).thenReturn(ad);
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));
        adService.create("test", newAd);

        assertNotNull(ad.getAuthor());
        assertNotNull(ad.getCreationDate());
        assertFalse(ad.isClosed());
        assertFalse(ad.isDeleted());

        verify(adRepository, times(1)).save(ad);
    }

    @Test
    void createThrowsExceptionOnUser() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> adService.create("test", new AdvertisementCreateRequest()));
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
    void show() {
        Advertisement ad = new Advertisement();
        ad.setClosed(false);
        ad.setDeleted(false);
        when(adRepository.findById(any())).thenReturn(Optional.of(ad));
        assertDoesNotThrow(() -> adService.show(anyLong()));
    }

    @Test
    void showThrowsWhenClosed() {
        Advertisement ad = new Advertisement();
        ad.setClosed(true);
        ad.setDeleted(false);
        when(adRepository.findById(any())).thenReturn(Optional.of(ad));
        assertThrows(AccessException.class, () -> adService.show(anyLong()));
    }

    @Test
    void showThrowsWhenDeleted() {
        Advertisement ad = new Advertisement();
        ad.setClosed(false);
        ad.setDeleted(true);
        when(adRepository.findById(any())).thenReturn(Optional.of(ad));
        assertThrows(AccessException.class, () -> adService.show(anyLong()));
    }

    @Test
    void showThrowsWhenClosedDeleted() {
        Advertisement ad = new Advertisement();
        ad.setClosed(true);
        ad.setDeleted(true);
        when(adRepository.findById(any())).thenReturn(Optional.of(ad));
        assertThrows(AccessException.class, () -> adService.show(anyLong()));
    }

    @Test
    void showThrowsExceptionOnAd() {
        when(adRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(AdvertisementNotFoundException.class, () -> adService.show(anyLong()));
    }
}