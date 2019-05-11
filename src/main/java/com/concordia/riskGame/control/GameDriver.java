package com.concordia.riskGame.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;

import com.concordia.riskGame.View.CardView;
import com.concordia.riskGame.View.PhaseView;
import com.concordia.riskGame.View.WorldDominationView;
import com.concordia.riskGame.model.Card.Deck;
import com.concordia.riskGame.model.Country.Country;
import com.concordia.riskGame.model.Map.MapContents;
import com.concordia.riskGame.model.Player.Player;
import com.concordia.riskGame.model.save.RiskSaveGame;

/**
 * This class implements the Game phases in a round robin fashion
 * 
 * @author Sande
 *
 */
public class GameDriver {

	private Scanner scanner;
	private String[] nameArmiesSpilt;
	private String countryName;
	private String armiesCount;
	private List<Player> gmPlayerList;
	private HashMap<Country, List<Country>> gmcountryAndNeighbours;
	private List<Player> updatedPlayerList;
	private int assignedArmies;
	private boolean endTheGame;
	private MapContents mapContents;
	private RiskSaveGame riskSaveGameObject;
	private int roundCounter = 0;
	private int roundCounterLoad = 0;

	/**
	 * The following method calls each of the game phase for each player.
	 * 
	 * @throws Exception throws exception
	 */
	public void gamePhase() throws Exception {
		MapContents mapContents = MapContents.getInstance();
		gmcountryAndNeighbours = new HashMap<Country, List<Country>>();
		gmcountryAndNeighbours = mapContents.getCountryAndNeighbors();
		scanner = new Scanner(System.in);
		updatedPlayerList = new ArrayList<Player>();
		endTheGame = false;

		Deck deck = Deck.getInstance();
		deck.setDeckOfCards(mapContents.getCountryList());
		while (!endTheGame) {
			List<Player> removablePlayers = new ArrayList<>();
			for (Player player : mapContents.getPlayerList()) {
				if (player.isHasLost()) {
					removablePlayers.add(player);
				}
			}
			mapContents.getPlayerList().removeAll(removablePlayers);

			/* Iterator<Player> iterator = mapContents.getPlayerList().iterator(); */
			ListIterator<Player> iter = mapContents.getPlayerList().listIterator();
			while (iter.hasNext()) {
				// Player playerInstance = new Player();
				Player p = iter.next();
				if (!p.isHasLost()) {
					p = p.strategy.reinforcePhase(p);
					if (p.getCanAttack()) {
						p = p.strategy.attackPhase(p);
					}
					if (p.getHasWon()) {
						System.exit(0);
					}
					if (p.getCanFortify()) {
						p = p.strategy.forfeitPhase(p);
					}
				}

				roundCounter = roundCounter + 1;
				if (roundCounter % 2 == 0) {
					System.out.println("##### Saving the Game . . . . .  #######");
					int rotateCount;
					rotateCount = iter.nextIndex();
					MapContents mapContentObject = MapContents.getInstance();
					mapContentObject.setRotateCount(rotateCount);
					System.out.println("######### The rotate value is ###### : " + rotateCount);

					riskSaveGameObject = new RiskSaveGame();
					riskSaveGameObject.saveGame(mapContentObject);

				}
			}
		}
		if (endTheGame) {
			System.exit(0);
		}

		System.out.println("######## Do you want to exit : yes  #########");
		String choice = scanner.nextLine();

		if (choice.equalsIgnoreCase("yes")) {
			System.exit(0);
		}
		gamePhase();

	}

