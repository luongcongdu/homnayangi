package luongcongdu.blogspot.com.homnayangi.View.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import luongcongdu.blogspot.com.homnayangi.Adapter.VideoYoutubeAdapter;
import luongcongdu.blogspot.com.homnayangi.Model.VideoYoutube;
import luongcongdu.blogspot.com.homnayangi.R;
import luongcongdu.blogspot.com.homnayangi.View.activity.PlayVideoActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideosFragment extends Fragment {
    View view;
    String URL_API_YOUTUBE = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLxcNdLGGW7F-GqTUZTDd2em5jA26H4pAp&key=AIzaSyDLlL4NkmR5Fb0-LJiB792iH8I_mo7mKeo&maxResults=50";
    ListView lvVideo;
    ArrayList<VideoYoutube> arrayVideo;
    VideoYoutubeAdapter adapter;

    public VideosFragment() {
        // Required empty public constructor
    }

    public static VideosFragment newInstance() {

        VideosFragment fragment = new VideosFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_videos, container, false);

        lvVideo = view.findViewById(R.id.lv_video);
        arrayVideo = new ArrayList<>();
        adapter = new VideoYoutubeAdapter(getActivity(), R.layout.row_video_youtube, arrayVideo);
        lvVideo.setAdapter(adapter);

        GetJsonYoutube(URL_API_YOUTUBE);
        lvVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), PlayVideoActivity.class);
                intent.putExtra("ID_VIDEO",arrayVideo.get(i).getVideoId());
                startActivity(intent);
            }
        });
        return view;
    }

    private void GetJsonYoutube(final String url) {
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("items");
                            String title="";
                            String urlImage="";
                            String videoID="";

                            for (int i=0; i <jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);

                                JSONObject jsonSpippet = object.getJSONObject("snippet");
                                title = jsonSpippet.getString("title");
                                Log.d("TITLE",title);
                                JSONObject jsonThumbnail  = jsonSpippet.getJSONObject("thumbnails");
                                JSONObject jsonMedium = jsonThumbnail.getJSONObject("medium");
                                urlImage = jsonMedium.getString("url");
                                Log.d("URLIMAGE",urlImage);
                                JSONObject jsonResource = jsonSpippet.getJSONObject("resourceId");
                                videoID = jsonResource.getString("videoId");
                                Log.d("VIDEOID",videoID);

                                arrayVideo.add(new VideoYoutube(title, urlImage, videoID));
                            }

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        }
        );

        requestQueue.add(jsonObjectRequest);
    }

}
