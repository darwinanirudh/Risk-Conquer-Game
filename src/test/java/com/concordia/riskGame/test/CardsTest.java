package com.concordia.riskGame.test;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.concordia.riskGame.model.Card.Card;
import com.concordia.riskGame.model.Card.Deck;
import com.concordia.riskGame.model.Country.Country;
/**
 * This Class implements the test methods to test deck of cards
 * @author Dheeraj As
 *
 */
public class CardsTest {
	private List<Country> list;
	private Deck deckObj;
	private Card cardObj;
	/**
	 * sets up the data for card test methods 
	 * @throws Exception throws exception
	 */
	@Before
	public void setUp() throws Exception {
		deckObj= new Deck();
		list= new ArrayList<>();
		list.add(new Country("c1"));
		list.add(new Country("c2"));
		list.add(new Country("c3"));
		list.add(new Country("c4"));
		list.add(new Country("c5"));
		cardObj= new Card("Infantry",new Country("c1"));
	}
	/**
	 * Method to check for setting of the deck of cards
	 */
	@Test
	public void setDeckOfCardsTest() {
		deckObj.setDeckOfCards(list);
		for(int i=0; i<=4; i++) {
			assertNotNull(deckObj.draw());
		}
	}
	/**
	 * Method to check drawing of cards
	 */
	@Test
	public void DeckOfCardsDrawTest() {
		deckObj.setDeckOfCards(list);
		for(int i=0; i<=4; i++) {
			assertNotNull(deckObj.draw());
		}
			assertTrue(deckObj.deckOfCards.isEmpty());
	}
	/**
	 * Method to check if card can be added to the deck
	 */
	@Test
	public void addCardTest() {
		deckObj.setDeckOfCards(list);
		for(int i=0; i<=4; i++) {
			assertNotNull(deckObj.draw());
		}
		assertTrue(deckObj.deckOfCards.isEmpty());
		deckObj.add(cardObj);
		assertFalse(deckObj.deckOfCards.isEmpty());
	}
}


