package com.srb.srb.controller;

import com.srb.srb.common.SpotifyClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/song")
public class SpotifyController {
    private final SpotifyClient spotifyClient;

    @GetMapping("list")
    public ResponseEntity<Map<String, Object>> token (@RequestParam String search) {
        Map<String, Object> result = spotifyClient.searchTrack(search);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
