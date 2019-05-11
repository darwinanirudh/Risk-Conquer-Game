package com.concordia.riskGame.View;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.concordia.riskGame.model.Continent.Continent;
import com.concordia.riskGame.model.Country.Country;
import com.concordia.riskGame.model.Map.MapContents;
import com.concordia.riskGame.model.Map.MapOperations;
import com.concordia.riskGame.model.Map.MapParseProcessor;

/**
 * This class is used for editing the map based on user inputs.
 * 
 * @author saich
 */
public class MapEditView extends java.awt.Frame {
	private static Logger LOGGER = LogManager.getLogger();

	private JLabel headingLabel = new JLabel();
	private JLabel labecountries = new JLabel();
	private JButton removeContinent = new JButton();
	private JButton removeCountry = new JButton();
	private JButton addCountry = new JButton();
	private JButton addContinent = new JButton();
	private JButton renameContinent = new JButton();
	private JButton renameCountry = new JButton();
	private JButton addAdjacentCountry = new JButton();
	private JButton removeAdjacentCountry = new JButton();
	private JButton saveMap = new JButton();
	private JButton exitMap = new JButton();
	private JTextArea log = new JTextArea(30, 30);
	private JFileChooser fileChooser;
	private FileNameExtensionFilter filenameFilter;
	private String filePath = null;
	private HashMap<Country, List<Country>> countryAndNeighbors;
	private HashMap<String, List<String>> countryAndNeighborsMap = new HashMap<String, List<String>>();
	private HashMap<Continent, List<Country>> continentAndItsCountries;
	private HashMap<String, List<String>> continentsAndCountriesMap = new HashMap<String, List<String>>();
	private List<String> Continents = new ArrayList<String>();
	private DefaultListModel<String> countries = new DefaultListModel<>();
	private DefaultListModel<String> countryNeighbours = new DefaultListModel<>();
	private DefaultListModel<String> continents = new DefaultListModel<>();
	private DefaultListModel<String> continentCountries = new DefaultListModel<>();
	private MapParseProcessor mapParseObject;
	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();

	private JTextField AddText = new JTextField(20);

