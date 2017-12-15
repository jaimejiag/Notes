package com.mycompany.notes;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mycompany.notes.ui.note.AddEditFragment;
import com.mycompany.notes.ui.note.ListNoteFragment;

/**
 * Activity que funciona como contendor y comunicador entre fragments.
 */
public class MainActivity extends AppCompatActivity implements ListNoteFragment.ListNoteFragmentListener,
        AddEditFragment.AddEditFragmentListener {
    private ListNoteFragment listNoteFragment;
    private AddEditFragment addEditFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listNoteFragment = (ListNoteFragment) getSupportFragmentManager().findFragmentByTag(ListNoteFragment.TAG);

        if (listNoteFragment == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            listNoteFragment = ListNoteFragment.newInstance(null);
            transaction.add(android.R.id.content, listNoteFragment, ListNoteFragment.TAG).commit();
        }
    }

    
    @Override
    public void showAddEditNoteFragment(Bundle arguments) {
        addEditFragment = (AddEditFragment) getSupportFragmentManager().findFragmentByTag(AddEditFragment.TAG);

        if (addEditFragment == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            addEditFragment = AddEditFragment.newInstance(arguments);
            transaction.addToBackStack(null);
            transaction.replace(android.R.id.content, addEditFragment, AddEditFragment.TAG).commit();
        }
    }


    @Override
    public void showListNotefragment() {
        getSupportFragmentManager().popBackStack();
    }
}
