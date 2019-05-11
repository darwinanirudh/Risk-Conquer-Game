package com.concordia.riskGame.View;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.concordia.riskGame.model.Continent.Continent;
import com.concordia.riskGame.model.Country.Country;
import com.concordia.riskGame.model.Map.MapContents;
import com.concordia.riskGame.model.Map.MapOperations;

/**
 * This class is for the user driven creation of the Map File
 * 
 * @author sande
 *
 */
public class CreateMapFile {
	private static Logger LOGGER = LogManager.getLogger();

	private static final int List = 0;
	private int numberOfContinents;
	private int numberOfCountries;
	private List<String> nameOfContinents = new ArrayList<>();
	private List<String> nameOfCountries = new ArrayList<>();
	private HashMap<Continent, List<Country>> continentsWithItsCountries = new HashMap<>();
	private HashMap<Country, List<Country>> countriesWithItsNeighbours = new HashMap<>();
	private List<Country> countries = new ArrayList<>();

	/**
	 * In this method we start the creation of the Map by asking the inputs from the
	 * user.
	 * 
	 */
	public void createMap() {
		Scanner scanner = new Scanner(System.in);
		try {
			countries = new ArrayList<>();
			continentsWithItsCountries = new HashMap<>();
			countriesWithItsNeighbours = new HashMap<>();
			LOGGER.info("Enter the number of Continents");
			numberOfContinents = scanner.nextInt();
			for (int i = 1; i <= numberOfContinents; i++) {
				LOGGER.info("Enter the name of the Continent" + i);
				nameOfContinents.add(scanner.next());
			}
			for (int i = 0; i < numberOfContinents; i++) {
				LOGGER.info("Enter the Number of Countries in the Continent : " + nameOfContinents.get(i));
				int numberOfCountries = scanner.nextInt(); // add number format exception
				LOGGER.info("Enter the Name of countries that belong to the Continent : " + nameOfContinents.get(i));
				for (int j = 0; j < numberOfCountries; j++) {
					String countryName = scanner.next();
					nameOfCountries.add(countryName);
					Country country = new Country(countryName, 0, 0, nameOfContinents.get(i));
					countries.add(country);
				}
				scanner.nextLine();
				Continent continent = new Continent(nameOfContinents.get(i));
				continent.setNumberOfCountries(numberOfCountries);
				continentsWithItsCountries.put(continent, countries);
			}
			for (int i = 0; i < countries.size(); i++) {
				LOGGER.info("Enter the neighbouring countries to the country\"" + countries.get(i).getCountryName()
						+ "\"in \",\"(comma) seperated values ");
				String[] neighbouringCountries = scanner.nextLine().split(",");
				List<Country> neighbourCountries = new ArrayList<>();
				boolean errorWhileReadingCountry = false;
				for (String neighbour : neighbouringCountries) {
					if (neighbour.equals(countries.get(i).getCountryName())) {
						LOGGER.info(
								"A country cannot be neighbour to itself. Please enter the neighbouring countries again ");
						errorWhileReadingCountry = true;
						neighbourCountries = reenterCountries(countries.get(i).getCountryName(), scanner,
								countries.get(i).getBelongsToContinent());
					} else if (!nameOfCountries.contains(neighbour)) {
						LOGGER.info("A country is not in the list. Please enter the neighbouring countries again ");
						errorWhileReadingCountry = true;
						neighbourCountries = reenterCountries(countries.get(i).getCountryName(), scanner,
								countries.get(i).getBelongsToContinent());
					} else {
						if (!errorWhileReadingCountry) {
							Country neighbouringCountry = new Country(neighbour, 0, 0,
									countries.get(i).getBelongsToContinent());
							neighbourCountries.add(neighbouringCountry);
						}
					}
				}
				countriesWithItsNeighbours.put(countries.get(i), neighbourCountries);
			}
		} catch (Exception InputMismatchException) {
			LOGGER.error("Please enter a valid input ");
			createMap();
		}

		LOGGER.info("Please Enter the Name of The Map that you want to create");

		String fileName = scanner.nextLine();
		MapContents.setMapContents(null);
		MapContents mapContents = MapContents.getInstance();
		mapContents.setContinentAndItsCountries(continentsWithItsCountries);
		mapContents.setCountryAndNeighbors(countriesWithItsNeighbours);
		MapOperations mapOperations = new MapOperations();
		try {
			mapOperations.writeMapFile(mapContents, fileName, null);
		} catch (FileNotFoundException e) {
			LOGGER.error("Error Message : " + e.getMessage());
		}
	}

	/**
	 * this method ask user countries one more time after giving the wrong
	 * information
	 * 
	 * @param countryName name of the countries
	 * @param scanner     Scanner object
	 * @param continent   Continent name
	 * @return neighbourCountries list of neighbor countries
	 */
	private List<Country> reenterCountries(String countryName, Scanner scanner, String continent) {

		String[] neighbouringCountries = scanner.nextLine().split(",");
		List<Country> neighbourCountries = new ArrayList<>();
		boolean errorWhileReadingCountry = false;
		for (String neighbour : neighbouringCountries) {
			if (neighbour.equals(countryName)) {
				LOGGER.info("A country cannot be neighbour to itself. Please enter the neighbouring countries again ");
				reenterCountries(countryName, scanner, continent);
			} else if (!nameOfCountries.contains(neighbour)) {
				LOGGER.info("A country is not in the list. Please enter the neighbouring countries again ");
				errorWhileReadingCountry = true;
				neighbourCountries = reenterCountries(countryName, scanner, continent);
			} else {
				Country neighbouringCountry = new Country(neighbour, 0, 0, continent);
				neighbourCountries.add(neighbouringCountry);
			}
		}
		return neighbourCountries;
	}
}
