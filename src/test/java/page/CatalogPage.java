package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CatalogPage extends AbstractPageWithParameterizedUrl {

    @FindBy(xpath = "//a[@class=\"a_model_item\"]//div[@class=\"title\"]")
    private WebElement model;

    @FindBy(xpath = "//button[@class='catalog-filter__title']/span[text()='Цвет']/../..//button[@class='catalog-filter__category-more']")
    private WebElement moreColorButton;
    private By mod = By.xpath("//a[@class=\"a_model_item\"]//div[@class=\"title\"]");

    public CatalogPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @Override
    public CatalogPage openPage(String partUrl) {
        driver.get("https://www.lcwaikiki.by/ru-RU/BY/product-group/"+partUrl);
        return this;
    }

    public CatalogPage clickFilterButton(){
            waitUntilElementIsClickable((By.xpath("//div[@class=\"filter right\"]"))).click();
        return this;
    }

    public CatalogPage clickBackFilterButton(){
        waitUntilElementIsClickable((By.xpath("//span[@class=\"close-icon\"]"))).click();
        return this;
    }

    public CatalogPage clickToChooseFilterCategory(String category) {
        waitUntilElementIsClickable(By.xpath("//input[contains(@title,'"+category+"')]//following-sibling::span")).click();
        return this;
    }

    public CatalogPage clickToChooseFilterPrice(String price) {
        waitUntilElementIsClickable(By.xpath("//input[contains(@title,'"+price+"')]//following-sibling::span")).click();
        return this;
    }

    public CatalogPage clickFilterCategory(){
        waitUntilElementIsClickable((By.xpath("//span[text()=\"Категория\"]"))).click();
        return this;
    }

    public CatalogPage clickFilterPrice(){
        waitUntilElementIsClickable((By.xpath("//span[text()=\"Цена\"]"))).click();
        return this;
    }


    public List<String> getAllProductCategory() {
        List<String> list =new ArrayList<>();
        waitUntilAjaxCompleted();
        for (WebElement item:driver.findElements(By.xpath("//a[@class=\"a_model_item\"]//div[@class=\"title\"]"))) {
            list.add(item.getText());
        }
        return list;
    }

    public List<Double> getAllProductPrice() {
        List<Double> productPricelist =new ArrayList<>();
        waitUntilAjaxCompleted();
        for (WebElement priceItem: driver.findElements( new ByChained
                (By.xpath("//a[@class=\"a_model_item\"]//div[@class=\"discount-price\"]"),
                (By.xpath("//a[@class=\"a_model_item\"]//div[@class=\"raw-price\"]"))))) {
            productPricelist.add(getDoubleByWebElementText(priceItem));
        }
        return productPricelist;
    }
}