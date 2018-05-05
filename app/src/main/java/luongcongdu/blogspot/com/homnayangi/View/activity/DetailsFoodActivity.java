package luongcongdu.blogspot.com.homnayangi.View.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import luongcongdu.blogspot.com.homnayangi.Adapter.CommentAdapter;
import luongcongdu.blogspot.com.homnayangi.Model.Comment;
import luongcongdu.blogspot.com.homnayangi.Model.Food;
import luongcongdu.blogspot.com.homnayangi.R;
import luongcongdu.blogspot.com.homnayangi.Utils.CommentRequest;
import luongcongdu.blogspot.com.homnayangi.Utils.Server;

public class DetailsFoodActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtDescrip, txtMaterial, txtRecipe, txtTime, txtAuthor;
    ImageView imgIcon;
    Toolbar toolbar;
    TextView title;
    ListView lvComment;
    EditText edtComment;
    ImageView imgSendComment;
    ArrayList<Comment> listComment;
    CommentAdapter adapter;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_food);

        initView();
        getDetailFood();


    }

    public void initView() {
        preferences = getSharedPreferences("dangnhap", MODE_PRIVATE);
        toolbar = findViewById(R.id.toolbar);
        title = toolbar.findViewById(R.id.toolbar_title);

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

        txtDescrip = findViewById(R.id.txt_descrip);
        txtMaterial = findViewById(R.id.txt_material);
        txtRecipe = findViewById(R.id.txt_recipe);
        txtAuthor = findViewById(R.id.txt_author);
        txtTime = findViewById(R.id.txt_time);
        imgIcon = findViewById(R.id.img_icon);
        edtComment = findViewById(R.id.edt_comment);
        imgSendComment = findViewById(R.id.img_send_comment);
        lvComment = findViewById(R.id.lv_comment);

        imgSendComment.setOnClickListener(this);

        listComment = new ArrayList<>();
        adapter = new CommentAdapter(DetailsFoodActivity.this, R.layout.row_comment, listComment);
        lvComment.setAdapter(adapter);

        getComment();


    }


    public void getDetailFood() {
        int id = 0;
        String name = "";
        String image = "";
        String descrip = "";
        String material = "";
        String recipe = "";
        String username = "";
        int time = 0;
        int idFoodType = 0;
        int userID = 0;

        Food food = (Food) getIntent().getSerializableExtra("DETAIL_FOOD");
        id = food.getId();
        name = food.getName();
        descrip = food.getDescrip();
        material = food.getMaterial();
        recipe = food.getRecipe();
        image = food.getImage();
        time = food.getTime();
        idFoodType = food.getIdFoodType();
        userID = food.getUser_id();
        username = food.getUsername();

        title.setText(name);
        txtDescrip.setText(descrip);
        txtMaterial.setText(Html.fromHtml(material));
        txtRecipe.setText(Html.fromHtml(recipe));
        txtTime.setText(time + " phút");
        txtAuthor.setText(username);
        Picasso.with(this).load(food.getImage())
                .placeholder(R.drawable.icon_loading)
                .error(R.drawable.icon_error)
                .into(imgIcon);
    }

    private void handleComment() {
        String stateLogin = preferences.getString("b", "");
        Log.d("STATE", stateLogin);

        if (stateLogin.equals("Đăng xuất")) {
            Food food = (Food) getIntent().getSerializableExtra("DETAIL_FOOD");
            String userID = preferences.getString("user_id", "1");
            String userName = preferences.getString("c", "Username");
            String foodID = String.valueOf(food.getId());
            String content = edtComment.getText().toString().trim();

            if (content.equals("")) {
                Toast.makeText(this, "Bạn hãy nhập nội dung!", Toast.LENGTH_SHORT).show();
            } else {
                CommentRequest request = new CommentRequest(userID, foodID, content, userName, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPONSE COMMENT", response);
                        if (response.equals("false")) {
                            Toast.makeText(DetailsFoodActivity.this, "Lỗi hệ thống, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(request);

                //handle listview
                listComment.add(new Comment(userName, content));
                adapter.notifyDataSetChanged();
                edtComment.setText("");

            }
        } else {
            Toast.makeText(this, "Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();
        }

    }

    private void getComment() {
        Food food = (Food) getIntent().getSerializableExtra("DETAIL_FOOD");
        final String idFood = String.valueOf(food.getId());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.getComment, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    String idComment = "";
                    String userID = "";
                    String foodID = "";
                    String content = "";
                    //String userName = preferences.getString("c", "Username");
                    String userName = "";

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idComment = jsonObject.getString("id_comment");
                            userID = jsonObject.getString("user_id");
                            foodID = jsonObject.getString("food_id");
                            content = jsonObject.getString("content");
                            userName = jsonObject.getString("username");

                            if (foodID.equals(idFood)) {
                                listComment.add(new Comment(userName, content));
                                adapter.notifyDataSetChanged();
                            }

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_send_comment:
                handleComment();
                break;
        }
    }
}
