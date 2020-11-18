package go.faddy.foodfornation.respones;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import go.faddy.foodfornation.models.CategoriesModel;

public class CategoriesResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("categories")
    private List<CategoriesModel> categories;

    public CategoriesResponse(boolean error, List<CategoriesModel> categories) {
        this.error = error;
        this.categories = categories;
    }

    public boolean isError() {
        return error;
    }

    public List<CategoriesModel> getCategories() {
        return categories;
    }
}
