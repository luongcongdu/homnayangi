package luongcongdu.blogspot.com.homnayangi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

import luongcongdu.blogspot.com.homnayangi.Model.Comment;
import luongcongdu.blogspot.com.homnayangi.R;

/**
 * Created by Admin on 4/20/2018.
 */

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Comment> commentList;

    public CommentAdapter(Context context, int layout, List<Comment> commentList) {
        this.context = context;
        this.layout = layout;
        this.commentList = commentList;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int i) {
        return commentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView userName;
        TextView content;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        CommentAdapter.ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new CommentAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.userName = view.findViewById(R.id.txt_username);
            viewHolder.content = view.findViewById(R.id.txt_content);
            view.setTag(viewHolder);
        } else {
            viewHolder = (CommentAdapter.ViewHolder) view.getTag();
        }

        Comment comment = commentList.get(i);
        viewHolder.userName.setText(comment.getUserName());
        viewHolder.content.setText(comment.getContent());

        return view;
    }
}
