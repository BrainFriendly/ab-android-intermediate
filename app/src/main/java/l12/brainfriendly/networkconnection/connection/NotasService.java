package l12.brainfriendly.networkconnection.connection;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author pjohnson on 26/03/18.
 */

public class NotasService {

    private static NotasApi service;

    private NotasService() {

    }

    public static NotasApi getNotasApi() {

        if (service == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://obscure-earth-55790.herokuapp.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            service = retrofit.create(NotasApi.class);
        }
        return service;
    }
}
