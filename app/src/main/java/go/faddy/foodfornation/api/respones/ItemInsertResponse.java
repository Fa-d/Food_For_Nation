package go.faddy.foodfornation.api.respones;

public class ItemInsertResponse {
    private boolean success;
    private int item_id, category_id;

    public ItemInsertResponse(boolean success, int item_id, int category_id) {
        this.success = success;
        this.item_id = item_id;
        this.category_id = category_id;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getItem_id() {
        return item_id;
    }

    public int getCategory_id() {
        return category_id;
    }
}
