package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

public class CheckOutCompletePage {
    private WebDriver driver;

    public CheckOutCompletePage(WebDriver driver) {
        this.driver = driver;
    }

    private By completeHeaderText = By.className("complete-header");
    private By completeHeaderTextDescription = By.className("complete-text");
    private By backHomeButton = By.id("back-to-products");
    private By ponyExpressImage = By.className("pony_express");

    public String getCompleteHeaderText(){
        return driver.findElement(completeHeaderText).getText();
    }
    public String getCompleteHeaderDescriptionText(){
        return driver.findElement(completeHeaderTextDescription).getText();
    }
    public String getBackHomeButtonColor(){
        Color color = Color.fromString(driver.findElement(backHomeButton).getCssValue("background-color"));
        return color.asHex();
    }
    public void clickBackHomeButton(){
        driver.findElement(backHomeButton).click();
    }
    public boolean isPonyExpressImageSpecsCorrect() {

        WebElement image = driver.findElement(ponyExpressImage);

        String maxHeight = image.getCssValue("max-height");
        String maxWidth = image.getCssValue("max-width");

        return maxHeight.equals("72px") && maxWidth.equals("72px");
    }
}
