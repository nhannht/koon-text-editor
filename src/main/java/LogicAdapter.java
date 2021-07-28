import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.plaf.synth.SynthConstants;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogicAdapter {
    /**
     * index array of founded search string
     */
    static List<Integer> indexArray = new ArrayList<Integer>();
    /**
     * list of founded search string
     */
    static List<String> stringsSearch = new ArrayList<>();
    /**
     * Track current index in both indexArray and stringsSearch
     */
    static int currentIndex;

    /**
     * track change in search box
     */
    static String currentQuery = "";
    /**
     * track change in text area
     */
    static String currentContentInTextArea = "";

    /**
     * Logger for TextEditor class
     */
    private static Logger logger = LoggerFactory.getLogger("Controller log");

    public static String changeLanguageMode(RSyntaxTextArea textArea, String syntaxConstants) {
        try {
            textArea.setSyntaxEditingStyle(syntaxConstants);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return syntaxConstants;
    }


    public static String loadFile(JFileChooser fileChooser) throws IOException {
        fileChooser.setVisible(true);
        String content = "";
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            content = new String(Files.readAllBytes(selectedFile.toPath()));
        }
        fileChooser.setVisible(false);
        return content;
    }

    public static void saveFile(String s, JFileChooser fileChooser) throws IOException {
        fileChooser.setVisible(true);
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            FileWriter w = new FileWriter(file);
            w.write(s);
            w.close();
        }
        fileChooser.setVisible(false);
    }

    public static void quit(Frame f) {
        f.dispose();
    }

    public static void setCursor(int index, String text, JTextArea jTextArea) {
        jTextArea.setCaretPosition(index + text.length());
        jTextArea.select(index, index + text.length());
        jTextArea.grabFocus();

    }

    /**
     * @param jTextArea
     * @param pattern
     * @param regex
     */
    public static void search(JTextArea jTextArea, String pattern, Boolean regex) {
        String content = jTextArea.getText();
        if (!currentQuery.equals(pattern) || !currentContentInTextArea.equals(content)) {
            currentQuery = pattern;
            currentContentInTextArea = content;
            currentIndex = 0;
            indexArray.clear();
            stringsSearch.clear();
            Pattern re;
            if (regex) {
                re = Pattern.compile(pattern);
            } else {
                re = Pattern.compile(Pattern.quote(pattern));
            }
            Matcher matcher = re.matcher(content);

            while (matcher.find()) {
                indexArray.add(matcher.start());
                stringsSearch.add(matcher.group());
            }
            logger.debug("Current possitions of searched string is " + indexArray);
            logger.debug("Current search String is " + stringsSearch);
        }
        if (indexArray.size() != 0) {
            setCursor(indexArray.get(currentIndex), stringsSearch.get(currentIndex), jTextArea);
            currentIndex += 1;
            if (currentIndex == indexArray.size()) {
                currentIndex = 0;
            }
        }
        logger.debug("Current index is " + currentIndex);


    }

    public static void nextMatch(JTextArea a) {
        currentIndex += 1;
        if (currentIndex == indexArray.size()) {
            currentIndex = 0;
        }

        logger.debug("Current index is " + currentIndex);
        logger.debug("Current possition of searched strign is " + indexArray);
        logger.debug("Current search String is " + stringsSearch);
        setCursor(indexArray.get(currentIndex), stringsSearch.get(currentIndex), a);

    }

    public static void prevMatch(JTextArea a) {
        currentIndex -= 1;
        if (currentIndex < 0) {
            currentIndex = indexArray.size() - 1;
        }
        logger.debug("Current index is " + currentIndex);
        logger.debug("Current possition of searched strign is " + indexArray);
        logger.debug("Current search String is " + stringsSearch);
        setCursor(indexArray.get(currentIndex), stringsSearch.get(currentIndex), a);

    }

}
