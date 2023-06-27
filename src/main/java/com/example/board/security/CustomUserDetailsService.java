package com.example.board.security;

import com.example.board.entity.Users;
import com.example.board.entity.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Users user = usersRepository.findByUsername(username);
        if (user == null) {
            log.warn("User {} does not exist in our database", username);
            throw new UsernameNotFoundException("User named "+ username+" is not found.");
        }
        log.info("User {} exists in our database", username);
        return new UDetails(user);
//        return new UDetails(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), user.getAuthorities());
    }

}
