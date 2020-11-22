package go.faddy.foodfornation.api;

import go.faddy.foodfornation.api.respones.CategoriesResponse;
import go.faddy.foodfornation.api.respones.CheckErrorResponse;
import go.faddy.foodfornation.api.respones.CitySpinnerResponse;
import go.faddy.foodfornation.api.respones.ItemDetailsResponse;
import go.faddy.foodfornation.api.respones.ItemsResponse;
import go.faddy.foodfornation.api.respones.ItemsbyLocationResponse;
import go.faddy.foodfornation.api.respones.LoginResponse;
import go.faddy.foodfornation.api.respones.RegionSpinnerResponse;
import go.faddy.foodfornation.api.respones.UserIDResponse;
import go.faddy.foodfornation.api.respones.UserProfileResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {
    @GET("getcatagories")
    Call<CategoriesResponse> getCategories();

    @FormUrlEncoded
    @POST("itemsfromcategories")
    Call<ItemsResponse> itemsByCategory(
            @Field("category") int category
    );

    @FormUrlEncoded
    @POST("getitemdetailsdata")
    Call<ItemDetailsResponse> itemDetails(
            @Field("item") int item
    );

    @FormUrlEncoded
    @POST("getuserinformation")
    Call<UserProfileResponse> profileDetails(
            @Field("id") int id
    );

    @GET("getitembylocation")
    Call<ItemsbyLocationResponse> itemsByLocation();

    @FormUrlEncoded
    @POST("getregionalitemdescription")
    Call<ItemsResponse> getregionalitemshortdescription(
            @Field("regionid") int regionid,
            @Field("type") int type
    );

    @GET("getregionsname")
    Call<RegionSpinnerResponse> getRegionsNameSpinner();

    @FormUrlEncoded
    @POST("getcitybyregion")
    Call<CitySpinnerResponse> getCitiesNameSpinner(
            @Field("region_id") int region_id
    );

    @Multipart
    @POST("insertitem")
    Call<CheckErrorResponse> insertItem(
            @Part("user_id") int user_id,
            @Part("category_id") int category_id,
            @Part("item_price") int item_price,
            @Part("user_ip") String user_ip,
            @Part("dt_expiration") String dt_expiration,
            @Part("user_address") String user_address,
            @Part("item_title") String item_title,
            @Part("item_description") String item_description,
            @Part("zip") int zip,
            @Part("region_name") String region_name,
            @Part("city_name") String city_name,
            @Part("d_coord_lat") double d_coord_lat,
            @Part("d_coord_long") double d_coord_long,
            @Part MultipartBody.Part image
    );

    @FormUrlEncoded
    @POST("checkpass")
    Call<LoginResponse> checkPassword(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("registeruser")
    Call<CheckErrorResponse> registerUser(
            @Field("full_user_name") String full_user_name,
            @Field("user_name") String user_name,
            @Field("password") String password,
            @Field("email") String email,
            @Field("website") String website,
            @Field("landline") int landline,
            @Field("mobile_no") int mobile_no,
            @Field("user_address") String user_address,
            @Field("zip") String zip,
            @Field("has_company") int has_company,
            @Field("region_name") String region_name,
            @Field("city_name") String city_name,
            @Field("ip") String ip,
            @Field("coord_lat") double coord_lat,
            @Field("coord_long") double coord_long,
            @Field("user_desc") String user_desc
    );


    @FormUrlEncoded
    @POST("useridatregistration")
    Call<UserIDResponse> checkId(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("addcomment")
    Call<CheckErrorResponse> addComment(
            @Field("user_id") int user_id,
            @Field("item_id") int item_id,
            @Field("comment_title") String comment_title,
            @Field("comment_body") String comment_body
    );

    @FormUrlEncoded
    @POST("updatecomment")
    Call<CheckErrorResponse> updateComment(
            @Field("comment_body") String comment_body,
            @Field("comment_title") String comment_title,
            @Field("comment_id") int comment_id
    );

    @FormUrlEncoded
    @POST("deletecomment")
    Call<CheckErrorResponse> deleteComment(
            @Field("user_id") int user_id,
            @Field("comment_id") int comment_id
    );

    @FormUrlEncoded
    @POST("updateuserprofile")
    Call<CheckErrorResponse> updateUserProfile(
            @Field("user_id") int user_id,
            @Field("full_user_name") String full_user_name,
            @Field("user_name") String user_name,
            @Field("password") String password,
            @Field("email") String email,
            @Field("website") String website,
            @Field("landline") int landline,
            @Field("mobile_no") int mobile_no,
            @Field("user_address") String user_address,
            @Field("zip") String zip,
            @Field("has_company") int has_company,
            @Field("region_name") String region_name,
            @Field("city_name") String city_name,
            @Field("ip") String ip,
            @Field("coord_lat") double coord_lat,
            @Field("coord_long") double coord_long,
            @Field("user_desc") String user_desc
    );
}
