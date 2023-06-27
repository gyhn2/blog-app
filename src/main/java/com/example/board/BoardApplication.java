package com.example.board;

import com.example.board.dto.PostDto;
import com.example.board.dto.UsersDto;
import com.example.board.entity.Post;
import com.example.board.entity.PostRepository;
import com.example.board.entity.Users;
import com.example.board.entity.UsersRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;

@SpringBootApplication
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

}



