package luongcongdu.blogspot.com.homnayangi.View.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import luongcongdu.blogspot.com.homnayangi.Adapter.HomeAdapter;
import luongcongdu.blogspot.com.homnayangi.R;
import luongcongdu.blogspot.com.homnayangi.View.fragments.ArticleFragment;
import luongcongdu.blogspot.com.homnayangi.View.fragments.HomeFragment;
import luongcongdu.blogspot.com.homnayangi.View.fragments.SearchFragment;
import luongcongdu.blogspot.com.homnayangi.View.fragments.VideosFragment;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;
    TextView txtHome, txtArticle, txtSearch, txtVideos;
    ImageView imgHome, imgArticle, imgSearch, imgVideos;
    TextView txtToolbar;
    LinearLayout linearHome, linearArticle, linearSearch, linearVideo, linearAdd;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initFragments();
    }

    public void initView() {
        viewPager = findViewById(R.id.viewpager_home);
        imgHome = findViewById(R.id.img_home_nav);
        txtHome = findViewById(R.id.txt_home_nav);
        imgHome.setImageResource(R.drawable.icon_home_focus);
        txtHome.setTextColor(getResources().getColor(R.color.text_focus));
        txtToolbar = findViewById(R.id.toolbar_title);
        txtArticle = findViewById(R.id.txt_article_nav);
        txtSearch = findViewById(R.id.txt_search_nav);
        txtVideos = findViewById(R.id.txt_video_nav);
        imgArticle = findViewById(R.id.img_article_nav);
        imgSearch = findViewById(R.id.img_search_nav);
        imgVideos = findViewById(R.id.img_video_nav);
        linearHome = findViewById(R.id.linear_home);
        linearArticle = findViewById(R.id.linear_article);
        linearAdd = findViewById(R.id.linear_add);
        linearSearch = findViewById(R.id.linear_search);
        linearVideo = findViewById(R.id.linear_videos);
        linearHome.setOnClickListener(this);
        linearArticle.setOnClickListener(this);
        linearSearch.setOnClickListener(this);
        linearAdd.setOnClickListener(this);
        linearVideo.setOnClickListener(this);

        preferences = getSharedPreferences("dangnhap", MODE_PRIVATE);


        //setup toolbar
        final Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.icon_user_with_padding));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iProfile = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(iProfile);
            }
        });

        setupBottomNav();
    }

    public void initFragments() {
        HomeFragment homeFragment = HomeFragment.newInstance();
        ArticleFragment articleFragment = ArticleFragment.newInstance();
        SearchFragment searchFragment = SearchFragment.newInstance();
        VideosFragment videosFragment = VideosFragment.newInstance();

        ArrayList<Fragment> listFragment = new ArrayList<>();
        listFragment.add(homeFragment);
        listFragment.add(articleFragment);
        listFragment.add(searchFragment);
        listFragment.add(videosFragment);

        HomeAdapter adapter = new HomeAdapter(getSupportFragmentManager(), listFragment);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setCurrentItem(0);
    }

    private void setupBottomNav() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //set title toolbar
                switch (position) {
                    case 0:
                        txtToolbar.setText("Trang chủ");
                        onHomeNavClick();
                        break;
                    case 1:

                        txtToolbar.setText("Bài viết");
                        onArticleNavClick();
                        break;
                    case 2:
                        txtToolbar.setText("Tìm kiếm");
                        onSearchNavClick();
                        break;
                    case 3:

                        txtToolbar.setText("Videos");
                        onVideoNavClick();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void onHomeNavClick() {
        imgHome.setImageResource(R.drawable.icon_home_focus);
        imgArticle.setImageResource(R.drawable.icon_article_unactive);
        imgSearch.setImageResource(R.drawable.icon_search_unactive);
        imgVideos.setImageResource(R.drawable.icon_video_unactive);

        txtHome.setTextColor(getResources().getColor(R.color.text_focus));
        txtArticle.setTextColor(getResources().getColor(R.color.text_default));
        txtSearch.setTextColor(getResources().getColor(R.color.text_default));
        txtVideos.setTextColor(getResources().getColor(R.color.text_default));
    }

    private void onArticleNavClick() {
        imgHome.setImageResource(R.drawable.icon_home_unactive);
        imgArticle.setImageResource(R.drawable.icon_article_focus);
        imgSearch.setImageResource(R.drawable.icon_search_unactive);
        imgVideos.setImageResource(R.drawable.icon_video_unactive);

        txtHome.setTextColor(getResources().getColor(R.color.text_default));
        txtArticle.setTextColor(getResources().getColor(R.color.text_focus));
        txtSearch.setTextColor(getResources().getColor(R.color.text_default));
        txtVideos.setTextColor(getResources().getColor(R.color.text_default));
    }

    private void onSearchNavClick() {
        imgHome.setImageResource(R.drawable.icon_home_unactive);
        imgArticle.setImageResource(R.drawable.icon_article_unactive);
        imgSearch.setImageResource(R.drawable.icon_search_focus);
        imgVideos.setImageResource(R.drawable.icon_video_unactive);

        txtHome.setTextColor(getResources().getColor(R.color.text_default));
        txtArticle.setTextColor(getResources().getColor(R.color.text_default));
        txtSearch.setTextColor(getResources().getColor(R.color.text_focus));
        txtVideos.setTextColor(getResources().getColor(R.color.text_default));
    }

    private void onVideoNavClick() {
        imgHome.setImageResource(R.drawable.icon_home_unactive);
        imgArticle.setImageResource(R.drawable.icon_article_unactive);
        imgSearch.setImageResource(R.drawable.icon_search_unactive);
        imgVideos.setImageResource(R.drawable.icon_video_focus);

        txtHome.setTextColor(getResources().getColor(R.color.text_default));
        txtArticle.setTextColor(getResources().getColor(R.color.text_default));
        txtSearch.setTextColor(getResources().getColor(R.color.text_default));
        txtVideos.setTextColor(getResources().getColor(R.color.text_focus));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_home:
                viewPager.setCurrentItem(0);
                onHomeNavClick();
                break;
            case R.id.linear_article:
                viewPager.setCurrentItem(1);
                onArticleNavClick();
                break;
            case R.id.linear_search:
                viewPager.setCurrentItem(2);
                onSearchNavClick();
                break;
            case R.id.linear_videos:
                viewPager.setCurrentItem(3);
                onVideoNavClick();
                break;
            case R.id.linear_add:
                String stateLogin = preferences.getString("b", "");
                Log.d("STATE", stateLogin);
                if (stateLogin.equals("Đăng xuất")) {
                    Intent intent = new Intent(HomeActivity.this, AddRecipeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();
                    break;
                }
                break;
        }
    }
}
