package com.medical.rvservice.Service;

import com.medical.rvservice.Model.UserToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CallService
{

    private final WebClient webClient;

    public CallService(WebClient webClient)
    {
        this.webClient = webClient;
    }

    public UserToken getAdminByMat(String mat)
    {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/admin/service/token/{mat}")
                        .build(mat)
                )
                .retrieve()
                .bodyToMono(UserToken.class)
                .block();
    }

    public UserToken getMedecinByMat(String mat)
    {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/med/service/token/{mat}")
                        .build(mat)
                )
                .retrieve()
                .bodyToMono(UserToken.class)
                .block();
    }

    public UserToken getSecretaireByMat(String mat)
    {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/sec/service/token/{mat}")
                        .build(mat)
                )
                .retrieve()
                .bodyToMono(UserToken.class)
                .block();
    }
}
