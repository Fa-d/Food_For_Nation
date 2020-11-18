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
import go.faddy.foodfornation.models.CitiesModel;

public class CityFetchAdapter extends RecyclerView.Adapter<CityFetchAdapter.CityFetchViewHolder> {
    private Context mCtx;
    private List<CitiesModel> citiesModelList;

    public CityFetchAdapter(Context mCtx, List<CitiesModel> citiesModelList) {
        this.mCtx = mCtx;
        this.citiesModelList = citiesModelList;
    }

    @NonNull
    @Override
    public CityFetchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(
                R.layout.recycler_unit_location, parent, false);
        return new CityFetchAdapter.CityFetchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityFetchViewHolder holder, int position) {
        CitiesModel citiesModel = citiesModelList.get(position);
        holder.recycler_city_name.setText(citiesModel.getCity_name());
        holder.recycler_city_item_count.setText(String.valueOf(citiesModel.getItem_count()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx.getApplicationContext(), ItemsListActivity.class);
                intent.putExtra("fromCity", citiesModel.getCity_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                        | Intent.FLAG_ACTIVITY_CLEAR_TASK
                );
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return citiesModelList.size();
    }

    public class CityFetchViewHolder extends RecyclerView.ViewHolder {
        private ImageView user_profile_item_image;
        private TextView recycler_city_name, recycler_city_item_count;
        private CardView cardView;

        public CityFetchViewHolder(@NonNull View itemView) {
            super(itemView);
            user_profile_item_image = itemView.findViewById(R.id.user_profile_item_image);
            recycler_city_name = itemView.findViewById(R.id.recycler_region_name);
            recycler_city_item_count = itemView.findViewById(R.id.recycler_region_item_count);
            cardView = itemView.findViewById(R.id.region_recycler_cardview);
        }
    }
}
