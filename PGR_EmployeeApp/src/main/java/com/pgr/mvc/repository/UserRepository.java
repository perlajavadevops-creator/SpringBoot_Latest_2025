package com.pgr.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pgr.mvc.dto.Login;
import com.pgr.mvc.entity.LoginEntity;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<LoginEntity, Integer> {
	 // 🔍 READ: Native SQL - Find user by username
    @Query(value = "SELECT * FROM users WHERE username = :name", nativeQuery = true)
    LoginEntity getLoginDetailsByName(@Param("name") String name);

    // 🔍 READ: JPQL - Find user by username
    @Query("FROM LoginEntity u WHERE u.username = :nameParam")
    LoginEntity getLoginDetailsByNameNonNativeQuery(@Param("nameParam") String nameParam);

    // 📋 READ: JPQL - Get all users with a specific email domain
    @Query("FROM LoginEntity u WHERE u.email LIKE %:domain")
    List<LoginEntity> getUsersByEmailDomain(@Param("domain") String domain);

    // ✏️ UPDATE: JPQL - Update password by username
    @Modifying
    @Transactional
    @Query("UPDATE LoginEntity u SET u.password = :newPassword WHERE u.username = :username")
    int updatePasswordByUsername(@Param("username") String username, @Param("newPassword") String newPassword);

    // 🗑️ DELETE: JPQL - Delete user by email
    @Modifying
    @Transactional
    @Query("DELETE FROM LoginEntity u WHERE u.email = :email")
    int deleteUserByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query("UPDATE LoginEntity u SET u.password = :password WHERE u.username = :username OR u.email = :username")
    int updatePasswordByUsernameOrEmail(String username, String password);

		
	}
	

