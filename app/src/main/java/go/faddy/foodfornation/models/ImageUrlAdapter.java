package go.faddy.foodfornation.models;

public class ImageUrlAdapter {
    private String s_path, s_extension;

    public String getS_path() {
        return s_path;
    }

    public String getS_extension() {
        return s_extension;
    }

    public ImageUrlAdapter(String s_path, String s_extension) {
        this.s_path = s_path;
        this.s_extension = s_extension;
    }
}
