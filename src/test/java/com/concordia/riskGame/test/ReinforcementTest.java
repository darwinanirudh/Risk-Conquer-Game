package com.concordia.riskGame.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.concordia.riskGame.control.GameDriver;
import com.concordia.riskGame.model.Continent.Continent;
import com.concordia.riskGame.model.Country.Country;
import com.concordia.riskGame.model.Map.MapContents;
import com.concordia.riskGame.model.Player.Player;


/**
 * This class implements the test cases for testing the computation of
 * reinforcement armies
 * @author Dheeraj As
 *
 */
public class ReinforcementTest {
	int noOfCountries1;
	int noOfCountries2;
	int noOfCountries3;
	int result;
	Player playerTest;
	Country c1;
	List<Country> countryList;
	Continent con;
	MapContents mapConObejct;
	HashMap<Continent, List<Country>> continentAndItsCountries;
	List<Player> playerList;
	
	/**
	 * Initializing the Context
	 */
	@Before
	public void setUp () {
		noOfCountries1= 10;
		noOfCountries2= 2;
		noOfCountries3= 21;
		result=0;
		playerTest = new Player("playerTest");
		countryList = new ArrayList();
		c1 = new Country("India");
		c1.setBelongsToPlayer(playerTest);
		countryList.add(c1);
		playerTest.setAssignedCountries(countryList);
		con = new Continent("Con",2);
		con.setCountries(countryList);
		mapConObejct=MapContents.getInstance();
		continentAndItsCountries = new  HashMap<>();
		continentAndItsCountries.put(con, countryList);
		mapConObejct.setContinentAndItsCountries(continentAndItsCountries);
		playerList = new ArrayList();
		playerList.add(playerTest);
		mapConObejct.setPlayerList(playerList);
	}
	
	/**
	 * This method test if the calculation of armies is correct when number of 
	 * countries is greater than 3
	 */
	@Test
	public void reinforcementArmyCalculationTest1() {
		
		GameDriver gameDriverObject= new GameDriver();
		result= gameDriverObject.calculateReiforcementArmies(noOfCountries1);
		assertEquals(3,result);
		
		result= gameDriverObject.calculateReiforcementArmies(noOfCountries3);
		assertEquals(7,result);
		
	}
	
	/**
	 * This method tests if the This method test if the calculation of armies is correct when number of 
	 * countries is less than 3
	 */
	@Test
	public void reinforcementArmyCalculationTest2() {
		GameDriver gameDriverObject= new GameDriver();
		
		result= gameDriverObject.calculateReiforcementArmies(noOfCountries2);
		assertEquals(3,result);
	}
	
	/**
	 * The following method tests the countries owned by the player.
	 */
	@Test
	public void testPrintCountriesOwnedByPlayer() {
	
	String name = playerTest.getAssignedCountries().get(0).getCountryName();
	int countryCount = playerTest.getAssignedCountries().size();
	
	assertEquals("India",name);
	assertEquals(1,countryCount);
	
	}
	
	
	/**
	 * The following returns the List of Continents owned by the players.
	 */
	@Test
	public void testContinentControlByPlayer()
	{
		
		int size = playerTest.contienentControlList(playerTest).size();
		assertEquals(1, size);
		
		
	}
}
