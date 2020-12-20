package test;

import model.Item;
import org.testng.annotations.Test;
import page.OrderPage;
import service.ItemCreator;
import service.TestDataReader;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest extends CommonConditions{
//    @Test
//    public void checkValidationUncorrectEmailInFastOrder() {
//        Item expectedItem= ItemCreator.withCredentialsFromProperty("first");
//        String email= TestDataReader.getTestData("test.data.failemail");
//        OrderPage item  = new HomePage(driver)
//                .openPage()
//                .search(ItemCreator.getUri("first"))
//                .chooseItemColor(expectedItem.getColor())
//                .chooseItemSize(expectedItem.getSize())
//                .addToCart()
//                .goToCart()
//                .orderButton()
//                .orderWithoutReg()
//                .orderWithoutRegButton();

//
//        String currentUrl=productPage.getCurrentUrl();
//        assertThat(productPage.getErrorInInputEmail()).isEqualTo("Не корректный адрес email");
//        assertThat(productPage.fastOrderIsCorrect()).isFalse();
//        assertThat(currentUrl).isEqualTo(productPage.getCurrentUrl());
    //}

}
