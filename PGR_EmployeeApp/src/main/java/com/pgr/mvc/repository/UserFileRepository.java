package com.pgr.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pgr.mvc.entity.UserFile;
@Repository
public interface UserFileRepository extends JpaRepository<UserFile, Long> {

	UserFileRepository save(UserFileRepository file);
	
	@Query("from UserFile where fileName =:fileName")
	public UserFile downloadFileByName(String fileName);

	@Modifying
	@Query("DELETE FROM UserFile uf WHERE uf.fileName = :fileName")
	int deleteFileByName(@Param("fileName") String fileName);

	
}
