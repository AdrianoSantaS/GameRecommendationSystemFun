package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.model.GameStore;
import com.project.service.GameStoreService;

@Controller
public class ImageController {

	@Autowired
	private GameStoreService gameStoreService;

	@GetMapping("/images/{gameId}")
	@ResponseBody
	public ResponseEntity<byte[]> getImage(@PathVariable Long gameId) {
	    GameStore game = gameStoreService.getGameById(gameId);
	    if (game != null && game.getImage() != null) {
	        return ResponseEntity.ok()
	                .contentType(MediaType.IMAGE_JPEG) 
	                .body(game.getImage());
	    }
	    return ResponseEntity.notFound().build();
	}
}
