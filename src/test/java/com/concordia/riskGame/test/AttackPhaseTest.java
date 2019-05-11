package com.concordia.riskGame.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.concordia.riskGame.control.GameDriver;
import com.concordia.riskGame.model.Card.Card;
import com.concordia.riskGame.model.Card.Deck;
import com.concordia.riskGame.model.Country.Country;
import com.concordia.riskGame.model.Map.MapContents;
import com.concordia.riskGame.model.Player.Player;

/**
 * The following class performs unit tests on attack phase.
 * @author Darwin Anirudh
 *
 */
public class AttackPhaseTest {

	private Player playerOne;
	private Player playerTwo;

	private Country countryOne;
	private Country countryTwo;
	private Country countryThree;
	private Country countryFour;

	private List<Country> playerOneCountryList;
	private List<Country> playerTwoCountryList;

	private String choice;
	private String sourceCountry;
	private Country sourceCountryObject;
	private String destinationCountry;
	private Country destinationCountryObject;
	private List<Country> attackableCountryList;
	private int attackerDice;
	private int maximumAttackerDice;
	private int maximumDefenderDice;
	private int defenderDice;
	private List<Integer> attackerDiceResults;
	private List<Integer> defenderDiceResults;
	private boolean allOut;
	private String attackChoice;
	private String attackerDiceString;
	private String defenderDiceString;
	public MapContents contents;
	private List<Country> neighborCountryOne;
	private List<Country> neighborCountryTwo;
	private List<Country> neighborCountryThree;
	private List<Country> neighborCountryFour;
	private List<Country> c1;
	private List<Country> c2;
	private List<Country> c3;
	private List<Country> c4;
	private List<Card> playerOneCard;
	private List<Card> playerTwoCard;
	private List<Country> countryList;
	private Card cd1;
	private Card cd2;
	private Card cd3;
	private Card cd4;
	
	/**
	 * The following method sets up the testing context for the unit tests.
	 */
	@Before
	public void setUp() {
		playerOne = new Player("playerOne");
		playerTwo = new Player("playerTwo");
		countryList = new ArrayList();

		countryOne = new Country();
		countryOne.setCountryName("countryOne");
		countryOne.setArmies(5);
		countryOne.setBelongsToPlayer(playerOne);

		countryTwo = new Country();
		countryTwo.setCountryName("countryTwo");
		countryTwo.setArmies(5);
		countryTwo.setBelongsToPlayer(playerOne);

		countryThree = new Country("countryThree");
		countryThree.setCountryName("countryThree");
		countryThree.setArmies(5);
		countryThree.setBelongsToPlayer(playerTwo);

		countryFour = new Country("countryFour");
		countryFour.setCountryName("countryFour");
		countryFour.setArmies(5);
		countryFour.setBelongsToPlayer(playerTwo);
		
		countryList.add(countryOne);
		countryList.add(countryTwo);
		countryList.add(countryThree);
		countryList.add(countryFour);

		neighborCountryOne = new ArrayList();
		neighborCountryOne.add(countryThree);
		countryOne.setNeighbouringCountries(neighborCountryOne);

		neighborCountryTwo = new ArrayList();
		neighborCountryTwo.add(countryFour);
		countryTwo.setNeighbouringCountries(neighborCountryTwo);

		neighborCountryThree = new ArrayList();
		neighborCountryThree.add(countryOne);
		countryThree.setNeighbouringCountries(neighborCountryThree);

		neighborCountryFour = new ArrayList();
		neighborCountryFour.add(countryTwo);
		countryFour.setNeighbouringCountries(neighborCountryFour);

		playerOneCountryList = new ArrayList();
		playerOneCountryList.add(countryOne);
		playerOneCountryList.add(countryTwo);

		playerOne.setAssignedCountries(playerOneCountryList);

		playerTwoCountryList = new ArrayList();
		playerTwoCountryList.add(countryThree);
		playerTwoCountryList.add(countryFour);

		playerTwo.setAssignedCountries(playerTwoCountryList);

		contents = MapContents.getInstance();
		HashMap<Country, List<Country>> countriesAndItsNeighbours = contents.getCountryAndNeighbors();
		contents.setCountryList(countryList);
		
		c1 = new ArrayList();
		c1.add(countryThree);

		c2 = new ArrayList();
		c2.add(countryFour);

		c3 = new ArrayList();
		c3.add(countryOne);

		c4 = new ArrayList();
		c4.add(countryTwo);

		countriesAndItsNeighbours.put(countryOne, c1);
		countriesAndItsNeighbours.put(countryTwo, c2);
		countriesAndItsNeighbours.put(countryThree, c3);
		countriesAndItsNeighbours.put(countryFour, c4);

		
		playerOneCard = new ArrayList();
		playerTwoCard = new ArrayList();
		Deck deck = Deck.getInstance();
		System.out.println("#### The country list size is ###### : "+MapContents.getInstance().getCountryList());
		deck.setDeckOfCards(MapContents.getInstance().getCountryList());
		
		/*cd1 = cd1.getCarrdInfo(cd1);*/
		Card cd1 = deck.draw();
				

		
		Card cd2 =deck.draw();

		playerOneCard.add(cd1);
		playerOneCard.add(cd2);

		
		 Card cd3 = deck.draw();
    	 Card cd4 = deck.draw();

		playerTwoCard.add(cd3);
		playerTwoCard.add(cd4);

		playerOne.setCardList(playerOneCard);
		playerTwo.setCardList(playerTwoCard);
	}

	/**
	 * The following method checks the list of attackable countries is valid or not.
	 */
	@Test
	public void printAttackAbleCountryList()
	{
		List<Country> attackableCountryList = new ArrayList();
		attackableCountryList = playerOne.printNeighboringAttackableCountriesAndArmies(countryOne, playerOne);
		String countryName = attackableCountryList.get(0).getCountryName();
		Country c = new Country();
		c = playerOne.getAttackableCountryOfCountryListFromString("countryThree", attackableCountryList);
		assertEquals(countryName, "countryThree");
		assertEquals(c.getCountryName(), "countryThree");
	}

	/**
	 * The following test method checks if player has won the game or not by checking count of countries.
	 */
	@Test
	public void hasPlayerWon()
	{
		assertFalse(playerOne.hasPlayerWon(playerOne));
	}
	
	
	/**
	 *The following test method checks if the cards are transfered after player loses. 
	 */
	@Test
	public void checkValidCards()
	{
		playerOne.playerHasLost(countryOne, countryThree);
		assertEquals(playerOne.getCardList().size(), 4);
		assertEquals(playerTwo.getCardList().size(), 0);
	}
	
	
	/**
	 * The following test method checks if armies are reduced after it is defeated. 
	 */
	@Test
	public void checkPlayerLoses()
	{
		countryTwo.setArmies(0);
		playerOne.movableArmies = 1;
		int armies = countryOne.getArmies();
		playerOne.playerLosesTheCountry(countryOne, countryThree, playerOne);
		assertEquals(countryOne.getBelongsToPlayer().getName(), countryThree.getBelongsToPlayer().getName());
		assertEquals(countryOne.getArmies(), armies - 1);
		
	}
	
	/**
	 * The following test method checks if correct number of dice count is returned depending upon the number of armies.
	 */
	@Test
	public void checkAttackerDefenderDice()
	{
		int armies = countryOne.getArmies();
		assertEquals(3, playerOne.getMaxAttackerDiceCount(armies));
		
		int armiesDef = countryThree.getArmies();
		assertEquals(2, playerOne.getMaxDefenderDiceCount(armiesDef));
		
	}
	
	
}
