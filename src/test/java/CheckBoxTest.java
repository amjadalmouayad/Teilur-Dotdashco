import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static io.netty.util.internal.PlatformDependent.isWindows;
import static org.testng.Assert.*;


public class CheckBoxTest {
    //    Variable:
    static String testURL = "http://localhost:7080/checkboxes";


    private static WebDriver driver;

    public static void openURL() {
        // open | URL
        driver.get(testURL);
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
        assertEquals("Checkboxes", HeaderPage);
    }

    public static void listElement() throws NoSuchElementException {
        // Get all Elements under checkboxes
        List<WebElement> allFormChildElements = driver.findElements(By.xpath("//*[@id=\"checkboxes\"]/*"));

        // Check & uncheck each element
        int i = 0;
        for (WebElement item : allFormChildElements) {
            if (item.getTagName().equals("input")) {
                i++;
                // Test uses assertions to make sure boxes were properly checked and unchecked.
                if ("checkbox".equals(item.getAttribute("type"))) {
                    WebElement checkbox = driver.findElement(By.xpath("//*[@id=\"checkboxes\"]/input[" + i + "]"));
                    if (!checkbox.isSelected()) {
                        checkbox.click();
                        System.out.println("Checkbox " + i + " checked");
                        assertTrue(checkbox.isSelected(), "Checkbox " + i + " Unchecked - Passed");
                    } else {
                        checkbox.click();
                        System.out.println("Checkbox " + i + " unchecked");
                        assertFalse(checkbox.isSelected(), "Checkbox " + i + " checked - Failed");
                    }
                }
            }
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
    public void CheckBox() {
        // Test name: Login Success
        openURL();
        setWindowSize();
        verifyHeader();
        listElement();
    }
}
