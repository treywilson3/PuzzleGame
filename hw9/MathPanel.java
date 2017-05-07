package hw9;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Trey Panel for math equations. Calls CreateEquations. This is where
 *         all buttons and textfields are made for math equation panel.
 */
public class MathPanel extends JPanel {
	private JLabel display;
	private JTextField answerInput;
	private JButton button;

	private CreateEquations question;
	private long startTime = 0;
	private long endTime;

	private final static int STARTING = 0;
	private final static int FIRST_TRY = 1;
	private final static int SECOND_TRY = 2;
	private final static int FINAL_STATE = 3;
	private int state = STARTING;

	private int quizQuestionType;
	private int quizQuestionRandomNumber;
	private int numberOfProblems;
	private int globalTime;
	private int correct = 0;
	private String response;

	private boolean incorrect;
	private AudioClip correctSound, chancesGoneSound;

	private String csvArithmeticSelection;
	private int csvRangeSelection;

	/**
	 * Constructor for MathPanel
	 * 
	 * @param number
	 *            - the number between 1 and 12 that the user chooses
	 * @param type
	 *            - either add/sub or multiply/divide
	 * @param problems
	 *            - how many problems the user wants
	 * @param width
	 *            - the correct width of the problem to match the image
	 * @param height
	 *            - the correct height of the problem to match the image.
	 */
	public MathPanel(int number, int type, int problems, int width, int height) {
		csvRangeSelection = number;
		quizQuestionRandomNumber = number;
		quizQuestionType = type;
		numberOfProblems = problems;
		setPreferredSize(new Dimension(width, height + 1));
		setLayout(new BorderLayout());

		// Needed for CSV
		if (type == 0) {
			setArithmeticSelection("Add/Subtract");
		} else if (type == 1) {
			setArithmeticSelection("Multiple/Divide");
		}

		display = new JLabel("Question");
		display.setHorizontalAlignment(JLabel.CENTER);
		display.setVerticalAlignment(JLabel.CENTER);
		add(display, BorderLayout.CENTER);

		JPanel bottom = new JPanel();
		bottom.setPreferredSize(new Dimension(100, 100));
		bottom.add(new JLabel("Enter Answer Here: "));
		answerInput = new JTextField(4);
		answerInput.setEnabled(false);
		bottom.add(answerInput);

		button = new JButton("Show Question");

		answerInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				buttonPressed();
			}
		});

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				buttonPressed();
			}
		});

		bottom.add(button);
		add(bottom, BorderLayout.SOUTH);
		this.correctSound = CreateSounds.getAudioClip("Correct.wav");
		this.chancesGoneSound = CreateSounds.getAudioClip("ChancesGone.wav");
	}

	/**
	 * What happens after show button is pressed This is where the user will
	 * enter their answer Has error catching abilities Lets user know if they
	 * get the question right Gives them two attempts.
	 */
	private void buttonPressed() {

		if (state == STARTING) { // Create and display the first question;
									// change state to FIRST_TRY.
			question = new CreateEquations(quizQuestionRandomNumber, quizQuestionType);
			this.startTime = System.currentTimeMillis() / 1000;
			display.setText(question.getQuestion());
			answerInput.setEnabled(true);
			answerInput.requestFocus();
			button.setText("Submit");
			state = FIRST_TRY;
			return;
		}

		String userInput = answerInput.getText().trim(); // Get user's input
															// from the input
															// box.

		if (userInput.length() == 0) { // Nothing was entered in the box; give
										// an appropriate error message.
			errorMessage("Enter your answer in the input box,\nand then click the Submit button.");
			return;
		}

		int userAnswer; // user's answer as an integer value
		try {
			userAnswer = Integer.parseInt(userInput); // convert input string to
														// integer
		} catch (NumberFormatException e) { // Input was not valid; give an
											// appropriate error message.
			errorMessage("\"" + userInput + "\" is not a legal integer.\nPlease enter your answer as an integer.");
			return;
		}

		// At this point, we have the user's answer stored in the int variable
		// userAnswer, and the
		// state is FIRST_TRY or SECOND_TRY. Check the answer and respond
		// accordingly.

		// String response; // This will be the program's feedback to the user
		// about the answer.

		if (state == FIRST_TRY) {
			if (userAnswer == question.getAnswer()) {
				endTime = System.currentTimeMillis() / 1000 - startTime;
				globalTime += endTime;
				response = "Correct! " + "Time: " + endTime + "sec(s)";
				correct++;
				setVisible(false);
				correctSound.play();
				GamePanel.setAnswered();

			} else { // Keep the same question, but change state to SECOND_TRY.
				response = "Question: " + question.getQuestion() + " Incorrect, try again..";
				state = SECOND_TRY;
			}
		} else if (state == SECOND_TRY) { // state is SECOND_TRY
			if (userAnswer == question.getAnswer()) {
				endTime = System.currentTimeMillis() / 1000 - startTime;
				globalTime += endTime;
				response = "Correct on the 2nd try!";
				correct++;
				correctSound.play();
				setVisible(false);
				GamePanel.setAnswered();
			} else {
				response = "Sorry, the correct answer is " + question.getAnswer() + ". Time wasn't recorded.";
				answerInput.requestFocus();
				chancesGoneSound.play();
				answerInput.setText(Integer.toString(question.getAnswer()));
				state = FINAL_STATE;

			}
		} else if (state == FINAL_STATE) {
			if (userAnswer == question.getAnswer()) {
				GamePanel.setAnswered();
				setVisible(false);
				incorrect = true;
			}
		}

		display.setText(response);
		answerInput.selectAll(); // Highlights the contents of the input box.
		answerInput.requestFocus(); // Moves input focus to input box, so user
									// doesn't have to click it.
	}

	/**
	 * Setter method for arithmetic type
	 * 
	 * @param string
	 *            - arithmetic mode
	 */
	public void setArithmeticSelection(String string) {
		csvArithmeticSelection = string;
	}

	/**
	 * Getter method for arithmetic selection
	 * 
	 * @return - user's arithmetic choice
	 */
	public String getArithmeticSelection() {
		return csvArithmeticSelection;
	}

	/**
	 * Setter method for user's range
	 * 
	 * @param number
	 *            - the number (range) the user chose
	 */
	public void setRangeSelection(int number) {
		csvRangeSelection = number;
	}

	/**
	 * Getter method for user's range selection
	 * 
	 * @return - the user's range selection
	 */
	public int getUserRangeSelection() {
		return csvRangeSelection;
	}

	/**
	 * Getter method for number of Correct answers
	 * 
	 * @return - returns number of correct problems
	 */
	public int getNumberCorrect() {
		return correct;
	}

	/**
	 * Getter method for global time
	 * 
	 * @return - Keeps track of of how long each question takes to solve.
	 */
	public int getEachTime() {
		return globalTime;
	}

	/**
	 * Getter method for incorrect problems
	 * 
	 * @return - will return if an answer is incorrect
	 */
	public boolean getIncorrect() {
		return incorrect;
	}

	/**
	 * Getter method for number of Problems
	 * 
	 * @return - the number of problems the user selected.
	 */
	public int getNumberOfProblems() {
		return numberOfProblems;
	}

	/**
	 * Error Message that is presented to user.
	 * 
	 * @param message
	 *            - error message presented to user.
	 */
	private void errorMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Error in Input", JOptionPane.ERROR_MESSAGE);
	}
}
