package luongcongdu.blogspot.com.homnayangi.Utils;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 2/27/2018.
 */

public class RegisterRequest extends StringRequest {

    private static final String url = Server.register;
    private Map<String, String> params;

    public RegisterRequest(String email, String username, String password, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("username", username);
        params.put("password", password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
