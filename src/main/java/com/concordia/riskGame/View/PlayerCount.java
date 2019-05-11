package com.concordia.riskGame.View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.concordia.riskGame.model.Map.MapParseProcessor;

/**
 * This Class performs actions for selecting the number of players from property
 * file and select the .map file for the game.
 * 
 * @author Darwin Anirudh -Team 13
 * @author Dheeraj As - Team 13
 *
 */
public class PlayerCount extends JFrame {
	private static Logger LOGGER = LogManager.getLogger();
	private String[] playerCounterArray = { "2", "3", "4", "5", "6" };
	private int noOfPlayers;
	private JFrame countFrame;
	private JPanel panel = new JPanel();
	private JLabel countLabel;
	private JLabel selectMapLabel;
	private JComboBox playerCountCombo;
	private JButton okayButton;
	private JFileChooser fileChooser;
	private FileNameExtensionFilter filenameFilter;
	private JFrame playerFrame;
	private String filePath = null;
	private int number = 1;
	private MapParseProcessor mapParseObject;
	private JLabel player1 = new JLabel();
	private JLabel player2 = new JLabel();
	private JLabel player3 = new JLabel();
	private JLabel player4 = new JLabel();
	private JLabel player5 = new JLabel();
	private JLabel player6 = new JLabel();
	private String playersTypes[] = { "Aggressive", "Benevolent", "Random", "Cheater", "Human" };
	private JComboBox cbPlayer1;
	private JComboBox cbPlayer2;
	private JComboBox cbPlayer3;
	private JComboBox cbPlayer4;
	private JComboBox cbPlayer5;
	private JComboBox cbPlayer6;
	private String playercount;

	/**
	 * This constructor calls the Count method
	 */
	public PlayerCount() {
		try {
			Count();
		} catch (Exception e) {
			LOGGER.error("Error Message : " + e.getMessage());
		}
	}

