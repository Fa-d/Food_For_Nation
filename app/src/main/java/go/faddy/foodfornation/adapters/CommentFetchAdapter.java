package go.faddy.foodfornation.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import go.faddy.foodfornation.R;
import go.faddy.foodfornation.api.RetrofitClient;
import go.faddy.foodfornation.api.respones.CheckErrorResponse;
import go.faddy.foodfornation.models.CommentModel;
import go.faddy.foodfornation.ui.popup.PopUpCommentEdit;
import go.faddy.foodfornation.utils.storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFetchAdapter extends RecyclerView.Adapter<CommentFetchAdapter.CommentViewHolder> {
    private Context mCtx;
    private List<CommentModel> commentModelList;

    public CommentFetchAdapter(Context mCtx, List<CommentModel> commentModelList) {
//        this.mCtx = new ContextThemeWrapper(mCtx, R.style.AppTheme);
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
        int user_id = SharedPrefManager.getInstance(mCtx).getUser().getUser_id();
        CommentModel commentModel = commentModelList.get(position);
        holder.textViewDescription.setText(commentModel.getComment_body());
        holder.textViewDate.setText(commentModel.getCommented_date());
        holder.textViewCommenterName.setText(commentModel.getCommenter_name());
        holder.textViewCommentTitle.setText(commentModel.getComment_title());

        if (commentModel.getCommenter_id() == user_id) {
            holder.edit.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mCtx, PopUpCommentEdit.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("comment_id", commentModel.getComment_id());
                    intent.putExtra("title", commentModel.getComment_title());
                    intent.putExtra("body", commentModel.getComment_body());
                    mCtx.startActivity(intent);
                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call<CheckErrorResponse> call  = RetrofitClient.getInstance().getApi().
                            deleteComment(commentModel.getCommenter_id(), commentModel.getComment_id());
                    call.enqueue(new Callback<CheckErrorResponse>() {
                        @Override
                        public void onResponse(Call<CheckErrorResponse> call, Response<CheckErrorResponse> response) {
                            if(!response.body().isError()){

                                removeAt(position);

//                                Intent intent = new Intent(mCtx.getApplicationContext(), mCtx.getClass());
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                mCtx.startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<CheckErrorResponse> call, Throwable t) {

                        }
                    });
                }
            });

        } else {
            holder.edit.setVisibility(View.GONE);
            holder.delete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return commentModelList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCommentTitle, textViewCommenterName, textViewDate, textViewDescription;
        ImageButton edit, delete;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCommentTitle = itemView.findViewById(R.id.comment_title);
            textViewCommenterName = itemView.findViewById(R.id.commenter_name);
            textViewDate = itemView.findViewById(R.id.comment_date);
            textViewDescription = itemView.findViewById(R.id.comment_description);
            edit = itemView.findViewById(R.id.comment_edit_button);
            delete = itemView.findViewById(R.id.comment_delete_button);
        }
    }
    public void removeAt(int position) {
        commentModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, commentModelList.size());
    }
}
