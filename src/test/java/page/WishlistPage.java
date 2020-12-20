package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WishlistPage extends AbstractPageWithStaticUrl {

    @FindBy(className = "favorite-item")
    private List<WebElement> favoriteItemsList;

    public WishlistPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public WishlistPage openPage() {
        return this;
    }

    public int getFavoriteItemsListSize() {
        waitUntilVisibilityOfAll(favoriteItemsList);
        return favoriteItemsList.size();
    }

    public String getCountOfProductsMessage(){
        WebElement countOfProductsMessage = waitUntilPresenceOfElement(By.xpath("//p[@class=\"text-center bold\"]"));
        return countOfProductsMessage.getText();
    }

    public String getProductTitle(String productUrl){
        WebElement productTitle = driver.findElement(By.xpath(String.format("//a[contains(@href, '%s')]" +
                "//following::p[@class=\"c-item-name\"]",productUrl)));
        return productTitle.getText();
    }

    public Double getProductPrice(String productUrl){
        WebElement productPrice = driver.findElement(By.xpath(String.format("//a[contains(@href, '%s')]" +
                "//following::span[@class=\"c-item-discount-price\" or @class=\"c-item-price\"]", productUrl)));
        return getDoubleByWebElementText(productPrice);
    }

}