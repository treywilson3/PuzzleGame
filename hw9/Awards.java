/**
 * 
 */
package hw9;

/**
 * @author Trey The awards that are written for the CSV file Are also displayed
 *         at end of game
 */
public class Awards {
	private double percentages;
	private double time;
	private String skillLevelAwarded;
	private String timeAwarded;

	/**
	 * Constructor for CSV file. Assigns the skill level and speed of user.
	 * 
	 * @param percentage
	 *            - number correct for user
	 * @param time
	 *            - user's time to answer the question
	 */
	public Awards(double percentage, double time) {
		this.percentages = percentage;
		if (this.percentages == 1.00) {
			setSkillLevel("Expert");
		} else if (this.percentages > .50 && this.percentages < 1.00) {
			setSkillLevel("Intermediate");
		} else {
			setSkillLevel("Beginner");
		}
		this.time = time;
		if (this.time < 2.5) {
			setSpeed("Energizer Bunny");
		} else if (this.time >= 2.5 && this.time <= 5) {
			setSpeed("Quarter Horse");
		} else {
			setSpeed("Turtle");
		}
	}

	/**
	 * Setter method for the user's speed
	 * 
	 * @param string
	 *            - time it took
	 */
	public void setSpeed(String string) {
		timeAwarded = string;

	}

	/**
	 * Setter method for the user's skill level
	 * 
	 * @param string
	 *            - score of user
	 */
	public void setSkillLevel(String string) {
		skillLevelAwarded = string;
	}

	/**
	 * Getter method for user's speed
	 * 
	 * @return the user's time
	 */
	public String getSpeed() {
		return timeAwarded;
	}

	/**
	 * Getter method for the user's skill level
	 * 
	 * @return the user's score
	 */
	public String getSkillLevel() {
		return skillLevelAwarded;
	}
}
