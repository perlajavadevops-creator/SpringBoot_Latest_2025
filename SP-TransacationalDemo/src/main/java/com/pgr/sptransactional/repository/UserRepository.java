package com.pgr.sptransactional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgr.sptransactional.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
