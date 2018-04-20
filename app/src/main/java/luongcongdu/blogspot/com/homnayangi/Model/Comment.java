package luongcongdu.blogspot.com.homnayangi.Model;

/**
 * Created by Admin on 4/20/2018.
 */

public class Comment {
    private String userName;
    private String content;

    public Comment(String userName, String content) {
        this.userName = userName;
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
