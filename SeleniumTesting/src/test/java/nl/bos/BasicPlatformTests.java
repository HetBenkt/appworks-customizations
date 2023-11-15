package nl.bos;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

//driver.switchTo().frame(driver.findElement(By.id("desktop")));
class BasicPlatformTests {

    private static WebDriver driver;
    private static final int THREAD_SLEEP_SECONDS = 2; //So you see something happening during development! :)
    private static final int WAIT_SECONDS = 30;
    private static PropertiesReader propertiesReader;

    @BeforeAll
    static void setup() {
        propertiesReader = new PropertiesReader("config.properties");
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void initDriver() {
        driver = new ChromeDriver(); //Because we close it after the test!
        driver.get(propertiesReader.getPropertyValue("otds.login_url"));
    }

    @Test
    void browserOpen() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS));
        WebElement otdsUsername = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("otds_username")));

        Thread.sleep(Duration.ofSeconds(THREAD_SLEEP_SECONDS).toMillis());
        Assertions.assertThat(otdsUsername).isNotNull();
    }

    @Test
    void createAndCancelInstance() throws InterruptedException {
        login();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[aria-label='Create a new item']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(propertiesReader.getPropertyValue("solution.creatable_option")))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ai-dialog.au-target.awlc-save-boundary.dialog-CreateItem")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[aria-label='Cancel']"))).click(); //By.xpath("//button[text()='Cancel']")
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[aria-label='Menu']")));

        logout();
    }

    @Test
    void loginLogout() throws InterruptedException {
        login();
        logout();
    }

    private static void logout() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[alt='User options']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[cmd='logout']"))).click();
        WebElement otdsUsername = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("otds_username")));

        Thread.sleep(Duration.ofSeconds(THREAD_SLEEP_SECONDS).toMillis());
        Assertions.assertThat(otdsUsername).isNotNull();
    }

    private static void login() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(WAIT_SECONDS));
        driver.findElement(By.id("otds_username")).sendKeys(propertiesReader.getPropertyValue("otds.username"));
        driver.findElement(By.id("otds_password")).sendKeys(propertiesReader.getPropertyValue("otds.password"));
        WebElement loginButton = driver.findElement(By.id("loginbutton"));
        Assertions.assertThat(loginButton.isEnabled()).isTrue();
        loginButton.click();
    }

    @AfterEach
    void closeDriver() {
        driver.close();
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
}
