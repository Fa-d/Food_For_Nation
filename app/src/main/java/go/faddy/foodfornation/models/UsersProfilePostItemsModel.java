package go.faddy.foodfornation.models;

import java.math.BigInteger;

public class UsersProfilePostItemsModel {
    int item_id;
    BigInteger item_price;
    String item_name, category_name, user_city, user_region, url_path, image_extention;

    public UsersProfilePostItemsModel(int item_id, BigInteger item_price, String item_name, String category_name, String user_city, String user_region, String url_path, String image_extention) {
        this.item_id = item_id;
        this.item_price = item_price;
        this.item_name = item_name;
        this.category_name = category_name;
        this.user_city = user_city;
        this.user_region = user_region;
        this.url_path = url_path;
        this.image_extention = image_extention;
    }

    public int getItem_id() {
        return item_id;
    }

    public BigInteger getItem_price() {
        return item_price;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getUser_city() {
        return user_city;
    }

    public String getUser_region() {
        return user_region;
    }

    public String getUrl_path() {
        return url_path;
    }

    public String getImage_extention() {
        return image_extention;
    }
}
