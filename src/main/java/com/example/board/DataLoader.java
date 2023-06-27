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
//        UsersDto usersDto = new UsersDto("gyhn2", "gyhn2",null, "gyhn2");
//        Users user = usersRepository.save(usersDto.toEntity());
//
//        //PostDto postDto = new PostDto("첫번째 게시물 입니다.", "첫번째 게시물입니다.", "gyhn2", new Timestamp(System.currentTimeMillis()), user);
//        for (int i=1; i<100; i++) {
//            Post result = postRepository.save(new PostDto(i+"번째 게시물 입니다.", i+"번째 게시물입니다.", "gyhn2",
//                    new Timestamp(System.currentTimeMillis()), user).toEntity());
//            if (result==null) {
//                log.info("\n"+i+"번 게시물 등록 실패");
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
//			Users user = usersRepository.save(new UsersDto("gyhn2", "gyhn2",null, "gyhn2").toEntity());
//			//PostDto postDto = new PostDto("첫번째 게시물 입니다.", "첫번째 게시물입니다.", "gyhn2", new Timestamp(System.currentTimeMillis()), user);
//			for (int i=1; i<100; i++) {
//				Post result = postRepository.save(new PostDto(i+"번째 게시물 입니다.", i+"번째 게시물입니다.", "gyhn2",
//						new Timestamp(System.currentTimeMillis()), user).toEntity());
//
//			}
//
//		};
//	}
