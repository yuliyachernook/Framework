package service;

import model.Item;

import static util.Resolver.resolveTemplate;

public class ItemCreator {
    public static final String ITEM_URL_TEMPLATE = "test.data.%s.uri";
    public static final String ITEM_NAME_TEMPLATE = "test.data.%s.name";
    public static final String ITEM_SIZE_TEMPLATE = "test.data.%s.size";
    public static final String ITEM_COST_TEMPLATE = "test.data.%s.cost";
    public static final String ITEM_COLOR_TEMPLATE = "test.data.%s.color";
    public static final String ITEM_COUNT_TEMPLATE = "test.data.%s.count";

    public static Item withAllProperties(String orderNumber){
        orderNumber = orderNumber.toLowerCase();

        String itemUrl = resolveTemplate(ITEM_URL_TEMPLATE, orderNumber);
        String itemName = resolveTemplate(ITEM_NAME_TEMPLATE, orderNumber);
        String itemSize = resolveTemplate(ITEM_SIZE_TEMPLATE, orderNumber);
        String itemCost = resolveTemplate(ITEM_COST_TEMPLATE, orderNumber);
        String itemColor = resolveTemplate(ITEM_COLOR_TEMPLATE, orderNumber);
        String itemCount = resolveTemplate(ITEM_COUNT_TEMPLATE, orderNumber);

        return new Item(TestDataReader.getTestData(itemUrl),
                TestDataReader.getTestData(itemName),
                TestDataReader.getTestData(itemSize),
                TestDataReader.getTestData(itemColor),
                Double.parseDouble(TestDataReader.getTestData(itemCost)),
                Integer.parseInt(TestDataReader.getTestData(itemCount)));
    }
}
