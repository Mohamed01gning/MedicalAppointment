package com.medical.cnxservice.Service;


import com.medical.cnxservice.Exception.NotFoundEx;
import com.medical.cnxservice.Model.User;
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

    public User getMedecinByMat(String mat)
    {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/med/service/{mat}")
                        .build(mat)
                )
                .retrieve()
                /*.onStatus(HttpStatusCode::is4xxClientError, ClientResponse ->{
                    if (ClientResponse.statusCode()== HttpStatus.BAD_REQUEST)
                    {
                        return ClientResponse.bodyToMono(new ParameterizedTypeReference<Map<String,String>>(){})
                                .flatMap(error -> Mono.error(new NotFoundEx(error)));
                    }
                    return Mono.error(new RuntimeException("erreur coté serveur : StatusCode > 400"));
                })*/
                .bodyToMono(User.class)
                .block();
    }
    public User getSecretaireByMat(String mat)
    {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/sec/service/{mat}")
                        .build(mat)
                )
                .retrieve()
               /* .onStatus(HttpStatusCode::is4xxClientError, ClientResponse ->{
                    if (ClientResponse.statusCode()== HttpStatus.BAD_REQUEST)
                    {
                        return ClientResponse.bodyToMono(new ParameterizedTypeReference<Map<String,String>>(){})
                                .flatMap(error -> Mono.error(new NotFoundEx(error)));
                    }
                    return Mono.error(new RuntimeException("erreur coté serveur : StatusCode > 400"));
                })*/
                .bodyToMono(User.class)
                .block();
    }

    public User getAdminByMat(String mat)
    {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/admin/service/{mat}")
                        .build(mat)
                )
                .retrieve()
                /*.onStatus(HttpStatusCode::is4xxClientError, ClientResponse ->{
                    if (ClientResponse.statusCode()== HttpStatus.BAD_REQUEST)
                    {
                        return ClientResponse.bodyToMono(new ParameterizedTypeReference<Map<String,String>>(){})
                                .flatMap(error -> Mono.error(new NotFoundEx(error)));
                    }
                    return Mono.error(new RuntimeException("erreur coté serveur : StatusCode > 400"));
                })*/
                .bodyToMono(User.class)
                .block();
    }
}
