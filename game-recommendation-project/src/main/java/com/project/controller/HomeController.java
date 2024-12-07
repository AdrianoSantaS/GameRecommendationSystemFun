package com.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.model.Client;
import com.project.model.GameStore;
import com.project.service.GameFeedbackService;
import com.project.service.GameStoreService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private GameStoreService gameStoreService;

	@Autowired
	private GameFeedbackService gameFeedbackService;

	@GetMapping("/home")
	public String showHomePage(Model model, HttpSession session) {
		Client loggedInUser = (Client) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/login";
		}
		model.addAttribute("loggedInUser", loggedInUser);

		List<GameStore> games = gameStoreService.listGames();
		model.addAttribute("games", games);

		Map<Long, Boolean> hasCommented = new HashMap<>();
		for (GameStore game : games) {
			hasCommented.put(game.getId(), gameFeedbackService.hasUserCommented(game.getId(), loggedInUser.getId()));
		}
		model.addAttribute("hasCommented", hasCommented);

		return "home";
	}

	@GetMapping("/api/games")
	@ResponseBody
	public List<GameStore> getAllGames() {
		return gameStoreService.listGames();
	}

}
