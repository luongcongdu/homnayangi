package luongcongdu.blogspot.com.homnayangi.Model.ModelAPI;

import luongcongdu.blogspot.com.homnayangi.Model.MessageYoutube.Example;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Admin on 4/12/2018.
 */

public interface APIServices {
    @GET("/youtube/v3/search?key=AIzaSyCSyE2hOEVqFrOgLgyurzw70BmcoiypUz0&channelId=UCBhgBmuPFbLLxnejr09lnAQ&part=snippet,id&order=date&maxResults=50")
    Call<Example> getMyJSON();

    @GET("/youtube/v3/playlistItems?part=snippet&key=AIzaSyCSyE2hOEVqFrOgLgyurzw70BmcoiypUz0&maxResults=20")
    Call<luongcongdu.blogspot.com.homnayangi.Model.MessageListVideo.Example> getMyJSON1(@Query("playlistId") String id);
}
