package go.faddy.foodfornation.api.respones;

import java.util.List;

import go.faddy.foodfornation.models.ItemsModel;

public class ItemsResponse {
    private boolean error;
    private List<ItemsModel> items;

    public ItemsResponse(boolean error, List<ItemsModel> items) {
        this.error = error;
        this.items = items;
    }

    public boolean isError() {
        return error;
    }

    public List<ItemsModel> getItems() {
        return items;
    }
}
