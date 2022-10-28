import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;

import static io.netty.util.internal.PlatformDependent.isWindows;
import static org.testng.Assert.assertEquals;


public class FileDownloadTest {
    //    Variable:
    static String TestURL = "http://localhost:7080/download";
    static String userprofile = System.getenv("USERPROFILE");
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
            assertEquals("File Downloader", HeaderPage);
            System.out.println("Header " + HeaderPage + " - passed");
        } catch (AssertionError e) {
            System.out.println("Header " + HeaderPage + " - failed");
            throw e;
        }
    }

    //    Test clicks on the file.
    public static void clicksDownloadFile() {
        driver.findElement(By.linkText("some-file.txt")).click();
        isFileDownloaded("some-file.txt");
    }

    //    Test asserts that the file is downloaded.
    public static void isFileDownloaded(String fileName) {
        try {
            String filePath;
            if (isWindows()) {
                // Windows Path
                filePath = userprofile + "\\Downloads\\" + fileName;
            } else {
                // Linux Path
                filePath = userprofile + "/Downloads/" + fileName;
            }
            File f = new File(filePath);
            assert f.exists();
            System.out.println("Exists");
        } catch (AssertionError e) {
            System.out.println("Does not Exists");
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
    public void FileDownload() {
        // Test name: File Download Test
        openURL();
        setWindowSize();
        verifyHeader();
        clicksDownloadFile();
    }
}