	/**
	 * This method is used to edit the map taking input of continent and country
	 * values.
	 * 
	 * @param countryAndNeighbors      Country with neighboring countries to be
	 *                                 passed of type hashmap.
	 * @param continentAndItsCountries Continent with counties is to be passed of
	 *                                 type hashmap.
	 * @param filePath                 Filename from which game is loaded
	 */
	public void MapDefinition(HashMap<Country, List<Country>> countryAndNeighbors,
			HashMap<Continent, List<Country>> continentAndItsCountries, String filePath) {
		this.countryAndNeighbors = countryAndNeighbors;
		this.continentAndItsCountries = continentAndItsCountries;
		this.filePath = filePath;
		headingLabel.setText(" Please Choose your operation to rename,remove or add Continent or Country from Map  ");
		headingLabel.setVisible(true);

		removeContinent.setText("RemoveContinent");
		removeContinent.setName("removeContinentButton");
		removeContinent.setVisible(true);

		removeCountry.setText("RemoveCountry");
		removeCountry.setName("removeCountryButton");
		removeCountry.setVisible(true);

		addCountry.setText("AddCountry");
		addCountry.setName("addCountryButton");
		addCountry.setVisible(true);

		addContinent.setText("AddContinent");
		addContinent.setName("addContinentButton");
		addContinent.setVisible(true);

		renameContinent.setText("RenameContinent");
		renameContinent.setName("renameContinentButton");
		renameContinent.setVisible(true);

		renameCountry.setText("RenameCountry");
		renameCountry.setName("renameCountryButton");
		renameCountry.setVisible(true);

		addAdjacentCountry.setText("AddAdjacentCountry");
		addAdjacentCountry.setName("addAdjacentCountryButton");
		addAdjacentCountry.setVisible(true);

		removeAdjacentCountry.setText("RemoveAdjacentCountry");
		removeAdjacentCountry.setName("removeAdjacentCountryButton");
		removeAdjacentCountry.setVisible(true);

		saveMap.setText("SaveMap");
		saveMap.setName("saveMap");
		saveMap.setVisible(true);

		exitMap.setText("ExitMap");
		exitMap.setName("exitMap");
		exitMap.setVisible(true);

		JScrollPane scroll = new JScrollPane(log);

		frame.setTitle("Edit Map");
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setSize(800, 800);
		panel.setSize(700, 700);
		panel.setBackground(Color.cyan);
		GridBagLayout layout = new GridBagLayout();
		panel.setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labecountries, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 4;
		panel.add(addCountry, gbc);

		gbc.gridx = 4;
		gbc.gridy = 4;
		gbc.gridwidth = 4;
		panel.add(addContinent, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 4;
		panel.add(renameCountry, gbc);

		gbc.gridx = 4;
		gbc.gridy = 3;
		gbc.gridwidth = 4;
		panel.add(renameContinent, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 4;
		panel.add(removeCountry, gbc);

		gbc.gridx = 4;
		gbc.gridy = 5;
		gbc.gridwidth = 4;
		panel.add(removeContinent, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 4;
		panel.add(addAdjacentCountry, gbc);
		gbc.gridx = 4;
		gbc.gridy = 6;
		gbc.gridwidth = 4;
		panel.add(removeAdjacentCountry, gbc);

		gbc.gridx = 8;
		gbc.gridy = 3;
		gbc.gridwidth = 4;
		gbc.gridheight = 2;
		panel.add(saveMap, gbc);

		gbc.gridx = 8;
		gbc.gridy = 5;
		gbc.gridwidth = 4;
		gbc.gridheight = 2;
		panel.add(exitMap, gbc);

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 12;
		gbc.gridheight = 2;
		AddText.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Countries and Continents data goes here", TitledBorder.CENTER, TitledBorder.TOP));

		panel.add(AddText, gbc);

		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.gridwidth = 12;
		gbc.weighty = 1;
		panel.add(scroll, gbc);

		for (Map.Entry<Country, List<Country>> entry : countryAndNeighbors.entrySet()) {

			List<Country> countryNeighbours = entry.getValue();
			List<String> neighboursList = new ArrayList<String>();

			for (int i = 0; i < countryNeighbours.size(); i++) {

				neighboursList.add(countryNeighbours.get(i).getCountryName().toString().toLowerCase());

			}
			countries.addElement(entry.getKey().getCountryName().toString().toLowerCase());

			countryAndNeighborsMap.put(entry.getKey().getCountryName().toString().toLowerCase(), neighboursList);

		}

		for (Map.Entry<Continent, List<Country>> entry : continentAndItsCountries.entrySet()) {

			List<Country> continentCountries = entry.getValue();
			List<String> CountriesList = new ArrayList<String>();

			for (int i = 0; i < continentCountries.size(); i++) {

				CountriesList.add(continentCountries.get(i).getCountryName().toString().toLowerCase());

			}
			continents.addElement(entry.getKey().getContinentName().toString().toLowerCase());

			continentsAndCountriesMap.put(entry.getKey().getContinentName().toString().toLowerCase(), CountriesList);

		}

		JList<String> countriesJList = new JList<>(countries);
		countriesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		countriesJList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane listScroller = new JScrollPane(countriesJList);
		listScroller.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Countries List",
				TitledBorder.CENTER, TitledBorder.TOP));

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.weighty = 2;
		gbc.weightx = 0.5;
		panel.add(listScroller, gbc);

		JList<String> jCN = new JList<>(countryNeighbours);
		jCN.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jCN.setLayoutOrientation(JList.VERTICAL);
		JScrollPane listScroller2 = new JScrollPane(jCN);
		listScroller2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Neighbours",
				TitledBorder.CENTER, TitledBorder.TOP));

		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.weighty = 2;
		gbc.weightx = 0.5;
		panel.add(listScroller2, gbc);

		JList<String> continentsJList = new JList<>(continents);
		continentsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		continentsJList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane ContinentslistScroller = new JScrollPane(continentsJList);
		ContinentslistScroller.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Countnents List", TitledBorder.CENTER, TitledBorder.TOP));

		gbc.gridx = 6;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.weightx = 0.5;
		gbc.weighty = 2;
		panel.add(ContinentslistScroller, gbc);

