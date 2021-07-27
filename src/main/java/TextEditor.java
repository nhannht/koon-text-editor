import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TextEditor extends JFrame {
    TextArea textArea = new TextArea();
    JPanel topPanel = new JPanel();
    private final JCheckBox regexCheck = new JCheckBox("Use regex");
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

    public TextEditor() {
        super("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setVisible(true);

        textArea.setName("textArea");


//        topPanel.setSize(500,100);
        topPanel.setName("topPanel");
        topPanel.setLayout(new FlowLayout());

        regexCheck.setName("UseRegExCheckbox");

        saveButton.setName("SaveButton");
        saveButton.setSize(100,100);


        loadButton.setName("OpenButton");
        loadButton.setSize(100,100);


        searchField.setName("SearchField");
        searchField.setSize(100,100);
        searchField.setPreferredSize(new Dimension(150,30));


        ImageIcon searchIcon = this.createImageIcon("search.png","Search Icon");
        searchIcon = this.scaleImage(searchIcon,20,20);
        searchButton.setName("StartSearchButton");
        searchButton.setIcon(searchIcon);

        prevSearchButton.setName("PreviousMatchButton");
        nextSearchButton.setName("NextMatchButton");

        topPanel.add(saveButton);
        topPanel.add(loadButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(regexCheck);
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
        jFileChooser.setPreferredSize(new Dimension(0,0));
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
        add(topPanel,BorderLayout.NORTH);
        getContentPane().add(jScrollPane);

        regexCheck.addChangeListener(e ->
        {
            if (regexCheck.isSelected()) Model.useRegex = true;
            else Model.useRegex = false;
            System.out.println(Model.useRegex);
        });


        menuUseRegexI.addActionListener(e -> {
            if (regexCheck.isSelected()) regexCheck.setSelected(false);
            else regexCheck.setSelected(true);
        });

        loadButton.addActionListener(e->{
            String c = null;
            try {
                c = Controller.loadFile(jFileChooser);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            textArea.setText(c);
        });

        saveButton.addActionListener(e ->{
            String content = textArea.getText();
            try {
                Controller.saveFile(content, jFileChooser);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        menuLoadTab.addActionListener(e ->{
            String c = null;
            try {
                c = Controller.loadFile(jFileChooser);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            textArea.setText(c);
        });

        menuSaveTab.addActionListener(e -> {
            String content = textArea.getText();
            try {
                Controller.saveFile(content, jFileChooser);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        menuExitTab.addActionListener(e -> Controller.quit(this));

        searchButton.addActionListener(e -> {
            Controller.search(textArea, searchField.getText(),Model.useRegex);
        });

        menuStartSearchI.addActionListener(e -> {
            Controller.search(textArea, searchField.getText(),Model.useRegex);
        });

        nextSearchButton.addActionListener(e -> {
            Controller.nextMatch(textArea);
        });

        menuNextMatchI.addActionListener(e -> {
            Controller.nextMatch(textArea);
        });

        prevSearchButton.addActionListener(e -> {
            Controller.prevMatch(textArea);
        });


        menuPrevMatchI.addActionListener(e -> {
            Controller.prevMatch(textArea);
        });
    }
    protected ImageIcon createImageIcon(String path,
                                        String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return  new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public ImageIcon scaleImage(ImageIcon icon,Integer width,Integer height){
        return new ImageIcon(icon.getImage().getScaledInstance(width,height,java.awt.Image.SCALE_SMOOTH));
    }

}
