package com.example.board.security;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.board.entity.Post;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.board.entity.Users;

import lombok.*;

@Getter
public class UDetails implements UserDetails {
    private Users user;

	public UDetails(Users user) {
		this.user = user;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> {
            return "USER";
        });
		
//        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(); 
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;

	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Users getUser() { return user; }
	public void setUser(Users user) {this.user = user; }


}
