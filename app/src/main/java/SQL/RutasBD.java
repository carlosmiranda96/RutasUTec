package SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.StringBuilderPrinter;

/**
 * Created by julio on 25/05/2017.
 */

public class RutasBD extends SQLiteOpenHelper {

    private final static  int DATABASE_VERRSION=1;
    private final static String DATABASE_NAME="rutasUtec.sql";
    //tabla coordenadas
    private final static String TABLA_COORDENADAS="coordenadas";
    private final static String COLUMNA_IdCoordenada="idCoordenada";
    private final static String COLUMNA_ordenRuta="ordenRuta";
    private final static String COLUMNA_LATITUD="latitud";
    private final static String COLUMNA_LONGITUD="longitud";
    private final static String COLUMNA_idRuta="idRuta";
    private String CREAR_TABLA_COORDENADAS="CREATE TABLE "+TABLA_COORDENADAS+"("
            +COLUMNA_IdCoordenada+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +COLUMNA_ordenRuta+" INTEGER,"
            +COLUMNA_LATITUD+" REAL,"
            +COLUMNA_LONGITUD+" REAL,"
            +COLUMNA_idRuta+" INTEGER"
            +" FOREIGN KEY"+COLUMNA_idRuta+" REFERENCES"+TABLA_RUTAS+"("+COLUMNA_idruta+")"
            +")";
    private String DROP_TABLA_COORDENADAS="DROP TABLE IF EXISTS"+TABLA_COORDENADAS;
    //tabla rutas
    private final static String TABLA_RUTAS="rutas";
    private final static String COLUMNA_idruta="idruta";
    private final static String COLUMNA_ruta="ruta";
    private final static String COLUMNA_idtipoRuta="idtipoRuta";
    private String CREAR_TABLA_RUTAS="CREATE TABLE "+TABLA_RUTAS+"("+COLUMNA_idruta+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +COLUMNA_ruta+" TEXT,"
            +COLUMNA_idtipoRuta+" INTEGER"+
            " FOREIGN KEY"+COLUMNA_idtipoRuta+" REFERENCES"+TABLA_tipoRutas+"("+COLUMNA_idtipo+")"+")";
    private String DROP_TABLA_RUTAS="DROP TABLE IF EXISTS"+TABLA_RUTAS;
    //tabla tipoRutas
    private final static String TABLA_tipoRutas="tipoRutas";
    private final static String COLUMNA_idtipo="idtipo";
    private final static String COLUMNA_tipo="tipo";
    private String CREAR_TABLA_TIPORUTAS="CREATE TABLE "+TABLA_tipoRutas+"("+COLUMNA_idtipo+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +COLUMNA_tipo+" TEXT "+")";
    private String DROP_TABLA_tipoRutas="DROP TABLE IF EXISTS"+TABLA_tipoRutas;
    public RutasBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //insertar datos tabla tiporutas
        db.execSQL("insert into tipoRutas values ('michobus')");
        //insertar datos tabla rutas
        db.execSQL("insert into rutas values ('29 H', 1)");
        db.execSQL("insert into rutas values ('29 F', 1)");
        db.execSQL("insert into rutas values ('101 B', 1)");
        db.execSQL("insert into rutas values ('42', 1)");
        db.execSQL("insert into rutas values ('38 F', 1)");
        db.execSQL("insert into rutas values ('3 ', 1)");
        db.execSQL("insert into rutas values ('9', 1)");
        //insertar datos tabla coordenadas
        db.execSQL("insert into coordenadas (1,13.700998301054618,-89.20119255781174,2)");
        db.execSQL("insert into coordendas (2,13.701983324177773,-89.21130985021591,2)");
        db.execSQL("insert into coordenadas (3,13.703963781453186,-89.21111673116684,2)");
        db.execSQL("insert into coordenadas (4,13.703890817533924,-89.21279579401016,2)");
        db.execSQL("insert into coordenadas (5,13.703729,-89.213818,2)");
        db.execSQL("insert into coordenadas (6,13.703917,-89.214773,2)");
        db.execSQL("insert into coordenadas (7,13.704073,-89.215502,2)");
        db.execSQL("insert into coordenadas (8,13.705011,-89.215084,2)");
        db.execSQL("insert into coordenadas (9,13.705814,-89.214365,2)");
        db.execSQL("insert into coordenadas (10,13.705209,-89.213431,2)");
        db.execSQL("insert into coordenadas (11,13.70499,-89.21282,2)");
        db.execSQL("insert into coordenadas (12,13.704772,-89.211093,2)");
        db.execSQL("insert into coordenadas (13,13.704313,-89.210867,2)");
        db.execSQL("insert into  coordenadas (14, 13.703896,-89.211007,2)");
        db.execSQL("insert into coordenadas (15,13.703364,-89.20958,2)");
        db.execSQL("insert into coordenadas (16,13.702833,-89.208464,2)");
        db.execSQL("insert into coordenadas (17,13.69982,-89.2087,2)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLA_COORDENADAS);
        db.execSQL(DROP_TABLA_RUTAS);
        db.execSQL(DROP_TABLA_tipoRutas);
        onCreate(db);
    }
}
