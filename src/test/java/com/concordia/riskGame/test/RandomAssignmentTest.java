package com.concordia.riskGame.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import org.junit.Before;
import org.junit.Test;

import com.concordia.riskGame.model.Country.Country;
import com.concordia.riskGame.model.Player.Player;
import com.concordia.riskGame.util.RandomAssignment;

/**
 * Test to check if reading the map is functioning properly
 * 
 */
public class RandomAssignmentTest {

	private RandomAssignment randomAssignment;
	private Player player;
	private List<Country> listOfCountry;
	private int playerCount;

	/**
	 * Before method to set initialize objects
	 * 
	 * @throws Exception Exception is thrown.
	 */
	@Before
	public void before() throws Exception {
		randomAssignment = new RandomAssignment();
		playerCount = 3;
		listOfCountry = new ArrayList<>();
		listOfCountry.add(new Country("c1"));
		listOfCountry.add(new Country("c2"));
		listOfCountry.add(new Country("c3"));
		listOfCountry.add(new Country("c4"));
		listOfCountry.add(new Country("c5"));
		listOfCountry.add(new Country("c6"));
		listOfCountry.add(new Country("c7"));
		listOfCountry.add(new Country("c8"));
		listOfCountry.add(new Country("c9"));
		listOfCountry.add(new Country("c10"));
	}

	/**
	 * Test method for randonAssignmentMethod method
	 */
	@Test
	public void testRandonAssignmentMethod() {
		HashMap<String, String> playerType = new HashMap<>();
		playerType.put("Player1", "Cheater");
		playerType.put("Player2", "Cheater");
		playerType.put("Player3", "Cheater");
		player = randomAssignment.randonAssignmentMethod(playerCount, listOfCountry, playerType);
		Map<Player, List<Country>> playerAssign = player.getPlayerAssign();

		for (Player player : playerAssign.keySet()) {
			for (Country country : player.getAssignedCountries()) {
				listOfCountry.remove(country);
			}
		}

		assertEquals(0, listOfCountry.size());
	}
}
