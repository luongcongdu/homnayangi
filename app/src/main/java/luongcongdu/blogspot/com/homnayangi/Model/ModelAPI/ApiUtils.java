package luongcongdu.blogspot.com.homnayangi.Model.ModelAPI;

/**
 * Created by Admin on 4/12/2018.
 */

public class ApiUtils {
    private ApiUtils() {}
    public static APIServices getApiService() {
        return RetrofitClient.getRetrofitInstance().create(APIServices.class);
    }
}
