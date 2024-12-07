package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.model.GameFeedback;
import com.project.repository.ClientRepository;
import com.project.repository.GameFeedbackRepository;
import com.project.repository.GameStoreRepository;

@Service
public class GameFeedbackService {

	@Autowired
	private GameFeedbackRepository gameFeedbackRepository;

	@Autowired
	private GameStoreRepository gameStoreRepository;

	@Autowired
	private ClientRepository clientRepository;

	public boolean hasUserCommented(Long gameId, Long userId) {
		return gameFeedbackRepository.existsByGameIdAndClientId(gameId, userId);
	}

	public GameFeedback insertGameFeedback(GameFeedback feedback) {
		if (feedback.getGame() != null && feedback.getGame().getId() != null) {
			feedback.setGame(gameStoreRepository.findById(feedback.getGame().getId()).orElse(null));
		}
		if (feedback.getClient() != null && feedback.getClient().getId() != null) {
			feedback.setClient(clientRepository.findById(feedback.getClient().getId()).orElse(null));
		}
		return gameFeedbackRepository.save(feedback);
	}

	public List<GameFeedback> getFeedbacksForGame(Long gameId) {
		return gameFeedbackRepository.findByGameId(gameId);
	}
}
