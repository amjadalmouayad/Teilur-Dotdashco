import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static io.netty.util.internal.PlatformDependent.isWindows;
import static org.testng.Assert.assertEquals;


public class ContextMenuTest {
    //    Variable:
    static String TestURL = "http://localhost:7080/context_menu";


    private static WebDriver driver;

    public static void openURL() {
        // open | URL
        driver.get(TestURL);
        String Title = driver.getTitle();
        String ExpectedTitle = "The Internet";
        assertEquals(ExpectedTitle, Title);
    }

    public static void setWindowSize() {
        // setWindowSize | 1059x812
        driver.manage().window().setSize(new Dimension(1059, 812));
    }

    public static void verifyHeader() throws NoSuchElementException {
        // Verify | Header: Context Menu|
        String HeaderPage = driver.findElement(By.xpath("//*[@id=\"content\"]/div/h3")).getText();
        assertEquals("Context Menu", HeaderPage);
    }

    public static void rightClickAlert() throws NoSuchElementException {

        // Right click Box
        Actions actions = new Actions(driver);
        actions.contextClick(driver.findElement(By.id("hot-spot"))).perform();

        // Switching to Alert
        Alert alert = driver.switchTo().alert();

        // Capturing alert message.
        String alertMessage = driver.switchTo().alert().getText();

        // Displaying alert message
        System.out.println(alertMessage);
        // Accepting alert
        alert.accept();
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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1000));
    }

    @AfterClass
    // Close Browser
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void ContextMenu() {
        // Test name: Context Menu Test
        openURL();
        setWindowSize();
        verifyHeader();
        rightClickAlert();
    }
}
