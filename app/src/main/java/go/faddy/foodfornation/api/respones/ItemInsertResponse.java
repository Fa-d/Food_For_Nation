package go.faddy.foodfornation.api.respones;

public class ItemInsertResponse {
    private boolean success;
    private int item_id;

    public ItemInsertResponse(boolean success, int item_id) {
        this.success = success;
        this.item_id = item_id;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getItem_id() {
        return item_id;
    }
}
