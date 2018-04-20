package luongcongdu.blogspot.com.homnayangi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import luongcongdu.blogspot.com.homnayangi.Model.VideoYoutube;
import luongcongdu.blogspot.com.homnayangi.R;

/**
 * Created by Admin on 4/18/2018.
 */

public class VideoYoutubeAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<VideoYoutube> videoYoutubeList;

    public VideoYoutubeAdapter(Context context, int layout, List<VideoYoutube> videoYoutubeList) {
        this.context = context;
        this.layout = layout;
        this.videoYoutubeList = videoYoutubeList;
    }

    @Override
    public int getCount() {
        return videoYoutubeList.size();
    }

    @Override
    public Object getItem(int i) {
        return videoYoutubeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        ImageView imgThumbnail;
        TextView txtTitle;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.txtTitle = view.findViewById(R.id.txtTitleVideo);
            viewHolder.imgThumbnail = view.findViewById(R.id.imgThumbnailVideo);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        VideoYoutube videoYoutube = videoYoutubeList.get(i);
        viewHolder.txtTitle.setText(videoYoutube.getTitle());
        Picasso.with(context).load(videoYoutube.getThumbnail())
                .placeholder(R.drawable.icon_loading)
                .error(R.drawable.icon_error)
                .into(viewHolder.imgThumbnail);


        return view;
    }
}
