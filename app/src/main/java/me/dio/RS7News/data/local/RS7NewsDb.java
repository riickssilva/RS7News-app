package me.dio.RS7News.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import me.dio.RS7News.domain.News;

@Database(entities = {News.class}, version = 1)
public abstract class RS7NewsDb extends RoomDatabase {
    public abstract NewsDao newsDao();
}
