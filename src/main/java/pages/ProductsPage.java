package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductsPage {
    private WebDriver driver;
    private Actions actions;
    private Header header;
    private WebDriverWait wait;


    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By productsTitle = By.className("title");
    private By dropDownSorting = By.className("product_sort_container");
    private By productsPriceList = By.className("inventory_item_price");
    private By productsNameList = By.className("inventory_item_name");
    private By backpackProductTitle = By.xpath("//*[@id=\"item_4_title_link\"]/div");
    private By burgerButton = By.id("react-burger-menu-btn");
    private By burgerButtonList = By.cssSelector(".bm-item-list .menu-item");

    public String getPageTitle() {
        return driver.findElement(productsTitle).getText();
    }

    public List<WebElement> getAllOptionsFromSortingDropdown() {
        Select orderingDropdown = new Select(driver.findElement(dropDownSorting));

        return orderingDropdown.getOptions();
    }

    public void selectOrderingDropdownOption(int optionIndex) {

        Select orderingDropdown = new Select(driver.findElement(dropDownSorting));

        orderingDropdown.selectByIndex(optionIndex);
    }

    public String getTextFromOrderingDropdown() {
        Select orderingDropdown = new Select(driver.findElement(dropDownSorting));

        return orderingDropdown.getFirstSelectedOption().getText();
    }

    public boolean areAllProductsPricesDescending() {
        List<Double> productsPrice = new ArrayList<>();

        List<WebElement> priceElements = driver.findElements(productsPriceList);
        for (int i = 0; i < priceElements.size(); i++) {
            productsPrice.add(Double.parseDouble(priceElements.get(i).getText().substring(1)));
        }
        for (int i = 0; i < productsPrice.size() - 1; i++) {
            if (productsPrice.get(i) < productsPrice.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public boolean areAllProductsPricesIncreasing() {
        List<Double> productsPrice = new ArrayList<>();

        List<WebElement> priceElements = driver.findElements(productsPriceList);
        for (int i = 0; i < priceElements.size(); i++) {
            productsPrice.add(Double.parseDouble(priceElements.get(i).getText().substring(1)));
        }
        for (int i = 0; i < productsPrice.size() - 1; i++) {
            if (productsPrice.get(i) > productsPrice.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public List<String> getAllProductsNames() {
        List<String> productNames = new ArrayList<>();

        List<WebElement> nameElements = driver.findElements(productsNameList);

        for (int i = 0; i < nameElements.size(); i++) {
            productNames.add(nameElements.get(i).getText());
        }

        return productNames;
    }

    public List<String> getAllProductsHoverColor() {

        List<String> hoverColors = new ArrayList<>();

        List<WebElement> nameElements = driver.findElements(productsNameList);

        for (WebElement element : nameElements) {
            actions.moveToElement(element).perform();
            String rgbaColor = element.getCssValue("color");

            Color parsedColor = Color.fromString(rgbaColor);
            String hexColor = parsedColor.asHex();

            hoverColors.add(hexColor);
        }
        return hoverColors;
    }

    public void hoverBackpackTitle() {
        WebElement backpackTitle = driver.findElement(backpackProductTitle);

        actions.moveToElement(backpackTitle).perform();
    }

    public String getColorFromBackPackTitle() {
        Color productTitleColor = Color.fromString(driver.findElement(backpackProductTitle).getCssValue("color"));
        return productTitleColor.asHex();
    }

    public void clickBurgerButton() {
        ClickUtil.click(driver, driver.findElement(burgerButton));
        wait.until(ExpectedConditions.visibilityOfElementLocated(burgerButtonList));
    }

    public List<String> getBurgerMenuColorsOnHover() {

        List<String> hoverColors = new ArrayList<>();

        List<WebElement> menuItems = driver.findElements(burgerButtonList);

        for (int i = 0; i < menuItems.size(); i++) {
            WebElement currentItem = menuItems.get(i);
            actions.moveToElement(currentItem).perform();

            Color color = Color.fromString(currentItem.getCssValue("color"));
            hoverColors.add(color.asHex());
        }
        return hoverColors;
    }

    public List<String> getBurgerMenuTextOnHover() {
        List<String> hoverTexts = new ArrayList<>();
        List<WebElement> menuItems = driver.findElements(burgerButtonList);

        for (int i = 0; i < menuItems.size(); i++) {
            WebElement currentItem = menuItems.get(i);

            actions.moveToElement(currentItem).perform();

            hoverTexts.add(currentItem.getText());
        }

        return hoverTexts;
    }
}
