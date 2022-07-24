package me.dio.RS7News.data;

import androidx.room.Room;

import me.dio.RS7News.App;
import me.dio.RS7News.data.local.RS7NewsDb;
import me.dio.RS7News.data.remote.RS7NewsApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RS7NewsRepository {

    //region Constantes
    private static final String REMOTE_API_URL = "https://riickssilva.github.io/RS7News-api/";
    private static final String LOCAL_DB_NAME = "RS7-news";
    //endregion

    //region Atributos: encapsulam o acesso a nossa API (Retrofit) e banco de dados local (Room).
    private RS7NewsApi remoteApi;
    private RS7NewsDb localDb;

    public RS7NewsApi getRemoteApi() {
        return remoteApi;
    }

    public RS7NewsDb getLocalDb() {
        return localDb;
    }
    //endregion

    //region Singleton: garante uma instância única dos atributos relacionados ao Retrofit e Room.
    private RS7NewsRepository() {
        remoteApi = new Retrofit.Builder()
                .baseUrl(REMOTE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RS7NewsApi.class);

        localDb = Room.databaseBuilder(App.getInstance(), RS7NewsDb.class, LOCAL_DB_NAME).build();
    }

    public static RS7NewsRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final RS7NewsRepository INSTANCE = new RS7NewsRepository();
    }
    //endregion
}
