package com.concordia.riskGame.model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.concordia.riskGame.View.CardView;
import com.concordia.riskGame.model.Card.Card;
import com.concordia.riskGame.model.Card.Deck;
import com.concordia.riskGame.model.Continent.Continent;
import com.concordia.riskGame.model.Country.Country;
import com.concordia.riskGame.model.dice.Dice;

/**
 * This class implements the behavior of aggressive player
 * 
 * @author Sande
 *
 */
public class AggresivePlayer implements PlayerStrategy, Serializable {

	private static final long serialversionUID = 1L;

	/**
	 * This method implements the reinforce phase of the aggresive player
	 * 
	 * @param player player object
	 * @return return player object after reinforcing
	 */
	@Override
	public Player reinforcePhase(Player player) {
		try {
			player.setDomination();
			player.setCurrentPhase(Player.reinforcePhase);
			int armiesContControl = 0;
			int assignedArmies = 0;
			int armiesToBeGiven = 0;
			player.setPhase(player.getName() + " is in Reinforcement phase ");
			player.setPhase("#### Aggressive Player Reinforcement Phase");
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
			CardView cardView = new CardView();
			armiesToBeGiven = cardView.exchangeCardsForComputerPlayers(player);
			assignedArmies += armiesToBeGiven;
			player.setPhase("#### The total number of armies to be reinforced are  #### :" + assignedArmies);
			Country strongestCountry = getStrongestCountry(player);
			strongestCountry.setArmies(strongestCountry.getArmies() + assignedArmies);
			player.setPhase("######### Player army count after reinforcment  ####### ");
			player.setPhase("######## Player Name ########### : " + player.getName());
			player.printAllCountriesOfaPlayer(player);
		} catch (Exception e) {
			System.out.println("Exception Message : " + e.getMessage());
			reinforcePhase(player);
		}
		player.setCanAttack(true);
		return player;
	}

	/**
	 * This method is to get the strongest country of a player
	 * 
	 * @param player It takes input of type player model
	 * @return country with most number of armies
	 */
	private Country getStrongestCountry(Player player) {
		List<Country> playerOwnedCountries = player.getAssignedCountries();
		Country strongestCountry = null;
		int armyCount = 0;
		for (Country country : playerOwnedCountries) {
			if (country.getArmies() > armyCount) {
				armyCount = country.getArmies();
				strongestCountry = country;
			}
		}
		return strongestCountry;
	}

