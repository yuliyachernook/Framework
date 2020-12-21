package page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static util.Resolver.resolveTemplate;

public class ProductPage extends AbstractPageWithParameterizedUrl {
    private final Logger logger = LogManager.getRootLogger();

    private static final String SIZE_TEMPLATE = "a[size=%s]";
    private static final String COLOR_TEMPLATE = "//div[@class=\"color-box\"]/div[@title=\"%s\"]";

    @FindBy(className = "add-to-favorite-detail")
    private WebElement addToWishlist;

    @FindBy(className = "header-favorite-icon")
    private WebElement goToWishlist;

    @FindBy(id= "pd_add_to_cart")
    private WebElement addToCart;

    @FindBy(className= "header-bag-icon")
    private WebElement goToCart;

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public ProductPage openPage(String urlPart) {
        driver.get("https://www.lcwaikiki.by/ru-RU/BY/product" + urlPart);
        return this;
    }
    public ProductPage addToWishlist() {
        addToWishlist.click();
        return this;
    }

    public WishlistPage goToWishlist() {
        goToWishlist.click();
        return new WishlistPage(driver);
    }

    public boolean isFavoriteItem(){
        waitUntilPresenceOfElement(By.xpath("//a[contains(@class,\"add-to-favorite-detail added\")]"));
        logger.info("Item is favorite");
        return !driver.findElements(By.xpath("//a[contains(@class,\"add-to-favorite-detail added\")]")).isEmpty();
    }

    public ProductPage chooseItemSize(String size){
        waitUntilElementIsClickable(By.cssSelector(resolveTemplate(SIZE_TEMPLATE, size))).click();
        return this;
    }

    public ProductPage chooseItemColor(String color){
        waitUntilPresenceOfElement(By.xpath(resolveTemplate(COLOR_TEMPLATE, color))).click();
        return this;
    }

    public ProductPage addToCart() {
        addToCart.click();
        return this;
    }

    public CartPage goToCart() {
        goToCart.click();
        return new CartPage(driver);
    }

    public boolean notSelectSizeMessage(){
        waitUntilPresenceOfElement(By.className("popover-content"));
        logger.info("Not selected size");
        return !driver.findElements(By.className("popover-content")).isEmpty();
    }

    public ProductPage chooseSimilarProduct(int order){
        driver.findElements(By.xpath("//div[@class=\"color-box\"]")).get(order-1).click();
        jQueryAJAXCompleted();
        return this;
    }

    public String getCodeProduct() {
        WebElement titleProduct = waitUntilPresenceOfElement(By.xpath("//div[@class=\"row title-info\"]" +
                "//following::div[@class='product-code']"));
        return titleProduct.getText().trim();
    }

    public String getNameProduct() {
        WebElement nameProduct = waitUntilPresenceOfElement(By.xpath("//div[@class=\"row title-info\"]" +
                "//following::div[@class='product-title']"));
        return nameProduct.getText().trim();
    }

    public Double getPriceProduct() {
        WebElement titleProduct = waitUntilPresenceOfElement(By.xpath("//div[@class=\"col-xs-12 price-area\"]" +
                "//following::span[@class='price']"));
        return getDoubleByWebElementText(titleProduct);
    }
}