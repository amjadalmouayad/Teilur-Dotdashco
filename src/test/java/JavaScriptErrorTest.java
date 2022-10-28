import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static io.netty.util.internal.PlatformDependent.isWindows;
import static org.testng.Assert.assertEquals;

public class JavaScriptErrorTest {
    //    Variable:
    static String TestURL = "http://localhost:7080/javascript_error";
    private static WebDriver driver;

    public static void openURL() {
        // open | URL
        driver.get(TestURL);
        String ExpectedTitle = null;
    }

    public static void setWindowSize() {
        // setWindowSize | 1059x812 |
        driver.manage().window().setSize(new Dimension(1059, 812));
    }

    //    Test asserts that the page contains error: Cannot read property 'xyz' of undefined
    public static void assertsJSError() {
        try {
            String actual = "";
            String expected = "Cannot read properties of undefined (reading 'xyz')";
            LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);

            for (LogEntry entry : logEntries) {
                actual = entry.getMessage() + actual;
            }
            assertEquals(actual.substring(actual.length() - expected.length()), expected);
            System.out.println("page contains error: Cannot read properties of undefined (reading 'xyz') - Passed");
        } catch (AssertionError e) {
            System.out.println("page not contains error: Cannot read properties of undefined (reading 'xyz') - Failed");
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
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void JavaScriptError() {
        // Test name: JavaScript Error Test,
        // Test finds the JavaScript error on the page.
        openURL();
        setWindowSize();
        assertsJSError();
    }
}
