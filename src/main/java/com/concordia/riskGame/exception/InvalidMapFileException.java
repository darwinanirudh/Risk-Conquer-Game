package com.concordia.riskGame.exception;

/**
 * This class contains custom exception for Invalid map file.
 * 
 * @author D_Modi
 */
public class InvalidMapFileException extends Exception {

	/**
	 * Default Constructor
	 */
	public InvalidMapFileException() {
	}

	/**
	 * Constructor that accepts a message
	 * 
	 * @param message : Invalid map file message
	 */
	public InvalidMapFileException(String message) {
		super(message);
	}

}
