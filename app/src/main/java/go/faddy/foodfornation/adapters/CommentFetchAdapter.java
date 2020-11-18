package go.faddy.foodfornation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import go.faddy.foodfornation.R;
import go.faddy.foodfornation.models.CommentModel;

public class CommentFetchAdapter extends RecyclerView.Adapter<CommentFetchAdapter.CommentViewHolder> {
    private Context mCtx;
    private List<CommentModel> commentModelList;

    public CommentFetchAdapter(Context mCtx, List<CommentModel> commentModelList) {
        this.mCtx = mCtx;
        this.commentModelList = commentModelList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyler_unit_comment, parent, false);
        return new CommentFetchAdapter.CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentModel commentModel = commentModelList.get(position);
        holder.textViewDescription.setText(commentModel.getComment_body());
        holder.textViewDate.setText(commentModel.getCommented_date());
        holder.textViewCommenterName.setText(commentModel.getCommenter_name());
        holder.textViewCommentTitle.setText(commentModel.getComment_title());
    }

    @Override
    public int getItemCount() {
        return commentModelList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCommentTitle, textViewCommenterName, textViewDate, textViewDescription;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCommentTitle = itemView.findViewById(R.id.comment_title);
            textViewCommenterName = itemView.findViewById(R.id.commenter_name);
            textViewDate = itemView.findViewById(R.id.comment_date);
            textViewDescription = itemView.findViewById(R.id.comment_description);
        }
    }
}
