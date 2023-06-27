package com.example.board.service;
import com.example.board.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.security.UDetails;
import com.example.board.dto.UsersDto;
import com.example.board.entity.Users;
import com.example.board.entity.UsersRepository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Slf4j
public class UsersService {
    private final UsersRepository usersRepository;
//    private final CustomUserDetailsService customUserDetailsService;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional
    public Long register(UsersDto usersDto) {
        //user already exists
        Users existing = usersRepository.findByUsername(usersDto.getUsername());
        if (existing != null) {
            return (long) -1;
        }

        //save account
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        usersDto.setPassword(passwordEncoder.encode(usersDto.getPassword()));
        usersDto.setNickname(usersDto.getUsername());
        return usersRepository.save(usersDto.toEntity()).getId();
    }



    //User Authentication Status
    public Boolean userStatus() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	
	    if (authentication instanceof AnonymousAuthenticationToken) {
	    	return false;
	    } else { 
	    	if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))) {
	    		return true;
	    	} else {
	    		return true;
	    	}
	    }
    }


    //Check whether the current user is the author of the post
    public Boolean sameUser(Post post) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (post != null && principal instanceof UDetails) {
            Users currentUser = ((UDetails) principal).getUser();
            Users postUser = post.getUsers();
            //check for null after database manipulation
            if (postUser != null) {
//                return currentUser.getId().equals(postUser.getId());
                return currentUser.getUsername().equals(postUser.getUsername());
            }
        }
        return false;
    }

    public void handleSignIn(Model model, HttpServletRequest request, UsersDto usersDto) {
        //previous Url
        String ref = request.getHeader("Referer");
        HttpSession session = request.getSession();
        session.setAttribute("prevUrl", ref);
        session.setAttribute("username", usersDto.getUsername());

        //log in error
        String error = (String)request.getAttribute("message");
        if (error!=null) {
            model.addAttribute("signInError", true);
            model.addAttribute("message", error);
            model.addAttribute("username", session.getAttribute("username"));
            session.removeAttribute("prevUrl");
            session.removeAttribute("username");
        }

        //redirected from sign-up
        model.addAttribute("signUpSuccess", session.getAttribute("signUpSuccess"));
        session.removeAttribute("signUpSuccess");

    }

    public void signUpError(Model model, HttpServletRequest request) {
        Boolean signUpError = (Boolean) request.getSession().getAttribute("signUpError");
        if (signUpError!=null) {
            model.addAttribute("signUpError", true);
            model.addAttribute("message", request.getSession().getAttribute("message"));
            model.addAttribute("username", request.getSession().getAttribute("userInfo"));
            request.getSession().removeAttribute("signUpError");
            request.getSession().removeAttribute("message");
            request.getSession().removeAttribute("userInfo");
        }
    }

    public String registerUserWithCheck(UsersDto usersDto, BindingResult bindingResult, HttpServletRequest request) {

        //validation
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("username")) {
                request.getSession().setAttribute("message", "Username should be at least 2 characters long");
            } else {
                request.getSession().setAttribute("message", "Password should be at least 2 characters long");
            }
            request.getSession().setAttribute("signUpError", true);
            request.getSession().setAttribute("userInfo", usersDto.getUsername());
            return "redirect:/sign-up";
        }

        //sign up if username available
        Long s = this.register(usersDto);
        if (s < 0) {
            request.getSession().setAttribute("signUpError", true);
            request.getSession().setAttribute("message", "Username taken!");
            request.getSession().setAttribute("userInfo", usersDto.getUsername());
            return "redirect:/sign-up";
        }
        request.getSession().setAttribute("signUpSuccess", "Accounted Created! Sign In.");
        return "redirect:/sign-in";

    }

    public Users update(String nickname) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UDetails) {
            Users currentUser = ((UDetails) principal).getUser();
            currentUser.setNickname(nickname);
            return usersRepository.save(currentUser);
        }
        return null;
    }

    public Users getUser(String username) {
        return usersRepository.findByUsername(username);
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }
}
