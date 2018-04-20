package luongcongdu.blogspot.com.homnayangi.View.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import luongcongdu.blogspot.com.homnayangi.Adapter.FoodAdapter;
import luongcongdu.blogspot.com.homnayangi.Model.Food;
import luongcongdu.blogspot.com.homnayangi.R;
import luongcongdu.blogspot.com.homnayangi.Utils.GetFoodForUserRequest;
import luongcongdu.blogspot.com.homnayangi.Utils.Server;

public class ManageRecipeActivity extends AppCompatActivity {

    SharedPreferences preferences;
    String user_id = "";
    ArrayList<Food> listFood;
    FoodAdapter foodAdapter;
    RecyclerView recyclerManageRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_recipe);

        preferences = getSharedPreferences("dangnhap", MODE_PRIVATE);
        user_id = preferences.getString("user_id", "1");
        recyclerManageRecipe = findViewById(R.id.recycler_manage_recipe);
        listFood = new ArrayList<>();
        foodAdapter = new FoodAdapter(this, listFood);
        recyclerManageRecipe.setHasFixedSize(true);
        recyclerManageRecipe.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerManageRecipe.setAdapter(foodAdapter);

        getFood();
    }

    private void getFood() {
        GetFoodForUserRequest getFoodForUserRequest = new GetFoodForUserRequest(user_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.d("JSONARRAY",response);
                    if (jsonArray != null) {
                        int id = 0;
                        String name = "";
                        String image = "";
                        String descrip = "";
                        String material = "";
                        String recipe = "";
                        int userID = 0;
                        String username = "";
                        int time = 0;
                        int idFoodType = 0;

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            name = jsonObject.getString("foodname");
                            image = jsonObject.getString("image");
                            descrip = jsonObject.getString("descrip");
                            material = jsonObject.getString("material");
                            recipe = jsonObject.getString("recipe");
                            time = jsonObject.getInt("time");
                            idFoodType = jsonObject.getInt("idfoodtype");
                            userID = jsonObject.getInt("user_id");
                            username = jsonObject.getString("username");

                            listFood.add(new Food(id, name, image, descrip, material, recipe, time, idFoodType, userID, username));
                            foodAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(getFoodForUserRequest);
    }
}
