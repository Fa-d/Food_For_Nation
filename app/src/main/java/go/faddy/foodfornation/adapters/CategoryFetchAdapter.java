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

import java.util.List;
import java.util.Random;

import go.faddy.foodfornation.R;
import go.faddy.foodfornation.activities.ItemsListActivity;
import go.faddy.foodfornation.models.CategoriesModel;

public class CategoryFetchAdapter extends RecyclerView.Adapter<CategoryFetchAdapter.CategoryViewHolder> {
    private Context mCtx;
    private List<CategoriesModel> categoriesModelList;

    private int x[] = {
            R.drawable.ic_rice, R.drawable.ic_beans, R.drawable.ic_apple_tree, R.drawable.ic_lentils,
            R.drawable.ic_mustard, R.drawable.ic_chilli, R.drawable.ic_betal_leaf, R.drawable.ic_jute,
            R.drawable.ic_bee, R.drawable.ic_potato, R.drawable.ic_eggs, R.drawable.ic_duck
    };

    public CategoryFetchAdapter(Context mCtx, List<CategoriesModel> categoriesModelList) {
        this.mCtx = mCtx;
        this.categoriesModelList = categoriesModelList;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewCatName, textViewcatagory_count;
        private ImageView img;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCatName = itemView.findViewById(R.id.catagory_type_name);
            textViewcatagory_count = itemView.findViewById(R.id.catagory_count);
            img = itemView.findViewById(R.id.category_icon);
        }
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recycler_unit_for_categories, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoriesModel categoriesModel = categoriesModelList.get(position);
        holder.textViewCatName.setText(categoriesModel.getCategory_name());
        holder.textViewcatagory_count.setText(String.valueOf(categoriesModel.getCategory_count()));
        Random rand = new Random();
        int randInt = rand.nextInt( 12 );
        holder.img.setImageResource(x[randInt]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx.getApplicationContext(), ItemsListActivity.class);
                Integer temp = categoriesModel.getCategory_id();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                        | Intent.FLAG_ACTIVITY_CLEAR_TASK
                );

                intent.putExtra("categories", temp);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesModelList.size();
    }
}
