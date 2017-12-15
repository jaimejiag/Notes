package com.mycompany.notes.ui.note.presenters;

import com.mycompany.notes.data.db.models.Note;
import com.mycompany.notes.ui.note.contracts.ListNoteContract;
import com.mycompany.notes.ui.note.interactors.ListNotesInteractor;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Clase presentador de ListNote donde se gestiona la comunicación entre la vista y el interactor.
 */

public class ListNotePresenter implements ListNoteContract.Presenter, ListNoteContract.Interactor.OnFinishListNotes {
    private ListNoteContract.View mView;
    private ListNoteContract.Interactor mInteractor;


    public ListNotePresenter(ListNoteContract.View mView) {
        this.mView = mView;
        mInteractor = new ListNotesInteractor();
    }


    @Override
    public void loadNotes(boolean showOnlyVisible, Comparator<Note> comparator) {
        mInteractor.loadNotes(showOnlyVisible, comparator, this);
    }


    @Override
    public void deleteNote(Note note, boolean showOnlyVisible, Comparator<Note> comparator) {
        mInteractor.deleteNote(note, showOnlyVisible, comparator, this);
    }


    @Override
    public void onSuccess(ArrayList<Note> notes) {
        mView.showListNotes(notes);
    }
}
