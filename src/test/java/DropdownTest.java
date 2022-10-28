import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static io.netty.util.internal.PlatformDependent.isWindows;
import static org.testng.Assert.assertEquals;


public class DropdownTest {
    //    Variable:
    static String TestURL = "http://localhost:7080/dropdown";


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
        // Verify | Header: Dropdown List|
        String HeaderPage = null;
        try {
            HeaderPage = driver.findElement(By.xpath("//*[@id=\"content\"]/div/h3")).getText();
            assertEquals("Dropdown List", HeaderPage);
            System.out.println("Header " + HeaderPage + " - passed");
        } catch (AssertionError e) {
            System.out.println("Header " + HeaderPage + " - failed");
            throw e;
        }
    }

    // Click on DropDown Option, get all elements
    public static void dropDown() throws NoSuchElementException {
        Select dropdown = new Select(driver.findElement(By.id("dropdown")));
        List<WebElement> listOption = dropdown.getOptions();
        int size = listOption.size();
        for (int i = 1; i < size; i++) {
            String optionName = listOption.get(i).getText();
            selectOption(optionName, dropdown);
        }
    }

    // Select Option | Test selects Option 1 and Option 2 from the drop down menu.
    public static void selectOption(String OName, Select dropdownOP) {
        driver.findElement(By.xpath("//option[. = '" + OName + "']")).click();
        String selectedOption = dropdownOP.getFirstSelectedOption().getText();
        System.out.println("Selected Option=  " + selectedOption);
        assertOption(OName, selectedOption);
    }

    // Assert Option | Test asserts Option 1 and Option 2 are selected.
    public static void assertOption(String OName, String selectedOP) {
        try {
            assertEquals(OName, selectedOP);
            System.out.println(OName + " - passed");
        } catch (AssertionError e) {
            System.out.println(OName + " - failed");
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
    public void Dropdown() {
        // Test name: Dropdown
        openURL();
        setWindowSize();
        verifyHeader();
        dropDown();
    }
}