	/**
	 * The function of this method is to allow the player to select the number of
	 * players from a configuration file.
	 */
	public void Count() {
		// JFrame.setDefaultLookAndFeelDecorated(true);
		countFrame = new JFrame("Concordia Conquest");
		countFrame.setVisible(true);
		countFrame.setTitle("Choose Player");
		countFrame.setVisible(true);
		countFrame.setLocationRelativeTo(null);
		countFrame.setSize(500, 500);
		countFrame.setLocation(500, 200);
		// panel.setBackground(Color.cyan);
		GridBagLayout layout = new GridBagLayout();
		panel.setLayout(layout);
		countFrame.add(panel);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		countLabel = new JLabel("Select the number of players");
		playerCountCombo = new JComboBox(playerCounterArray);
		okayButton = new JButton("OK");
		player1.setText("Player1");
		player1.setVisible(true);
		player2.setText("Player2");
		player2.setVisible(true);
		player3.setText("Player3");
		player3.setVisible(true);
		player4.setText("Player4");
		player4.setVisible(true);
		player5.setText("Player5");
		player5.setVisible(true);
		player6.setText("Player6");
		player6.setVisible(true);
		cbPlayer1 = new JComboBox(playersTypes);
		cbPlayer1.setBounds(50, 50, 90, 20);
		cbPlayer2 = new JComboBox(playersTypes);
		cbPlayer2.setBounds(50, 50, 90, 20);
		cbPlayer3 = new JComboBox(playersTypes);
		cbPlayer3.setBounds(50, 50, 90, 20);
		cbPlayer4 = new JComboBox(playersTypes);
		cbPlayer4.setBounds(50, 50, 90, 20);
		cbPlayer5 = new JComboBox(playersTypes);
		cbPlayer5.setBounds(50, 50, 90, 20);
		cbPlayer6 = new JComboBox(playersTypes);
		cbPlayer6.setBounds(50, 50, 90, 20);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(countLabel, gbc);
		gbc.gridx = 2;
		gbc.gridy = 0;
		panel.add(playerCountCombo, gbc);
		countFrame.add(panel);
		playerCountCombo.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				playercount = (String) playerCountCombo.getSelectedItem();

				if (playercount.equals("2") || playercount.equals("3") || playercount.equals("4")
						|| playercount.equals("5") || playercount.equals("6")) {

					gbc.gridx = 0;
					gbc.gridy = 4;
					panel.add(player1, gbc);
					gbc.gridx = 2;
					gbc.gridy = 4;
					panel.add(cbPlayer1, gbc);

					gbc.gridx = 0;
					gbc.gridy = 6;
					panel.add(player2, gbc);
					gbc.gridx = 2;
					gbc.gridy = 6;
					panel.add(cbPlayer2, gbc);
					panel.remove(cbPlayer3);
					panel.remove(player3);
					panel.remove(cbPlayer4);
					panel.remove(player4);
					panel.remove(cbPlayer5);
					panel.remove(player5);
					panel.remove(cbPlayer6);
					panel.remove(player6);

				}
				if (playercount.equals("3") || playercount.equals("4") || playercount.equals("5")
						|| playercount.equals("6")) {

					gbc.gridx = 0;
					gbc.gridy = 8;
					panel.add(player3, gbc);
					gbc.gridx = 2;
					gbc.gridy = 8;
					panel.add(cbPlayer3, gbc);
					panel.remove(cbPlayer4);
					panel.remove(player4);
					panel.remove(cbPlayer5);
					panel.remove(player5);
					panel.remove(cbPlayer6);
					panel.remove(player6);
				}
				if (playercount.equals("4") || playercount.equals("5") || playercount.equals("6")) {
					gbc.gridx = 0;
					gbc.gridy = 10;
					panel.add(player4, gbc);
					gbc.gridx = 2;
					gbc.gridy = 10;
					panel.add(cbPlayer4, gbc);
					panel.remove(cbPlayer5);
					panel.remove(player5);
					panel.remove(cbPlayer6);
					panel.remove(player6);
				}
				if (playercount.equals("5") || playercount.equals("6")) {
					gbc.gridx = 0;
					gbc.gridy = 12;
					panel.add(player5, gbc);
					gbc.gridx = 2;
					gbc.gridy = 12;
					panel.add(cbPlayer5, gbc);
					panel.remove(cbPlayer6);
					panel.remove(player6);
				}
				if (playercount.equals("6")) {
					gbc.gridx = 0;
					gbc.gridy = 14;
					panel.add(player6, gbc);
					gbc.gridx = 2;
					gbc.gridy = 14;
					panel.add(cbPlayer6, gbc);
				}
				gbc.gridx = 2;
				gbc.gridy = 18;
				panel.add(okayButton, gbc);
				countFrame.validate();
				countFrame.repaint();
				countFrame.repaint();
			}
		});

		/**
		 * This function implements actions when okay button is clicked and presents
		 * user the option to select the map file
		 * 
		 * @param event User Event
		 */
		okayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					if (event.getSource() == okayButton) {
						HashMap<String, String> playerType = new HashMap<>();

						if (playercount.equals("2") || playercount.equals("3") || playercount.equals("4")
								|| playercount.equals("5") || playercount.equals("6")) {
							playerType.put("Player1", (String) cbPlayer1.getSelectedItem());
							playerType.put("Player2", (String) cbPlayer2.getSelectedItem());

						}
						if (playercount.equals("3") || playercount.equals("4") || playercount.equals("5")
								|| playercount.equals("6")) {

							playerType.put("Player3", (String) cbPlayer3.getSelectedItem());
						}
						if (playercount.equals("4") || playercount.equals("5") || playercount.equals("6")) {
							playerType.put("Player4", (String) cbPlayer4.getSelectedItem());
						}
						if (playercount.equals("5") || playercount.equals("6")) {
							playerType.put("Player5", (String) cbPlayer5.getSelectedItem());
						}
						if (playercount.equals("6") || playercount.equals("6")) {
							playerType.put("Player6", (String) cbPlayer6.getSelectedItem());
						}
						for (String name : playerType.keySet()) {
							String key = name.toString();
							String value = playerType.get(name).toString();
							LOGGER.info(key + " " + value);
						}
						LOGGER.info("#### Okay Button is Clicked ####");
						filenameFilter = new FileNameExtensionFilter(" .map", "map", "map");
						countFrame.setVisible(false);
						fileChooser = new JFileChooser();
						fileChooser.setDialogTitle("Select the desired map file");
						fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
						fileChooser.setFileFilter(filenameFilter);
						int result = fileChooser.showOpenDialog(fileChooser);
						fileChooser.setLocation(500, 200);
						fileChooser.setSize(500, 500);
						fileChooser.setVisible(true);
						if (result == JFileChooser.APPROVE_OPTION) {
							File selectedFile = fileChooser.getSelectedFile();
							LOGGER.info("Selected file: " + selectedFile.getAbsolutePath().toString());
							filePath = selectedFile.getAbsolutePath().toString();
							mapParseObject = new MapParseProcessor();
							mapParseObject.mapParser(selectedFile.getAbsolutePath().toString(),
									playerCountCombo.getSelectedItem().toString(), playerType, "single");
						}
					}
				} catch (Exception e) {
					LOGGER.error("Error Message : " + e.getMessage());
				}
			}
		});
	}

	/**
	 * This method get the filePath
	 * 
	 * @return filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * this method sets the filePath
	 * 
	 * @param filePath path of the file
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * method to get Player Array
	 * 
	 * @return playerCounterArray player array
	 */
	public String[] getPlayerCounter() {
		return playerCounterArray;
	}

	/**
	 * method to set player Array
	 * 
	 * @param playerCounter Alayer Array
	 */
	public void setPlayerCounter(String[] playerCounter) {
		this.playerCounterArray = playerCounter;
	}

	/**
	 * method to get ReadConfiguration object
	 * 
	 * @return readConfigurationObject ReadConfiguration object
	 */
	/**
	 * method to get numberOfPlayers
	 * 
	 * @return noOfPlayers number of players
	 */
	public int getNoOfPlayers() {
		return noOfPlayers;
	}

	/**
	 * method to set number of players
	 * 
	 * @param noOfPlayers number of Players
	 */
	public void setNoOfPlayers(int noOfPlayers) {
		this.noOfPlayers = noOfPlayers;
	}
}