package go.faddy.foodfornation.respones;

import java.util.List;

import go.faddy.foodfornation.models.CitiesModel;
import go.faddy.foodfornation.models.RegionsModel;

public class ItemsbyLocationResponse {
    private boolean error;
    private List<RegionsModel> regions;
    private List<CitiesModel> cities;

    public ItemsbyLocationResponse(boolean error, List<RegionsModel> regions, List<CitiesModel> cities) {
        this.error = error;
        this.regions = regions;
        this.cities = cities;
    }

    public boolean isError() {
        return error;
    }

    public List<RegionsModel> getRegions() {
        return regions;
    }

    public List<CitiesModel> getCities() {
        return cities;
    }
}
