package go.faddy.foodfornation.api;

import go.faddy.foodfornation.respones.CategoriesResponse;
import go.faddy.foodfornation.respones.CheckErrorResponse;
import go.faddy.foodfornation.respones.CitySpinnerResponse;
import go.faddy.foodfornation.respones.ImageUrlFetchResponse;
import go.faddy.foodfornation.respones.ItemDetailsResponse;
import go.faddy.foodfornation.respones.ItemsResponse;
import go.faddy.foodfornation.respones.ItemsbyLocationResponse;
import go.faddy.foodfornation.respones.RegionSpinnerResponse;
import go.faddy.foodfornation.respones.UserProfileResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
    @GET("getcatagories")
    Call<CategoriesResponse> getCategories();

    @FormUrlEncoded
    @POST("itemsfromcategories")
    Call<ItemsResponse> itemsByCategory(
            @Field("category") int category
    );

    @FormUrlEncoded
    @POST("itemsfromcategories")
    Call<ImageUrlFetchResponse> imageUrls(
            @Field("id") int id
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

    @FormUrlEncoded
    @POST("insertitem")
    Call<CheckErrorResponse> insertItem(
            @Field("user_id") int user_id,
            @Field("category_id") int category_id,
            @Field("item_price") int item_price,
            @Field("user_ip") String user_ip,
            @Field("dt_expiration") String dt_expiration,
            @Field("user_address") String user_address,
            @Field("item_title") String item_title,
            @Field("item_description") String item_description,
            @Field("zip") int zip,
            @Field("region_name") String region_name,
            @Field("city_name") String city_name,
            @Field("d_coord_lat") int d_coord_lat,
            @Field("d_coord_long") int d_coord_long
    );
}
