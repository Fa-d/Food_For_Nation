package go.faddy.foodfornation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

import go.faddy.foodfornation.R;
import go.faddy.foodfornation.adapters.ItemFetchAdapter;
import go.faddy.foodfornation.api.RetrofitClient;
import go.faddy.foodfornation.models.ItemsModel;
import go.faddy.foodfornation.api.respones.ItemsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsListActivity extends FragmentActivity {
    private RecyclerView recyclerView;
    private List<ItemsModel> itemsModelList;
    private ItemFetchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_items_by_category);
        recyclerView = findViewById(R.id.recycler_for_items_by_category);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Intent intent = getIntent();
        int x = intent.getIntExtra("categories", -1);
        if (x != -1) {
            apiFetch(x, 1);
        } else {
            x = intent.getIntExtra("fromRegion", -1);
            if (x != -1) {
                apiFetch(x, 2);
            } else {
                x = intent.getIntExtra("fromCity", -1);
                apiFetch(x, 3);
            }
        }
    }

    void apiFetch(int x, int bool) {
        Call<ItemsResponse> call;
        if (bool == 1) {
            call = RetrofitClient.getInstance().getApi().itemsByCategory(x);
        } else if (bool == 2) {
            call = RetrofitClient.getInstance().getApi().getregionalitemshortdescription(x, 1);
        } else {
            call = RetrofitClient.getInstance().getApi().getregionalitemshortdescription(x, 2);
        }
        call.enqueue(new Callback<ItemsResponse>() {
            @Override
            public void onResponse(Call<ItemsResponse> call, Response<ItemsResponse> response) {
                itemsModelList = response.body().getItems();
                adapter = new ItemFetchAdapter(getApplicationContext(), itemsModelList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ItemsResponse> call, Throwable t) {
                Toast.makeText(ItemsListActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("error", t.getMessage());
            }
        });
    }
}

