package model;

public class Filter {

    private String price;
    private String category;

    public static class Builder{
        private Filter filter;

        public Builder(){
            filter=new Filter();
        }

        public Builder withPrice(String price){
            filter.price=price;
            return this;
        }

        public Builder withCategory(String category){
            filter.category=category;
            return this;
        }

        public Filter build(){
            return filter;
        }
    }

    public String getPrice() {
        return price;
    }

    public void setColor(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
