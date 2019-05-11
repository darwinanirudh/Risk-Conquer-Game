package com.concordia.riskGame.model.Map;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.concordia.riskGame.View.GameLauncher;
import com.concordia.riskGame.exception.InvalidMapFileException;
import com.concordia.riskGame.model.Continent.Continent;
import com.concordia.riskGame.model.Country.Country;
import com.concordia.riskGame.util.MapValidator;

/**
 * This class is created to perform different operations on the map file
 * 
 * @author Sande
 *
 */
public class MapOperations {

	private StringBuilder mapFileContents;
	private static final String[] ILLEGAL_CHARACTERS = { "/", "\n", "\r", "\t", "\0", "\f", "`", "?", "*", "\\", "<",
			">", "|", "\"", ":" };
	private MapValidator mapValidator = new MapValidator();

	/**
	 * This method is used to create map file after taking inputs from the user
	 * 
	 * @param mapContents Map Content
	 * @param fileName    name of the file
	 * @param filePath    Path of the file.
	 * @return fileName name of the file
	 * @throws FileNotFoundException File when found in its path.
	 */
	public String writeMapFile(MapContents mapContents, String fileName, String filePath) throws FileNotFoundException {
		mapFileContents = new StringBuilder();
		mapFileContents.append("[Map]");
		mapFileContents.append(System.lineSeparator());
		mapFileContents.append("[Continents]" + System.lineSeparator());
		for (Continent continent : mapContents.getContinentAndItsCountries().keySet()) {
			mapFileContents.append(continent.getContinentName());
			mapFileContents.append("=");
			mapFileContents.append(continent.getNumberOfCountries());
			mapFileContents.append(System.lineSeparator());
		}
		mapFileContents.append(System.lineSeparator() + "[Territories]");
		for (Map.Entry<Country, List<Country>> countryAndNeighbours : mapContents.getCountryAndNeighbors().entrySet()) {
			mapFileContents.append(System.lineSeparator() + countryAndNeighbours.getKey().getCountryName() + ",");
			mapFileContents.append("0,0");
			mapFileContents.append("," + countryAndNeighbours.getKey().getBelongsToContinent());

			List<Country> neighbours = countryAndNeighbours.getValue();
			if (neighbours != null && !neighbours.isEmpty()) {
				for (Country country : neighbours) {
					mapFileContents.append("," + country.getCountryName());
				}
			}
		}
		fileName = fileName + ".map";
		for (int i = 0; i < ILLEGAL_CHARACTERS.length; i++) {
			if (fileName.contains(ILLEGAL_CHARACTERS[i])) {
				throw new FileNotFoundException();
			}
		}
		File file;
		String currentDir = System.getProperty("user.dir");
		System.out.println("Current dir using System:" + currentDir);
		currentDir = currentDir + File.separator + fileName;
		if (filePath != null && !filePath.isEmpty()) {
			filePath = filePath + File.separator + fileName;
			file = new File(filePath);
		} else {
			file = new File(currentDir);
		}
		try (PrintWriter out = new PrintWriter(file)) {
			out.print(mapFileContents);

		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (Exception e) {
			System.out.println("Error Message : " + e.getMessage());
			e.printStackTrace();
		}

		try {

			mapValidator.init(file);
			if (!mapValidator.getValidMapFlag()) {
				throw new InvalidMapFileException("Invalid Map File");
			}
		} catch (Exception e) {
			System.out.println("Please start over the map creation");
			System.out.println("Fileexists" + file.exists());
			// System.out.println("File deleted "+file.delete());
			GameLauncher gameLauncher = new GameLauncher();
		}
		if (mapValidator.getValidMapFlag()) {
			System.out.println("File has been created");
			GameLauncher gameLauncher;
			if (filePath == null) {
				gameLauncher = new GameLauncher();
			}
		}
		return fileName;
	}

	/**
	 * method to return map content as a String Builder
	 * 
	 * @return mapFileContents map content
	 */
	public StringBuilder getMapFileContents() {
		return mapFileContents;
	}
}
