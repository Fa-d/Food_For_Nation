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
import go.faddy.foodfornation.models.CategoriesModel;
import go.faddy.foodfornation.ui.activities.ItemsListActivity;

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
        setImage(holder);
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

    private void setImage(CategoryViewHolder holder) {
        switch (holder.getAdapterPosition()) {
            case 0:
                holder.img.setImageResource(R.drawable.ic_mango);
                break;
            case 1:
                holder.img.setImageResource(R.drawable.ic_cow);
                break;
            case 2:
                holder.img.setImageResource(R.drawable.ic_lychee);
                break;
            case 3:
                holder.img.setImageResource(R.drawable.ic_honey);
                break;
            case 4:
                holder.img.setImageResource(R.drawable.ic_pumpkin);
                break;
            case 5:
                holder.img.setImageResource(R.drawable.ic_rice);
                break;
            case 6:
                holder.img.setImageResource(R.drawable.ic_jute);
                break;
            case 7:
                holder.img.setImageResource(R.drawable.ic_eggplant);
                break;
            case 8:
                holder.img.setImageResource(R.drawable.ic_okra);
                break;
            case 10:
                holder.img.setImageResource(R.drawable.ic_eggs);
                break;
            case 11:
                holder.img.setImageResource(R.drawable.ic_gourd);
                break;
            case 12:
                holder.img.setImageResource(R.drawable.ic_mustard);
                break;
            case 13:
                holder.img.setImageResource(R.drawable.ic_pumpkin);
                break;
            case 14:
                holder.img.setImageResource(R.drawable.ic_hen);
                break;
            case 15:
                holder.img.setImageResource(R.drawable.ic_gourd);
                break;
            case 16:
                holder.img.setImageResource(R.drawable.ic_duck);
                break;
            case 17:
                holder.img.setImageResource(R.drawable.ic_cucumber);
                break;
            case 18:
                holder.img.setImageResource(R.drawable.ic_lemon);
                break;
            case 19:
                holder.img.setImageResource(R.drawable.ic_potato);
                break;
            case 21:
                holder.img.setImageResource(R.drawable.ic_bananas);
                break;
            case 23:
                holder.img.setImageResource(R.drawable.ic_guava);
                break;
            case 24:
                holder.img.setImageResource(R.drawable.ic_tomato);
                break;
            case 25:
                holder.img.setImageResource(R.drawable.ic_chilli);
                break;
            case 26:
                holder.img.setImageResource(R.drawable.ic_duck);
                break;
            default:
                Random rand = new Random();
                int randInt = rand.nextInt(12);
                holder.img.setImageResource(x[randInt]);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return categoriesModelList.size();
    }
}
