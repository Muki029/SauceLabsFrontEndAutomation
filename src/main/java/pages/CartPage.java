package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v146.domstorage.model.Item;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;


    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By inventoryList = By.className("inventory_item");
    private By addToCartButton = By.className("btn_inventory");
    private By shoppingCartButton = By.className("shopping_cart_link");
    private By shoppingCartBadgeNum = By.className("shopping_cart_badge");
    private By removeButton = By.id("remove-sauce-labs-backpack");
    private By checkoutButton = By.id("checkout");
    private By continueShoppingButton = By.id("continue-shopping");
    private By itemInCartName = By.className("inventory_item_name");
    private By priceOfItemInCart = By.className("inventory_item_price");
    private By addToCartOneItemButton = By.id("add-to-cart");
    private By removeSingleItemButton = By.id("remove");


    public void addItemToCartButton(int itemIndex) {
        List<WebElement> listOfItems = driver.findElements(inventoryList);

        WebElement targetProduct = listOfItems.get(itemIndex);

        ClickUtil.click(driver, targetProduct.findElement(addToCartButton));


    }

    public void removeItemFromCartButton(int itemIndex) {
        List<WebElement> listOfItems = driver.findElements(inventoryList);

        WebElement targetProduct = listOfItems.get(itemIndex);

        ClickUtil.click(driver, targetProduct.findElement(removeButton));


    }

    public int getItemsCountInCart() {

        return driver.findElements(itemInCartName).size();
    }
    public void clickOnInventoryItemName(int itemIndex){
        ClickUtil.click(driver, driver.findElements(itemInCartName).get(itemIndex));
    }
    public void clickAddToCartSingleItemButton(){
        ClickUtil.click(driver, driver.findElement(addToCartOneItemButton));
    }
    public void clickRemoveSingleItemButton(){
        ClickUtil.click(driver, driver.findElement(removeSingleItemButton));
    }
    public String getColorFromRemoveButton(){
        Color color = Color.fromString(driver.findElement(removeSingleItemButton).getCssValue("color"));
        return color.asHex();
    }

    public void clickCartButton() {
        ClickUtil.click(driver, driver.findElement(shoppingCartButton));
        wait.until(ExpectedConditions.urlContains("cart.html"));
    }

    public void clickRemoveButton() {
        ClickUtil.click(driver, driver.findElement(removeButton));
    }

    public void clickContinueShoppingButton() {
        ClickUtil.click(driver, driver.findElement(continueShoppingButton));
        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }

    public void clickCheckoutButton() {
        ClickUtil.click(driver, driver.findElement(checkoutButton));
        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
    }


    public String getItemInCartName() {
        return driver.findElement(itemInCartName).getText();
    }

    public String getPriceOfItemInCart() {
        String price = driver.findElement(priceOfItemInCart).getText();
        return price.substring(1);
    }

    public String getColorOfRemoveButton() {
        Color color = Color.fromString(driver.findElement(removeButton).getCssValue("color"));
        return color.asHex();
    }

    public String getColorOfCheckoutButton() {
        Color color = Color.fromString(driver.findElement(checkoutButton).getCssValue("background-color"));
        return color.asHex();
    }

    public boolean isItemAddedToCart() {
        try {

            return driver.findElement(shoppingCartBadgeNum).isDisplayed();

        } catch (NoSuchElementException e) {
            return false;
        }

    }

}
