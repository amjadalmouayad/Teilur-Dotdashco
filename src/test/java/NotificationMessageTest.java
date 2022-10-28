import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static io.netty.util.internal.PlatformDependent.isWindows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class NotificationMessageTest {
    //    Variable:
    static String TestURL = "http://localhost:7080/notification_message_rendered";

    static String A1 = "Action successful\n" +
            "×";
    static String A2 = "Action unsuccesful, please try again\n" +
            "×";
    private static WebDriver driver;

    public static void openURL() {
        // open | URL
        String ExpectedTitle = null;
        try {
            driver.get(TestURL);
            String Title = driver.getTitle();
            ExpectedTitle = "The Internet";
            Assert.assertEquals(ExpectedTitle, Title);
            assert driver.getWindowHandles().size() == 1;
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
            Assert.assertEquals("Notification Message", HeaderPage);
            System.out.println("Header " + HeaderPage + " - passed");
        } catch (AssertionError e) {
            System.out.println("Header " + HeaderPage + " - failed");
            throw e;
        }
    }

    //    Test clicks on the Click Here link a multiple times.
    public static void clicksHereLinkMultipleTimes() {
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/p/a")).click();
    }

    //    Test asserts Flash Msg.
    public static void assertsActionSuccessful() {
        String flashMsg = null;
        try {
            flashMsg = driver.findElement(By.id("flash")).getText();
            assertThat(flashMsg, anyOf(is(A1), is(A2)));
            System.out.println("Test Flash " + flashMsg + " - Passed");

        } catch (AssertionError e) {
            System.out.println("Test Flash " + flashMsg + " - Failed");
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
    public void NotificationMessage() {
        // Test name: Notification Message Test
        openURL();
        setWindowSize();
        verifyHeader();
        clicksHereLinkMultipleTimes();
        assertsActionSuccessful();

    }
}
