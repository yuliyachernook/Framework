package page;

import model.Item;
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

    public String emptyCart(){
        return waitUntilVisibilityOf(emptyTitle).getText();
    }

    public String cost() {
        WebElement del = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class=\"price-info-area\"]//span[contains(text(),\"Предварительная сумма\")]//following-sibling::span")));
        return del.getText().replace("BYN","").replace(",", ".");
    }

    public String deliveryCost() {
        WebElement del = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"price-info-area\"]//span[contains(text(),\"Цена доставки\")]//following-sibling::span")));
        return getStringByWebElementText(del);
    }

    public String getTitleProduct(String productUrl){
        WebElement titleProduct = driver.findElement(By.xpath(String.format("//a[contains(@href, '%s')]" +
                "//following::span[@class=\"rd-cart-item-title\"]",productUrl)));
        return titleProduct.getText();
    }

    public Double getPriceProduct(String productUrl){
        WebElement priceProduct = driver.findElement(By.xpath(String.format("//a[contains(@href, '%s')]" +
                "//following::span[@class=\"rd-cart-item-price mb-15\"]", productUrl)));
        return getDoubleByWebElementText(priceProduct);
    }

    public boolean promo(){
         new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class=\"price-info-area\"]//span[contains(text(),\"Ввести промокод\")]//following-sibling::span"))).click();
     
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"ShoppingCartContent\"]/div[2]/div[2]/div[1]/div[6]/div[2]/div[1]/input")));
        driver.findElement(By.xpath("//input[@class=\"campaign-code-input cc-desktop\"]")).click();
        driver.findElement(By.xpath("//input[@class=\"campaign-code-input cc-desktop\"]")).sendKeys("5");
         WebElement b =  new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"ShoppingCartContent\"]/div[2]/div[2]/div[1]/div[6]/div[2]/div[2]/a")));
       b.click();
        waitUntilPresenceOfElement(By.xpath("//div[@class=\"coupon-code-msg-error\"]"));

        return !driver.findElements(By.xpath("//div[@class=\"coupon-code-msg-error\"]")).isEmpty();
    }

    public CartPage changeCountOfProduct(String productUrl,int countProduct){
        WebElement itemCost = driver.findElement(By.xpath("//span[@class=\"rd-cart-item-price mb-15\"]"));
        String startValue = itemCost.getText();
        WebElement el = driver.findElement(By.xpath(String.format("//a[contains(@href, '%s')]//" +
                "following::input[@class=\"item-quantity-input ignored\"]",productUrl)));
        el.click();
        el.sendKeys(Keys.DELETE);
        el.sendKeys(Integer.toString(countProduct));

        WebElement b =  new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("cartalertblock")));

        return this;
    }
    public double costInt() {
        WebElement del = waitUntilPresenceOfElement(By.xpath("//div[@class=\"price-info-area\"]//span[contains(text(),\"Предварительная сумма\")]//following-sibling::span"));
        return getDoubleByWebElementText(del);
    }

    public double getSumAllProductPrice(){
        List<WebElement> allPrice=driver.findElements(By.xpath("//div[@class=\"rd-cart-item shoppingcart-item\"]//span[@class=\"rd-cart-item-price mb-15\"]"));
        return allPrice.stream().mapToDouble(element -> getDoubleByWebElementText(element)).sum();
    }


    public Item getItem(int number) {
        WebElement itemName = driver.findElements(By.xpath("//span[@class=\"rd-cart-item-code\"]")).get(number - 1);
        WebElement itemCost = driver.findElements(By.xpath(itemCostTemplate)).get(number - 1);
        WebElement itemColor = driver.findElements(By.xpath("//span[@class=\"rd-cart-item-color\"]//strong")).get(number - 1);
        WebElement itemSize = driver.findElements(By.xpath("//span[@class=\"rd-cart-item-size\"]//strong")).get(number - 1);
        WebElement itemCount = driver.findElements(By.xpath("//input[@class=\"item-quantity-input ignored\"]")).get(number - 1);

        String name = itemName.getText();
        String size = itemSize.getText().toLowerCase();
        int cost = Resolver.resolveCost(itemCost.getText().replace("BYN", "").replace(",","").trim());
        int amount = Resolver.resolveInt(itemCount.getAttribute("value"));
        String color = itemColor.getText();

        return Item.of(name, size, color, cost, amount);
    }

    public CartPage removeItem(String url){
        driver.findElement(By.xpath(String.format("//a[contains(@href, '%s')]/ancestor::div[contains(@class, \"text-left\")]" +
                "//following::a[@class=\"cart-square-link\"]", url))).click();
        waitUntilElementIsClickable(By.xpath("//a[@class=\"inverted-modal-button sc-delete\"]")).click();
        return this;
    }


    public OrderPage orderButton(){
        WebElement removeButton = driver.findElement(By.id("Cart_CompleteOrder"));
        removeButton.click();
        return new OrderPage(driver);
    }
}
