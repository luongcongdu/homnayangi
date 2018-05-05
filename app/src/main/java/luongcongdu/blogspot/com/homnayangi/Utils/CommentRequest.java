package luongcongdu.blogspot.com.homnayangi.Utils;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 4/20/2018.
 */

public class CommentRequest extends StringRequest {
    private static final String url = Server.postComment;
    private Map<String, String> params;

    public CommentRequest(String user_id, String food_id, String content, String username, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("food_id", food_id);
        params.put("content", content);
        params.put("username", username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
