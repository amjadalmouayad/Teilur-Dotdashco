import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static io.netty.util.internal.PlatformDependent.isWindows;
import static org.testng.Assert.*;


public class DynamicControlsTest {
    //    Variable:
    static String TestURL = "http://localhost:7080/dynamic_controls";
    private static WebDriver driver;

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
            HeaderPage = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/h4[1]")).getText();
            assertEquals("Dynamic Controls", HeaderPage);
            System.out.println("Header " + HeaderPage + " - passed");
        } catch (AssertionError e) {
            System.out.println("Header " + HeaderPage + " - failed");
            throw e;
        }
    }

    //  Test clicks on the Remove Button and uses explicit wait.
    public static void clicksRemoveButton() throws NoSuchElementException {
        driver.findElement(By.cssSelector("#checkbox-example > button")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"loading\"]")));
    }

    //     Test asserts that the checkbox is gone.
    public static void assertsCheckboxGone() {

        try {
            List<WebElement> elements = driver.findElements(By.id("checkbox"));
            assert (elements.size() == 0);
            System.out.println("Test asserts that the checkbox is gone." + " - Passed");
        } catch (AssertionError e) {
            System.out.println("Test asserts that the checkbox is gone." + " - Failed");
            throw e;
        }
    }

    //    Test clicks on Add Button and uses explicit wait.
    public static void clicksAddButton() {
        driver.findElement(By.cssSelector("button:nth-child(1)")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"loading\"]")));
    }

    //    Test asserts that the checkbox is present.
    public static void assertsCheckboxPresent() {
        try {
            List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"checkbox-example\"]/div[1]"));
            assert (elements.size() > 0);
            System.out.println("Test asserts that the checkbox is present." + " - Passed");
        } catch (AssertionError e) {
            System.out.println("Test asserts that the checkbox is present." + " - Failed");
            throw e;
        }
    }

    //    Test clicks on the Enable Button and uses explicit wait.
    public static void clicksEnableButton() {
        driver.findElement(By.cssSelector("#input-example > button")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"loading\"]")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"loading\"]")));
    }

    //    Test asserts that the text box is enabled.
    public static void assertsTextBoxEnabled() {

        try {
            WebElement element = driver.findElement(By.cssSelector("#input-example > input"));
            boolean isEditable = element.isEnabled() && element.getAttribute("readonly") == null;
            assertTrue(isEditable);
            System.out.println("Element 3 is enabled");
        } catch (NoSuchElementException e) {
            System.out.println("Element 3 is not enabled");
        }
    }

    //    Test clicks on the Disable Button and uses explicit wait.
    public static void clicksDisableButton() {
        driver.findElement(By.cssSelector("#input-example > button")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"loading\"]")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"loading\"]")));
    }

    //    Test asserts that the text box is disabled.
    public static void assertsTextBoxDisabled() {
        try {
            WebElement element = driver.findElement(By.cssSelector("#input-example > input"));
            boolean isEditable = element.isEnabled() && element.getAttribute("readonly") == null;
            assertFalse(isEditable);
            System.out.println("Element 4 is not enabled");
        } catch (NoSuchElementException e) {
            System.out.println("Element 4 is enabled");
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
    public void DynamicControls() {
        // Test name: Dynamic Controls Test
        openURL();
        setWindowSize();
        verifyHeader();
        clicksRemoveButton();
        assertsCheckboxGone();
        clicksAddButton();
        assertsCheckboxPresent();
        clicksEnableButton();
        assertsTextBoxEnabled();
        clicksDisableButton();
        assertsTextBoxDisabled();
    }
}
