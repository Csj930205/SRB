package com.srb.srb.repository;

import com.srb.srb.domain.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findSongByTitle(String title);
}
