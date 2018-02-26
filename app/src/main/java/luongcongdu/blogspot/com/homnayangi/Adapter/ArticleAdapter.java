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

import luongcongdu.blogspot.com.homnayangi.Model.Article;
import luongcongdu.blogspot.com.homnayangi.Model.Food;
import luongcongdu.blogspot.com.homnayangi.R;
import luongcongdu.blogspot.com.homnayangi.View.activity.DetailArticleActivity;
import luongcongdu.blogspot.com.homnayangi.View.activity.DetailsFoodActivity;

/**
 * Created by Admin on 2/26/2018.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    Context context;
    ArrayList<Article> listArticle;

    public ArticleAdapter(Context context, ArrayList<Article> listArticle) {
        this.context = context;
        this.listArticle = listArticle;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, null);
        ArticleAdapter.ViewHolder viewHolder = new ArticleAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = listArticle.get(position);
        holder.txtName.setText(article.getName());
        holder.txtDescrip.setText(article.getDescrip());
        Picasso.with(context).load(article.getImage())
                .placeholder(R.drawable.icon_loading)
                .error(R.drawable.icon_error)
                .into(holder.imgIcon);

        onItemArticleClick(holder, position);
    }

    @Override
    public int getItemCount() {
        return listArticle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        public TextView txtDescrip;
        public ImageView imgIcon;


        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_name_article);
            imgIcon = itemView.findViewById(R.id.img_article);
            txtDescrip = itemView.findViewById(R.id.txt_descrip_article);
        }
    }

    public void onItemArticleClick(ArticleAdapter.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailArticleActivity.class);
                intent.putExtra("DETAIL_ARTICLE", listArticle.get(position));
                context.startActivity(intent);
            }
        });
    }

}
