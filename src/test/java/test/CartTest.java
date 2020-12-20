package test;

import model.Item;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.CartPage;
import page.ProductPage;
import service.ItemCreator;

import static org.assertj.core.api.Assertions.assertThat;

public class CartTest extends CommonConditions {
    //8
    @Test
    public void addItemToCartWithoutSizeTest(){
        Item expectedItem = ItemCreator.withCredentialsFromProperty("first");
        String uri = ItemCreator.getUri("first");

        String item = new ProductPage(driver)
                .openPage(uri)
                .chooseItemColor(expectedItem.getColor())
                .addToCart()
                .noSize()
                .goToCart()
                .emptyCart();

        Assert.assertEquals(item, "Ваша корзина пуста");
    }
    //9
    @Test
    public void removeItemFromCartTest() {
        Item expectedItem = ItemCreator.withCredentialsFromProperty("first");
        String uri = ItemCreator.getUri("first");

        String item  = new ProductPage(driver)
                .openPage(uri)
                .chooseItemColor(expectedItem.getColor())
                .chooseItemSize(expectedItem.getSize())
                .addToCart()
                .goToCart()
                .removeItem(uri)
                .emptyCart();


        Assert.assertEquals(item, "Ваша корзина пуста");
}

    @Test
    public void moreThanThereIS(){
        Item expectedItem = ItemCreator.withCredentialsFromProperty("first");
        String uri = ItemCreator.getUri("first");
        int expectedAmount = 20;

        ProductPage productPage = new ProductPage(driver)
                .openPage(uri)
                .chooseItemSize(expectedItem.getSize())
                .addToCart();

        String productTitle = productPage.getTitleProduct();
        Double productPrice = productPage.getPriceProduct();

               CartPage cartPage = productPage
                       .goToCart()
                        .changeCountOfProduct(uri,expectedAmount);


    }


    @Test
    public void checkCorrectChangeCountProduct() throws InterruptedException {
        Item expectedItem = ItemCreator.withCredentialsFromProperty("first");
        String uri = ItemCreator.getUri("first");

        CartPage cartPage = new ProductPage(driver)
                .openPage(uri)
                .chooseItemColor(expectedItem.getColor())
                .chooseItemSize(expectedItem.getSize())
                .addToCart()
                .goToCart();

        cartPage.changeCountOfProduct(uri,expectedItem.getCount());
        assertThat(String.format("%.2f",expectedItem.getPrice()*expectedItem.getCount()).contains(cartPage.cost().trim()));
    }

    @Test
    public void checkCorrectSumPriceProduct() {
        Item expectedItem = ItemCreator.withCredentialsFromProperty("first");
        Item expectedItem2 = ItemCreator.withCredentialsFromProperty("second");

        CartPage orderPage = new ProductPage(driver)
                .openPage(ItemCreator.getUri("first"))
                .chooseItemColor(expectedItem.getColor())
                .chooseItemSize(expectedItem.getSize())
                .addToCart()
                .openPage(ItemCreator.getUri("first"))
                .chooseItemColor(expectedItem.getColor())
                .chooseItemSize(expectedItem.getSize())
                .addToCart()
                .goToCart();

        assertThat(orderPage.getSumAllProductPrice()).isEqualTo(orderPage.costInt());
    }
}
