package com.mycompany.notes.ui.note;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mycompany.notes.R;
import com.mycompany.notes.adapters.NotesAdapter;
import com.mycompany.notes.data.db.models.Note;
import com.mycompany.notes.ui.note.contracts.ListNoteContract;
import com.mycompany.notes.ui.note.presenters.ListNotePresenter;

import java.util.ArrayList;

/**
 * Clase fragment que gestiona la vista de ListNote.
 */
public class ListNoteFragment extends Fragment implements ListNoteContract.View {
    public static final String TAG = "listnotefragment";
    public static final String KEY_EDIT = "keyedit";

    private ListView lvNotes;
    private FloatingActionButton fabAddEdit;

    private ListNoteFragmentListener mCallback;
    private NotesAdapter mAdapter;
    private ListNoteContract.Presenter mPresenter;
    private boolean showOnlyVisible;


    public interface ListNoteFragmentListener {
        void showAddEditNoteFragment(Bundle arguments);
    }


    public static ListNoteFragment newInstance(Bundle arguments) {
        ListNoteFragment fragment = new ListNoteFragment();

        if (arguments != null)
            fragment.setArguments(arguments);

        return fragment;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (ListNoteFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getLocalClassName() + " must implement ListNoteFragmentListener");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        mAdapter = new NotesAdapter(getActivity());
        mPresenter = new ListNotePresenter(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_note, container, false);

        lvNotes = view.findViewById(R.id.lv_notes);
        fabAddEdit = view.findViewById(R.id.fab_addedit);


        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerForContextMenu(lvNotes);

        lvNotes.setAdapter(mAdapter);
        mPresenter.loadNotes(false, Note.TITLE_ORDER);

        fabAddEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.showAddEditNoteFragment(null);
            }
        });

        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note note = (Note) adapterView.getItemAtPosition(i);
                Bundle bundle = new Bundle();
                bundle.putParcelable(KEY_EDIT, note);
                mCallback.showAddEditNoteFragment(bundle);
            }
        });
    }


    @Override
    public void showListNotes(ArrayList<Note> notes) {
        mAdapter.clear();
        mAdapter.addAll(notes);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_listnote_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_order_id:
                showOnlyVisible = false;
                mPresenter.loadNotes(false, Note.ID_ORDER);
                break;

            case R.id.action_order_title:
                showOnlyVisible = false;
                mPresenter.loadNotes(false, Note.TITLE_ORDER);
                break;

            case R.id.action_show_actives:
                showOnlyVisible = true;
                mPresenter.loadNotes(true, Note.TITLE_ORDER);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle(getResources().getString(R.string.menu_context_header));
        getActivity().getMenuInflater().inflate(R.menu.menu_context_listnotes_fragment, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Note note = (Note) lvNotes.getItemAtPosition(info.position);

        switch (item.getItemId()) {
            case R.id.action_context_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(R.string.dialog_title))
                        .setMessage(getResources().getString(R.string.dialog_message) + " " + note.getTitle())
                        .setPositiveButton(getResources().getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mPresenter.deleteNote(note, showOnlyVisible, Note.TITLE_ORDER);
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).create();

                builder.show();
                break;
        }

        return super.onContextItemSelected(item);
    }
}
