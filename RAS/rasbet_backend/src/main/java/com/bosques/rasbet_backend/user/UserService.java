package com.bosques.rasbet_backend.user;

import com.bosques.rasbet_backend.event.EventAPIResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {

    public UserService() {
    }

    @Cacheable("users")
    public List<User> getUsers() {
        System.out.println("Fetching new USER data.");

        String url  ="https://randomuser.me/api/?noinfo&results=1000";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserAPIResponse> response = restTemplate.getForEntity(url, UserAPIResponse.class );
         var userAPIResponse = response.getBody();



        return userAPIResponse.getResults();

    }

}
