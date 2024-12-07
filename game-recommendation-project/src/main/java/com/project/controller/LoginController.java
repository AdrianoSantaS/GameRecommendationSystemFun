package com.project.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.model.Client;
import com.project.service.ClientService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private ClientService clientService;

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, Model model,
			HttpSession session) {
		Optional<Client> clientOptional = clientService.getClientByLogin(username);

		if (clientOptional.isPresent() && clientService.isValidClient(username, password)) {
			Client loggedInClient = clientOptional.get();
			session.setAttribute("loggedInUser", loggedInClient);
			model.addAttribute("loggedInUser", loggedInClient);
			return "redirect:/home";
		} else {
			model.addAttribute("error", "Invalid username or password");
			model.addAttribute("username", username);
			return "login";
		}
	}

	@PostMapping("/register")
	public String register(@ModelAttribute Client newClient, Model model) {

		if (clientService.isLoginTaken(newClient.getLogin())) {
			model.addAttribute("error", "Username is already taken. Please choose a different one.");
			return "login :: #registerModal";
		}

		clientService.insertClient(newClient);
		model.addAttribute("successMessage", "Registration successful! Please login with your new credentials.");

		return "login";
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

}
