package com.example.lab1.controller.MainController;

import com.turtlerace.model.RaceConfig;
import com.turtlerace.model.RaceSession;
import com.turtlerace.model.Turtle;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("raceSession")
public class RaceController {

	@GetMapping("/")
	public String showConfigForm(Model model) {
		// Add a new RaceConfig object to the model for form binding
		model.addAttribute("raceConfig", new RaceConfig());

		// Add available turtles list
		List<Turtle> availableTurtles = new ArrayList<>();
		availableTurtles.add(new Turtle("Speedy", 9.0, 6.0, 5.0));
		availableTurtles.add(new Turtle("Tank", 5.0, 9.0, 6.0));
		availableTurtles.add(new Turtle("Lucky", 6.0, 5.0, 9.0));
		availableTurtles.add(new Turtle("Balanced", 7.0, 7.0, 6.0));
		model.addAttribute("turtles", availableTurtles);

		// Add track types
		List<String> trackTypes = new ArrayList<>();
		trackTypes.add("arena");
		trackTypes.add("cesped");
		trackTypes.add("asfalto");
		model.addAttribute("trackTypes", trackTypes);

		return "configForm";
	}

	@PostMapping("/start-race")
	public String startRace(@ModelAttribute("raceConfig") RaceConfig raceConfig, Model model) {
		// Create a new race session using the submitted configuration
		RaceSession raceSession = new RaceSession(raceConfig);

		// Simulate the race
		raceSession.simulateRace();

		// Add race session to model
		model.addAttribute("raceSession", raceSession);

		return "redirect:/results";
	}

	@GetMapping("/results")
	public String showResults(Model model) {
		if (!model.containsAttribute("raceSession")) {
			return "redirect:/";
		}

		return "results";
	}

	@GetMapping("/new-race")
	public String startNewRace(SessionStatus status) {
		// Clear the session
		status.setComplete();

		// Redirect to the configuration form
		return "redirect:/";
	}
}
