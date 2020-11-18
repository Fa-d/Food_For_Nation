package go.faddy.foodfornation.models;

public class RegionsModel {
    int region_id, item_count;
    String region_name;

    public RegionsModel(int region_id, int item_count, String region_name) {
        this.region_id = region_id;
        this.item_count = item_count;
        this.region_name = region_name;
    }

    public int getRegion_id() {
        return region_id;
    }

    public int getItem_count() {
        return item_count;
    }

    public String getRegion_name() {
        return region_name;
    }
}
