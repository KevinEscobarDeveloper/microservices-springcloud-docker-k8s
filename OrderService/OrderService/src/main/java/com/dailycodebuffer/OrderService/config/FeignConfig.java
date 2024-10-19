package com.dailycodebuffer.OrderService.config;

import com.dailycodebuffer.OrderService.external.decoder.CustomErrorDecoder;
import com.dailycodebuffer.OrderService.external.interceptor.OAuthRequestInterceptor;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

@Configuration
public class FeignConfig {

    @Bean
    ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    @Bean
    public RequestInterceptor oauthRequestInterceptor(OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager) {
        // Registrar el OAuthRequestInterceptor para Feign
        return new OAuthRequestInterceptor(oAuth2AuthorizedClientManager);
    }
}
