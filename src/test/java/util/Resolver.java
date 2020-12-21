package util;

import org.openqa.selenium.WebElement;

public class Resolver {

    public static double resolveCost(Double price, double count){
        return  Math.round(price*count*100.0)/100.0;
    }

    public static String resolveTemplate(String template, int data){
        return String.format(template, data);
    }

    public static String resolveTemplate(String template, String data){
        return String.format(template, data);
    }

}