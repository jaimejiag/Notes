package com.mycompany.notes.data.db.repositories;

import com.mycompany.notes.data.db.models.Note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Clase repositorio donde se gestiona la base de datos de notas.
 */

public class NoteRepository {
    private static NoteRepository mInstance;
    private ArrayList<Note> mNotes;


    public NoteRepository() {
        mNotes = new ArrayList<>();
        initialize();
    }


    public static NoteRepository getInstance() {
        if (mInstance == null)
            mInstance = new NoteRepository();

        return mInstance;
    }


    private void initialize() {
        addNote(new Note(1, "Comprar leche", "Comprar leche desnatada", false));
        addNote(new Note(2, "Examen DEINT", "Preparar el examen para el jueves 14", true));
    }


    public void addNote(Note note) {
        mNotes.add(note);
    }


    public ArrayList<Note> getNotes(boolean showOnlyVisible, Comparator<Note> comparator) {
        ArrayList<Note> noteList = new ArrayList<>();

        if (showOnlyVisible)
            noteList = filterNote();
        else
            noteList = mNotes;

        Collections.sort(noteList, comparator);

        return noteList;
    }


    public void deleteNote(Note note) {
        Iterator<Note> iterator = mNotes.iterator();
        Note tmpNote = null;

        while (iterator.hasNext()) {
            tmpNote = iterator.next();

            if (note.getTitle().equals(tmpNote.getTitle())) {
                iterator.remove();
                break;
            }
        }
    }


    public void editNote(Note note) {
        int index = 0;

        while (index < mNotes.size()) {
            if (note.getTitle().equals(mNotes.get(index).getTitle())) {
                mNotes.get(index).setDescription(note.getDescription());
                mNotes.get(index).setVisible(note.isVisible());
                index = mNotes.size();
            } else
                index++;
        }
    }


    public int getNewId() {
        return (mNotes.get(mNotes.size() - 1).getId()) + 1;
    }


    public boolean titleExists(String title) {
        int index = 0;
        boolean result = false;

        while (index < mNotes.size() && !result) {
            if (mNotes.get(index).getTitle().equals(title)) {
                result = true;
            } else
                index++;
        }

        return result;
    }


    private ArrayList<Note> filterNote() {
        ArrayList<Note> filterNotes = new ArrayList<>();

        for (int i = 0; i < mNotes.size(); i++) {
            if (mNotes.get(i).isVisible())
                filterNotes.add(mNotes.get(i));
        }

        return filterNotes;
    }
}
