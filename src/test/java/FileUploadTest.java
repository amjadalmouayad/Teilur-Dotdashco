import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static io.netty.util.internal.PlatformDependent.isWindows;
import static org.testng.Assert.assertEquals;


public class FileUploadTest {
    //    Variable:
    static String TestURL = "http://localhost:7080/upload";
    static String userprofile = System.getenv("USERPROFILE");
    static String fileName = "some-file.txt";
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
            assertEquals("File Uploader", HeaderPage);
            System.out.println("Header " + HeaderPage + " - passed");
        } catch (AssertionError e) {
            System.out.println("Header " + HeaderPage + " - failed");
            throw e;
        }
    }

    //    Test uses Upload Button or Drag and Drop to upload a file.
    public static void uploadFile() {
        WebElement uploadElement = driver.findElement(By.id("file-upload"));
        if (isWindows()) {
            // Windows Path
            uploadElement.sendKeys(userprofile + "\\Downloads\\" + fileName);
        } else {
            // Linux Path
            uploadElement.sendKeys(userprofile + "/Downloads/" + fileName);
        }
        driver.findElement(By.id("file-submit")).click();
    }

    //    Test asserts that the file is uploaded.
    public static void assertUploaded() {
        try {
            String headerUpload = driver.findElement(By.xpath("//*[@id=\"content\"]/div/h3")).getText();
            String fileUploaded = driver.findElement(By.id("uploaded-files")).getText();
            assertEquals(headerUpload, "File Uploaded!");
            assertEquals(fileUploaded, fileName);
            System.out.println("File Uploaded" + " - passed");
        } catch (AssertionError e) {
            System.out.println("File Uploaded" + " - failed");
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
    public void FileUpload() {
        // Test name: File UploadTest
        openURL();
        setWindowSize();
        verifyHeader();
        uploadFile();
        assertUploaded();
    }
}
