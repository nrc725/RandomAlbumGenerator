package nrc.albumoftheday;

import android.os.Parcel;
import android.os.Parcelable;

public class PlaylistInfo implements Parcelable
{
    int playlistCount;
    String playlistName, playlistURL, playlistImage;
    public PlaylistInfo(String playlistName, String playlistURL, String playlistImage, int playlistCount)
    {
        this.playlistName = playlistName;
        this.playlistURL = playlistURL;
        this.playlistImage = playlistImage;
        this.playlistCount = playlistCount;
    }

    protected PlaylistInfo(Parcel in) {
        playlistName = in.readString();
        playlistURL = in.readString();
        playlistImage = in.readString();
        playlistCount = in.readInt();
    }

    public static final Creator<PlaylistInfo> CREATOR = new Creator<PlaylistInfo>() {
        @Override
        public PlaylistInfo createFromParcel(Parcel in) {
            return new PlaylistInfo(in);
        }

        @Override
        public PlaylistInfo[] newArray(int size) {
            return new PlaylistInfo[size];
        }
    };

    public String getPlaylistName()
    {
        return playlistName;
    }

    public String getPlaylistURL()
    {
        return playlistURL;
    }

    public String getPlaylistImage()
    {
        return playlistImage;
    }

    public int getPlaylistCount(){return playlistCount;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(playlistName);
        dest.writeString(playlistURL);
        dest.writeString(playlistImage);
        dest.writeInt(playlistCount);
    }
}
