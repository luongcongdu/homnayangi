package luongcongdu.blogspot.com.homnayangi.View.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import luongcongdu.blogspot.com.homnayangi.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtUsername, txtEmail, txtLogin;
    LinearLayout linearLogin;
    ImageView imgLogin;

    public static int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initView();
    }

    public void initView() {
        SharedPreferences preferences = getSharedPreferences("dangnhap", MODE_PRIVATE);
        txtUsername = findViewById(R.id.txt_username);
        txtEmail = findViewById(R.id.txt_email);
        linearLogin = findViewById(R.id.linear_login);
        txtLogin = findViewById(R.id.txt_login);
        imgLogin = findViewById(R.id.icon_login);
        linearLogin.setOnClickListener(this);

        txtUsername.setText(preferences.getString("c", "Username"));
        txtEmail.setText(preferences.getString("d", "Email"));

        String stateLogin = preferences.getString("b", "");
        Log.d("STATE", stateLogin);
        if (stateLogin.equals("Đăng xuất")) {
            txtLogin.setText(stateLogin);
            imgLogin.setImageResource(R.drawable.icon_logout);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_login:
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == REQUEST_CODE && data != null) {
//            String message = data.getStringExtra("SEND_BACK");
//            if (message.equals("Đăng xuất")) {
//                txtLogin.setText("Đăng xuất");
//                imgLogin.setImageResource(R.drawable.icon_logout);
//            } else {
//                txtLogin.setText("Đăng nhập");
//                imgLogin.setImageResource(R.drawable.icon_login);
//            }
//        }

    }
}