	/**
	 * This method implements the attack phase of the player
	 * 
	 * @param player player object
	 * @return return player object after attack is complete
	 */
	@Override
	public Player attackPhase(Player player) {
		player.setCurrentPhase(Player.attackPhase);
		player.setDomination();
		int attackerDice = 0;
		int maximumAttackerDice = 0;
		int maximumDefenderDice = 0;
		int defenderDice = 0;
		Dice dice = new Dice();

		List<Integer> attackerDiceResults;
		List<Integer> defenderDiceResults;
		Country sourceCountryObject;
		Country destinationCountryObject;
		player.setPhase(player.getName() + " is in attack phase ");
		player.setPhase("#### Aggressive Player Attack Phase");
		int attackableCount = player.getAssignedCountries().size();
		List<Country> sortedListBasedOnArmies = getSortedCountryListBasedOnArmy(player);
		while (attackableCount != 0) {
			sourceCountryObject = getStrongestAttackableCountry(player);
			if (sourceCountryObject == null || sourceCountryObject.getArmies() == 1) {
				break;
			}
			int numberOfNeighbours = player.checkNeighboringAttackableCountriesAndArmies(sourceCountryObject, player)
					.size();
			if (sourceCountryObject.getArmies() > 1 && numberOfNeighbours > 0) {
				destinationCountryObject = player
						.checkNeighboringAttackableCountriesAndArmies(sourceCountryObject, player).get(0);
				player.setPhase("Source Country : " + sourceCountryObject.getCountryName());
				player.setPhase("Destination Country : " + destinationCountryObject.getCountryName());
				maximumAttackerDice = player.getMaxAttackerDiceCount(sourceCountryObject.getArmies());
				maximumDefenderDice = player.getMaxDefenderDiceCount(destinationCountryObject.getArmies());
				System.out.println();
				player.setPhase(
						"###### The maximum number of dice attacker can roll is  #### : " + maximumAttackerDice);
				player.setPhase(
						"###### The maximum number of dice defender can roll is  #### : " + maximumDefenderDice);
				attackerDiceResults = dice.rollDice(maximumAttackerDice);
				defenderDiceResults = dice.rollDice(maximumDefenderDice);
				player.setPhase("Attacker Dice Roll results : " + attackerDiceResults.size() + " dice has been rolled");
				for (Integer result : attackerDiceResults) {
					player.setPhase(result + " ");
				}
				player.setPhase("Defender Dice Roll results" + defenderDiceResults.size() + " dice has been rolled");
				for (Integer result : defenderDiceResults) {
					player.setPhase(result + " ");
				}
				Collections.sort(attackerDiceResults);
				Collections.reverse(attackerDiceResults);
				Collections.sort(defenderDiceResults);
				Collections.reverse(defenderDiceResults);
				int minimumDiceValue = maximumAttackerDice < maximumDefenderDice ? maximumAttackerDice
						: maximumDefenderDice;
				for (int i = 0; i < minimumDiceValue; i++) {
					if (attackerDiceResults.get(i) != null && defenderDiceResults.get(i) != null) {
						player.setPhase("Result number " + (i + 1));
						player.setPhase("Attacker Dice value " + attackerDiceResults.get(i));
						player.setPhase("Defender Dice value " + defenderDiceResults.get(i));
						if (attackerDiceResults.get(i) > defenderDiceResults.get(i)) {
							player.setPhase("Attacker wins this battle");
							destinationCountryObject.setArmies(destinationCountryObject.getArmies() - 1);
						} else {
							player.setPhase("Defender wins this battle");
							sourceCountryObject.setArmies(sourceCountryObject.getArmies() - 1);
						}
					} else {
						break;
					}

					player.setPhase("Number of armies in " + sourceCountryObject.getCountryName() + " is "
							+ sourceCountryObject.getArmies());
					player.setPhase("Number of armies in " + destinationCountryObject.getCountryName() + " is "
							+ destinationCountryObject.getArmies());
				}
				if (destinationCountryObject.getArmies() < 1) {
					playerLosesTheCountry(sourceCountryObject, destinationCountryObject, player);
					player.printAllCountriesOfaPlayer(sourceCountryObject.getBelongsToPlayer());
				}
				if (player.hasPlayerWon(player)) {
					player.setHasWon(true);
					player.setPhase("Player " + player.getName() + "has won the game");
					return player;
				}
				player.checkPlayerTurnCanContinue(player);
			}
		}
		player.setPhase("Attack Phase has ended");
		player.printAllCountriesOfaPlayer(player);
		checkPlayerTurnCanContinue(player);
		player.setCardGiven(false);
		return player;
	}

	/**
	 * This Method returns the strongest country object with attackable neighbors of
	 * a Player.
	 * 
	 * @param player player It takes input of type player model
	 * @return returns the attackable country
	 */
	private Country getStrongestAttackableCountry(Player player) {
		List<Country> playerOwnedCountries = player.getAssignedCountries();
		Country strongestCountry = null;
		int armyCount = 0;
		for (Country country : playerOwnedCountries) {
			if (country.getArmies() > armyCount
					&& player.checkNeighboringAttackableCountriesAndArmies(country, player).size() > 0) {
				armyCount = country.getArmies();
				strongestCountry = country;
			}
		}
		return strongestCountry;
	}

	/**
	 * This method transfer country from one player to another player when player
	 * losses the country
	 * 
	 * @param sourceCountryObject      Source Country Object
	 * @param destinationCountryObject destination country object
	 * @param player                   player object
	 */
	public void playerLosesTheCountry(Country sourceCountryObject, Country destinationCountryObject, Player player) {
		destinationCountryObject.getBelongsToPlayer().getAssignedCountries().remove(destinationCountryObject);
		sourceCountryObject.getBelongsToPlayer().getAssignedCountries().add(destinationCountryObject);
		Deck deck = Deck.getInstance();
		if (!player.isCardGiven()) {
			if (player.getCardList() != null && !deck.deckOfCards.isEmpty()) {

				Card card = deck.draw();
				player.getCardList().add(card);
				player.setCardGiven(true);
			}
		}
		if (destinationCountryObject.getBelongsToPlayer().getAssignedCountries().size() == 0) {
			playerHasLost(sourceCountryObject, destinationCountryObject);
		}
		destinationCountryObject.setBelongsToPlayer(sourceCountryObject.getBelongsToPlayer());
		int movableArmies = sourceCountryObject.getArmies() - 1;
		if (movableArmies > 0) {
			sourceCountryObject.setArmies(1);
			destinationCountryObject.setArmies(destinationCountryObject.getArmies() + movableArmies);
		}
	}

