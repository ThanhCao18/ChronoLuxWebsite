package com.hau.repository;

import com.hau.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findOneByUserNameAndStatus(String username,Integer status);

    UserEntity findOneByResetPasswordToken(String token);
    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE u.email = :email AND r.code = :roleCode")
    UserEntity findByEmailAndRoleCode(@Param("email") String email, @Param("roleCode") String roleCode);


    @Query("SELECT u FROM UserEntity u WHERE u.email = :email AND u.password IS NOT NULL ")
    UserEntity findOneByEmailAndPasswordNotNull(@Param("email") String email);

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    List<UserEntity> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.code = 'ROLE_ADMIN' AND u.status = 1")
    Page<UserEntity> findAllNotBannedUserAccounts(Pageable pageable);

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.code = 'ROLE_ADMIN' AND u.status = 0")
    Page<UserEntity> findAllBannedUserAccounts(Pageable pageable);

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.code = 'ROLE_ADMIN'")
    Page<UserEntity> findAllAdminAccounts(Pageable pageable);

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.code = 'ROLE_ADMIN'")
    Set<UserEntity> findAllAdminAccounts();

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.code = 'ROLE_USER' OR r.code = 'ROLE_USER_FACEBOOK' OR r.code = 'ROLE_USER_GOOGLE' ")
    Page<UserEntity> findAllUserAccounts(Pageable pageable);
}
