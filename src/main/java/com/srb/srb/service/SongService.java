package com.srb.srb.service;

import com.srb.srb.domain.entity.Song;
import com.srb.srb.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;


    public List<Song> songList (String title) {
        List<Song> songList = songRepository.findSongByTitle(title);

        return songList;
    }

}
