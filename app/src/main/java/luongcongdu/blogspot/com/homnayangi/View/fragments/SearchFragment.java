package luongcongdu.blogspot.com.homnayangi.View.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import luongcongdu.blogspot.com.homnayangi.Adapter.FoodAdapter;
import luongcongdu.blogspot.com.homnayangi.Model.Food;
import luongcongdu.blogspot.com.homnayangi.R;
import luongcongdu.blogspot.com.homnayangi.Utils.Server;
import luongcongdu.blogspot.com.homnayangi.Utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    View view;
    ArrayList<Food> listFood;
    FoodAdapter foodAdapter;
    RecyclerView recyclerSearch;
    SearchView searchView;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerSearch = view.findViewById(R.id.recycler_search);
        searchView = view.findViewById(R.id.search_view);

        listFood = new ArrayList<>();
        foodAdapter = new FoodAdapter(getActivity(), listFood, Utils.RECIPE);
        recyclerSearch.setHasFixedSize(true);
        recyclerSearch.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerSearch.setAdapter(foodAdapter);

        getFood();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                final List<Food> filterList = filter(listFood, s);
                foodAdapter.filterList(filterList);
                return true;
            }
        });
        return view;
    }

    public void getFood() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.getallfood, new Response.Listener<JSONArray>() {
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

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private List<Food> filter(List<Food> p1, String query) {
        query = query.toLowerCase();
        List<Food> filterList = new ArrayList<>();
        for (Food model : p1) {
            final String text = model.getName().toLowerCase();
            if (text.startsWith(query)) {
                filterList.add(model);
            }
        }

        return filterList;
    }
}
