package com.quochung.covid20;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class docbaiviet extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private AppBarLayout appBarLayout;
    private TextView appbar_subtitle;
    private TextView appbar_title;
    private TextView date;
    private FrameLayout date_behavior;
    private ImageView imageView;
    private boolean isHideToolbarView = false;
    private String mAuthor;
    private String mDate;
    private String mImg;
    private String mSource;
    private String mTitle;
    private String mUrl;
    private TextView time;
    private TextView title;
    private LinearLayout titleAppbar;
    private Toolbar toolbar;

    public void onCreate(Bundle bundle) {
        String str;
        super.onCreate(bundle);
        setContentView(R.layout.activity_docbaiviet);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");
        appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(this);
        date_behavior = findViewById(R.id.date_behavior);
        titleAppbar = findViewById(R.id.title_appbar);
        imageView = findViewById(R.id.backdrop);
        appbar_title = findViewById(R.id.title_on_appbar);
        appbar_subtitle = findViewById(R.id.subtitle_on_appbar);
        date =  findViewById(R.id.date);
        time = findViewById(R.id.timedocbai);
        title = findViewById(R.id.title);
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        mImg = intent.getStringExtra("img");
        mTitle = intent.getStringExtra("title");
        mDate = intent.getStringExtra("date");
        mSource = intent.getStringExtra("source");
        mAuthor = intent.getStringExtra("author");
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(Utils.getRandomDrawbleColor());
        Glide.with(this).load(this.mImg).apply(requestOptions).transition(DrawableTransitionOptions.withCrossFade()).into(this.imageView);
        appbar_title.setText(mSource);
        appbar_subtitle.setText(mUrl);
        date.setText(Utils.DateFormat(mDate));
        title.setText(mTitle);
        if (mAuthor != null) {
            str = " • " + mAuthor;
        } else {
            str = "";
        }
        time.setText(this.mSource + str + " • " + Utils.DateToTimeFormat(this.mDate));
        initWebView(mUrl);
    }

    private void initWebView(String str) {
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(str);
    }

    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onOffsetChanged(AppBarLayout appBarLayout2, int i) {
        float abs = ((float) Math.abs(i)) / ((float) appBarLayout2.getTotalScrollRange());
        if (abs == 1.0f && isHideToolbarView) {
            date_behavior.setVisibility(View.GONE);
            titleAppbar.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;
        } else if (abs < 1.0f && !isHideToolbarView) {
            date_behavior.setVisibility(View.VISIBLE);
            titleAppbar.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.view_web) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(this.mUrl));
            startActivity(intent);
            return true;
        }
        if (itemId == R.id.share) {
            try {
                Intent intent2 = new Intent("android.intent.action.SEND");
                intent2.setType("text/plan");
                intent2.putExtra("android.intent.extra.SUBJECT", this.mSource);
                intent2.putExtra("android.intent.extra.TEXT", this.mTitle + "\n" + this.mUrl + "\nShare from the News App\n");
                startActivity(Intent.createChooser(intent2, "Share with :"));
            } catch (Exception unused) {
                Toast.makeText(this, "Hmm.. Sorry, \nCannot be share", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
