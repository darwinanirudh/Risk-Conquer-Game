package com.concordia.riskGame.View;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.concordia.riskGame.control.TournamentGameDriver;
import com.concordia.riskGame.util.MapValidator;

/**
 * This class has the implementation of tournament view consisting of all the
 * tournament, player, games and turns.
 * 
 * @author saich
 *
 */
public class Tournament extends JFrame implements ActionListener {
	private static Logger LOGGER = LogManager.getLogger();

	private HashMap<String, String> playerType = new HashMap<>();
	private int noOfTurns;
	private int noOfGames;
	private List<String> maps;
	private JFrame frame;
	private JPanel panel;
	private JLabel player1;
	private JLabel player2;
	private JLabel player3;
	private JLabel player4;
	private JLabel map1;
	private JLabel map2;
	private JLabel map3;
	private JLabel map4;
	private JLabel map5;

	private JComboBox cbPlayer1;
	private JComboBox cbPlayer2;
	private JComboBox cbPlayer3;
	private JComboBox cbPlayer4;

	private JButton fileButton1;
	private JButton fileButton2;
	private JButton fileButton3;
	private JButton fileButton4;
	private JButton fileButton5;

	private JFileChooser fileChooser;
	private FileNameExtensionFilter filenameFilter;

	private String playercount;
	private String mapCount;

	private String playersTypes[] = { "Aggressive", "Benevolent", "Random", "Cheater" };
	private JComboBox playerCountCombo;
	private JComboBox mapCountCombo;
	private JComboBox gamesCountCombo;
	private JComboBox turnsCombo;

	private String[] playerCounterArray = { "2", "3", "4" };
	private String[] mapArray = { "1", "2", "3", "4", "5" };
	private int[] turnsArray = IntStream.rangeClosed(10, 50).toArray();
	private String strArray[] = Arrays.stream(turnsArray).mapToObj(String::valueOf).toArray(String[]::new);

	private JDialog dialogBox;

	private JLabel countLabel;
	private JLabel mapLabel;
	private JLabel gamesLabel;
	private JLabel turnsLabel;

	private JButton okayButton;

	private List<String> filesSelected = new ArrayList<>();
	TournamentGameDriver tgm;

	private File fileObject;
	private BufferedReader bufferReaderForFile;
	private MapValidator mapValidator;

	/**
	 * Default constructor
	 */
	Tournament() {
		tournamentView();
	}

