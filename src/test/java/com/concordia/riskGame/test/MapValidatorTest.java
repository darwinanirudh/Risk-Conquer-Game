package com.concordia.riskGame.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.concordia.riskGame.exception.InvalidMapFileException;
import com.concordia.riskGame.util.MapValidator;
/**
 * This TestClass implements the test methods for Map Validation
 * @author Dheeraj As
 *
 */
public class MapValidatorTest {
	
	File fileValid;
	File fileInvalidDisconnectedContinent;
	File fileInvalidDisconnected;
	File fileInvalidZeroContinent;
	File fileInvalidZeroCountry;
	File fileInvalidUnassignedCountry;
	File fileInvalidAssignedToTwoCountries;
	File fileInvalidContinentNotInList;
	File fileInvalidContinentControlValue;
	MapValidator mapValidatorObject;
	Boolean validFlag;
	
	/**
	 * This method loads all the maps into different file objects
	 * @throws Exception throws exception
	 */
	@Before
	public void setUp() throws Exception {
		fileValid= new File("src/main/resources/Africa.map");
		fileInvalidDisconnectedContinent= new File("src/main/resources/Invalid_Not_Connected_Continent_Graph.map");
		fileInvalidDisconnected= new File("src/main/resources/Invalid_Not_Connected_Graph.map");
		fileInvalidZeroContinent= new File("src/main/resources/Invalid_Zero_Continent.map");	
		fileInvalidZeroCountry= new File("src/main/resources/Invalid_Zero_Country.map");
		fileInvalidUnassignedCountry= new File("src/main/resources/Invalid_Country_Not_Assigned_To_Continent.map");
		fileInvalidAssignedToTwoCountries= new File("src/main/resources/Invalid_Country_Assigned_To_Two_Continent.map");
		fileInvalidContinentNotInList= new File("src/main/resources/Invalid_Country_Continent_Not_From_Continent_List.map");
		fileInvalidContinentControlValue = new File("src/main/resources/Invalid_Continent_Control_Value.map");
		
		mapValidatorObject = new MapValidator(); 
		
		
	}
	
   /**
    * This method check if map is valid 
    * The input used is a valid map
    * @throws InvalidMapFileException  Map is Invalid.
    */
	@Test
	public void validMapTest() throws InvalidMapFileException {
		
		 mapValidatorObject.init(fileValid);
		 validFlag=mapValidatorObject.getValidMapFlag();
		 
		 assertTrue(validFlag);
	}
	
	/**
	 * This method checks if a disconnected continent map is considered to be invalid
	 * @throws InvalidMapFileException  Map is Invalid.
	 */
	@Test
	public void testDisconnectedContinentMap() throws InvalidMapFileException {
		
		 mapValidatorObject.init(fileInvalidDisconnectedContinent);
		 validFlag=mapValidatorObject.getValidMapFlag();
		 
		 assertFalse(validFlag);
	}
	
	/**
	 * This method checks if a disconnected map is considered to be invalid
	 * @throws InvalidMapFileException  Map is Invalid.
	 */
	@Test
	public void testDisconnectedMap() throws InvalidMapFileException {
		
		 mapValidatorObject.init(fileInvalidDisconnected);
		 validFlag=mapValidatorObject.getValidMapFlag();
		 
		 assertFalse(validFlag);
	}
	
	/**
	 * This method checks if a continent control value is valid
	 * @throws InvalidMapFileException  Map is Invalid.
	 */
	@Test
	public void testcheckContinentControlValue() throws InvalidMapFileException {
		
		 mapValidatorObject.init(fileInvalidContinentControlValue);
		 validFlag=mapValidatorObject.getValidMapFlag();
		 
		 assertFalse(validFlag);
	}
	
    /**
     * This method checks if map with Zero Continents is considered to be invalid
     */
	@Test
	public void testZeroContinentMap() {
		 mapValidatorObject.init(fileInvalidZeroContinent);
		 validFlag=mapValidatorObject.getValidMapFlag();
		 
		 assertFalse(validFlag);
	}
	
    /**
     * This method checks if map with Zero Continents is considered to be invalid
     */
	@Test
	public void testZeroCountryMap() {
		
		mapValidatorObject.init(fileInvalidZeroCountry);
		validFlag=mapValidatorObject.getValidMapFlag();
		 
		 assertFalse(validFlag);
		
	}
	
   /**
    * This method checks if map with an Unassigned country throws an error
    * 
    */
	@Test
	public void testUnassignedCountryMap() {
		
		mapValidatorObject.init(fileInvalidUnassignedCountry);
		validFlag=mapValidatorObject.getValidMapFlag();
		 
		 assertFalse(validFlag);
		
	}
	
    /**
     * 	This method checks if map with a country assigned to two Continents throws an error
     */
	@Test
	public void testAssignedCountryToTwoContinent() {
		
		mapValidatorObject.init(fileInvalidAssignedToTwoCountries);
		validFlag=mapValidatorObject.getValidMapFlag();
		 
		 assertFalse(validFlag);
		
	}

	/**
	 * This method tests if the continent with that is not in the list throws error or not
	 */
	@Test
	public void testContinentNotInListMap() {
		
		mapValidatorObject.init(fileInvalidContinentNotInList);
		validFlag=mapValidatorObject.getValidMapFlag();
		 
		 assertFalse(validFlag);
		
	}


}
