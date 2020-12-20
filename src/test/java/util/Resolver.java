package util;

public class Resolver {
    public static int resolveCost(String cost){
        return Integer.parseInt(String.join("", cost.trim().split("[.$]")));
    }

    public static int resolveInt(String string){
        return Integer.parseInt(string.trim());
    }

    public static String resolveTemplate(String template, String data){
        return String.format(template, data);
    }

    public static String resolveTemplate(String template, int data){
        return String.format(template, data);
    }

    public static int resolveDiscount(int cost, int discount){
        return cost - (int)(cost * (discount / 100.));
    }
}