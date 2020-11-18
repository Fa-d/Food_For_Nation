package go.faddy.foodfornation.respones;

import go.faddy.foodfornation.models.ItemDetailsModel;

public class ItemDetailsResponse {
    private boolean error;
    private ItemDetailsModel items;

    public ItemDetailsResponse(boolean error, ItemDetailsModel items) {
        this.error = error;
        this.items = items;
    }

    public boolean isError() {
        return error;
    }

    public ItemDetailsModel getItems() {
        return items;
    }
}
