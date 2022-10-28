import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import static io.netty.util.internal.PlatformDependent.isWindows;
import static org.testng.Assert.*;


public class DynamicContentTest {
    //    Variable:
    static String TestURL = "http://localhost:7080/dynamic_content";
    static String[] contentsArr;
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
            HeaderPage = driver.findElement(By.xpath("//*[@id=\"content\"]/div/h3")).getText();
            assertEquals("Dynamic Content", HeaderPage);
            System.out.println("Header " + HeaderPage + " - passed");
        } catch (AssertionError e) {
            System.out.println("Header " + HeaderPage + " - failed");
            throw e;
        }
    }

    // Test refreshes the page a couple of times.
    // Test asserts that the content changes on each refresh.
    public static void dynamicContentRandomRefresh() {
        Random r = new Random();
        int low = 2;
        int high = 5;
        int randomRefresh = low + r.nextInt(high);
        System.out.println("Random Refresh = " + randomRefresh);
        for (int i = 0; i < randomRefresh; i++) {
            indexingContents();
            refreshPage();
            assertContents();
        }

    }

    public static void indexingContents() throws NoSuchElementException {
        // Indexing Content in Array: Contents[]
        // list of image and text of contents
        List<WebElement> listOption = driver.findElements(By.xpath("//*[@id=\"content\"]/div/div"));
        List<WebElement> listImage = driver.findElements(By.tagName("img"));

        // Define size of array: contentsArr[]
        contentsArr = new String[listOption.size()];
        int ImageIndex = 1;
        int TextIndex = 2;
        // Indexing img URL & Text in Array: contentsArr[]
        for (int i = 1; i < listImage.size(); i++) {
            //identify separate div
            contentsArr[ImageIndex] = listImage.get(i).getAttribute("src");
            contentsArr[ImageIndex] = contentsArr[ImageIndex] + listOption.get(TextIndex).getText();
            ImageIndex++;
            TextIndex = TextIndex + 2;
        }

    }

    public static void refreshPage() {
        // Refresh Web Page

        driver.navigate().refresh();
        assertTrue(true, "Refresh Page - Failed");
    }


    public static void assertContents() throws NoSuchElementException {
        // Asserting content after page refresh | Test asserts that the content changes on each refresh.
        try {
            List<WebElement> verifyListOption = driver.findElements(By.xpath("//*[@id=\"content\"]/div/div"));
            List<WebElement> verifyListImage = driver.findElements(By.tagName("img"));
            int assertTextIndex = 2;
            for (int assertImageIndex = 1; assertImageIndex < verifyListImage.size(); assertImageIndex++) {
                assertNotEquals(verifyListImage.get(assertImageIndex).getAttribute("src") + verifyListOption.get(assertTextIndex).getText(), contentsArr[assertImageIndex]);
                System.out.println("Dynamic Content " + assertImageIndex + " - passed");
                assertTextIndex = assertTextIndex + 2;
            }

        } catch (AssertionError e) {
            System.out.println("Dynamic Content Test" + " - failed");
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
    public void DynamicContent() {
        // Test name: Dynamic Content Test
        openURL();
        setWindowSize();
        verifyHeader();
        dynamicContentRandomRefresh();
    }
}
