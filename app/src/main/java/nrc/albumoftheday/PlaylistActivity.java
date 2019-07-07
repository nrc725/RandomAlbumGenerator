package nrc.albumoftheday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class PlaylistActivity extends AppCompatActivity implements PlaylistAdapterInterface
{
    private String token;
    private Bundle extras;
    private ArrayList<PlaylistInfo> playlistInfos;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    PlaylistAdapterInterface pai;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        extras = getIntent().getExtras();
        playlistInfos = extras.getParcelableArrayList("PLAYLISTS");
        token = extras.getString("AUTHENTICATION");
        recyclerView = (RecyclerView) findViewById(R.id.playlistSelectionList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        pai = new PlaylistAdapterInterface()
        {
            @Override
            public void moveToPlaylistButtonPress(int position) {
                selectPlaylistTrackButtonPress(position);
            }
        };
        mAdapter = new PlaylistAdapter(playlistInfos, pai);
        recyclerView.setAdapter(mAdapter);

    }

    //Uses playlist to pick a random song to select an artist
    public void selectPlaylistTrackButtonPress(int position)
    {
        Random random = new Random();
        int randomNum = random.nextInt(playlistInfos.get(position).getPlaylistCount()) +1;
        final String URL = "https://api.spotify.com/v1/playlists/" + playlistInfos.get(position).getPlaylistURL() +
                "/tracks?market=US&fields=items(track(name%2Cartists))&limit=1&offset="+ randomNum;

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

    //FOR THIS MAY WANT TO CONSIDER INCREASING LIMIT TO GET MULTIPLE ALBUMS IN CASE ONE IS SINGLE
    public void selectArtistAlbum(String artistURI)
    {
        Random random = new Random();
        final String URL = "https://api.spotify.com/v1/artists/"+ artistURI + "/albums?include_groups=album&market=US&limit=50&offset=0";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, URL,null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Artist Album Response", response.toString());
                        try {
                            int totalAlbums = response.getInt("total");

                            Log.d("total album count", totalAlbums+"");
                            //takes jsonobject and unwraps it to get to the album uri
                            JSONArray jarray = response.getJSONArray("items");
                            JSONObject object = jarray.getJSONObject(random.nextInt(totalAlbums));
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
        extras.putBoolean("IS_PLAYLIST", true);
        Intent intent = new Intent(this, AlbumActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // save data first
        Intent MainActivityIntent = new Intent(this, MenuActivity.class);
        extras.putString("AUTHENTICATION", token);
        MainActivityIntent.putExtras(extras);
        startActivity(MainActivityIntent);
        super.onBackPressed();
    }

    @Override
    public void moveToPlaylistButtonPress(int position) {
        selectPlaylistTrackButtonPress(position);
    }
}
