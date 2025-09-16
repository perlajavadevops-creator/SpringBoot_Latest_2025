package com.pgr.mvc.controller;

import java.awt.PageAttributes.MediaType;
import java.net.http.HttpHeaders;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pgr.mvc.entity.LoginEntity;
import com.pgr.mvc.entity.UserFile;
import com.pgr.mvc.service.UserFileService;

import jakarta.annotation.Resource;

@Controller
public class UserFileController {

	@Autowired
	UserFileService userFileService;

	@GetMapping("/userfile")
	public String showUploadForm() {
		return "userfile"; // Thymeleaf template
	}
	
	
	@GetMapping("/viewfile")
	public String showDownloadForm(Model model) {
		List<UserFile> files = userFileService.getAllFiles();
		model.addAttribute("files", files);

		if (files.isEmpty()) {
			model.addAttribute("message", "No files available");
		}

		return "viewfile"; // Thymeleaf template
	}

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("username") String username, @RequestParam("file") MultipartFile file,
			Model model) {
		String message = userFileService.uploadFile(username, file);
		model.addAttribute("message", message);
		return "userfile";
	}

	
	
	@GetMapping("/dashboard")
	public ModelAndView login(RedirectAttributes redirectAttributes) {
		ModelAndView view = new ModelAndView("dashboard");

		return view;
	}
	
	/*
	 * @GetMapping("/files") public List<UserFile> getAllFiles() { return
	 * userFileService.getAllFiles(); }
	 */

	@GetMapping("/files")
	public String listFiles(Model model) {
		List<UserFile> files = userFileService.getAllFiles();
		model.addAttribute("files", files);

		if (files.isEmpty()) {
			model.addAttribute("message", "No files available");
		}

		return "userfile"; // Thymeleaf template: userfile.html
	}
	 

    // Download a file
    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        UserFile file = userFileService.downloadFileByName(fileName);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; fileName=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getData()));
    }

    // Upload page redirect
    @GetMapping("/upload")
    public String showUploadPage() {
        return "userfile"; // create a Thymeleaf upload.html page
    }
    
    @PostMapping("/delete/{fileName}")
    public String deleteFileByName(@PathVariable String fileName, RedirectAttributes redirectAttributes) {
        boolean deleteFlag = userFileService.deleteFileByName(fileName);
        if(deleteFlag) {
        	redirectAttributes.addFlashAttribute("message", "File Deleted Successfully and the file is "+fileName);
        }else{
        	redirectAttributes.addFlashAttribute("message", "File is Not Deleted and the file is "+fileName);
        }
        return "redirect:/viewfile"; // reload list after deletion
    }
    
}
