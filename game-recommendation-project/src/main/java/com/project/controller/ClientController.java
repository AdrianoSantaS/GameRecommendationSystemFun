package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.model.Client;
import com.project.service.ClientService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/clients")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@GetMapping()
	public String getClientDetails(Model model, HttpSession session) {
		Client loggedInClient = (Client) session.getAttribute("loggedInUser");
		model.addAttribute("client", loggedInClient);
		return "client";
	}

	@GetMapping("/delete")
	public String confirmDelete(@RequestParam("id") Long clientId) {
		clientService.deleteClient(clientId);
		return "redirect:/login";
	}

	@GetMapping("/listUsers")
	public String listAllClients(Model model) {
		List<Client> clients = clientService.listClients();
		model.addAttribute("clients", clients);
		return "clientsList";
	}

}
