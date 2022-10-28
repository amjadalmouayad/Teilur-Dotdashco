import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static io.netty.util.internal.PlatformDependent.isWindows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;


public class DragDropTest {
    //    Variable:
    static String TestURL = "http://localhost:7080/drag_and_drop";
    private static WebDriver driver;

    public static void openURL() {
        // open | URL
        driver.get(TestURL);
        String Title = driver.getTitle();
        String ExpectedTitle = "The Internet";
        assertEquals(ExpectedTitle, Title);
    }

    public static void setWindowSize() {
        // setWindowSize | 1059x812 |
        driver.manage().window().setSize(new Dimension(1059, 812));
    }

    public static void verifyHeader() throws NoSuchElementException {
        // Verify | Header: Checkboxes|
        String HeaderPage = driver.findElement(By.xpath("//*[@id=\"content\"]/div/h3")).getText();
        assertEquals("Drag and Drop", HeaderPage);
    }

    public static void dragAndDropToObjectA2B() throws NoSuchElementException {
        try {
            // Test drags element A to element B.
            WebElement dragged = driver.findElement(By.xpath("//*[@id=\"column-a\"]"));
            WebElement dropped = driver.findElement(By.xpath("//*[@id=\"column-b\"]"));
            Actions builder = new Actions(driver);
            Action dragAndDrop = builder.clickAndHold(dragged).moveToElement(dropped).release(dropped).build();
            dragAndDrop.perform();

            // Test asserts that the text on element A and B are switched.
            assertThat(driver.findElement(By.id("column-a")).getText(), is("B"));
            assertThat(driver.findElement(By.id("column-b")).getText(), is("A"));
            System.out.println("Test asserts that the text on element A and B are switched - Passed");
        } catch (AssertionError e) {
            System.out.println("Test asserts that the text on element A and B are switched - Failed");
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
    public void DragDrop() throws InterruptedException {
        // Test name: Drag & Drop Test
        openURL();
        setWindowSize();
        verifyHeader();
        dragAndDropToObjectA2B();
    }
}
