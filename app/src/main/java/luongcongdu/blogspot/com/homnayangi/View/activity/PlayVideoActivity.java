package luongcongdu.blogspot.com.homnayangi.View.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import luongcongdu.blogspot.com.homnayangi.R;

public class PlayVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerView youTubePlayerView;
    String idVideo = "";
    int REQUEST_VIDEO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        youTubePlayerView = findViewById(R.id.layout_youtube);

        Intent intent = getIntent();
        idVideo = intent.getStringExtra("ID_VIDEO");
        Log.d("ID_VIDEO", idVideo);

        youTubePlayerView.initialize(getResources().getString(R.string.YOUTUBE_API_KEY), this);


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(idVideo);
        youTubePlayer.setFullscreen(true);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(PlayVideoActivity.this, REQUEST_VIDEO);
        } else {
            Toast.makeText(this, "Có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIDEO) {
            youTubePlayerView.initialize(getResources().getString(R.string.YOUTUBE_API_KEY), this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
