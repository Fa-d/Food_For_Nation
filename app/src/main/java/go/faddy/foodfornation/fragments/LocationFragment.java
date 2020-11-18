package go.faddy.foodfornation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

import go.faddy.foodfornation.R;
import go.faddy.foodfornation.adapters.CityFetchAdapter;
import go.faddy.foodfornation.adapters.RegionFetchAdapter;
import go.faddy.foodfornation.api.RetrofitClient;
import go.faddy.foodfornation.models.CitiesModel;
import go.faddy.foodfornation.models.RegionsModel;
import go.faddy.foodfornation.respones.ItemsbyLocationResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationFragment extends Fragment {
    private RecyclerView recyclerRegions, recyclerCities;
    private List<RegionsModel> regionsModelList;
    private List<CitiesModel> citiesModelList;
    private RegionFetchAdapter regionFetchAdapter;
    private CityFetchAdapter cityFetchAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_location_layout, container, false);
        recyclerRegions  = view.findViewById(R.id.recycler_region_recyclerview);
        recyclerCities = view.findViewById(R.id.recycler_city_recyclerview);
        recyclerRegions.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerRegions.setItemAnimator(new DefaultItemAnimator());
        recyclerCities.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerCities.setItemAnimator(new DefaultItemAnimator());

        Call<ItemsbyLocationResponse> call = RetrofitClient.getInstance().getApi().itemsByLocation();
        call.enqueue(new Callback<ItemsbyLocationResponse>() {
            @Override
            public void onResponse(Call<ItemsbyLocationResponse> call, Response<ItemsbyLocationResponse> response) {
                regionsModelList = response.body().getRegions();
                citiesModelList = response.body().getCities();
                regionFetchAdapter = new RegionFetchAdapter(view.getContext(), regionsModelList);
                cityFetchAdapter = new CityFetchAdapter(view.getContext(), citiesModelList);
                recyclerRegions.setAdapter(regionFetchAdapter);
                recyclerCities.setAdapter(cityFetchAdapter);
            }

            @Override
            public void onFailure(Call<ItemsbyLocationResponse> call, Throwable t) {

            }
        });

        return view;

    }
}
