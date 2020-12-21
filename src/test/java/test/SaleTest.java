package test;

import model.Item;
import org.testng.annotations.Test;
import page.AbstractPage;
import page.CartPage;
import page.ProductPage;
import service.ItemCreator;

import static org.assertj.core.api.Assertions.assertThat;

public class SaleTest extends CommonConditions {
//1
    @Test
    public void freeDeliveryWhenOrderAmountIsMoreThanSeventyTest() {
        Item expectedItem = ItemCreator.withAllProperties("third");
        String urlExpectedItem = expectedItem.getUrl();

        ProductPage productPage  = new ProductPage(driver)
                .openPage(expectedItem.getUrl())
                .chooseItemSize(expectedItem.getSize())
                .chooseItemColor(expectedItem.getColor());

        String productTitle = productPage.getTitleProduct();
        Double productPrice = productPage.getPriceProduct();

        CartPage cartPage = productPage
                .addToCart()
                .goToCart();

        assertThat(cartPage.deliveryCost()).contains("Бесплатно");
        assertThat(productTitle).contains(cartPage.getTitleProduct(urlExpectedItem));
        assertThat(cartPage.getPriceProduct(urlExpectedItem)).isEqualTo(productPrice);
        assertThat(cartPage.getPriceProduct(urlExpectedItem)).isGreaterThanOrEqualTo(70);
    }
//2
    @Test
    public void invalidPromoCodeTest() {
        Item expectedItem = ItemCreator.withAllProperties("first");
        String urlExpectedItem = expectedItem.getUrl();

        CartPage cartPage  = new ProductPage(driver)
                .openPage(urlExpectedItem)
                .chooseItemSize(expectedItem.getSize())
                .chooseItemColor(expectedItem.getColor())
                .addToCart()
                .goToCart()
                .inputPromoCode(promoCode);

        assertThat(cartPage.isInvalidPromoCode()).isTrue();
        assertThat(cartPage.totalСost()).
                isEqualTo(Double.parseDouble(cartPage.deliveryCost()) + cartPage.getPriceProduct(urlExpectedItem));
    }
}
