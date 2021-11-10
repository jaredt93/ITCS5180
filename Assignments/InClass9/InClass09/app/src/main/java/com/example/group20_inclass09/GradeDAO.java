package com.example.group20_inclass09;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GradeDAO {
    @Query("SELECT * FROM grade order by course_title asc")
    List<Grade> getAll();

    @Insert
    void insertAll(Grade... grade);

    @Insert
    void insert(Grade grade);

    @Delete
    void delete(Grade grade);

    @Query("DELETE FROM grade WHERE id = :id")
    void delete(long id);
}