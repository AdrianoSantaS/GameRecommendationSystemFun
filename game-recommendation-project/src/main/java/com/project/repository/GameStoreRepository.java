package com.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.GameStore;

@Repository
public interface GameStoreRepository extends JpaRepository<GameStore, Long> {
	
}
