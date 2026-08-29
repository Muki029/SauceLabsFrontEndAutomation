package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;

public class Footer {
    private WebDriver driver;
    private WebDriverWait wait;

    public Footer(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    By twitterButton = By.cssSelector(".social_twitter a");
    By facebookButton = By.cssSelector(".social_facebook a");
    By linkedInButton = By.cssSelector(".social_linkedin a");
    By footerMessage = By.className("footer_copy");


    public void switchToNewTab() {
        wait.until(d -> d.getWindowHandles().size() > 1);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
    }

    public void clickTwitterbutton() {
        ClickUtil.click(driver, driver.findElement(twitterButton));
    }

    public void clickFacebookButton() {
        ClickUtil.click(driver, driver.findElement(facebookButton));
    }

    public void clickLinkedInButton() {
        ClickUtil.click(driver, driver.findElement(linkedInButton));
    }

    public String footerMessage() {
        return driver.findElement(footerMessage).getText();
    }
}
