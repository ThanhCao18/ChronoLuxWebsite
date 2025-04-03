package com.haui.service.impl;

import com.haui.constant.SystemConstant;
import com.haui.dto.MyUser;
import com.haui.entity.RoleEntity;
import com.haui.entity.UserEntity;
import com.haui.repository.UserRepository;
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

