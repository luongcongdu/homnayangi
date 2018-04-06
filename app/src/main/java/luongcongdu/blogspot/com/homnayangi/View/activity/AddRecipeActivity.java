package luongcongdu.blogspot.com.homnayangi.View.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import luongcongdu.blogspot.com.homnayangi.R;
import luongcongdu.blogspot.com.homnayangi.Utils.AddRecipeRequest;
import luongcongdu.blogspot.com.homnayangi.Utils.Server;

public class AddRecipeActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtName, edtDescrip, edtMaterial, edtRecipe, edtTime;
    Spinner spinner;
    Button btnCancel, btnAccept;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    TextView title;
    ImageButton btnImage;
    SharedPreferences preferences;
    String user_id = "";

    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Uri to store the image uri
    private Uri filePath;

    //Bitmap to get image from gallery
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        requestStoragePermission();
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
        btnImage = findViewById(R.id.btn_image);
        preferences = getSharedPreferences("dangnhap", MODE_PRIVATE);

        user_id = preferences.getString("user_id", "1");

        btnCancel.setOnClickListener(this);
        btnAccept.setOnClickListener(this);
        btnImage.setOnClickListener(this);

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
            case R.id.btn_image:
                showFileChooser();
                break;
        }
    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                btnImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
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
            //old code
//            final Response.Listener<String> listener = new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    try {
//                        JSONObject object = new JSONObject(response);
//                        boolean success = object.getBoolean("success");
//                        if (success == true) {
//                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                            Toast.makeText(getApplicationContext(), "Tải lên thành công !", Toast.LENGTH_SHORT).show();
//                            startActivity(intent);
//                            finish();
//                        } else {
//                            Toast.makeText(AddRecipeActivity.this, "Tải lên thất bại, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                }
//            };
//            AddRecipeRequest addRecipeRequest = new AddRecipeRequest(name, descrip, material, recipe, time, idFoodtype, user_id, listener);
//            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//            queue.add(addRecipeRequest);

            //new code
            //getting the actual path of the image
            String path = getPath(filePath);

            //Uploading code
            try {
                String uploadId = UUID.randomUUID().toString();

                //Creating a multi part request
                new MultipartUploadRequest(this, uploadId, Server.postRecipe)
                        .addFileToUpload(path, "image") //Adding file
                        .addParameter("name", name)
                        .addParameter("descrip", descrip)
                        .addParameter("material", material)
                        .addParameter("recipe", recipe)
                        .addParameter("time", time)
                        .addParameter("id_foodtype", idFoodtype)
                        .addParameter("user_id", user_id)
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload(); //Starting the upload

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            Toast.makeText(getApplicationContext(), "Tải lên thành công !", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();



            } catch (Exception exc) {
                Toast.makeText(this, "Tải lên thất bại, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }


        }

    }

    /*
   * This is the method responsible for image upload
   * We need the full image path and the name for the image in this method
   * */
    public void uploadMultipart() {
        //getting name for the image
       // String name = editText.getText().toString().trim();

        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, Server.UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}