	/**
	 * Tournament view method consists of the tournament view which accepts players,
	 * maps, turns and games.
	 */
	public void tournamentView() {
		frame = new JFrame();
		panel = new JPanel();

		frame.setTitle("Touranment Mode");
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setSize(550, 250);
		panel.setSize(700, 700);
		GridBagLayout layout = new GridBagLayout();
		panel.setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		player1 = new JLabel();
		player2 = new JLabel();
		player3 = new JLabel();
		player4 = new JLabel();

		map1 = new JLabel();
		map2 = new JLabel();
		map3 = new JLabel();
		map4 = new JLabel();
		map5 = new JLabel();

		countLabel = new JLabel("Select the number of Players");
		mapLabel = new JLabel("Select the number of Maps");
		gamesLabel = new JLabel("Select number of Games");
		turnsLabel = new JLabel("Select number of turns");
		playerCountCombo = new JComboBox(playerCounterArray);
		mapCountCombo = new JComboBox(mapArray);
		gamesCountCombo = new JComboBox(mapArray);
		turnsCombo = new JComboBox(strArray);

		okayButton = new JButton("OK");

		player1.setText("Player1");
		player1.setVisible(true);
		player2.setText("Player2");
		player2.setVisible(true);
		player3.setText("Player3");
		player3.setVisible(true);
		player4.setText("Player4");
		player4.setVisible(true);

		map1.setText("Map1");
		map1.setVisible(true);
		map2.setText("Map2");
		map2.setVisible(true);
		map3.setText("Map3");
		map3.setVisible(true);
		map4.setText("Map4");
		map4.setVisible(true);
		map5.setText("Map5");
		map5.setVisible(true);

		cbPlayer1 = new JComboBox(playersTypes);
		cbPlayer2 = new JComboBox(playersTypes);
		cbPlayer3 = new JComboBox(playersTypes);
		cbPlayer4 = new JComboBox(playersTypes);

		fileButton1 = new JButton("Map1");
		fileButton2 = new JButton("Map2");
		fileButton3 = new JButton("Map3");
		fileButton4 = new JButton("Map4");
		fileButton5 = new JButton("Map5");

		fileButton1.addActionListener(this);
		fileButton2.addActionListener(this);
		fileButton3.addActionListener(this);
		fileButton4.addActionListener(this);
		fileButton5.addActionListener(this);

		gbc.gridx = 0;
		gbc.gridy = 0;

		panel.add(countLabel, gbc);
		gbc.gridx = 4;
		gbc.gridy = 0;
		panel.add(playerCountCombo, gbc);

		gbc.gridx = 8;
		gbc.gridy = 0;

		panel.add(mapLabel, gbc);
		gbc.gridx = 12;
		gbc.gridy = 0;

		panel.add(mapCountCombo, gbc);

		gbc.gridx = 0;
		gbc.gridy = 15;
		panel.add(turnsLabel, gbc);
		gbc.gridx = 4;
		gbc.gridy = 15;
		panel.add(turnsCombo, gbc);

		gbc.gridx = 8;
		gbc.gridy = 15;
		panel.add(gamesLabel, gbc);
		gbc.gridx = 12;
		gbc.gridy = 15;
		panel.add(gamesCountCombo, gbc);

		frame.add(panel);

		playerCountCombo.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				playercount = (String) playerCountCombo.getSelectedItem();

				if (playercount.equals("2") || playercount.equals("3") || playercount.equals("4")) {

					gbc.gridx = 0;
					gbc.gridy = 4;
					panel.add(player1, gbc);
					gbc.gridx = 4;
					gbc.gridy = 4;
					panel.add(cbPlayer1, gbc);

					gbc.gridx = 0;
					gbc.gridy = 6;
					panel.add(player2, gbc);
					gbc.gridx = 4;
					gbc.gridy = 6;
					panel.add(cbPlayer2, gbc);

					panel.remove(cbPlayer3);
					panel.remove(player3);
					panel.remove(cbPlayer4);
					panel.remove(player4);

				}
				if (playercount.equals("3") || playercount.equals("4")) {

					gbc.gridx = 0;
					gbc.gridy = 8;
					panel.add(player3, gbc);
					gbc.gridx = 4;
					gbc.gridy = 8;
					panel.add(cbPlayer3, gbc);

					panel.remove(cbPlayer4);
					panel.remove(player4);
				}
				if (playercount.equals("4")) {
					gbc.gridx = 0;
					gbc.gridy = 10;
					panel.add(player4, gbc);
					gbc.gridx = 4;
					gbc.gridy = 10;
					panel.add(cbPlayer4, gbc);

				}

				frame.validate();
				frame.repaint();
				frame.repaint();

			}
		});

		mapCountCombo.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				mapCount = (String) mapCountCombo.getSelectedItem();

				if (mapCount.equals("1") || mapCount.equals("2") || mapCount.equals("3") || mapCount.equals("4")
						|| mapCount.equals("5")) {

					gbc.gridx = 8;
					gbc.gridy = 4;
					panel.add(map1, gbc);
					gbc.gridx = 12;
					gbc.gridy = 4;
					panel.add(fileButton1, gbc);

					panel.remove(fileButton2);
					panel.remove(map2);
					panel.remove(fileButton3);
					panel.remove(map3);
					panel.remove(fileButton4);
					panel.remove(map4);
					panel.remove(fileButton5);
					panel.remove(map5);

				}

				if (mapCount.equals("2") || mapCount.equals("3") || mapCount.equals("4") || mapCount.equals("5")) {

					gbc.gridx = 8;
					gbc.gridy = 6;
					panel.add(map2, gbc);
					gbc.gridx = 12;
					gbc.gridy = 6;
					panel.add(fileButton2, gbc);
					panel.remove(fileButton3);
					panel.remove(map3);
					panel.remove(fileButton4);
					panel.remove(map4);
					panel.remove(fileButton5);
					panel.remove(map5);

				}
				if (mapCount.equals("3") || mapCount.equals("4") || mapCount.equals("5")) {

					gbc.gridx = 8;
					gbc.gridy = 8;
					panel.add(map3, gbc);
					gbc.gridx = 12;
					gbc.gridy = 8;
					panel.add(fileButton3, gbc);
					panel.remove(fileButton4);
					panel.remove(map4);
					panel.remove(fileButton5);
					panel.remove(map5);
				}
				if (mapCount.equals("4") || mapCount.equals("5")) {
					gbc.gridx = 8;
					gbc.gridy = 10;
					panel.add(map4, gbc);
					gbc.gridx = 12;
					gbc.gridy = 10;
					panel.add(fileButton4, gbc);
					panel.remove(fileButton5);
					panel.remove(map5);

				}

				if (mapCount.equals("5")) {
					gbc.gridx = 8;
					gbc.gridy = 12;

					panel.add(map5, gbc);
					gbc.gridx = 12;
					gbc.gridy = 12;

					panel.add(fileButton5, gbc);

				}
				gbc.gridx = 6;
				gbc.gridy = 18;
				panel.add(okayButton, gbc);
				frame.validate();
				frame.repaint();
				frame.repaint();

			}
		});

		okayButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				boolean validity = true;

				if (!mapCountCombo.getSelectedItem().toString().equals(String.valueOf(filesSelected.size()))) {

					JOptionPane.showMessageDialog(frame, "Select file for all the maps.");
					validity = false;
				}
				if (playercount == null) {
					JOptionPane.showMessageDialog(frame, "Select the players");
					validity = false;

				}
				if (validity) {
					if (playercount.equals("2") || playercount.equals("3") || playercount.equals("4")) {
						playerType.put("Player1", (String) cbPlayer1.getSelectedItem());
						playerType.put("Player2", (String) cbPlayer2.getSelectedItem());

					}
					if (playercount.equals("3") || playercount.equals("4")) {

						playerType.put("Player3", (String) cbPlayer3.getSelectedItem());
					}
					if (playercount.equals("4")) {
						playerType.put("Player4", (String) cbPlayer4.getSelectedItem());
					}

					tgm = new TournamentGameDriver();

					try {
						tgm.gamePhase(playerType, filesSelected,
								Integer.parseInt(gamesCountCombo.getSelectedItem().toString()),
								Integer.parseInt(turnsCombo.getSelectedItem().toString()));
						frame.setVisible(false);
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fileButton1 || e.getSource() == fileButton2 || e.getSource() == fileButton3
				|| e.getSource() == fileButton4 || e.getSource() == fileButton5) {

			fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Select the map");
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			filenameFilter = new FileNameExtensionFilter(".map Files", "map", "map");
			fileChooser.setFileFilter(filenameFilter);
			int result = fileChooser.showOpenDialog(fileChooser);
			fileChooser.setLocation(500, 200);
			fileChooser.setSize(500, 500);
			fileChooser.setVisible(true);
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				String fileName = file.getName();

				fileObject = new File(file.getAbsolutePath().toString());
				try {
					bufferReaderForFile = new BufferedReader(new FileReader(fileObject));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mapValidator = new MapValidator();
				mapValidator.init(fileObject);

				if (!mapValidator.getValidMapFlag()) {
					LOGGER.info("Invalid Map File");
					JOptionPane.showMessageDialog(frame, "Invalid Map File");

				}

				else {
					filesSelected.add(file.getAbsolutePath().toString());
				}
			}

		}
	}
}
