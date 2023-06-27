package com.example.board.dto;
import com.example.board.entity.Post;
import com.example.board.entity.Users;

import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
public class UsersDto {

    @NotBlank
    @NotNull
    @NotEmpty
    @Size(min=2, max=30)
    private String username;

    @NotBlank
    @NotNull
    @NotEmpty
    @Size(min=2)
    private String password;

    private List<Post> posts;

//    @NotNull
    private String nickname;

//    @NotNull
//    @Min(18)
//    private Integer age;

    public Users toEntity() {
        return new Users(null, username, password, nickname, posts);
    }


}
