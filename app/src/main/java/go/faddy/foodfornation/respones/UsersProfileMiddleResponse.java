package go.faddy.foodfornation.respones;

import java.util.List;

import go.faddy.foodfornation.models.UsersProfilePostItemsModel;

public class UsersProfileMiddleResponse {

    private String user_name, user_email, user_website, user_mobile_no, user_region, user_city;
    private List<UsersProfilePostItemsModel> users_post_items;

    public UsersProfileMiddleResponse(String user_name, String user_email, String user_website, String user_mobile_no, String user_region, String user_city, List<UsersProfilePostItemsModel> users_post_items) {

        this.user_name = user_name;
        this.user_email = user_email;
        this.user_website = user_website;
        this.user_mobile_no = user_mobile_no;
        this.user_region = user_region;
        this.user_city = user_city;
        this.users_post_items = users_post_items;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_website() {
        return user_website;
    }

    public String getUser_mobile_no() {
        return user_mobile_no;
    }

    public String getUser_region() {
        return user_region;
    }

    public String getUser_city() {
        return user_city;
    }

    public List<UsersProfilePostItemsModel> getUsers_post_items() {
        return users_post_items;
    }
}
