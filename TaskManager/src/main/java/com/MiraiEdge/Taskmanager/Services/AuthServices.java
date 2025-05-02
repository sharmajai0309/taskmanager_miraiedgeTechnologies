package com.MiraiEdge.Taskmanager.Services;

import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.MiraiEdge.Taskmanager.Entity.Role;
import com.MiraiEdge.Taskmanager.Entity.User;
import com.MiraiEdge.Taskmanager.dto.AuthRequest;
import com.MiraiEdge.Taskmanager.repository.UserRepository;
import com.MiraiEdge.Taskmanager.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServices {
	
	
	 private final UserRepository userRepository;
	    private final PasswordEncoder passwordEncoder;
	    private final JwtUtil jwtUtil;
	    private final AuthenticationManager authenticationManager;

	    public void signup(AuthRequest request) {
	        User user = new User();
	        user.setUsername(request.getUsername());
	        user.setPassword(passwordEncoder.encode(request.getPassword()));
	        user.setRoles(Set.of(Role.ROLE_USER));
	        userRepository.save(user);
	    }

	    public String login(AuthRequest request) {
	        authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
	        );
	        return jwtUtil.generateToken(request.getUsername());
	    }

}
