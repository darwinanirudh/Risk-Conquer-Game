package com.concordia.riskGame.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.Assertion;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import com.concordia.riskGame.control.TournamentGameDriver;
import com.concordia.riskGame.model.Map.MapContents;
import com.concordia.riskGame.model.Player.Player;
/**
 * This class implements the tournament test cases
 * @author DModi
 *
 */
public class TournamentGameDriverTest {

	private HashMap<String, String> playerNamesAndTypes;
	private List<String> gameMapFiles;
	private Player p1, p2;
	private boolean validGameFlag;

	@Rule
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();

	/**
	 * before method for initializing objects
	 */
	@Before
	public void before() {

		playerNamesAndTypes = new HashMap<>();
		gameMapFiles = new ArrayList<>();
		validGameFlag = false;
		playerNamesAndTypes.put("Player1", "Aggressive");
		playerNamesAndTypes.put("Player2", "Cheater");
		gameMapFiles.add("src/main/resources/test.map");
	}

	/**
	 * Test method to check if saved game file has been created or not
	 * 
	 * @throws IOException if there is an error 
	 */
	@Test
	public void testReadSavedMapContent() throws IOException {
		try {
			exit.expectSystemExitWithStatus(0);
			exit.checkAssertionAfterwards(new Assertion() {
				@Override
				public void checkAssertion() throws Exception {
					System.out.println("This is executed AFTER System.exit()");

					MapContents mapContents = MapContents.getInstance();
					System.out.println("No of Countries : " + mapContents.getCountryList().size());
					System.out.println("No of Players : " + mapContents.getPlayerList().size());

					for(String result : mapContents.getTournamentResults()) {
						System.out.println("in If");
						if(result.equalsIgnoreCase("draw")
								&& mapContents.playerList.get(0).getAssignedCountries().size()
										+ mapContents.playerList.get(1).getAssignedCountries().size() == mapContents
												.getCountryList().size()) {
							validGameFlag = true;
						} else if(!result.equalsIgnoreCase("draw")) {
							System.out.println("in else");

							for(Player player : mapContents.getPlayerList()) {
								System.out.println("Player Name : " + player.getName());
								int i = 0;
								for(String result1 : mapContents.getTournamentResults()) {
									if(player.getName().equalsIgnoreCase(result1)) {
										if (player.getAssignedCountries().size() == mapContents.getCountryList()
												.size()) {
											validGameFlag = true;
										} else {
											System.out.println(" 3 : result " + result);
											validGameFlag = false;
											break;
										}
									}
								}
								i++;
							}
						}
					}

					assertTrue(validGameFlag);

					for(String result : mapContents.getTournamentResults()) {
						System.out.println("results : " + result);
					}

				}
			});

			TournamentGameDriver driver = new TournamentGameDriver();
			driver.gamePhase(playerNamesAndTypes, gameMapFiles, 2, 8);
		}catch (Exception e) {
			// e.printStackTrace();
		}

	}

}
