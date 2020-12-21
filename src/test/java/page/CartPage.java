package page;

import model.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.Resolver;

import java.util.List;

import static util.Resolver.resolveTemplate;

public class CartPage extends AbstractPageWithStaticUrl{
    private final Logger logger = LogManager.getRootLogger();
    String itemCostTemplate = "//span[@class=\"rd-cart-item-price mb-15\"]";
    String countOfItemTemplate = "//input[@class=\"item-quantity-input ignored\"]";
    String itemDeleteTemplate = "//a[@class=\"cart-square-link\"]";

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public CartPage openPage() {
        return this;
    }

    @FindBy(xpath = "//div[@class=\"price-info-area\"]//span[contains(text(),\"Предварительная сумма\")]//following-sibling::span")
    private WebElement cost;
    @FindBy(xpath = "//div[@class=\"price-info-area\"]//span[contains(text(),\"Цена доставки\")]//following-sibling::span")
    private WebElement del;

    @FindBy(className = "cart-empty-title")
    private WebElement emptyTitle;

    public String isEmptyCart(){
        return waitUntilVisibilityOf(emptyTitle).getText();
    }

    public Double preliminaryСost() {
        WebElement del = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class=\"price-info-area\"]//span[contains(text(),\"Предварительная сумма\")]//following-sibling::span")));
        return getDoubleByWebElementText(del);
    }

    public String deliveryCost() {
        WebElement del = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"price-info-area\"]//span[contains(text(),\"Цена доставки\")]//following-sibling::span")));
        return getStringByWebElementText(del);
    }

    public Double totalСost() {
        WebElement del = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class=\"price-info-area\"]//span[contains(text(),\"ОБЩАЯ\")]//following-sibling::span")));
        return getDoubleByWebElementText(del);
    }

    public String getTitleProduct(String productUrl){
        WebElement titleProduct = driver.findElement(By.xpath(String.format("//a[contains(@href, '%s')]" +
                "//following::span[@class=\"rd-cart-item-code\"]",productUrl)));
        return titleProduct.getText();
    }

    public Double getPriceProduct(String productUrl){
        WebElement priceProduct = driver.findElement(By.xpath(String.format("//a[contains(@href, '%s')]" +
                "//following::span[@class=\"rd-cart-item-price mb-15\"]", productUrl)));
        return getDoubleByWebElementText(priceProduct);
    }

    public CartPage inputPromoCode(String promoCode){
         WebElement promoCodeButton = waitUntilPresenceOfElement(By.xpath("//div[@class=\"price-info-area\"]//span[contains(text(),\"Ввести промокод\")]//following-sibling::span"));
         promoCodeButton.click();
     
         WebElement inputPromoCode = waitUntilPresenceOfElement(By.xpath("//input[@class=\"campaign-code-input cc-desktop\"]"));
         inputPromoCode.click();
         inputPromoCode.sendKeys(promoCode);
         waitUntilElementIsClickable(By.xpath("//div[@class=\"price-info-area\"]//a[@class=\"button b-ccode\"]")).click();
         return this;
    }

    public boolean isInvalidPromoCode() {
        waitUntilPresenceOfElement(By.xpath("//div[@class=\"coupon-code-msg-error\"]"));
        return !driver.findElements(By.xpath("//div[@class=\"coupon-code-msg-error\"]")).isEmpty();
    }

    public CartPage changeCountOfProduct(String productUrl,int countProduct){
        WebElement el = driver.findElement(By.xpath(String.format("//a[contains(@href, '%s')]//" +
                "following::input[@class=\"item-quantity-input ignored\"]",productUrl)));
        el.click();
        el.sendKeys(Keys.DELETE);
        el.sendKeys(Integer.toString(countProduct));
        return this;
    }


    public boolean updatedCartMessage(){
        waitUntilElementIsClickable(By.id("cartalertblock"));
        return !driver.findElements(By.id("cartalertblock")).isEmpty();

    }

    public double costInt() {
        WebElement del = waitUntilPresenceOfElement(By.xpath("//div[@class=\"price-info-area\"]//span[contains(text(),\"Предварительная сумма\")]//following-sibling::span"));
        return getDoubleByWebElementText(del);
    }

    public double getSumAllProductPrice(){
        List<WebElement> allPrice=driver.findElements(By.xpath("//div[@class=\"rd-cart-item shoppingcart-item\"]//span[@class=\"rd-cart-item-price mb-15\"]"));
        return allPrice.stream().mapToDouble(element -> getDoubleByWebElementText(element)).sum();
    }

    public CartPage removeItem(String url){
        driver.findElement(By.xpath(String.format("//a[contains(@href, '%s')]/ancestor::div[contains(@class, \"text-left\")]" +
                "//following::a[@class=\"cart-square-link\"]", url))).click();
        waitUntilElementIsClickable(By.xpath("//a[@class=\"inverted-modal-button sc-delete\"]")).click();
        return this;
    }
}
