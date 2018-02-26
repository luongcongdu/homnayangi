package luongcongdu.blogspot.com.homnayangi.Model;

import java.io.Serializable;

/**
 * Created by Admin on 2/25/2018.
 */

public class Food implements Serializable {
    public int id;
    public String name;
    public String image;
    public String descrip;
    public String material;
    public String recipe;
    public int time;
    public int idFoodType;

    public Food(int id, String name, String image, String descrip, String material, String recipe, int time, int idFoodType) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.descrip = descrip;
        this.material = material;
        this.recipe = recipe;
        this.time = time;
        this.idFoodType = idFoodType;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getIdFoodType() {
        return idFoodType;
    }

    public void setIdFoodType(int idFoodType) {
        this.idFoodType = idFoodType;
    }
}
