package test;

import model.Item;
import org.testng.annotations.Test;
import page.CartPage;
import page.ProductPage;
import service.ItemCreator;

import static org.assertj.core.api.Assertions.assertThat;
import static util.Resolver.resolveCost;

public class CartTest extends CommonConditions {

    @Test
    public void addItemToCartWithoutSelectingASizeTest(){
        Item expectedItem = ItemCreator.withAllProperties("first");

        ProductPage productPage = new ProductPage(driver)
                .openPage(expectedItem.getUrl())
                .chooseItemColor(expectedItem.getColor())
                .addToCart();

        assertThat(productPage.notSelectSizeMessage()).isTrue();

        CartPage cartPage = productPage.goToCart();

        assertThat(cartPage.isEmptyCart()).isTrue();
    }

    @Test
    public void selectAmountGreaterThanThereIsTest(){
        Item expectedItem = ItemCreator.withAllProperties("first");
        int expectedAmount = 20;

        CartPage cartPage = new ProductPage(driver)
                .openPage(expectedItem.getUrl())
                .chooseItemSize(expectedItem.getSize())
                .addToCart()
                .goToCart()
                .changeCountOfProduct(expectedItem.getUrl(),expectedAmount);

        assertThat(cartPage.isSelectedMoreThanThereIs()).isTrue();
    }

    @Test
    public void checkCorrectSumPriceProduct() {
        Item firstExpectedItem = ItemCreator.withAllProperties("first");
        Item secondExpectedItem = ItemCreator.withAllProperties("second");

        CartPage cartPage = new ProductPage(driver)
                .openPage(firstExpectedItem.getUrl())
                .chooseItemColor(firstExpectedItem.getColor())
                .chooseItemSize(firstExpectedItem.getSize())
                .addToCart()
                .openPage(secondExpectedItem.getUrl())
                .chooseItemColor(secondExpectedItem.getColor())
                .chooseItemSize(secondExpectedItem.getSize())
                .addToCart()
                .goToCart();

        assertThat(cartPage.getSumAllProductPrice()).isEqualTo(cartPage.getPreliminaryСost());
    }

    @Test
    public void correctChangeCountProductTest() {
        Item expectedItem = ItemCreator.withAllProperties("first");

        CartPage cartPage = new ProductPage(driver)
                .openPage(expectedItem.getUrl())
                .chooseItemColor(expectedItem.getColor())
                .chooseItemSize(expectedItem.getSize())
                .addToCart()
                .goToCart();

        cartPage.changeCountOfProduct(expectedItem.getUrl(),expectedItem.getCount());

        assertThat(resolveCost(expectedItem.getPrice(), expectedItem.getCount())).isEqualTo(cartPage.getPreliminaryСost());
    }

    @Test
    public void removeItemFromCartTest() {
        Item expectedItem = ItemCreator.withAllProperties("first");

        boolean isEmptyCart  = new ProductPage(driver)
                .openPage(expectedItem.getUrl())
                .chooseItemColor(expectedItem.getColor())
                .chooseItemSize(expectedItem.getSize())
                .addToCart()
                .goToCart()
                .removeItem(expectedItem.getUrl())
                .isEmptyCart();

        assertThat(isEmptyCart).isTrue();
    }
}
