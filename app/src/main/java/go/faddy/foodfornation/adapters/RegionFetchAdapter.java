package go.faddy.foodfornation.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import go.faddy.foodfornation.R;
import go.faddy.foodfornation.activities.ItemsListActivity;
import go.faddy.foodfornation.models.RegionsModel;

public class RegionFetchAdapter extends RecyclerView.Adapter<RegionFetchAdapter.RegionFetchViewHolder> {
    private Context mCtx;
    private List<RegionsModel> regionsModelList;

    public RegionFetchAdapter(Context mCtx, List<RegionsModel> regionsModelList) {
        this.mCtx = mCtx;
        this.regionsModelList = regionsModelList;
    }

    @NonNull
    @Override
    public RegionFetchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(
                R.layout.recycler_unit_location, parent, false);
        return new RegionFetchAdapter.RegionFetchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegionFetchViewHolder holder, int position) {
        RegionsModel regionsModel = regionsModelList.get(position);
        holder.region_name.setText(regionsModel.getRegion_name());
        holder.region_item_count.setText(String.valueOf(regionsModel.getItem_count()));
        holder.region_recycler_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx.getApplicationContext(), ItemsListActivity.class);
                intent.putExtra("fromRegion", regionsModel.getRegion_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                        | Intent.FLAG_ACTIVITY_CLEAR_TASK
                );
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return regionsModelList.size();
    }

    public class RegionFetchViewHolder extends RecyclerView.ViewHolder {
        private ImageView user_profile_item_image;
        private TextView region_name, region_item_count;
        CardView region_recycler_cardview;

        public RegionFetchViewHolder(@NonNull View itemView) {
            super(itemView);
            user_profile_item_image = itemView.findViewById(R.id.user_profile_item_image);
            region_name = itemView.findViewById(R.id.recycler_region_name);
            region_item_count = itemView.findViewById(R.id.recycler_region_item_count);
            region_recycler_cardview = itemView.findViewById(R.id.region_recycler_cardview);
        }
    }
}
