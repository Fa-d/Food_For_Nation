package go.faddy.foodfornation.respones;

import go.faddy.foodfornation.models.ImageUrlAdapter;

public class ImageUrlFetchResponse {
    private boolean error;
    private ImageUrlAdapter images;

    public ImageUrlFetchResponse(boolean error, ImageUrlAdapter images) {
        this.error = error;
        this.images = images;
    }

    public boolean isError() {
        return error;
    }

    public ImageUrlAdapter getImages() {
        return images;
    }
}
