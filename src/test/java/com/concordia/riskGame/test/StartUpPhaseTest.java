package com.concordia.riskGame.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.concordia.riskGame.model.Country.Country;
import com.concordia.riskGame.model.Map.MapParseProcessor;
import com.concordia.riskGame.model.Player.Player;

/**
 * This Test Class implements the test methods to validate the StartUp Phase 
 * @author Dheeraj As
 *
 */

public class StartUpPhaseTest {
	
	int noOfPlayers2;
	int noOfPlayers3;
	int	noOfPlayers4;
	int	noOfPlayers5;
	int	noOfPlayers6;
	int armiesGot;
	List<Player> playerList;
	Player playerObject1;
	Player playerObject2;
	Player playerObject3;
	int initialArmies_test;
	List<Country> assignedCountries1;
	List<Country> assignedCountries2;
	List<Country> assignedCountries3;
	
	/**
	 * This method sets up the data for start up phase testing
	 */
	@Before
	public void setUp() {
		noOfPlayers2= 2;
		noOfPlayers3= 3;
		noOfPlayers4= 4;
		noOfPlayers5= 5;
		noOfPlayers6= 6;
		initialArmies_test=35;
		
		assignedCountries1= new ArrayList<>();
		assignedCountries2= new ArrayList<>();
		assignedCountries3= new ArrayList<>();
		playerObject1= new Player();
		playerObject2= new Player();
		playerObject3= new Player();
		playerList= new ArrayList<>();
		assignedCountries1.add(new Country("a1"));
		assignedCountries1.add(new Country("a2"));
		assignedCountries1.add(new Country("a3"));
		assignedCountries2.add(new Country("b1"));
		assignedCountries2.add(new Country("b2"));
		assignedCountries2.add(new Country("b3"));
		assignedCountries3.add(new Country("c1"));
		assignedCountries3.add(new Country("c2"));
		assignedCountries3.add(new Country("c3"));
		playerObject1.setAssignedCountries(assignedCountries1);
		playerObject2.setAssignedCountries(assignedCountries2);
		playerObject3.setAssignedCountries(assignedCountries3);
		playerObject1.setTotalArmies(initialArmies_test);
		playerObject2.setTotalArmies(initialArmies_test);
		playerObject3.setTotalArmies(initialArmies_test);
		playerList.add(playerObject1);
		playerList.add(playerObject2);
		playerList.add(playerObject3);
		
	}
	/**
	 * This test method checks if the initial army assignment is done for 2 players
	 */
	@Test
	public void validInitialArmyAssignment2PlayerTest() {
		
		MapParseProcessor processorObj= new MapParseProcessor();
		armiesGot=processorObj.initialArmyAssignment(noOfPlayers2);
		assertEquals(40,armiesGot);
	}
	/**
	 * This test method checks if the initial army assignment is done for 3 players
	 */
	@Test
	public void validInitialArmyAssignment3PlayerTest() {
		MapParseProcessor processorObj= new MapParseProcessor();
		armiesGot=processorObj.initialArmyAssignment(noOfPlayers3);
		assertEquals(35,armiesGot);
	}
	/**
	 * This test method checks if the initial army assignment is done for 4 players
	 */
	@Test
	public void validInitialArmyAssignment4PlayerTest() {
		MapParseProcessor processorObj= new MapParseProcessor();
		armiesGot=processorObj.initialArmyAssignment(noOfPlayers4);
		assertEquals(30,armiesGot);
	}	
	/**
	 * This test method checks if the initial army assignment is done for 5 players
	 */
	@Test
	public void validInitialArmyAssignment5PlayerTest() {
		MapParseProcessor processorObj= new MapParseProcessor();
		armiesGot=processorObj.initialArmyAssignment(noOfPlayers5);
		assertEquals(25,armiesGot);
	}	
	/**
	 * This test method checks if the initial army assignment is done for 6 players
	 */
	@Test
	public void validInitialArmyAssignment6PlayerTest() {
		MapParseProcessor processorObj= new MapParseProcessor();
		armiesGot=processorObj.initialArmyAssignment(noOfPlayers6);
		assertEquals(20,armiesGot);
	}		
	/**
	 * This method checks if the assigned initial armies is not zero
	 */
	@Test
	public void invalidInitialArmyAssignmentTest() {
		armiesGot=0;
		MapParseProcessor processorObj= new MapParseProcessor();
		armiesGot=processorObj.initialArmyAssignment(noOfPlayers2);
		assertNotEquals(0,armiesGot);
		
		armiesGot=processorObj.initialArmyAssignment(noOfPlayers3);
		assertNotEquals(0,armiesGot);
		
		armiesGot=processorObj.initialArmyAssignment(noOfPlayers4);
		assertNotEquals(0,armiesGot);
		
		armiesGot=processorObj.initialArmyAssignment(noOfPlayers5);
		assertNotEquals(0,armiesGot);
		
		armiesGot=processorObj.initialArmyAssignment(noOfPlayers6);
		assertNotEquals(0,armiesGot);
	}
	/**
	 * This test method checks if all the countries owned by players are assigned with armies randomly
	 */
	@Test
	public void randomPlacementOfArmiesTest() {
		MapParseProcessor processorObj= new MapParseProcessor();
		processorObj.armyAssignment(playerList);
		
		for(int i=0; i<=2; i++) {
			for(int j=0;j<=2;j++) {
				assertNotNull(playerList.get(i).getAssignedCountries().get(j).getArmies());
			}
		}
	}
	
	

	
}
