package go.faddy.foodfornation.ui.activities;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.List;

import go.faddy.foodfornation.R;
import go.faddy.foodfornation.adapters.CommentFetchAdapter;
import go.faddy.foodfornation.api.RetrofitClient;
import go.faddy.foodfornation.api.respones.CheckErrorResponse;
import go.faddy.foodfornation.api.respones.ItemDetailsResponse;
import go.faddy.foodfornation.models.CommentModel;
import go.faddy.foodfornation.models.ItemDetailsModel;
import go.faddy.foodfornation.utils.storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetailsActivity extends Activity implements View.OnClickListener {
    private static final int REQUEST_CALL = 4;
    private TextView textViewtitle, textViewcategory, textViewlocation, textViewdate,
            textViewprice, textViewdescription, textViewpersonName,
            textViewListing, textViewmemberSince;
    private Button buttonViewProfile, buttonAddComment;
    private ImageButton buttonCall;
    private ImageView item_details_image;
    private ItemDetailsModel item;
    private String mobileNo, commentTitle, commentBody;
    private RecyclerView recyclerView;
    private List<CommentModel> commentModelList;
    private CommentFetchAdapter adapter;
    private Animator currentAnimator;
    private int shortAnimationDuration;
    private String image_url;
    private  int item_id;
    private View thumb1View;
    TextInputEditText item_details_comment_title, item_details_comment_body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        initilizeIDs();

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        thumb1View = findViewById(R.id.item_details_image);
        thumb1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        Intent intent = getIntent();
        item_id = intent.getIntExtra("item", -1);
        image_url = intent.getStringExtra("image_url");

        refreshRecycler();
    }

    private void refreshRecycler() {
        Call<ItemDetailsResponse> call = RetrofitClient.getInstance().getApi().itemDetails(item_id);
        call.enqueue(new Callback<ItemDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<ItemDetailsResponse> call, @NonNull Response<ItemDetailsResponse> response) {
                getTextsandItems(response);

                adapter = new CommentFetchAdapter(getApplicationContext(), commentModelList);
                if (image_url != null) {
                    Picasso.get()
                            .load(image_url)
                            .into(item_details_image);
                }
                buttonCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mobileNo = item.getUsers_mobile_number();
                        if (mobileNo != null) {
                            makePhoneCall(mobileNo);
                        } else {
                            Toast.makeText(ItemDetailsActivity.this, "Sorry, Couldn't place call for this contract", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                buttonViewProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                        intent.putExtra("user_id", item.getUsers_unique_id());
                        intent.putExtra("from_intent", 1);
                        intent.putExtra("add_edit", 0);
                        startActivity(intent);
                    }
                });
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ItemDetailsResponse> call, Throwable t) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshRecycler();
    }


    private void getTextsandItems(Response<ItemDetailsResponse> response) {
        item = response.body().getItems();
        textViewtitle.setText(item.getS_title());
        textViewcategory.setText(item.getS_title_category());
        textViewdescription.setText(item.getS_description());
        textViewlocation.setText(item.getS_city() + ", " + item.getS_region());
        textViewpersonName.setText(item.getUsers_name());
        textViewListing.setText(item.getUsers_items());
        textViewmemberSince.setText(item.getUser_date_registered());
        textViewdate.setText(item.getDt_pub_date());
        textViewprice.setText(String.valueOf(item.getI_price()));
        commentModelList = item.getComment();
    }

    private void initilizeIDs() {
        textViewtitle = findViewById(R.id.item_details_title_lala);
        textViewcategory = findViewById(R.id.item_details_category);
        textViewlocation = findViewById(R.id.item_details_location);
        textViewdate = findViewById(R.id.item_details_date);
        textViewprice = findViewById(R.id.item_details_price);
        textViewdescription = findViewById(R.id.item_details_description);
        textViewpersonName = findViewById(R.id.item_details_person_name);
        textViewListing = findViewById(R.id.item_listing_details);
        textViewmemberSince = findViewById(R.id.item_details_member_since);
        buttonViewProfile = findViewById(R.id.item_details_view_profile);
        buttonCall = findViewById(R.id.item_details_call);
        buttonAddComment = findViewById(R.id.item_details_add_comment);
        item_details_image = findViewById(R.id.item_details_image);
        recyclerView = findViewById(R.id.item_details_comment_recycler);
        item_details_comment_title = findViewById(R.id.item_details_comment_title);
        item_details_comment_body = findViewById(R.id.item_details_comment_body);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            item_details_comment_title.setVisibility(View.VISIBLE);
            item_details_comment_body.setVisibility(View.VISIBLE);
            buttonAddComment.setVisibility(View.VISIBLE);
        }else{
            item_details_comment_title.setVisibility(View.GONE);
            item_details_comment_body.setVisibility(View.GONE);
            buttonAddComment.setVisibility(View.GONE);
        }
        buttonAddComment.setOnClickListener(this);
    }
    private boolean hasEmptyParams() {
        boolean return_val;
        if (TextUtils.isEmpty(item_details_comment_title.getText())) {
            return_val = false;
            item_details_comment_title.setError("Comment title is required to comment");
        } else if (TextUtils.isEmpty(item_details_comment_body.getText())) {
            return_val = false;
            item_details_comment_body.setError("Comment title is required to comment");
        } else {
            return_val = true;
        }
        return return_val;
    }
    private void makePhoneCall(String number) {
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ItemDetailsActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(ItemDetailsActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
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
        switch (v.getId()){
            case R.id.item_details_add_comment:
                addComment();
        }
    }

    private void addComment() {
        if(hasEmptyParams()){
            commentTitle = item_details_comment_title.getText().toString().trim();
            commentBody = item_details_comment_body.getText().toString().trim();
            int user_id = SharedPrefManager.getInstance(this).getUser().getUser_id();
            Call<CheckErrorResponse> call = RetrofitClient.getInstance().getApi().addComment(user_id, item_id,commentTitle, commentBody);
            call.enqueue(new Callback<CheckErrorResponse>() {
                @Override
                public void onResponse(Call<CheckErrorResponse> call, Response<CheckErrorResponse> response) {
                    if(response.body() != null){
                        item_details_comment_body.setText("");
                        item_details_comment_title.setText("");
                        refreshRecycler();
                        Toast.makeText(ItemDetailsActivity.this, "Successfully commented", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CheckErrorResponse> call, Throwable t) {

                }
            });
        }
    }
}


