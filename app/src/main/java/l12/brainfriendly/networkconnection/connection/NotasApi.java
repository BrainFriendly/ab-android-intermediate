package l12.brainfriendly.networkconnection.connection;

import l12.brainfriendly.networkconnection.model.Nota;
import l12.brainfriendly.networkconnection.model.NotasResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author pjohnson on 26/03/18.
 */

public interface NotasApi {

    @GET("notes/")
    Call<NotasResponse> obtenerNotas();

    @POST("notes/register")
    Call<Nota> registrarNota(@Body Nota nota);

    @DELETE("notes/{id}")
    Call<Void> eliminarNota(@Path("id") String id);

}
