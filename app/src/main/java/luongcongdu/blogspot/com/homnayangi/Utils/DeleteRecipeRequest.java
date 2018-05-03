package luongcongdu.blogspot.com.homnayangi.Utils;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 5/3/2018.
 */

public class DeleteRecipeRequest extends StringRequest {
    private static final String url = Server.deleteRecipe;
    private Map<String, String> params;

    public DeleteRecipeRequest(String id, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("Id", id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
