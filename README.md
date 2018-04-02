# ab-android-intermediate

Android Bootcamp - Android Intermediate
## Lesson 6

  - Network Connection
  - Backendless

## Conectar nuestra app a la nube usando Backendless

Vamos a usar backendless para construir una Base de Datos remota y luego mediante la Rest API, conectarla a nuestra APP. Para este ejemplo vamos a usar Backendless versión 4.x para lo cual pueden revisar este canal en youtube https://www.youtube.com/watch?v=x7IiCEzFbiw&list=PLWRqDbbT5r9APHOn7vXcq-TQr_M1QJ09k

### Paso 1 
Crear nuestra cuenta en backendless https://backendless.com/

### Paso 2 
Crear nuestra App https://help.dropsource.com/docs/documentation/integrating-with-dropsource/api-tools/connect-your-app-to-backendless/

![img00](https://backendless.com/wp-content/uploads/2013/06/js-keys.jpg)

### Paso 3
Crear nuestras tablas seleccionamos la opción "DATA" https://backendless.com/feature-8-create-data-table-schema-in-management-console/
Deben considerar que ya existen algunas tablas predefinidas y otras que puedes crear para tu app. Existen 2 tipos : 

- SYSTEM TABLES

- APP TABLES 

* Revisar este link https://backendless.com/feature-8-create-data-table-schema-in-management-console/

![img01](https://backendless.com/docs/images/business-logic/click-data.jpg)

![img02](https://backendless.com/docs/images/business-logic/new-order-table.jpg)

![img03](https://backendless.com/docs/images/business-logic/new-column-customer-name.jpg)

### Paso 4

REST API 

![img04](https://backendless.com/docs/images/shared/backendless-architecture.png)

Revisar la guia de la REST API https://backendless.com/mobile-developers/quick-start-guide-for-rest-api/ y luego la documentación https://backendless.com/docs/rest/doc.html

![img05](https://backendless.com/wp-content/uploads/2015/11/app-id-rest-key-dashboard.jpg)

Considerar que vamos a manejar ciertas credenciales para poder conectar nuestra app con Backendless

- Application ID

- Rest API Key

### Probando la REST API

Para la autenticación tenemos *User Service API* https://backendless.com/docs/rest/doc.html#dynanchor1
 
 - LogIn https://backendless.com/docs/rest/doc.html#users_login
 
 Método : POST 
 
 URL :
 ```
 /application-id/REST-api-key/users/login
 ```
 
 Request headers
 ```
 Content-Type:application/json
 ```
 
 Request body
 ```
 {
 "login" : value,
 "password" : value,
}
 ```
 
 Response Body:
 
 ```
  {
 "objectId" : value,
 "user-token": value,
 //all user properties (except for password) in the "prop-name":"prop-value" format
 "prop-name1":value,
 "prop-name2":value,
 "prop-name3":value,
 ...
}
 ```
 
 - LogIn en la App

Contamos con una clase llamada *APIClient* donde declaramos las peticiones que vamos a realizar

```
package com.androidbootcamp.notebackendless.storage.network

public class ApiClient {
private static final String API_BASE_URL="https://api.backendless.com/";

@POST("/{applicationid}/{restapikey}/users/login")
        Call<LogInBLResponse> logInBL(@Path("applicationid") String appID,
                                      @Path("restapikey") String restApiKEY, @Body LogInBLRaw raw);

```

Para realizar la llamada desde la app, en la clase *LogInActivity*

```
**
     * Podemos probar con:
     * username : admin@admin.com
     * password: 123456
     */

    /*
        {"code":3003,"message":"Invalid login or password","errorData":{}}
     */
    private void logIn() {
        showLoading();

        final LogInBLRaw logInRaw= new LogInBLRaw();
        logInRaw.setLogin(username);
        logInRaw.setPassword(password);

        Call<LogInBLResponse> call= ApiClient.getMyApiClient().logInBL(
                StorageConstant.APPLICATIONID,
                StorageConstant.RESTAPIKEY,logInRaw);

        call.enqueue(new Callback<LogInBLResponse>() {
            @Override
            public void onResponse(Call<LogInBLResponse> call, Response<LogInBLResponse> response) {
                hideLoading();
                if(response!=null){
                    LogInBLResponse logInResponse=null;

                    if(response.isSuccessful()){
                        logInResponse=response.body();
                        if(logInResponse!=null){
                            saveSession(logInResponse);
                            gotoMain();
                        }
                    }else{
                        //Log.v("CONSOLE", "error "+logInResponse);
                        Log.v("CONSOLE", "error "+response.errorBody());

                        JSONObject jsonObject = null;
                        try {
                            jsonObject=new JSONObject(response.errorBody().string());
                        }catch (Exception e){
                            jsonObject= new JSONObject();
                        }
                        logInResponse= GsonHelper.jsonToLogInBLResponse(jsonObject.toString());
                        showMessage(logInResponse.getMessage());

                    }
                }else{
                   showMessage("Ocurrió un error");
                }
            }

            @Override
            public void onFailure(Call<LogInBLResponse> call, Throwable t) {
                hideLoading();
                showMessage(t.getMessage());
            }
        });

    }
```

- Obtener elementos de una tabla https://backendless.com/docs/rest/doc.html#dynanchor2

Usamos *Data Service API*

Método : GET

URL : 
```
https://api.backendless.com/<application-id>/<REST-api-key>/data/<table-name>
```

Request Headers :

```
user-token:value-of-the-user-token-header-from-login
```

Request body :

```
none
```

Response body :

```
[
	{
		"created": 1520381912645,
		"description": "Esta una nota 2",
		"title": "Nota 2",
		"ownerId": "B0254EE8-CC3E-EDD8-FF5A-423577F08F00",
		"updated": null,
		"objectId": "54A27887-55B1-73AB-FFC7-0BA59F761000",
		"___class": "Note"
	},
	{
		"created": 1510344329602,
		"description": "Subir notas de U2 y U3 en isil",
		"title": "Subir notas",
		"ownerId": null,
		"updated": 1512173912679,
		"objectId": "6484C388-D7FF-65C9-FF1C-934062327900",
		"___class": "Note"
	},
	{
		"created": 1510968214089,
		"description": "Esta es un nota desde backendless",
		"title": "Nota desde BL",
		"ownerId": "B0254EE8-CC3E-EDD8-FF5A-423577F08F00",
		"updated": null,
		"objectId": "788FF40F-00DB-2FB2-FF7F-3C9F630CEA00",
		"___class": "Note"
	},
	{
		"created": 1510970748399,
		"description": "Esta es una nota de prueba desde BL 1",
		"title": "Nota de prueba desde BL 1",
		"ownerId": "B0254EE8-CC3E-EDD8-FF5A-423577F08F00",
		"updated": 1512173925885,
		"objectId": "CBAD6A9E-F26C-04CE-FF76-C2D8A1B84900",
		"___class": "Note"
	},
	{
		"created": 1520381606285,
		"description": "Nota de prueba",
		"title": "Note 1",
		"ownerId": "B0254EE8-CC3E-EDD8-FF5A-423577F08F00",
		"updated": null,
		"objectId": "E4B4B15D-9EDE-675A-FFB7-CA51A0094600",
		"___class": "Note"
	}
]
```

- Listar notas en la app

Primero registramos la petición en la clase *ApiClient*

```
 //https://api.backendless.com/7FBB8DC0-4C21-0178-FF76-367F7D30DC00/E5214A86-653A-529C-FF73-95B4DD4F8C00/data/Note
        @GET("/{applicationid}/{restapikey}/data/Note")
        Call<NotesBLResponse> notesbl(@Path("applicationid") String appID,
                                      @Path("restapikey") String restApiKEY, @HeaderMap Map<String, String> headers);
```

Luego en la vista *NoteListActivity* realizamos la llamada para que nos devuelva el listado de notas

```
private void loadDataBackendless(){
        showLoading();

        String token= PreferencesHelper.getTokenSession(this);

        Map<String, String> map = new HashMap<>();
        map.put("user-token",token);
        Call<NotesBLResponse> call= ApiClient.getMyApiClient().notesbl(
                StorageConstant.APPLICATIONID, StorageConstant.RESTAPIKEY,map);

        call.enqueue(new Callback<NotesBLResponse>() {
            @Override
            public void onResponse(Call<NotesBLResponse> call, Response<NotesBLResponse> response) {
                hideLoading();
                if(response!=null){
                    NotesBLResponse notesResponse=null;
                    if(response.isSuccessful()){
                        notesResponse= response.body();
                        renderNotesBL(notesResponse);
                    }else{
                    }
                }
            }

            @Override
            public void onFailure(Call<NotesBLResponse> call, Throwable t) {
                hideLoading();
                showMessage(t.getMessage());
            }
        });
    }
```

## BackendLess

- Backendless https://backendless.com

- Backendless REST API https://backendless.com/docs/rest/doc.html

- LogIn https://backendless.com/docs/rest/doc.html#users_login

## Referencias

- HeaderMap

https://futurestud.io/tutorials/retrofit-2-dynamic-request-headers-with-headermap

- GSON Annotations

http://www.javacreed.com/gson-annotations-example/

- Postman https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop?hl=es




