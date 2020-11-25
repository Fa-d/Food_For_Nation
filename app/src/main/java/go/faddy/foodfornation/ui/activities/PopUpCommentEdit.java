package go.faddy.foodfornation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import go.faddy.foodfornation.R;
import go.faddy.foodfornation.api.RetrofitClient;
import go.faddy.foodfornation.api.respones.CheckErrorResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopUpCommentEdit extends AppCompatActivity {
    TextInputEditText titletxt, bodytxt;
    MaterialButton updateButton;
    String title, body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_comment_edit);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -params.height / 2;
        params.height = params.height;
        params.width = params.width / 2;
        params.y = -params.width / 2;
        this.getWindow().setAttributes(params);

        initilizeIDs();

        Intent intent = getIntent();
        int comment_id = intent.getIntExtra("comment_id", -1);
        title = intent.getExtras().getString("title");
        body = intent.getExtras().getString("body");

        if (title != null && body != null && comment_id != -1) {
            titletxt.setText(title);
            bodytxt.setText(body);
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    title = titletxt.getText().toString().trim();
                    body = bodytxt.getText().toString().trim();
                    Call<CheckErrorResponse> call = RetrofitClient.getInstance().getApi().updateComment(body, title, comment_id);
                    call.enqueue(new Callback<CheckErrorResponse>() {
                        @Override
                        public void onResponse(Call<CheckErrorResponse> call, Response<CheckErrorResponse> response) {
                            if (!response.body().isError()) {
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<CheckErrorResponse> call, Throwable t) {

                        }
                    });
                }
            });
        }
    }

    private void initilizeIDs() {
        titletxt = findViewById(R.id.popup_comment_title);
        bodytxt = findViewById(R.id.popup_comment_body);
        updateButton = findViewById(R.id.popup_comment_btn);
    }
}
