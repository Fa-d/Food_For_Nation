package go.faddy.foodfornation.api.respones;

public class CheckErrorResponse {
    private boolean error;

    public CheckErrorResponse(boolean error) {
        this.error = error;
    }

    public boolean isError() {
        return error;
    }
}
