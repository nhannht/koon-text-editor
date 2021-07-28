import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;

public class ApplicationRunner {
    public static void main(String[] args) {
        try {

            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            UIManager.put( "Button.arc", 100 );
        } catch (Exception ignored) {
        }
        new TextEditor();
    }
}
