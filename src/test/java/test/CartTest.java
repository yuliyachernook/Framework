package test;

import model.Item;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.CartPage;
import page.ProductPage;
import service.ItemCreator;

import static org.assertj.core.api.Assertions.assertThat;

public class CartTest extends CommonConditions {
    //9
    @Test
    public void addItemToCartWithoutSelectingASizeTest(){
        Item expectedItem = ItemCreator.withAllProperties("first");

        ProductPage productPage = new ProductPage(driver)
                .openPage(expectedItem.getUrl())
                .chooseItemColor(expectedItem.getColor())
                .addToCart();

        assertThat(productPage.notSelectSizeMessage()).isTrue();

        String emptycart = productPage.goToCart()
                                      .isEmptyCart();

        assertThat(emptycart).isEqualTo("Ваша корзина пуста");
    }
    //10
    @Test
    public void removeItemFromCartTest() {
        Item expectedItem = ItemCreator.withAllProperties("first");

        String item  = new ProductPage(driver)
                .openPage(expectedItem.getUrl())
                .chooseItemColor(expectedItem.getColor())
                .chooseItemSize(expectedItem.getSize())
                .addToCart()
                .goToCart()
                .removeItem(expectedItem.getUrl())
                .isEmptyCart();

        Assert.assertEquals(item, "Ваша корзина пуста");
}
//11
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

        assertThat(cartPage.updatedCartMessage()).isTrue();

    }

//12
    @Test
    public void correctChangeCountProductTest() {
        Item expectedItem = ItemCreator.withAllProperties("first");

        CartPage cartPage = new ProductPage(driver)
                .openPage(expectedItem.getUrl())
                .chooseItemColor(expectedItem.getColor())
                .chooseItemSize(expectedItem.getSize())
                .addToCart()
                .goToCart();

        cartPage.changeCountOfProduct(expectedItem.getUrl(),expectedItem.getCount())
        .updatedCartMessage();

        assertThat(Math.round(expectedItem.getPrice()*expectedItem.getCount()*100.0)/100.0).isEqualTo(cartPage.preliminaryСost());
    }
//13
    @Test
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

        assertThat(cartPage.getSumAllProductPrice()).isEqualTo(cartPage.costInt());
    }
}
