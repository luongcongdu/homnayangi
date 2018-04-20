package luongcongdu.blogspot.com.homnayangi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import luongcongdu.blogspot.com.homnayangi.Model.Food;
import luongcongdu.blogspot.com.homnayangi.R;
import luongcongdu.blogspot.com.homnayangi.View.activity.DetailsFoodActivity;

/**
 * Created by Admin on 2/25/2018.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    Context context;
    ArrayList<Food> listFood;

    public FoodAdapter(Context context, ArrayList<Food> listFood) {
        this.context = context;
        this.listFood = listFood;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Food food = listFood.get(position);
        holder.txtName.setText(food.getName());
        Picasso.with(context).load(food.getImage())
                .placeholder(R.drawable.icon_loading)
                .error(R.drawable.icon_error)
                .into(holder.imgIcon);

        onItemFoodClick(holder, position);

    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        public ImageView imgIcon;


        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_foodname);
            imgIcon = itemView.findViewById(R.id.img_food);
        }
    }

    public void onItemFoodClick(ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, listFood.get(position).getName().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailsFoodActivity.class);
                intent.putExtra("DETAIL_FOOD", listFood.get(position));
                context.startActivity(intent);
            }
        });
    }

    public void filterList(List<Food> foodList) {
        listFood = new ArrayList<>();
        listFood.addAll(foodList);
        notifyDataSetChanged();
    }
}
