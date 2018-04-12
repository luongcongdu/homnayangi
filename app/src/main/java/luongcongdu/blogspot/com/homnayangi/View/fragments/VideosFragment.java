package luongcongdu.blogspot.com.homnayangi.View.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.ArrayList;
import java.util.List;

import luongcongdu.blogspot.com.homnayangi.Adapter.PlaylistAdapter;
import luongcongdu.blogspot.com.homnayangi.Model.CallAPIYoutubeSuccess;
import luongcongdu.blogspot.com.homnayangi.Model.MessageYoutube.Item;
import luongcongdu.blogspot.com.homnayangi.Model.ModelPlayListVideo;
import luongcongdu.blogspot.com.homnayangi.Model.VideoInfo;
import luongcongdu.blogspot.com.homnayangi.Model.YoutubeVideoView;
import luongcongdu.blogspot.com.homnayangi.R;
import luongcongdu.blogspot.com.homnayangi.Utils.CallYoutubeAPI;
import luongcongdu.blogspot.com.homnayangi.Utils.SharedPrefsUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideosFragment extends Fragment  implements YoutubeVideoView.Callback, YouTubePlayer.PlaybackEventListener{
    View view;
    ArrayList<ModelPlayListVideo> playlistlist;
    ArrayList<VideoInfo> infoArrayList;
    PlaylistAdapter playlistAdapter;
    ListView listplayid;
    public static ListView listViewsub;
    public static YoutubeVideoView mYouTubeVideoView;
    public static TextView txtvvideotitle;
    CallYoutubeAPI callAPI;

    public VideosFragment() {
        // Required empty public constructor
    }

    public static VideosFragment newInstance() {

        VideosFragment fragment = new VideosFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_videos, container, false);

        init();
        GetJsonYoutube();
        YouTubePlayerFragment youtubeFragment = (YouTubePlayerFragment) getActivity().getFragmentManager().findFragmentById(R.id.youtubeFragment);
        youtubeFragment.initialize("YOUR API KEY",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo(PlaylistAdapter.videoid);
                        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
        mYouTubeVideoView = view.findViewById(R.id.youtube_view);
        mYouTubeVideoView.setCallback(this);



        return view;
    }

    private void GetJsonYoutube() {
        playlistlist = new ArrayList<>();
        callAPI.Get_Video(getActivity());
        int i;
        //Toast.makeText(this, SharedPrefsUtils.getStringPreference(getApplicationContext(),"listplaylistidsize"), Toast.LENGTH_SHORT).show();
        for (i = 0; i < Integer.parseInt(SharedPrefsUtils.getStringPreference(getActivity(), "listplaylistidsize")); i++) {
            final int finalI = i;
            callAPI.Get_ListVideo(getActivity(), SharedPrefsUtils.getStringPreference(getActivity(), "playlistid" + i), new CallAPIYoutubeSuccess() {
                @Override
                public void OnSuccess(List<luongcongdu.blogspot.com.homnayangi.Model.MessageListVideo.Item> videoinfos) {
                    infoArrayList = new ArrayList<>();
                    for (int j = 0; j < videoinfos.size(); j++) {
                        String title, chaneltitle, playlistid, imgvideo, videoid;
                        Log.d("titlevideo", videoinfos.get(j).getSnippet().getTitle());
                        title = videoinfos.get(j).getSnippet().getTitle();
                        chaneltitle = videoinfos.get(j).getSnippet().getChannelTitle();
                        playlistid = videoinfos.get(j).getSnippet().getPlaylistId();
                        videoid = videoinfos.get(j).getSnippet().getResourceId().getVideoId();
                        imgvideo = videoinfos.get(j).getSnippet().getThumbnails().getMedium().getUrl();
                        infoArrayList.add(new VideoInfo(title, chaneltitle, playlistid, videoid, imgvideo));
                        Log.d("kkkk", String.valueOf(infoArrayList.size()));
                    }
                    playlistlist.add(new ModelPlayListVideo(SharedPrefsUtils.getStringPreference(getActivity(), "playlisttitle" + finalI), infoArrayList));
                    playlistAdapter = new PlaylistAdapter(getActivity(), playlistlist);
                    listplayid.setAdapter(playlistAdapter);
                }
            });
        }
        setListViewHeightBasedOnChildren(listplayid);
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
            totalHeight += (listItem.getMeasuredHeight() + 17);
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    private void init() {
        listplayid =  view.findViewById(R.id.lv_playlist);
        listViewsub =  view.findViewById(R.id.lv_videosub1);
        mYouTubeVideoView = view.findViewById(R.id.youtube_view);
        txtvvideotitle =  view.findViewById(R.id.txtv_videotitle1);
    }

    @Override
    public void onPlaying() {
        mYouTubeVideoView.show();
    }

    @Override
    public void onPaused() {
        mYouTubeVideoView.show();
    }

    @Override
    public void onStopped() {
        mYouTubeVideoView.show();
    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }

    @Override
    public void onVideoViewHide() {

    }

    @Override
    public void onVideoClick() {

    }
}
