package nrc.albumoftheday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity
{
    private String token, artistName, albumName, albumURL, imageURL, redirectURL;
    private Bundle extras;
    String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        extras = getIntent().getExtras();
        token = extras.getString("AUTHENTICATION");
        imageView = (ImageView) findViewById(R.id.curatedAlbumImage);

        if((artistName = extras.getString("ARTIST_NAME")) == null) {
            final String URL = "http://192.168.1.152/getAlbum.php";
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                //takes jsonobject and unwraps it to get to the artist uri and logs it
                                Log.d("Response", response.toString());
                                JSONObject object = response.getJSONObject(0);
                                artistName = object.getString("Artist_Name");
                                albumName = object.getString("Album_Name");
                                albumURL = object.getString("Album_URL");
                                imageURL = object.getString("Image_URL");
                                redirectURL = object.getString("Redirect_URL");
                                Picasso.get().load(imageURL).into(imageView);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Log.d("ERROR", "error => " + error.toString());
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Bearer " + token);
                    return params;
                }
            };
            queue.add(getRequest);
        }
    }

    public void directToCuratedAlbumPage(View view)
    {
        Intent intent = new Intent(this, CuratedAlbumActivity.class);
        extras.putString("AUTHENTICATION", token);
        extras.putString("CURATED_ARTIST_NAME", artistName);
        extras.putString("CURATED_ALBUM_NAME", albumName);
        extras.putString("CURATED_ALBUM_URL", albumURL);
        extras.putString("CURATED_IMAGE_URL", imageURL);
        extras.putString("CURATED_REDIRECT_URL", redirectURL);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void onGenreButtonPress(View view)
    {
        Intent intent = new Intent(this, GenreActivity.class);
        extras.putString("AUTHENTICATION", token);
        intent.putExtras(extras);
        startActivity(intent);
    }

    //Gets spotify username so we can get the user's public playlists
    public void onPlaylistButtonPress(View view)
    {
        final String URL = "https://api.spotify.com/v1/me";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, URL,null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Tries to get the playlist uri from the jsonobject
                        try {
                            String username = response.getString("display_name");
                            Log.d("Spotify Username",username);
                            findUserPlaylists(username);
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
    //Gets user playlists using username
    public void findUserPlaylists(String username)
    {
        final String URL = "https://api.spotify.com/v1/users/"+ username +"/playlists?limit=50&offset=0";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, URL,null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        //On response it should try to get the playlist name, the playlist id, and the image for the playlist
                        try {
                            ArrayList<PlaylistInfo> playlistInfos = new ArrayList<>();
                            String playlistID, playlistName, imageURL;
                            int playlistCount;
                            int i = 0;
                            JSONArray jsonArray = response.getJSONArray("items");
                            try {
                                while (jsonArray.getJSONObject(i) != null) {
                                    //gets playlist id and playlist name
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    playlistID = object.getString("id");
                                    playlistName = object.getString("name");

                                    //tries to see if there is an image for the playlist otherwise temp string is input
                                    try {
                                        JSONArray Jarray = object.getJSONArray("images");
                                        JSONObject object2 = Jarray.getJSONObject(0);
                                        imageURL = object2.getString("url");
                                    } catch (org.json.JSONException o) {
                                        imageURL = "https://is5-ssl.mzstatic.com/image/thumb/Purple123/v4/2d/e6/a7/2de6a7a7-cc1e-49c8-f133-c1c3fbcbdeca/AppIcon-0-1x_U007emarketing-0-0-GLES2_U002c0-512MB-sRGB-0-0-0-85-220-0-0-0-6.png/246x0w.jpg";
                                    }

                                    JSONObject object3 =  object.getJSONObject("tracks");
                                    playlistCount = object3.getInt("total");



                                    playlistInfos.add(new PlaylistInfo(playlistName, playlistID, imageURL, playlistCount));
                                    //Log.d("Playlist ID", playlistID);
                                    //Log.d("Playlist Name", playlistName);
                                    //Log.d("Playlist Picture", imageURL);
                                    Log.d("Playlist Song Count", playlistCount +"");
                                    i++;
                                }
                            }catch(org.json.JSONException o)
                            {
                                //On catch it should direct to playlist page
                                Log.d("Complete", "All playlists found!");
                                showPlaylistPage(playlistInfos);

                            }
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

    public void showPlaylistPage(ArrayList<PlaylistInfo> playlistInfos)
    {
        //Puts parameters in bundle to send to next page
        extras.putString("AUTHENTICATION", token);
        extras.putParcelableArrayList("PLAYLISTS", playlistInfos);
        Intent intent = new Intent(this, PlaylistActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
