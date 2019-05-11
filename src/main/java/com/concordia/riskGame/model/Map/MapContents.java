package com.concordia.riskGame.model.Map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.concordia.riskGame.model.Continent.Continent;
import com.concordia.riskGame.model.Country.Country;
import com.concordia.riskGame.model.Player.Player;

/**
 * This class contains getters and setters for MapContents attributes
 * 
 * @author Dheeraj As
 */
public class MapContents implements Serializable {

	private static final long serialversionUID = 1L;

	private static MapContents mapContents;

	private HashMap<Country, List<Country>> countryAndNeighbors = new HashMap<>();
	private HashMap<Continent, List<Country>> continentAndItsCountries = new HashMap<>();
	private String mapAuthorName;
	private int labelCount;
	public List<Player> playerList = new ArrayList<>();
	private List<Country> countryList = new ArrayList<>();
	private int cardExchangeCount = 0;
	public int rotateCount;
	private List<String> tournamentResults;

	/**
	 * Default Constructor
	 */
	private MapContents() {
	}

	/**
	 * It creates the Instance of the mapContents
	 * 
	 * @return returns mapContents object
	 */
	public static MapContents getInstance() {
		if (null == mapContents) {
			mapContents = new MapContents();
		}
		return mapContents;
	}

	/**
	 * This method is to get map author name
	 * 
	 * @return mapAuthorName
	 */
	public String getMapAuthorName() {
		return mapAuthorName;
	}

	/**
	 * This method is to set map author name
	 * 
	 * @param mapAuthorName Name of the map author.
	 */
	public void setMapAuthorName(String mapAuthorName) {
		this.mapAuthorName = mapAuthorName;
	}

	/**
	 * This method is to get Hashmap of countries and its list of neighbors.
	 * 
	 * @return countryAndNeighbors Hashmap of countries and its list of neighbors.
	 */
	public HashMap<Country, List<Country>> getCountryAndNeighbors() {
		return countryAndNeighbors;
	}

	/**
	 * This method is to set Hashmap of countries and its list of neighbors.
	 * 
	 * @param countryAndNeighbors Hashmap of countries and its list of neighbors.
	 */
	public void setCountryAndNeighbors(HashMap<Country, List<Country>> countryAndNeighbors) {
		this.countryAndNeighbors = countryAndNeighbors;
	}

	/**
	 * Method to get Continent and its countries
	 * 
	 * @return continentAndItsCountries Hashmap of continents and its list of
	 *         countries.
	 */
	public HashMap<Continent, List<Country>> getContinentAndItsCountries() {
		return continentAndItsCountries;
	}

	/**
	 * Method to set Continent and its countries
	 * 
	 * @param continentAndItsCountries Hashmap of continents and its list of
	 *                                 countries.
	 */
	public void setContinentAndItsCountries(HashMap<Continent, List<Country>> continentAndItsCountries) {
		this.continentAndItsCountries = continentAndItsCountries;
	}

	/**
	 * To set the MapContentsObject
	 * 
	 * @param mapContents object is set
	 */
	public static void setMapContents(MapContents mapContents) {
		MapContents.mapContents = mapContents;
	}

	/**
	 * This method returns the Player List
	 * 
	 * @return List of Players in the game.
	 */
	public List<Player> getPlayerList() {
		return playerList;
	}

	/**
	 * This method sets the Player List
	 * 
	 * @param playerList The list of players to be set.
	 */
	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}

	/**
	 * Returns number of labels in the map
	 * 
	 * @return It returns labelCount
	 */
	public int getLabelCount() {
		return labelCount;
	}

	/**
	 * Sets number of labels in the map
	 * 
	 * @param labelCount label count value
	 */
	public void setLabelCount(int labelCount) {
		this.labelCount = labelCount;
	}

	/**
	 * Get the country list
	 * 
	 * @return the countryList
	 */
	public List<Country> getCountryList() {
		return countryList;
	}

	/**
	 * Setting the country list
	 * 
	 * @param countryList The countryList to set
	 */
	public void setCountryList(List<Country> countryList) {
		this.countryList = countryList;
	}

	/**
	 * Getting the cardexchangecount
	 * 
	 * @return It returns the CardExchange count
	 */
	public int getCardExchangeCount() {
		return cardExchangeCount;
	}

	/**
	 * Setting the army exchnage count
	 * 
	 * @param armyExchangeCount Army exchange count
	 */
	public void setCardExchangeCount(int armyExchangeCount) {
		this.cardExchangeCount = armyExchangeCount;
	}

	/**
	 * The following method returns the rotate count.
	 * 
	 * @return It returns the value of rotato count.
	 */
	public int getRotateCount() {
		return rotateCount;
	}

	/**
	 * The following sets the value of rotate count.
	 * 
	 * @param rotateCount Number of times to rotate.
	 */
	public void setRotateCount(int rotateCount) {
		this.rotateCount = rotateCount;
	}

	/**
	 * This method returns the result of the tournament
	 * 
	 * @return tournamentResults result of the tournament
	 */
	public List<String> getTournamentResults() {
		return tournamentResults;
	}

	/**
	 * This method sets the result of the tournament
	 * 
	 * @param tournamentResults result of the tournament
	 */
	public void setTournamentResults(List<String> tournamentResults) {
		this.tournamentResults = tournamentResults;
	}

}
