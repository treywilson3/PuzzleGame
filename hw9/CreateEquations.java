package hw9;

import java.util.Random;

/**
 * 
 * @author Trey Creates math problems.
 */
public class CreateEquations {
	private String question;
	private int equationAnswer;
	private int userArithmeticChoice;
	private int userNumberBetween0and12;

	/**
	 * Constructor for Arithmetic Questions. Calls whichever method the user
	 * requests
	 * 
	 * @param randomNumber
	 *            - random number that user choose between 1 and 12
	 * @param type
	 *            - either add/sub or multiply/divide
	 */
	public CreateEquations(int randomNumber, int type) {
		userNumberBetween0and12 = randomNumber;
		userArithmeticChoice = type;

		// Call one of the two methods depending on what user wants
		if (userArithmeticChoice == 0)
			;
		{
			addandSubtract();
		}
		if (userArithmeticChoice == 1) {
			multiplyandDivide();
		}
	}

	/**
	 * Multiply and divide method. Will create multiply and divide questions for
	 * user. Creates random numbers between 0 and 12. Also creates a random
	 * number between 0 and 1 for equations
	 */
	public void multiplyandDivide() {
		// Create the random number
		Random rand = new Random();
		int randomNumberBetween0and1 = rand.nextInt(2);

		// Create random number that is multiplied by
		// userNumberBetween0and12
		int randomNumberBetween0and12 = rand.nextInt(13);

		// Multiply and increase counter by 1
		if (randomNumberBetween0and1 == 0) {
			// Compute the right answer (multiply).
			equationAnswer = userNumberBetween0and12 * randomNumberBetween0and12;
			// Produce string of equation, then display string
			// (multiply).
			question = userNumberBetween0and12 + " * " + randomNumberBetween0and12;
		}
		if (randomNumberBetween0and1 == 1) {
			if (randomNumberBetween0and12 == 0) {
				randomNumberBetween0and12 = 1;
			}
			// Compute the right answer (divide).
			equationAnswer = userNumberBetween0and12 / randomNumberBetween0and12;
			// Produce string of equation and display it (divide)
			question = userNumberBetween0and12 + " / " + randomNumberBetween0and12;
		}

	}

	/**
	 * Add/Sub method Will create add/sub questions for user Creates random
	 * numbers between 0 and 12. Also creates a random number between 0 and 1
	 * for equations
	 */
	public void addandSubtract() {
		Random rand = new Random();
		int randomNumberBetween0and1 = rand.nextInt(2);

		// Create random number that is multiplied by
		// userNumberBetween0and12
		int randomNumberBetween0and12 = rand.nextInt(13);

		// If randomly generated number is 0, add and increase counter by 1
		if (randomNumberBetween0and1 == 0) {
			// Compute the right answer (addition).
			equationAnswer = userNumberBetween0and12 + randomNumberBetween0and12;
			// Produce string of equation, then display string
			// (addition).
			question = userNumberBetween0and12 + " + " + randomNumberBetween0and12;
		}
		if (randomNumberBetween0and1 == 1) {
			equationAnswer = userNumberBetween0and12 - randomNumberBetween0and12;
			// Produce string of equation and display it (subtraction)
			question = userNumberBetween0and12 + " - " + randomNumberBetween0and12;
		}
	}

	/**
	 * Getter method for the math question
	 * 
	 * @return - returns question in string form
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * Getter method for answer to the question
	 * 
	 * @return - returns question answer in int form.
	 */
	public int getAnswer() {
		return equationAnswer;
	}
}
