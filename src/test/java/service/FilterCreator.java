package service;

import model.Filter;

public class FilterCreator {
    public static final String PRICE="test.data.filter.price";
    public static final String CATEGORY="test.data.filter.category";

    public static Filter withEmptyPrice(){
        return new Filter.Builder()
                .withCategory(TestDataReader.getTestData(CATEGORY))
                .build();
    }

    public static Filter withEmptyCategory(){
        return new Filter.Builder()
                .withPrice(TestDataReader.getTestData(PRICE))
                .build();
    }

    public static Filter withAllProperty(){
        return new Filter.Builder()
                .withPrice(TestDataReader.getTestData(PRICE))
                .withCategory(TestDataReader.getTestData(CATEGORY))
                .build();
    }
}