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

    By twitterButton = By.className("social_twitter");
    By facebookButton = By.className("social_facebook");
    By linkedInButton = By.className("social_linkedin");
    By footerMessage = By.className("footer_copy");


    public void switchToNewTab() {
        wait.until(d -> d.getWindowHandles().size() > 1);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
    }

    public void clickTwitterbutton() {
        driver.findElement(twitterButton).click();
    }

    public void clickFacebookButton() {
        driver.findElement(facebookButton).click();
    }

    public void clickLinkedInButton() {
        driver.findElement(linkedInButton).click();
    }

    public String footerMessage() {
        return driver.findElement(footerMessage).getText();
    }
}
