package nrc.albumoftheday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuActivity extends AppCompatActivity {

    //Have user select a genre of music they want to listen to
    //generate the playlists associated with that category of music
    //select a random playlist, then a random track from playlist
    //use artist id from playlist to select a random album from artist

    ImageView imageView;
    Button button;
    String token;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        extras = getIntent().getExtras();
        imageView = (ImageView) findViewById(R.id.imageView1);
        button = (Button) findViewById(R.id.button1);
        token = extras.getString("AUTHENTICATION");
    }

    //Gets a random playlist that is associated with the genre that was selected
    public void genreButtonPress(View view) {
        button.setEnabled(false);
        Random random = new Random();
        final String URL = "https://api.spotify.com/v1/browse/categories/rock/playlists?country=US&limit=1&offset=" + random.nextInt(10);
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, URL,null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Tries to get the playlist uri from the jsonobject
                        try {
                            JSONObject json = response.getJSONObject("playlists");
                            JSONObject object = new JSONObject(json.toString());
                            JSONArray Jarray  = object.getJSONArray("items");
                            JSONObject Jasonobject = Jarray.getJSONObject(0);
                            String id = Jasonobject.getString("id");
                            Log.d("Playlist ID Code",id);
                            selectPlaylistTrack(id);
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

    //Picks a random song out of random playlist to find an artist to pick an album from
    public void selectPlaylistTrack(String playlistURI)
    {
        Random random = new Random();
        final String URL ="https://api.spotify.com/v1/playlists/"+ playlistURI +"/tracks?market=US&fields=items(track(name%2Cartists))&limit=1&offset=" + (random.nextInt(10)+1);

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, URL,null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Playlist Track Response", response.toString());
                        try {
                            //takes jsonobject and unwraps it to get to the artist uri and logs it
                            JSONArray jarray = response.getJSONArray("items");
                            JSONObject object = jarray.getJSONObject(0);
                            JSONObject object2 = object.getJSONObject("track");
                            JSONArray jarray2 = object2.getJSONArray("artists");
                            JSONObject object3 = jarray2.getJSONObject(0);
                            String artistURI = object3.getString("id");
                            Log.d("Artist URI", artistURI);
                            selectArtistAlbum(artistURI);
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

    public void selectArtistAlbum(String artistURI)
    {
        Random random = new Random();
        final String URL ="https://api.spotify.com/v1/artists/" + artistURI + "/albums?market=US&limit=1&offset=" + (random.nextInt(10)+1);

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, URL,null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                       Log.d("Artist Album Response", response.toString());
                       try {
                            //takes jsonobject and unwraps it to get to the album uri
                            JSONArray jarray = response.getJSONArray("items");
                            JSONObject object = jarray.getJSONObject(0);
                            String albumURI = object.getString("id");
                            Log.d("Album URI", albumURI);
                            getAlbumInfo(albumURI);
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

    public void getAlbumInfo(String albumURI)
    {
        final String URL ="https://api.spotify.com/v1/albums/" + albumURI + "?market=US";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, URL,null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Get Album Info Response", response.toString());
                        try {
                            //takes jsonobject and unwraps it to get to the artist uri
                            JSONArray jarray = response.getJSONArray("images");

                            //Gets the album url from the jsonobject
                            JSONObject object = jarray.getJSONObject(0);
                            String albumURL = object.getString("url");

                            //Gets redirecturl from json object
                            JSONObject object2 = response.getJSONObject("external_urls");
                            String albumRedirectURL = object2.getString("spotify");

                            //Gets name of artist from json response
                            JSONArray artistArray = response.getJSONArray("artists");
                            JSONObject artistObject = artistArray.getJSONObject(0);
                            String artistName = artistObject.getString("name");
                            String albumName = response.getString("name");

                            //Logs albumUrl/redirectURL and sends info to album page to display
                            Log.d("Artist Name", artistName);
                            Log.d("Album Name", albumName);
                            Log.d("Album URL", albumURL);
                            Log.d("Album Redirect URL", albumRedirectURL);
                            showAlbumPage(albumURI,albumURL, albumRedirectURL, albumName, artistName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR","error => "+error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);

                return params;
            }
        };
        queue.add(getRequest);
    }

    //Passes all important info to the page to display the album
    public void showAlbumPage(String albumURI, String albumURL, String albumRedirectURL, String albumName, String artistName)
    {
        //Puts parameters in bundle to send to next page
        extras.putString("AUTHENTICATION", token);
        extras.putString("ALBUM_URI", albumURI);
        extras.putString("ALBUM_URL", albumURL);
        extras.putString("ALBUM_REDIRECT_URL", albumRedirectURL);
        extras.putString("ALBUM_NAME", albumName);
        extras.putString("ARTIST_NAME", artistName);
        Intent intent = new Intent(this, AlbumActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

}
