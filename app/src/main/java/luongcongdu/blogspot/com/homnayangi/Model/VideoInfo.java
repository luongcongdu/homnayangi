package luongcongdu.blogspot.com.homnayangi.Model;

/**
 * Created by Admin on 4/12/2018.
 */

public class VideoInfo {
    String title,chaneltitle,playlistid,videoid,urlpicture;

    public VideoInfo(String title, String chaneltitle, String playlistid, String videoid, String urlpicture) {
        this.title = title;
        this.chaneltitle = chaneltitle;
        this.playlistid = playlistid;
        this.videoid = videoid;
        this.urlpicture = urlpicture;
    }

    public VideoInfo(String title, String chaneltitle, String playlistid, String urlpicture) {
        this.title = title;
        this.chaneltitle = chaneltitle;
        this.playlistid = playlistid;
        this.urlpicture = urlpicture;
    }

    public VideoInfo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChaneltitle() {
        return chaneltitle;
    }

    public void setChaneltitle(String chaneltitle) {
        this.chaneltitle = chaneltitle;
    }

    public String getPlaylistid() {
        return playlistid;
    }

    public void setPlaylistid(String playlistid) {
        this.playlistid = playlistid;
    }

    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid;
    }

    public String getUrlpicture() {
        return urlpicture;
    }

    public void setUrlpicture(String urlpicture) {
        this.urlpicture = urlpicture;
    }
}
