package com.concordia.riskGame.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import com.concordia.riskGame.model.Country.Country;
import com.concordia.riskGame.model.Map.MapContents;
import com.concordia.riskGame.model.Player.Player;


/**
 * This class implements the test cases for testing the computation of
 * fortification phase
 * @author D_Modi
 *
 */
public class FortificationTest {

	private Player p1, p2;
	private Country c1,c2,c3,c4;
	private List<Country> listCountry, listCountry1;
	private List<Player> listPlayer;
	private MapContents mapContents;
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
		c1.setArmies(10);
		c2.setArmies(10);
		c3.setArmies(10);
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
		listCountry.add(c3);
		c4.setNeighbouringCountries(listCountry);
		countryAndNBCountry.put(c4, listCountry);
		mapContents.setCountryAndNeighbors(countryAndNBCountry);
		
		listCountry = new ArrayList<>();
		
		listCountry.add(c1);
		listCountry.add(c2);
		p1.setAssignedCountries(listCountry);
		listPlayer.add(p1);
						
		listCountry1.add(c3);
		listCountry1.add(c4);
		p2.setAssignedCountries(listCountry1);
		listPlayer.add(p2);
		
		mapContents.setPlayerList(listPlayer);
		
	}
	
	/**
	 * Test method for 'From and To Country Can not Be Same' test case
	 */
	@Test
	public void testFromAndToCountryCanNotBeTheSameNegative() {
			
		for(int i=0; i < p2.getAssignedCountries().size(); i++) {
			p2.getAssignedCountries().get(i).setArmies(10);
		}
		
		systemInMock.provideLines("yes","c1","c1","c2","1");
	    p1.forfeitPhase(p1);
		assertEquals("Armies have been moved between countries", p1.getErrorMesage());
		
	}
	
	/**
	 * Test method for 'Countries are not neighbour' test case
	 */
	@Test
	public void testCountriesAreNotNeighbourNegative() {

		for(int i=0; i < p2.getAssignedCountries().size(); i++) {
			p2.getAssignedCountries().get(i).setArmies(10);
		}		
		
		systemInMock.provideLines("yes","c3","c2","c4","1");
	    p2.forfeitPhase(p2);
		assertEquals("Armies have been moved between countries", p2.getErrorMesage());
		
	}
	
	/**
	 * Test method for 'Country has only one army left' test case
	 */
	@Test
	public void testMoveOnlyLeftOneArmyNegative() {

		for(int i=0; i < p2.getAssignedCountries().size(); i++) {
			p2.getAssignedCountries().get(i).setArmies(1);
		}
		
	    systemInMock.provideLines("yes","c3","c4","1","no");
	    p2.forfeitPhase(p2);
		assertEquals("You cannot move the only army from this Country", p2.getErrorMesage());
		
	}
	
	/**
	 * Test method for 'Country have less number of armies than user input' test case
	 */
	@Test
	public void testLessNoOfArmiesNegative() {

		for(int i=0; i < p2.getAssignedCountries().size(); i++) {
			p2.getAssignedCountries().get(i).setArmies(2);
		}
		
	    systemInMock.provideLines("yes","c3","c4","3","no");
	    p2.forfeitPhase(p2);
		assertEquals("The country doesnt have the mentioned number of armies, please enter a lesser number", p2.getErrorMesage());
		
	}
	
	/**
	 * Test method for 'Country can not move all armies' test case
	 */
	@Test
	public void testCanNotMoveAllArmiesNegative() {

		for(int i=0; i < p2.getAssignedCountries().size(); i++) {
			p2.getAssignedCountries().get(i).setArmies(3);
		}
		
	    systemInMock.provideLines("yes","c3","c4","3","no");
	    p2.forfeitPhase(p2);
		assertEquals("You cannot move all the armies from this Country, please enter a lesser number", p2.getErrorMesage());
		
	}
	
	/**
	 * Test method for successful fortification scenario 
	 */
	@Test
	public void testMoveArmyPositive() {

		for(int i=0; i < p2.getAssignedCountries().size(); i++) {
			p2.getAssignedCountries().get(i).setArmies(3);
		}
		
	    systemInMock.provideLines("yes","c3","c4","2","no");
	    p2.forfeitPhase(p2);
		assertEquals("Armies have been moved between countries", p2.getErrorMesage());
		
	}
	
}
