package go.faddy.foodfornation.fragments;

import android.os.Bundle;
import android.util.Log;
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

import java.util.List;

import go.faddy.foodfornation.R;
import go.faddy.foodfornation.adapters.ItemFetchAdapter;
import go.faddy.foodfornation.api.RetrofitClient;
import go.faddy.foodfornation.models.ItemsModel;
import go.faddy.foodfornation.respones.ItemsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LatestItemsFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<ItemsModel> itemsModelList;
    private ItemFetchAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latest_items_layout, container, false);
        recyclerView = view.findViewById(R.id.recycler_for_latest_items);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Call<ItemsResponse> call = RetrofitClient.getInstance().getApi().getregionalitemshortdescription(23, 3);
        call.enqueue(new Callback<ItemsResponse>() {
            @Override
            public void onResponse(Call<ItemsResponse> call, Response<ItemsResponse> response) {
                itemsModelList = response.body().getItems();
                adapter = new ItemFetchAdapter(view.getContext(), itemsModelList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ItemsResponse> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("error", t.getMessage());
            }
        });
        return view;
    }
}
