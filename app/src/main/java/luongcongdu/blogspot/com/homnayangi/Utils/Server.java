package luongcongdu.blogspot.com.homnayangi.Utils;

/**
 * Created by Admin on 2/12/2018.
 */

public class Server {
    //    public static String localhost = "172.16.200.39";
    public static String localhost = "192.168.1.103";
    //    public static String localhost = "192.168.43.99";
    public static String http = "http://";
    public static String getuser = http + localhost + "/server/getuser.php";
    public static String getfood = http + localhost + "/server/getfood.php";
    public static String getfoodtype = http + localhost + "/server/getfoodtype.php";
    public static String getarticle = http + localhost + "/server/getarticle.php";
    public static String login = http + localhost + "/server/login.php";
    public static String register = http + localhost + "/server/register.php";
    public static String postRecipe = http + localhost + "/server/post_recipe.php";
}
