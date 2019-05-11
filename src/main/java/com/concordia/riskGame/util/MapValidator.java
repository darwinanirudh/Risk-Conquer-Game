package com.concordia.riskGame.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.concordia.riskGame.model.Continent.Continent;
import com.concordia.riskGame.model.Country.Country;
import com.concordia.riskGame.model.Map.MapContents;
import com.concordia.riskGame.model.Map.MapParseProcessor;
import com.concordia.riskGame.model.Player.Player;

/**
 * This class contains all the methods to validate the map file.
 * 
 * @author d_modi
 */
public class MapValidator {
	private Boolean validMapFlag;
	private String statusMessage;

	/**
	 * Method to check if all the labels are described properly
	 * 
	 * @param mapContents It is of type map Contents Object
	 * @return true if all labels are described properly, otherwise false
	 */
	public boolean checkMapLabel(MapContents mapContents) {
		if (mapContents.getLabelCount() != 3) {
			return false;
		}
		return true;
	}

	/**
	 * Method to check if Country Continent if from Continent list only
	 * 
	 * @param mapContents : MapContents object
	 * @return true if Country Continent is from Continent List, otherwise false
	 */
	public boolean checkContinent(MapContents mapContents) {
		List<String> listContinentName = new ArrayList<>();
		for (Continent continent : mapContents.getContinentAndItsCountries().keySet()) {
			listContinentName.add(continent.getContinentName());
		}
		for (Country country : mapContents.getCountryAndNeighbors().keySet()) {
			if (!listContinentName.contains(country.getBelongsToContinent())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Method to check if map is connected continent graph or not
	 * 
	 * @param mapContinent map of continent and Countries
	 * @param visitedMap   map of Country and visited value, 1 for visited and 0 if
	 *                     not visited
	 * @return true if graph is connected, otherwise false
	 */
	public Map<String, Integer> checkConnectedContinentGraph(Map<Continent, List<Country>> mapContinent,
			Map<String, Integer> visitedMap) {
		visitedMap.put(mapContinent.keySet().iterator().next().getContinentName(), 1);
		for (Entry<Continent, List<Country>> continent : mapContinent.entrySet()) {
			System.out.println("Continent name  : " + continent.getKey().getContinentName());
			for (Country country : continent.getValue()) {
				System.out.println("Country name  : " + country.getCountryName());
				for (Country neighbour : country.getNeighbouringCountries()) {
					Player player = new Player();
					Country n = player.getSourceCountryFromString(neighbour.getCountryName());
					if (!visitedMap.containsKey(n.getBelongsToContinent())
							&& !(n.getBelongsToContinent().equals(country.getBelongsToContinent()))) {
						visitedMap.put(n.getBelongsToContinent(), 1);
					}
				}
			}
		}
		return visitedMap;
	}

	/**
	 * Method to check if all continent have at least one country
	 * 
	 * @param mapContents : MapContents object
	 * @return true if all continent has at least one country, otherwise false
	 */
	public boolean checkContinentCountry(MapContents mapContents) {
		for (Continent continent : mapContents.getContinentAndItsCountries().keySet()) {
			if (mapContents.getContinentAndItsCountries().get(continent).isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Method to check if all continent control value
	 * 
	 * @param mapContents : MapContents object
	 * @return true if all continent has at least one country, otherwise false
	 */
	public boolean checkContinentControlValue(MapContents mapContents) {
		for (Continent continent : mapContents.getContinentAndItsCountries().keySet()) {
			if (continent.getContinentControlValue() < 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Method to check if country is assigned to only one country
	 * 
	 * @param mapContents : MapContents object
	 * @return true if all countries are assigned to only one country, otherwise
	 *         false
	 */
	public boolean checkUniqueContinentCountry(MapContents mapContents) {
		Map<String, String> counrtyContinentValue = new HashMap<>();
		for (Continent continent : mapContents.getContinentAndItsCountries().keySet()) {
			for (Country country : mapContents.getContinentAndItsCountries().get(continent)) {
				if (!counrtyContinentValue.keySet().contains(country.getCountryName())) {
					counrtyContinentValue.put(country.getCountryName(), continent.getContinentName());
				} else {
					if (!continent.getContinentName()
							.equalsIgnoreCase(counrtyContinentValue.get(country.getCountryName()))) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Method to check if map is connected graph or not
	 * 
	 * @param parent     starting node of the graph
	 * @param mapCountry map of Country and neighboring Countries
	 * @param visitedMap map of Country and visited value, 1 for visited and 0 if
	 *                   not visited
	 * @return visitedMap key is continent name and value shows if continent is
	 *         connected or not
	 */
	public Map<String, Integer> checkConnectedGraphForContinent(Country parent, Map<Country, List<Country>> mapCountry,
			Map<String, Integer> visitedMap) {
		List<Country> nbCountries = new ArrayList<>();
		visitedMap.put(parent.getCountryName().trim(), 1);

		for (Map.Entry<Country, List<Country>> entry : mapCountry.entrySet()) {
			if (parent.getCountryName().equalsIgnoreCase(entry.getKey().getCountryName())) {
				nbCountries = entry.getValue();
			}
		}
		if (nbCountries != null && !nbCountries.isEmpty()) {
			for (int i = 0; i < nbCountries.size(); i++) {
				if (!visitedMap.containsKey(nbCountries.get(i).getCountryName().trim())) {

					checkConnectedGraphForContinent(nbCountries.get(i), mapCountry, visitedMap);

				}
			}
		}
		return visitedMap;
	}

	/**
	 * Method to check if all the countries in the continents are connected or not
	 * 
	 * @param mapContents MapContents object
	 * @return true if all continents are connected otherwise false
	 */
	public boolean checkConnectedCountryContinent(MapContents mapContents) {

		Map<Country, List<Country>> listTempCountry = mapContents.getCountryAndNeighbors();

		for (Continent c : mapContents.getContinentAndItsCountries().keySet()) {

			Map<Country, List<Country>> mapCountry = new HashMap<>();
			Map<String, Integer> visitedMapForContinent = new HashMap<>();

			if (mapContents.getContinentAndItsCountries().get(c).size() == 1) {
				System.out.println(
						"Country : " + mapContents.getContinentAndItsCountries().get(c).get(0).getCountryName());
				System.out.println("---------------------------------------------------");
				System.out.println("Continent " + c.getContinentName() + " is Connected");
				System.out.println("---------------------------------------------------");
				visitedMapForContinent.put(c.getContinentName(), 1);
				continue;
			}

			for (Country country : mapContents.getContinentAndItsCountries().get(c)) {
				System.out.println("Country : " + country.getCountryName());
				List<Country> listNbCountry = new ArrayList<>();
				for (Country nbCountry : country.getNeighbouringCountries()) {
					for (Country tempCountry : listTempCountry.keySet()) {
						if (nbCountry.getCountryName().equalsIgnoreCase(tempCountry.getCountryName())) {
							if (country.getBelongsToContinent().equalsIgnoreCase(tempCountry.getBelongsToContinent())) {
								listNbCountry.add(tempCountry);
							}
						}
					}
				}
				mapCountry.put(country, listNbCountry);
			}
			int countireswithNoNbCounty = 0;
			for (Country country : mapCountry.keySet()) {
				for (Country nbCountry : mapCountry.get(country)) {
				}
				if (mapCountry.get(country) == null || mapCountry.get(country).isEmpty()) {
					countireswithNoNbCounty++;
				}
			}

			Iterator<Country> it = mapCountry.keySet().iterator();
			Country next;

			while (it.hasNext()) {
				next = it.next();
				if (visitedMapForContinent.size() != mapCountry.keySet().size()) {
					Map<String, Integer> visitedMap = new HashMap<>();
					visitedMapForContinent = checkConnectedGraphForContinent(next, mapCountry, visitedMap);
				} else {
					break;
				}
			}

			int connectedCountries = 0;
			for (Map.Entry<String, Integer> entry : visitedMapForContinent.entrySet()) {
				if (entry.getValue() == 1) {
					connectedCountries++;
				}
			}

			if (mapContents.getContinentAndItsCountries().get(c).size() == countireswithNoNbCounty) {
				System.out.println("****************************************************");
				System.out.println("Continent " + c.getContinentName() + " is Not Connected");
				System.out.println("****************************************************");
				return false;
			}

			if (connectedCountries != mapCountry.keySet().size()) {
				System.out.println("****************************************************");
				System.out.println("Continent " + c.getContinentName() + " is Not Connected");
				System.out.println("****************************************************");
				return false;
			}
			System.out.println("---------------------------------------------------");
			System.out.println("Continent " + c.getContinentName() + " is Connected");
			System.out.println("---------------------------------------------------");
		}

		return true;
	}

	/**
	 * Method to check if map is connected graph or not
	 * 
	 * @param parent     starting node of the graph
	 * @param mapCountry map of Country and neighboring Countries
	 * @param visitedMap map of Country and visited value, 1 for visited and 0 if
	 *                   not visited
	 * @return visitedMap if graph is connected, otherwise false
	 */
	public Map<String, Integer> checkConnectedGraph(Country parent, Map<Country, List<Country>> mapCountry,
			Map<String, Integer> visitedMap) {
		List<Country> nbCountries = new ArrayList<>();
		visitedMap.put(parent.getCountryName().trim(), 1);
		for (Map.Entry<Country, List<Country>> entry : mapCountry.entrySet()) {
			if (parent.getCountryName().equalsIgnoreCase(entry.getKey().getCountryName())) {
				nbCountries = entry.getValue();
			}
		}
		if (nbCountries != null) {
			for (int i = 0; i < nbCountries.size(); i++) {
				if (!visitedMap.containsKey(nbCountries.get(i).getCountryName().trim())) {
					checkConnectedGraph(nbCountries.get(i), mapCountry, visitedMap);
				}
			}
		}
		return visitedMap;
	}

	/**
	 * Method to check all the output returned by above described method
	 * 
	 * @param file : File Object
	 */
	public void init(File file) {
		MapValidator mapValidator = new MapValidator();
		Map<String, Integer> visitedMap1 = new HashMap<>();
		MapParseProcessor mapParseProcessor = new MapParseProcessor();
		MapContents mapContents = null;
		BufferedReader bufferReaderForFile = null;
		try {
			bufferReaderForFile = new BufferedReader(new FileReader(file));
			mapContents = mapParseProcessor.readMapElements(bufferReaderForFile);
			Map<Country, List<Country>> mapCountry = mapContents.getCountryAndNeighbors();

			Iterator<Country> it = mapContents.getCountryAndNeighbors().keySet().iterator();
			Country next;
			while (it.hasNext()) {
				next = it.next();
				if (visitedMap1.size() != mapContents.getCountryAndNeighbors().keySet().size()) {
					Map<String, Integer> visitedMap = new HashMap<>();
					visitedMap1 = mapValidator.checkConnectedGraph(next, mapCountry, visitedMap);
				} else {
					break;
				}
			}
			int connectedCountries = 0;
			for (Map.Entry<String, Integer> entry : visitedMap1.entrySet()) {
				if (entry.getValue() == 1) {
					connectedCountries++;
				}
			}
			if (mapValidator.checkMapLabel(mapContents)) {
				validMapFlag = true;
			} else {
				validMapFlag = false;
				statusMessage = "Map is invalid as labels are not properly described";
				System.out.println("Message : " + statusMessage);
				return;
			}
			if (mapContents.getContinentAndItsCountries().keySet().isEmpty()) {
				validMapFlag = false;
				statusMessage = "Map should have atleast one Continent";
				System.out.println("Message : " + statusMessage);
				return;
			} else {
				validMapFlag = true;
				statusMessage = "Map is valid";
			}
			if (mapContents.getCountryAndNeighbors().keySet().isEmpty()) {
				validMapFlag = false;
				statusMessage = "Map should have atleast one Country";
				System.out.println("Message : " + statusMessage);
				return;
			} else {
				validMapFlag = true;
				statusMessage = "Map is valid";
			}
			if (mapValidator.checkContinent(mapContents)) {
				validMapFlag = true;
			} else {
				validMapFlag = false;
				statusMessage = "Map is invalid as Country Continent should be from only Continent List";
				System.out.println("Message : " + statusMessage);
				return;
			}
			if (mapValidator.checkContinentCountry(mapContents)) {
				validMapFlag = true;
			} else {
				validMapFlag = false;
				statusMessage = "Map is invalid as Every Continent should have atleast one country";
				System.out.println("Message : " + statusMessage);
				return;
			}
			if (mapValidator.checkUniqueContinentCountry(mapContents)) {
				validMapFlag = true;
			} else {
				validMapFlag = false;
				statusMessage = "Map is invalid as Country can assign to only one Continent";
				System.out.println("Message : " + statusMessage);
				return;
			}

			if (mapValidator.checkConnectedCountryContinent(mapContents)) {
				validMapFlag = true;
			} else {
				validMapFlag = false;
				statusMessage = "Map is invalid as Continents are not connected";
				System.out.println("Message : " + statusMessage);
				return;
			}

			if (mapValidator.checkContinentControlValue(mapContents)) {
				validMapFlag = true;
			} else {
				validMapFlag = false;
				statusMessage = "Map is invalid as Continents Control value can not be less than zero";
				System.out.println("Message : " + statusMessage);
				return;
			}

			if (connectedCountries == mapContents.getCountryAndNeighbors().keySet().size()) {
				validMapFlag = true;
				statusMessage = "Map is valid";
			} else {
				validMapFlag = false;
				statusMessage = "Map is invalid as countries are not connected";
				System.out.println("Message : " + statusMessage);
				return;
			}
			System.out.println("Connected Countries : " + connectedCountries);
			System.out.println("Total Countries : " + mapContents.getCountryAndNeighbors().keySet().size());
			System.out.println("Message : " + statusMessage);
			System.out.println("Valid Map Flag : " + validMapFlag);
		} catch (FileNotFoundException e) {
			validMapFlag = false;
		} catch (Exception e) {
			validMapFlag = false;
		}
	}

	/**
	 * This method returns boolean value for Valid Map
	 * 
	 * @return validMapFlag true if map is valid otherwise false
	 */
	public Boolean getValidMapFlag() {
		return validMapFlag;
	}
}