package luongcongdu.blogspot.com.homnayangi.Utils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 3/2/2018.
 */

public class AddRecipeRequest extends StringRequest {
    private static final String url = Server.postRecipe;
    private Map<String, String> params;

    public AddRecipeRequest(String name, String descrip, String material, String recipe, String time, String id_foodtype,
                            Response.Listener<String> listener) {
        super(Request.Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("image", "default");
        params.put("descrip", descrip);
        params.put("material", material);
        params.put("recipe", recipe);
        params.put("time", time);
        params.put("id_foodtype", id_foodtype);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
