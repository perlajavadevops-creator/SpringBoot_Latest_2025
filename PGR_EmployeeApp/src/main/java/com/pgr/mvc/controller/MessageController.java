package com.pgr.mvc.controller;

import com.pgr.mvc.dto.MessageDTO;
import com.pgr.mvc.entity.MessageEntity;
import com.pgr.mvc.service.MessageService;

import jakarta.servlet.http.HttpSession;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/messages")
public class MessageController {
	
	@Autowired
	  MessageService messageServic;

	@GetMapping("/compose") public String showComposeForm(Model model) {
	  model.addAttribute("message", new MessageDTO()); 
	  return "compose"; //	  your thymeleaf file
	  }

	@PostMapping("/send")
	public String sendMessage(@ModelAttribute("message") MessageDTO messageDto, Model model, HttpSession session) {
		// For demo, assume sender is "admin" (replace with logged-in user later)
		String sender = (String) session.getAttribute("username");

		MessageEntity sentMessage = messageServic.sendMessage(sender, messageDto);

		model.addAttribute("messageSent", true);
	    model.addAttribute("successMessage", "Message sent successfully!"); // message

		model.addAttribute("sentMessage", sentMessage);

		return "compose"; // stay on same page with success msg
	}

	
	
	 
	 // Inbox
	@GetMapping("/inbox")
	public String inbox(Model model) {
	    List<MessageEntity> messages = messageServic.getAllMessages();
	    model.addAttribute("messages", messages);
	    return "inbox"; // inbox.html
	}
	@GetMapping("/delete/{id}")
	public String deleteMessage(@PathVariable Long id, RedirectAttributes redirectAttributes) {
	    messageServic.deleteMessage(id);
	    redirectAttributes.addFlashAttribute("message", "Message deleted with ID: " + id);
	    return "redirect:/messages/inbox";
	}


	
}
