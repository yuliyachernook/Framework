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

    @FindBy(xpath = "//input[@class=\"campaign-code-input cc-desktop\"]")
    private WebElement promoCodeInput;
    @FindBy(xpath = "//a[@class=\"inverted-modal-button sc-delete\"]")
    private WebElement confirmButton;

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public CartPage openPage() {
        return this;
    }

    public boolean isEmptyCart(){
        waitUntilPresenceOfElement(By.className("cart-empty-title"));
        logger.info("The cart is empty");
        return !driver.findElements(By.className("cart-empty-title")).isEmpty();
    }

    public Double getPreliminaryСost() {
        WebElement preliminaryCost = waitUntilPresenceOfElement(By.xpath("//div[@class=\"price-info-area\"]" +
                "//span[contains(text(),\"Предварительная сумма\")]//following-sibling::span"));
        return getDoubleByWebElementText(preliminaryCost);
    }

    public String getDeliveryCost() {
        WebElement deliveryCost = waitUntilPresenceOfElement(By.xpath("//div[@class=\"price-info-area\"]" +
                "//span[contains(text(),\"Цена доставки\")]//following-sibling::span"));
        return getStringByWebElementText(deliveryCost);
    }

    public Double getTotalСost() {
        WebElement totalCost = waitUntilPresenceOfElement(By.xpath("//div[@class=\"price-info-area\"]" +
                "//span[contains(text(),\"ОБЩАЯ\")]//following-sibling::span"));
        return getDoubleByWebElementText(totalCost);
    }

    public String getCodeProduct(String productUrl){
        WebElement codeProduct = driver.findElement(By.xpath(resolveTemplate("//a[contains(@href, '%s')]" +
                "//following::span[@class=\"rd-cart-item-code\"]",productUrl)));
        return codeProduct.getText();
    }

    public Double getPriceProduct(String productUrl){
        WebElement priceProduct = driver.findElement(By.xpath(resolveTemplate("//a[contains(@href, '%s')]" +
                "//following::span[@class=\"rd-cart-item-price mb-15\"]", productUrl)));
        return getDoubleByWebElementText(priceProduct);
    }

    public CartPage inputPromoCode(String promoCode){
         WebElement promoCodeButton = waitUntilPresenceOfElement(By.xpath("//div[@class=\"price-info-area\"]" +
                 "//span[contains(text(),\"Ввести промокод\")]//following-sibling::span"));
         promoCodeButton.click();
         WebElement inputPromoCode = waitUntilVisibilityOf(promoCodeInput);
         inputPromoCode.click();
         inputPromoCode.sendKeys(promoCode);
         waitUntilPresenceOfElement(By.xpath("//div[@class=\"price-info-area\"]//a[@class=\"button b-ccode\"]")).click();
         return this;
    }

    public boolean isInvalidPromoCode() {
        waitUntilPresenceOfElement(By.xpath("//div[@class=\"coupon-code-msg-error\"]"));
        logger.info("The promo code is invalid");
        return !driver.findElements(By.xpath("//div[@class=\"coupon-code-msg-error\"]")).isEmpty();
    }

    public CartPage changeCountOfProduct(String productUrl,int countProduct){
        WebElement el = driver.findElement(By.xpath(resolveTemplate("//a[contains(@href, '%s')]//" +
                "following::input[@class=\"item-quantity-input ignored\"]",productUrl)));
        el.click();
        el.sendKeys(Keys.DELETE);
        el.sendKeys(Integer.toString(countProduct));
        waitUntilElementIsClickable(By.id("cartalertblock"));
        logger.info("Count of products are changed");
        return this;
    }

    public boolean isSelectedMoreThanThereIs() {
        waitUntilPresenceOfElement(By.xpath("//div[@class=\"bootbox-body\"]"));
        return !driver.findElements(By.xpath("//div[@class=\"bootbox-body\"]")).isEmpty();
    }

    public double getSumAllProductPrice(){
        List<WebElement> allPrice=driver.findElements(By.xpath("//div[@class=\"rd-cart-item shoppingcart-item\"]" +
                "//span[@class=\"rd-cart-item-price mb-15\"]"));
        return allPrice.stream().mapToDouble(element -> getDoubleByWebElementText(element)).sum();
    }

    public CartPage removeItem(String url){
        driver.findElement(By.xpath(resolveTemplate("//a[contains(@href, '%s')]/ancestor::div[contains(@class, \"text-left\")]" +
                "//following::a[@title=\"Удалить\"]", url))).click();
        waitUntilVisibilityOf(confirmButton).click();
        logger.info("Item are removed");
        return this;
    }
}
