package go.faddy.foodfornation.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
import java.util.ArrayList;
import java.util.List;

import go.faddy.foodfornation.R;
import go.faddy.foodfornation.api.RetrofitClient;
import go.faddy.foodfornation.models.CitiesNameSpinnerModel;
import go.faddy.foodfornation.models.RegionsNameSpinnerModel;
import go.faddy.foodfornation.api.respones.CheckErrorResponse;
import go.faddy.foodfornation.api.respones.CitySpinnerResponse;
import go.faddy.foodfornation.api.respones.LoginResponse;
import go.faddy.foodfornation.api.respones.RegionSpinnerResponse;
import go.faddy.foodfornation.api.respones.UserIDResponse;
import go.faddy.foodfornation.utils.storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LocationListener {

    private EditText user_full_name_register, user_name_register, enter_password_register,
            mail_register, user_website_register, user_landline_register, user_mobile_register,
            user_address_inputed_register, user_zip_register, user_self_description;

    private List<RegionsNameSpinnerModel> regionsNameSpinnerModelList;
    private List<CitiesNameSpinnerModel> citySpinnerResponseList;

    private String user_full_name_register_string, user_name_register_string, enter_password_register_string,
            mail_register_string, user_website_register_string, user_landline_register_string,
            user_mobile_register_string, user_address_inputed_register_string, user_zip_register_string,
            user_self_description_string, regionName, cityName;

    private double latitude, longitude;
    private LocationManager locationManager;
    private Spinner spinnerCities, spinnerRegions;
    private Criteria criteria;
    private String bestProvider, ip;
    RadioButton radioButton;
    RadioGroup radioGroup;
    RadioButton yes, no;
    private int isOwner = 0;
    private Button the_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        netOnMainThreadPermission();
        checkLocationPermission();
        initializeViews();
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
        spinnerRegions.setOnItemSelectedListener(this);
        the_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerCities.getSelectedItemPosition() >= 1 && spinnerRegions.getSelectedItemPosition() >= 1) {
                    getTextsFromEditTexts();
                    boolean check = checkEmptyParams();
                    if (check) {
                        beingRegistered();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Select all Required params", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void beingRegistered() {
        if (user_full_name_register_string != null && user_name_register_string != null && enter_password_register_string != null &&
                mail_register_string != null && user_website_register_string != null && Integer.parseInt(user_landline_register_string) != 0 &&
                Integer.parseInt(user_mobile_register_string) != 0 && user_address_inputed_register_string != null &&
                user_zip_register_string != null && isOwner != 0 && regionName != null && cityName != null && ip != null && (int) latitude != 0 &&
                (int) longitude != 0 && user_self_description_string != null) {
            Call<CheckErrorResponse> call = RetrofitClient.getInstance().getApi().registerUser(
                    user_full_name_register_string, user_name_register_string, enter_password_register_string,
                    mail_register_string, user_website_register_string, Integer.parseInt(user_landline_register_string),
                    Integer.parseInt(user_mobile_register_string), user_address_inputed_register_string,
                    user_zip_register_string, isOwner, regionName, cityName, ip, (int) latitude,
                    (int) longitude, user_self_description_string
            );
            call.enqueue(new Callback<CheckErrorResponse>() {
                @Override
                public void onResponse(Call<CheckErrorResponse> call, Response<CheckErrorResponse> response) {
                    if (response.body() != null) {

                        if (!response.body().isError()) {

                            Call<UserIDResponse> calltoId = RetrofitClient.getInstance().getApi().checkId(mail_register_string, enter_password_register_string);
                            calltoId.enqueue(new Callback<UserIDResponse>() {
                                @Override
                                public void onResponse(Call<UserIDResponse> call, Response<UserIDResponse> response2) {
                                    if (response2.body() != null) {
                                        Toast.makeText(RegisterActivity.this, "came", Toast.LENGTH_SHORT).show();
                                        LoginResponse temp = new LoginResponse(response2.body().getUser_id(), response.body().isError());
                                        SharedPrefManager.getInstance(RegisterActivity.this).saveUser(temp);
                                        Intent intent = new Intent(RegisterActivity.this, CategoryDetailsActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onFailure(Call<UserIDResponse> call, Throwable t) {

                                }
                            });

                        }
                    }
                }
                @Override
                public void onFailure(Call<CheckErrorResponse> call, Throwable t) {
                }
            });
        } else {
            Toast.makeText(this, "Someting Seems null", Toast.LENGTH_SHORT).show();
        }

    }

    private void netOnMainThreadPermission() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    private void checkLocationPermission() {
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
    }

    private void getTextsFromEditTexts() {
        user_full_name_register_string = user_full_name_register.getText().toString().trim();
        user_name_register_string = user_name_register.getText().toString().trim();
        enter_password_register_string = enter_password_register.getText().toString().trim();
        mail_register_string = mail_register.getText().toString().trim();
        user_website_register_string = user_website_register.getText().toString().trim();
        user_landline_register_string = user_landline_register.getText().toString().trim();
        user_mobile_register_string = user_mobile_register.getText().toString().trim();
        user_address_inputed_register_string = user_address_inputed_register.getText().toString().trim();
        user_zip_register_string = user_zip_register.getText().toString().trim();
        user_self_description_string = user_self_description.getText().toString().trim();

        cityName = spinnerCities.getSelectedItem().toString();
        regionName = spinnerRegions.getSelectedItem().toString();

        ip = getIP();
        while (latitude == 0) {
            getLocation();
        }
        radioController();
    }

    private boolean checkEmptyParams() {
        boolean return_val;
        if (TextUtils.isEmpty(user_full_name_register.getText())) {
            return_val = false;
            user_full_name_register.setError("Your full name is required");
        } else if (TextUtils.isEmpty(user_name_register.getText())) {
            return_val = false;
            user_name_register.setError("Your UserName is required");
        } else if (TextUtils.isEmpty(enter_password_register.getText())) {
            return_val = false;
            enter_password_register.setError("Your Password is required");
        } else if (TextUtils.isEmpty(mail_register.getText())) {
            return_val = false;
            mail_register.setError("Your email is required");
        } else if (TextUtils.isEmpty(user_mobile_register.getText())) {
            return_val = false;
            user_mobile_register.setError("Your mobile no is required");
        } else if (TextUtils.isEmpty(user_zip_register.getText())) {
            return_val = false;
            user_zip_register.setError("Your ZIP is required");
        } else if (TextUtils.isEmpty(user_address_inputed_register.getText())) {
            return_val = false;
            user_address_inputed_register.setError("Your Address is required");
        } else if (TextUtils.isEmpty(user_self_description.getText())) {
            return_val = false;
            user_self_description.setError("A little description please");
        } else if (spinnerCities.getSelectedItemPosition() < 1 || spinnerRegions.getSelectedItemPosition() < 1) {
            return_val = false;
            Toast.makeText(this, "Select All required Params", Toast.LENGTH_SHORT).show();
        } else {
            return_val = true;
        }
        return return_val;
    }

    private void initializeViews() {
        user_full_name_register = findViewById(R.id.user_full_name_register);
        user_name_register = findViewById(R.id.user_name_register);
        enter_password_register = findViewById(R.id.enter_password_register);
        mail_register = findViewById(R.id.mail_register);
        user_website_register = findViewById(R.id.user_website_register);
        user_landline_register = findViewById(R.id.user_landline_register);
        user_mobile_register = findViewById(R.id.user_mobile_register);
        user_address_inputed_register = findViewById(R.id.user_address_inputed_register);
        user_zip_register = findViewById(R.id.user_zip_register);
        spinnerCities = findViewById(R.id.city_spinner_register);
        spinnerRegions = findViewById(R.id.region_spinner_register);
        radioGroup = findViewById(R.id.radioGroup);
        the_register = findViewById(R.id.the_register);
        user_self_description = findViewById(R.id.user_self_description);
    }

    private void populateSpinner(int num) {
        List<String> lables = new ArrayList<String>();
        if (num == 2) {
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

    private void goThere() {
        Call<CitySpinnerResponse> callCity =
                RetrofitClient.getInstance().getApi().getCitiesNameSpinner(
                        regionsNameSpinnerModelList.get(
                                spinnerRegions.getSelectedItemPosition() - 1).getRegion_id());
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

    private void radioController() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        yes = findViewById(R.id.radioYes);
        no = findViewById(R.id.radioNo);
        if (radioButton == yes) {
            yes.setChecked(true);
            isOwner = 1;
            no.setChecked(false);
        } else {
            yes.setChecked(false);
            no.setChecked(true);
            isOwner = 0;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        locationManager.removeUpdates(this);
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getAdapter() == spinnerRegions.getAdapter() && position > 0) {
            goThere();
        }
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

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

