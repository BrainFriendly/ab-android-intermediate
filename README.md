# ab-android-intermediate
Android Bootcamp - Android Intermediate

## Lesson 3

- Ejercicios

  - SharedPreferences (SPExercises)
  
  - Base de Datos Local SQLite ( DBExercises)
  
## SharedPreferences

- Inicializar SharedPreferences

```java
Context context = getActivity();
SharedPreferences sharedPref = context.getSharedPreferences(
        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
```
- Guardar un valor

```java
SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
SharedPreferences.Editor editor = sharedPref.edit();
editor.putInt(getString(R.string.saved_high_score_key), newHighScore);
editor.commit();
```

- Obtener un valor
```java
SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
int defaultValue = getResources().getInteger(R.integer.saved_high_score_default_key);
int highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), defaultValue);
```

## Referencias

- Save Key-Value Data with SharedPreferences https://developer.android.com/training/notas-storage/shared-preferences.html#java

- Save Data using SQLite https://developer.android.com/training/notas-storage/sqlite.html



