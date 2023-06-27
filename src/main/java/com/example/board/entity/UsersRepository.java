package com.example.board.entity;
import com.example.board.dto.UsersDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import lombok.*;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

	Users findByUsername(String username);


}
