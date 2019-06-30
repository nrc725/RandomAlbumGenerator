package nrc.albumoftheday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

public class GenreActivity extends AppCompatActivity implements GenreAdapterInterface
{

    private String token;
    private Bundle extras;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GenreAdapterInterface gai;

    //To avoid making an api call just to get the image of the genre I got the URLs manually
    private ArrayList<GenreInfo> genreInfos = new ArrayList<GenreInfo>(){{
        add(new GenreInfo("Rock","https://t.scdn.co/media/derived/rock_9ce79e0a4ef901bbd10494f5b855d3cc_0_0_274_274.jpg"));
        add(new GenreInfo("Hip Hop","https://t.scdn.co/media/original/hip-274_0a661854d61e29eace5fe63f73495e68_274x274.jpg"));
        add(new GenreInfo("Pop","https://t.scdn.co/media/derived/pop-274x274_447148649685019f5e2a03a39e78ba52_0_0_274_274.jpg"));
        add(new GenreInfo("Summer","https://t.scdn.co/images/8e508d7eb5b843a89c368c9507ecc429.jpeg"));
        add(new GenreInfo("Country","https://t.scdn.co/images/a2e0ebe2ebed4566ba1d8236b869241f.jpeg"));
        add(new GenreInfo("Metal","https://t.scdn.co/media/original/metal_27c921443fd0a5ba95b1b2c2ae654b2b_274x274.jpg"));
        add(new GenreInfo("Workout","https://t.scdn.co/media/links/workout-274x274.jpg"));
        add(new GenreInfo("Party","https://t.scdn.co/media/links/partyicon_274x274.jpg"));
        add(new GenreInfo("Classical","https://t.scdn.co/media/derived/classical-274x274_abf78251ff3d90d2ceaf029253ca7cb4_0_0_274_274.jpg"));
        add(new GenreInfo("Jazz","https://t.scdn.co/media/derived/jazz-274x274_d6f407453a1f43d3163c55cca624a764_0_0_274_274.jpg"));
        add(new GenreInfo("Gaming","https://t.scdn.co/media/categories/gaming2_274x274.jpg"));
        add(new GenreInfo("Funk","https://t.scdn.co/images/f4f0987fcab446fcaa7173acb5e25701.jpeg"));
        add(new GenreInfo("Punk","https://t.scdn.co/media/derived/punk-274x274_f3f1528ea7bbb60a625da13e3315a40b_0_0_274_274.jpg"));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);
        extras = getIntent().getExtras();
        recyclerView = (RecyclerView) findViewById(R.id.genreSelectionList);
        token = extras.getString("AUTHENTICATION");

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        gai = new GenreAdapterInterface()
        {
            @Override
            public void moveToGenreButtonPress(int position) {
                genreButtonPress(position);
            }
        };
        mAdapter = new GenreAdapter(genreInfos, gai);
        recyclerView.setAdapter(mAdapter);
    }

    //Gets a random playlist that is associated with the genre that was selected
    public void genreButtonPress(int position)
    {
        String genre = genreInfos.get(position).getGenreName().replaceAll("\\s+","");
        Random random = new Random();
        final String URL = "https://api.spotify.com/v1/browse/categories/" + genre.toLowerCase() + "/playlists?country=US&limit=1&offset=" + random.nextInt(10);
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

    //FOR THIS MAY WANT TO CONSIDER INCREASING LIMIT TO GET MULTIPLE ALBUMS IN CASE ONE IS SINGLE
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
        extras.putBoolean("IS_PLAYLIST", false);
        Intent intent = new Intent(this, AlbumActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public void moveToGenreButtonPress(int position) {
        genreButtonPress(position);
    }
}
