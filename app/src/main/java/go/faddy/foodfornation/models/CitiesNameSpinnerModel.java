package go.faddy.foodfornation.models;

public class CitiesNameSpinnerModel {
    int city_id;
    String city_name;

    public CitiesNameSpinnerModel(int city_id, String city_name) {
        this.city_id = city_id;
        this.city_name = city_name;
    }

    public int getCity_id() {
        return city_id;
    }

    public String getCity_name() {
        return city_name;
    }
}
