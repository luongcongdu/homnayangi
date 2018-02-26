package luongcongdu.blogspot.com.homnayangi.View.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import luongcongdu.blogspot.com.homnayangi.Model.Food;
import luongcongdu.blogspot.com.homnayangi.R;

public class DetailsFoodActivity extends AppCompatActivity {

    TextView txtDescrip, txtMaterial, txtRecipe, txtTime;
    ImageView imgIcon;
    Toolbar toolbar;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_food);

        initView();
        getDetailFood();


    }

    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        title = toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.button_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        txtDescrip = findViewById(R.id.txt_descrip);
        txtMaterial = findViewById(R.id.txt_material);
        txtRecipe = findViewById(R.id.txt_recipe);
        txtTime = findViewById(R.id.txt_time);
        imgIcon = findViewById(R.id.img_icon);

    }


    public void getDetailFood() {
        int id = 0;
        String name = "";
        String image = "";
        String descrip = "";
        String material = "";
        String recipe = "";
        int time = 0;
        int idFoodType = 0;

        Food food = (Food) getIntent().getSerializableExtra("DETAIL_FOOD");
        id = food.getId();
        name = food.getName();
        descrip = food.getDescrip();
        material = food.getMaterial();
        recipe = food.getRecipe();
        image = food.getImage();
        time = food.getTime();
        idFoodType = food.getIdFoodType();

        title.setText(name);
        txtDescrip.setText(descrip);
        txtMaterial.setText(material);
        txtRecipe.setText(recipe);
        txtTime.setText(time + " phút");
        Picasso.with(this).load(food.getImage())
                .placeholder(R.drawable.icon_loading)
                .error(R.drawable.icon_error)
                .into(imgIcon);

    }

}
