package com.medical.secretaireservice.Service;

import com.medical.secretaireservice.Model.AdminT;
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


}
