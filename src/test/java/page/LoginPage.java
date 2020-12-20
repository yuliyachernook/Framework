package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends AbstractPageWithStaticUrl{

    @FindBy(xpath = "//input[@id='LoginEmail']")
    private WebElement inputLogin;

    @FindBy(xpath = "//input[@id='Password']")
    private WebElement inputPassword;

    @FindBy(xpath = "//a[@id='loginLink']")
    private WebElement submitButton;

    Actions actions;

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
        inputLogin.sendKeys(login);
        return this;
    }

    public LoginPage inputUserPassword(String password){
        inputPassword.sendKeys(password);
        return this;
    }

    public LoginPage clickSubmitButton(){
        submitButton.click();
        return this;
    }
    public AccountPage goToAccount() {
        WebElement account = driver.findElement(By.id("header-login-section"));
        actions.moveToElement(account).build().perform();
        WebElement goTo = driver.findElement(By.xpath("//*[@id=\"header-profile-section\"]/div/ul/li[2]/a"));
        goTo.click();
        return new AccountPage(driver);
    }


}