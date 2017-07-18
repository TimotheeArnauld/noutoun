package com.example.fileApi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolas on 09/04/2017.
 */
@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    @Query("update ImageEntity set title = :new_title where id = :id")
    @Modifying(clearAutomatically = true)
    int updateTitle(@Param("new_title") String title, @Param("id") Long id);

    @Query("update ImageEntity set likescore = :new_like where id = :id")
    @Modifying(clearAutomatically = true)
    int updateLike(@Param("new_like") Integer likescore, @Param("id") Long id);

    @Query("update ImageEntity set dislikescore = :new_dislike where id = :id")
    @Modifying(clearAutomatically = true)
    int updateDislike(@Param("new_dislike") Integer dislikescore, @Param("id") Long id);

    @Query(nativeQuery = true,value="Select ie.* from image ie where ie.title = :title")
    List<ImageEntity> findByTitle(@Param("title")String title);

    @Query(nativeQuery = true,value="Select ie.* from image ie where ie.id = :id")
    Optional<ImageEntity> findById(@Param("id")Long id);

    @Query(nativeQuery = true,value="Select ie.* from image ie  where ie.album_photo = :id")
    List<ImageEntity> findByAlbum(@Param("id")Long id);

    @Query(nativeQuery = true, value="Select * from image ORDER BY RAND() LIMIT 5")
    List<ImageEntity> findRandom();


}