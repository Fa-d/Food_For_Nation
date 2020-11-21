package go.faddy.foodfornation.api.respones;

public class UserProfileResponse {
    private boolean error;
    private UsersProfileMiddleResponse items;

    public UserProfileResponse(boolean error, UsersProfileMiddleResponse items) {
        this.error = error;
        this.items = items;
    }

    public boolean isError() {
        return error;
    }

    public UsersProfileMiddleResponse getItems() {
        return items;
    }
}
