package com.example.group20_inclass09;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Grade.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GradeDAO GradeDAO();
}
