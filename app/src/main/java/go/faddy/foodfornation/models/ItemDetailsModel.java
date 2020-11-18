package go.faddy.foodfornation.models;

import java.math.BigInteger;
import java.util.List;

public class ItemDetailsModel {
    private String s_title, s_description, s_city, s_region, s_address, s_title_category,
            dt_pub_date, users_name, user_date_registered, users_mobile_number, users_items;
    BigInteger i_price;
    Integer users_unique_id;
    private List<CommentModel> comment;

    public String getS_title() {
        return s_title;
    }

    public String getS_description() {
        return s_description;
    }

    public String getS_city() {
        return s_city;
    }

    public String getS_region() {
        return s_region;
    }

    public String getS_address() {
        return s_address;
    }

    public String getS_title_category() {
        return s_title_category;
    }

    public String getDt_pub_date() {
        return dt_pub_date;
    }

    public String getUsers_name() {
        return users_name;
    }

    public String getUser_date_registered() {
        return user_date_registered;
    }

    public String getUsers_mobile_number() {
        return users_mobile_number;
    }

    public String getUsers_items() {
        return users_items;
    }

    public BigInteger getI_price() {
        return i_price;
    }

    public Integer getUsers_unique_id() {
        return users_unique_id;
    }

    public List<CommentModel> getComment() {
        return comment;
    }

    public ItemDetailsModel(String s_title, String s_description, String s_city, String s_region,
                            String s_address, String s_title_category, String dt_pub_date,
                            String users_name, String user_date_registered, String users_mobile_number,
                            String users_items, BigInteger i_price, Integer users_unique_id,
                            List<CommentModel> comment) {
        this.s_title = s_title;
        this.s_description = s_description;
        this.s_city = s_city;
        this.s_region = s_region;
        this.s_address = s_address;
        this.s_title_category = s_title_category;
        this.dt_pub_date = dt_pub_date;
        this.users_name = users_name;
        this.user_date_registered = user_date_registered;
        this.users_mobile_number = users_mobile_number;
        this.users_items = users_items;
        this.i_price = i_price;
        this.users_unique_id = users_unique_id;
        this.comment = comment;
    }
}
