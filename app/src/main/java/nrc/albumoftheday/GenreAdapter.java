package nrc.albumoftheday;

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


public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.MyViewHolder>
{
    private ArrayList<GenreInfo> genreInfos;
    private GenreAdapterInterface gai;

    public GenreAdapter(ArrayList<GenreInfo> gList, GenreAdapterInterface gAdapterInterface)
    {
        genreInfos = gList;
        gai = gAdapterInterface;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView item;
        public ImageView img;
        public MyViewHolder(View v)
        {
            super(v);
            img = (ImageView) v.findViewById(R.id.genreImageView);
            item = (TextView) v.findViewById(R.id.genreTextView);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_genre, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        String currentGenre = genreInfos.get(i).getGenreName();
        String imageURL = genreInfos.get(i).getGenreImage();
        myViewHolder.item.setText(currentGenre);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(myViewHolder.item,5,20, 1, TypedValue.COMPLEX_UNIT_DIP);
        Picasso.get().load(imageURL).into(myViewHolder.img);
        myViewHolder.item.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                gai.moveToGenreButtonPress(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return genreInfos.size();
    }
}