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

    /**
     * 노래리스트 출력(가수 이름)
     * @param title
     * @return
     */
    public List<Song> songList (String title) {
        List<Song> songList = songRepository.findSongByTitle(title);

        return songList;
    }

    /**
     * 노래 상세조회(제목)
     * @param content
     * @return
     */
    public Song detailSong(String content) {
        Song detailSong = songRepository.findSongByContent(content);

        if (detailSong != null) {
            return detailSong;
        } else {
            return null;
        }
    }
}
