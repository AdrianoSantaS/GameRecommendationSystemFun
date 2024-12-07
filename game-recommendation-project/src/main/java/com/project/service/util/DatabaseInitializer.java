package com.project.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Component
public class DatabaseInitializer implements CommandLineRunner {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		// Check if the user already exists
		Long count = (Long) entityManager.createQuery("SELECT COUNT(c) FROM Client c WHERE c.login = 'admin@email.com'")
				.getSingleResult();
		if (count == 0) {
			// Insert the user
			entityManager.createNativeQuery(
					"INSERT INTO client (name, phone, address, login, password, type_permission) VALUES (?, ?, ?, ?, ?, ?)")
					.setParameter(1, "Master").setParameter(2, "123-456-7890")
					.setParameter(3, "123 Admin St, Admin City").setParameter(4, "admin@email.com")
					.setParameter(5, "admin123").setParameter(6, "ADMIN").executeUpdate();
			System.out.println("Admin user inserted into the database.");
		}
	}
}