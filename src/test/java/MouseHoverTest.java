import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static io.netty.util.internal.PlatformDependent.isWindows;

public class MouseHoverTest {
    //    Variable:
    static String TestURL = "http://localhost:7080/hovers";
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
            Assert.assertEquals("Hovers", HeaderPage);
            System.out.println("Header " + HeaderPage + " - passed");
        } catch (AssertionError e) {
            System.out.println("Header " + HeaderPage + " - failed");
            throw e;
        }
    }

    //    Test does a mouse hover on each image.
    public static void mouseHoverImg() {
        List<WebElement> listImage = driver.findElements(By.tagName("img"));
        for (int i = 1; i < listImage.size(); i++) {
            //identify separate div
            WebElement element = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[" + i + "]/img"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
            assertsAdditionalInfoImg(i);
        }

    }

    //    Test asserts that additional information is displayed for each image.
    public static void assertsAdditionalInfoImg(int i) {
        try {
            boolean iUser = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[" + i + "]/div/h5")).isDisplayed();
            boolean iLink = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[" + i + "]/div/a")).isDisplayed();
            Assert.assertTrue(iUser, "True");
            Assert.assertTrue(iLink, "True");
            System.out.println("Additional information is displayed for image. " + i + " - passed");
        } catch (AssertionError e) {
            System.out.println("Additional information is NOT displayed for image. " + i + " - passed");
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
    public void mouseHover() {
        // Test name: Mouse Hover Test
        openURL();
        setWindowSize();
        verifyHeader();
        mouseHoverImg();
    }
}