	/**
	 * This method do needed operations after player has lost
	 * 
	 * @param sourceCountryObject      source country object
	 * @param destinationCountryObject destination country object
	 */
	public void playerHasLost(Country sourceCountryObject, Country destinationCountryObject) {
		destinationCountryObject.getBelongsToPlayer().setHasLost(true);
		destinationCountryObject.getBelongsToPlayer().setEndGameForThisPlayer(true);
		List<Card> listOfDefenderCards = destinationCountryObject.getBelongsToPlayer().getCardList();
		for (Card card : listOfDefenderCards)
			sourceCountryObject.getBelongsToPlayer().getCardList().add(card);
		destinationCountryObject.getBelongsToPlayer().setCardList(new ArrayList<Card>());
	}

	/**
	 * This method implements the fortify phase of the player
	 * 
	 * @param player player object
	 * @return return player object after fortifying
	 */
	@Override
	public Player forfeitPhase(Player player) {
		player.setCurrentPhase(Player.fortificationPhase);
		player.setDomination();
		player.setPhase(player.getName() + " is in Forfeit phase ");
		player.setPhase("#### Aggressive Player Forfeit Phase");
		player.setPhase("#### Before Fortification ####");
		player.printAllCountriesOfaPlayer(player);
		boolean fortificationDone = false;
		List<Country> sortedCountryList = getSortedCountryListBasedOnArmy(player);
		Country sourceCountry = null;
		Country destinationCountry = null;
		int countriesSize = sortedCountryList.size();
		while (!fortificationDone && countriesSize > 0) {
			destinationCountry = sortedCountryList.get(countriesSize - 1);
			if (player.checkNeighboringAttackableCountriesAndArmies(destinationCountry, player) != null
					&& player.checkNeighboringAttackableCountriesAndArmies(destinationCountry, player).size() > 0) {
				for (Country country2 : destinationCountry.getNeighbouringCountries()) {
					sourceCountry = player.getSourceCountryFromPlayerUsingString(country2.getCountryName(), player);
					if (sourceCountry != null && sourceCountry.getArmies() > 1) {
						fortificationDone = true;
						break;
					}
				}
			}
			countriesSize--;
		}

		if (sourceCountry == null || destinationCountry == null || !fortificationDone) {
			player.setPhase("#### Fortification not possible####");
			player.setPhase("#### After Fortification ####");
			player.printAllCountriesOfaPlayer(player);
			return player;
		}
		player.setPhase("					###########    Source country      	     ###############       : "
				+ sourceCountry.getCountryName());
		player.setPhase("					###########  Destination Country   	 ###############       : "
				+ destinationCountry.getCountryName());
		player.setPhase("					############   Armies to be moved    ###############      : "
				+ (sourceCountry.getArmies() - 1));
		destinationCountry.setArmies(destinationCountry.getArmies() + sourceCountry.getArmies() - 1);
		sourceCountry.setArmies(1);
		player.setPhase("#### After Fortification ####");
		player.printAllCountriesOfaPlayer(player);
		return player;
	}

	/**
	 * This method is to sort the country list of a player based on the army count
	 * of the countries in ascending order
	 * 
	 * @param player the player object is passed
	 * @return a sorted list of countries
	 */
	public List<Country> getSortedCountryListBasedOnArmy(Player player) {
		List<Integer> armiesList = new ArrayList<>();
		for (Country country : player.getAssignedCountries()) {
			Country playerCountry = player.getSourceCountryFromString(country.getCountryName());
			armiesList.add(playerCountry.getArmies());
		}
		List<Country> sortedCountryList = new ArrayList<>();
		Collections.sort(armiesList);
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
			if (c.getArmies() > 1 && player.checkNeighboringAttackableCountriesAndArmies(c, player) != null
					&& !player.checkNeighboringAttackableCountriesAndArmies(c, player).isEmpty()) {
				player.setCanAttack(true);
				break;
			}
		}
		for (Country c : player.getAssignedCountries()) {
			player.setCanFortify(false);
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
