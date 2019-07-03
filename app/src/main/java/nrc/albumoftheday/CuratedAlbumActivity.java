package nrc.albumoftheday;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CuratedAlbumActivity extends AppCompatActivity
{
    private String token, artistName, albumName, albumURL, imageURL, redirectURL;
    private Bundle extras;
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curated_album);
        imageView = (ImageView) findViewById(R.id.curatedAlbumCover);
        textView = (TextView) findViewById(R.id.curatedAlbumSelection);
        extras = getIntent().getExtras();
        token = extras.getString("AUTHENTICATION");
        artistName = extras.getString("CURATED_ARTIST_NAME");
        albumName = extras.getString("CURATED_ALBUM_NAME");
        albumURL = extras.getString("CURATED_ALBUM_URL");
        imageURL = extras.getString("CURATED_IMAGE_URL");

        //Sets image and text for album
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(textView,5,20, 1, TypedValue.COMPLEX_UNIT_DIP);
        textView.setText(albumName + " by " + artistName);
        Picasso.get().load(imageURL).into(imageView);

    }

    public void directToAlbumPage(View view)
    {
        redirectURL = extras.getString("REDIRECT_URL");
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(redirectURL));
        startActivity(browserIntent);
    }

    public void directToMenuActivity(View view)
    {
        Intent intent = new Intent(this, MenuActivity.class);
        extras.putString("AUTHENTICATION", token);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
