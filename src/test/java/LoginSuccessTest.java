import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static io.netty.util.internal.PlatformDependent.isWindows;


public class LoginSuccessTest {
    //    Variable:
    static String TestURL = "http://localhost:7080/login";
    static String Username = "tomsmith";
    static String encodedPwd = "U3VwZXJTZWNyZXRQYXNzd29yZCE=";


    private static WebDriver driver;

    public static void openURL() {
        // open | URL
        driver.get(TestURL);
        String Title = driver.getTitle();
        String ExpectedTitle = "The Internet";
        Assert.assertEquals(ExpectedTitle, Title);
    }

    public static void setWindowSize() {
        // setWindowSize | 1059x812
        driver.manage().window().setSize(new Dimension(1059, 812));
    }

    public static void verifyLoginPage() throws NoSuchElementException {
        // Verify | Header: Login Page
        String HeaderPage = driver.findElement(By.xpath("//*[@id=\"content\"]/div/h2")).getText();
        Assert.assertEquals("Login Page", HeaderPage);
    }

    public static void insertUserName() throws NoSuchElementException {
        // click | id=username
        driver.findElement(By.id("username")).click();
        // insert | id=username
        driver.findElement(By.id("username")).sendKeys(Username);
    }

    public static void insertPassword() throws NoSuchElementException {
        // click | id=password
        driver.findElement(By.id("password")).click();
        // insert | id=password
        driver.findElement(By.id("password")).sendKeys(decodeString(encodedPwd));
    }

    public static void clickLoginButton() throws NoSuchElementException {
        // click | css=.fa
        driver.findElement(By.cssSelector(".fa")).click();
    }

    public static void verifyAlert() throws NoSuchElementException {
        // verifyAlert | id=flash
        String FlshText = driver.findElement(By.id("flash")).getText();
        System.out.println("FlashText:   =" + FlshText + "=");
        Assert.assertEquals("You logged into a secure area!\n" +
                "×", FlshText);
    }

    public static void clickLogoutButton() throws NoSuchElementException {
        // click | css=html |
        driver.findElement(By.cssSelector("html")).click();
        // click | linkText=Logout |
        driver.findElement(By.linkText("Logout")).click();
    }

    static String decodeString(String password) {
        // decode Password|
        byte[] decodedString = Base64.decodeBase64(password.getBytes());
        return (new String(decodedString));
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

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void loginSuccess() {
        // Test name: Login Success
        openURL();
        setWindowSize();
        verifyLoginPage();
        insertUserName();
        insertPassword();
        clickLoginButton();
        verifyAlert();
        clickLogoutButton();
    }
}
