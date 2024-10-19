package com.dailycodebuffer.OrderService.external.interceptor;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import java.io.IOException;
import java.util.Objects;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
    private OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    public RestTemplateInterceptor(OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager){
        this.oAuth2AuthorizedClientManager = oAuth2AuthorizedClientManager;
    }
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // Obtener el token de acceso
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("internal-client")
                .principal("internal")
                .build();

        String tokenValue = oAuth2AuthorizedClientManager.authorize(authorizeRequest)
                .getAccessToken()
                .getTokenValue();

        System.out.println("JWT Token: " + tokenValue);

        // Añadir el token JWT al encabezado Authorization
        request.getHeaders().add("Authorization", "Bearer " + tokenValue);

        return execution.execute(request, body);
    }
}
