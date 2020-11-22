package go.faddy.foodfornation.models;

public class CommentModel {
    private String commenter_name, comment_title, comment_body, commented_date;
    int commenter_id, comment_id;

    public CommentModel(String commenter_name, String comment_title, String comment_body,
                        String commented_date, int commenter_id, int comment_id) {
        this.commenter_name = commenter_name;
        this.comment_title = comment_title;
        this.comment_body = comment_body;
        this.commented_date = commented_date;
        this.commenter_id = commenter_id;
        this.comment_id = comment_id;
    }

    public String getCommenter_name() {
        return commenter_name;
    }

    public String getComment_title() {
        return comment_title;
    }

    public String getComment_body() {
        return comment_body;
    }

    public String getCommented_date() {
        return commented_date;
    }

    public int getCommenter_id() {
        return commenter_id;
    }

    public int getComment_id() {
        return comment_id;
    }
}
