package com.example.memotion.image.repository;

import com.example.memotion.image.domain.Image;
import com.example.memotion.reecord.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    public List<Image> findImagesByDiary(Diary diary);

    public void deleteImageByDiary(Diary diary);

}
