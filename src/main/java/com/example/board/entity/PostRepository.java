package com.example.board.entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import lombok.*;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAll(Pageable pageable);

    @Query(value="SELECT p FROM Post p WHERE p.title LIKE %?1%")
    Page<Post> findAllByTitle(String title, Pageable pageable);

}
