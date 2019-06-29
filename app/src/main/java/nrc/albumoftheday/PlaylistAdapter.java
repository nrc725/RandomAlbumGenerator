package nrc.albumoftheday;

import android.support.annotation.NonNull;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.MyViewHolder>{
    private ArrayList<PlaylistInfo> playlistInfos;
    private PlaylistAdapterInterface pai;

    public PlaylistAdapter(ArrayList<PlaylistInfo> pInfo, PlaylistAdapterInterface pAdapterInterface)
    {
        playlistInfos = pInfo;
        pai = pAdapterInterface;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView item;
        public ImageView img;
        public MyViewHolder(View v)
        {
            super(v);
            item = (TextView) v.findViewById(R.id.playlistTextView);
            img = (ImageView) v.findViewById(R.id.playlistImageView);
        }
    }

    @Override
    public PlaylistAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_playlist, viewGroup, false);
        return new PlaylistAdapter.MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(PlaylistAdapter.MyViewHolder myViewHolder, final int i) {
        String currentPlaylist = playlistInfos.get(i).getPlaylistName();
        String imageURL = playlistInfos.get(i).getPlaylistImage();
        myViewHolder.item.setText(currentPlaylist);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(myViewHolder.item,5,20, 1, TypedValue.COMPLEX_UNIT_DIP);
        Picasso.get().load(imageURL).into(myViewHolder.img);
        myViewHolder.item.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                pai.moveToPlaylistButtonPress(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playlistInfos.size();
    }
}
