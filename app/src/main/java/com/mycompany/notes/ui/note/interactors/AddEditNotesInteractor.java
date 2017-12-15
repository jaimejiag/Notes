package com.mycompany.notes.ui.note.interactors;

import com.mycompany.notes.data.db.models.Note;
import com.mycompany.notes.data.db.repositories.NoteRepository;
import com.mycompany.notes.ui.note.contracts.AddEditNoteContract;

/**
 * Clase interactor de AddEditNote que se comunica con el repositorio.
 */

public class AddEditNotesInteractor implements AddEditNoteContract.Interactor {

    @Override
    public void validateNote(Note note, OnFinishAddEditNote listener) {
        if (note.getTitle().isEmpty())
            listener.titleEmptyError();
        else if (NoteRepository.getInstance().titleExists(note.getTitle()))
            listener.titleExistsError();
        else
            addNote(note, listener);
    }


    @Override
    public void addNote(Note note, OnFinishAddEditNote listener) {
        note.setId(NoteRepository.getInstance().getNewId());
        NoteRepository.getInstance().addNote(note);
        listener.onSuccess();
    }


    @Override
    public void editNote(Note note, OnFinishAddEditNote listener) {
        NoteRepository.getInstance().editNote(note);
        listener.onSuccess();
    }
}
