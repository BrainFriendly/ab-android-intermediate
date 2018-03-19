
# Room

Room proporciona una capa de abstracción sobre SQLite para permitir el acceso a la base de datos con fluidez y, al mismo tiempo, aprovechar toda la potencia de SQLite

## Componentes

 - Base de datos (DataBase): contiene a la base de datos y sirve como el principal punto de acceso para la conexión a los datos relacionales persistentes de su aplicación.
La clase anotada con @Database debe cumplir las siguientes condiciones:
	 - Sea una clase abstracta que extienda RoomDatabase.
	 - Incluya la lista de entidades asociadas con la base de datos dentro de la anotación.
	 - Contiene un método abstracto que tiene 0 argumentos y devuelve la clase anotada con @Dao.
Para adquirir una instancia de la base de datos se debe llamar a los métodos Room.databaseBuilder () o Room.inMemoryDatabaseBuilder ().
 - Entidad: representa una tabla dentro de la base de datos.
 - DAO: contiene los métodos utilizados para acceder a la base de datos.

![Room architecture diagram](https://github.com/BrainFriendly/ab-android-intermediate/blob/L2-Persistence/images/room_architecture.png)

User.java
```java
@Entity
public class User {
    @PrimaryKey
    private int uid;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    // Getters y setters ignorados por simplicidad,
    // pero son necesarios para que Room trabaje.
	}
```

UserDao.java
```java
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
           + "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
```

AppDatabase.java
```java
@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
```

Luego de crear los archivos previos, la forma de obtener una instancia de la BD es la siguiente:
```java
AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "database-name").build();
```

## Como agregarlo al proyecto?
Agregar las siguientes lineas al build.gradle de la aplicación
```groovy
dependencies {
    implementation "android.arch.persistence.room:runtime:1.0.0"
    annotationProcessor "android.arch.persistence.room:compiler:1.0.0"
}
```

## Bibliografía
https://developer.android.com/training/data-storage/room/index.html
