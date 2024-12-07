package com.project.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.model.GameFeedback;
import com.project.model.GameStore;
import com.project.repository.GameFeedbackRepository;
import com.project.repository.GameStoreRepository;

import jakarta.transaction.Transactional;
import net.coobird.thumbnailator.Thumbnails;

@Service
public class GameStoreService {

    @Autowired
    private GameStoreRepository gameStoreRepository;
    
    @Autowired
    private GameFeedbackRepository gameFeedbackRepository;

    @Transactional
    public GameStore insertGame(GameStore gameStore) {
        if (gameStore.getImage() != null && gameStore.getImage().length > 0) {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                Thumbnails.of(new ByteArrayInputStream(gameStore.getImage()))
                        .size(200, 150)
                        .toOutputStream(outputStream);
                byte[] imageBytes = outputStream.toByteArray();
                gameStore.setImage(imageBytes);
            } catch (IOException e) {
                throw new RuntimeException("Failed to process image", e);
            }
        }
        
        if (!(gameStore.getImage() instanceof byte[])) {
            throw new IllegalArgumentException("Image must be of type byte[]");
        }

        return gameStoreRepository.save(gameStore);
    }

    public GameStore updateGame(GameStore gameStore) {
        if (gameStoreRepository.existsById(gameStore.getId())) {
            return gameStoreRepository.save(gameStore);
        } else {
            throw new RuntimeException("Game not found for update");
        }
    }

    public List<GameStore> listGames() {
        return gameStoreRepository.findAll();
    }

    public void deleteGame(Long gameId) {
        if (gameStoreRepository.existsById(gameId)) {
            gameStoreRepository.deleteById(gameId);
        } else {
            throw new RuntimeException("Game not found for deletion");
        }
    }
   

    public GameStore getGameById(Long gameId) {
        Optional<GameStore> optionalGame = gameStoreRepository.findById(gameId);
        return optionalGame.orElseThrow(() -> new RuntimeException("Game not found for ID: " + gameId));
    }

    public byte[] getGameImage(Long gameId) {
        Optional<GameStore> optionalGame = gameStoreRepository.findById(gameId);
        if (optionalGame.isPresent()) {
            GameStore gameStore = optionalGame.get();
            return gameStore.getImage();
        } else {
            return null;
        }
    }
    
    public List<GameFeedback> getFeedbacksForGame(Long gameId) {
        return gameFeedbackRepository.findByGameId(gameId);
    }

}
