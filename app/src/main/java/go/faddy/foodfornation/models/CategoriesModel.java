package go.faddy.foodfornation.models;

public class CategoriesModel {
    private int category_id, category_count;
    private String category_name;

    public CategoriesModel(int category_id, int category_count, String category_name) {
        this.category_id = category_id;
        this.category_count = category_count;
        this.category_name = category_name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public int getCategory_count() {
        return category_count;
    }

    public String getCategory_name() {
        return category_name;
    }
}
