package go.faddy.foodfornation.api.respones;

import java.util.List;

import go.faddy.foodfornation.models.CitiesNameSpinnerModel;

public class CitySpinnerResponse {
    private boolean error;
    private List<CitiesNameSpinnerModel> cities;

    public CitySpinnerResponse(boolean error, List<CitiesNameSpinnerModel> cities) {
        this.error = error;
        this.cities = cities;
    }

    public boolean isError() {
        return error;
    }

    public List<CitiesNameSpinnerModel> getCities() {
        return cities;
    }
}
