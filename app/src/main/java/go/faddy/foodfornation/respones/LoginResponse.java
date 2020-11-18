package go.faddy.foodfornation.respones;

public class LoginResponse {
    private int user_id;
    private boolean error;

    public LoginResponse(int user_id, boolean error) {
        this.user_id = user_id;
        this.error = error;
    }

    public int getUser_id() {
        return user_id;
    }

    public boolean isError() {
        return error;
    }
}
