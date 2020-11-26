package go.faddy.foodfornation.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import go.faddy.foodfornation.R;
import go.faddy.foodfornation.api.respones.UserProfileResponse;
import go.faddy.foodfornation.ui.activities.ItemDetailsActivity;
import go.faddy.foodfornation.ui.popup.ItemUpdateOrDeletePopUp;

public class UsersProfileItemFetchAdapter extends
        RecyclerView.Adapter<UsersProfileItemFetchAdapter.UsersProfileItemViewHolder> {
    private Context mCtx;
    private List<UserProfileResponse.UsersProfilePostItemsModel> usersProfilePostItemsModelList;

    public UsersProfileItemFetchAdapter(Context mCtx, List<UserProfileResponse.UsersProfilePostItemsModel> usersProfilePostItemsModelList) {
        this.mCtx = mCtx;
//        this.mCtx = new ContextThemeWrapper(mCtx, R.style.AppTheme);
        this.usersProfilePostItemsModelList = usersProfilePostItemsModelList;
    }

    @NonNull
    @Override
    public UsersProfileItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(
                R.layout.recylcler_unit_user_profile_items, parent, false);
        return new UsersProfileItemFetchAdapter.UsersProfileItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersProfileItemViewHolder holder, int position) {
        String url = null;
        UserProfileResponse.UsersProfilePostItemsModel usersProfilePostItemsModel = usersProfilePostItemsModelList.get(position);
        holder.itemName.setText(usersProfilePostItemsModel.getItem_name());
        holder.itemTitle.setText(usersProfilePostItemsModel.getCategory_name());
        holder.itemLocation.setText(usersProfilePostItemsModel.getUser_city() + usersProfilePostItemsModel.getUser_region());
        holder.itemPrice.setText(String.valueOf(usersProfilePostItemsModel.getItem_price()));
        if (usersProfilePostItemsModel.getUrl_path() != null) {
            url = "https://foodfornation.gov.bd/" + usersProfilePostItemsModel.getUrl_path()
                    + usersProfilePostItemsModel.getItem_id() + '.' +
                    usersProfilePostItemsModel.getImage_extention();
            Picasso.get()
                    .load(url)
                    .resize(200, 200)
                    .centerCrop()
                    .into(holder.userProfileItemImage);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(mCtx, String.valueOf(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mCtx.getApplicationContext(), ItemUpdateOrDeletePopUp.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    int item_id = usersProfilePostItemsModel.getItem_id();
                    intent.putExtra("item_id", item_id);
                    mCtx.startActivity(intent);
                    return true;
                }
            });
        }

        String finalUrl = url;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx.getApplicationContext(), ItemDetailsActivity.class);
                Integer temp = usersProfilePostItemsModel.getItem_id();
                intent.putExtra("item", temp);
                intent.putExtra("image_url", finalUrl);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersProfilePostItemsModelList.size();
    }

    public class UsersProfileItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemPrice, itemName, itemCategory, itemTitle, itemLocation;
        ImageView userProfileItemImage;

        public UsersProfileItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemPrice = itemView.findViewById(R.id.user_profile_price_item);
            itemName = itemView.findViewById(R.id.user_profile_item_name);
            itemCategory = itemView.findViewById(R.id.user_profile_item_cat);
            itemTitle = itemView.findViewById(R.id.user_profile_item_title);
            itemLocation = itemView.findViewById(R.id.user_profile_item_loc);
            userProfileItemImage = itemView.findViewById(R.id.user_profile_item_image);
        }
    }
}
