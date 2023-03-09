package com.srb.srb.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.srb.srb.domain.dto.SongDto;
import com.srb.srb.domain.entity.Song;
import com.srb.srb.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
@RequiredArgsConstructor
public class SpotifyClient {
    private final SongRepository songRepository;

    @Value("${spotify.clientId}")
    private String clientId;

    @Value("${spotify.clientSecret}")
    private String clientSecret;

    /**
     * Spotify 토큰발급
     * @return
     */
    public String getAccessToken () {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://accounts.spotify.com/api/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String token = (String) response.getBody().get("access_token");
            return token;
        } else {
            return null;
        }
    }

    /**
     * 검색조건별 Spotify API 호출
     * @param search
     * @return
     */
    public Map<String, Object> searchTrack(String search) {
        String token = getAccessToken();
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.spotify.com/v1/search?q=" + search + "&type=track&market=KR";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String response = responseEntity.getBody();

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(response);
            JsonNode itemsNode = root.path("tracks").path("items");

            for (JsonNode itemNode : itemsNode) {
                Song detailSong = songRepository.findSongByContent(itemNode.get("name").asText());
                if (detailSong == null) {
                    SongDto songDto = new SongDto();
                    songDto.setTitle(search);
                    songDto.setContent(itemNode.get("name").asText());
                    songRepository.save(songDto.toEntity());
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        List<Song> songList = songRepository.findSongByTitle(search);

        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        result.put("code", HttpStatus.OK.value());
        result.put("songList", songList);

        return result;
    }
}

