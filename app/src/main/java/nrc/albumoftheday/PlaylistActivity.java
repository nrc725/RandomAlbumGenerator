package nrc.albumoftheday;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity
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
                System.out.println("Feature to be added");
                //eventually move to select album from playlist
                //playlistButtonPress(position);
            }
        };
        mAdapter = new PlaylistAdapter(playlistInfos, pai);
        recyclerView.setAdapter(mAdapter);

    }
}
