import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.concurrent.TimeUnit;

@Listeners(Listener.class)

public class Annotations {

    protected WebDriver driver;

    @Parameters("browser")
    @BeforeClass
    protected WebDriver getDriver(String browser) {
        if(browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
            driver = new ChromeDriver();
        }
        else if(browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/drivers/geckodriver.exe");
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver;
    }

    @AfterClass
    protected void tearDown() {
        if (driver != null)
            driver.quit();
    }

    @Test
    public void ProductCreation() {System.out.println("ProductCreation execution");}

    @Test(dependsOnMethods = "ProductCreation")
    public void AssertProductCreation() {System.out.println("AssertProductCreation execution");}
}
