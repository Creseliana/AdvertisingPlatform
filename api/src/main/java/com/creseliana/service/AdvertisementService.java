package com.creseliana.service;

import com.creseliana.dto.AdvertisementCreateRequest;
import com.creseliana.dto.AdvertisementDetailedResponse;
import com.creseliana.dto.AdvertisementEditRequest;
import com.creseliana.dto.AdvertisementPreviewResponse;
import com.creseliana.dto.AdvertisementShortResponse;

import java.util.List;

/**
 * Service for {@link com.creseliana.model.Advertisement}
 */
public interface AdvertisementService {
    /**
     * Creates new advertisement
     *
     * @param username the name of the ad author
     * @param newAd    the DTO of the {@link com.creseliana.model.Advertisement}
     *                 that contains new ad details
     */
    void create(String username, AdvertisementCreateRequest newAd);

    /**
     * Edits existing advertisement if this user is its author
     *
     * @param username  the name of the user that is trying to edit ad
     * @param id        the id of the ad for editing
     * @param adChanges the DTO of the {@link com.creseliana.model.Advertisement}
     *                  that contains changed ad details
     */
    void edit(String username, Long id, AdvertisementEditRequest adChanges);

    /**
     * Deletes existing advertisement if this user is its author.
     * It supposes not to delete completely, but mark as deleted
     *
     * @param username the name of the user that is trying to delete ad
     * @param id       the id of the ad for deleting
     */
    void delete(String username, Long id);

    /**
     * Closes existing advertisement if this user is its author
     *
     * @param username the name of the user that is trying to close ad
     * @param id       the id of the ad for closing
     */
    void close(String username, Long id);

    /**
     * Provides payment for advertisement if this user is its author
     *
     * @param username the name of the user that is trying to pay for ad
     * @param id       the id of the ad for paying
     */
    void pay(String username, Long id);

    /**
     * Gets {@link com.creseliana.model.Advertisement} by its id
     *
     * @param id the id of the ad
     * @return DTO of the {@link com.creseliana.model.Advertisement}
     */
    AdvertisementDetailedResponse getById(Long id);

    /**
     * Gets limited amount of {@link com.creseliana.model.Advertisement} entities where author is this user
     * and that are set closed <code>true</code>
     *
     * @param username the name of the user whose ads needed
     * @param page the number of the page that contains certain amount of ads
     * @param amount the number of the ads on page
     * @return List of the DTOs of the {@link com.creseliana.model.Advertisement}
     */
    List<AdvertisementShortResponse> getCompletedByUsername(String username, int page, int amount);

    /**
     * Gets limited amount of {@link com.creseliana.model.Advertisement} entities where author is this user
     * and that are set closed <code>false</code>
     *
     *
     * @param username the name of the user whose ads are needed
     * @param page the number of the page that contains certain amount of ads
     * @param amount the number of ads on page
     * @return List of DTOs of the {@link com.creseliana.model.Advertisement}
     */
    List<AdvertisementShortResponse> getIncompleteByUsername(String username, int page, int amount);

    List<AdvertisementPreviewResponse> getAll(String category, int page, int amount); //todo add doc
}
