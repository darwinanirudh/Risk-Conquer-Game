package com.concordia.riskGame.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test Suite class to run all the test case files
 * @author Dhaval
 *
 */
@RunWith(Suite.class)
@SuiteClasses({CreateMapFileTest.class,
			   MapValidatorTest.class,
			   RandomAssignmentTest.class, 
			   ReinforcementTest.class,
			   StartUpPhaseTest.class,
			   AttackPhaseTest.class,
			   FortificationTest.class,
			   CardsTest.class,
			   DiceTest.class,
			   LoadGameTest.class,
			   RiskSaveGameTest.class,
			   TournamentGameDriverTest.class
			   })

public class TestSuiteAll {

}
