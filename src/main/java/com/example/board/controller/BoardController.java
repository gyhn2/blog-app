package com.example.board.controller;
import com.example.board.entity.Users;
import com.example.board.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.example.board.service.UsersService;
import com.example.board.dto.PostDto;
import com.example.board.dto.UsersDto;
import com.example.board.entity.Post;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@Controller
@Slf4j
public class BoardController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private PostService postService;
	Faker faker = new Faker();
		
	@GetMapping("/")
	public String index(Model model) {
		// Sign in/out status
		model.addAttribute("userStatus", usersService.userStatus());
		model.addAttribute("body", faker.lorem().sentence());
		return "index";
	}
	
	@GetMapping("/new")
	public String newPost(Model model) {
		model.addAttribute("userStatus", usersService.userStatus());
		return "newPost";
	}

	//valid
	@PostMapping(value="/posts")
	public String createPost(PostDto postDto ) {
		if (usersService.userStatus()) {
			return "redirect:/posts/" + postService.writePost(postDto).getId();
		}
		return "error";
	}

	@GetMapping("/posts")
	public String allposts(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=5) Pageable pageable, @RequestParam(name = "title", required= false) String title) {
		model.addAttribute("userStatus", usersService.userStatus());

		Page<Post> posts;
		if (title==null)
			posts = postService.listPosts(pageable);
		else
			posts = postService.listPostsByTitle(title, pageable);
		postService.populateList(model, posts, pageable);
		return "list";
	}
	
	@GetMapping(value="/posts/{id}", produces ="text/html; charset=utf-8")
	public String postById(@PathVariable Long id, Model model) {
		Post post = postService.getCurrentPost(id);
		if (post!=null) {
			model.addAttribute("postModel", post);
			model.addAttribute("time", post.getDate().toString().substring(0, 16));
			model.addAttribute("userStatus", usersService.userStatus());
			model.addAttribute("sameUser", usersService.sameUser(post));
		}
		return "post";
	}

	@DeleteMapping("/posts/{id}")
	public String deletePost(@PathVariable Long id) {
		Post post = postService.getCurrentPost(id);
		if (post!=null && usersService.sameUser(post)) {
			postService.deletePost(id);
			return "redirect:/posts";
		}
		return "error";
	}

	@GetMapping(value="/posts/{id}/edit")
	public String editPage(@PathVariable Long id, Model model) {
		Post post = postService.getCurrentPost(id);
		if (post!=null && usersService.sameUser(post)) {
			model.addAttribute("postModel", post);
			model.addAttribute("userStatus", usersService.userStatus());
			model.addAttribute("sameUser", usersService.sameUser(post));
			return "edit";
		}
		return "error";
	}

	@PutMapping("/posts/{id}")
	public String editPost(@PathVariable Long id, PostDto postDto) {
		Post post = postService.getCurrentPost(id);
		if (post!=null && usersService.sameUser(post)) {
			postService.update(post, postDto);
			return "redirect:/posts/"+id;
		}
		return "redirect:/";
	}

//	@GetMapping("/posts/search")
//	public String search(@RequestParam(value = "title", required = false) String param) {
//		log.info(param);
//		return "redirect:/";
//	}


	@RequestMapping("/sign-in") //both GET and POST
	public String loginPage(Model model, HttpServletRequest request, @ModelAttribute UsersDto usersDto) {
		usersService.handleSignIn(model, request, usersDto);
		return "login";
	}

	@GetMapping("/sign-up")
	public String register(Model model, HttpServletRequest request) {
		usersService.signUpError(model, request);
		return "register";
	}
	
	@PostMapping("/createUser")
	public String registerUser(@Valid UsersDto usersDto, BindingResult bindingResult, HttpServletRequest request) {
		return usersService.registerUserWithCheck(usersDto, bindingResult, request);
	}

//	@GetMapping("/users/{id}")
//	public String getUser(@PathVariable Long id, Model model) {
//		usersService.getUser(id);
//		return "user";
//	}
	@GetMapping("/users/{username}")
	public String getUser(@PathVariable String username, Model model) {
		Users user = usersService.getUser(username);
		model.addAttribute("userStatus", usersService.userStatus());
		if (user!=null) {
			model.addAttribute("nickname", user.getNickname());
		} else {
			model.addAttribute("nickname", "User does not exist");
		}
		return "user";
	}

	@GetMapping("/users")
	public ResponseEntity<List<Users>> usersList(Model model) {
		model.addAttribute("userStatus", usersService.userStatus());
		return ResponseEntity.ok().body(usersService.getAllUsers());
	}

	@GetMapping("/posts-api")
	public ResponseEntity<Page<Post>> postsApi(Model model, Pageable pageable) {
		model.addAttribute("userStatus", usersService.userStatus());
		return ResponseEntity.ok().body(postService.listPosts(pageable));
	}



	//modify from here
	@GetMapping("/nickname")
	public String usernameChange(Model model) {
		if (usersService.userStatus()) {
			model.addAttribute("userStatus", usersService.userStatus());
		}
		return "userInfoChange";
	}

	@PutMapping("/nickname/update")
	public String changeUserInfo(@RequestParam String nickname) {
		if (usersService.userStatus()) {
			Users userChanged = usersService.update(nickname);
			System.out.println(userChanged);
		}
		return "redirect:/posts";
	}

}

//drop down sort
// previous post etc
// nav bar sign up
// warning popup on delete post
//		log.info(usersRepository.findByUsername(username).getPosts().toString());
// width of container
// @media dynamic width for buttons
// submit, edit button 위치
// dark light theme
// responsive @media - in smaller screen, make max-width smaller for "back", and remove halving
/*
- if you want to modify usernames and also display the modified username on old posts,
then consider removing the "writer" field on Post and replacing post.writer with the post.users.username instead.

*/