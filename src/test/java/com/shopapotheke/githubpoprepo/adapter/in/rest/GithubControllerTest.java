package com.shopapotheke.githubpoprepo.adapter.in.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopapotheke.githubpoprepo.adapter.out.rest.dto.GithubResponse;
import com.shopapotheke.githubpoprepo.adapter.out.rest.dto.Item;
import com.shopapotheke.githubpoprepo.application.port.in.GetGithubPopularRepositories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(GithubController.class)
public class GithubControllerTest {

    public static final String API_URL = "/api/pop";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GetGithubPopularRepositories getGithubPopularRepositories;

    @Test
    public void shouldReturnPopularRepos() throws Exception {
        //given
        int totalCount = 11043779;
        String id = "47997753";
        String repoName = "react-native-push-notification";
        String repoDescription = "React Native Local and Remote Notifications";
        String language = "Java";
        String repoUrl = "https://github.com/zo0r/react-native-push-notification";
        int stars = 6629;
        String creationDate = "2015-12-14T19:53:36Z";

        Item firstItem = new Item(id,
                                  repoName,
                                  repoDescription,
                                  language,
                                  repoUrl,
                                  stars,
                                  creationDate);
        GithubResponse mockResponse = new GithubResponse(totalCount, List.of(firstItem));

        given(getGithubPopularRepositories.getPopularRepositories(any(GetGithubPopularRepositories.Arguments.class)))
                .willReturn(mockResponse);

        //when
        ResultActions result = mockMvc.perform(get(API_URL)
                                                       .param("date", "2023-08-13")
                                                       .param("language", "java")
                                                       .param("top", "10")
                                                       .contentType(MediaType.APPLICATION_JSON));
        //then
        MvcResult mvcResult = result.andExpect(MockMvcResultMatchers.status().isOk())
                                    .andReturn();

        JsonNode actual = objectMapper.readTree((mvcResult.getResponse().getContentAsString()));
        JsonNode expected = getJsonFile("json/get_response.json");

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldReturnBadRequestWhenDataIsInvalid() throws Exception {
        //given
        String invalidDate = "invalid-date";

        //when
        ResultActions result = mockMvc.perform(get(API_URL)
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
    public void shouldReturnBadRequestWhenTopIsInvalid() throws Exception {
        //given
        String invalidTopValue = "99";

        //when
        ResultActions result = mockMvc.perform(get(API_URL)
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
    public void shouldReturnServerErrorInCaseOfUnknownException() throws Exception {
        //given
        given(getGithubPopularRepositories.getPopularRepositories(any(GetGithubPopularRepositories.Arguments.class)))
                .willThrow(new RuntimeException("Some exception"));

        //when
        ResultActions result = mockMvc.perform(get(API_URL)
                                                       .param("date", "2023-08-13")
                                                       .param("language", "java")
                                                       .param("top", "10")
                                                       .contentType(MediaType.APPLICATION_JSON));
        //then
        result.andExpect(MockMvcResultMatchers.status().is5xxServerError())
              .andExpect(MockMvcResultMatchers.jsonPath("$.description")
                                              .value("An unexpected error occurred."));
    }

    private JsonNode getJsonFile(String jsonFile) throws IOException {
        return objectMapper.readValue(new ClassPathResource(jsonFile).getFile(), JsonNode.class);
    }
}