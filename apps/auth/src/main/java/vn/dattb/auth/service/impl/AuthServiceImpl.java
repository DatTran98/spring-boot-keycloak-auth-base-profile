package vn.dattb.auth.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import vn.dattb.auth.dto.AuthRequest;
import vn.dattb.auth.dto.AuthResponse;
import vn.dattb.auth.service.AuthService;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("username", authRequest.getUsername());
        map.add("password", authRequest.getPassword());
        map.add("grant_type", "password");
        map.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        String url = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            //map response to AuthResponse
            AuthResponse authResponse = new ObjectMapper().readValue(response.getBody(), AuthResponse.class);
            return authResponse;
        } catch (Exception e) {
            log.error("Error while logging in", e);
            return null; // or handle error scenario
        }
    }
}
