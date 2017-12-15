package com.mycompany.notes.ui.note.contracts;

import com.mycompany.notes.data.db.models.Note;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Interfaz para implementar métodos MVP para ListNote
 */

public interface ListNoteContract {

    interface View {
        void showListNotes(ArrayList<Note> notes);
    }


    interface Presenter {
        void loadNotes(boolean showOnlyVisible, Comparator<Note> comparator);
        void deleteNote(Note note, boolean showOnlyVisible, Comparator<Note> comparator);
    }


    interface Interactor {
        void loadNotes(boolean showOnlyVisible, Comparator<Note> comparator, OnFinishListNotes listener);
        void deleteNote(Note note, boolean showOnlyVisible, Comparator<Note> comparator, OnFinishListNotes listener);

        interface OnFinishListNotes {
            void onSuccess(ArrayList<Note> notes);
        }
    }
}
