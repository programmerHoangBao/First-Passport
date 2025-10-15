package com.group5.firstpassport.service.impl;

import com.group5.firstpassport.entity.UserEntity;
import com.group5.firstpassport.repository.UserRepository;
import com.group5.firstpassport.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
public class UserServiceImpl implements IUserService, UserDetailsService {

  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

    return new User(userEntity.getUsername(),
            userEntity.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().name())));
  }
}
