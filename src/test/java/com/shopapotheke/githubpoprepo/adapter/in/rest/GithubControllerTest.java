package com.shopapotheke.githubpoprepo.adapter.in.rest;

import com.shopapotheke.githubpoprepo.adapter.out.rest.dto.GithubResponse;
import com.shopapotheke.githubpoprepo.application.port.in.GithubService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(GithubController.class)
public class GithubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GithubService githubService;

    @Test
    public void testGetPopularRepos() throws Exception {
        //given
        GithubResponse mockResponse = new GithubResponse(10, new ArrayList<>());


        BDDMockito.given(githubService.getPopularRepositories(any(GithubService.Arguments.class)))
                  .willReturn(mockResponse);

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api")
                                                                     .param("date", "2023-08-13")
                                                                     .param("language", "java")
                                                                     .param("top", "10")
                                                                     .contentType(MediaType.APPLICATION_JSON));
        //then
        result.andExpect(MockMvcResultMatchers.status().isOk())
              .andExpect(MockMvcResultMatchers.jsonPath("$.total_count").exists());
    }

    @Test
    public void testInvalidDate() throws Exception {
        //given
        String invalidDate = "invalid-date";

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api")
                                                                     .param("date", invalidDate)
                                                                     .param("language", "java")
                                                                     .param("top", "10")
                                                                     .contentType(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest())
              .andExpect(MockMvcResultMatchers.jsonPath("$.description")
                                              .value("Bad input: Date query param is not valid, is should follow the format: yyyy-MM-dd"));
    }

    @Test
    public void testInvalidTopValue() throws Exception {
        //given
        String invalidTopValue = "99";

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api")
                                                                     .param("date", "2023-08-13")
                                                                     .param("language", "java")
                                                                     .param("top", invalidTopValue)
                                                                     .contentType(MediaType.APPLICATION_JSON));
        //then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest())
              .andExpect(MockMvcResultMatchers.jsonPath("$.description")
                                              .value("Bad input: Top value not allowed. Possible values: 10, 50, 100"));
    }

    @Test
    public void testGenericException() throws Exception {
        //given
        BDDMockito.given(githubService.getPopularRepositories(any(GithubService.Arguments.class)))
                  .willThrow(new RuntimeException("Some exception"));

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api")
                                                                     .param("date", "2023-08-13")
                                                                     .param("language", "java")
                                                                     .param("top", "10")
                                                                     .contentType(MediaType.APPLICATION_JSON));
        //then
        result.andExpect(MockMvcResultMatchers.status().is5xxServerError())
              .andExpect(MockMvcResultMatchers.jsonPath("$.description")
                                              .value("An unexpected error occurred."));
    }
}