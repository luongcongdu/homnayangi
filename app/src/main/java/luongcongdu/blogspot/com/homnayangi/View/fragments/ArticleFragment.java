package luongcongdu.blogspot.com.homnayangi.View.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import luongcongdu.blogspot.com.homnayangi.Adapter.ArticleAdapter;
import luongcongdu.blogspot.com.homnayangi.Adapter.FoodAdapter;
import luongcongdu.blogspot.com.homnayangi.Model.Article;
import luongcongdu.blogspot.com.homnayangi.Model.Food;
import luongcongdu.blogspot.com.homnayangi.R;
import luongcongdu.blogspot.com.homnayangi.Utils.Server;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment {
    View view;
    ArrayList<Article> listArticle;
    ArticleAdapter articleAdapter;
    RecyclerView recyclerArticle;


    public ArticleFragment() {
        // Required empty public constructor
    }

    public static ArticleFragment newInstance() {
        ArticleFragment fragment = new ArticleFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_article, container, false);

        recyclerArticle = view.findViewById(R.id.recycler_article);
        listArticle = new ArrayList<>();
        articleAdapter = new ArticleAdapter(getActivity(), listArticle);
        recyclerArticle.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerArticle.setLayoutManager(layoutManager);
        recyclerArticle.setAdapter(articleAdapter);

        getArticle();

        return view;
    }

    public void getArticle() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.getarticle, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("RESPONSE", String.valueOf(response));
                if (response != null) {
                    int id = 0;
                    String name = "";
                    String image = "";
                    String descrip = "";
                    String link = "";

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            name = jsonObject.getString("name");
                            image = jsonObject.getString("image");
                            descrip = jsonObject.getString("descrip");
                            link = jsonObject.getString("link");
                            listArticle.add(new Article(id, name, descrip, image, link));
                            articleAdapter.notifyDataSetChanged();

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

}
