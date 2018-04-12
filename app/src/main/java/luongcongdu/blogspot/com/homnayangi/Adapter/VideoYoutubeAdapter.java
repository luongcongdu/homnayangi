package luongcongdu.blogspot.com.homnayangi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import luongcongdu.blogspot.com.homnayangi.Model.VideoInfo;
import luongcongdu.blogspot.com.homnayangi.R;

/**
 * Created by Admin on 4/12/2018.
 */

public class VideoYoutubeAdapter extends BaseAdapter {
    private Context context;
    ArrayList<VideoInfo> videoInfoArrayList = new ArrayList<>();

    public VideoYoutubeAdapter(Context context, ArrayList<VideoInfo> videoInfoArrayList) {
        this.context = context;
        this.videoInfoArrayList = videoInfoArrayList;
    }

    @Override
    public int getCount() {
        return videoInfoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return videoInfoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txtvtitle,txtchaneltitle;
        public ImageView imgvideo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_list_video,null);
            viewHolder.txtvtitle = convertView.findViewById(R.id.txtv_title);
            viewHolder.txtchaneltitle = convertView.findViewById(R.id.txtv_chaneltitle);
            viewHolder.imgvideo = convertView.findViewById(R.id.img_video);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        VideoInfo videoInfo = (VideoInfo) getItem(position);
        viewHolder.txtvtitle.setText(videoInfo.getTitle());
        viewHolder.txtchaneltitle.setText(videoInfo.getChaneltitle());
        Picasso.with(context).load(videoInfo.getUrlpicture())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(viewHolder.imgvideo);

        return convertView;
    }
}
