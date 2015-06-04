package com.albinmathew.samples.designsupportlibrary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.albinmathew.samples.designsupportlibrary.models.Cheeses;
import com.bumptech.glide.Glide;

import java.io.IOException;

/**
 * @author albin
 * @date 3/6/15
 */
public class ContactDetailActivity extends AppCompatActivity {
    public static final String CONTACT_NAME = "contact_name";
    public static final String CONTACT_IMAGE = "contact_image";
    private int mutedColor;
    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);

        Intent intent = getIntent();
        final String cheeseName = intent.getStringExtra(CONTACT_NAME);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(cheeseName);
        loadBackdrop();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        imageView.setImageURI(Uri.parse(CONTACT_IMAGE));
        Glide.with(this).load(getIntent().getStringExtra(CONTACT_IMAGE)).centerCrop().into(imageView);
        Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(getIntent().getStringExtra(CONTACT_IMAGE)));
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                    collapsingToolbar.setContentScrimColor(mutedColor);
                }
            });
        } catch (IOException e) {
            Log.d("IOException",e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }
}
