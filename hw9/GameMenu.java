package hw9;

import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*;

/**
 * @author Trey GameMenu is where the GameMenu is made. All labels, checkboxes,
 *         and choices are made in this class. Also, all choices are made from
 *         this panel So multiple classes are called to run from this class.
 */
public class GameMenu extends JPanel {
	private JLabel userNameLabel;
	private TextField userNameTextField;
	private JLabel colorChoice;
	private JLabel numbersLabel;
	private JLabel arithmeticLabel;
	private JLabel picturesLabel;
	private JLabel splitLabel;

	private DefaultComboBoxModel<String> colorChoiceModel;
	private DefaultComboBoxModel<String> numbersModel;
	private DefaultComboBoxModel<String> arithmeticModel;
	private DefaultComboBoxModel<String> picturesModel;
	private DefaultComboBoxModel<String> splitModel;

	private JComboBox<String> colorChoiceCombo;
	private JComboBox<String> numbersCombo;
	private JComboBox<String> arithmeticCombo;
	private JComboBox<String> picturesCombo;
	private JComboBox<String> splitCombo;
	private JButton beginButton;

	private JPanel images;
	private JPanel buttons;
	private int width;
	private int height;

	private int rowsandcolumns;
	private int userRangeSelection;
	private int userPictureSelection;
	private int userArithmeticSelection;
	private int userGridSelection;
	private String pictureAddress;
	private ArrayList<MathPanel> mathPanel;

	/**
	 * Constrcutor for GameMenu. Where game menu buttons, textfields,
	 * comboboxes, and labels are made.
	 */
	public GameMenu() {
		// Jframe init
		mathPanel = new ArrayList<MathPanel>();
		setLayout(new BorderLayout());

		// Creates all Labels
		colorChoice = new JLabel("Color of Puzzel");
		userNameLabel = new JLabel("User Name");
		numbersLabel = new JLabel("Numbers");
		arithmeticLabel = new JLabel("Arithmetic");
		picturesLabel = new JLabel("Picture");
		splitLabel = new JLabel("Split");

		// Create Text Field
		userNameTextField = new TextField(10);

		// Creates all drop down selections
		colorChoiceCombo = new JComboBox<String>();
		numbersCombo = new JComboBox<String>();
		arithmeticCombo = new JComboBox<String>();
		picturesCombo = new JComboBox<String>();
		splitCombo = new JComboBox<String>();

		// Creates begin button
		beginButton = new JButton("Begin");

		// Adds selections to drop down menu
		colorChoiceModel = new DefaultComboBoxModel<String>();
		numbersModel = new DefaultComboBoxModel<String>();
		picturesModel = new DefaultComboBoxModel<String>();
		splitModel = new DefaultComboBoxModel<String>();

		arithmeticModel = new DefaultComboBoxModel<String>();
		arithmeticModel.addElement("add/subtract");
		arithmeticModel.addElement("multiple/divide");
		arithmeticCombo.setModel(arithmeticModel);

		// Add Numbers 0-12, add numbers 1-4 (picture choice)
		// Add numbers 4, 9, 16 (split)
		for (int i = 0; i < 13; i++) {
			numbersModel.addElement(Integer.toString(i));
			if (i > 0 && i < 4) {
				picturesModel.addElement(Integer.toString(i));
			}
			if (i > 1 && i < 5) {
				splitModel.addElement(Integer.toString(i * i));
			}
		}

		colorChoiceModel.addElement("Random");
		colorChoiceModel.addElement("Red");
		colorChoiceModel.addElement("Blue");
		colorChoiceModel.addElement("Green");
		colorChoiceCombo.setModel(colorChoiceModel);

		// Set all the models
		numbersCombo.setModel(numbersModel);
		picturesCombo.setModel(picturesModel);
		splitCombo.setModel(splitModel);

		// Create button panel and then add the buttons to it
		buttons = new JPanel();
		buttons.add(userNameLabel);
		buttons.add(userNameTextField);
		buttons.add(colorChoice);
		buttons.add(colorChoiceCombo);
		buttons.add(numbersLabel);
		buttons.add(numbersCombo);
		buttons.add(arithmeticLabel);
		buttons.add(arithmeticCombo);
		buttons.add(picturesLabel);
		buttons.add(picturesCombo);
		buttons.add(splitLabel);
		buttons.add(splitCombo);
		buttons.add(beginButton);
		add(buttons, BorderLayout.NORTH);

		// Add action listener for begin button on game menu
		beginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				beginButtonPressed();
			}
		});
	}

	/**
	 * What happens when the begin button is pressed on the GameMenu This is
	 * where everything the user selects is used for calling classes. MathPanel,
	 * ImageSplitter, and GamePanel classes are called here.
	 */
	public void beginButtonPressed() {
		// Get information from gameMenu
		String userName = (String) userNameTextField.getText();

		String color = (String) colorChoiceCombo.getSelectedItem();

		String numberString = (String) numbersCombo.getSelectedItem();
		userRangeSelection = Integer.parseInt(numberString);

		userArithmeticSelection = arithmeticCombo.getSelectedIndex();

		String picturesString = (String) picturesCombo.getSelectedItem();
		userPictureSelection = Integer.parseInt(picturesString);

		String splitString = (String) splitCombo.getSelectedItem();
		userGridSelection = Integer.parseInt(splitString);

		// Load correct picture
		if (userPictureSelection == 1) {
			pictureAddress = "HW9Picture1.jpg";
		} else if (userPictureSelection == 2) {
			pictureAddress = "HW9Picture2.jpg";
		} else {
			pictureAddress = "HW9Picture3.jpg";
		}

		// Create the correct number of problems
		if (userGridSelection == 4) {
			rowsandcolumns = 2;
		} else if (userGridSelection == 9) {
			rowsandcolumns = 3;
		} else {
			rowsandcolumns = 4;
		}

		// If user wants to create new game this will accomplish it
		if (images != null) {
			mathPanel = new ArrayList<MathPanel>();
			GamePanel.resetStats();
			remove(images);
		}

		// Create images panel
		images = new JPanel();
		images.setLayout(new GridLayout(rowsandcolumns, rowsandcolumns));
		// Split the chosen image
		Image[] imgs = ImageSplitter.splitImage(pictureAddress, rowsandcolumns, rowsandcolumns);

		// get width and heights
		width = imgs[0].getWidth(null);
		height = imgs[0].getHeight(null);

		// Make the math problems
		for (int i = 0; i < rowsandcolumns * rowsandcolumns; i++) {
			mathPanel.add(new MathPanel(userRangeSelection, userArithmeticSelection, userGridSelection, width, height));
		}
		// Call the GPanel and add them
		for (int i = 0; i < rowsandcolumns * rowsandcolumns; i++) {
			images.add(new GamePanel(imgs[i], mathPanel.get(i), color, userName));
		}
		// Add the images and make sure to refresh panel
		add(images, BorderLayout.SOUTH);
		repaint();
		revalidate();
	}
}
