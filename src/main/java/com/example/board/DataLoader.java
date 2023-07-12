package com.example.board;

import com.example.board.dto.PostDto;
import com.example.board.dto.UsersDto;
import com.example.board.entity.Post;
import com.example.board.entity.PostRepository;
import com.example.board.entity.Users;
import com.example.board.entity.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;


 /* METHOD 1: Data Loader - Initialize Data before starting */

//@Slf4j
//@Component
//public class DataLoader implements ApplicationRunner {
//
//    private UsersRepository usersRepository;
//    private PostRepository postRepository;
//
//    @Autowired
//    public DataLoader(UsersRepository usersRepository, PostRepository postRepository) {
//        this.usersRepository = usersRepository;
//        this.postRepository = postRepository;
//    }
//
//    public void run(ApplicationArguments args) {
//        UsersDto usersDto = new UsersDto("abcd", "abcd",null, "abcd");
//        Users user = usersRepository.save(usersDto.toEntity());
//
//        for (int i=1; i<100; i++) {
//            Post result = postRepository.save(new PostDto("Post "+i+".", "Post number" + i, "abcd",
//                    new Timestamp(System.currentTimeMillis()), user).toEntity());
//            if (result==null) {
//                log.info("\n"+i+"- fail");
//            }
//        }
//    }
//}




/* METHOD 2: Paste this in BoardApplication.java, just below the main method */

//	@Autowired
//	UsersRepository usersRepository;
//	@Autowired
//	PostRepository postRepository;
//
//	@Bean
//	InitializingBean sendDatabase() {
//		return () -> {
//			Users user = usersRepository.save(new UsersDto("abcd", "abcd",null, "abcd").toEntity());
//			for (int i=1; i<100; i++) {
//            Post result = postRepository.save(new PostDto("Post "+i+".", "Post number" + i, "abcd",
//                    new Timestamp(System.currentTimeMillis()), user).toEntity());
//			}
//
//		};
//	}
