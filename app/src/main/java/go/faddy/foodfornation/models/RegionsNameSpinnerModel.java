package go.faddy.foodfornation.models;

public class RegionsNameSpinnerModel {
    String region_name;
    int region_id;

    public RegionsNameSpinnerModel(String region_name, int region_id) {
        this.region_name = region_name;
        this.region_id = region_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public int getRegion_id() {
        return region_id;
    }
}
