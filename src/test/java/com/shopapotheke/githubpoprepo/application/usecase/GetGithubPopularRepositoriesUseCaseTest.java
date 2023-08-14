package com.shopapotheke.githubpoprepo.application.usecase;

import com.shopapotheke.githubpoprepo.application.port.in.GetGithubPopularRepositories;
import com.shopapotheke.githubpoprepo.application.port.out.GithubClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class GetGithubPopularRepositoriesUseCaseTest {
    @Mock
    private GithubClient githubRestClient;
    @InjectMocks
    private GetGithubPopularRepositoriesUseCase getGithubPopularRepositoriesUseCase;

    @Test
    void shouldCallGithubClient() {
        //given
        GetGithubPopularRepositories.Arguments arguments =
                new GetGithubPopularRepositories.Arguments("2000-01-01",
                                                           "Java",
                                                           10);

        //when
        getGithubPopularRepositoriesUseCase.getPopularRepositories(arguments);

        //then
        then(githubRestClient).should().fetchPopularRepositories(arguments);
    }
}