package nl.bos;

import com.aventstack.extentreports.ExtentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class ClientSpecificTests extends WebDriverSetup {

    @Test
    void createAndOpenInstance() throws InterruptedException {
        ExtentTest test = getExtentReports().createTest("createAndCancelInstance()", "Create and cancel the entity creation screen");

        login(test);

        test.info("Start waiting for UI loading (to create new instance)");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(WAIT_SECONDS));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[aria-label='Create a new item']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(getPropertiesReader().getStringPropertyValue("solution.creatable_option")))).click();
        test.info("Presence of creation dialog (to create and open the instance)");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ai-dialog.au-target.awlc-save-boundary.dialog-CreateItem")));

        test.info("Fill in the form with data");
        //TODO Make this flexible (by input file?) to fill in a 'Create' form (with optional/required data and different data types)!
        WebElement inputField1 = getDriver().findElement(By.xpath("//div[contains(@style, 'flex-direction: row')]/div[label[contains(text(), '{label_name}')]]/following-sibling::input".replace("{label_name}", getPropertiesReader().getStringPropertyValue("solution.create.field1"))));
        inputField1.sendKeys(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        WebElement inputField2 = getDriver().findElement(By.xpath("//div[contains(@style, 'flex-direction: row')]/div[label[contains(text(), '{label_name}')]]/following-sibling::input".replace("{label_name}", getPropertiesReader().getStringPropertyValue("solution.create.field2"))));
        inputField2.sendKeys("john@doe.io");
        WebElement inputField3 = getDriver().findElement(By.xpath("//div[contains(@style, 'flex-direction: row')]/div[label[contains(text(), '{label_name}')]]/following-sibling::input".replace("{label_name}", getPropertiesReader().getStringPropertyValue("solution.create.field3"))));
        inputField3.sendKeys("John Doe");
        getDriver().findElement(By.xpath("//adjustable-container//label[@title='{label_name}']/ancestor::div[contains(@class, 'adjustable-container')][1]//bs-image-button[.//img[@alt='Browse']]/button".replace("{label_name}", getPropertiesReader().getStringPropertyValue("solution.create.field4")))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ai-dialog-header/div/h5[text()='{label_name}']".replace("{label_name}", getPropertiesReader().getStringPropertyValue("solution.create.field5")))));

        Thread.sleep(Duration.ofSeconds(getPropertiesReader().getIntPropertyValue("option.thread_sleep")).toMillis());

        //No need to check if the action should be "Create and open" | "Create" | "Save and create another"
        //By.xpath("//button[text()='Create and open']")
        //In incognito modes it always starts with "Create"!
        test.info("Create and open the instance");
        wait.until(ExpectedConditions.elementToBeClickable(By.className("ot-drop-button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li[title='Create and open']"))).click();
        test.info("Instance successfully created");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.success")));

        Thread.sleep(Duration.ofSeconds(getPropertiesReader().getIntPropertyValue("option.thread_sleep")).toMillis());

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[aria-label='Menu']")));
        logout(test);
        test.pass("Test is passed!");
    }
}
