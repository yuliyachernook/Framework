package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends AbstractPageWithStaticUrl{

    @FindBy(id = "LoginEmail")
    private WebElement loginInput;

    @FindBy(id = "Password")
    private WebElement passwordInput;

    @FindBy(id = "loginLink")
    private WebElement submitButton;

    @FindBy(id = "header-login-section")
    private WebElement goToAccountButton;

    @FindBy(xpath = "//ul[@class=\"dropdown-menu\"]//a[@data-tracking-label=\"Личныеданные\"]")
    private WebElement goToPersonalInformationButton;

    private Actions actions;

    public LoginPage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(this.driver,this);
        actions = new Actions(driver);
    }

    @Override
    public LoginPage openPage()
    {
        driver.navigate().to("https://www.lcwaikiki.by/ru-RU/BY/login");
        return this;
    }

    public LoginPage inputUserLogin(String login){
        loginInput.sendKeys(login);
        return this;
    }

    public LoginPage inputUserPassword(String password){
        passwordInput.sendKeys(password);
        return this;
    }

    public LoginPage clickSubmitButton(){
        submitButton.click();
        return this;
    }
    public AccountPage goToAccount() {
        actions.moveToElement(goToAccountButton).build().perform();
        waitUntilVisibilityOf(goToPersonalInformationButton).click();
        return new AccountPage(driver);
    }
}