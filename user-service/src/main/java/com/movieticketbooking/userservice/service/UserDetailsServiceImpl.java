package com.movieticketbooking.userservice.service;

import com.movieticketbooking.userservice.entity.User;
import com.movieticketbooking.userservice.respositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       User user = userRepository.findByLoginId(username);
       if(user==null){
           throw new UsernameNotFoundException("Login ID invalid");
       }else{
           return org.springframework.security.core.userdetails.User.withUsername(user.getLoginId())
                                                                    .password("{noop}"+user.getPassword())
                                                                    .authorities("user").build();
       }
    }
}
