package nrc.albumoftheday;

import android.os.Parcel;
import android.os.Parcelable;

public class GenreInfo implements Parcelable
{
        String genreName, genreImage;
        public GenreInfo(String genreName,String genreImage)
        {
            this.genreName = genreName;
            this.genreImage = genreImage;
        }

        protected GenreInfo(Parcel in) {
            genreName = in.readString();
            genreImage = in.readString();
        }

        public static final Creator<GenreInfo> CREATOR = new Creator<GenreInfo>() {
            @Override
            public nrc.albumoftheday.GenreInfo createFromParcel(Parcel in) {
                return new nrc.albumoftheday.GenreInfo(in);
            }

            @Override
            public nrc.albumoftheday.GenreInfo[] newArray(int size) {
                return new nrc.albumoftheday.GenreInfo[size];
            }
        };

        public String getGenreName()
        {
            return genreName;
        }

        public String getGenreImage()
        {
            return genreImage;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(genreName);
            dest.writeString(genreImage);
        }
}

