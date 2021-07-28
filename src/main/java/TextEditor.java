import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class TextEditor extends JFrame {
    RSyntaxTextArea textArea = new RSyntaxTextArea();
    JPanel topPanel = new JPanel();
    private final JCheckBox regexCheckBox = new JCheckBox("Use regex");
    private final JButton saveButton = new JButton("Save");
    private final JButton loadButton = new JButton("Load");
    private final JTextField searchField = new JTextField();
    private final JButton searchButton = new JButton();
    public static final JButton prevSearchButton = new JButton("Prev");
    private final JButton nextSearchButton = new JButton("Next");
    private final JScrollPane jScrollPane = new JScrollPane(textArea);
    private final JMenuBar jMenuBar = new JMenuBar();
    private final JMenu jMenuTabFile = new JMenu("File");
    private final JMenuItem menuLoadTab = new JMenuItem("Load");
    private final JMenuItem menuSaveTab = new JMenuItem("Save");
    private final JMenuItem menuExitTab = new JMenuItem("Exit");
    private final JFileChooser jFileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
    Logger logger = LoggerFactory.getLogger(TextEditor.class);
    private final JComboBox jComboBox;
    /**
     * This state track regex status or not
     */
    Boolean useRegex;


    public TextEditor() {
        super("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setVisible(true);


        textArea.setName("textArea");
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_MARKDOWN);


//        topPanel.setSize(500,100);
        topPanel.setName("topPanel");
        topPanel.setLayout(new FlowLayout());

        regexCheckBox.setName("UseRegExCheckbox");

        saveButton.setName("SaveButton");
        saveButton.setSize(100, 100);


        loadButton.setName("OpenButton");
        loadButton.setSize(100, 100);
        HashMap<String,String> syntaxMap = new HashMap<>();
        syntaxMap.put("java",SyntaxConstants.SYNTAX_STYLE_JAVA);
        syntaxMap.put("markdown",SyntaxConstants.SYNTAX_STYLE_MARKDOWN);
        syntaxMap.put("python",SyntaxConstants.SYNTAX_STYLE_PYTHON);
        syntaxMap.put("unix",SyntaxConstants.SYNTAX_STYLE_UNIX_SHELL);
        syntaxMap.put("javascript",SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);

        jComboBox = new JComboBox(syntaxMap.keySet().toArray());
        jComboBox.setEditable(false);
        textArea.setSyntaxEditingStyle(syntaxMap.keySet().toArray()[0].toString());
        jComboBox.addActionListener(e -> {
            Object item = jComboBox.getSelectedItem();
            LogicAdapter.changeLanguageMode(textArea, syntaxMap.get(item));
            logger.error("Syntax set to " + item.toString());
        });

        topPanel.add(jComboBox);

        searchField.setName("SearchField");

        searchField.setSize(100, 100);
        searchField.setPreferredSize(new Dimension(150, 30));
        searchField.putClientProperty("JComponent.roundRect", true);


        ImageIcon searchIcon = this.createImageIcon("search.png", "Search Icon");
        searchIcon = this.scaleImage(searchIcon, 20, 20);
        searchButton.setName("StartSearchButton");
        searchButton.setIcon(searchIcon);

        prevSearchButton.setName("PreviousMatchButton");
        nextSearchButton.setName("NextMatchButton");

        topPanel.add(saveButton);
        topPanel.add(loadButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(regexCheckBox);
        topPanel.add(prevSearchButton);
        topPanel.add(nextSearchButton);

        jScrollPane.setName("ScrollPane");

        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


        jMenuTabFile.setName("MenuFile");
        menuLoadTab.setName("MenuOpen");
        menuSaveTab.setName("MenuSave");
        menuExitTab.setName("MenuExit");

        jFileChooser.setName("FileChooser");
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jFileChooser.setPreferredSize(new Dimension(0, 0));
        jFileChooser.setVisible(false);


        jMenuBar.add(jMenuTabFile);
        jMenuTabFile.add(menuLoadTab);
        jMenuTabFile.add(menuSaveTab);
        jMenuTabFile.addSeparator();
        jMenuTabFile.add(menuExitTab);


        JMenu searchBar = new JMenu("Search");
        JMenuItem menuStartSearchI = new JMenuItem("Start search");
        menuStartSearchI.setName("MenuStartSearch");
        JMenuItem menuPrevMatchI = new JMenuItem("Prev match");
        menuPrevMatchI.setName("MenuPreviousMatch");
        JMenuItem menuNextMatchI = new JMenuItem("NextMatch");
        menuNextMatchI.setName("MenuNextMatch");
        JMenuItem menuUseRegexI = new JMenuItem("Use regex");
        menuUseRegexI.setName("MenuUseRegExp");

        jMenuBar.add(searchBar);
        searchBar.add(menuStartSearchI);
        searchBar.add(menuPrevMatchI);
        searchBar.add(menuNextMatchI);
        searchBar.add(menuUseRegexI);


        setJMenuBar(jMenuBar);
        add(jFileChooser);
        add(topPanel, BorderLayout.NORTH);
        getContentPane().add(jScrollPane);

        regexCheckBox.addChangeListener(e ->
        {
            if (regexCheckBox.isSelected()) useRegex = true;
            else useRegex = false;
            System.out.println(useRegex);
        });


        menuUseRegexI.addActionListener(e -> {
            if (regexCheckBox.isSelected()) regexCheckBox.setSelected(false);
            else regexCheckBox.setSelected(true);
        });

        loadButton.addActionListener(e -> {
            String c = null;
            try {
                c = LogicAdapter.loadFile(jFileChooser);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            textArea.setText(c);
        });

        saveButton.addActionListener(e -> {
            String content = textArea.getText();
            try {
                LogicAdapter.saveFile(content, jFileChooser);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        menuLoadTab.addActionListener(e -> {
            String c = null;
            try {
                c = LogicAdapter.loadFile(jFileChooser);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            textArea.setText(c);
        });

        menuSaveTab.addActionListener(e -> {
            String content = textArea.getText();
            try {
                LogicAdapter.saveFile(content, jFileChooser);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        menuExitTab.addActionListener(e -> LogicAdapter.quit(this));

        searchButton.addActionListener(e -> {
            LogicAdapter.search(textArea, searchField.getText(), useRegex);
        });

        menuStartSearchI.addActionListener(e -> {
            LogicAdapter.search(textArea, searchField.getText(), useRegex);
        });

        nextSearchButton.addActionListener(e -> {
            LogicAdapter.nextMatch(textArea);
        });

        menuNextMatchI.addActionListener(e -> {
            LogicAdapter.nextMatch(textArea);
        });

        prevSearchButton.addActionListener(e -> {
            LogicAdapter.prevMatch(textArea);
        });


        menuPrevMatchI.addActionListener(e -> {
            LogicAdapter.prevMatch(textArea);
        });
    }

    protected ImageIcon createImageIcon(String path,
                                        String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public ImageIcon scaleImage(ImageIcon icon, Integer width, Integer height) {
        return new ImageIcon(icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
    }

}
