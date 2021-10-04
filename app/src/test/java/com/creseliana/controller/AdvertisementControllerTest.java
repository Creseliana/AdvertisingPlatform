package com.creseliana.controller;

import com.creseliana.dto.AdvertisementCreateRequest;
import com.creseliana.dto.AdvertisementDetailedResponse;
import com.creseliana.dto.AdvertisementEditRequest;
import com.creseliana.dto.AdvertisementPreviewResponse;
import com.creseliana.service.AdvertisementService;
import com.creseliana.service.CommentService;
import com.creseliana.service.exception.AccessException;
import com.creseliana.service.exception.ad.AdvertisementNotFoundException;
import com.creseliana.service.exception.category.CategoryNotFoundException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AdvertisementControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private AdvertisementService adService;
    @MockBean
    private CommentService commentService;

    @Test
    @WithMockUser(username = "user", password = "user", authorities = {"USER"})
    public void create() throws Exception {
        mockMvc.perform(post("/ads")
                        .content(TestUtils.jsonBody("__files/ad-create-req.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(adService).create(anyString(), any(AdvertisementCreateRequest.class));
        verifyNoMoreInteractions(adService);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = {"USER"})
    public void createParamValidationFailedStatus409() throws Exception {
        mockMvc.perform(post("/ads")
                        .content(TestUtils.jsonBody("__files/ad-create-req-bad.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict());

        verifyNoInteractions(adService);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = {"ADMIN"})
    public void createRoleAdminStatus403() throws Exception {
        mockMvc.perform(post("/ads")
                        .content(TestUtils.jsonBody("__files/ad-create-req.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());

        verifyNoInteractions(adService);
    }

    @Test
    public void createUnauthorizedStatus401() throws Exception {
        mockMvc.perform(post("/ads")
                        .content(TestUtils.jsonBody("__files/ad-create-req.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").doesNotExist());
        ;

        verifyNoInteractions(adService);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = {"USER"})
    public void createThrowsExceptionOnCategoryExistenceStatus404() throws Exception {
        doThrow(CategoryNotFoundException.class).when(adService).create(anyString(), any(AdvertisementCreateRequest.class));

        mockMvc.perform(post("/ads")
                        .content(TestUtils.jsonBody("__files/ad-create-req.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(adService).create(anyString(), any(AdvertisementCreateRequest.class));
        verifyNoMoreInteractions(adService);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = {"USER"})
    public void getAll() throws Exception {
        AdvertisementPreviewResponse ad = new AdvertisementPreviewResponse(
                1L, "test", BigDecimal.valueOf(3), "test",
                "test", BigDecimal.valueOf(3), LocalDateTime.now());

        List<AdvertisementPreviewResponse> list = Collections.singletonList(ad);

        when(adService.getAds(eq(null), eq(1), eq(10))).thenReturn(list);

        mockMvc.perform(get("/ads?page=1&amount=10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(content().json(TestUtils.jsonBody("__files/ad-preview-res-arr.json")));

        verify(adService).getAds(eq(null), eq(1), eq(10));
        verifyNoMoreInteractions(adService);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = {"USER"})
    public void getAllEmptyList() throws Exception {
        when(adService.getAds(eq(null), eq(1), eq(10))).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/ads?page=1&amount=10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(adService).getAds(eq(null), eq(1), eq(10));
        verifyNoMoreInteractions(adService);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = {"USER"})
    public void getAllParamValidationFailedStatus400() throws Exception {
        mockMvc.perform(get("/ads?page=-1&amount=-1"))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verifyNoInteractions(adService);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = {"ADMIN"})
    public void getAllRoleAdminStatus403() throws Exception {
        mockMvc.perform(get("/ads?page=1&amount=10"))
                .andDo(print())
                .andExpect(status().isForbidden());

        verifyNoInteractions(adService);
    }

    @Test
    public void getAllUnauthorizedStatus401() throws Exception {
        mockMvc.perform(get("/ads?page=1&amount=10"))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(adService);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = {"USER"})
    public void getAllThrowsExceptionOnCategoryExistenceStatus404() throws Exception {
        when(adService.getAds(eq("test"), eq(1), eq(10))).thenThrow(CategoryNotFoundException.class);

        mockMvc.perform(get("/ads?category=test&page=1&amount=10"))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(adService).getAds(anyString(), anyInt(), anyInt());
        verifyNoMoreInteractions(adService);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = {"USER"})
    public void show() throws Exception {
        String test = "test";
        AdvertisementDetailedResponse ad = new AdvertisementDetailedResponse(test, "375290000000", BigDecimal.valueOf(4.3),
                test, test, test, BigDecimal.valueOf(4.3), LocalDateTime.now(), Collections.emptyList());

        when(adService.getById(anyLong())).thenReturn(ad);

        mockMvc.perform(get("/ads/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.jsonBody("__files/ad-detailed-res.json")));

        verify(adService).getById(anyLong());
        verifyNoMoreInteractions(adService);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = {"ADMIN"})
    public void showRoleAdminStatus403() throws Exception {
        mockMvc.perform(get("/ads/{id}", 1))
                .andDo(print())
                .andExpect(status().isForbidden());

        verifyNoInteractions(adService);
    }

    @Test
    public void showUnauthorizedStatus401() throws Exception {
        mockMvc.perform(get("/ads/{id}", 1))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(adService);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = {"USER"})
    public void showAdNotFoundThrowsExceptionStatus404() throws Exception {
        when(adService.getById(anyLong())).thenThrow(AdvertisementNotFoundException.class);

        mockMvc.perform(get("/ads/{id}", 1))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(adService).getById(anyLong());
        verifyNoMoreInteractions(adService);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = {"USER"})
    public void showAdNotAvailableThrowsExceptionStatus403() throws Exception {
        when(adService.getById(anyLong())).thenThrow(AccessException.class);

        mockMvc.perform(get("/ads/{id}", 1))
                .andDo(print())
                .andExpect(status().isForbidden());

        verify(adService).getById(anyLong());
        verifyNoMoreInteractions(adService);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = {"USER"})
    public void edit() throws Exception {
        mockMvc.perform(put("/ads/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.jsonBody("__files/ad-edit-req.json")))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());

        verify(adService).edit(anyString(), anyLong(), any(AdvertisementEditRequest.class));
        verifyNoMoreInteractions(adService);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = {"ADMIN"})
    public void editRoleAdminStatus403() throws Exception {
        mockMvc.perform(put("/ads/{id}", 1)
                        .content(TestUtils.jsonBody("__files/ad-edit-req.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());

        verifyNoInteractions(adService);
    }

    @Test
    public void editUnauthorizedStatus401() throws Exception {
        mockMvc.perform(put("/ads/{id}", 1)
                        .content(TestUtils.jsonBody("__files/ad-edit-req.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").doesNotExist());
        ;

        verifyNoInteractions(adService);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = {"USER"})
    public void editParamValidationFailedStatus409() throws Exception {
        mockMvc.perform(put("/ads/{id}", 1)
                        .content(TestUtils.jsonBody("__files/ad-edit-req-bad.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict());

        verifyNoInteractions(adService);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = {"USER"})
    public void editAdNotFoundThrowsExceptionStatus404() throws Exception {
        doThrow(AdvertisementNotFoundException.class).when(adService).edit(anyString(), anyLong(), any(AdvertisementEditRequest.class));

        mockMvc.perform(put("/ads/{id}", 1)
                        .content(TestUtils.jsonBody("__files/ad-edit-req.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(adService).edit(anyString(), anyLong(), any(AdvertisementEditRequest.class));
        verifyNoMoreInteractions(adService);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = {"USER"})
    public void editAdNotAvailableThrowsExceptionStatus403() throws Exception {
        doThrow(AccessException.class).when(adService).edit(anyString(), anyLong(), any(AdvertisementEditRequest.class));

        mockMvc.perform(put("/ads/{id}", 1)
                        .content(TestUtils.jsonBody("__files/ad-edit-req.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());

        verify(adService).edit(anyString(), anyLong(), any(AdvertisementEditRequest.class));
        verifyNoMoreInteractions(adService);
    }
}