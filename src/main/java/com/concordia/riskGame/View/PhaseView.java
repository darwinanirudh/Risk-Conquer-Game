/**
 * 
 */
package com.concordia.riskGame.View;

import java.util.Observable;
import java.util.Observer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.concordia.riskGame.model.Player.*;

/**
 * This class will display the Game phase details
 * 
 * @author saich
 *
 */
public class PhaseView implements Observer {
	private static Logger LOGGER = LogManager.getLogger();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		Player player = (Player) arg0;

		if ("phase".equals(player.phasePrint)) {
			player.phasePrint = "";
			LOGGER.info("## Phase view ## ->  " + player.phaseMsg);

		}
	}

}
