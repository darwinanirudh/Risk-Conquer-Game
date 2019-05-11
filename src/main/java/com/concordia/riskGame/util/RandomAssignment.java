package com.concordia.riskGame.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.concordia.riskGame.model.Continent.Continent;
import com.concordia.riskGame.model.Country.Country;
import com.concordia.riskGame.model.Player.AggresivePlayer;
import com.concordia.riskGame.model.Player.BenevolentPlayer;
import com.concordia.riskGame.model.Player.CheaterPlayer;
import com.concordia.riskGame.model.Player.Player;
import com.concordia.riskGame.model.Player.RandomPlayer;

/**
 * This class assigns countries randomly to the different players
 * 
 * @author d_modi
 */
public class RandomAssignment {

	public static String PLAYER = "Player";

	/**
	 * This method sets countries randomly assign to the players
	 * 
	 * @param noOfPlayers number of players
	 * @param countryList List of Countries
	 * @param playerType  type of player he is
	 * @return player Player object
	 */
	public Player randonAssignmentMethod(int noOfPlayers, List<Country> countryList,
			HashMap<String, String> playerType) {
		int noOfCountries = countryList.size();
		System.out.println("Players : " + noOfPlayers + " Countries : " + noOfCountries);
		List<Player> playersList = new ArrayList<>();
		for (int i = 0; i < noOfPlayers; i++) {
			Player playerObject = new Player("Player - " + (i + 1));
			String type = playerType.get(PLAYER + (i + 1));
			switch (type) {
			case "Aggressive":
				playerObject.setStrategy(new AggresivePlayer());
				playerObject.setComputerPlayer(true);
				break;
			case "Benevolent":
				playerObject.setStrategy(new BenevolentPlayer());
				playerObject.setComputerPlayer(true);
				break;
			case "Random":
				playerObject.setStrategy(new RandomPlayer());
				playerObject.setComputerPlayer(true);
				break;
			case "Cheater":
				playerObject.setStrategy(new CheaterPlayer());
				playerObject.setComputerPlayer(true);
				break;
			default:
				playerObject.setStrategy(new Player());
			}
			playersList.add(playerObject);
		}

		playersList.sort(Comparator.comparing(Player::getName));

		RandomAssignment inputObject = new RandomAssignment();
		int[] dividedValuesList = inputObject.divider(noOfCountries, noOfPlayers);
		List<Country> newCountryList = new ArrayList<>(countryList);
		dividedValuesList = inputObject.divider(noOfCountries, noOfPlayers);
		Map<Player, List<Country>> playerAssign = new HashMap<>();
		newCountryList = new ArrayList<>(countryList);
		Player player = new Player();
		int k = 0;
		for (int i = 0; i < noOfPlayers; i++) {
			List<Country> countryList1 = new ArrayList<>();
			for (int j = 0; j < dividedValuesList[i]; j++) {
				int rando = (int) ((Math.random() * newCountryList.size()));
				Country tempCountry = newCountryList.get(rando);
				tempCountry.setBelongsToPlayer(playersList.get(i));
				countryList1.add(tempCountry);
				newCountryList.remove(rando);
			}
			playersList.get(i).setAssignedCountries(countryList1);
			playerAssign.put(playersList.get(i), countryList1);
		}
		player.setPlayerAssign(playerAssign);

		return player;
	}

	/**
	 * This method divides a number into smaller numbers
	 * 
	 * @param number number of countries
	 * @param parts  number of players
	 * @return randoms array of divided numbers
	 */
	int[] divider(int number, int parts) {
		int[] randoms = new int[parts];
		Arrays.fill(randoms, (number / parts));
		int remainder = number - (number / parts) * parts;
		Random random = new Random();
		for (int i = 0; i < parts - 1 && remainder > 0; ++i) {
			int diff = random.nextInt(remainder);
			randoms[i] += diff;
			remainder -= diff;
		}
		randoms[parts - 1] += remainder;
		Arrays.sort(randoms);
		for (int i = 0, j = parts - 1; i < j; ++i, --j) {
			int temp = randoms[i];
			randoms[i] = randoms[j];
			randoms[j] = temp;
		}
		return randoms;
	}
}
