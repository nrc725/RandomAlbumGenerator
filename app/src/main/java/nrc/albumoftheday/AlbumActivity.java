package nrc.albumoftheday;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class AlbumActivity extends AppCompatActivity
{
    ImageView imageView;
    TextView albumSelection;
    Button button;
    Bundle extras;
    String token, albumURL, albumRedirectURL, artistName, albumName;
    boolean isPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        extras = getIntent().getExtras();
        albumSelection = (TextView) findViewById(R.id.albumSelection);
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button1);

        //gets items from bundle
        token = extras.getString("AUTHENTICATION");
        albumURL = extras.getString("ALBUM_URL");
        albumRedirectURL = extras.getString("ALBUM_REDIRECT_URL");
        artistName = extras.getString("ARTIST_NAME");
        albumName = extras.getString("ALBUM_NAME");
        isPlaylist = extras.getBoolean("IS_PLAYLIST");

        //Sets image and text for album
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(albumSelection,5,20, 1, TypedValue.COMPLEX_UNIT_DIP);
        albumSelection.setText(albumName + " by " + artistName);
        Picasso.get().load(albumURL).into(imageView);
    }

    public void directToAlbumPage(View view)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(albumRedirectURL));
        startActivity(browserIntent);
    }

    public void directToMenuPage(View view)
    {
        if(isPlaylist)
        {
            Intent intent = new Intent(this, PlaylistActivity.class);
            extras.putString("AUTHENTICATION", token);
            intent.putExtras(extras);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, GenreActivity.class);
            extras.putString("AUTHENTICATION", token);
            intent.putExtras(extras);
            startActivity(intent);
        }
    }

}
