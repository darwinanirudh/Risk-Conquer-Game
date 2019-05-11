package com.concordia.riskGame.model.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The following dice class implementing rolling a dice and returns of a list of
 * dice values.
 * 
 * @author Sande
 *
 */
public class Dice {

	private static final long serialversionUID = 1L;

	int numberOfDice;
	List<Integer> results = new ArrayList<>();
	public static final int max = 6;
	public static final int min = 1;

	/**
	 * The method returns the List of dice values.
	 * 
	 * @param numberOfDice The number of dice values to be returned.
	 * @return List of Integer Dice values.
	 */
	public List<Integer> rollDice(int numberOfDice) {
		this.numberOfDice = numberOfDice;
		results = new ArrayList<>();
		while (numberOfDice > 0) {
			Random random = new Random();
			int result = random.nextInt(max - min + 1) + min;
			results.add(result);
			numberOfDice--;
		}

		return results;
	}
}
