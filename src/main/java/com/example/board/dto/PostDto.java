package com.example.board.dto;
import java.sql.Timestamp;

import com.example.board.entity.Post;

import com.example.board.entity.Users;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class PostDto {

	@NotBlank
	@NotNull
	@NotEmpty
	private String title;
	@NotBlank
	@NotNull
	@NotEmpty
	private String content;

	private String writer;
	private Timestamp date;
	private Users users;

	
    public Post toEntity() {
        return new Post(null, title, content, writer, date, users);
    }


}
