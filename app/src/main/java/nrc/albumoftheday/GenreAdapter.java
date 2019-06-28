package nrc.albumoftheday;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.MyViewHolder>
{
    private String[] genreList;
    private GenreAdapterInterface gai;

    public GenreAdapter(String[] gList, GenreAdapterInterface gAdapterInterface)
    {
        genreList = gList;
        gai = gAdapterInterface;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView item;
        public MyViewHolder(View v)
        {
            super(v);
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
        String currentGenre = genreList[i];
        myViewHolder.item.setText(currentGenre);
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
        return genreList.length;
    }
}