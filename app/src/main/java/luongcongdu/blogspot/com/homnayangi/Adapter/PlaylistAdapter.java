package luongcongdu.blogspot.com.homnayangi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import luongcongdu.blogspot.com.homnayangi.Model.CallAPIYoutubeSuccess;
import luongcongdu.blogspot.com.homnayangi.Model.MessageListVideo.Item;
import luongcongdu.blogspot.com.homnayangi.Model.ModelPlayListVideo;
import luongcongdu.blogspot.com.homnayangi.Model.VideoInfo;
import luongcongdu.blogspot.com.homnayangi.R;
import luongcongdu.blogspot.com.homnayangi.Utils.CallYoutubeAPI;
import luongcongdu.blogspot.com.homnayangi.View.activity.VideoActivity;
import luongcongdu.blogspot.com.homnayangi.View.fragments.VideosFragment;

/**
 * Created by Admin on 4/12/2018.
 */

public class PlaylistAdapter extends BaseAdapter {

    private Context context;
    public static String videoid;
    CallYoutubeAPI callAPI;
    ArrayList<ModelPlayListVideo> playlistArrayList = new ArrayList<>();
    VideoYoutubeAdapter videoYoutubeAdapter;
    ArrayList<VideoInfo> listvideo;
    public PlaylistAdapter(Context context, ArrayList<ModelPlayListVideo> playlistArrayList) {
        this.context = context;
        this.playlistArrayList = playlistArrayList;
    }

    @Override
    public int getCount() {
        return playlistArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return playlistArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txtvplaylisttitle;
        public ListView lvplaylist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PlaylistAdapter.ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new PlaylistAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_playlist_video,null);
            viewHolder.txtvplaylisttitle = convertView.findViewById(R.id.txtv_listtitle);
            viewHolder.lvplaylist = convertView.findViewById(R.id.lv_video);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (PlaylistAdapter.ViewHolder) convertView.getTag();
        }

        final ModelPlayListVideo modelPlayListVideo = playlistArrayList.get(position);
        viewHolder.txtvplaylisttitle.setText(modelPlayListVideo.getPlaylisttitle());

        videoYoutubeAdapter = new VideoYoutubeAdapter(context, (ArrayList<VideoInfo>) modelPlayListVideo.getListplaylist());
        viewHolder.lvplaylist.setAdapter(videoYoutubeAdapter);
        setListViewHeightBasedOnChildren(viewHolder.lvplaylist);
        viewHolder.lvplaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentvideoview = new Intent(context, VideoActivity.class);
                videoid = String.valueOf(modelPlayListVideo.getListplaylist().get(position).getVideoid());
                String playlistid = modelPlayListVideo.getListplaylist().get(position).getPlaylistid();
                String videotitle = modelPlayListVideo.getListplaylist().get(position).getTitle();
                intentvideoview.putExtra("videoid", videoid);
                intentvideoview.putExtra("playlistid", playlistid);
                intentvideoview.putExtra("videotitle",videotitle);
                context.startActivity(intentvideoview);
//                GetlistVideo(playlistid);
//                ListVideoActivity.txtvvideotitle.setText(videotitle);
//                ListVideoActivity.mYouTubeVideoView.show();

            }
        });
        return convertView;
    }
    private void GetlistVideo(String playlistid) {
        callAPI.Get_ListVideo(context, playlistid, new CallAPIYoutubeSuccess() {
            @Override
            public void OnSuccess(List<luongcongdu.blogspot.com.homnayangi.Model.MessageListVideo.Item> videoinfos) {
                listvideo = new ArrayList<>();
                for (int j = 0; j < videoinfos.size(); j++) {
                    String title, chaneltitle, playlistid, imgvideo, videoid;
                    Log.d("titlevideo", videoinfos.get(j).getSnippet().getTitle());
                    title = videoinfos.get(j).getSnippet().getTitle();
                    chaneltitle = videoinfos.get(j).getSnippet().getChannelTitle();
                    playlistid = videoinfos.get(j).getId();
                    videoid = videoinfos.get(j).getSnippet().getResourceId().getVideoId();
                    imgvideo = videoinfos.get(j).getSnippet().getThumbnails().getMedium().getUrl();
                    listvideo.add(new VideoInfo(title, chaneltitle, playlistid, videoid, imgvideo));
                }
                videoYoutubeAdapter = new VideoYoutubeAdapter(context, listvideo);
                VideosFragment.listViewsub.setAdapter(videoYoutubeAdapter);
            }

        });
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += (listItem.getMeasuredHeight()+16);
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
