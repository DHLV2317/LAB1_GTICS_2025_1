package com.example.lab1.Entity.turtle.model;

public class Turtle {
	private String name;
	private double speed;
	private double resistance;
	private double luck;
	private double finalPosition;

	public Turtle(String name, double speed, double resistance, double luck) {
		this.name = name;
		this.speed = speed;
		this.resistance = resistance;
		this.luck = luck;
	}

	// Getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getResistance() {
		return resistance;
	}

	public void setResistance(double resistance) {
		this.resistance = resistance;
	}

	public double getLuck() {
		return luck;
	}

	public void setLuck(double luck) {
		this.luck = luck;
	}

	public double getFinalPosition() {
		return finalPosition;
	}

	public void setFinalPosition(double finalPosition) {
		this.finalPosition = finalPosition;
	}
}

// RaceConfig.java
package com.turtlerace.model;

public class RaceConfig {
	private String selectedTurtle;
	private double betAmount;
	private String trackType; // arena, cesped, asfalto

	// Default constructor for form binding
	public RaceConfig() {
	}

	// Getters and setters
	public String getSelectedTurtle() {
		return selectedTurtle;
	}

	public void setSelectedTurtle(String selectedTurtle) {
		this.selectedTurtle = selectedTurtle;
	}

	public double getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(double betAmount) {
		this.betAmount = betAmount;
	}

	public String getTrackType() {
		return trackType;
	}

	public void setTrackType(String trackType) {
		this.trackType = trackType;
	}
}

// RaceSession.java
package com.turtlerace.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class RaceSession {
	private List<Turtle> turtles;
	private RaceConfig config;
	private List<Turtle> results;
	private boolean userWon;
	private double winnings;

	public RaceSession(RaceConfig config) {
		this.config = config;
		this.turtles = initializeTurtles();
		this.results = new ArrayList<>();
	}

	private List<Turtle> initializeTurtles() {
		List<Turtle> turtleList = new ArrayList<>();
		// Create different turtles with unique attributes
		turtleList.add(new Turtle("Speedy", 9.0, 6.0, 5.0));
		turtleList.add(new Turtle("Tank", 5.0, 9.0, 6.0));
		turtleList.add(new Turtle("Lucky", 6.0, 5.0, 9.0));
		turtleList.add(new Turtle("Balanced", 7.0, 7.0, 6.0));
		return turtleList;
	}

	public void simulateRace() {
		Random random = new Random();

		for (Turtle turtle : turtles) {
			// Apply track modifiers based on track type
			double speedModifier = 1.0;
			double resistanceModifier = 1.0;
			double luckModifier = 1.0;

			switch (config.getTrackType()) {
				case "arena":
					speedModifier = 0.8;
					luckModifier = 1.2;
					break;
				case "cesped":
					resistanceModifier = 1.2;
					break;
				case "asfalto":
					speedModifier = 1.2;
					break;
			}

			// Calculate performance using the formula: P = (V×1.5) + (R×1.2) + (random×S)
			double randomFactor = random.nextDouble();
			double performance = (turtle.getSpeed() * 1.5 * speedModifier) +
					(turtle.getResistance() * 1.2 * resistanceModifier) +
					(randomFactor * turtle.getLuck() * luckModifier);

			turtle.setFinalPosition(performance);
		}

		// Sort turtles by final position (higher value = better position)
		results = new ArrayList<>(turtles);
		Collections.sort(results, (t1, t2) ->
				Double.compare(t2.getFinalPosition(), t1.getFinalPosition()));

		// Check if user won
		Turtle winningTurtle = results.get(0);
		userWon = winningTurtle.getName().equals(config.getSelectedTurtle());

		if (userWon) {
			winnings = config.getBetAmount() * 2;
		} else {
			winnings = 0;
		}
	}

	// Getters and setters
	public List<Turtle> getTurtles() {
		return turtles;
	}

	public List<Turtle> getResults() {
		return results;
	}

	public boolean isUserWon() {
		return userWon;
	}

	public double getWinnings() {
		return winnings;
	}

	public RaceConfig getConfig() {
		return config;
	}
}