		JList<String> countinentCountriesJList = new JList<>(continentCountries);
		countinentCountriesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		countinentCountriesJList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane countinentCountrieslistScroller = new JScrollPane(countinentCountriesJList);
		countinentCountrieslistScroller.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"ContinentCountries", TitledBorder.CENTER, TitledBorder.TOP));
		gbc.gridx = 9;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.weightx = 0.5;
		gbc.weighty = 2;
		panel.add(countinentCountrieslistScroller, gbc);

		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ListSelectionListener listSelectionListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				boolean adjust = listSelectionEvent.getValueIsAdjusting();
				if (!adjust) {
					JList list = (JList) listSelectionEvent.getSource();
					int selections[] = list.getSelectedIndices();
					Object selectionValues[] = list.getSelectedValues();
					for (int i = 0, n = selections.length; i < n; i++) {
						List<String> countryNeighboursList = countryAndNeighborsMap.get(selectionValues[i].toString());
						LOGGER.info(countryNeighboursList);
						countryNeighbours.removeAllElements();
						if (countryNeighboursList != null) {
							for (int k = 0; k < countryNeighboursList.size(); k++) {
								countryNeighbours.addElement(countryNeighboursList.get(k));

							}
						}
					}
					frame.getContentPane().validate();
					frame.getContentPane().repaint();

				}
			}
		};

		ListSelectionListener continentsListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {

				boolean adjust = listSelectionEvent.getValueIsAdjusting();
				if (!adjust) {
					JList list = (JList) listSelectionEvent.getSource();
					int selections[] = list.getSelectedIndices();
					Object selectionValues[] = list.getSelectedValues();
					for (int i = 0, n = selections.length; i < n; i++) {
						List<String> countinentCountriesList = continentsAndCountriesMap
								.get(selectionValues[i].toString());
						continentCountries.removeAllElements();
						if (countinentCountriesList != null) {
							for (int k = 0; k < countinentCountriesList.size(); k++) {
								continentCountries.addElement(countinentCountriesList.get(k));

							}
						}
					}
					frame.getContentPane().validate();
					frame.getContentPane().repaint();

				}
			}
		};

		countriesJList.addListSelectionListener(listSelectionListener);
		continentsJList.addListSelectionListener(continentsListener);

		removeContinent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = (String) continentsJList.getSelectedValue();
				LOGGER.info("Continent Selected: " + s);
				setLog("Continent Removed: " + s);
				removeContinent(s);
				frame.validate();
				frame.repaint();
				panel.repaint();

			}
		});

		removeCountry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = (String) countriesJList.getSelectedValue();
				LOGGER.info("Country Selected: " + s);
				setLog("Country Removed: " + s);
				removeCountry(s);
				frame.validate();
				frame.repaint();
				panel.repaint();

			}
		});

		addCountry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = continentsJList.getSelectedValue();

				if (continentsJList.isSelectionEmpty()) {
					setLog("Select Continent to add Country");

				} else {

					if (!AddText.getText().equals("")) {
						if (!countries.contains(AddText.getText().toLowerCase())) {
							setLog("Country Input is : " + AddText.getText());
							addCountry(AddText.getText().toLowerCase(), continentsJList.getSelectedValue().toString());
							frame.validate();
							frame.repaint();
							panel.repaint();
						} else {
							setLog("Country with name already exists");

						}
					} else {
						setLog(" Give the new country name in input box");

					}

				}
			}
		});

		addContinent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (AddText.getText().isEmpty()) {
					setLog("Enter Continent in Textbox");

				} else {
					if (!continents.contains(AddText.getText().toLowerCase())) {
						continentsAndCountriesMap.put(AddText.getText().toLowerCase(), null);
						continents.addElement(AddText.getText().toLowerCase());
						frame.validate();
						frame.repaint();
						panel.repaint();
					} else {
						setLog("Continent with name already exist");
						frame.validate();
						frame.repaint();
						panel.repaint();

					}
				}

			}
		});

		renameContinent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = continentsJList.getSelectedValue();

				if (AddText.getText().isEmpty()) {
					setLog("Enter Continent in Textbox");

				} else {
					if (continents.contains(AddText.getText().toString().toLowerCase())) {
						setLog("Continent with the name already exist");

					} else {
						setLog("Renaming Continent");
						renameContinent(s, AddText.getText());
						frame.validate();
						frame.repaint();
						panel.repaint();
					}
				}

			}
		});

		renameCountry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = countriesJList.getSelectedValue();

				if (AddText.getText().isEmpty()) {
					setLog("Enter Country in Textbox");

				} else {
					if (countries.contains(AddText.getText().toString().toLowerCase())) {
						setLog("Country withthe name already exists");

					} else {
						setLog("Renaming Country");
						renameCountry(s, AddText.getText());
					}
				}

			}
		});

		addAdjacentCountry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)

			{
				String flag = "F";

				for (Map.Entry<String, List<String>> entry : countryAndNeighborsMap.entrySet()) {

					if (entry.getKey().equals(AddText.getText())) {
						flag = "T";

					}

				}
				String s = countriesJList.getSelectedValue();

				if (AddText.getText().isEmpty()) {
					setLog("Enter Adjacent Country in Textbox");

				} else {
					if (flag.equals("T")) {
						List<String> countryNeighbours = countryAndNeighborsMap
								.get(countriesJList.getSelectedValue().toLowerCase());
						if (countryNeighbours != null
								&& countryNeighbours.contains(AddText.getText().toString().toLowerCase())) {
							setLog("Adjacent Country already exist");
						} else {
							if (AddText.getText().toString().toLowerCase()
									.equals(countriesJList.getSelectedValue().toLowerCase())) {
								setLog("Country cannot be adjacent to itself");

							} else {
								setLog("Adding Adjacent Country");
								addAdjacentCountry(s, AddText.getText().toString().toLowerCase());
							}
						}
					}

					else {
						setLog("Enter Country in the list");

					}

				}

			}
		});

		removeAdjacentCountry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String country = countriesJList.getSelectedValue();
				String adjacentCountry = jCN.getSelectedValue();

				if (jCN.getSelectedValue().isEmpty() | countriesJList.getSelectedValue().isEmpty()) {
					setLog("Select Country and Adjacent Country");

				} else {

					setLog("Remvoing Adjacent Country");
					removeAdjacentCountry(country, adjacentCountry);

				}

			}
		});

		saveMap.addActionListener(new ActionListener()

		{

			public void actionPerformed(ActionEvent e) {
				HashMap<Continent, List<Country>> continentsWithItsCountries = new HashMap<>();
				HashMap<Country, List<Country>> countriesWithItsNeighbours = new HashMap<>();

				for (Map.Entry<String, List<String>> entry : continentsAndCountriesMap.entrySet()) {

					List<Country> countries = new ArrayList<>();

					Continent continent = new Continent(entry.getKey());
					List<String> continentCountries = entry.getValue();
					if (continentCountries != null) {

						for (int i = 0; i < continentCountries.size(); i++) {

							Country country = new Country(continentCountries.get(i).toString(), 0, 0, entry.getKey());

							countries.add(country);

						}

					}

					continent.setNumberOfCountries(continentCountries.size());
					continentsWithItsCountries.put(continent, countries);

				}

				for (Map.Entry<String, List<String>> entry : countryAndNeighborsMap.entrySet()) {

					List<Country> neighbourCountries = new ArrayList<>();

					Country country = new Country(entry.getKey(), getContinet(entry.getKey().toString()));

					List<String> countryCountries = entry.getValue();

					if (countryCountries != null) {
						for (int i = 0; i < countryCountries.size(); i++) {

							Country countries = new Country(countryCountries.get(i).toString(), 0, 0,
									getContinet(countryCountries.get(i).toString()));
							neighbourCountries.add(countries);

						}
					}
					countriesWithItsNeighbours.put(country, neighbourCountries);

				}

				MapContents.setMapContents(null);
				MapContents mapContents = MapContents.getInstance();

				mapContents.setContinentAndItsCountries(continentsWithItsCountries);
				mapContents.setCountryAndNeighbors(countriesWithItsNeighbours);
				MapOperations mapOperations = new MapOperations();
				String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.length());
				try {
					mapOperations.writeMapFile(mapContents, fileName.substring(0, fileName.lastIndexOf(".")),
							filePath.substring(0, filePath.lastIndexOf("\\")));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}

			}
		});

		exitMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("In exit map");
				frame.dispose();
				System.exit(0);

			}
		});

	}

	/**
	 * This method is used to get the continent of the country passed as input.
	 * 
	 * @param country Country input to get the continent value.
	 * @return Continent value of the country passed.
	 */
	public String getContinet(String country)

	{
		String Country = null;
		for (Map.Entry<String, List<String>> entry : continentsAndCountriesMap.entrySet()) {

			List<String> continentCountries = entry.getValue();

			if (continentCountries != null) {

				for (int i = 0; i < continentCountries.size(); i++) {

					if (continentCountries.get(i).equals(country)) {
						Country = entry.getKey();
					}

				}
			}

		}
		return Country;

	}

	/**
	 * It remove the continent from Map based on the selected value
	 * 
	 * @param Continent Continent value that to be removedfrom Map.
	 */
	public void removeContinent(String Continent) {

		List<String> continentRemoveCountries = new ArrayList<String>();
		continentRemoveCountries = continentsAndCountriesMap.get(Continent);

		if (continentRemoveCountries != null) {
			for (int i = 0; i < continentRemoveCountries.size(); i++) {

				removeCountry(continentRemoveCountries.get(i));
			}
		}

		continentsAndCountriesMap.remove(Continent);
		continents.removeElement(Continent);

	}

	/**
	 * It is used to remove the country from Map based on selected continent.
	 * 
	 * @param Country The value of the country to be removed.
	 */
	public void removeCountry(String Country) {
		LOGGER.info("In removing Continent");
		countryAndNeighborsMap.remove(Country);
		countries.removeElement(Country);

		for (Map.Entry<String, List<String>> entry : continentsAndCountriesMap.entrySet()) {

			List<String> continentCountries = entry.getValue();
			List<String> CountriesList = new ArrayList<String>();

			if (continentCountries != null) {

				for (int i = 0; i < continentCountries.size(); i++) {

					if (!continentCountries.get(i).toString().equals(Country)) {

						CountriesList.add(continentCountries.get(i).toString());
					}

				}
			}
			continentsAndCountriesMap.put(entry.getKey().toString(), CountriesList);

		}

		for (Map.Entry<String, List<String>> entry : countryAndNeighborsMap.entrySet()) {

			List<String> continentCountries = entry.getValue();
			List<String> CountriesList = new ArrayList<String>();

			if (continentCountries != null) {
				for (int i = 0; i < continentCountries.size(); i++) {

					if (!continentCountries.get(i).equals(Country)) {

						CountriesList.add(continentCountries.get(i).toString());
					}

				}
			}
			countryAndNeighborsMap.put(entry.getKey().toString(), CountriesList);

		}

		frame.validate();
		frame.repaint();
		panel.repaint();

	}

	/**
	 * This method is used to set the log and append the log to frame.
	 * 
	 * @param logger log value to be set
	 */
	public void setLog(String logger) {
		String currentText = log.getText();
		String newLog = new Date() + " " + logger;
		String appendLog = newLog + "\n" + currentText;
		log.setText(appendLog);

	}

	/**
	 * This method is used to add country to map
	 * 
	 * @param Country   Country value that is to be added to the continent.
	 * @param Continent Continent for addition of country to it.
	 */
	public void addCountry(String Country, String Continent) {
		LOGGER.info("In Adding Country");
		countries.addElement(Country);

		for (Map.Entry<String, List<String>> entry : continentsAndCountriesMap.entrySet()) {

			List<String> continentCountries = new ArrayList<String>();

			if (Continent.equals(entry.getKey().toString()) & !(entry.getValue() == null))

			{
				continentCountries = entry.getValue();

				continentCountries.add(Country);
				LOGGER.info("continent countries are" + continentCountries);
				continentsAndCountriesMap.put(entry.getKey().toString(), continentCountries);
			}

			if (entry.getValue() == null & Continent.equals(entry.getKey().toString())) {
				continentCountries.add(Country);
				LOGGER.info("continent countries loop are" + continentCountries);
				continentsAndCountriesMap.put(entry.getKey().toString(), continentCountries);
			}

		}

		countryAndNeighborsMap.put(Country, null);

		frame.validate();
		frame.repaint();
		panel.repaint();

	}

	/**
	 * This method is used to rename the Continent based on the value passed from UI
	 * 
	 * @param continent       original continent value.
	 * @param renameContinent Value of the continent to be renamed.
	 */
	public void renameContinent(String continent, String renameContinent)

	{
		String removeFlag = "N";
		List<String> continentCountriesList = null;
		for (Map.Entry<String, List<String>> entry : continentsAndCountriesMap.entrySet()) {

			if (entry.getKey().equals(continent))

			{

				continentCountriesList = entry.getValue();

				removeFlag = "Y";

			}
		}

		if (removeFlag.equals("Y")) {

			continentsAndCountriesMap.remove(continent);
			continents.removeElement(continent);
			continentsAndCountriesMap.put(renameContinent, continentCountriesList);
			continents.addElement(renameContinent);
		}

		frame.validate();
		frame.repaint();
		panel.repaint();

	}

	/**
	 * This method renames the country
	 * 
	 * @param Country        original country to be renamed.
	 * @param renamedCountry renamed country value.
	 */
	public void renameCountry(String Country, String renamedCountry) {
		LOGGER.info("In renaming Country");

		for (Map.Entry<String, List<String>> entry : continentsAndCountriesMap.entrySet()) {

			List<String> continentCountries = entry.getValue();
			List<String> CountriesList = new ArrayList<String>();

			for (int i = 0; i < continentCountries.size(); i++) {

				if (!continentCountries.get(i).toString().equals(Country)) {

					CountriesList.add(continentCountries.get(i).toString());
				}

				if (continentCountries.get(i).toString().equals(Country)) {
					CountriesList.add(renamedCountry);

				}

			}
			continentsAndCountriesMap.put(entry.getKey().toString(), CountriesList);

		}

		List<String> CountriesRenameList = new ArrayList<String>();

		for (Map.Entry<String, List<String>> entry : countryAndNeighborsMap.entrySet()) {

			List<String> continentCountries = entry.getValue();
			List<String> CountriesList = new ArrayList<String>();

			if (entry.getKey().equals(Country)) {
				CountriesRenameList = continentCountries;
			}

			if (continentCountries != null)

			{
				for (int i = 0; i < continentCountries.size(); i++) {

					if (!continentCountries.get(i).equals(Country)) {

						CountriesList.add(continentCountries.get(i).toString());

					}

					if (continentCountries.get(i).equals(Country)) {

						CountriesList.add(renamedCountry);

					}

				}
			}

			countryAndNeighborsMap.put(entry.getKey().toString(), CountriesList);

		}

		countries.removeElement(Country);
		countries.addElement(renamedCountry);
		countryAndNeighborsMap.remove(Country);
		countryAndNeighborsMap.put(renamedCountry, CountriesRenameList);

		frame.validate();
		frame.repaint();
		panel.repaint();

	}

	/**
	 * This method is used to add neighboring country to country.
	 * 
	 * @param country         Country to which adjacent country need to be passed.
	 * @param adjacentCountry Adjacent country for adding it to country.
	 */
	public void addAdjacentCountry(String country, String adjacentCountry) {

		for (Map.Entry<String, List<String>> entry : countryAndNeighborsMap.entrySet()) {

			List<String> continentCountries = new ArrayList<String>();
			if (entry.getValue() != null) {
				continentCountries = entry.getValue();
			}

			if (entry.getKey().equals(country)) {
				continentCountries.add(adjacentCountry);
				LOGGER.info(adjacentCountry);
				countryAndNeighborsMap.put(country, continentCountries);
			}

		}

	}

	/**
	 * This method is used to add the remove the adjacent country.
	 * 
	 * @param country         Country value where the adjacent country have to be
	 *                        removed.
	 * @param adjacentCountry Adjacent country value to add to country.
	 */
	public void removeAdjacentCountry(String country, String adjacentCountry) {

		for (Map.Entry<String, List<String>> entry : countryAndNeighborsMap.entrySet()) {

			List<String> continentCountries = entry.getValue();

			if (entry.getKey().equals(country) & continentCountries != null) {
				continentCountries.remove(adjacentCountry);
				countryAndNeighborsMap.put(country, continentCountries);
			}

		}

	}

	/**
	 * 
	 * It is used to read the file from the location and pass the file location to
	 * map parser.
	 */
	public void EditMapFileChoose() {
		try {

			{
				LOGGER.info("#### In Choosing the file ####");
				filenameFilter = new FileNameExtensionFilter(" .map", "map", "map");
				// countFrame.setVisible(true);
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
					mapParseObject.editMapParsermapParser(selectedFile.getAbsolutePath().toString());

				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
