package test;

import model.User;
import org.testng.annotations.Test;
import page.AccountPage;
import page.LoginPage;
import service.UserCreator;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest extends CommonConditions {
    //done6
    @Test
    public void loginTest() {
        User testUser= UserCreator.withAllProperty();
        LoginPage loginPage= new LoginPage(driver)
                .openPage()
                .inputUserLogin(testUser.getEmail())
                .inputUserPassword(testUser.getPassword());

        String currentUrl=loginPage.getCurrentUrl();
        loginPage.clickSubmitButton();

        assertThat(loginPage.getCurrentUrl()).isNotEqualTo(currentUrl);

        AccountPage accountPage= loginPage.goToAccount();

        assertThat(accountPage.getUserName()).contains(testUser.getUsername());
        assertThat(accountPage.getUserNameEmail()).isEqualTo(testUser.getEmail());
    }
}
