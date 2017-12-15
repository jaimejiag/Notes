package com.mycompany.notes.data.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Clase POJO de notas.
 */

public class Note implements Parcelable {
    private int id;
    private String title;
    private String description;
    private boolean isVisible;

    public static final Comparator<Note> ID_ORDER =  new Comparator<Note>() {
        @Override
        public int compare(Note o1, Note o2) {
            int result = 0;

            if (o1.getId() > o2.getId())
                result = 1;
            else if (o1.getId() < o2.getId())
                result = -1;

            return result;
        }
    };

    public static final Comparator<Note> TITLE_ORDER = new Comparator<Note>() {
        @Override
        public int compare(Note o1, Note o2) {
            return o1.getTitle().compareToIgnoreCase(o2.getTitle());
        }
    };


    public Note(String title, String description, boolean isVisible) {
        this.title = title;
        this.description = description;
        this.isVisible = isVisible;
    }


    public Note(int id, String title, String description, boolean isVisible) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isVisible = isVisible;
    }


    protected Note(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        isVisible = in.readByte() != 0;
    }


    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeByte((byte) (isVisible ? 1 : 0));
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
