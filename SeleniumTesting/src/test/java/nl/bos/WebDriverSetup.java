package nl.bos;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.invoke.MethodHandles;
import java.time.Duration;

//In case we forget how to switch to an iFrame!
//driver.switchTo().frame(driver.findElement(By.id("desktop")));
public class WebDriverSetup {
    private static WebDriver driver;
    protected static final int WAIT_SECONDS = 30;
    private static PropertiesReader propertiesReader;
    private static ExtentReports extentReports;

    @BeforeAll
    static void setup() {
        ExtentSparkReporter spark = new ExtentSparkReporter("index.html");
        spark.config().setTheme(Theme.DARK);
        spark.config().setReportName("Report for " + MethodHandles.lookup().lookupClass());

        extentReports = new ExtentReports();
        extentReports.attachReporter(spark);

        propertiesReader = new PropertiesReader("config.properties");
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void initDriver() {
        ChromeOptions options = new ChromeOptions();
        if(getPropertiesReader().getBooleanPropertyValue("option.headless")) {
            options.addArguments("--log-level=3", "--headless", "--disable-extensions", "--incognito");
        } else {
            options.addArguments("--disable-extensions", "--incognito");
        }
        ChromeDriverService service = ChromeDriverService.createDefaultService();
        driver = new ChromeDriver(service, options); //Because we close it after the test!
        driver.get(getPropertiesReader().getStringPropertyValue("otds.login_url"));
    }

    @AfterEach
    void closeDriver() {
        driver.close();
        extentReports.flush();
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    protected static void logout(ExtentTest test) throws InterruptedException {
        test.info("Do logout");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(WAIT_SECONDS));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[alt='User options']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[cmd='logout']"))).click();
        WebElement otdsUsername = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("otds_username")));

        Thread.sleep(Duration.ofSeconds(getPropertiesReader().getIntPropertyValue("option.thread_sleep")).toMillis());
        Assertions.assertThat(otdsUsername).isNotNull();
        test.info("Logout done");
    }

    protected static void login(ExtentTest test) {
        test.info("Do login");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(WAIT_SECONDS));
        getDriver().findElement(By.id("otds_username")).sendKeys(getPropertiesReader().getStringPropertyValue("otds.username"));
        getDriver().findElement(By.id("otds_password")).sendKeys(getPropertiesReader().getStringPropertyValue("otds.password"));
        WebElement loginButton = getDriver().findElement(By.id("loginbutton"));
        Assertions.assertThat(loginButton.isEnabled()).isTrue();
        loginButton.click();
        test.info("Login done");
    }

    public static PropertiesReader getPropertiesReader() {
        return propertiesReader;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static ExtentReports getExtentReports() {
        return extentReports;
    }
}
