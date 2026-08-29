package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckOutV2Page {
    private WebDriver driver;
    private CheckOutPage checkout;
    private CheckOutV2Page checkout2;
    private ProductsPage products;
    private WebDriverWait wait;

    public CheckOutV2Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By paymentInformation = By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[1]");
    private By priceInfoTxt = By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[5]");
    private By paymentInformationInfo = By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[2]");
    private By shippingInformation = By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[3]");
    private By shippingInformationInfo = By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[4]");
    private By itemTotal = By.className("summary_subtotal_label");
    private By tax = By.className("summary_tax_label");
    private By finishButton = By.id("finish");
    private By cancelButton = By.id("cancel");
    private By inventoryItemName = By.className("inventory_item_name");
    private By inventoryItemDescription = By.className("inventory_item_desc");

    public double calculateTotalPriceWithTax() {

        String itemtotalprice = driver.findElement(itemTotal).getText();
        String taxprice = driver.findElement(tax).getText();

        double itemtotalpricedouble = Double.parseDouble(itemtotalprice.substring(13));
        double taxpricedouble = Double.parseDouble(taxprice.substring(6));

        double finalPrice = itemtotalpricedouble + taxpricedouble;

        return finalPrice;
    }

    public double getItemTotalPricewithoutTax() {

        String itemtotalprice = driver.findElement(itemTotal).getText();

        String priceOnly = itemtotalprice.substring(13);

        double priceOnly1 = Double.parseDouble(priceOnly);
        return priceOnly1;
    }

    public double getOnlyTaxPriceFromItems() {
        String taxPrice = driver.findElement(tax).getText();

        String taxPriceCut = taxPrice.substring(6);

        double taxPriceCut1 = Double.parseDouble(taxPriceCut);
        return taxPriceCut1;
    }

    public void clickCancelButton() {
        ClickUtil.click(driver, driver.findElement(cancelButton));
        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }

    public String getPaymentInfomationText() {
        return driver.findElement(paymentInformation).getText();
    }

    public String getPaymentInformationdescriptionText() {
        return driver.findElement(paymentInformationInfo).getText();
    }

    public String getShippingInformationText() {
        return driver.findElement(shippingInformation).getText();
    }

    public String getShippingInformationDescriptionText() {
        return driver.findElement(shippingInformationInfo).getText();
    }

    public String getFinishButtonColor() {
        Color color = Color.fromString(driver.findElement(finishButton).getCssValue("background-color"));
        return color.asHex();
    }

    public void clickFinishButton() {
        ClickUtil.click(driver, driver.findElement(finishButton));
    }

    public String getPricetext() {
        return driver.findElement(priceInfoTxt).getText();
    }

    public String getInventoryItemName(int inventoryItemIndex) {
        return driver.findElements(inventoryItemName).get(inventoryItemIndex).getText();
    }

    public String getInventoryItemDescription(int inventoryItemIndex) {
        return driver.findElements(inventoryItemDescription).get(inventoryItemIndex).getText();
    }
}
