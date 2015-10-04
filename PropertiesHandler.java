import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JFileChooser;

/**
 * After creating an handler object the property file will be loaded and the properties can be accessed.</br>
 * Remember to call saveProperties() after setting new Properties.
 */
public class PropertiesHandler {
	
	private Properties properties;
	private File propertyPath;
	
	/**
	 * Loads the existing properties file from user/mydocuments/IncGame/IncGame.properties.</br>
	 * If the file is nonexistent it will be created.
	 */
	public PropertiesHandler() {
		properties = new Properties();
		try {
			File documentPath = new JFileChooser().getFileSystemView().getDefaultDirectory();
			propertyPath = new File(documentPath, "IncGame" + File.separator + "IncGame.properties");
			if (propertyPath.exists()) {
				FileInputStream fileInput = new FileInputStream(propertyPath);
				properties.load(fileInput);
				fileInput.close();
			}
			else {
				propertyPath.getParentFile().mkdirs();
				propertyPath.createNewFile();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}
	
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	public void saveProperties() {
        try {
			OutputStream out = new FileOutputStream(propertyPath);
			properties.store(out, "IncGame Properties");
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteProps() {
		propertyPath.delete();
	}

}
