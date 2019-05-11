package com.concordia.riskGame.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.concordia.riskGame.control.GameDriver;
import com.concordia.riskGame.model.Map.MapContents;
import com.concordia.riskGame.model.Map.MapParseProcessor;

/**
 * The load game class is used to load the saved file and continue the game
 * where it was saved.
 * 
 * @author Darwin Anirudh
 *
 */
public class LoadGame {
	private static Logger LOGGER = LogManager.getLogger();

	private JFileChooser fileChooser;
	private String filePath;
	private int rotateValue;

	/**
	 * Method to choose file
	 */
	public void chooseFileMethod() {

		try {
			LOGGER.info("##### Select a file to load the game #######");
			LOGGER.info("#### Okay Button is Clicked ####");

			fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Select the saved game file");
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

			int result = fileChooser.showOpenDialog(fileChooser);
			fileChooser.setLocation(500, 200);
			fileChooser.setSize(500, 500);
			fileChooser.setVisible(true);

			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				LOGGER.info("Selected file: " + selectedFile.getAbsolutePath().toString());
				filePath = selectedFile.getAbsolutePath().toString();
				LOGGER.info("###### The selected file path is ####### : " + filePath);
				MapContents mapContentObject = readSavedMapContent(filePath);
				GameDriver gameDriverObject = new GameDriver();
				gameDriverObject.load(mapContentObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * The following method read the saved map file from the specified location and
	 * instantiates the map content object.
	 * 
	 * @param filePath The path of the saved file.
	 * @return mapContentObject returns mapContent object after loading
	 */
	public MapContents readSavedMapContent(String filePath)

	{

		MapContents mapContentObject = null;
		try {
			LOGGER.info("##### Calling readSavedMapContent() #########");
			LOGGER.info("##### file path is  ######### : " + filePath);
			FileInputStream saveFile = new FileInputStream(filePath);
			ObjectInputStream restore = new ObjectInputStream(saveFile);
			Object obj = restore.readObject();
			LOGGER.info("####### Saved file object is ####### : " + obj.toString());

			mapContentObject = (MapContents) obj;
			LOGGER.info("####### mapContentObject file object is ####### : " + mapContentObject.toString());

			LOGGER.info("##########  Loaded Saved game File #######");
			LOGGER.info("##########  Map Content Number of Player       ####### : "
					+ mapContentObject.getPlayerList().size());
			LOGGER.info("##########  Map Content Number of Countries  ####### : "
					+ mapContentObject.getCountryList().size());

			rotateValue = mapContentObject.getRotateCount();
			LOGGER.info("######### The roate count value is ######## : " + rotateValue);
			List<com.concordia.riskGame.model.Player.Player> playerListLoadGame = new ArrayList();
			playerListLoadGame = mapContentObject.getPlayerList();
			Collections.rotate(playerListLoadGame, playerListLoadGame.size() - rotateValue);
			mapContentObject.setPlayerList(playerListLoadGame);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapContentObject;
	}

}