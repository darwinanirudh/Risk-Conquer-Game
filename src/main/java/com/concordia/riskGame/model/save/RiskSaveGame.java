package com.concordia.riskGame.model.save;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.concordia.riskGame.model.Map.MapContents;

/**
 * This class implements the logic to save the game into a file
 * 
 * @author Darwin Anirudh G
 *
 */
public class RiskSaveGame {

	private Date date;

	/**
	 * This method writes the objects into the file
	 * 
	 * @param mapContObj map content object that is to be saved
	 * @return filePath file path of the saved game
	 * @throws IOException if there is an error
	 */
	public String saveGame(MapContents mapContObj) throws IOException {
		FileOutputStream fileOutStream = null;
		ObjectOutputStream out = null;
		String filePath = null;
		try {
			System.out.println("########## SAVE GAME  ##########");
			date = new Date();

			DateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");
			String dateString = sdf.format(date);

			dateString = dateString.replaceAll(":", "");
			System.out.println("########## Date String is  ########## : " + dateString);
			filePath = "C:\\SaveGame\\" + dateString;
			File file = new File(filePath);

			file.setReadable(true);
			file.setWritable(true);
			file.setExecutable(true);

			System.out
					.println("##### The rotate value inside save game file is ###### : " + mapContObj.getRotateCount());

			fileOutStream = new FileOutputStream(filePath);
			out = new ObjectOutputStream(fileOutStream);
			out.writeObject(mapContObj);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (fileOutStream != null) {
				try {
					fileOutStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return filePath;
	}
}
