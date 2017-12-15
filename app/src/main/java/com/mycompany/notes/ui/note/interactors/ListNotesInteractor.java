package com.mycompany.notes.ui.note.interactors;

import com.mycompany.notes.data.db.models.Note;
import com.mycompany.notes.data.db.repositories.NoteRepository;
import com.mycompany.notes.ui.note.contracts.ListNoteContract;

import java.util.Comparator;

/**
 * Clase interactor de ListNote que se comunica con el repositorio.
 */

public class ListNotesInteractor implements ListNoteContract.Interactor {

    @Override
    public void loadNotes(boolean showOnlyVisible, Comparator<Note> comparator, OnFinishListNotes listener) {
        listener.onSuccess(NoteRepository.getInstance().getNotes(showOnlyVisible, comparator));
    }


    @Override
    public void deleteNote(Note note, boolean showOnlyVisible, Comparator<Note> comparator, OnFinishListNotes listener) {
        NoteRepository.getInstance().deleteNote(note);
        listener.onSuccess(NoteRepository.getInstance().getNotes(showOnlyVisible, comparator));
    }
}
