package com.mycompany.notes.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mycompany.notes.R;
import com.mycompany.notes.data.db.models.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter que controla una colección de notas.
 */

public class NotesAdapter extends ArrayAdapter<Note> {

    public NotesAdapter(@NonNull Context context) {
        super(context, R.layout.item_note, new ArrayList<Note>());
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = convertView;
        NoteHolder holder;

        if (rootView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            rootView = inflater.inflate(R.layout.item_note, null);
            holder = new NoteHolder();

            holder.txvTitle = rootView.findViewById(R.id.txv_title);
            holder.txvDescription = rootView.findViewById(R.id.txv_description);

            rootView.setTag(holder);
        } else
            holder = (NoteHolder) rootView.getTag();

        holder.txvTitle.setText(getItem(position).getTitle());
        holder.txvDescription.setText(getItem(position).getDescription());

        return rootView;
    }


    public class NoteHolder {
        TextView txvTitle;
        TextView txvDescription;
    }
}
