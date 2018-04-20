package luongcongdu.blogspot.com.homnayangi.Model;

/**
 * Created by Admin on 4/18/2018.
 */

public class VideoYoutube {
    private String title;
    private String thumbnail;
    private String videoId;

    public VideoYoutube(String title, String thumbnail, String videoId) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
