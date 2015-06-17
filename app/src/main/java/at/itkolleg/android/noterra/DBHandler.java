package at.itkolleg.android.noterra;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.security.Timestamp;

/**
 * Created by SandroB on 26.05.2015.
 */
public class DBHandler extends SQLiteOpenHelper {
    private int countBegehung = 1;
    private static final String DATABASE_NAME = "forstDB";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase forstDB;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        forstDB = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase forstDB) {
        this.forstDB = forstDB;

        try {

            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Formular';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Gps';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Holzablagerung';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Holzbewuchs';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_OhneBehinderung';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_SchadenAnRegulierung';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Sprachaufnahme';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Sprachaufnahme';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Text';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_WasserAusEinleitung';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Foto';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Abflussbehinderung';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Ablagerung';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Notiz';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Beobachtung';");

            //Erstellen der Tabelle tbl_Formular in der Datenbank forst_db
            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_Formular' (" +
                    "'idFormular' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL CHECK('idFormular'>=0)," +
                    "'Gemeinde' VARCHAR(45) DEFAULT NULL," +
                    "'Zeit' TIMESTAMP DEFAULT NULL," +
                    "'Kosten' INTEGER CHECK('Kosten'>=0) DEFAULT NULL," +
                    "'Massnahme' TEXT DEFAULT NULL," +
                    "'Prioritaet' VARCHAR(45) DEFAULT NULL," +
                    "'Foerderfaehig' INTEGER DEFAULT NULL," +
                    "'Abwicklung' VARCHAR(45) DEFAULT NULL," +
                    "'Absturzsicherung' INTEGER DEFAULT 0," +
                    "'BaumFaellen' INTEGER DEFAULT 0," +
                    "'BauwerkSanieren' INTEGER DEFAULT 0," +
                    "'Bauwe rkWarten' INTEGER DEFAULT 0," +
                    "'DurchlassFreilegen' INTEGER DEFAULT 0," +
                    "'GenemigungPruefen' INTEGER DEFAULT 0," +
                    "'HindernisseEntfernen' INTEGER DEFAULT 0," +
                    "'HindernissSprengen' INTEGER DEFAULT 0," +
                    "'HolzAblaengen' INTEGER DEFAULT 0," +
                    "'KeineMassnahme' INTEGER DEFAULT 0," +
                    "'SperreOdGerinneRaumen' INTEGER DEFAULT 0," +
                    "'UferSichern' INTEGER DEFAULT 0," +
                    "'AZustandBeobachten' INTEGER DEFAULT 0" +
                    ");");

            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_Gps'(" +
                    "'idGps' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL CHECK('idGps'>=0)," +
                    "'Laenge' VARCHAR(20) DEFAULT NULL," +
                    "'Breite' VARCHAR(20) DEFAULT NULL," +
                    "'Toleranz' INTEGER CHECK('Toleranz'>=0) DEFAULT NULL" +
                    ");");

            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_Holzablagerung'(" +
                    "'idHolzablagerung' INTEGER PRIMARY KEY NOT NULL CHECK('idHolzablagerung'>=0)," +
                    "'AnzahlStaemme' INTEGER CHECK('AnzahlStaemme'>=0)," +
                    "'Baumart' VARCHAR(45) DEFAULT NULL," +
                    "'Media' INTEGER CHECK('Media'>=0) DEFAULT NULL," +
                    "'Holzmenge' INTEGER CHECK('Holzmenge'>=0) DEFAULT NULL," +
                    "'Bachabschnitt' INTEGER CHECK('Bachabschnitt'>=0) DEFAULT NULL," +
                    "CONSTRAINT 'fk_tbl_Holzablagerung_tbl_Formular1'" +
                    "FOREIGN KEY('idHolzablagerung')" +
                    "REFERENCES 'tbl_Formular'('idFormular')" +
                    ");");

            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_Holzbewuchs'(" +
                    "'idHolzbewuchs' INTEGER PRIMARY KEY NOT NULL CHECK('idHolzbewuchs'>=0)," +
                    "'Anzahl' INTEGER CHECK('Anzahl'>=0) DEFAULT NULL," +
                    "'Baumart' VARCHAR(45) DEFAULT NULL," +
                    "'Hoehe' INTEGER CHECK('Hoehe'>=0) DEFAULT NULL," +
                    "'Menge' INTEGER CHECK('Menge'>=0) DEFAULT NULL," +
                    "'Beschreibung' TEXT DEFAULT NULL," +
                    "CONSTRAINT 'fk_tbl_Holzbewuchs_tbl_Formular1'" +
                    "FOREIGN KEY('idHolzbewuchs')" +
                    "REFERENCES 'tbl_Formular'('idFormular')" +
                    ");");

            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_OhneBehinderung'(" +
                    "'idOhneBehinderung' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL CHECK('idOhneBehinderung'>=0)," +
                    "'Art' TEXT DEFAULT NULL," +
                    "'Beschreibung' TEXT DEFAULT NULL," +
                    "CONSTRAINT 'fk_tbl_OhneBehinderung_tbl_Formular1'" +
                    "FOREIGN KEY('idOhneBehinderung')" +
                    "REFERENCES 'tbl_Formular'('idFormular')" +
                    ");");

            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_SchadenAnRegulierung'(" +
                    "'idSchadenAnRegulierung' INTEGER PRIMARY KEY NOT NULL CHECK('idSchadenAnRegulierung'>=0)," +
                    "'Art' VARCHAR(255) DEFAULT NULL," +
                    "'Hoehe' INTEGER CHECK('Hoehe'>=0) DEFAULT NULL," +
                    "'FehlendeAbsturzsicherung' INTEGER DEFAULT 0," +
                    "'Ausgang Sperrenfluegel' INTEGER DEFAULT 0," +
                    "'Geschiebesperre' INTEGER DEFAULT 0," +
                    "'Risse' INTEGER DEFAULT 0," +
                    "'SchadhaftesMauerwerk' INTEGER DEFAULT 0," +
                    "'Sonstiges' INTEGER DEFAULT 0," +
                    "'Bewuchs' INTEGER DEFAULT 0," +
                    "'UnterspueltesFundament' INTEGER DEFAULT 0," +
                    "CONSTRAINT 'fk_tbl_SchadenAnRegulierung_tbl_Formular1'" +
                    "FOREIGN KEY('idSchadenAnRegulierung')" +
                    "REFERENCES 'tbl_Formular'('idFormular')" +
                    ");");

            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_Sprachaufnahme'(" +
                    "'idSprachaufnahme' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL CHECK('idSprachaufnahme'>=0)," +
                    "'Laenge' FLOAT DEFAULT NULL," +
                    "'Ref' TEXT DEFAULT NULL" +
                    ");");

            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_Text'(" +
                    "'idText' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL CHECK('idText'>=0)," +
                    "'Text' TEXT DEFAULT NULL" +
                    ");");

            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_WasserAusEinleitung'(" +
                    "'idWasserAusEinleitung' INTEGER PRIMARY KEY NOT NULL CHECK('idWasserAusEinleitung'>=0)," +
                    "'Art' VARCHAR(45) DEFAULT NULL," +
                    "'Zweck' VARCHAR(45) DEFAULT NULL," +
                    "'Beschreibung' TEXT DEFAULT NULL," +
                    "CONSTRAINT 'fk_tbl_WasserAusEinleitung_tbl_Formular1'" +
                    "FOREIGN KEY('idWasserAusEinleitung')" +
                    "REFERENCES 'tbl_Formular'('idFormular')" +
                    ");");

            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_Foto'(" +
                    "'idFoto' INTEGER PRIMARY KEY NOT NULL CHECK('idFoto'>=0)," +
                    "'Ref' TEXT" +
                    ");");

            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_Abflussbehinderung'(" +
                    "'idAbflussbehinderung' INTEGER PRIMARY KEY NOT NULL CHECK('idAbflussbehinderung'>=0)," +
                    "'Art' TEXT DEFAULT NULL," +
                    "'Beschreibung' TEXT DEFAULT NULL," +
                    " CONSTRAINT 'fk_tbl_Abflussbehinderung_tbl_Formular1'" +
                    "FOREIGN KEY('idAbflussbehinderung')" +
                    "REFERENCES 'tbl_Formular'('idFormular')" +
                    ");");

            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_Ablagerung'(" +
                    "'idAblagerung' INTEGER PRIMARY KEY NOT NULL CHECK('idAblagerung'>=0)," +
                    "'Art' VARCHAR(45) DEFAULT NULL," +
                    "'Beschreibung' TEXT DEFAULT NULL," +
                    "'Bachabschnitt' INTEGER CHECK('Bachabschnitt'>=0) DEFAULT NULL," +
                    "'Ausmass' INTEGER CHECK('Ausmass'>=0) DEFAULT NULL," +
                    "CONSTRAINT 'fk_tbl_Ablagerung_tbl_Formular1'" +
                    "FOREIGN KEY('idAblagerung')" +
                    "REFERENCES 'tbl_Formular'('idFormular')" +
                    ");");

            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_Notiz'(" +
                    "'idNotiz' INTEGER PRIMARY KEY NOT NULL CHECK('idNotiz'>=0)," +
                    "'tbl_Sprachaufnahme_idSprachaufnahme' INTEGER CHECK('tbl_Sprachaufnahme_idSprachaufnahme'>=0)," +
                    "'tbl_Foto_idFoto' INTEGER CHECK('tbl_Foto_idFoto'>=0)," +
                    "'tbl_Text_idText' INTEGER CHECK('tbl_Text_idText'>=0)," +
                    "CONSTRAINT 'fk_tbl_Notiz_tbl_Sprachaufnahme'" +
                    "FOREIGN KEY('tbl_Sprachaufnahme_idSprachaufnahme')" +
                    "REFERENCES 'tbl_Sprachaufnahme'('idSprachaufnahme')" +
                    "CONSTRAINT 'fk_tbl_Notiz_tbl_Foto1'" +
                    "FOREIGN KEY('tbl_Foto_idFoto')" +
                    "REFERENCES 'tbl_Foto'('idFoto')" +
                    "CONSTRAINT 'fk_tbl_Notiz_tbl_Text1'" +
                    "FOREIGN KEY('tbl_Text_idText')" +
                    "REFERENCES 'tbl_Text'('idText')" +
                    ");");


            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_Beobachtung'(" +
                    "'idBeobachtung' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL CHECK('idBeobachtung'>=0)," +
                    "'tbl_Notiz_idNotiz' INTEGER CHECK('tbl_Notiz_idNotiz'>=0)," +
                    "'tbl_Gps_idGps' INTEGER CHECK('tbl_Gps_idGps'>=0)," +
                    "'tbl_Formular_idFormular' INTEGER CHECK('tbl_Formular_idFormular'>=0)," +
                    "'Zeit' TIMESTAMP DEFAULT NULL," +
                    "'Begehungsnummer' INTEGER NOT NULL," +
                    "'CONSTRAINT 'fk_tbl_Beobachtung_tbl_Notiz1'" +
                    "'FOREIGN KEY('tbl_Notiz_idNotiz')" +
                    "'REFERENCES 'tbl_Notiz'('idNotiz')" +
                    "'CONSTRAINT 'fk_tbl_Beobachtung_tbl_Gps1'" +
                    "'FOREIGN KEY('tbl_Gps_idGps')" +
                    "'REFERENCES 'tbl_Gps'('idGps')" +
                    "'CONSTRAINT 'fk_tbl_Beobachtung_tbl_Formular1'" +
                    "'FOREIGN KEY('tbl_Formular_idFormular')" +
                    "'REFERENCES 'tbl_Formular'('idFormular')" +
                    ");");

            forstDB.execSQL("CREATE INDEX 'tbl_Holzablagerung.fk_tbl_Holzablagerung_tbl_Formular1_idx' ON 'tbl_Holzablagerung'('idHolzablagerung');");
            forstDB.execSQL("CREATE INDEX 'tbl_Holzbewuchs.fk_tbl_Holzbewuchs_tbl_Formular1_idx' ON 'tbl_Holzbewuchs' ('idHolzbewuchs');");
            forstDB.execSQL("CREATE INDEX 'tbl_OhneBehinderung.fk_tbl_OhneBehinderung_tbl_Formular1_idx' ON 'tbl_OhneBehinderung'('idOhneBehinderung');");
            forstDB.execSQL("CREATE INDEX 'tbl_SchadenAnRegulierung.fk_tbl_SchadenAnRegulierung_tbl_Formular1_idx' ON 'tbl_SchadenAnRegulierung'('idSchadenAnRegulierung');");
            forstDB.execSQL("CREATE INDEX 'tbl_WasserAusEinleitung.fk_tbl_WasserAusEinleitung_tbl_Formular1_idx' ON 'tbl_WasserAusEinleitung'('idWasserAusEinleitung');");
            forstDB.execSQL("CREATE INDEX 'tbl_Abflussbehinderung.fk_tbl_Abflussbehinderung_tbl_Formular1_idx' ON 'tbl_Abflussbehinderung'('idAbflussbehinderung');");
            forstDB.execSQL("CREATE INDEX 'tbl_Ablagerung.fk_tbl_Ablagerung_tbl_Formular1_idx' ON 'tbl_Ablagerung'('idAblagerung');");
            forstDB.execSQL("CREATE INDEX 'tbl_Notiz.fk_tbl_Notiz_tbl_Sprachaufnahme_idx' ON 'tbl_Notiz'('tbl_Sprachaufnahme_idSprachaufnahme');");
            forstDB.execSQL("CREATE INDEX 'tbl_Notiz.fk_tbl_Notiz_tbl_Foto1_idx' ON 'tbl_Notiz'('tbl_Foto_idFoto');");
            forstDB.execSQL("CREATE INDEX 'tbl_Notiz.fk_tbl_Notiz_tbl_Text1_idx' ON 'tbl_Notiz'('tbl_Text_idText');");
            forstDB.execSQL("CREATE INDEX 'tbl_Beobachtung.fk_tbl_Beobachtung_tbl_Notiz1_idx' ON 'tbl_Beobachtung'('tbl_Notiz_idNotiz');");
            forstDB.execSQL("CREATE INDEX 'tbl_Beobachtung.fk_tbl_Beobachtung_tbl_Gps1_idx' ON 'tbl_Beobachtung'('tbl_Gps_idGps');");
            forstDB.execSQL("CREATE INDEX 'tbl_Beobachtung.fk_tbl_Beobachtung_tbl_Formular1_idx' ON 'tbl_Beobachtung'('tbl_Formular_idFormular');'");

        } catch (Exception e) {
            Log.e("Error", "Error", e);
        } finally {
            if (forstDB != null) {
                forstDB.close();
            }
        }
    }

    public void addBeobachtung(Timestamp time){
        forstDB = this.getWritableDatabase();
        String begehungsDatum = time.toString();

        ContentValues values = new ContentValues();
        values.put("Zeit", begehungsDatum);
        values.put("Begehungsnummer", countBegehung);

        forstDB.insert("tbl_Beobachtung", null, values);
        forstDB.close();

        countBegehung++;
    }

    public void addImageRef(String imagePath){
        forstDB = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Ref", imagePath);

        forstDB.insert("tbl_Foto", null, values);
        forstDB.close();
    }

    public void addAudioRef(String audioPath){
        forstDB = this.getWritableDatabase();
        forstDB = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("Ref", audioPath);

        //Cursor c = forstDB.rawQuery("SELECT id FROM tbl_Sprachaufnahme ORDER BY id DESC",null);

        forstDB.insert("tbl_Sprachaufnahme", null, values);
        forstDB.close();
    }

    public void addNoteText(String text){
        forstDB = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Text", text);

        forstDB.insert("tbl_Text", null, values);
        forstDB.close();
    }

    public void addCoordinates(String laengengrad, String breitengrad){
        forstDB = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Laenge", laengengrad);
        values.put("Breite", breitengrad);

        forstDB.insert("tbl_Gps", null, values);
        forstDB.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase forstDB, int oldVersion, int newVersion) {
        forstDB = this.getWritableDatabase();
        this.forstDB = forstDB;

        try {
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Formular';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Gps';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Holzablagerung';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Holzbewuchs';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_OhneBehinderung';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_SchadenAnRegulierung';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Sprachaufnahme';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Sprachaufnahme';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Text';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_WasserAusEinleitung';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Foto';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Abflussbehinderung';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Ablagerung';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Notiz';");
            forstDB.execSQL("DROP TABLE IF EXISTS 'tbl_Beobachtung';");
        } catch (Exception e) {
            Log.e("Error", "Error", e);
        } finally {
            if (forstDB != null) {
                forstDB.close();
            }
        }

    }
}
