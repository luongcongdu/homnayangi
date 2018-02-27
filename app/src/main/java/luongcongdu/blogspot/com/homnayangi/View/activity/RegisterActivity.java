package luongcongdu.blogspot.com.homnayangi.View.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import luongcongdu.blogspot.com.homnayangi.R;
import luongcongdu.blogspot.com.homnayangi.Utils.RegisterRequest;
import luongcongdu.blogspot.com.homnayangi.Utils.Server;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtUsername, edtEmail, edtPass1, edtPass2;
    Button btnRegister;
    TextView linkLogin;
    ArrayList<String> listUsername = new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        getUsername();


    }

    public void initView() {
        edtUsername = findViewById(R.id.input_username);
        edtEmail = findViewById(R.id.input_email);
        edtPass1 = findViewById(R.id.input_password1);
        edtPass2 = findViewById(R.id.input_password2);
        btnRegister = findViewById(R.id.btn_register);
        linkLogin = findViewById(R.id.link_login);

        btnRegister.setOnClickListener(this);
        linkLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                handleRegister();
                break;
            case R.id.link_login:
                Intent iLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(iLogin);
                break;
        }

    }

    public void getUsername() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.getuser, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            listUsername.add(jsonObject.getString("username"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void handleRegister() {
        int k = 0;
        String username = edtUsername.getText().toString();
        String pass1 = edtPass1.getText().toString();
        String pass2 = edtPass2.getText().toString();
        String email = edtEmail.getText().toString();

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Đang đăng ký");
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        if (username.equals("") || email.equals("") || pass1.equals("") || pass2.equals("")) {
            progressDialog.dismiss();
            Toast.makeText(RegisterActivity.this, "Bạn hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < listUsername.size(); i++) {
                if (username.equals(listUsername.get(i).toString())) {
                    k++;
                }
            }
            if (k == 0) {
                if (pass1.equals(pass2)) {
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            Toast.makeText(getApplicationContext(), "Đăng ký thành công !", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        }
                    };
                    RegisterRequest request = new RegisterRequest(email, username, pass1, listener);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(request);

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Nhập lại mật khẩu sai !", Toast.LENGTH_SHORT).show();
                }
            } else {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Trùng tài khoản đã đăng ký !", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
