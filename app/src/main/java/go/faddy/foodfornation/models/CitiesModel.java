package go.faddy.foodfornation.models;

public class CitiesModel {
    int city_id, item_count;
    String city_name;

    public CitiesModel(int city_id, int item_count, String city_name) {
        this.city_id = city_id;
        this.item_count = item_count;
        this.city_name = city_name;
    }

    public int getCity_id() {
        return city_id;
    }

    public int getItem_count() {
        return item_count;
    }

    public String getCity_name() {
        return city_name;
    }
}
