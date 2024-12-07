package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.model.Client;
import com.project.model.GameFeedback;
import com.project.model.GameStore;
import com.project.service.GameFeedbackService;
import com.project.service.GameStoreService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	private GameFeedbackService gameFeedbackService;

	@Autowired
	private GameStoreService gameStoreService;

	@PostMapping("/add")
	public String addComment(@RequestParam Long gameId, @RequestParam String commentText,
	                         @RequestParam String feedbackType, HttpSession session, 
	                         RedirectAttributes redirectAttributes) {
	    Client loggedInUser = (Client) session.getAttribute("loggedInUser");

	    if (loggedInUser != null) {
	        if (commentText == null || commentText.trim().isEmpty()) {
	            redirectAttributes.addFlashAttribute("errorMessage", "Comment cannot be empty.");
	            return "redirect:/comments/new?gameId=" + gameId;
	        }

	        if (gameFeedbackService.hasUserCommented(gameId, loggedInUser.getId())) {
	            redirectAttributes.addFlashAttribute("errorMessage",
	                    "You have already commented on this game and cannot add another.");
	            return "redirect:/home";
	        }

	        Integer likeCount = feedbackType.equals("like") ? 1 : 0;
	        Integer notlikeCount = feedbackType.equals("dislike") ? 1 : 0;

	        GameStore game = gameStoreService.getGameById(gameId);
	        GameFeedback feedback = new GameFeedback(null, game, loggedInUser, commentText, likeCount, notlikeCount);

	        gameFeedbackService.insertGameFeedback(feedback);
	    }

	    return "redirect:/home";
	}


	@GetMapping("/new")
	public String showAddCommentPage(@RequestParam Long gameId, Model model) {
		GameStore game = gameStoreService.getGameById(gameId);
		model.addAttribute("game", game);
		return "new_comment";
	}
	
	
	@GetMapping("/{gameId}")
	public String showComments(@PathVariable Long gameId, Model model) {
	    List<GameFeedback> feedbacks = gameFeedbackService.getFeedbacksForGame(gameId);
	    
	    int likesCount = (int) feedbacks.stream().filter(f -> f.getLikeCount() > 0).count();
	    int dislikesCount = (int) feedbacks.stream().filter(f -> f.getNotlikeCount() > 0).count();
	    int commentsCount = feedbacks.size(); 

	    model.addAttribute("game", gameStoreService.getGameById(gameId));
	    model.addAttribute("feedbacks", feedbacks);
	    model.addAttribute("likesCount", likesCount);
	    model.addAttribute("dislikesCount", dislikesCount);
	    model.addAttribute("commentsCount", commentsCount);

	    return "comments"; 
	}



}
