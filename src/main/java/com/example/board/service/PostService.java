package com.example.board.service;

import com.example.board.dto.PostDto;
import com.example.board.security.UDetails;
import com.example.board.entity.Post;
import com.example.board.entity.PostRepository;
import com.example.board.entity.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.sql.Timestamp;
import java.util.Date;

@Service
@Slf4j
public class PostService {
    private UsersRepository usersRepository;
    private PostRepository postRepository;

    @Autowired
    public PostService(UsersRepository usersRepository, PostRepository postRepository) {
        this.usersRepository = usersRepository;
        this.postRepository = postRepository;
    }

    public Post writePost(PostDto postDto) {
        //Post Writer - valid
        String username = ""; //faker.name().username();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UDetails) {
            username = ((UDetails) principal).getUsername();
//        } else {
//            username = principal.toString();
//        }
            postDto.setWriter(username);


            // Post User
            postDto.setUsers(usersRepository.findByUsername(username));

            //Date
            Date date = new Date();
            Timestamp timestamp2 = new Timestamp(date.getTime());
            postDto.setDate(timestamp2);

            //Post save
            Post post = postDto.toEntity();
            return postRepository.save(post);
        }
        return null;
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public Post getCurrentPost(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Page<Post> listPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return posts;
    }

    public Page<Post> listPostsByTitle(String title, Pageable pageable) {
        return postRepository.findAllByTitle(title, pageable);

    }

    public void populateList(Model model, Page<Post> posts, Pageable pageable) {

        //Pagination Starts from 1
        int totalPages = posts.getTotalPages();

        //Method 1: set current page as next page
//		model.addAttribute("hasPrevious", ( pageable.getPageNumber() > 0));
//		model.addAttribute("hasNext", totalPages-pageable.next().getPageNumber()>0);
//		model.addAttribute("previous", pageable.getPageNumber());
//		model.addAttribute("current", pageable.next().getPageNumber());
//		model.addAttribute("next", pageable.next().next().getPageNumber());
//		model.addAttribute("pages", posts);

        //Method 2: add 1 to every page
        model.addAttribute("hasPrevious", ( pageable.hasPrevious() && pageable.previousOrFirst().getPageNumber()+1 > 0));
        model.addAttribute("hasNext", totalPages-(pageable.getPageNumber()+1)>0);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber()+1);
        model.addAttribute("current", pageable.getPageNumber()+1);
        model.addAttribute("next", pageable.next().getPageNumber()+1);
        model.addAttribute("pages", posts);


    }


    public Post update(Post post, PostDto postDto) {
            post.setContent(postDto.getContent());
            post.setTitle(postDto.getTitle());
            return postRepository.save(post);
    }


}
