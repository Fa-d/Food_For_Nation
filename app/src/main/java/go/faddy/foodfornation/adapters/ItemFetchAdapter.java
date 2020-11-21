package go.faddy.foodfornation.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import go.faddy.foodfornation.R;
import go.faddy.foodfornation.ui.activities.ItemDetailsActivity;
import go.faddy.foodfornation.models.ItemsModel;

public class ItemFetchAdapter extends RecyclerView.Adapter<ItemFetchAdapter.ItemViewHolder> {
    private Context mCtx;
    private List<ItemsModel> itemsModelList;

    public ItemFetchAdapter(Context mCtx, List<ItemsModel> itemsModelList) {
        this.mCtx = mCtx;
        this.itemsModelList = itemsModelList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(
                R.layout.recycler_unit_items, parent, false);
        return new ItemFetchAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String url = null;
        ItemsModel itemsModel = itemsModelList.get(position);
        holder.textViewItemLoc.setText("Location:  " + itemsModel.getCity() +", " +  itemsModel.getRegion());
        holder.textViewItemName.setText(itemsModel.getItem_title());
        holder.textViewUserName.setText("User:  " + itemsModel.getUser_name());
        holder.textViewItemCat.setText(itemsModel.getItem_category());
        if( itemsModel.getUrl_path() != null){
            url = "https://foodfornation.gov.bd/" + itemsModel.getUrl_path() + itemsModel.getImages_id() +'.' + itemsModel.getImage_ext();
            Picasso.get()
                    .load(url)
                .resize(200, 200)
                    .centerCrop()
                    .into(holder.imageView);
        }
        holder.textViewPriceItem.setText("Price:  " + String.valueOf(itemsModel.getPrice()));

        String finalUrl = url;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx.getApplicationContext(), ItemDetailsActivity.class);
                Integer temp = itemsModel.getItem_id();
                intent.putExtra("item", temp);
                intent.putExtra("image_url", finalUrl);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsModelList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewPriceItem, textViewItemName,
                textViewItemCat, textViewItemLoc, textViewUserName;
        private ImageView imageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPriceItem = itemView.findViewById(R.id.price_item);
            textViewItemName = itemView.findViewById(R.id.item_name);
            textViewItemCat = itemView.findViewById(R.id.item_cat);
            textViewItemLoc = itemView.findViewById(R.id.item_loc);
            textViewUserName = itemView.findViewById(R.id.user_name);
            imageView = itemView.findViewById(R.id.item_image);
        }
    }
}
