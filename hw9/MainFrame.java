package hw9;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * @author Trey MainFrame is where the frame is made and also where the MenuBar
 *         is made and added
 */
public class MainFrame {

	private JFrame frame;
	private JMenuBar menuBar;
	private GameMenu gameMenu;

	/**
	 * Constructor for MainFrame Where frame is made, menuBar added, and
	 * GameMenu is ran
	 */
	public MainFrame() {
		frame = new JFrame();
		frame.setTitle("Homework 9: Trey Wilson");
		frame.setSize(1100, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setJMenuBar(createMenuBar());
		gameMenu = new GameMenu();
		frame.add(gameMenu, BorderLayout.NORTH);
		frame.setVisible(true);
	}

	/**
	 * Creates the menu bar. Open, save, and exit are made here
	 * 
	 * @return - the menu bar
	 */
	public JMenuBar createMenuBar() {
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");

		JMenuItem openItem = new JMenuItem("Open");
		openItem.setMnemonic(KeyEvent.VK_O);
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		fileMenu.add(openItem);

		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.setMnemonic(KeyEvent.VK_S);
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		fileMenu.add(saveItem);
		fileMenu.addSeparator();

		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		fileMenu.add(exitItem);

		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);

		openItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = JOptionPane.showConfirmDialog(frame, "Open CSV File?", "Open", JOptionPane.YES_NO_OPTION);
				if (action == JOptionPane.YES_OPTION) {
					CSV.readCSVFile("MathGame.csv");
				}
			}
		});

		saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = JOptionPane.showConfirmDialog(frame, "Save CSV File?", "Save", JOptionPane.YES_NO_OPTION);
				if (action == JOptionPane.YES_OPTION) {
					CSV.writeCSVFile(GamePanel.getCSVInformation());
				}
				System.out.println("Saved CSV File");
			}
		});

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = JOptionPane.showConfirmDialog(frame, "Do you really want to exit the application?",
						"Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
				if (action == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		});
		return menuBar;
	}

	/**
	 * Runs program
	 * 
	 * @param args
	 *            - not used
	 */
	public static void main(String[] args) {
		new MainFrame();
	}
}
