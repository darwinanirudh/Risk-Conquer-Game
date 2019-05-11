package com.concordia.riskGame.model.Map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;
import com.concordia.riskGame.View.GameLauncher;
import com.concordia.riskGame.View.MapEditView;
import com.concordia.riskGame.control.GameDriver;
import com.concordia.riskGame.exception.InvalidMapFileException;
import com.concordia.riskGame.model.Continent.Continent;
import com.concordia.riskGame.model.Country.Country;
import com.concordia.riskGame.model.Player.Player;
import com.concordia.riskGame.util.MapValidator;
import com.concordia.riskGame.util.RandomAssignment;

/**
 * This Class has the implementation of reading the .map file and setting
 * countryAndNeighbors and continentAndItsCountries fields in the MapContents
 * class
 * 
 * @author Dheeraj As - Team 13
 * @author Darwin Anirudh G - Team 13
 */
public class MapParseProcessor implements Serializable {

	private static final long serialversionUID = 1L;

	private File file;
	private String currentLine;
	private int labelCount;
	private String filePath;
	private File fileObject;
	private BufferedReader bufferReaderForFile;
	private MapContents mapContentsObj;
	private String mapAuthorName;
	private int numberOfPlayers;
	private String[] splitUtllityString;
	private List<Continent> contitentList;
	private List<Country> countryList;
	private List<Country> adjCountry;
	private Continent continentObejct;
	private Country countryObject;
	private MapContents mapContentObject;
	private HashMap<Country, List<Country>> countryAndNeighbors;
	private HashMap<Continent, List<Country>> continentAndItsCountries;
	private GameDriver gameDriverObject;
	private Player playerObject;
	private List<Player> playerList;
	private int initialArmies;
	private MapEditView mapEditView;
	private MapValidator mapValidator;
	private List<Player> playerListClone;
	private Random randomObject;
	private List<Country> countryList2 = new CopyOnWriteArrayList();

	/**
	 * This method reads the map file and set the corresponding continents and
	 * countries in their respective entity classes.
	 * 
	 * @param filePath       Path of the map file
	 * @param numberOfPlayer Number of players returned from the drop down menu
	 * @param playerType     Type of player (human, aggressive.. etc)
	 * @param gameType       Type of game that is being played
	 * @return the mapContent Object with map details enriched
	 */

	public MapContents mapParser(String filePath, String numberOfPlayer, HashMap<String, String> playerType,
			String gameType) {
		try {

			fileObject = new File(filePath);
			numberOfPlayers = Integer.parseInt(numberOfPlayer);
			System.out.println("File Path " + filePath);
			bufferReaderForFile = new BufferedReader(new FileReader(fileObject));
			mapValidator = new MapValidator();
			mapValidator.init(fileObject);

			if (!mapValidator.getValidMapFlag()) {
				throw new InvalidMapFileException("Invalid Map File");
			}

			readMapElements(bufferReaderForFile);
			setcontitentAndCountriesMap();
			MapContents.setMapContents(null);
			mapContentObject = MapContents.getInstance();
			mapContentObject.setContinentAndItsCountries(continentAndItsCountries);
			mapContentObject.setCountryAndNeighbors(countryAndNeighbors);
			mapContentObject.setCountryList(countryList);
			RandomAssignment randonAssignment = new RandomAssignment();
			playerObject = randonAssignment.randonAssignmentMethod(numberOfPlayers, countryList, playerType);
			initialArmies = initialArmyAssignment(numberOfPlayers);
			playerList = new ArrayList();

			Set<Player> setplayer = playerObject.getPlayerAssign().keySet();
			List<Player> sortedPlayerList = new ArrayList<Player>(setplayer);
			sortedPlayerList.sort(Comparator.comparing(Player::getName));

			for (Player key : sortedPlayerList) {
				System.out.println("Player Name : " + key.getName());
				System.out.println("     Assigned no Of Countries  :" + key.getAssignedCountries().size());
				key.setTotalArmies(initialArmies);
				System.out.println("     Assigned Armies            :" + initialArmies);
				for (int i = 0; i < key.getAssignedCountries().size(); i++) {
					System.out.println("     Assigned Of Countries : "
							+ playerObject.getPlayerAssign().get(key).get(i).getCountryName());
				}
				playerList.add(key);
				System.out.println("");
			}

			armyAssignment(playerList);
			mapContentObject.setPlayerList(playerList);
			if (!gameType.equals("tournament")) {
				gameDriverObject = new GameDriver();
				gameDriverObject.gamePhase();
			}
		} catch (InvalidMapFileException e) {
			System.out.println("Please select a valid map");
			GameLauncher gameLauncherObject = new GameLauncher();
		} catch (Exception e) {
			System.out.println("Error Message : " + e.getMessage());
			e.printStackTrace();
		}
		return mapContentObject;
	}

