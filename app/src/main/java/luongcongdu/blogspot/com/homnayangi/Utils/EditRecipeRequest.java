package luongcongdu.blogspot.com.homnayangi.Utils;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 3/2/2018.
 */

public class EditRecipeRequest extends StringRequest {
    private static final String url = Server.editRecipe;
    private Map<String, String> params;

    public EditRecipeRequest(String id, String name, String descrip, String material, String recipe, String time, String id_foodtype, String user_id, String username,
                             Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("Id", id);
        params.put("name", name);
        params.put("image", "default");
        params.put("descrip", descrip);
        params.put("material", material);
        params.put("recipe", recipe);
        params.put("time", time);
        params.put("id_foodtype", id_foodtype);
        params.put("user_id", user_id);
        params.put("username", username);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
