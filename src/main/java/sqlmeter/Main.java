package sqlmeter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Leonid Ivanov
 */
public class Main {

    public static final String CONFIG_FILE_NAME = "config.ini";
    public static final String CONFIG_FILE_PATH = System.getProperty("user.dir") + System.getProperty("file.separator") + CONFIG_FILE_NAME;
    public static final Properties CONFIG_PROPERTIES = getProperty();

    static Properties getProperty() {
        Properties prop = new Properties();
        try (FileInputStream ins = new FileInputStream(CONFIG_FILE_PATH)) {
            prop.load(ins);
            return prop;
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден!" + e);
            JOptionPane.showMessageDialog(null, "Файл " + CONFIG_FILE_NAME + " не найден!", "Файл не найден!", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            System.out.println("Не удалось прочесть файл " + e);
            JOptionPane.showMessageDialog(null, "Не удалось прочесть файл " + CONFIG_FILE_NAME + " не найден!", "Не удалось прочесть файл!", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            System.out.println("Ошибка программы! " + e);
            JOptionPane.showMessageDialog(null, "Ошибка программы!", "Ошибка программы!", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }
    
    public static void main(String[] args) {
        
        if (CONFIG_PROPERTIES==null){
            System.exit(0);
        }
        
        new StartJFrame();        
        
    }
    
}
