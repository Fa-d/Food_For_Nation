package go.faddy.foodfornation.api.respones;

import java.util.List;

import go.faddy.foodfornation.models.RegionsNameSpinnerModel;

public class RegionSpinnerResponse {
    private boolean error;
    private List<RegionsNameSpinnerModel> regions;

    public RegionSpinnerResponse(boolean error, List<RegionsNameSpinnerModel> regions) {
        this.error = error;
        this.regions = regions;
    }

    public boolean isError() {
        return error;
    }

    public List<RegionsNameSpinnerModel> getRegions() {
        return regions;
    }
}
