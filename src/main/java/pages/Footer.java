package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

public class Footer {
    private WebDriver driver;

    public Footer(WebDriver driver) {
        this.driver = driver;
    }

    By twitterButton = By.className("social_twitter");
    By facebookButton = By.className("social_facebook");
    By linkedInButton = By.className("social_linkedin");
    By footerMessage = By.className("footer_copy");


    public void switchToNewTab() {
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
