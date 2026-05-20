package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.Color;

public class CheckOutPage {
    private WebDriver driver;

    public CheckOutPage(WebDriver driver) {
        this.driver = driver;
    }


    private By firstNameField = By.id("first-name");
    private By lastNameField = By.id("last-name");
    private By zipPostalCode = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By cancelButton = By.id("cancel");
    private By errorMessage = By.xpath("//*[@id=\"checkout_info_container\"]/div/form/div[1]/div[4]");
    private By XerrorButton = By.className("error-button");

    public void enterFirstname(String Firstname) {
        driver.findElement(firstNameField).sendKeys(Firstname);
    }

    public void enterLastname(String Lastname) {
        driver.findElement(lastNameField).sendKeys(Lastname);
    }

    public void enterZipPostalCode(String zipCode) {
        driver.findElement(zipPostalCode).sendKeys(zipCode);
    }

    public void enterAllUserDataSucess() {
        driver.findElement(firstNameField).sendKeys("Muhamed");
        driver.findElement(lastNameField).sendKeys("Mavmudoski");
        driver.findElement(zipPostalCode).sendKeys("5020");
    }

    public void clickContinueButton() {
        driver.findElement(continueButton).click();
    }

    public void clickCancelButton() {
        driver.findElement(cancelButton).click();
    }

    public String getErrormessageText() {
        return driver.findElement(errorMessage).getText();
    }

    public String getErrorMessageColor() {
        Color color = Color.fromString(driver.findElement(errorMessage).getCssValue("background-color"));
        return color.asHex();
    }

    public String getContinueButtonColor() {
        Color color = Color.fromString(driver.findElement(continueButton).getCssValue("background-color"));
        return color.asHex();
    }

    public boolean isErrorMessageDisplayed() {

        try {
            String errorText = driver.findElement(errorMessage).getText();

            if (errorText.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickXonerrorButton(){
        driver.findElement(XerrorButton).click();
    }
}
