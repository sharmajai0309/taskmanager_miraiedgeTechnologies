package com.MiraiEdge.Taskmanager.Service;
import java.io.IOException;  

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response, 
            FilterChain filterChain) throws ServletException, IOException {
    	
        
    	try{
// 1. Extract Authorization header

    	    String authHeader = request.getHeader("Authorization");
    	    
// Skip filter if no Bearer token present
    	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
    	        filterChain.doFilter(request, response);
    	        return;
    	    }
    	    
    	    
// 2. Extract and validate JWT
    	    String jwt = authHeader.substring(7);
    	    String username = jwtUtil.extractUsername(jwt);
    	    
    	    
// 3. Validate token and set authentication
    	    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
    	        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    	        if (jwtUtil.validateToken(jwt)) {
    	        	
    	        	
 // Create authentication token
    	            var authToken = new UsernamePasswordAuthenticationToken(
    	                userDetails, null, userDetails.getAuthorities());
    	            SecurityContextHolder.getContext().setAuthentication(authToken);
    	        }
    	    }
    	    filterChain.doFilter(request, response);
    	    System.out.println("Raw header: " + authHeader);
            System.out.println("Extracted token: " + jwt); 
            System.out.println("Extracted username: " + username);
    	}
           
       
        filterChain.doFilter(request, response);
    }
}