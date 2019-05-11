package com.concordia.riskGame.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import com.concordia.riskGame.exception.InvalidMapFileException;
import com.concordia.riskGame.model.Card.Deck;
import com.concordia.riskGame.model.Country.Country;
import com.concordia.riskGame.model.Map.MapContents;
import com.concordia.riskGame.model.Map.MapParseProcessor;
import com.concordia.riskGame.model.Player.Player;
import com.concordia.riskGame.model.Tournament.Tournament;
import com.concordia.riskGame.util.MapValidator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * This class implements the tournament game driver logic
 * @author saich
 *
 */
public class TournamentGameDriver {

	private static Logger LOGGER = LogManager.getLogger();

	private HashMap<Country, List<Country>> gmcountryAndNeighbours;
	private boolean endTheGame;
	public int turns;
	private List<String> results;
	MapParseProcessor mapParseObject;
	private String filePath;
	private File fileObject;
	private BufferedReader bufferReaderForFile;
	private MapValidator mapValidator;

	/**
	 * The following method calls each of the game phase for each player.
	 * @param playerNamesAndTypes hash map of player names and types
	 * @param gameMapFiles is the list of map files for the tournament
	 * @param noOfGames number of games in tournament
	 * @param noOfTurns is the defactor number
	 * @throws Exception throws exception
	 */
	public void gamePhase(HashMap<String, String> playerNamesAndTypes,List<String> gameMapFiles,int noOfGames,int noOfTurns) throws Exception {

		turns=0;
		endTheGame = false;
		results = new ArrayList<>();

		Tournament tournament = new Tournament(playerNamesAndTypes, gameMapFiles, noOfGames, noOfTurns);
		LOGGER.info("=============Tournament Started============");
		for(String fileName : gameMapFiles)
		{
			for(int j=0; j<noOfGames;j++)
			{

				fileObject = new File(fileName);
				bufferReaderForFile = new BufferedReader(new FileReader(fileObject));
				mapValidator = new MapValidator();
				mapValidator.init(fileObject);

				if (!mapValidator.getValidMapFlag()) {
					results.add("Invalid Map File");

				}
				else
				{
				mapParseObject = new MapParseProcessor();
				mapParseObject.mapParser(fileName,String.valueOf(playerNamesAndTypes.size()), playerNamesAndTypes,"tournament");
				MapContents mapContents =MapContents.getInstance();
				gmcountryAndNeighbours = new HashMap<Country, List<Country>>();
				gmcountryAndNeighbours = mapContents.getCountryAndNeighbors();
				Scanner scanner = new Scanner(System.in);
				endTheGame=false;
				turns=0;
				Deck deck = Deck.getInstance();
				deck.setDeckOfCards(mapContents.getCountryList());
				LOGGER.debug("Map "+gameMapFiles.indexOf(fileName)+"In game "+j);

				while(!endTheGame && turns < noOfTurns) {
					List<Player> removablePlayers = new ArrayList<>();
					for(Player player : mapContents.getPlayerList()) {
						if(player.isHasLost()) {
							removablePlayers.add(player);
						}
					}
					mapContents.getPlayerList().removeAll(removablePlayers);
					Iterator<Player> iterator = mapContents.getPlayerList().iterator();
					turns++;
					LOGGER.info("Turns Count is"+turns);

					while(iterator.hasNext() &&!endTheGame) {
						Player playerInstance = new Player();
						Player p = iterator.next();
						if(!p.isHasLost()) {
							playerInstance = p.strategy.reinforcePhase(p);
							if(playerInstance.getCanAttack()) {
								playerInstance = playerInstance.strategy.attackPhase(playerInstance);
							}
							if(playerInstance.getHasWon()) {
								endTheGame=true;
								results.add(playerInstance.getName());
								LOGGER.debug("Game won by "+playerInstance.getName());



							}
							if(playerInstance.getCanFortify()) {
								playerInstance = playerInstance.strategy.forfeitPhase(playerInstance);
							}
						}

						if(turns == noOfTurns && !playerInstance.getHasWon()) {
							LOGGER.debug("Game result is draw");
							endTheGame=true;
							results.add("Draw");
							LOGGER.debug(results.size());
						}
					}

				}

			}
		}

	}
	LOGGER.debug(Arrays.toString(results.toArray()));
	LOGGER.debug("========================================================");

	int listcount=0;
	for(String fileName : gameMapFiles)
	{
		for(int j=0; j<noOfGames;j++)
		{
			LOGGER.debug( "Map "+( gameMapFiles.indexOf(fileName)+1) +" Game "+(j+1)+" result is " +results.get(listcount));
			listcount++;
		}

	}
	LOGGER.info("========================================================");

	if(endTheGame) {
		MapContents mapContents = MapContents.getInstance();
		mapContents.setTournamentResults(results);
		System.exit(0);
	}



}

}
