package com.concordia.riskGame.model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.concordia.riskGame.model.Continent.Continent;
import com.concordia.riskGame.model.Country.Country;

/**
 * This class implements the logic for the benevolent player behavior
 * 
 * @author Sande
 *
 */
public class BenevolentPlayer implements PlayerStrategy, Serializable {

	private static final long serialversionUID = 1L;

	/**
	 * This method implements the reinforce phase of the benevolent player
	 * 
	 * @param player player object
	 * @return return player object after reinforcing
	 */
	@Override
	public Player reinforcePhase(Player player) {
		player.setDomination();
		player.setCurrentPhase(Player.reinforcePhase);
		int armiesContControl = 0;
		int assignedArmies = 0;
		int armiesToBeGiven = 0;
		player.setPhase(player.getName() + " is in Reinforcement phase ");
		player.setPhase("#### Benevolent Player Reinforcement Phase");
		assignedArmies = player.calculateReiforcementArmies(player.getAssignedCountries().size());
		List<Continent> currcontControlList = new ArrayList();
		currcontControlList = player.contienentControlList(player);
		if (currcontControlList != null) {
			for (Continent cont : currcontControlList) {
				player.setPhase(" ##### continent name is ###### " + cont.getContinentName()
						+ " and     control value is " + cont.getContinentControlValue());
				armiesContControl = armiesContControl + cont.getContinentControlValue();
			}
		}
		player.setPhase(
				"#### The armies to be assigned from control value of continent is ###### : " + armiesContControl);
		assignedArmies = assignedArmies + armiesContControl;
		player.setPhase("#### The total number of armies to be reinforced are  #### :" + assignedArmies);
		Country weakestCountry = getWeakestCountry(player);
		weakestCountry.setArmies(weakestCountry.getArmies() + assignedArmies);
		player.setPhase("######### Player army count after reinforcment  ####### ");
		player.setPhase("######## Player Name ########### : " + player.getName());
		player.printAllCountriesOfaPlayer(player);
		player.setCanAttack(true);
		return player;
	}

	/**
	 * This method is to get the strongest country of a player
	 * 
	 * @param player player It takes input of type player model
	 * @return country with most number of armies
	 */
	private Country getWeakestCountry(Player player) {
		List<Country> playerOwnedCountries = player.getAssignedCountries();
		Country weakestCountry = null;
		int armyCount = 9999;
		for (Country country : playerOwnedCountries) {
			if (country.getArmies() < armyCount) {
				armyCount = country.getArmies();
				weakestCountry = country;
			}
		}
		return weakestCountry;
	}

	/**
	 * This method implements the attack phase of the benevolent player
	 * 
	 * @param player player object
	 * @return return player object after attack is complete
	 */
	@Override
	public Player attackPhase(Player player) {
		player.setDomination();
		player.setCurrentPhase(Player.attackPhase);
		player.setPhase(player.getName() + " is in attack phase ");
		player.setPhase("#### BENEVOLENT PLAYER ATTACK PHASE BEGINS####");
		player.setPhase("#### BENEVOLENT PLAYER DOESNT ATTACK####");
		checkPlayerTurnCanContinue(player);
		return player;
	}

	/**
	 * This method implements the fortify phase of the benevolent player
	 * 
	 * @param player player object
	 * @return return player object after fortifying
	 */
	@Override
	public Player forfeitPhase(Player player) {
		player.setCurrentPhase(Player.fortificationPhase);
		player.setDomination();
		player.setPhase(player.getName() + " is in Forfeit phase ");
		player.setPhase("#### Benevolent Player Forfeit Phase");
		player.setPhase("#### Before Fortification ####");
		player.printAllCountriesOfaPlayer(player);
		boolean fortificationDone = false;
		List<Country> sortedCountryList = getSortedCountryListBasedOnDescArmy(player);
		Country sourceCountry = null;
		Country destinationCountry = null;
		int countriesSize = sortedCountryList.size();
		while (!fortificationDone && countriesSize > 0) {
			destinationCountry = sortedCountryList.get(countriesSize - 1);
			for (Country country2 : destinationCountry.getNeighbouringCountries()) {
				sourceCountry = player.getSourceCountryFromPlayerUsingString(country2.getCountryName(), player);
				if (sourceCountry != null && sourceCountry.getArmies() > 3) {
					fortificationDone = true;
					break;
				}
			}
			countriesSize--;
		}

		if (sourceCountry == null || destinationCountry == null) {
			player.setPhase("#### Fortification not possible####");
			player.setPhase("#### After Fortification ####");
			player.printAllCountriesOfaPlayer(player);
			return player;
		}
		player.setPhase("					###########    Source country      	     ###############       : "
				+ sourceCountry.getCountryName());
		player.setPhase("					###########  Destination Country   	 ###############       : "
				+ destinationCountry.getCountryName());
		player.setPhase("					############   Armies to be moved    ###############      : " + (2));
		destinationCountry.setArmies(destinationCountry.getArmies() + 2);
		sourceCountry.setArmies(sourceCountry.getArmies() - 2);
		player.setPhase("#### After Fortification ####");
		player.printAllCountriesOfaPlayer(player);
		return player;
	}

	/**
	 * This method is to sort the country list of a player based on the army count
	 * of the countries in descending order
	 * 
	 * @param player the player object is passed
	 * @return a sorted list of countries
	 */
	public List<Country> getSortedCountryListBasedOnDescArmy(Player player) {
		List<Integer> armiesList = new ArrayList<>();
		for (Country country : player.getAssignedCountries()) {
			Country playerCountry = player.getSourceCountryFromString(country.getCountryName());
			armiesList.add(playerCountry.getArmies());
		}
		List<Country> sortedCountryList = new ArrayList<>();
		Collections.sort(armiesList);
		Collections.reverse(armiesList);
		for (Integer army : armiesList) {
			for (Country country : player.getAssignedCountries()) {
				Country playerCountry = player.getSourceCountryFromString(country.getCountryName());
				if (playerCountry.getArmies() == army && !sortedCountryList.contains(playerCountry)) {
					sortedCountryList.add(playerCountry);
					break;
				}
			}
		}
		return sortedCountryList;
	}

	/**
	 * This method checks if player's turn can continue or not
	 * 
	 * @param player player object
	 */
	void checkPlayerTurnCanContinue(Player player) {
		for (Country c : player.getAssignedCountries()) {
			player.setCanAttack(false);
			player.setCanFortify(false);
			if (c.getArmies() > 1 && player.checkNeighboringAttackableCountriesAndArmies(c, player) != null
					&& !player.checkNeighboringAttackableCountriesAndArmies(c, player).isEmpty()) {
				player.setCanAttack(true);
			}

			if (c.getArmies() > 1 && player.checkNeighboringPlayerOwnedCountriesAndArmies(c, player) != null
					&& !player.checkNeighboringPlayerOwnedCountriesAndArmies(c, player).isEmpty()) {
				player.setCanFortify(true);
				break;
			}
		}
		if (player.getAssignedCountries().size() == 1) {
			player.setCanFortify(false);
		}
	}
}
