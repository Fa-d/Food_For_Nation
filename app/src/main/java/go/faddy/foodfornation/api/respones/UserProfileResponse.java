package go.faddy.foodfornation.api.respones;

import java.math.BigInteger;
import java.util.List;

public class UserProfileResponse {
    private boolean error;
    private UsersProfileMiddleResponse items;

    public UserProfileResponse(boolean error, UsersProfileMiddleResponse items) {
        this.error = error;
        this.items = items;
    }

    public boolean isError() {
        return error;
    }

    public UsersProfileMiddleResponse getItems() {
        return items;
    }

    public static class UsersProfileMiddleResponse {

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

    public static class UsersProfilePostItemsModel {
        private int item_id;
        private BigInteger item_price;
        private String item_name, category_name, user_city, user_region, url_path, image_extention;

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
}

