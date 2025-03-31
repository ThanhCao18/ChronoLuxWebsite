package com.hau.service.impl;

import com.hau.constant.SystemConstant;
import com.hau.dto.MyUser;
import com.hau.entity.RoleEntity;
import com.hau.entity.UserEntity;
import com.hau.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findOneByUserNameAndStatus(username, SystemConstant.ACTIVE_STATUS);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");

        }
        else {
            List<GrantedAuthority> authorities = new ArrayList<>();
            for(RoleEntity role : userEntity.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(role.getCode()));
            }
            MyUser user = new MyUser(userEntity.getUserName(), userEntity.getPassword(), true, true, true, true, authorities);
            user.setFullName(userEntity.getFullName());
            user.setImgUrl(userEntity.getImgUrl());
            user.setUid(userEntity.getId());
            return user;
        }
    }
}

