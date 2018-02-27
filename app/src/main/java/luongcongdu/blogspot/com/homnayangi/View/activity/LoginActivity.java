package luongcongdu.blogspot.com.homnayangi.View.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import luongcongdu.blogspot.com.homnayangi.R;
import luongcongdu.blogspot.com.homnayangi.Utils.LoginRequest;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtUsername, edtPassword;
    Button btnLogin;
    TextView linkRegister;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    public void initView() {
        edtUsername = findViewById(R.id.input_username);
        edtPassword = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.btn_login);
        linkRegister = findViewById(R.id.link_register);

        btnLogin.setOnClickListener(this);
        linkRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.link_register:
                Intent iRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(iRegister);
                break;
            case R.id.btn_login:
                handleLogin();
                break;
        }
    }

    public void handleLogin() {
        final String username = edtUsername.getText().toString();
        final String password = edtPassword.getText().toString();
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    boolean success = object.getBoolean("success");
                    Log.d("SUCCESS", String.valueOf(success));
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("Đang đăng nhập");
                    progressDialog.setMessage("Loading...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    if (success) {
                        //dang nhap thanh cong!
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        SharedPreferences preferences = getSharedPreferences("dangnhap", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        String username = object.getString("username");
                        String email = object.getString("email");
                        editor.putString("c", username);
                        editor.putString("b", "Đăng xuất");
                        editor.putString("d", email);
                        editor.commit();
                        startActivity(intent);
                        finish();
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng !", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LoginRequest request = new LoginRequest(username, password, listener);
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(request);
    }
}
