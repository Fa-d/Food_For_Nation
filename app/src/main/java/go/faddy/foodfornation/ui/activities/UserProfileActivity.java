package go.faddy.foodfornation.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

import go.faddy.foodfornation.R;
import go.faddy.foodfornation.adapters.UsersProfileItemFetchAdapter;
import go.faddy.foodfornation.api.RetrofitClient;
import go.faddy.foodfornation.api.respones.UserProfileResponse;
import go.faddy.foodfornation.api.respones.UsersProfileMiddleResponse;
import go.faddy.foodfornation.models.UsersProfilePostItemsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CALL = 4;
    private TextView username, location, regionalLocation;
    private Button user_call_button;
    private RecyclerView recyclerView;
    private ImageButton add_item_user, edit_user_item;
    private UsersProfileMiddleResponse usersposts;
    private UsersProfileItemFetchAdapter adapter;
    private List<UsersProfilePostItemsModel> usersProfilePostItemsModelList;
    private String mobileNo = null;
    private int x, add_edit, from_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initializeIDs();

        Intent intent = getIntent();
        x = intent.getIntExtra("user_id", -1);
        from_intent = intent.getIntExtra("from_intent", -1);
        add_edit = intent.getIntExtra("add_edit", -1);

        settingVisibilityOfButtons();

        user_call_button.setOnClickListener(this);
        add_item_user.setOnClickListener(this);
        edit_user_item.setOnClickListener(this);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Call<UserProfileResponse> call = RetrofitClient.getInstance().getApi().profileDetails(x == -1 ? 18 : x);
        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                usersposts = response.body().getItems();
                mobileNo = usersposts.getUser_mobile_no();
                username.setText(usersposts.getUser_name());
                location.setText(usersposts.getUser_city() + " , " + usersposts.getUser_region());
                usersProfilePostItemsModelList = usersposts.getUsers_post_items();
                adapter = new UsersProfileItemFetchAdapter(getApplicationContext(), usersProfilePostItemsModelList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {

            }
        });
    }

    private void settingVisibilityOfButtons() {
        if (add_edit == -1) {
            edit_user_item.setVisibility(View.VISIBLE);
            add_item_user.setVisibility(View.VISIBLE);
        } else {
            edit_user_item.setVisibility(View.GONE);
            add_item_user.setVisibility(View.GONE);
        }
        if (from_intent == -1) {
            user_call_button.setVisibility(View.GONE);
        } else {
            user_call_button.setVisibility(View.VISIBLE);
        }
    }

    private void initializeIDs() {
        username = findViewById(R.id.user_username);
        location = findViewById(R.id.user_location);
        regionalLocation = findViewById(R.id.user_regional_location);
        user_call_button = findViewById(R.id.user_from_profile_call_btn);
        recyclerView = findViewById(R.id.recycler_user_profile);
        add_item_user = findViewById(R.id.add_item_user);
        edit_user_item = findViewById(R.id.edit_user_item);
    }

    private void makePhoneCall(String number) {
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(UserProfileActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(getApplicationContext(), "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(mobileNo);
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_user_item:
                Intent intent1 = new Intent(this, RegisterActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("from_user", 1);
                startActivity(intent1);
                break;
            case R.id.add_item_user:
                Intent intent2 = new Intent(getApplicationContext(), addNewItem.class);
                intent2.putExtra("user_id", x);
                startActivity(intent2);
                break;
            case R.id.user_from_profile_call_btn:
                if (mobileNo != null) {
                    makePhoneCall(mobileNo);
                }
                break;
        }
    }
}