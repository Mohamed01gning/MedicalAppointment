package com.medical.secretaireservice.Configuration;

import com.medical.secretaireservice.JwtService.ServiceTokenGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration
{
    private final ServiceTokenGenerator tokenGenerator;

    public WebClientConfiguration(ServiceTokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

    @Bean
    public WebClient getWebClient()
    {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .filter(filterWebClient())
                .build();

    }

    @Bean
    public ExchangeFilterFunction filterWebClient()
    {
        return (request, next) ->
        {
            ClientRequest serviceRequest=ClientRequest
                    .from(request)
                    .header("Authorization-Service", "Bearer "+tokenGenerator.generatorServiceToken())
                    .build();
            return next.exchange(serviceRequest);
        };
    }
}
