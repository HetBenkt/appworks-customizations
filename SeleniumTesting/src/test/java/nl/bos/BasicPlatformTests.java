package nl.bos;

import com.aventstack.extentreports.ExtentTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

class BasicPlatformTests extends WebDriverSetup {

    @Test
    void browserOpen() {
        ExtentTest test = getExtentReports().createTest("browserOpen()", "A simple test to open the browser");
        test.info("Start waiting for login screen");

        WebElement otdsUsername = null;
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(WAIT_SECONDS));
            otdsUsername = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("otds_username")));
            test.info("Login screen found");
            Thread.sleep(Duration.ofSeconds(getPropertiesReader().getIntPropertyValue("option.thread_sleep")).toMillis());
            test.pass("Test is passed!");
        } catch (Exception e) {
            test.fail("Login screen not found!");
        }
        Assertions.assertThat(otdsUsername).isNotNull();
    }

    @Test
    void createAndCancelInstance() throws InterruptedException {
        ExtentTest test = getExtentReports().createTest("createAndCancelInstance()", "Create and cancel the entity creation screen");

        login(test);

        test.info("Start waiting for UI loading (to create new instance)");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(WAIT_SECONDS));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[aria-label='Create a new item']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(getPropertiesReader().getStringPropertyValue("solution.creatable_option")))).click();
        test.info("Presence of creation dialog (to cancel the instance)");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ai-dialog.au-target.awlc-save-boundary.dialog-CreateItem")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[aria-label='Cancel']"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[aria-label='Menu']")));
        logout(test);
        test.pass("Test is passed!");
    }

    @Test
    void loginLogout() throws InterruptedException {
        ExtentTest test = getExtentReports().createTest("loginLogout()", "A simple login/logout check");
        login(test);
        logout(test);
        test.pass("Test is passed!");
    }
}
