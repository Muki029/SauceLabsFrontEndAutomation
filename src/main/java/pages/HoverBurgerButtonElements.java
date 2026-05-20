package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HoverBurgerButtonElements {
    private WebDriver driver;
    private Actions actions;
    private HoverBurgerButtonElements elements;


    public HoverBurgerButtonElements(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
    }

    public void hoverOverElement(WebElement elements) {
        actions.moveToElement(elements).perform();
    }

    private By allItemsButton = By.id("inventory_sidebar_link");
    private By aboutButton = By.id("about_sidebar_link");
    private By logOutButton = By.id("logout_sidebar_link");
    private By resetAppState = By.id("reset_sidebar_link");

    public WebElement allItemsElement() {
        return driver.findElement(allItemsButton);
    }

    public WebElement aboutButtonElement() {
        return driver.findElement(aboutButton);
    }

    public WebElement logOutElement() {
        return driver.findElement(logOutButton);
    }

    public WebElement resetAppState() {
        return driver.findElement(resetAppState);
    }

    public void hoverOverAllElements() {

        List<WebElement> menuItems = new ArrayList<>();
        menuItems.add(elements.allItemsElement());
        menuItems.add(elements.aboutButtonElement());
        menuItems.add(elements.logOutElement());
        menuItems.add(elements.resetAppState());

        for (int i = 0; i < menuItems.size(); i++) {
            WebElement currentItem = menuItems.get(i);
            elements.hoverOverElement(currentItem);

        }
    }
        public String getHoverElementsColor(){
            Color getHoverColor = Color.fromString(driver.findElement(allItemsButton).getCssValue("color"));
            return getHoverColor.asHex();
        }

}
