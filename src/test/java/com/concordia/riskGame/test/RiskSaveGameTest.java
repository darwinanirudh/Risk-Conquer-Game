package com.concordia.riskGame.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import com.concordia.riskGame.model.Country.Country;
import com.concordia.riskGame.model.Map.MapContents;
import com.concordia.riskGame.model.Player.BenevolentPlayer;
import com.concordia.riskGame.model.Player.Player;
import com.concordia.riskGame.model.save.RiskSaveGame;

public class RiskSaveGameTest {
	
	private MapContents mapContents;
	private RiskSaveGame riskSaveGame;
	private String expectedFileName;
	private String expectedFilePath;
	private Player p1, p2;
	private Country c1,c2,c3,c4;
	private List<Country> listCountry, listCountry1;
	private List<Player> listPlayer;
	private HashMap<Country, List<Country>> countryAndNBCountry;
	
    @Rule
    public final TextFromStandardInputStream systemInMock
      = TextFromStandardInputStream.emptyStandardInputStream();
	
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
		countryAndNBCountry = new HashMap<>();
		listPlayer = new ArrayList<>();
		
		p1.setTotalArmies(40);
		p2.setTotalArmies(40);
		c1.setArmies(20);
		c2.setArmies(10);
		c3.setArmies(20);
		c4.setArmies(20);
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
		listCountry.add(c3);
		c4.setNeighbouringCountries(listCountry);
		countryAndNBCountry.put(c4, listCountry);
		mapContents.setCountryAndNeighbors(countryAndNBCountry);
		
		listCountry = new ArrayList<>();
		
		listCountry.add(c1);
		listCountry.add(c2);
		c4.setNeighbouringCountries(listCountry);
		mapContents.getCountryAndNeighbors().put(c4, listCountry);
		
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
	 */
	@Test
	public void testFileCreation() {
		try {
		boolean flag = false;
		DateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		Date date = new Date();
		expectedFileName = sdf.format(date);
		expectedFileName = expectedFileName.replaceAll(":", "");
		
		riskSaveGame = new RiskSaveGame();
		riskSaveGame.saveGame(mapContents);
		
		File folder = new File("C:\\SaveGame\\");
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
			    System.out.println("File " + listOfFiles[i].getName());
			}
			if(listOfFiles[i].getName().equalsIgnoreCase(expectedFileName)) {
				flag = true;
				break;
			}
		}
	    assertTrue(flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method to check if saved game file has been created or not 
	 * @throws IOException if there is an error
	 */
	@Test
	public void testCheckFileData() throws IOException {
		boolean flag = false;
		ObjectInputStream restore = null;
		try {
			
			BenevolentPlayer bp = new BenevolentPlayer();
			
			p1.setStrategy(new BenevolentPlayer());
			//p2.setStrategy(new CheaterPlayer());
			Player p11 = bp.reinforcePhase(p1);
			//Player p22 = cp.reinforcePhase(p2);
			listPlayer.add(p11);
			//listPlayer.add(p22);
			mapContents.setPlayerList(listPlayer);
			
			riskSaveGame = new RiskSaveGame();
			expectedFilePath = riskSaveGame.saveGame(mapContents);
						
			FileInputStream savedFile = new FileInputStream(expectedFilePath);
			restore = new ObjectInputStream(savedFile);
			Object obj = restore.readObject();
			System.out.println("####### Saved file object is ####### : " + obj.toString());

			MapContents mapContentObject = (MapContents) obj;
			List<Player> listPlayer1 = mapContentObject.getPlayerList();
			
			System.out.println("1 : " + listPlayer1.get(0).getAssignedCountries().get(0).getArmies());
			System.out.println("2 : " + listPlayer1.get(0).getAssignedCountries().get(1).getArmies());
			//System.out.println("3 : " + listPlayer1.get(1).getAssignedCountries().get(0).getArmies());
			//System.out.println("4 : " + listPlayer1.get(1).getAssignedCountries().get(1).getArmies());
			
			if(listPlayer1.get(0).getAssignedCountries().get(0).getArmies() == 20 && listPlayer1.get(0).getAssignedCountries().get(1).getArmies() == 13) {
				flag = true;
			}
			
			assertTrue(flag);
			restore.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			restore.close();
		}

	}

}
