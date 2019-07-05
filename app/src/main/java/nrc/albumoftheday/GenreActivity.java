package nrc.albumoftheday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
    private int playlistCount;
    private String token;
    private Bundle extras;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GenreAdapterInterface gai;

    //To avoid making an api call just to get the images of all the genres I got the URLs manually
    private ArrayList<GenreInfo> genreInfos = new ArrayList<GenreInfo>(){{
        add(new GenreInfo("Blues","https://t.scdn.co/media/derived/icon-274x274_aeeb8eb70c80e2b701b25425390a1737_0_0_274_274.jpg"));
        add(new GenreInfo("Chill","https://t.scdn.co/media/derived/chill-274x274_4c46374f007813dd10b37e8d8fd35b4b_0_0_274_274.jpg"));
        add(new GenreInfo("Christian","https://t.scdn.co/images/6359655809534407b31a728fe262dc3a.jpeg"));
        add(new GenreInfo("Classical","https://t.scdn.co/media/derived/classical-274x274_abf78251ff3d90d2ceaf029253ca7cb4_0_0_274_274.jpg"));
        add(new GenreInfo("Comedy","https://t.scdn.co/media/derived/comedy-274x274_d07fcbc1202f00f8684f37742d0a4f2f_0_0_274_274.jpg"));
        add(new GenreInfo("Country","https://t.scdn.co/images/a2e0ebe2ebed4566ba1d8236b869241f.jpeg"));
        add(new GenreInfo("Decades","https://t.scdn.co/media/derived/decades_9ad8e458242b2ac8b184e79ef336c455_0_0_274_274.jpg"));
        add(new GenreInfo("Dinner","https://t.scdn.co/media/original/dinner_1b6506abba0ba52c54e6d695c8571078_274x274.jpg"));
        add(new GenreInfo("EDM/Dance","https://t.scdn.co/media/derived/edm-274x274_0ef612604200a9c14995432994455a6d_0_0_274_274.jpg"));
        add(new GenreInfo("Focus","https://t.scdn.co/media/original/genre-images-square-274x274_5e50d72b846a198fcd2ca9b3aef5f0c8_274x274.jpg"));
        add(new GenreInfo("Funk","https://t.scdn.co/images/f4f0987fcab446fcaa7173acb5e25701.jpeg"));
        add(new GenreInfo("Gaming","https://t.scdn.co/media/categories/gaming2_274x274.jpg"));
        add(new GenreInfo("Hip Hop","https://t.scdn.co/media/original/hip-274_0a661854d61e29eace5fe63f73495e68_274x274.jpg"));
        add(new GenreInfo("Indie_Alt","https://t.scdn.co/images/7fe0f2c9c91f45a3b6bae49d298201a4.jpeg"));
        add(new GenreInfo("Jazz","https://t.scdn.co/media/derived/jazz-274x274_d6f407453a1f43d3163c55cca624a764_0_0_274_274.jpg"));
        add(new GenreInfo("K Pop","https://t.scdn.co/images/69c728f3bd9643a5ab0f4ef5a79919f1.jpeg"));
        add(new GenreInfo("Latin","https://t.scdn.co/media/derived/latin-274x274_befbbd1fbb8e045491576e317cb16cdf_0_0_274_274.jpg"));
        add(new GenreInfo("Metal","https://t.scdn.co/media/original/metal_27c921443fd0a5ba95b1b2c2ae654b2b_274x274.jpg"));
        add(new GenreInfo("Mood","https://t.scdn.co/media/original/mood-274x274_976986a31ac8c49794cbdc7246fd5ad7_274x274.jpg"));
        add(new GenreInfo("Party","https://t.scdn.co/media/links/partyicon_274x274.jpg"));
        add(new GenreInfo("Pop","https://t.scdn.co/media/derived/pop-274x274_447148649685019f5e2a03a39e78ba52_0_0_274_274.jpg"));
        add(new GenreInfo("Pride","https://t.scdn.co/images/90f4c163-46f6-4cda-bd84-e78ff90d4959.jpg"));
        add(new GenreInfo("Punk","https://t.scdn.co/media/derived/punk-274x274_f3f1528ea7bbb60a625da13e3315a40b_0_0_274_274.jpg"));
        add(new GenreInfo("Rock","https://t.scdn.co/media/derived/rock_9ce79e0a4ef901bbd10494f5b855d3cc_0_0_274_274.jpg"));
        add(new GenreInfo("Romance","https://t.scdn.co/media/derived/romance-274x274_8100794c94847b6d27858bed6fa4d91b_0_0_274_274.jpg"));
        add(new GenreInfo("Roots","https://t.scdn.co/media/links/partyicon_274x274.jpg"));
        add(new GenreInfo("RnB","https://t.scdn.co/media/derived/r-b-274x274_fd56efa72f4f63764b011b68121581d8_0_0_274_274.jpg"));
        add(new GenreInfo("Sleep","https://t.scdn.co/media/derived/sleep-274x274_0d4f836af8fab7bf31526968073e671c_0_0_274_274.jpg"));
        add(new GenreInfo("Soul","https://t.scdn.co/media/derived/soul-274x274_266bc900b35dda8956380cffc73a4d8c_0_0_274_274.jpg"));
        add(new GenreInfo("Summer","https://t.scdn.co/images/8e508d7eb5b843a89c368c9507ecc429.jpeg"));
        add(new GenreInfo("Top Lists","https://t.scdn.co/media/derived/toplists_11160599e6a04ac5d6f2757f5511778f_0_0_275_275.jpg"));
        add(new GenreInfo("Workout","https://t.scdn.co/media/links/workout-274x274.jpg"));
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
                            JSONObject object3 =  Jasonobject.getJSONObject("tracks");
                            playlistCount = object3.getInt("total");
                            Log.d("Playlist ID Code",id);
                            Log.d("Playlist Song Count", playlistCount+"");
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
        int randomNum = random.nextInt(playlistCount) +1;
        final String URL ="https://api.spotify.com/v1/playlists/"+ playlistURI +"/tracks?market=US&fields=items(track(name%2Cartists))&limit=1&offset=" + randomNum;

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

    //IF THE OFFSET IS TOO LARGE IT WILL NOT SHOW AN ALBUM, SPOTIFY DOESNT KEEP ALBUM COUNTS FOR ARTISTS SO I CANT CHECK TO MAKE SURE THIS CAN BE PREVENTED
    public void selectArtistAlbum(String artistURI)
    {
        Random random = new Random();
        final String URL ="https://api.spotify.com/v1/artists/" + artistURI + "/albums?include_groups=album&market=US&limit=1&offset=" + (random.nextInt(5)+1);

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
                           Toast.makeText(GenreActivity.this, "Error Getting Album, Please Try Again", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(GenreActivity.this, "Error Getting Album, Please Try Again", Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {
        // save data first
        Intent MainActivityIntent = new Intent(this, MenuActivity.class);
        extras.putString("AUTHENTICATION", token);
        MainActivityIntent.putExtras(extras);
        startActivity(MainActivityIntent);
        super.onBackPressed();
    }

    @Override
    public void moveToGenreButtonPress(int position) {
        genreButtonPress(position);
    }
}
