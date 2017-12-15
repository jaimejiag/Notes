package com.mycompany.notes.ui.note.presenters;

import com.mycompany.notes.data.db.models.Note;
import com.mycompany.notes.ui.note.contracts.AddEditNoteContract;
import com.mycompany.notes.ui.note.interactors.AddEditNotesInteractor;

/**
 * Clase presentador de AddEditNote donde se gestiona la comunicación entre la vista y el interactor.
 */

public class AddEditNotePresenter implements AddEditNoteContract.Presenter, AddEditNoteContract.Interactor.OnFinishAddEditNote {
    private AddEditNoteContract.View mView;
    private AddEditNoteContract.Interactor mInteractor;


    public AddEditNotePresenter(AddEditNoteContract.View mView) {
        this.mView = mView;
        mInteractor = new AddEditNotesInteractor();
    }


    @Override
    public void addNote(Note note) {
        mInteractor.validateNote(note, this);
    }


    @Override
    public void editNote(Note note) {
        mInteractor.editNote(note, this);
    }


    @Override
    public void titleEmptyError() {
        mView.onTitleEmptyError();
    }


    @Override
    public void titleExistsError() {
        mView.onTitleExistsError();
    }


    @Override
    public void onSuccess() {
        mView.goToListNoteFragment();
    }
}
