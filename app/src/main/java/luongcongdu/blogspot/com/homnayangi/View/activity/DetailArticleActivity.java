package luongcongdu.blogspot.com.homnayangi.View.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import luongcongdu.blogspot.com.homnayangi.Model.Article;
import luongcongdu.blogspot.com.homnayangi.R;

public class DetailArticleActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView title;
    WebView webView;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);

        initView();
        getDetailArticle();

    }

    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        title = toolbar.findViewById(R.id.toolbar_title);
        webView = findViewById(R.id.wv_detail_article);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.button_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void getDetailArticle() {
        String link = "";
        Article article = (Article) getIntent().getSerializableExtra("DETAIL_ARTICLE");
        link = article.getLink();
        Log.d("LINK",link);
        title.setText(article.getName());

        if (article.getLink() != null) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Đang tải...");
            dialog.setCancelable(false);
            dialog.show();

            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(onWebViewLoaded);
            webView.loadUrl(link);
        }
    }

    private WebViewClient onWebViewLoaded = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            dialog.dismiss();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            dialog.dismiss();
            Toast.makeText(DetailArticleActivity.this, "Sorry! Some error was detected!", Toast.LENGTH_SHORT).show();
        }


    };
}
