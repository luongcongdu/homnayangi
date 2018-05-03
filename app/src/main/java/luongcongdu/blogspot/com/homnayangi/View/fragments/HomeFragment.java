package luongcongdu.blogspot.com.homnayangi.View.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

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

import luongcongdu.blogspot.com.homnayangi.Adapter.FoodAdapter;
import luongcongdu.blogspot.com.homnayangi.Model.Food;
import luongcongdu.blogspot.com.homnayangi.R;
import luongcongdu.blogspot.com.homnayangi.Utils.Server;
import luongcongdu.blogspot.com.homnayangi.Utils.Utils;
import luongcongdu.blogspot.com.homnayangi.View.activity.DetailsFoodActivity;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    View view;
    ViewFlipper viewFlipper;
    ArrayList<Food> listFood;
    FoodAdapter foodAdapter;
    RecyclerView recyclerHome;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        viewFlipper = view.findViewById(R.id.flipper_home);
        recyclerHome = view.findViewById(R.id.recycler_home);

//        viewFlipper.setFlipInterval(5000);
//        viewFlipper.setAutoStart(true);
//        viewFlipper.startFlipping();//
//
//        Animation slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
//        Animation slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
//        viewFlipper.setInAnimation(slide_in);
//        viewFlipper.setOutAnimation(slide_out);

        listFood = new ArrayList<>();
        foodAdapter = new FoodAdapter(getActivity(), listFood, Utils.RECIPE);
        recyclerHome.setHasFixedSize(true);
        recyclerHome.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerHome.setAdapter(foodAdapter);

        getFood();


        return view;
    }

    public void getFood() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.getfood, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("RESPONSE", String.valueOf(response));
                if (response != null) {
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

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
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

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    setViewFlipper(listFood);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    public void setViewFlipper(final ArrayList<Food> listFood) {
        viewFlipper.removeAllViews();
        for (int i = 0; i < 5; i++) {

            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.item_view_flipper, null);


            ImageView imageView = view.findViewById(R.id.img_view_flipper);
            Picasso.with(getActivity()).load(listFood.get(i).getImage()).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            TextView txtFoodName = view.findViewById(R.id.txt_food_name);
            txtFoodName.setText(listFood.get(i).getName());
            TextView txtFoodTime = view.findViewById(R.id.txt_food_time);
            txtFoodTime.setText(String.valueOf(listFood.get(i).getTime()) + " phút");
            viewFlipper.addView(view);
        }

        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        viewFlipper.startFlipping();//

        Animation slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);

        viewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int focus = viewFlipper.getDisplayedChild();
                Intent intent = new Intent(getActivity(), DetailsFoodActivity.class);
                intent.putExtra("DETAIL_FOOD", listFood.get(focus));
                startActivity(intent);
            }
        });

    }

}
