package com.example.android.roomwordssample;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {

    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getAlphabetizedWords();

    @Insert
    void insert(Word word);

    @Delete
    void delete(Word word);

    @Update
    void update(Word word);

    @Query("DELETE FROM word_table")
    void deleteAll();
}
