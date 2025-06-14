package com.medical.medecinservice.Service;

import com.medical.medecinservice.Exception.NotFoundEx;
import com.medical.medecinservice.Model.AdminT;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class CallService
{

    private final WebClient webClient;

    public CallService(WebClient webClient)
    {
        this.webClient = webClient;
    }

    public AdminT getAdminByMat(String mat)
    {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/admin/service/token/{mat}")
                        .build(mat)
                )
                .retrieve()
                .bodyToMono(AdminT.class)
                .block();
    }

    public AdminT getSecretaireByMat(String mat)
    {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/sec/service/token/{mat}")
                        .build(mat)
                )
                .retrieve()
                .bodyToMono(AdminT.class)
                .block();
    }

}
