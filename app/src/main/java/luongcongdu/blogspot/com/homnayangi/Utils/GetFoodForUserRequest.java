package luongcongdu.blogspot.com.homnayangi.Utils;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 4/20/2018.
 */

public class GetFoodForUserRequest extends StringRequest{
    private static final String url = Server.getFoodForUser;
    private Map<String, String> params;

    public GetFoodForUserRequest(String user_id, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("user_id", user_id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
