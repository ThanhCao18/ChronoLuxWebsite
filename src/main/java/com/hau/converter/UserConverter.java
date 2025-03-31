package com.hau.converter;

import com.hau.dto.UserDTO;
import com.hau.entity.RoleEntity;
import com.hau.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {
    public UserDTO toDTO(UserEntity userEntity){
        if(userEntity != null){
            UserDTO userDTO = new UserDTO();
            userDTO.setId(userEntity.getId());
            userDTO.setUserName(userEntity.getUserName());
            userDTO.setPassword(userEntity.getPassword());
            userDTO.setFullName(userEntity.getFullName());
            userDTO.setStatus(userEntity.getStatus());
            userDTO.setEmail(userEntity.getEmail());
            userDTO.setResetPasswordToken(userEntity.getResetPasswordToken());
            userDTO.setImgUrl(userEntity.getImgUrl());
            userDTO.setCreatedDate(userEntity.getCreateDate());
            userDTO.setCreatedBy(userEntity.getCreateBy());

            if(userEntity.getVoucher() != null){
                userDTO.setVoucherId(userEntity.getVoucher().getId());
            }
            List<String> rolesCode = new ArrayList<>();
            for(RoleEntity role : userEntity.getRoles()){
                rolesCode.add(role.getCode());
            }

            userDTO.setRoleCode(rolesCode);
            return userDTO;
        }
       else return  null;

    }
    public UserEntity toEntity(UserDTO userDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userDTO.getUserName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setFullName(userDTO.getFullName());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setStatus(userDTO.getStatus());
        userEntity.setImgUrl(userDTO.getImgUrl());

        return userEntity;
    }
    public UserEntity toEntity(UserEntity result,UserDTO userDTO){
        result.setUserName(userDTO.getUserName());
        result.setFullName(userDTO.getFullName());
        result.setEmail(userDTO.getEmail());
        result.setStatus(userDTO.getStatus());
        result.setPassword(userDTO.getPassword());
        result.setImgUrl(userDTO.getImgUrl());

        return result;
    }
}
