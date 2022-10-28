import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static io.netty.util.internal.PlatformDependent.isWindows;
import static org.testng.Assert.assertEquals;

public class OpenNewTabTest {
    //    Variable:
    static String testURL = "http://localhost:7080/windows";
    static String newTabURL = "http://localhost:7080/windows/new";

    private static WebDriver driver;

    public static void openURL() {
        // open | URL
        String ExpectedTitle = null;
        try {
            driver.get(testURL);
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
            assertEquals("Opening a new window", HeaderPage);
            System.out.println("Header " + HeaderPage + " - passed");
        } catch (AssertionError e) {
            System.out.println("Header " + HeaderPage + " - failed");
            throw e;
        }
    }


    //    Test clicks on the Click Here link.
    public static void clicksHereLink() {
        String originalWindow = driver.getWindowHandle();
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/a")).click();
        // Loop through until we find a new window handle
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        String newWindow = driver.getWindowHandle();
        System.out.println("Original Tab: " + originalWindow + "\n" + "New Tab: " + newWindow);
        driver.switchTo().window(newWindow);
        assertEquals(driver.getCurrentUrl(), "http://localhost:7080/windows/new");
    }

    //    Test asserts that a new tab is opened with text New Window.
    public static void assertsNewTabOpened() {
        try {
            assertEquals(driver.getCurrentUrl(), newTabURL);
            assertEquals(driver.findElement(By.cssSelector("h3")).getText(), "New Window");
            System.out.println("Open new Tab & verify Text - Passed");
        } catch (AssertionError e) {
            System.out.println("Open new Tab - Failed");
            throw e;
        }
    }

    public void getOperatingSystem() {
//        String os = System.getProperty("os.name");
//         System.out.println("Using System Property: " + os);
        if (isWindows()) {
            System.out.println("This is Windows");
        } else {
            System.out.println("This is not Windows");
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
    public void OpenNewTab() {
        // Test name: Open New Tab Test
        openURL();
        setWindowSize();
        verifyHeader();
        clicksHereLink();
        assertsNewTabOpened();
        getOperatingSystem();

    }
}
