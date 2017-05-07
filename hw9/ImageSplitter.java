package hw9;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;

/**
 * @author Trey Splits images up and puts them into list
 */

public class ImageSplitter {
	/**
	 * Static splitImage method
	 * 
	 * @param fileName
	 *            - filename of picture
	 * @param rows
	 *            - how many rows the user wants
	 * @param cols
	 *            - how many columns the user wants
	 * @return - returns list of split images.
	 */
	public static Image[] splitImage(String fileName, int rows, int cols) {
		BufferedImage[] imgs = new BufferedImage[rows * cols]; // Image array to
																// hold images
		Image[] images = new Image[rows * cols];

		try {
			File file = new File(fileName); // takes picture from
											// directory
			FileInputStream fis = new FileInputStream(file);
			BufferedImage image = ImageIO.read(fis); // reading the image file

			int pieceWidth = image.getWidth() / cols; // determines the width
			int pieceHeight = image.getHeight() / rows; // and height of each
														// piece

			for (int x = 0; x < rows; x++) {
				for (int y = 0; y < cols; y++) {
					// Initialize the image array with image pieces
					imgs[x * rows + y] = new BufferedImage(pieceWidth, pieceHeight, image.getType());
					// draws the image piece
					Graphics2D gr = imgs[x * rows + y].createGraphics();
					gr.drawImage(image, 0, 0, pieceWidth, pieceHeight, pieceWidth * y, pieceHeight * x,
							pieceWidth * y + pieceWidth, pieceHeight * x + pieceHeight, null);
					gr.dispose();
				}
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

		for (int i = 0; i < rows * cols; i++)
			images[i] = (Image) imgs[i];

		return images;
	}
}
