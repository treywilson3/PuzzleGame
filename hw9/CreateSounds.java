package hw9;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Trey This was given to us previously in class Sounds class just has
 *         one static method Creates sounds for game
 */
public class CreateSounds {
	/**
	 * Gets the audio clip and opens it Therefore making the sound run
	 * 
	 * @param filename
	 *            - gets audio clip name
	 * @return the audio clip
	 */
	public static AudioClip getAudioClip(String filename) {
		URL url = null;
		try {
			File file = new File(filename);
			if (file.canRead())
				url = file.toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (url == null)
			throw new RuntimeException("audio " + filename + " not found");
		return Applet.newAudioClip(url);
	}
}
