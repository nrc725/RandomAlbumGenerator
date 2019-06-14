package nrc.albumoftheday;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AlbumActivity extends AppCompatActivity
{
    ImageView imageView;
    Button button;
    Bundle extras;
    String token, albumURI,albumURL, albumRedirectURL;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        extras = getIntent().getExtras();
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button1);
        token = extras.getString("AUTHENTICATION");
        albumURI = extras.getString("ALBUM_URI");
        getAlbumInfo(albumURI);
    }

    public void getAlbumInfo(String albumURI)
    {
        final String URL ="https://api.spotify.com/v1/albums/" + albumURI + "?market=US";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, URL,null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        try {
                            //takes jsonobject and unwraps it to get to the artist uri
                            JSONArray jarray = response.getJSONArray("images");
                            System.out.println(jarray.toString());
                            JSONObject object = jarray.getJSONObject(0);
                            JSONObject object2 = response.getJSONObject("external_urls");
                            //JSONArray jarray2 = object2.getJSONArray("artists");
                            //JSONObject object3 = jarray2.getJSONObject(0);
                            albumRedirectURL = object2.getString("spotify");
                            albumURL = object.getString("url");

                            Log.d("Album URL", albumURL);
                            System.out.println(albumRedirectURL);
                            Picasso.get().load(albumURL).into(imageView);
                            //Log.d("Artist URI", artistURI);
                           // selectArtistAlbum(artistURI);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);

                return params;
            }
        };
        queue.add(getRequest);
    }

    public void directToAlbumPage(View view)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(albumRedirectURL));
        startActivity(browserIntent);
    }

    public void directToMenuPage(View view)
    {
        Intent intent = new Intent(this, MenuActivity.class);
        extras.putString("AUTHENTICATION", token);
        intent.putExtras(extras);
        startActivity(intent);
    }


}