	/**
	 * Checks for player attack possibility based on source and destination
	 * countries
	 * 
	 * @param player Accept the player object as Input
	 * @return It returns the countries list
	 * @throws Exception throws exception
	 */
	public List<Country> getSourceAndDestinationCountry(Player player) throws Exception {
		String sourceCountry;
		Country sourceCountryObject;
		String destinationCountry;
		Country destinationCountryObject;
		List<Country> attackableCountryList;
		List<Country> sourceAndDestinationCountry = new ArrayList<>();
		System.out.println("#### List of countries owned by the player #####");

		try {
			for (Country countryObj : player.getAssignedCountries()) {
				Country country = getSourceCountryFromString(countryObj.getCountryName());
				System.out.println(country.getCountryName() + " : " + country.getArmies());
			}
			scanner = new Scanner(System.in);
			System.out.println(
					"Enter the name of the country through which you want to attack or enter 'quit' to exit the attack");
			sourceCountry = scanner.nextLine();
			if (sourceCountry.equals("quit")) {
				return null;
			}
			sourceCountryObject = player.getSourceCountryFromPlayerUsingString(sourceCountry, player);
			if (sourceCountryObject == null) {
				System.out.println(
						"The country with the given name is not owned by the player. Please reenter the country");
				sourceCountryObject = player.reenterTheCountry(player);
			}
			if (sourceCountryObject == null) {
				return null;
			}
			System.out.println("Number of armies in " + sourceCountryObject.getCountryName() + " : "
					+ sourceCountryObject.getArmies());
			while (sourceCountryObject.getArmies() == 1) {
				System.out.println("Attack not possible as the country has only 1 army. Please reenter the country");
				sourceCountryObject = player.reenterTheCountry(player);
			}
			System.out.println("#### The neighbouring attackable countries are #####");
			attackableCountryList = player.printNeighboringAttackableCountriesAndArmies(sourceCountryObject, player);
			if (attackableCountryList == null || attackableCountryList.isEmpty()) {
				System.out.println("Attack not possible as there are no neighboring countries.");
				throw new Exception();
			}
			System.out.println(
					"Enter the name of the country on which you want to attack or enter 'quit' to exit the attack");
			destinationCountry = scanner.nextLine();
			if (destinationCountry.equals("quit")) {
				return null;
			}
			destinationCountryObject = player.getAttackableCountryOfCountryListFromString(destinationCountry,
					attackableCountryList);
			while (destinationCountryObject == null || !attackableCountryList.contains(destinationCountryObject)) {
				System.out.println("The country with the given name is not in the list or the country does not exist");
				destinationCountryObject = player.reenterTheDestinationCountry(attackableCountryList);
			}

			if (destinationCountryObject.getCountryName().equals("quit")) {
				return null;
			}
			sourceAndDestinationCountry.add(sourceCountryObject);
			sourceAndDestinationCountry.add(destinationCountryObject);

		} catch (Exception e) {
			sourceAndDestinationCountry = getSourceAndDestinationCountry(player);
		}
		return sourceAndDestinationCountry;

	}

