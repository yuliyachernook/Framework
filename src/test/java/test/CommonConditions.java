package test;

import driver.DriverSingleton;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import service.TestDataReader;
import util.TestListener;

import java.util.List;
@Listeners(TestListener.class)
public class CommonConditions {
    protected WebDriver driver;
    protected String promoCode = TestDataReader.getTestData("test.data.promocode");

    @BeforeClass
    public void init() {
        driver = DriverSingleton.getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void close() {
        DriverSingleton.deleteAllCookies();
    }

    @AfterClass(alwaysRun = true)
    public void dispose() {
        DriverSingleton.closeDriver();
    }

    public SoftAssertions generateSoftAssertionWithEquals(List<String> collection, String contains){
        SoftAssertions softAssertions=new SoftAssertions();
        collection.stream().forEach(element->softAssertions.assertThat(element.equals(contains)));
        return softAssertions;
    }

    public SoftAssertions generateSoftAssertionWithBetween(List<Double> collection, String contains){
        SoftAssertions softAssertions=new SoftAssertions();
        String[] data = contains.split("-");
        collection.stream().forEach(element-> {
            softAssertions.assertThat(element).isBetween(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
        });
        return softAssertions;
    }
}
