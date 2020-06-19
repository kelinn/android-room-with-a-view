package com.example.android.roomwordssample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private WordViewModel mWordViewModel;
    private ViewGroup _parent;
    private WordListAdapter adapter;
    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        public  Button updateBTN;
        public  Button deleteBTN;



        private WordViewHolder(View itemView) {
            super(itemView);

            wordItemView = itemView.findViewById(R.id.textView);
            updateBTN = (Button) itemView.findViewById(R.id.editBtn);
            deleteBTN = (Button) itemView.findViewById(R.id.deleteBtn);
            mWordViewModel = ViewModelProviders.of((FragmentActivity) mInflater.getContext()).get(WordViewModel.class);

        }
    }

    private final LayoutInflater mInflater;
    private List<Word> mWords = Collections.emptyList(); // Cached copy of words

    WordListAdapter(Context context, WordViewModel mWordViewM) {
        mWordViewModel = mWordViewM;
        mInflater = LayoutInflater.from(context);
        adapter = this;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        _parent = parent;
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WordViewHolder holder, final int position) {

        final Word current = mWords.get(position);

        holder.wordItemView.setText(current.getWord());

        holder.updateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View _inflater = mInflater.inflate(R.layout.edit_word, _parent, false);

                final EditText editText;

                editText = (EditText) _inflater.findViewById(R.id.word_);

                editText.setText(current.getWord());
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(mInflater.getContext());
                dialogo1.setTitle(current.getWord());
                dialogo1.setMessage("¿ Desea Editar el item ?");
                dialogo1.setView(_inflater);
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        current.setmWord(editText.getText().toString());
                        mWordViewModel.update(current);
                        adapter.notifyItemChanged(position);
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.dismiss();
                    }
                });
                dialogo1.show();

            }
        });

        holder.deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(mInflater.getContext());
                dialogo1.setTitle(current.getWord());
                dialogo1.setMessage("¿ Desea eliminar el item ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        mWordViewModel.delete(current);
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.dismiss();
                    }
                });
                dialogo1.show();
            }
        });
    }



    void setWords(List<Word> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }
}


