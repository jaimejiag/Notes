package com.mycompany.notes.ui.note.contracts;

import com.mycompany.notes.data.db.models.Note;

/**
 * Interfaz para implementar métodos MVP para AddEditNote
 */

public interface AddEditNoteContract {

    interface View {
        void goToListNoteFragment();
        void onTitleEmptyError();
        void onTitleExistsError();
    }


    interface Presenter {
        void addNote(Note note);
        void editNote(Note note);
    }


    interface Interactor {
        void validateNote(Note note, OnFinishAddEditNote listener);
        void addNote(Note note, OnFinishAddEditNote listener);
        void editNote(Note note, OnFinishAddEditNote listener);

        interface OnFinishAddEditNote {
            void titleEmptyError();
            void titleExistsError();
            void onSuccess();
        }
    }
}
