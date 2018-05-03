package luongcongdu.blogspot.com.homnayangi.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import luongcongdu.blogspot.com.homnayangi.Model.Food;
import luongcongdu.blogspot.com.homnayangi.R;
import luongcongdu.blogspot.com.homnayangi.Utils.DeleteRecipeRequest;
import luongcongdu.blogspot.com.homnayangi.Utils.Utils;
import luongcongdu.blogspot.com.homnayangi.View.activity.AddRecipeActivity;
import luongcongdu.blogspot.com.homnayangi.View.activity.DetailsFoodActivity;
import luongcongdu.blogspot.com.homnayangi.View.activity.HomeActivity;

/**
 * Created by Admin on 2/25/2018.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    Context context;
    ArrayList<Food> listFood;
    String type = "";

    public FoodAdapter(Context context, ArrayList<Food> listFood, String type) {
        this.context = context;
        this.listFood = listFood;
        this.type = type;
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
        if (type.equals(Utils.MANAGE_RECIPE)) {
            onItemFoodLongClick(holder, position);
        }

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
                Intent intent = new Intent(context, DetailsFoodActivity.class);
                intent.putExtra("DETAIL_FOOD", listFood.get(position));
                context.startActivity(intent);
            }
        });
    }


    public void onItemFoodLongClick(ViewHolder holder, final int position) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final CharSequence colors[] = new CharSequence[]{"Sửa", "Xóa"};

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Tùy chọn");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent(context, AddRecipeActivity.class);
                            intent.putExtra("HANDLE_TYPE", "EDIT");
                            intent.putExtra("ID", listFood.get(position).getId());
                            intent.putExtra("NAME", listFood.get(position).getName());
                            intent.putExtra("DESCRIP", listFood.get(position).getDescrip());
                            intent.putExtra("MATERIAL", listFood.get(position).getMaterial());
                            intent.putExtra("RECIPE", listFood.get(position).getRecipe());
                            intent.putExtra("TIME", String.valueOf(listFood.get(position).getTime()));
                            intent.putExtra("ID_FOODTYPE", String.valueOf(listFood.get(position).getIdFoodType()));
                            intent.putExtra("USER_ID", String.valueOf(listFood.get(position).getUser_id()));
                            intent.putExtra("USERNAME", listFood.get(position).getUsername());
                            context.startActivity(intent);

                        } else if (which == 1) {
                            //delete
                            //show confirm dialog
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Bạn có chắc chắn muốn xóa?")
                                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            DeleteRecipeRequest deleteRecipeRequest = new DeleteRecipeRequest(String.valueOf(listFood.get(position).getId()),
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            Toast.makeText(context, "Đã xóa!", Toast.LENGTH_SHORT).show();
                                                            listFood.remove(position);
                                                            notifyDataSetChanged();
                                                        }
                                                    });
                                            RequestQueue queue = Volley.newRequestQueue(context);
                                            queue.add(deleteRecipeRequest);
                                        }
                                    })
                                    .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    })
                                    .show();
                        }
                    }
                });
                builder.show();
                return true;
            }
        });
    }


    public void filterList(List<Food> foodList) {
        listFood = new ArrayList<>();
        listFood.addAll(foodList);
        notifyDataSetChanged();
    }
}
