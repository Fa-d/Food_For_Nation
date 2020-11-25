package go.faddy.foodfornation.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import go.faddy.foodfornation.R;
import go.faddy.foodfornation.adapters.CategoryFetchAdapter;
import go.faddy.foodfornation.api.RetrofitClient;
import go.faddy.foodfornation.models.CategoriesModel;
import go.faddy.foodfornation.api.respones.CategoriesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<CategoriesModel> categoriesModelList;
    private CategoryFetchAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories_layout, container, false);
        recyclerView = view.findViewById(R.id.category_recycler);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Call<CategoriesResponse> call = RetrofitClient.getInstance().getApi().getCategories();
        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                categoriesModelList = response.body().getCategories();
                adapter = new CategoryFetchAdapter(view.getContext(), categoriesModelList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
            }
        });
        return view;
    }
}
