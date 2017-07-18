package com.example.fileApi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 *  @author Nicolas Sirac
 */
public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {
    @Query("update AlbumEntity set title = :new_title where id = :id")
    @Modifying(clearAutomatically = true)
    int updateTitle(@Param("new_title") String title, @Param("id") Long id);

    @Query(nativeQuery = true,value="select ae.* from album ae where ae.id = :id")
    Optional<AlbumEntity> findById(@Param("id")Long id);

    @Query(nativeQuery = true,value="Select ae.* from album ae where ae.title = :title")
    Optional<AlbumEntity> findByTitle(@Param("title") String title);

    @Query(nativeQuery = true,value="Select ae.* from album ae join users WHERE ae.title = :title and users.pseudo = :pseudo LIMIT 1;")
    Optional<AlbumEntity> findByTitleAndPseudo(@Param("title") String title,@Param("pseudo") String pseudo);

    @Query(nativeQuery = true,value="Select ae.* from album ae where ae.users=:id")
    List<AlbumEntity> findAllByUser(@Param("id") Long id);
   /* @Query("Select * from album where  user.id= :user_id")
    List<AlbumEntity> findByUser(@Param("user") Long user_id);*/
}
