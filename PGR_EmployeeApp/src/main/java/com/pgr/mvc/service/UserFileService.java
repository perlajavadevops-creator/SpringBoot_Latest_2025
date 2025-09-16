package com.pgr.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pgr.mvc.entity.UserFile;
import com.pgr.mvc.repository.UserFileRepository;

@Service
public class UserFileService {

	@Autowired
	UserFileRepository userFileRepository;

	public String uploadFile(String username, MultipartFile file) {
		try {
			UserFile userFile = new UserFile(username, file.getOriginalFilename(), file.getBytes());
			userFileRepository.save(userFile);
			return "File uploaded successfully for user: " + username;
		} catch (Exception e) {
			throw new RuntimeException("File upload failed: " + e.getMessage(), e);
		}
	}

	public UserFile getFile(Long id) {
		return userFileRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found with id " + id));
	}

	public UserFileRepository saveFile(UserFileRepository file) {
		return userFileRepository.save(file);
	}

	public byte[] downloadFile(Long id) {
		return userFileRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found with id: " + id))
				.getData();
	}

	public List<UserFile> getAllFiles() {
		return userFileRepository.findAll();
	}

	public UserFile downloadFileByName(String fileName) {
		return userFileRepository.downloadFileByName(fileName);
	}

	@Transactional
	public boolean deleteFileByName(String fileName) {
		boolean flag = false;
		if (userFileRepository.deleteFileByName(fileName) == 1) {
			flag = true;
		}

		return flag;

	}

}
