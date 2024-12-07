package com.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.model.Client;
import com.project.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	public Client insertClient(Client client) {
		return clientRepository.save(client);
	}

	public Client updateClient(Client client) {
		if (clientRepository.existsById(client.getId())) {
			return clientRepository.save(client);
		} else {
			throw new RuntimeException("Client not found for update");
		}
	}

	public List<Client> listClients() {
		return clientRepository.findAll();
	}

	public void deleteClient(Long clientId) {
		Client client = clientRepository.findById(clientId).orElse(null);

		if (client != null) {
			clientRepository.delete(client);
		}
	}

	public Optional<Client> getClientByLogin(String login) {
		return clientRepository.findByLogin(login);
	}

	public boolean isValidClient(String login, String password) {
		Optional<Client> optionalClient = getClientByLogin(login);
		return optionalClient.isPresent() && optionalClient.get().getPassword().equals(password);
	}

	public boolean isLoginTaken(String login) {
		return clientRepository.findByLogin(login).isPresent();
	}

	private Client loggedInClient;

	public boolean login(String username, String password) {
		Optional<Client> clientOptional = getClientByLogin(username);

		if (clientOptional.isPresent() && isValidClient(username, password)) {
			loggedInClient = clientOptional.get();
			return true;
		} else {
			return false;
		}
	}

	public Client getLoggedInClient() {
		return loggedInClient;
	}

	public Client getClientById(Long clientId) {
		Optional<Client> optionalClient = clientRepository.findById(clientId);
		return optionalClient.orElseThrow(() -> new RuntimeException("Client not found for ID: " + clientId));
	}

	public Optional<Client> getClientByUsername(String username) {
		return clientRepository.findByName(username);
	}
}
