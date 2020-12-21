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

public class ItemTest extends  CommonConditions{
//4
    @Test
    public void correctFilterByCategoryTest() {
        Filter filter= FilterCreator.withEmptyPrice();

        List<String> categoryList = new CatalogPage(driver)
                .openPage(TestDataReader.getTestData("test.data.catalog"))
                .clickFilterButton()
                .clickFilterCategory()
                .clickToChooseFilterCategory(filter.getCategory())
                .getAllProductCategory();

        SoftAssertions softAssertions= generateSoftAssertionWithEquals(categoryList,filter.getCategory());
        softAssertions.assertAll();
    }
//5
    @Test
    public void correctFilterByPriceTest() {
        Filter filter= FilterCreator.withEmptyCategory();
        List<Double> priceList = new CatalogPage(driver)
                .openPage(TestDataReader.getTestData("test.data.catalog"))
                .clickFilterButton()
                .clickFilterPrice()
                .clickToChooseFilterPrice(filter.getPrice())
                .getAllProductPrice();

        SoftAssertions softAssertions= generateSoftAssertionWithBetween(priceList,filter.getPrice());
        softAssertions.assertAll();
    }

//6
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

        SoftAssertions softAssertions= generateSoftAssertionWithBetween(catalogPage.getAllProductPrice(),filter.getPrice());
        softAssertions.assertAll();

        softAssertions=generateSoftAssertionWithEquals(catalogPage.getAllProductCategory(),filter.getCategory());
        softAssertions.assertAll();
    }

//7
    @Test
    public void addItemToWishlistTest(){
        Item expectedItem = ItemCreator.withAllProperties("third");
        ProductPage productPage = new ProductPage(driver)
                .openPage(expectedItem.getUrl())
                .addToWishlist();

        String productName = productPage.getNameProduct();
        Double productPrice = productPage.getPriceProduct();

        assertThat(productPage.isFavoriteItem()).isTrue();

        WishlistPage wishlistPage= productPage.goToWishlist();

        assertThat(wishlistPage.getCountOfFavoriteItemsMessage()).contains("Вас в Списке желаний 1 товаров");
        assertThat(wishlistPage.getFavoriteItemsListSize()).isEqualTo(1);
        assertThat(productName).contains(wishlistPage.getNameProduct(expectedItem.getUrl()));
        assertThat(wishlistPage.getProductPrice(expectedItem.getUrl())).isEqualTo(productPrice);
    }
//8
    @Test
    public void changesWhenChooseSimilarItemTest(){
        Item expectedItem = ItemCreator.withAllProperties("third");
        int similarItemOrder = 3;

        ProductPage productPage=new ProductPage(driver)
                .openPage(expectedItem.getUrl());

        String startTitle =productPage.getTitleProduct();
        productPage.chooseSimilarProduct(similarItemOrder);

        assertThat(startTitle).isNotEqualTo(productPage.getTitleProduct());
    }
}
