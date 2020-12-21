package page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountPage extends AbstractPageWithStaticUrl {
    private final Logger logger = LogManager.getRootLogger();

    @FindBy(xpath = "//div[@class=\"row border-solid bgcolor-lgrey\"]//div[@class=\"col-sm-7 col-md-7 pi-value\"]")
    private WebElement userNameField;

    @FindBy(xpath = "//div[@class=\"row border-solid\"]//div[@class=\"col-sm-7 col-md-7 pi-value\"]")
    private WebElement userNameEmail;

    public AccountPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @Override
    public AccountPage openPage() {
        driver.get("https://www.lcwaikiki.by/ru-RU/BY/membership/personal-information");
        logger.info("Account Page opened");
        return this;
    }

    public String getUserName(){
        return userNameField.getText();
    }

    public String getUserNameEmail(){
        return userNameEmail.getText();
    }
}
