import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class TextArea extends JTextArea {
    public TextArea(){
//        setBackground(Color.green);
        setName("TextArea");
        setBorder(new LineBorder(Color.BLACK));
        setMinimumSize(new Dimension(200,200));
        setMaximumSize(new Dimension(1600,1200));
        setPreferredSize(new Dimension(300,300));




    }

}
