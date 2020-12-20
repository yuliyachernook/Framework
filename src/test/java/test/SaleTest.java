package test;

import model.Item;
import org.testng.annotations.Test;
import page.CartPage;
import page.ProductPage;
import service.ItemCreator;

import static org.assertj.core.api.Assertions.assertThat;

public class SaleTest extends CommonConditions{

    //Done5
    @Test
    public void freeDeliveryWhenOrderAmountIsMoreThanSeventyTest() {
        Item expectedItem = ItemCreator.withCredentialsFromProperty("third");
        String uri = ItemCreator.getUri("third");

        ProductPage productPage  = new ProductPage(driver)
                .openPage(uri)
                .chooseItemSize(expectedItem.getSize())
                .chooseItemColor(expectedItem.getColor());

        String productTitle = productPage.getTitleProduct();
        Double productPrice = productPage.getPriceProduct();

        CartPage cartPage = productPage.addToCart()
                   .goToCart();

        assertThat(cartPage.deliveryCost()).contains("Бесплатно");
        assertThat(cartPage.getTitleProduct(uri)).contains(productTitle);
        assertThat(cartPage.getPriceProduct(uri)).isEqualTo(productPrice);
        assertThat(cartPage.getPriceProduct(uri)).isGreaterThanOrEqualTo(70);
    }
//done6
    @Test
    public void invalidPromoTest() {
        Item expectedItem = ItemCreator.withCredentialsFromProperty("first");
        String uri = ItemCreator.getUri("first");

        CartPage cartPage  = new ProductPage(driver)
                .openPage(uri)
                .chooseItemSize(expectedItem.getSize())
                .chooseItemColor(expectedItem.getColor())
                .addToCart()
                .goToCart();

        assertThat(cartPage.promo()).isTrue();

    }

}
