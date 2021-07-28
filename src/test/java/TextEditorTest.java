import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.*;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.finder.JFileChooserFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JFileChooserFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TextEditorTest {
    private FrameFixture window;

    @BeforeClass
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Before
    public void setUp() {
        TextEditor frame = GuiActionRunner.execute(TextEditor::new);
        window = new FrameFixture(frame);
        window.show();
    }

    @After
    public void tearDown() {
        window.cleanUp();
    }

    @Test
    public void testNormalEnterTestCase(){

        JTextComponentFixture textArea = window.textBox("textArea");
        String textCase = "A normal string";
        textArea.enterText(textCase);
        textArea.requireText(textCase);
    }

    @Test
    public void testEnterSuperLongTest() {
        JTextComponentFixture textArea = window.textBox("textArea");
        String textCase = RandomStringUtils.randomAlphabetic(1000000);
        textArea.setText(textCase);
        textArea.requireEditable();
        textArea.requireText(textCase);
        textArea.deleteText();
        textArea.requireEmpty();

    }

//    @GUITest
//    @Test
//    public void testLoadFunction() {
//        JButtonFixture loadButton = window.button("OpenButton");
//        JTextComponentFixture textArea = window.textBox("textArea");
//        loadButton.click();
//        Robot robot = window.robot();
//
//        JFileChooserFixture jFileChooserFixture = new JFileChooserFixture(robot, "FileChooser");
//
//        final String filePath = "src/main/resources/";
//        jFileChooserFixture.setCurrentDirectory(new File(filePath));
//        final String fileName = "patrol.txt";
//        jFileChooserFixture.selectFile(new File(fileName));
//        jFileChooserFixture.approve();
//        File file = new File(filePath + fileName);
//        try {
//            String expect = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
//            textArea.requireText(expect);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//    @GUITest
//    @Test
//    public void testSearch(){}

}
