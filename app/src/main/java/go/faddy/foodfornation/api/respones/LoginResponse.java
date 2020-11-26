package go.faddy.foodfornation.api.respones;

public class LoginResponse {

    private boolean error;
    private int user_id;
    private String region, city;

    public LoginResponse(boolean error, int user_id, String region, String city) {
        this.error = error;
        this.user_id = user_id;
        this.region = region;
        this.city = city;
    }

    public boolean isError() {
        return error;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }
}
