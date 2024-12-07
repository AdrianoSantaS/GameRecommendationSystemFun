package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.GameFeedback;



@Repository
public interface GameFeedbackRepository extends JpaRepository<GameFeedback, Long> {

	List<GameFeedback> findByGameId(Long gameId);
	
	boolean existsByGameIdAndClientId(Long gameId, Long clientId);

}
