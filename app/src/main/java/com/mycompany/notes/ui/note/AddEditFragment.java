package com.mycompany.notes.ui.note;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import com.mycompany.notes.R;
import com.mycompany.notes.data.db.models.Note;
import com.mycompany.notes.ui.note.contracts.AddEditNoteContract;
import com.mycompany.notes.ui.note.presenters.AddEditNotePresenter;

/**
 * Clase fragment que gestiona la vista de AddEditNote.
 */
public class AddEditFragment extends Fragment implements AddEditNoteContract.View {
    public static final String TAG = "addeditfragment";

    private EditText edtTitle;
    private Switch swVisible;
    private EditText edtDescription;
    private FloatingActionButton fabSave;

    private AddEditFragmentListener mCallback;
    private AddEditNoteContract.Presenter mPresenter;


    public interface AddEditFragmentListener {
        void showListNotefragment();
    }


    public static AddEditFragment newInstance(Bundle arguments) {
        AddEditFragment fragment = new AddEditFragment();

        if (arguments != null)
            fragment.setArguments(arguments);

        return fragment;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (AddEditFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getLocalClassName() + " must implement AddEditFragmentListener");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new AddEditNotePresenter(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit, container, false);

        edtTitle = view.findViewById(R.id.edt_title);
        edtDescription = view.findViewById(R.id.edt_description);
        swVisible = view.findViewById(R.id.sw_visible);
        fabSave = view.findViewById(R.id.fab_save);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getArguments() == null) {
                    addEditNote(true);
                } else
                    addEditNote(false);
            }
        });

        if (getArguments() != null) {
            initializeData();
        }
    }


    @Override
    public void goToListNoteFragment() {
        mCallback.showListNotefragment();
    }


    @Override
    public void onTitleEmptyError() {
        Snackbar.make(getView(), getResources().getString(R.string.title_empty_error), Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onTitleExistsError() {
        Snackbar.make(getView(), getResources().getString(R.string.note_exits_error), Snackbar.LENGTH_SHORT).show();
    }


    private void addEditNote(boolean addNote) {
        Note note = new Note(
                edtTitle.getText().toString(),
                edtDescription.getText().toString(),
                swVisible.isChecked()
        );

        if (addNote)
            mPresenter.addNote(note);
        else
            mPresenter.editNote(note);
    }


    private void initializeData() {
        Note note = getArguments().getParcelable(ListNoteFragment.KEY_EDIT);

        edtTitle.setText(note.getTitle());
        edtTitle.setEnabled(false);
        edtDescription.setText(note.getDescription());
        swVisible.setChecked(note.isVisible());
    }
}
