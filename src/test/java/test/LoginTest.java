package test;

import model.User;
import org.testng.annotations.Test;
import page.AccountPage;
import page.LoginPage;
import service.UserCreator;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest extends CommonConditions {

    @Test
    public void loginTest() {
        User testUser= UserCreator.withAllProperty();

        AccountPage accountPage = new LoginPage(driver)
                .openPage()
                .inputUserLogin(testUser.getEmail())
                .inputUserPassword(testUser.getPassword())
                .clickSubmitButton()
                .goToAccount();

        assertThat(accountPage.getUserName()).contains(testUser.getUsername());
        assertThat(accountPage.getUserNameEmail()).isEqualTo(testUser.getEmail());
    }
}
