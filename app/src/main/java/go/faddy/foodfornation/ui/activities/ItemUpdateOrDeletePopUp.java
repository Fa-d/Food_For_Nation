package go.faddy.foodfornation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import go.faddy.foodfornation.R;

public class ItemUpdateOrDeletePopUp extends AppCompatActivity {
    private LinearLayout edit_item_from_user_list, delete_item_from_user_list;
    int x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_item_update_or_delete_pop_up);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -params.height / 2;
        params.width = params.width / 2;
        params.y = -params.width / 2;
        this.getWindow().setAttributes(params);

        delete_item_from_user_list = findViewById(R.id.delete_item_from_user_list);
        edit_item_from_user_list = findViewById(R.id.edit_item_from_user_list);
        Intent intent = getIntent();
        x = intent.getIntExtra("item_id", -1);

        delete_item_from_user_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ItemUpdateOrDeletePopUp.this, "Something", Toast.LENGTH_SHORT).show();
            }
        });

        edit_item_from_user_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), addNewItem.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("update_item", 1);
                startActivity(intent1);
                Toast.makeText(ItemUpdateOrDeletePopUp.this, String.valueOf(x), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }


}






























