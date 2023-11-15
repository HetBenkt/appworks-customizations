package nl.bos;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
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

//driver.switchTo().frame(driver.findElement(By.id("desktop")));
class BasicPlatformTests {

    private static WebDriver driver;
    private static final int WAIT_SECONDS = 30;
    private static PropertiesReader propertiesReader;
    private static ExtentReports extent;

    @BeforeAll
    static void setup() {
        ExtentSparkReporter spark = new ExtentSparkReporter("index.html");
        spark.config().setTheme(Theme.DARK);
        spark.config().setReportName("Report for " + MethodHandles.lookup().lookupClass());

        extent = new ExtentReports();
        extent.attachReporter(spark);

        propertiesReader = new PropertiesReader("config.properties");
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void initDriver() {
        ChromeOptions options = new ChromeOptions();
        if(propertiesReader.getBooleanPropertyValue("option.headless")) {
            options.addArguments("--log-level=3", "--headless", "--disable-extensions", "--incognito");
        } else {
            options.addArguments("--disable-extensions", "--incognito");
        }
        ChromeDriverService service = ChromeDriverService.createDefaultService();
        driver = new ChromeDriver(service, options); //Because we close it after the test!
        driver.get(propertiesReader.getStringPropertyValue("otds.login_url"));
    }

    @Test
    void browserOpen() {
        ExtentTest test = extent.createTest("browserOpen()", "A simple test to open the browser");
        test.info("Start waiting for login screen");

        WebElement otdsUsername = null;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS));
            otdsUsername = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("otds_username")));
            test.info("Login screen found");
            Thread.sleep(Duration.ofSeconds(propertiesReader.getIntPropertyValue("option.thread_sleep")).toMillis());
            test.pass("Test is passed!");
        } catch (Exception e) {
            test.fail("Login screen not found!");
        }
        Assertions.assertThat(otdsUsername).isNotNull();
    }

    @Test
    void createAndCancelInstance() throws InterruptedException {
        ExtentTest test = extent.createTest("createAndCancelInstance()", "Create and cancel the entity creation screen");

        login(test);

        test.info("Start waiting for UI loading");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[aria-label='Create a new item']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(propertiesReader.getStringPropertyValue("solution.creatable_option")))).click();
        test.info("Presence of creation dialog");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ai-dialog.au-target.awlc-save-boundary.dialog-CreateItem")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[aria-label='Cancel']"))).click(); //By.xpath("//button[text()='Cancel']")
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[aria-label='Menu']")));

        logout(test);

        test.pass("Test is passed!");
    }

    @Test
    void loginLogout() throws InterruptedException {
        ExtentTest test = extent.createTest("loginLogout()", "A simple login/logout check");
        login(test);
        logout(test);
        test.pass("Test is passed!");
    }

    private static void logout(ExtentTest test) throws InterruptedException {
        test.info("Do logout");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[alt='User options']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[cmd='logout']"))).click();
        WebElement otdsUsername = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("otds_username")));

        Thread.sleep(Duration.ofSeconds(propertiesReader.getIntPropertyValue("option.thread_sleep")).toMillis());
        Assertions.assertThat(otdsUsername).isNotNull();
        test.info("Logout done");
    }

    private static void login(ExtentTest test) {
        test.info("Do login");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(WAIT_SECONDS));
        driver.findElement(By.id("otds_username")).sendKeys(propertiesReader.getStringPropertyValue("otds.username"));
        driver.findElement(By.id("otds_password")).sendKeys(propertiesReader.getStringPropertyValue("otds.password"));
        WebElement loginButton = driver.findElement(By.id("loginbutton"));
        Assertions.assertThat(loginButton.isEnabled()).isTrue();
        loginButton.click();
        test.info("Login done");
    }

    @AfterEach
    void closeDriver() {
        driver.close();
        extent.flush();
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
}
