package luongcongdu.blogspot.com.homnayangi.Model;

import java.io.Serializable;

/**
 * Created by Admin on 2/26/2018.
 */

public class Article implements Serializable{
    public int id;
    public String name;
    public String descrip;
    public String image;
    public String link;

    public Article(int id, String name, String descrip, String image, String link) {
        this.id = id;
        this.name = name;
        this.descrip = descrip;
        this.image = image;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
