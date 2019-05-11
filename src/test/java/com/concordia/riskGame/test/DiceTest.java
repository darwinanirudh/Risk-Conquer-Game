package com.concordia.riskGame.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.concordia.riskGame.model.dice.Dice;
/**
 * This test class implements test case method for dice
 * @author Dheeraj As
 *
 */

public class DiceTest {

	int numberOfDice;
	List<Integer> results;
	Dice dice;
	boolean check;
	
	/**
	 * It is used for setting up context for test case
	 * @throws Exception throws exception
	 */
	@Before
	public void setUp() throws Exception {
		results = new ArrayList<>();
		numberOfDice=3;
	}
/**
 * Method to check the dice results
 */
	@Test
	public void diceResultTest() {
		dice = new Dice();
		results= dice.rollDice(numberOfDice);
		
		for(int i=0;i<3;i++) {
			assertNotEquals(Integer.valueOf(0),results.get(i));
			check = results.get(i) >6 ? false : true;
			assertTrue(check);
		}
		
		
	}

}
