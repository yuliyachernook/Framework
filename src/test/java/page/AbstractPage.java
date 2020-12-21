package page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public abstract class AbstractPage {
    protected final int WAIT_TIMEOUT_SECONDS = 50;
    protected WebDriver driver;


    protected AbstractPage(WebDriver driver){
        this.driver = driver;
    }

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    public WebElement waitUntilPresenceOfElement(By location){
        return new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(location));
    }

    public WebElement waitUntilVisibilityOf(WebElement element){
        return new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public List<WebElement> waitUntilVisibilityOfAll(List<WebElement> elementsList){
        return new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOfAllElements(elementsList));
    }

    public WebElement waitUntilElementIsClickable(By location){
        return new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(location));
    }

    public WebElement waitUntilElementIsClickable(WebElement element){
        return new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(element));
    }


    public void waitUntilAjaxCompleted(){
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(jQueryAJAXCompleted());
    }

    protected static ExpectedCondition<Boolean> jQueryAJAXCompleted(){
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver){
                return (Boolean) ((JavascriptExecutor)
                        driver).executeScript("return (window.jQuery != null) && (jQuery.active == 0);");
            }
        };
    }

    public Double getDoubleByWebElementText(WebElement webElement){
        return Double.parseDouble(webElement.getText().replace(",", ".").replace("BYN","").trim());
    }

    public String getStringByWebElementText(WebElement webElement){
        return webElement.getText().replace(",", ".").replace("BYN","").trim();
    }
}