	/**
	 * This method get the number of players and returns initial armies
	 * 
	 * @param intialArmies initial number of army assigned.
	 * 
	 * @return The initial army assignment number
	 */
	public int initialArmyAssignment(int intialArmies) {

		if (intialArmies == 2)
			initialArmies = 40;
		else if (intialArmies == 3)
			initialArmies = 35;
		else if (intialArmies == 4)
			initialArmies = 30;
		else if (intialArmies == 5)
			initialArmies = 25;
		else if (intialArmies == 6)
			initialArmies = 20;
		else
			initialArmies = 15;

		return initialArmies;
	}

	/**
	 * The following method reads the file to be edited and edit the map.
	 * 
	 * @param filePath Path of the map file.
	 */
	public void editMapParsermapParser(String filePath) {

		try {
			fileObject = new File(filePath);
			System.out.println("File Path is  " + filePath);
			bufferReaderForFile = new BufferedReader(new FileReader(fileObject));
			mapValidator = new MapValidator();
			mapValidator.init(fileObject);
			readMapElements(bufferReaderForFile);
			setcontitentAndCountriesMap();
			MapContents.setMapContents(null);
			mapContentObject = MapContents.getInstance();
			mapContentObject.setContinentAndItsCountries(continentAndItsCountries);
			mapContentObject.setCountryAndNeighbors(countryAndNeighbors);
			mapEditView = new MapEditView();
			mapEditView.MapDefinition(countryAndNeighbors, continentAndItsCountries, filePath);
		} catch (FileNotFoundException e) {
			System.out.println("Error Message : " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error Message : " + e.getMessage());
		}
	}

	/**
	 * This method reads the selected map's elements
	 * 
	 * @param bufferReader BufferedReader to read text from the file
	 * @return It retuns of type mapcontent object
	 */
	public MapContents readMapElements(BufferedReader bufferReader) {
		try {
			contitentList = new ArrayList<>();
			countryList = new ArrayList<>();
			countryAndNeighbors = new HashMap<>();
			labelCount = 0;
			while ((currentLine = bufferReader.readLine()) != null) {
				if (currentLine.contains("[Map]")) {
					labelCount++;
					while ((currentLine = bufferReader.readLine()) != null && !currentLine.contains("[")) {
						if (currentLine.contains("author")) {
							splitUtllityString = currentLine.split("=");
							System.out.println("splitUtllityString :" + splitUtllityString[1]);
							mapAuthorName = splitUtllityString[1];
						}
					}
					bufferReader.mark(0);
				}

				bufferReader.reset();

				if (currentLine.contains("[Continents]")) {
					labelCount++;
					while ((currentLine = bufferReader.readLine()) != null && !currentLine.contains("[")) {
						if (!currentLine.isEmpty()) {
							String[] continentValues = currentLine.split("=");
							Continent continentObject = new Continent(continentValues[0],
									Integer.parseInt(continentValues[1]));
							contitentList.add(continentObject);
						}
					}
					bufferReader.mark(0);
				}

				bufferReader.reset();

				if (currentLine.contains("[Territories]")) {
					labelCount++;
					while ((currentLine = bufferReader.readLine()) != null) {
						if (!currentLine.isEmpty()) {
							String[] territoryValues = currentLine.split(",", 2);
							String[] adjCountries = territoryValues[1].split(",", 2);
							String[] adjCountries2 = adjCountries[1].split(",", 2);
							String[] adjCountries3 = adjCountries2[1].split(",", 2);
							Country cc = new Country(territoryValues[0], adjCountries3[0]);
							adjCountry = new ArrayList<Country>();

							StringTokenizer st = null;
							if (adjCountries3.length > 1)
								st = new StringTokenizer(adjCountries3[1], ", ");

							while (st != null && st.hasMoreTokens()) {
								Country adjC = new Country(st.nextToken(","));
								adjCountry.add(adjC);
							}
							cc.setNeighbouringCountries(adjCountry);
							countryList.add(cc);
							countryAndNeighbors.put(cc, adjCountry);
						}
					}
				}
			}

			MapContents.setMapContents(null);
			mapContentObject = MapContents.getInstance();
			setcontitentAndCountriesMap();
			mapContentObject.setContinentAndItsCountries(continentAndItsCountries);
			mapContentObject.setCountryAndNeighbors(countryAndNeighbors);
			mapContentObject.setLabelCount(labelCount);

		} catch (Exception e) {
			System.out.println("Error Message : " + e.getMessage());
			e.printStackTrace();

		}
		return mapContentObject;

	}

	/**
	 * Method prints out the countries and its neighbours.
	 */
	public void countryAndNeighboursMap() {
		try {
			System.out.println("######  countryAndNeighbors Map size     #############" + countryAndNeighbors.size());
			for (Map.Entry<Country, List<Country>> entry : countryAndNeighbors.entrySet()) {
				System.out.println("##### Key is ##### :" + entry.getKey().getCountryName() + "value size is   "
						+ entry.getValue().size());
			}
		} catch (Exception e) {
			System.out.println("Error Message : " + e.getMessage());
		}
	}

	/**
	 * This method set Continent and its respective countries Map
	 */
	public void setcontitentAndCountriesMap() {
		try {
			System.out.println("##### Reading countryList ######");
			continentAndItsCountries = new HashMap<>();
			Collections.shuffle(contitentList);
			for (Continent continentInstance : contitentList) {
				System.out.println("Continent Name is " + continentInstance.getContinentName());
				Continent contientObj = new Continent();
				contientObj = continentInstance;
				List<Country> counList = new ArrayList<Country>();
				for (Country countryInstance : countryList) {
					if (countryInstance.getBelongsToContinent().equals(continentInstance.getContinentName())) {
						counList.add(countryInstance);
					}
				}
				continentAndItsCountries.put(contientObj, counList);
			}

			for (Map.Entry<Continent, List<Country>> entry : continentAndItsCountries.entrySet()) {
				System.out.println("##### Key is ##### :" + entry.getKey().getContinentName()
						+ "### value size is   ### :" + entry.getValue().size());
			}
		} catch (Exception e) {
			System.out.println("Error Message : " + e.getMessage());
		}
	}

	/**
	 * This method gets the List of countries for the corresponding continent.
	 *
	 * @param continentName Continent Name
	 * @return Returns list of Country Objects.
	 */
	public List<Country> getContinentCountries(String continentName) {

		try {
			List<Country> countriesList = new ArrayList<Country>();
			for (int i = 0; i < countryList.size(); i++) {
				if (continentName.equals(countryList.get(i).getBelongsToContinent())) {
					System.out
							.println("###### Country name is ###########      :" + countryList.get(i).getCountryName());
					countriesList.add(countryList.get(i));

				}
			}
			return countriesList;
		} catch (Exception e) {
			System.out.println("Error Message : " + e.getMessage());
			return null;
		}
	}

	/**
	 * The following method assigns the initial number of armies to each player
	 * 
	 * @param listPlayer List of Player Instances.
	 */

	public void armyAssignment(List<Player> listPlayer) {

		playerListClone = new ArrayList();
		List<Country> assignedCountry = new ArrayList();
		int assignedArmies;

		for (Player player : listPlayer) {

			System.out.println("                                               ");
			System.out.println("######### The name of the player is ######### :" + player.getName());
			System.out.println("    ");
			System.out.println("######### The assigned armies are  #########  :" + player.getTotalArmies());
			Player playerObject = new Player();
			playerObject = player;
			assignedArmies = playerObject.getTotalArmies();
			assignedCountry = new ArrayList();
			for (Country countryInstance : playerObject.getAssignedCountries()) {
				countryInstance.setArmies(1);
				assignedArmies = assignedArmies - 1;
				assignedCountry.add(countryInstance);

			}

			while (assignedArmies > 0) {
				int index = getRandomNumber(0, playerObject.getAssignedCountries().size());
				Country c = new Country();
				c = assignedCountry.get(index);
				int armies = c.getArmies();
				armies = armies + 1;
				c.setArmies(armies);
				assignedArmies = assignedArmies - 1;
				Collections.replaceAll(assignedCountry, assignedCountry.get(index), c);
			}

			playerObject.setAssignedCountries(assignedCountry);
			playerObject.setTotalArmies(assignedArmies);
			playerListClone.add(playerObject);
		}

		for (Player playerObj : listPlayer) {
			System.out.println("######### The name of the player is #########  :" + playerObj.getName());
			for (Country countryObject : playerObj.getAssignedCountries()) {
				System.out.println("###### The name of the country is    ##### :" + countryObject.getCountryName());
				System.out.println("###### The number of assigned armies ##### :" + countryObject.getArmies());
			}

		}

	}

	/**
	 * The following method is used to random number
	 * 
	 * @param start start range of the random number
	 * @param end   end range of the random number
	 * @return Random Integer.
	 */

	public int getRandomNumber(int start, int end) {
		randomObject = new Random();

		return randomObject.nextInt(end - start) + start;
	}

	/**
	 * Call the driver for the load game.
	 * 
	 * @param mpcontObject mapcontent object
	 */
	public void callGamePhase(MapContents mpcontObject) {
		try {
			gameDriverObject.load(mpcontObject);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
