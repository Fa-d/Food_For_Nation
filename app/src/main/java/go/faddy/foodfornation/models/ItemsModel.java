package go.faddy.foodfornation.models;

import java.math.BigInteger;

public class ItemsModel {
    private int item_id, images_id;
    private BigInteger price;
    private String item_title;
    private String city;
    private String region;
    private String item_category;
    private String image_ext;
    private String url_path;
    private String user_name;

    public ItemsModel(int item_id, int images_id, BigInteger price, String item_title, String city, String region, String item_category, String image_ext, String url_path, String user_name) {
        this.item_id = item_id;
        this.images_id = images_id;
        this.price = price;
        this.item_title = item_title;
        this.city = city;
        this.region = region;
        this.item_category = item_category;
        this.image_ext = image_ext;
        this.url_path = url_path;
        this.user_name = user_name;
    }

    public int getItem_id() {
        return item_id;
    }

    public int getImages_id() {
        return images_id;
    }

    public BigInteger getPrice() {
        return price;
    }

    public String getItem_title() {
        return item_title;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getItem_category() {
        return item_category;
    }

    public String getImage_ext() {
        return image_ext;
    }

    public String getUrl_path() {
        return url_path;
    }

    public String getUser_name() {
        return user_name;
    }
}


