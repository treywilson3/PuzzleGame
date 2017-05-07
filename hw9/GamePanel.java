package hw9;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * 
 * @author Trey Adds image and math problem to panel. Each image has different
 *         states. Image is clickable
 */

public class GamePanel extends JPanel {
	private Image img;
	private MathPanel mathPanel;
	private String userName;

	private static boolean answered;
	private boolean clicked;
	private boolean mouseListenerIsActive;

	private int w;
	private int h;

	private static final long serialVersionUID = 1L;

	private static float allTime = 0;
	private static int counter = 0;
	private static int numberOfRightQuestions = 0;
	private double questionPercentage;
	private int numOfProblems;

	private String endOfGameMessage;
	private Color color;
	private Awards awards;
	private AudioClip endOfGame;

	private static ArrayList<String> csvInformation = new ArrayList<String>();

	/**
	 * Constructor for GPanel The color of the puzzle is determined here. Along
	 * with setting the size of the panel.
	 * 
	 * @param img
	 *            - buffered image
	 * @param mathPanel
	 *            - math problem that is created in MathPanel
	 */
	public GamePanel(Image img, MathPanel panel, String userColor, String userName) {
		// This will set the color
		determineColor(userColor);
		this.img = img;
		this.mathPanel = panel;
		if (userName != null && !userName.isEmpty()) {
			this.userName = userName;
		} else {
			this.userName = "Not Given";
		}

		w = img.getWidth(this);
		h = img.getHeight(this);
		setPreferredSize(new Dimension(w, h));

		mouseListenerIsActive = true;
		answered = false;
		clicked = false;
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (mouseListenerIsActive) {
					clicked = true;
					answered = false;
					repaint();
					stopMouseListner();
				}
			}
		});
		numOfProblems = mathPanel.getNumberOfProblems();
	}

	/**
	 * Paints components onto panel. This is also used to determine when game is
	 * over. If it is over, call collectData and endOfGameMessage. Will also
	 * determine if the user got the question right or wrong and will paint the
	 * correct component onto the panel.
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (!answered) {
			if (!clicked) {
				g2.setColor(color);
				g2.fillRect(0, 0, w, h);
			} else {
				add(mathPanel);
				revalidate();
			}
		} else {
			counter++;
			// If the user answered it wrong, repaint the square
			if (mathPanel.getIncorrect()) {
				g2.setColor(color);
				g2.fillRect(0, 0, w, h);
				revalidate();

			} // If the user answers it right, draw the image and record
				// the time it took to answer, and that the user
				// got it correct.
			else {
				allTime += mathPanel.getEachTime();
				numberOfRightQuestions += mathPanel.getNumberCorrect();
				g2.drawImage(img, 0, 0, this);
			}
			// Checks if the game is over.
			if (counter == getNumberOfProblems()) {
				collectData();
				endOfGameMessage();
			}
		}
	}

	/**
	 * This is used in the constructor to determine the puzzle piece color
	 * Returns the correct puzzle piece the user chose.
	 * 
	 * @param userColor
	 *            - the color the user wants
	 * @return - what the puzzle color piece will be
	 */
	public Color determineColor(String userColor) {
		if (userColor.equals("Random")) {
			Random rand = new Random();
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			this.color = new Color(r, g, b);
		} else if (userColor.equals("Red")) {
			this.color = Color.RED;
		} else if (userColor.equals("Blue")) {
			this.color = Color.BLUE;
		} else {
			this.color = Color.GREEN;
		}
		return color;
	}

	/**
	 * Once the game is over, present them with a message. The message contains
	 * the average time to solve, number of right over wrong questions. And the
	 * awards the user won.
	 */
	public void endOfGameMessage() {
		endOfGameMessage = "It took you an average of " + String.format("%.2f", getAverageTime())
				+ " seconds to solve the problems. " + "You got " + getNumberOfRightQuestions() + " out of "
				+ getNumberOfProblems() + " right." + "\n"
				+ String.format("%70s", "Skill Level: " + awards.getSkillLevel()) + "\n"
				+ String.format("%70s", " Speed: " + awards.getSpeed());

		endOfGame = CreateSounds.getAudioClip("EndOfGame.wav");
		endOfGame.play();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(null, endOfGameMessage);
			}
		});
	}

	/**
	 * Collects the data for the CSV file This will be used when the user wants
	 * to save the CSV file.
	 * 
	 * @return - the csvArray.
	 */
	public ArrayList<String> collectData() {
		awards = new Awards(getPercentageCorrect(), getAverageTime());
		String information = "User Name" + "," + "Average Time To Solve" + "," + "Number Correct" + ","
				+ "Number Group Chosen" + "," + "Arithmetic Chosen" + "," + "Skill Level" + "," + "Speed" + "\n"
				+ getUserName() + "," + String.format("%.2f", getAverageTime()) + "," + getNumberOfRightQuestions()
				+ " out of " + getNumberOfProblems() + "," + mathPanel.getUserRangeSelection() + ","
				+ mathPanel.getArithmeticSelection() + "," + awards.getSkillLevel() + "," + awards.getSpeed();
		csvInformation.add(information);

		return csvInformation;
	}

	/**
	 * Will set "answered" = true if called
	 */
	public static void setAnswered() {
		answered = true;
	}

	/**
	 * Resets Stats. Used for when new game is created in same runtime.
	 */
	public static void resetStats() {
		allTime = 0;
		counter = 0;
		numberOfRightQuestions = 0;
	}

	/**
	 * Getter method for answered questions.
	 * 
	 * @return - will return if question is answered
	 */
	public boolean getAnswered() {
		return answered;
	}

	/**
	 * Getter method in string form of the average time The user took to solve
	 * the questions.
	 * 
	 * @return - will return the average time to solve problems.
	 */
	public String getAverage() {
		return Float.toString(allTime / numOfProblems);
	}

	/**
	 * Makes mouseListener to false so the image can't be clicked again.
	 */
	public void stopMouseListner() {
		mouseListenerIsActive = false;
	}

	/**
	 * Getter method for average time
	 * 
	 * @return the average time it took to solve the question
	 */
	public double getAverageTime() {
		return allTime / numberOfRightQuestions;
	}

	/**
	 * Getter method for number of right questions
	 * 
	 * @return - the number of right questions
	 */
	public int getNumberOfRightQuestions() {
		return numberOfRightQuestions;
	}

	/**
	 * Getter method for number of problems
	 * 
	 * @return - the number of problems the user chose in the game
	 */
	public int getNumberOfProblems() {
		return numOfProblems;
	}

	/**
	 * Getter method for userName
	 * 
	 * @return - userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Static Getter method for CSV file. Needed for CSV file. CSV class will
	 * loop through this arraylist and print it
	 * 
	 * @return - csv information array list
	 */
	public static ArrayList<String> getCSVInformation() {
		return csvInformation;
	}

	/**
	 * Getter method for percentage of right questions Used for certifcate
	 * 
	 * @return questionPercentage
	 */
	public double getPercentageCorrect() {
		questionPercentage = ((double) getNumberOfRightQuestions() / getNumberOfProblems());
		return questionPercentage;
	}
}