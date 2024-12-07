package com.project.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.config.MultipartFileToByteArrayConverter;
import com.project.model.Client;
import com.project.model.GameStore;
import com.project.model.details.FreeToGameResponse;
import com.project.model.details.RawgResponse;
import com.project.service.ApiFreeToGamesService;
import com.project.service.ApiRawgService;
import com.project.service.ClientService;
import com.project.service.GameStoreService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/gameStore")
public class GameStoreController {

	@Autowired
	private GameStoreService gameStoreService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private ApiRawgService apiRawgService;
	
	@Autowired
	private ApiFreeToGamesService apiFreeToGamesService;

	@Autowired
	private MultipartFileToByteArrayConverter multipartFileToByteArrayConverter;

	@GetMapping
	public String listGames(Model model) {
		List<GameStore> listGames = gameStoreService.listGames();
		model.addAttribute("games", listGames);
		model.addAttribute("game", new GameStore());

		return "gameStore";
	}

	@GetMapping("/form")
	public String showGameStoreForm(Model model) {
		GameStore gameStore = new GameStore();
		model.addAttribute("gameStore", gameStore);
		model.addAttribute("clients", clientService.listClients());
		return "gameStore/form";
	}

	@PostMapping("/save")
	public String saveGames(@Validated GameStore gameStore, BindingResult result,
			@RequestParam("image") MultipartFile image, HttpSession session) {

		if (result.hasErrors()) {
			return "redirect:/gameStore";
		}

		Client loggedInClient = (Client) session.getAttribute("loggedInUser");

		if (loggedInClient != null) {
			loggedInClient = clientService.getClientById(loggedInClient.getId());

			gameStore.setClient(loggedInClient);

			Set<String> allowedMediaTypes = Set.of(MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE);
			if (image != null && !image.isEmpty() && allowedMediaTypes.contains(image.getContentType())) {
				try {
					byte[] imageBytes = multipartFileToByteArrayConverter.convert(image);
					gameStore.setImage(imageBytes);
				} catch (RuntimeException e) {
					return "redirect:/error";
				}
			}

			gameStoreService.insertGame(gameStore);

			return "redirect:/gameStore";
		} else {
			return "redirect:/error";
		}

	}

	@GetMapping("/delete")
	public String confirmDelete(@RequestParam("id") Long gameStoreId) {
		gameStoreService.deleteGame(gameStoreId);
		return "redirect:/gameStore";
	}


	@GetMapping("/gamesRawg")
	public String getGames(Model model) {
		RawgResponse rawgResponse = apiRawgService.getDataFromApi();
		model.addAttribute("games", rawgResponse.getResults()); 
		return "gamesRawg"; 
	}
	
	@GetMapping("/gamesFreeToGames")
	public String getGamesFreeToGames(Model model) {
	    FreeToGameResponse[] gamesArray = apiFreeToGamesService.getDataFromApi();
	    List<FreeToGameResponse> gamesList = Arrays.asList(gamesArray);
	    model.addAttribute("games", gamesList);
	    return "gamesFreeToGames";
	}

}
