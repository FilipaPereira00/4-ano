package com.bosques.rasbet_backend.event;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class EventService {

    @Cacheable("events")
    public List<Event> getEvents() {
        return fetchAllEvents();
    }

    @Scheduled(fixedRate = 10000) // 60 segundos
    @CachePut(value = "events")
    public List<Event> refreshEvents() {

        long start = System.currentTimeMillis();

        var ret = fetchAllEvents();

        long end = System.currentTimeMillis();
        long elaspedTime = end - start;
        System.out.println("Scheduled request took " + elaspedTime + " ms");


        return ret;
    }

    private List<Event> fetchAllEvents()
    {
        System.out.println("Fetching new EVENT data.");
        String url  ="http://api.isportsapi.com/sport/football/odds/inplay?api_key=p8fvdMvpfAdc3Ih3";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<EventAPIResponse> response = restTemplate.getForEntity(url, EventAPIResponse.class );
        var eventAPIResponse = response.getBody();

        return eventAPIResponse.getData();
    }
}
