# Ubicación usando los servicios de Google

Los APIs de ubicación disponibles con los servicios de Google Play facilitan el adicionarle la funcionalidad de ubicación a una aplicación. Es preferible usar estos APIs que los que provee el *framework* de Android.

## Preparar el proyecto para usar los servicios de google
Para desarrollar una aplicación que pueda trabajar con los APIs de los servicios de Google Play se necesita configurar el proyecto con el SDK de los servicios de Google Play. Para esto es necesario instalar el SDK de la siguiente manera:

 - Abre Android Studio
 - Abrir el menu Tools y clickear SDK Manager
 - Actualizar el SDK Manager de Android Studio, click en SDK Tools, expandir Support Repository, seleccionar Google Repository y luego click en OK.

Luego de esto debemos agregar las dependencias al proyecto:

 - Abrir el build.gradle del módulo.
 - Agregar la dependencia al bloque de dependencies
```groovie
dependencies {
        compile 'com.google.android.gms:play-services:10.2.0'
    }
```
 - Guardar los cambios y sincronizar el proyecto

## Trabajar con la ultima ubicación conocida
Para obtener la ultima ubicación conocida del usuario debemos usar el proveedor de ubicación *fused*. Este proveedor encapsula la parte técnica y provee una API bastante simple con la cual se puede especificar requerimientos a alto nivel como alta precisión o poco consumo. También optimiza el uso de la batería del dispositivo.


## Cómo obtener la última posición conocida del usuario?
Inicializamos el proveedor Fused y luego llamamos al metodo getLastLocation() y le agregamos un successListener.
La precisión obtenida de esta llamada está determinada por el permiso que hayamos solicitado en el manifest (o runtime).

```java
public class MainActivity extends ActionBarActivity implements
        ConnectionCallbacks, OnConnectionFailedListener {
    ...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	.
	.
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
	mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
		    updateUI(location);
                    // Logic to handle location object
                }
            }
        });
	.
	.
   }
}
```
El método onSuccess() trae un parametro Location del cual se puede obtener la latitud y longitud del dispositivo de usuario. El objeto obtenido puede ser nulo en algunos casos.


## Configurando las actualizaciones de ubicación

Para configurar las actualizaciones es necesario crear un `LocationRequest`. En el es posible configurar el intervalo de actualización, el máximo intervalo de actualización y la prioridad.

 - Intervalo de actualización: Se configura a través del método setInterval(). Este método especifica el ratio en milisegundos el cual la aplicación prefiere obtener actualizaciones. 
 - Máximo intervalo de actualización: Se configura a través del método setFastestInterval(). Este método especifica el máximo ratio en milisegundos el cual la aplicación puede manejar los updates que recibe.
 - Prioridad: Se configura a través del método setPriority(). Este método sirve para indicar a los servicios de Google que fuente de información de ubicación utilizar.
Los siguientes son los valores soportados:
	 - PRIORITY_BALANCED_POWER_ACCURACY: Utiliza este valor para especificar que se desea un nivel de precision de aproximadamente 100 metros. Este es considerado un nivel de precisión relativamente bajo por lo que consume poca batería.
	 - PRIORITY_HIGH_ACCURACY: Utiliza este valor para solicitar la máxima precisión posible. 
	 - PRIORITY_LOW_POWER: Utiliza este valor para obtener un valor de precisión a nivel de ciudad lo cual es aproximadamente 10 kilómetros.
	 - PRIORITY_NO_POWER: Utiliza este valor si no deseas crear un impacto en el consumo de batería pero a la vez deseas recibir actualizaciones de ubicación cuando sea posible. Con este valor la aplicación no pedirá ninguna actualización pero las recibirá cuando otra aplicación lo haga.
```java
protected void createLocationRequest() {
    LocationRequest mLocationRequest = new LocationRequest();
    mLocationRequest.setInterval(10000);
    mLocationRequest.setFastestInterval(5000);
    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
}
```

## Pedir al usuario que cambie la configuración de ubicación
Para verificar si las configuración de la ubicación es apropiada para el request que hemos generado creamos un `task` que se encargara de verificar si los settings del dispositivo cumplen con las necesidades de nuestro request.
Agregamos dos listeners a nuestro task  `OnSuccessListener` y  `OnFailureListener`. Recibiremos una respuesta por el primero si es que los settings satisfacen al request creado. De lo contrario recibiremos una respuesta por el segundo listener la cual manejaremos para plantearle al usuario una solucion

```java
   private void checkLocationSettings() {
        if (mLocationRequest == null) {
            createLocationRequest();
        }
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                startLocationUpdates();
            }
        });
        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(GoogleLocationActivity.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }
```

## Activar las actualizaciones de ubicación
Para activar las actualizaciones de ubicación es necesario llamar al método requestLocationUpdates utilizando el LocationRequest que creamos previamente.

```java
    protected void startLocationUpdates() {        
	mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }
```
El segundo parámetro sera un un LocationCallback el cual tendremos que implementar para manejar las respuestas.

```java
	mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                    updateUI(location);
                    break;
                }
            }

            ;
        };
```

## Referencias

 - https://developers.google.com/android/guides/setup
 - https://developer.android.com/training/location/index.html
