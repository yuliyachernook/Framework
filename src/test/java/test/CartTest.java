package test;

import model.Item;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.CartPage;
import page.ProductPage;
import service.ItemCreator;

import static org.assertj.core.api.Assertions.assertThat;
import static util.Resolver.resolveCost;

public class CartTest extends CommonConditions {

    @Test (priority=1)
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
    //10
    @Test (priority=2)
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
//11
    @Test (priority=3)
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

    @Test (priority=4)
    public void checkCorrectSumPriceProduct() {
        Item expectedItem = ItemCreator.withAllProperties("first");
        Item expectedItem2 = ItemCreator.withAllProperties("third");

        CartPage cartPage = new ProductPage(driver)
                .openPage(expectedItem.getUrl())
                .chooseItemColor(expectedItem.getColor())
                .chooseItemSize(expectedItem.getSize())
                .addToCart()
                .openPage(expectedItem2.getUrl())
                .chooseItemColor(expectedItem2.getColor())
                .chooseItemSize(expectedItem2.getSize())
                .addToCart()
                .goToCart();

        assertThat(cartPage.getSumAllProductPrice()).isEqualTo(cartPage.getPreliminaryСost());
    }

    @Test (priority=5)
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
}
