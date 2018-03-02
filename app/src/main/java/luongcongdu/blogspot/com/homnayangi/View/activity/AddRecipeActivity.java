package luongcongdu.blogspot.com.homnayangi.View.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import luongcongdu.blogspot.com.homnayangi.R;
import luongcongdu.blogspot.com.homnayangi.Utils.AddRecipeRequest;

public class AddRecipeActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtName, edtDescrip, edtMaterial, edtRecipe, edtTime;
    Spinner spinner;
    Button btnCancel, btnAccept;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        initView();
        initSpinner();
    }

    public void initView() {
        edtName = findViewById(R.id.edt_name);
        edtDescrip = findViewById(R.id.edt_descrip);
        edtMaterial = findViewById(R.id.edt_material);
        edtRecipe = findViewById(R.id.edt_recipe);
        edtTime = findViewById(R.id.edt_time);
        btnAccept = findViewById(R.id.btn_accept);
        btnCancel = findViewById(R.id.btn_cancel);

        btnCancel.setOnClickListener(this);
        btnAccept.setOnClickListener(this);

        toolbar = findViewById(R.id.toolbar);
        title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Thêm công thức");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.button_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initSpinner() {
        spinner = findViewById(R.id.spinner_foodtype);
        String arr[] = {
                "Ăn sáng",
                "Món chay",
                "Món chính",
                "Bánh",
                "Thức uống",
                "Nước chấm",
                "Bún - Mì - Phở"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddRecipeActivity.this,
                android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_accept:
                handleAdd();
                break;
            case R.id.btn_cancel:
                onBackPressed();
                break;
        }
    }

    public void handleAdd() {

        String name = edtName.getText().toString();
        String descrip = edtDescrip.getText().toString();
        String material = edtMaterial.getText().toString();
        String recipe = edtRecipe.getText().toString();
        String time = edtTime.getText().toString();
        String idFoodtype = "";

        String temp = spinner.getSelectedItem().toString();
        if (temp.equals("Ăn sáng")) {
            idFoodtype = "1";
        } else if (temp.equals("Món chay")) {
            idFoodtype = "2";
        } else if (temp.equals("Món chính")) {
            idFoodtype = "3";
        } else if (temp.equals("Bánh")) {
            idFoodtype = "4";
        } else if (temp.equals("Thức uống")) {
            idFoodtype = "5";
        } else if (temp.equals("Nước chấm")) {
            idFoodtype = "6";
        } else if (temp.equals("Bún - Mì - Phở")) {
            idFoodtype = "7";
        }

        progressDialog = new ProgressDialog(AddRecipeActivity.this);
        progressDialog.setTitle("Đang tải lên");
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (name.equals("") || descrip.equals("") || material.equals("") || recipe.equals("")
                || time.equals("")) {
            progressDialog.dismiss();
            Toast.makeText(AddRecipeActivity.this, "Bạn hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            final Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        boolean success = object.getBoolean("success");
                        if (success == true) {
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            Toast.makeText(getApplicationContext(), "Tải lên thành công !", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(AddRecipeActivity.this, "Tải lên thất bại, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            };
            AddRecipeRequest addRecipeRequest = new AddRecipeRequest(name, descrip, material, recipe, time, idFoodtype, listener);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(addRecipeRequest);
        }

    }
}
