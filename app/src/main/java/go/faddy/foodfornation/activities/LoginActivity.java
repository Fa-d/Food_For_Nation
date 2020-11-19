package go.faddy.foodfornation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import go.faddy.foodfornation.R;
import go.faddy.foodfornation.api.RetrofitClient;
import go.faddy.foodfornation.respones.LoginResponse;
import go.faddy.foodfornation.storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText login_email, login_password;
    String email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasEmptyParams()) {
                    forNextActivity();
                }
            }
        });
        findViewById(R.id.to_registration_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    private void forNextActivity() {
        email = login_email.getText().toString().trim();
        pass = login_password.getText().toString().trim();
        if (email != null || pass != null) {
            Call<LoginResponse> call = RetrofitClient.getInstance().getApi().checkPassword(email, pass);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.body() != null) {
                        if (!response.body().isError()) {
                            SharedPrefManager.getInstance(LoginActivity.this).saveUser(response.body());
                            Intent intent = new Intent(LoginActivity.this, CategoryDetailsActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Passwords do not match, Try again", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Wrong email or password!! Please check and try again.", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean hasEmptyParams() {
        boolean return_val;
        if (TextUtils.isEmpty(login_email.getText())) {
            return_val = false;
            login_email.setError("Your email is required");
        } else if (TextUtils.isEmpty(login_password.getText())) {
            return_val = false;
            login_password.setError("Your password is required");
        } else {
            return_val = true;
        }
        return return_val;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, CategoryDetailsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}

