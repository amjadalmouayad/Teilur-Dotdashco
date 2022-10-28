import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

import static io.netty.util.internal.PlatformDependent.isWindows;
import static org.testng.Assert.assertEquals;

public class JavaScriptAlertsTest {
    //    Variable:
    static String TestURL = "http://localhost:7080/javascript_alerts";
    static String typeMsg;
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
            assertEquals("JavaScript Alerts", HeaderPage);
            System.out.println("Header " + HeaderPage + " - passed");
        } catch (AssertionError e) {
            System.out.println("Header " + HeaderPage + " - failed");
            throw e;
        }
    }

    //    Test Clicks on JS Alert Button.
    public static void clicksAlertButton() {
        driver.findElement(By.cssSelector("li:nth-child(1) > button")).click();
        assertsAlertMessage("I am a JS Alert");
        acceptAlert();
        assertsResultMsg("You successfuly clicked an alert");
    }

    //    Test clicks on JS confirm Button and clicks ok on alert.
    public static void clicksJSConfirmButtonAndAlert() {
        driver.findElement(By.cssSelector("li:nth-child(2) > button")).click();
        assertsAlertMessage("I am a JS Confirm");
        acceptAlert();
        assertsResultMsg("You clicked: Ok");
    }

    //     Test clicks on JS Prompt Button and types a message on Prompt.
    public static void clicksJSPromptButtonTypesMessage() {
        driver.findElement(By.cssSelector("li:nth-child(3) > button")).click();
        Alert alert = driver.switchTo().alert();
        typeMsg = String.valueOf(generateRandomNumber());
        alert.sendKeys(typeMsg);
        assertsAlertMessage("I am a JS prompt");
        acceptAlert();
        assertsResultMsg("You entered: " + typeMsg);
    }


    public static void acceptAlert() {
        Alert alert = driver.switchTo().alert();
        WebDriverWait waitAlert = new WebDriverWait(driver, Duration.ofSeconds(5));
        //alertIsPresent() condition applied
        if (waitAlert.until(ExpectedConditions.alertIsPresent()) != null) {
            System.out.println("Alert exists");
            alert.accept();
        } else {
            System.out.println("Alert not exists");
        }
    }

    //    Test asserts the alert message.
    public static void assertsAlertMessage(String alertMsg) {
        String alertText = driver.switchTo().alert().getText();
        try {
            assertEquals(alertText, alertMsg);
            System.out.println("Alert Text - " + alertText + " - Passed");
        } catch (AssertionError e) {
            System.out.println("Alert Text - " + alertText + " - Failed");
        }
    }

    //    Test asserts that the alert message contains the typed message.
    public static void assertsResultMsg(String resultMsg) {
        String resultMsgText = driver.findElement(By.id("result")).getText();
        try {
            assertEquals(resultMsgText, resultMsg);
            System.out.println("Result Msg Text - " + resultMsgText + " - Passed");
        } catch (AssertionError e) {
            System.out.println("Result Msg Text - " + resultMsgText + " - Failed");
        }
    }

    //    Generate Random Number
    public static int generateRandomNumber() {
        Random r = new Random();
        int low = 1;
        int high = 500;
        int randomNumber = low + r.nextInt(high);
        System.out.println("Random Number = " + randomNumber);
        return randomNumber;
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
    public void JavaScriptAlerts() {
        // Test name: JavaScript Alerts Test
        openURL();
        setWindowSize();
        verifyHeader();
        clicksAlertButton();
        clicksJSConfirmButtonAndAlert();
        clicksJSPromptButtonTypesMessage();

    }
}
