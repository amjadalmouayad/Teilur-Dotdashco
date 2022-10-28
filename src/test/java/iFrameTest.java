import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static io.netty.util.internal.PlatformDependent.isWindows;
import static org.testng.Assert.assertEquals;


public class iFrameTest {
    //    Variable:
    static String TestURL = "http://localhost:7080/iframe";
    static JavascriptExecutor js;
    static String TypesText = "Some Text";
    private static WebDriver driver;
    private Map<String, Object> vars;

    public static void openURL() {
        // open | URL
        String ExpectedTitle = null;
        try {
            driver.get(TestURL);
            String Title = driver.getTitle();
            ExpectedTitle = "The Internet";
            assertEquals(ExpectedTitle, Title);
            System.out.println(ExpectedTitle + " - passed");
        } catch (AssertionError e) {
            System.out.println(ExpectedTitle + " - failed");
            throw e;
        }
    }

    public static void setWindowSize() {
        // setWindowSize | 1059x812 |
        driver.manage().window().setSize(new Dimension(1059, 812));
    }

    public static void verifyHeader() throws NoSuchElementException {
        // Verify | Header: Dynamic Content|
        String HeaderPage = null;
        try {
            HeaderPage = driver.findElement(By.xpath("//*[@id=\"content\"]/div/h3")).getText();
            assertEquals("An iFrame containing the TinyMCE WYSIWYG Editor", HeaderPage);
            System.out.println("Header " + HeaderPage + " - passed");
        } catch (AssertionError e) {
            System.out.println("Header " + HeaderPage + " - failed");
            throw e;
        }
    }

    //    Test switches to Iframe and types some text.
    public static void typesText() {
        driver.findElement(By.cssSelector(".tox-notification__dismiss svg")).click();

        driver.switchTo().frame(0);
        driver.findElement(By.cssSelector("p")).click();
        driver.findElement(By.cssSelector("html")).click();
        WebElement element = driver.findElement(By.id("tinymce"));
        js.executeScript("if(arguments[0].contentEditable === 'true') {arguments[0].innerText = '" + TypesText + "'}", element);
    }

    //    Test asserts that the typed text is as expected.
    public static void assertText() {

        String iframeText = null;
        try {
            iframeText = driver.findElement(By.id("tinymce")).getText();
            assertEquals(TypesText, iframeText);
            System.out.println("iFrame Text: " + iframeText + " - passed");
        } catch (AssertionError e) {
            System.out.println("iFrame Text: " + iframeText + " - failed");
            throw e;
        }

    }

    @BeforeClass
    public void setUp() {
        if (isWindows()) {
            System.out.println("This is Windows");
            // Windows WebDriver |  ChromeDriver 106.0.5249.61
            System.setProperty("webdriver.chrome.driver", "driver\\Windows\\chromedriver.exe");
        } else {
            System.out.println("This is not Windows");
            // Linux WebDriver  |  ChromeDriver 106.0.5249.61
            System.setProperty("webdriver.chrome.driver", "driver/Linux/chromedriver");
        }
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void iFrame() {
        // Test name: iFrameTest Test
        openURL();
        setWindowSize();
        verifyHeader();
        typesText();
        assertText();
    }
}
