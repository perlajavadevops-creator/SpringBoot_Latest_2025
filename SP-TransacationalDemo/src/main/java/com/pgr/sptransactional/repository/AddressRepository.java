package com.pgr.sptransactional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgr.sptransactional.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}
