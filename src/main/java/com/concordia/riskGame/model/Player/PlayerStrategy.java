package com.concordia.riskGame.model.Player;

/**
 * This interface is for the strategies that a player can have and contains the
 * methods that change with respect to the behaviour of the player and these
 * methods will be implemented in the subsequent classes which implement this
 * interface.
 * 
 * @author sande
 *
 */
public interface PlayerStrategy {

	/**
	 * This the abstract method for which implementation will be provided in the
	 * classes which implement this interface. This method is for the reinforcement
	 * of the countries owned by the player.
	 * 
	 * @param player We pass the player object who is currently playing the game.
	 * @return enriched player object is returned
	 * @throws Exception if error
	 */
	public Player reinforcePhase(Player player) throws Exception;

	/**
	 * This the abstract method for which implementation will be provided in the
	 * classes which implement this interface. This method is for the attack iin
	 * which player may decide to attack neighboring countries or not
	 * 
	 * @param player We pass the player object who is currently playing the game.
	 * @return enriched player object is returned
	 */
	public Player attackPhase(Player player);

	/**
	 * This the abstract method for which implementation will be provided in the
	 * classes which implement this interface. This method is for the fortification
	 * in which
	 * 
	 * @param player We pass the player object who is currently playing the game.
	 * @return enriched player object is returned
	 */
	public Player forfeitPhase(Player player);

}
