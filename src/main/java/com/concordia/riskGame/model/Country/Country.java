package com.concordia.riskGame.model.Country;

import java.io.Serializable;
import java.util.List;

import com.concordia.riskGame.model.Player.Player;

/**
 * Country Class go get and set all properties of a country.
 * 
 * @author sande
 *
 */

public class Country implements Serializable {

	private static final long serialversionUID = 1L;

	private String countryName;
	private List<Country> neighbouringCountries;
	private String belongsToContinent;
	private int startPixel;
	private int endPixel;
	private int armies;
	private Player belongsToPlayer;

	/***
	 * Default Constructor
	 **/
	public Country() {
	}

	/***
	 * This is the parameterized constructor with following parameters
	 * 
	 * @param countryName Name of the Country
	 **/
	public Country(String countryName) {
		this.countryName = countryName;
	}

	/***
	 * This is the parameterized constructor with following parameters
	 * 
	 * @param countryName        Name of the Country
	 * @param belongsToContinent Name of the Continent
	 **/
	public Country(String countryName, String belongsToContinent) {
		this.countryName = countryName;
		this.belongsToContinent = belongsToContinent;
	}

	/**
	 * This is the parameterized constructor with following parameters
	 * 
	 * @param countryName        Name of the Country
	 * @param startPixel         For displaying the map
	 * @param endPixel           For displaying the map
	 * @param belongsToContinent Name of the Continent
	 */
	public Country(String countryName, int startPixel, int endPixel, String belongsToContinent) {
		this.countryName = countryName;
		this.startPixel = startPixel;
		this.endPixel = endPixel;
		this.belongsToContinent = belongsToContinent;
	}

	/**
	 * method to get the name of the country
	 * 
	 * @return countryName : Name of the country
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * method to set the name of the country
	 * 
	 * @param countryName : Name of the country
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * method to get the neighbors of this particular country
	 * 
	 * @return adjacentCountries : List of neighboring countries
	 */
	public List<Country> getNeighbouringCountries() {
		return neighbouringCountries;
	}

	/**
	 * method to set the neighbors of this particular country
	 * 
	 * @param adjacentCountries : List of neighboring countries
	 */
	public void setNeighbouringCountries(List<Country> adjacentCountries) {
		this.neighbouringCountries = adjacentCountries;
	}

	/**
	 * method to get the continent to which the country belongs
	 * 
	 * @return belongsToContinent : Name of the continent to which the country
	 *         belongs
	 */
	public String getBelongsToContinent() {
		return belongsToContinent;
	}

	/**
	 * method to set the continent to which the country belongs
	 * 
	 * @param belongsToContinent : Name of the continent to which the country
	 *                           belongs
	 */
	public void setBelongsToContinent(String belongsToContinent) {
		this.belongsToContinent = belongsToContinent;
	}

	/**
	 * method to get the startPixel
	 * 
	 * @return startPixel : Start Coordinate of the country
	 */
	public int getStartPixel() {
		return startPixel;
	}

	/**
	 * method to set the startPixel
	 * 
	 * @param startPixel : Start Coordinate of the country
	 */
	public void setStartPixel(int startPixel) {
		this.startPixel = startPixel;
	}

	/**
	 * method to get the startPixel
	 * 
	 * @return endPixel : End coordinate of the country
	 */
	public int getEndPixel() {
		return endPixel;
	}

	/**
	 * method to set the startPixel
	 * 
	 * @param endPixel : End coordinate of the country
	 */
	public void setEndPixel(int endPixel) {
		this.endPixel = endPixel;
	}

	/**
	 * This method returns number of armies.
	 * 
	 * @return The number of armies is returned.
	 */
	public int getArmies() {
		return armies;
	}

	/**
	 * This method set the number of armies.
	 * 
	 * @param armies The number of armies.
	 */
	public void setArmies(int armies) {
		this.armies = armies;
	}

	/**
	 * This method returns the player to which the country belongs.
	 * 
	 * @return Player Returns the player to which this country belongs.
	 */
	public Player getBelongsToPlayer() {
		return belongsToPlayer;
	}

	/**
	 * This method sets the player to which the country belongs.
	 * 
	 * @param belongsToPlayer The player to which this country belongs.
	 */
	public void setBelongsToPlayer(Player belongsToPlayer) {
		this.belongsToPlayer = belongsToPlayer;
	}

	@Override
	public boolean equals(Object anObject) {
		if (!(anObject instanceof Country)) {
			return false;
		}
		Country otherCountry = (Country) anObject;
		return otherCountry.getCountryName().equals(getCountryName());
	}

}
