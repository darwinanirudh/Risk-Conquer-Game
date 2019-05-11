package com.concordia.riskGame.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * The functionality of the class is to present the viewer a menu to start the
 * game , exit etc
 * 
 * @author Darwin Anirudh -Team 13
 * @author Dheeraj As - Team 13
 */
public class GameLauncher extends JFrame implements ActionListener {
	private static Logger LOGGER = LogManager.getLogger();

	private JFrame gameFrame;
	private TitledBorder border;
	private JPanel gamePanel;
	private JButton startGameButton;
	private JButton createMapButton;
	private JButton editMapButton;
	private JButton tournamentButton;
	private JButton loadGameButton;
	private JButton exitButton;
	private JLabel titleLabel;
	private PlayerCount playerCount;
	private CreateMapFile createMapFile;
	private MapEditView editObject;
	private Tournament tournament;

	/**
	 * This method launches the game and displays the start menu
	 */
	public GameLauncher() {
		try {
			initUI();
		} catch (Exception e) {
			LOGGER.error("Error Message : " + e.getMessage());
		}

	}

	/**
	 * this method initialize the UI of the game
	 */
	public void initUI() {

		JFrame.setDefaultLookAndFeelDecorated(true);
		gameFrame = new JFrame("Concordia Conquest");
		gameFrame.setVisible(true);

		gameFrame.setSize(500, 500);
		gameFrame.setLocation(500, 200);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		border = new TitledBorder("Concordia Conquest");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);

		gamePanel = new JPanel();
		gamePanel.setBorder(border);

		titleLabel = new JLabel("Concordia Conquest");

		titleLabel.setForeground(Color.black);
		titleLabel.setFont(new Font("Sans Serif", Font.PLAIN, 14));

		gameFrame.getContentPane().add(titleLabel);
		gameFrame.getContentPane().add(gamePanel);
		gamePanel.setLayout(null);
		startGameButton = new JButton("Start Game");
		gamePanel.add(startGameButton);
		startGameButton.setFont(new Font("Georgia", Font.PLAIN, 10));
		startGameButton.setBackground(UIManager.getColor("Button.highlight"));
		startGameButton.setForeground(new Color(102, 0, 0));
		startGameButton.setForeground(new Color(102, 0, 0));

		startGameButton.setBounds(186, 102, 121, 21);
		startGameButton.addActionListener(this);
		createMapButton = new JButton("Create Map");
		gamePanel.add(createMapButton);
		createMapButton.setForeground(new Color(102, 0, 0));
		createMapButton.setFont(new Font("Georgia", Font.PLAIN, 10));

		createMapButton.setBounds(186, 158, 121, 21);
		createMapButton.addActionListener(this);
		editMapButton = new JButton("Edit map");
		gamePanel.add(editMapButton);
		editMapButton.setFont(new Font("Georgia", Font.PLAIN, 10));
		editMapButton.setForeground(new Color(102, 0, 0));
		editMapButton.setBackground(UIManager.getColor("Button.highlight"));

		editMapButton.setBounds(186, 214, 121, 21);
		editMapButton.addActionListener(this);
		tournamentButton = new JButton("Tournament");
		gamePanel.add(tournamentButton);
		tournamentButton.setForeground(new Color(102, 0, 0));
		tournamentButton.setFont(new Font("Georgia", Font.PLAIN, 10));

		tournamentButton.setBounds(186, 270, 121, 21);
		tournamentButton.addActionListener(this);
		loadGameButton = new JButton("Load Game");
		gamePanel.add(loadGameButton);
		loadGameButton.setForeground(new Color(102, 0, 0));
		loadGameButton.setFont(new Font("Georgia", Font.PLAIN, 10));

		loadGameButton.setBounds(186, 326, 121, 21);
		loadGameButton.addActionListener(this);
		exitButton = new JButton("Exit");
		gamePanel.add(exitButton);
		exitButton.setForeground(new Color(102, 0, 0));
		exitButton.setFont(new Font("Georgia", Font.PLAIN, 10));

		exitButton.setBounds(186, 371, 121, 21);
		exitButton.addActionListener(this);

	}

	/**
	 * main method
	 * 
	 * @param args String array.
	 */
	public static void main(String[] args) {
		GameLauncher ex = new GameLauncher();

	}

	/**
	 * This method takes an event as an input and performs task depending on the
	 * event
	 */
	public void actionPerformed(ActionEvent event) {

		String action = event.getActionCommand();
		if (event.getSource() == startGameButton) {
			LOGGER.info("####  startGameButton is clicked ####");
			gameFrame.setVisible(false);
			playerCount = new PlayerCount();
			playerCount.setVisible(true);

		} else if (event.getSource() == createMapButton) {
			LOGGER.info("#### createMapButton  is clicked ####");
			gameFrame.setVisible(false);
			createMapFile = new CreateMapFile();
			createMapFile.createMap();

		} else if (event.getSource() == editMapButton) {
			LOGGER.info("#### editMapMapButton  is clicked ####");
			gameFrame.setVisible(false);
			editObject = new MapEditView();
			editObject.EditMapFileChoose();

		} else if (event.getSource() == tournamentButton) {
			LOGGER.info("#### tournamentButton  is clicked ####");
			gameFrame.setVisible(false);
			tournament = new Tournament();

		}

		else if (event.getSource() == loadGameButton) {
			LOGGER.info("#### loadGameButton  is clicked ####");
			gameFrame.dispose();
			LoadGame loadgame = new LoadGame();
			loadgame.chooseFileMethod();

		} else if (event.getSource() == exitButton) {
			System.exit(0);

		}

	}
}