package go.faddy.foodfornation.activities;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import go.faddy.foodfornation.R;
import go.faddy.foodfornation.adapters.CommentFetchAdapter;
import go.faddy.foodfornation.api.RetrofitClient;
import go.faddy.foodfornation.models.CommentModel;
import go.faddy.foodfornation.models.ItemDetailsModel;
import go.faddy.foodfornation.respones.ItemDetailsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetailsActivity extends AppCompatActivity {
    private static final int REQUEST_CALL = 4;
    private TextView textViewtitle, textViewcategory, textViewlocation, textViewdate,
            textViewprice, textViewdescription, textViewpersonName,
            textViewListing, textViewmemberSince;
    private Button buttonViewProfile, buttonAddComment;
    private ImageButton buttonCall;
    private ImageView item_details_image;
    private ItemDetailsModel item;
    private String mobileNo;
    private RecyclerView recyclerView;
    private List<CommentModel> commentModelList;
    private CommentFetchAdapter adapter;
    private Animator currentAnimator;
    private int shortAnimationDuration;
    private String image_url;
    private View thumb1View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
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
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        thumb1View = findViewById(R.id.item_details_image);
        thumb1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        Intent intent = getIntent();
        int x = intent.getIntExtra("item", -1);
        image_url = intent.getStringExtra("image_url");
        Call<ItemDetailsResponse> call = RetrofitClient.getInstance().getApi().itemDetails(x);
        call.enqueue(new Callback<ItemDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<ItemDetailsResponse> call, @NonNull Response<ItemDetailsResponse> response) {
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
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<ItemDetailsResponse> call, Throwable t) {
            }
        });
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


    private void zoomImageFromThumb(final View thumbView, Bitmap imageResId) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (currentAnimator != null) {
            currentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) findViewById(
                R.id.expanded_image);
//        expandedImageView.setImageResource(imageResId);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.image_enlarge_container)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView,
                        View.SCALE_Y, startScale, 1f));
        set.setDuration(shortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                currentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                currentAnimator = null;
            }
        });
        set.start();
        currentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentAnimator != null) {
                    currentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(shortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }
                });
                set.start();
                currentAnimator = set;
            }
        });
    }
    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            zoomImageFromThumb(thumb1View, bitmap
            );
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
//            zoomImageFromThumb(thumb1View, placeHolderDrawable
//            );
        }
    };

    private void someMethod() {
        Picasso.get().load(image_url).into(target);
    }

    @Override
    public void onDestroy() {
        Picasso.get().cancelRequest(target);
        super.onDestroy();
    }
}


