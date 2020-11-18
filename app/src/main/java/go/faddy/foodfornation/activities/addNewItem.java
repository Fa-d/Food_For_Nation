package go.faddy.foodfornation.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import go.faddy.foodfornation.R;
import go.faddy.foodfornation.api.RetrofitClient;
import go.faddy.foodfornation.models.CategoriesModel;
import go.faddy.foodfornation.models.CitiesNameSpinnerModel;
import go.faddy.foodfornation.models.RegionsNameSpinnerModel;
import go.faddy.foodfornation.respones.CategoriesResponse;
import go.faddy.foodfornation.respones.CheckErrorResponse;
import go.faddy.foodfornation.respones.CitySpinnerResponse;
import go.faddy.foodfornation.respones.RegionSpinnerResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class addNewItem extends Activity implements OnItemSelectedListener, LocationListener {
    private Spinner spinnerCategories, spinnerRegions, spinnerCities;
    private List<CategoriesModel> categoriesModelList;
    private List<RegionsNameSpinnerModel> regionsNameSpinnerModelList;
    private List<CitiesNameSpinnerModel> citySpinnerResponseList;
    private Button testing, get_expiration_date, publish_button;
    private double latitude, longitude;
    private LocationManager locationManager;
    private Criteria criteria;
    private String bestProvider;
    private int mYear, mMonth, mDay;
    private EditText item_price, item_title, item_description, user_zip, user_address;
    private String categoryName, regionName, cityName, price, itemTitle, itemDescription,
            userZip, experationDate, userAddress, ip;
    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        initilizeIDs();
        get_expiration_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(addNewItem.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                mYear = year;
                                mMonth = monthOfYear;
                                mDay = dayOfMonth;
//                                Toast.makeText(addNewItem.this,String.valueOf(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year) , Toast.LENGTH_SHORT).show();
                                get_expiration_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        testing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(addNewItem.this,
                        String.valueOf(user_id), Toast.LENGTH_SHORT).show();
            }
        });
        publish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchTexts();
            }
        });

        Call<CategoriesResponse> callCategories = RetrofitClient.getInstance().getApi().getCategories();
        callCategories.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                categoriesModelList = response.body().getCategories();
                populateSpinner(1);
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
            }
        });
        Call<RegionSpinnerResponse> callRegions = RetrofitClient.getInstance().getApi().getRegionsNameSpinner();
        callRegions.enqueue(new Callback<RegionSpinnerResponse>() {
            @Override
            public void onResponse(Call<RegionSpinnerResponse> call, Response<RegionSpinnerResponse> response) {
                regionsNameSpinnerModelList = response.body().getRegions();
                populateSpinner(2);
            }

            @Override
            public void onFailure(Call<RegionSpinnerResponse> call, Throwable t) {
            }
        });
        spinnerCategories.setOnItemSelectedListener(this);
        spinnerRegions.setOnItemSelectedListener(this);
        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", -1);
    }

    private void fetchTexts() {
        categoryName = spinnerCategories.getSelectedItem().toString();
        regionName = spinnerRegions.getSelectedItem().toString();
        cityName = spinnerCities.getSelectedItem().toString();

        price = item_price.getText().toString().trim();
        itemTitle = item_title.getText().toString().trim();
        itemDescription = item_description.getText().toString().trim();
        userZip = user_zip.getText().toString().trim();
        userAddress = user_address.getText().toString().trim();

        try {
            experationDate = stringDateToMySqlDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ip = getIP();
        while (latitude == 0) {
            getLocation();
        }

        int category_id = categoriesModelList.get(spinnerCategories.getSelectedItemPosition()).getCategory_id();
        Toast.makeText(this, String.valueOf(category_id), Toast.LENGTH_SHORT).show();
        if ((category_id != 0) && (Integer.parseInt(price) != 0) && (ip != null) && (experationDate != null) &&
                (userAddress != null) && (itemTitle != null) && (itemDescription != null) && (Integer.parseInt(userZip)
                != 0) && (regionName != null) && (cityName != null) && ((int) latitude != 0) && (int) longitude != 0) {
            Call<CheckErrorResponse> call = RetrofitClient.getInstance().getApi().
                    insertItem(18, category_id, Integer.parseInt(price), ip, experationDate, userAddress, itemTitle,
                            itemDescription, Integer.parseInt(userZip), regionName, cityName, (int) latitude, (int) longitude);
            call.enqueue(new Callback<CheckErrorResponse>() {
                @Override
                public void onResponse(Call<CheckErrorResponse> call, Response<CheckErrorResponse> response) {
                    if (!response.body().isError()) {
                        Toast.makeText(addNewItem.this, "Sucessfully inseted", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CheckErrorResponse> call, Throwable t) {
                    Toast.makeText(addNewItem.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void initilizeIDs() {
        testing = findViewById(R.id.testing);
        spinnerCategories = findViewById(R.id.categories_spinner);
        spinnerRegions = findViewById(R.id.regions_spinner);
        spinnerCities = findViewById(R.id.city_spinner);
        testing = findViewById(R.id.testing);
        get_expiration_date = findViewById(R.id.get_expiration_date);
        publish_button = findViewById(R.id.publish_button);
        item_price = findViewById(R.id.item_price);
        item_title = findViewById(R.id.item_title);
        item_description = findViewById(R.id.item_description);
        user_zip = findViewById(R.id.user_zip);
        user_address = findViewById(R.id.user_address);
    }

    protected void getLocation() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
        }
    }

    private void populateSpinner(int num) {
        List<String> lables = new ArrayList<String>();
        if (num == 1) {
            lables.add(0, "Choose Category");
            for (int i = 0; i < categoriesModelList.size(); i++) {
                lables.add(categoriesModelList.get(i).getCategory_name());
            }
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, lables);
            spinnerAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerCategories.setAdapter(spinnerAdapter);
        } else if (num == 2) {
            lables.add(0, "Choose Region");
            for (int i = 0; i < regionsNameSpinnerModelList.size(); i++) {
                lables.add(regionsNameSpinnerModelList.get(i).getRegion_name());
            }
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, lables);
            spinnerAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerRegions.setAdapter(spinnerAdapter);
        } else if (num == 3) {
            lables.add(0, "Choose City");
            for (int i = 0; i < citySpinnerResponseList.size(); i++) {
                lables.add(citySpinnerResponseList.get(i).getCity_name());
            }
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, lables);
            spinnerAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerCities.setAdapter(spinnerAdapter);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getAdapter() == spinnerRegions.getAdapter() && position > 0) {
            goThere();
        }
    }

    private void goThere() {
        Call<CitySpinnerResponse> callCity =
                RetrofitClient.getInstance().getApi().getCitiesNameSpinner(
                        regionsNameSpinnerModelList.get(
                                spinnerRegions.getSelectedItemPosition()).getRegion_id());
        callCity.enqueue(new Callback<CitySpinnerResponse>() {
            @Override
            public void onResponse(Call<CitySpinnerResponse> call, Response<CitySpinnerResponse> response) {
                citySpinnerResponseList = response.body().getCities();
                populateSpinner(3);
            }

            @Override
            public void onFailure(Call<CitySpinnerResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private String getIP() {
        String ip = null;
        String sURL = "http://www.ip-api.com/json"; //just a string
        URL url = null;
        try {
            url = new URL(sURL);
            URLConnection request = url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject();
            ip = rootobj.get("query").getAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ip != null) {
            return ip;
        } else {
            return "";
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        locationManager.removeUpdates(this);
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    private String stringDateToMySqlDate() throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yy");
        Date inputDate = fmt.parse(mMonth + "-" + mDay + "-" + mYear);
        fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = fmt.format(inputDate);
        return dateString;
    }

}

