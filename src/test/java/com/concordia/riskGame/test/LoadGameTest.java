package com.concordia.riskGame.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.concordia.riskGame.View.LoadGame;
import com.concordia.riskGame.model.Country.Country;
import com.concordia.riskGame.model.Map.MapContents;
import com.concordia.riskGame.model.Player.BenevolentPlayer;
import com.concordia.riskGame.model.Player.Player;
import com.concordia.riskGame.model.save.RiskSaveGame;

/**
 * @author saich
 * It is used for testing loaded game
 *
 */
public class LoadGameTest {
	
	private MapContents mapContents;
	private RiskSaveGame riskSaveGame;
	private String expectedFilePath;
	private Player p1, p2;
	private Country c1,c2,c3,c4;
	private List<Country> listCountry, listCountry1;
	private List<Player> listPlayer;
	private HashMap<Country, List<Country>> countryAndNBCountry;
	
	/**
	 * before method for initializing objects
	 */
	@Before
	public void before () {
		
		p1 = new Player("p1");
		p2 = new Player("p2");
		c1 = new Country("c1");
		c2 = new Country("c2");
		c3 = new Country("c3");
		c4 = new Country("c4");
		listCountry = new ArrayList<>();
		listCountry1 = new ArrayList<>();
		mapContents = MapContents.getInstance();
		listPlayer = new ArrayList<>();
		countryAndNBCountry = new HashMap<>();
		
		p1.setTotalArmies(30);
		p2.setTotalArmies(30);
		c1.setArmies(20);
		c2.setArmies(10);
		c3.setArmies(20);
		c4.setArmies(10);
		c1.setBelongsToPlayer(p1);
		c2.setBelongsToPlayer(p1);
		c3.setBelongsToPlayer(p2);
		c4.setBelongsToPlayer(p2);
		
		listCountry.add(c2);
		listCountry.add(c3);
		c1.setNeighbouringCountries(listCountry);
		countryAndNBCountry.put(c1, listCountry);
		
		listCountry = new ArrayList<>();
		
		listCountry.add(c3);
		listCountry.add(c4);
		c2.setNeighbouringCountries(listCountry);
		countryAndNBCountry.put(c2, listCountry);
		
		listCountry = new ArrayList<>();
		
		listCountry.add(c4);
		listCountry.add(c1);
		c3.setNeighbouringCountries(listCountry);
		countryAndNBCountry.put(c3, listCountry);
		
		listCountry = new ArrayList<>();
		
		listCountry.add(c1);
		listCountry.add(c2);
		c4.setNeighbouringCountries(listCountry);
		countryAndNBCountry.put(c4, listCountry);
		mapContents.setCountryAndNeighbors(countryAndNBCountry);
		
		listCountry = new ArrayList<>();
		
		listCountry.add(c1);
		listCountry.add(c2);
		p1.setAssignedCountries(listCountry);
						
		listCountry1.add(c3);
		listCountry1.add(c4);
		p2.setAssignedCountries(listCountry1);
		
	}
	
	/**
	 * Test method to check if saved game file has been created or not 
	 * @throws IOException if there is an error
	 */
	@Test
	public void testReadSavedMapContent() throws IOException {
		boolean flag = false;
		try {
			
			BenevolentPlayer bp = new BenevolentPlayer();
			
			p1.setStrategy(new BenevolentPlayer());
			p2.setStrategy(new BenevolentPlayer());
			Player p11 = bp.reinforcePhase(p1);
			Player p22 = bp.reinforcePhase(p2);
			System.out.println("1 : " + p11.getAssignedCountries().get(0).getArmies());
			System.out.println("1 : " + p11.getAssignedCountries().get(1).getArmies());
			System.out.println("1 : " + p22.getAssignedCountries().get(0).getArmies());
			System.out.println("1 : " + p22.getAssignedCountries().get(1).getArmies());
			listPlayer.add(p11);
			listPlayer.add(p22);
			mapContents.setPlayerList(listPlayer);
			
			riskSaveGame = new RiskSaveGame();
			expectedFilePath = riskSaveGame.saveGame(mapContents);
			
			LoadGame lg = new LoadGame();
			mapContents = lg.readSavedMapContent(expectedFilePath);
			List<Player> listPlayer1 = mapContents.getPlayerList();
			
			System.out.println("1 : " + listPlayer1.get(0).getAssignedCountries().get(0).getArmies());
			System.out.println("2 : " + listPlayer1.get(0).getAssignedCountries().get(1).getArmies());
			System.out.println("3 : " + listPlayer1.get(1).getAssignedCountries().get(0).getArmies());
			System.out.println("4 : " + listPlayer1.get(1).getAssignedCountries().get(1).getArmies());
			
			if(listPlayer1.get(0).getAssignedCountries().get(0).getArmies() == 20 && listPlayer1.get(0).getAssignedCountries().get(1).getArmies() == 13
					&& listPlayer1.get(1).getAssignedCountries().get(0).getArmies() == 20 && listPlayer1.get(1).getAssignedCountries().get(1).getArmies() == 13) {
				flag = true;
			}
			
			assertTrue(flag);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

}
