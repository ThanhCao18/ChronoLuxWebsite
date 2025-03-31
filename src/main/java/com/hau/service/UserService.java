package com.hau.service;

import com.hau.dto.UserDTO;
import com.hau.exception.CustomerNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;


public interface UserService {
    void updateSecurityContext(UserDTO userDTO, Authentication authentication);
    public UserDTO findOneByUserNameAndStatus(String userName, int status);
    UserDTO findOneById(Long id);
    public UserDTO save(UserDTO userDTO, String typeAccount);
    void update(UserDTO userDTO);
    public void updateResetPasswordToken(String token,String email) throws CustomerNotFoundException;
    public UserDTO findOneByResetPasswordToken(String resetPasswordToken);
    public void updatePassword(UserDTO user, String password );
    public UserDTO getCurrentLoggedInCustomer(Authentication authentication);
    public UserDTO findOneByEmailAndPassWordNotNull(String email);
    public UserDTO findOneByEmailAndRoleCode(String email , String roleCode);
    Page<UserDTO> findAllAdminAccounts(int page, int limit);
    Page<UserDTO> findAllUserAccounts(int page, int limit);
    boolean usernameExists(String username);
    boolean emailExits(String email);
}