	/**
	 * The following checks if the string is number or not
	 * 
	 * @param str String value is passed which is used to check as number or not.
	 * @return Returns true if the string is a number else it returns false.
	 */
	public boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}

	/**
	 * The following method checks the countries passed in the forfeit phase are
	 * neighbour or not.
	 * 
	 * @param sourceCountry The country from which armies have to moved.
	 * @param destCountry   The country which receives armies.
	 * @return returns true if both the countries are countries else it returns
	 *         false.
	 */
	public boolean isNeighbour(String sourceCountry, String destCountry) {
		System.out.println("##### Checking the country, if its a neighbour of the country or not #####");
		System.out.println("##### Source country is        : ##### :" + sourceCountry);
		System.out.println("##### Destination country is : ##### :" + destCountry);
		List<Country> connectedCountries = new ArrayList<Country>();
		boolean returnValue = false;
		for (Map.Entry<Country, List<Country>> entry : gmcountryAndNeighbours.entrySet()) {
			if (entry.getKey().getCountryName().equalsIgnoreCase(sourceCountry)) {
				connectedCountries = entry.getValue();
				for (Country countryInstance : connectedCountries) {
					if (countryInstance.getCountryName().equalsIgnoreCase(destCountry)) {
						System.out.println("#### Country is a neighbour ######");
						returnValue = true;
					}
				}
			}
		}
		return returnValue;
	}

	/**
	 * Method to Calculate Number of armies in reinforcement Phase
	 * 
	 * @param numberOfCountriesOwned Number of countries owned.
	 * @return number of reinforced armies
	 */
	public int calculateReiforcementArmies(int numberOfCountriesOwned) {
		int armiesToAssign;

		armiesToAssign = numberOfCountriesOwned / 3;
		if (armiesToAssign < 3) {
			armiesToAssign = 3;
		}

		return armiesToAssign;
	}

	/**
	 * The following method displays the countries assigned to a player.
	 * 
	 * @param player The instance of the player is taken as the parameter.
	 */
	public void printCountriesOwnedByPlayer(Player player) {
		System.out.println("### Countries Owned by the player are ##### :");
		for (Country country : player.getAssignedCountries()) {
			System.out.print(country.getCountryName() + " ,");
		}

	}

	/**
	 * The method is driver method for the loaded game from file.
	 * 
	 * @param mapContentOb The mapContent Object.
	 * @throws Exception if there is an error
	 */
	public void load(MapContents mapContentOb) throws Exception {

		System.out.println("##########  load is Called #######");
		MapContents.setMapContents(mapContentOb);
		mapContents = MapContents.getInstance();
		gmcountryAndNeighbours = new HashMap<Country, List<Country>>();
		gmcountryAndNeighbours = mapContents.getCountryAndNeighbors();
		scanner = new Scanner(System.in);
		updatedPlayerList = new ArrayList<Player>();
		endTheGame = false;
		try {
			Deck deck = Deck.getInstance();
			deck.setDeckOfCards(mapContents.getCountryList());
			while (!endTheGame) {
				List<Player> removablePlayers = new ArrayList<>();
				for (Player player : mapContents.getPlayerList()) {
					PhaseView phaseView = new PhaseView();
					player.addObserver(phaseView);
					WorldDominationView dominationView = new WorldDominationView();
					player.addObserver(dominationView);
					CardView cardView = new CardView();
					player.addObserver(cardView);
					if (player.isHasLost()) {
						removablePlayers.add(player);
					}
				}
				mapContents.getPlayerList().removeAll(removablePlayers);
				ListIterator<Player> iter = mapContents.getPlayerList().listIterator();
				while (iter.hasNext()) {
					Player playerInstance = new Player();
					Player p = iter.next();
					if (!p.isHasLost()) {
						playerInstance = p.getStrategy().reinforcePhase(p);
						if (playerInstance.getCanAttack()) {
							playerInstance = playerInstance.getStrategy().attackPhase(playerInstance);
						}
						if (playerInstance.getHasWon()) {
							System.exit(0);
						}
						if (playerInstance.getCanFortify()) {
							playerInstance = playerInstance.getStrategy().forfeitPhase(playerInstance);
						}
					}
				}

				roundCounterLoad = roundCounterLoad + 1;
				if (roundCounterLoad % 2 == 0) {
					System.out.println("##### Saving the Game . . . . .  #######");
					int rotateCount;
					rotateCount = iter.nextIndex();
					MapContents mapContentObject = MapContents.getInstance();
					System.out.println("######### The rotate value is ###### : " + rotateCount);
					mapContentObject.setRotateCount(rotateCount);
					riskSaveGameObject = new RiskSaveGame();
					riskSaveGameObject.saveGame(mapContentObject);
					mapContentObject.setRotateCount(0);
				}

			}
			if (endTheGame) {
				System.exit(0);
			}
			System.out.println("######## Do you want to exit : yes  #########");
			String choice = scanner.nextLine();
			if (choice.equalsIgnoreCase("yes")) {
				System.exit(0);
			}
			MapContents mapContentObject = MapContents.getInstance();
			load(mapContentObject);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * To get the country object from the string value of the country
	 * 
	 * @param sourceCountry Pass the source country
	 * @return the country object
	 */
	public Country getSourceCountryFromString(String sourceCountry) {
		MapContents contents = MapContents.getInstance();

		HashMap<Country, List<Country>> countriesAndItsNeighbours = contents.getCountryAndNeighbors();
		for (Country country : countriesAndItsNeighbours.keySet()) {
			if (sourceCountry != null && !sourceCountry.isEmpty() && sourceCountry.equals(country.getCountryName())) {
				return country;
			}
		}
		return null;
	}

}
