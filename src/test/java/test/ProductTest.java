package test;

import model.Filter;
import model.Item;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import page.CatalogPage;
import page.ProductPage;
import page.WishlistPage;
import service.FilterCreator;
import service.ItemCreator;
import service.TestDataReader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest extends  CommonConditions{
    //DONE2
    @Test
    public void correctFilterByCategoryTest() {
        Filter filter= FilterCreator.withEmptyPrice();
        List<String> categoryList = new CatalogPage(driver)
                .openPage(TestDataReader.getTestData("test.data.catalog"))
                .clickFilterButton()
                .clickFilterCategory()
                .clickToChooseFilterCategory(filter.getCategory())
                .getAllProductCategory();

        SoftAssertions softAssertions= generateSoftAssertionWithContains(categoryList,filter.getCategory());
        softAssertions.assertAll();
    }
    //DONE3
    @Test
    public void correctFilterByPriceTest() {
        Filter filter= FilterCreator.withEmptyCategory();
        List<Double> priceList = new CatalogPage(driver)
                .openPage(TestDataReader.getTestData("test.data.catalog"))
                .clickFilterButton()
                .clickFilterPrice()
                .clickToChooseFilterPrice(filter.getPrice())
                .getAllProductPrice();

        SoftAssertions softAssertions= generateSoftAssertionWithContains2(priceList,filter.getPrice());
        softAssertions.assertAll();
    }

    //done4
    @Test
    public void correctFilterByPriceAndCategoryTest(){
        Filter filter= FilterCreator.withAllProperty();
        CatalogPage catalogPage = new CatalogPage(driver)
                .openPage(TestDataReader.getTestData("test.data.catalog"))
                .clickFilterButton()
                .clickFilterCategory()
                .clickToChooseFilterCategory(filter.getCategory())
                .clickBackFilterButton()
                .clickFilterPrice()
                .clickToChooseFilterPrice(filter.getPrice());

        SoftAssertions softAssertions= generateSoftAssertionWithContains2(catalogPage.getAllProductPrice(),filter.getPrice());
        softAssertions.assertAll();

        softAssertions=generateSoftAssertionWithContains(catalogPage.getAllProductCategory(),filter.getCategory());
        softAssertions.assertAll();
    }

    //DONE1
    @Test
    public void addItemToWishlist(){
        String uri = ItemCreator.getUri("first");
        ProductPage productPage = new ProductPage(driver)
                .openPage(uri)
                .addToWishlist();

        String productTitle = productPage.getTitleProduct();
        Double productPrice = productPage.getPriceProduct();

        assertThat(productPage.isFavoriteItem()).isTrue();

        WishlistPage wishlistPage= productPage.goToWishlist();

        assertThat(wishlistPage.getCountOfProductsMessage()).contains("Вас в Списке желаний 1 товаров");
        assertThat(wishlistPage.getFavoriteItemsListSize()).isEqualTo(1);
        assertThat(wishlistPage.getProductTitle(uri)).contains(productTitle);
        assertThat(wishlistPage.getProductPrice(uri)).isEqualTo(productPrice);
    }
//done7
    @Test
    public void checkChangeWhenChooseSimilarProduct(){
        Item expectedItem = ItemCreator.withCredentialsFromProperty("third");
        String uri = ItemCreator.getUri("third");
        int orderSimilarProduct=2;
        ProductPage productPage=new ProductPage(driver)
                .openPage(uri);

        String startArticle=productPage.getTitleProduct();

        productPage.chooseSimilarProduct(orderSimilarProduct);

        assertThat(startArticle).isNotEqualTo(productPage.getTitleProduct());
    }
}
