package com.user_service.service;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user_service.exception.InvalidUserException;
import com.user_service.model.dto.B2CUserDTO;

@Service
public class AzureB2CServiceImpl implements IAzureB2CService {

    @Value("${azure.ad.b2c.client-id}")
    private String clientId;

    @Value("${azure.ad.b2c.client-secret}")
    private String clientSecret;

    @Value("${azure.ad.b2c.tenant-id}")
    private String tenantId;

    @Value("${azure.ad.b2c.token-uri}")
    private String tokenUri;

    @Value("${azure.ad.b2c.graph-api}")
    private String graphApiUri;

    private String tenantDomain = "appfm.onmicrosoft.com";

    public void createUser(String displayName, String email, String password) {
        try {
            String token = getAccessToken();
            email = email.toLowerCase().trim();

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Accept-Language", "es-ES");

            B2CUserDTO userDTO = new B2CUserDTO(displayName, email, password);
            HttpEntity<B2CUserDTO> request = new HttpEntity<>(userDTO, headers);

            var response = restTemplate.postForEntity(graphApiUri, request, String.class);
        } 
        catch(HttpClientErrorException ex) {
            
            var errorMessage = getErrorMessage(ex);

            if (errorMessage.contains("userPrincipalName already exists")) {
                return;
            }

            throw new InvalidUserException(errorMessage);
        }
        catch(Exception ex){
            throw new InvalidUserException("Error Creando usuario en Azure AD B2C.");
        }
        
    }

    
    private String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "client_id=" + clientId +
                      "&scope=https://graph.microsoft.com/.default" +
                      "&client_secret=" + clientSecret +
                      "&grant_type=client_credentials";

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUri, request, Map.class);
        return response.getBody().get("access_token").toString();
    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            String responseBody = ex.getResponseBodyAsString();
            // Si deseas parsearlo:
            ObjectMapper mapper = new ObjectMapper();
            JsonNode errorJson = mapper.readTree(responseBody);
            return errorJson.path("error").path("message").asText();
        } catch (Exception ex1) {
            throw new InvalidUserException("Error Creando usuario en Azure AD B2C.");
        }
    }
}
