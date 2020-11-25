package go.faddy.foodfornation.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import go.faddy.foodfornation.R;
import go.faddy.foodfornation.adapters.ItemFetchAdapter;
import go.faddy.foodfornation.api.RetrofitClient;
import go.faddy.foodfornation.api.respones.ItemsResponse;
import go.faddy.foodfornation.models.ItemsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LatestItemsFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<ItemsModel> itemsModelList;
    private ItemFetchAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

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

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                itemsModelList.clear();
                adapter.notifyDataSetChanged();
                RetrofitClient.getInstance().getApi().getregionalitemshortdescription(23, 3)
                        .enqueue(new Callback<ItemsResponse>() {
                            @Override
                            public void onResponse(Call<ItemsResponse> call, Response<ItemsResponse> response) {
                                itemsModelList = response.body().getItems();
                                adapter = new ItemFetchAdapter(view.getContext(), itemsModelList);
                                recyclerView.setAdapter(adapter);
                            }

                            @Override
                            public void onFailure(Call<ItemsResponse> call, Throwable t) {
                                Log.d("error", t.getMessage());
                            }
                        });
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        RetrofitClient.getInstance().getApi().getregionalitemshortdescription(23, 3)
                .enqueue(new Callback<ItemsResponse>() {
                    @Override
                    public void onResponse(Call<ItemsResponse> call, Response<ItemsResponse> response) {
                        itemsModelList = response.body().getItems();
                        adapter = new ItemFetchAdapter(view.getContext(), itemsModelList);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<ItemsResponse> call, Throwable t) {
                        Log.d("error", t.getMessage());
                    }
                });


        return view;
    }
}
