package at.itkolleg.android.noterra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.UUID;

/**
 * Created by SandroB on 26.05.2015.
 */
public class DBHandler extends SQLiteOpenHelper {
    private int countBegehung = 1;
    private String uuid;
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
                    "'idFormular' VARCHAR(38) PRIMARY KEY NOT NULL," +
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
                    "'BauwerkWarten' INTEGER DEFAULT 0," +
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
                    "'idGps' VARCHAR(38) PRIMARY KEY NOT NULL," +
                    "'Laenge' VARCHAR(20) DEFAULT NULL," +
                    "'Breite' VARCHAR(20) DEFAULT NULL," +
                    "'Toleranz' INTEGER CHECK('Toleranz'>=0) DEFAULT NULL" +
                    ");");

            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_Holzablagerung'(" +
                    "'idHolzablagerung' VARCHAR(38) PRIMARY KEY NOT NULL," +
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
                    "'idHolzbewuchs' VARCHAR(38) PRIMARY KEY NOT NULL," +
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
                    "'idOhneBehinderung' VARCHAR(38) PRIMARY KEY NOT NULL," +
                    "'Art' TEXT DEFAULT NULL," +
                    "'Beschreibung' TEXT DEFAULT NULL," +
                    "CONSTRAINT 'fk_tbl_OhneBehinderung_tbl_Formular1'" +
                    "FOREIGN KEY('idOhneBehinderung')" +
                    "REFERENCES 'tbl_Formular'('idFormular')" +
                    ");");

            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_SchadenAnRegulierung'(" +
                    "'idSchadenAnRegulierung' VARCHAR(38) PRIMARY KEY NOT NULL," +
                    "'Art' VARCHAR(255) DEFAULT NULL," +
                    "'Hoehe' INTEGER CHECK('Hoehe'>=0) DEFAULT NULL," +
                    "'FehlendeAbsturzsicherung' INTEGER DEFAULT 0," +
                    "'AusgangSperrenfluegel' INTEGER DEFAULT 0," +
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
                    "'idSprachaufnahme' VARCHAR(38) PRIMARY KEY NOT NULL," +
                    "'Laenge' FLOAT DEFAULT NULL," +
                    "'Ref' TEXT DEFAULT NULL" +
                    ");");

            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_Text'(" +
                    "'idText' VARCHAR(38) PRIMARY KEY NOT NULL," +
                    "'Text' TEXT DEFAULT NULL" +
                    ");");

            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_WasserAusEinleitung'(" +
                    "'idWasserAusEinleitung' VARCHAR(38) PRIMARY KEY NOT NULL," +
                    "'Art' VARCHAR(45) DEFAULT NULL," +
                    "'Zweck' VARCHAR(45) DEFAULT NULL," +
                    "'Beschreibung' TEXT DEFAULT NULL," +
                    "CONSTRAINT 'fk_tbl_WasserAusEinleitung_tbl_Formular1'" +
                    "FOREIGN KEY('idWasserAusEinleitung')" +
                    "REFERENCES 'tbl_Formular'('idFormular')" +
                    ");");

            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_Foto'(" +
                    "'idFoto' VARCHAR(38) PRIMARY KEY NOT NULL," +
                    "'Ref' TEXT" +
                    ");");

            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_Abflussbehinderung'(" +
                    "'idAbflussbehinderung' VARCHAR(38) PRIMARY KEY NOT NULL," +
                    "'Art' TEXT DEFAULT NULL," +
                    "'Beschreibung' TEXT DEFAULT NULL," +
                    " CONSTRAINT 'fk_tbl_Abflussbehinderung_tbl_Formular1'" +
                    "FOREIGN KEY('idAbflussbehinderung')" +
                    "REFERENCES 'tbl_Formular'('idFormular')" +
                    ");");

            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_Ablagerung'(" +
                    "'idAblagerung' VARCHAR(38) PRIMARY KEY NOT NULL," +
                    "'Art' VARCHAR(45) DEFAULT NULL," +
                    "'Beschreibung' TEXT DEFAULT NULL," +
                    "'Bachabschnitt' INTEGER CHECK('Bachabschnitt'>=0) DEFAULT NULL," +
                    "'Ausmass' INTEGER CHECK('Ausmass'>=0) DEFAULT NULL," +
                    "CONSTRAINT 'fk_tbl_Ablagerung_tbl_Formular1'" +
                    "FOREIGN KEY('idAblagerung')" +
                    "REFERENCES 'tbl_Formular'('idFormular')" +
                    ");");

            forstDB.execSQL("CREATE TABLE IF NOT EXISTS 'tbl_Notiz'(" +
                    "'idNotiz' VARCHAR(38) PRIMARY KEY NOT NULL," +
                    "'tbl_Sprachaufnahme_idSprachaufnahme' VARCHAR(38) CHECK('tbl_Sprachaufnahme_idSprachaufnahme'>=0)," +
                    "'tbl_Foto_idFoto' VARCHAR(38) CHECK('tbl_Foto_idFoto'>=0)," +
                    "'tbl_Text_idText' VARCHAR(38) CHECK('tbl_Text_idText'>=0)," +
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
                    "'idBeobachtung' VARCHAR(38) PRIMARY KEY NOT NULL," +
                    "'tbl_Notiz_idNotiz' VARCHAR(38) CHECK('tbl_Notiz_idNotiz'>=0)," +
                    "'tbl_Gps_idGps' VARCHAR(38) CHECK('tbl_Gps_idGps'>=0)," +
                    "'tbl_Formular_idFormular' VARCHAR(38) CHECK('tbl_Formular_idFormular'>=0)," +
                    "'Zeit' VARCHAR(30) DEFAULT NULL," +
                    "'Begehungsnummer' INTEGER NOT NULL," +
                    "CONSTRAINT 'fk_tbl_Beobachtung_tbl_Notiz1'" +
                    "FOREIGN KEY('tbl_Notiz_idNotiz')" +
                    "REFERENCES 'tbl_Notiz'('idNotiz')" +
                    "CONSTRAINT 'fk_tbl_Beobachtung_tbl_Gps1'" +
                    "FOREIGN KEY('tbl_Gps_idGps')" +
                    "REFERENCES 'tbl_Gps'('idGps')" +
                    "CONSTRAINT 'fk_tbl_Beobachtung_tbl_Formular1'" +
                    "FOREIGN KEY('tbl_Formular_idFormular')" +
                    "REFERENCES 'tbl_Formular'('idFormular')" +
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
                //forstDB.close();
            }
        }
    }

    public String getRandomUUID(){
        uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }

    public void addBeobachtung(String time){
        forstDB = this.getWritableDatabase();
        String begehungsDatum = time.toString();
        String begehungsID = getRandomUUID();

        ContentValues values = new ContentValues();
        values.put("idBeobachtung", begehungsID);
        values.put("Zeit", begehungsDatum);
        values.put("Begehungsnummer", countBegehung);

        forstDB.insert("tbl_Beobachtung", null, values);

        countBegehung++;
    }

    public void addFormular(String gemeinde, String zeit, Integer kosten, String massnahmen, String prioritaet,
                            int foerderfaehig, String abwicklung, int absturzsicherung, int baumFaellen, int bauwerkSanieren,
                            int bauwerkWarten, int durchlassFreilegen, int genemigungPruefen, int hindernissEntfernen, int hindernissSprengen,
                            int holzAblaegen, int keineMassnahme, int sperreOdGerinneRaumen, int uferSichern, int zustandBeobachten){

        forstDB = this.getWritableDatabase();
        String formularID = getRandomUUID();

        String beobachtungsID = getIDfromTable("tbl_Beobachtung", "idBeobachtung");

        ContentValues values = new ContentValues();
        values.put("idFormular", formularID);
        values.put("Gemeinde", gemeinde);
        values.put("Zeit", zeit);
        values.put("Kosten", kosten);
        values.put("Massnahme", massnahmen);
        values.put("Prioritaet", prioritaet);
        values.put("Foerderfaehig", foerderfaehig);
        values.put("Abwicklung", abwicklung);
        // Int mitgeben 0 oder 1 ob gesetzt oder nicht gesetzt!
        values.put("Absturzsicherung", absturzsicherung);
        values.put("BaumFaellen", baumFaellen);
        values.put("BauwerkSanieren", bauwerkSanieren);
        values.put("BauwerkWarten", bauwerkWarten);
        values.put("DurchlassFreilegen", durchlassFreilegen);
        values.put("GenemigungPruefen", genemigungPruefen);
        values.put("HindernisseEntfernen", hindernissEntfernen);
        values.put("HindernissSprengen", hindernissSprengen);
        values.put("HolzAblaengen", holzAblaegen);
        values.put("KeineMassnahme", keineMassnahme);
        values.put("SperreOdGerinneRaumen", sperreOdGerinneRaumen);
        values.put("UferSichern", uferSichern);
        values.put("ZustandBeobachten", zustandBeobachten);

        ContentValues setFormularID = new ContentValues();
        setFormularID.put("tbl_Formular_idFormular", formularID);

        forstDB.insert("tbl_Formular", null, values);
        forstDB.update("tbl_Beobachtung", setFormularID, "idBeobachtung = '" + beobachtungsID + "'", null);
    }

    public void addHolzablagerung(int anzahlStaemme, String baumart, int media, int holzmaenge, int bachabschnitt){
        forstDB = this.getWritableDatabase();
        forstDB = this.getReadableDatabase();

        String holzablagerungID = getIDfromTable("tbl_Formular", "idFormular");

        ContentValues values = new ContentValues();
        values.put("idHolzablagerung", holzablagerungID);
        values.put("Baumart", baumart);
        values.put("Media", media);
        values.put("Holzmenge", holzmaenge);
        values.put("Bachabschnitt", bachabschnitt);

        forstDB.insert("tbl_Holzablagerung", null, values);
}

    public void addHolzbewuchs(int anzahl, String baumart, int hoehe, int menge, String beschreibung){
        forstDB = this.getWritableDatabase();
        forstDB = this.getReadableDatabase();

        String holzbewuchsID = getIDfromTable("tbl_Formular", "idFormular");

        ContentValues values = new ContentValues();
        values.put("idHolzbewuchs", holzbewuchsID);
        values.put("Anzahl", anzahl);
        values.put("Baumart", baumart);
        values.put("Hoehe", hoehe);
        values.put("Menge", menge);
        values.put("Beschreibung", beschreibung);

        forstDB.insert("tbl_Holzbewuchs", null, values);
    }

    public void addOhneBehinderung(String art, String beschreibung){
        forstDB = this.getWritableDatabase();
        forstDB = this.getReadableDatabase();

        String ohneBehinderungID = getIDfromTable("tbl_Formular", "idFormular");

        ContentValues values = new ContentValues();
        values.put("idOhneBehinderung", ohneBehinderungID);
        values.put("Art", art);
        values.put("Beschreibung", beschreibung);

        forstDB.insert("tbl_OhneBehinderung", null, values);
    }

    public void addSchadenAnRegulierung(String art, int hoehe, int fehlendeAbsturzsicherung,
                                        int ausgangSperrenfluegel, int geschiebesperre, int risse,
                                        int schadhaftesMauerwerk, int sonstiges, int bewuchs, int unterspueltesfundament){
        forstDB = this.getWritableDatabase();
        forstDB = this.getReadableDatabase();

        String schadenAnRegulierungID = getIDfromTable("tbl_Formular", "idFormular");

        ContentValues values = new ContentValues();
        values.put("idSchadenAnRegulierung", schadenAnRegulierungID);
        values.put("Art", art);
        values.put("Hoehe", hoehe);
        values.put("FehlendeAbsturzsicherung", fehlendeAbsturzsicherung);
        values.put("AusgangSperrenfluegel", ausgangSperrenfluegel);
        values.put("Geschiebesperre", geschiebesperre);
        values.put("Risse", risse);
        values.put("SchadhaftesMauerwerk", schadhaftesMauerwerk);
        values.put("Sonstiges", sonstiges);
        values.put("Bewuchs", bewuchs);
        values.put("UnterspueltesFundament", unterspueltesfundament);


        forstDB.insert("tbl_OhneBehinderung", null, values);
    }

    public void addWasserAuseinleitung(String art, String zweck, String beschreibung){
        forstDB = this.getWritableDatabase();
        forstDB = this.getReadableDatabase();

        String wasserAusEinleitungID = getIDfromTable("tbl_Formular", "idFormular");

        ContentValues values = new ContentValues();
        values.put("idWasserAusEinleitung", wasserAusEinleitungID);
        values.put("Art", art);
        values.put("Zweck", zweck);
        values.put("Beschreibung", beschreibung);

        forstDB.insert("tbl_WasserAusEinleitung", null, values);
    }

    public void addAbflussbehinderung(String art, String beschreibung){
        forstDB = this.getWritableDatabase();
        forstDB = this.getReadableDatabase();

        String abflussbehinderungID = getIDfromTable("tbl_Formular", "idFormular");

        ContentValues values = new ContentValues();
        values.put("idAbflussbehinderung", abflussbehinderungID);
        values.put("Art", art);
        values.put("Beschreibung", beschreibung);

        forstDB.insert("tbl_Abflussbehinderung", null, values);
    }

    public void addAblagerung(String art, String beschreibung, int bachabschnitt, int ausmass){
        forstDB = this.getWritableDatabase();
        forstDB = this.getReadableDatabase();

        String ablagerungID = getIDfromTable("tbl_Formular", "idFormular");

        ContentValues values = new ContentValues();
        values.put("idAblagerung", ablagerungID);
        values.put("Art", art);
        values.put("Beschreibung", beschreibung);
        values.put("Bachabschnitt", bachabschnitt);
        values.put("Ausmass", ausmass);

        forstDB.insert("tbl_Ablagerung", null, values);
    }

    //Methoden zum setzen der Notiz Attribute (Audio, Bilder, Text)
    public void addNotiz(){
        forstDB = this.getWritableDatabase();
        String notizID = getRandomUUID();

        String beobachtungsID = getIDfromTable("tbl_Beobachtung", "idBeobachtung");

        ContentValues values = new ContentValues();
        values.put("idNotiz", notizID);

        ContentValues setNotizID = new ContentValues();
        setNotizID.put("tbl_Notiz_idNotiz", notizID);

        forstDB.insert("tbl_Notiz", null, values);
        forstDB.update("tbl_Beobachtung", setNotizID, "idBeobachtung = '" + beobachtungsID + "'", null);
    }

    public void addImageRef(String imagePath){
        forstDB = this.getWritableDatabase();
        String imageID = getRandomUUID();

        String notizID = getIDfromTable("tbl_Notiz", "idNotiz");

        ContentValues values = new ContentValues();
        values.put("idFoto", imageID);
        values.put("Ref", imagePath);

        ContentValues setImageID = new ContentValues();
        setImageID.put("tbl_Foto_idFoto", imageID);

        forstDB.insert("tbl_Foto", null, values);
        forstDB.update("tbl_Notiz", setImageID, "idNotiz = '" + notizID + "'", null);
    }

    public void addAudioRef(String audioPath){
        forstDB = this.getWritableDatabase();
        String audioID = getRandomUUID();

        String notizID = getIDfromTable("tbl_Notiz", "idNotiz");

        ContentValues values = new ContentValues();
        values.put("idSprachaufnahme", audioID);
        values.put("Ref", audioPath);

        ContentValues setAudioID = new ContentValues();
        setAudioID.put("tbl_Sprachaufnahme_idSprachaufnahme", audioID);

        forstDB.insert("tbl_Sprachaufnahme", null, values);
        forstDB.update("tbl_Notiz", setAudioID, "idNotiz = '" + notizID + "'", null);
    }

    public void addNoteText(String text){
        forstDB = this.getWritableDatabase();
        String textID = getRandomUUID();

        String notizID = getIDfromTable("tbl_Notiz", "idNotiz");

        ContentValues values = new ContentValues();
        values.put("idText", textID);
        values.put("Text", text);

        ContentValues setTextID = new ContentValues();
        setTextID.put("tbl_Text_idText", textID);

        forstDB.insert("tbl_Text", null, values);
        forstDB.update("tbl_Notiz", setTextID, "idNotiz = '" + notizID + "'", null);
    }
    //ENDE Setzen der Attribute Notiz

    //Hinzuf�gen der Koordinaten
    public void addCoordinates(String laengengrad, String breitengrad){
        forstDB = this.getWritableDatabase();
        String gpsID = getRandomUUID();

        String beobachtungsID = getIDfromTable("tbl_Beobachtung", "idBeobachtung");

        ContentValues values = new ContentValues();
        values.put("idGps", gpsID);
        values.put("Laenge", laengengrad);
        values.put("Breite", breitengrad);

        ContentValues setGpsID = new ContentValues();
        setGpsID.put("tbl_Gps_idGps", gpsID);

        forstDB.insert("tbl_Gps", null, values);
        forstDB.update("tbl_Beobachtung", setGpsID, "idBeobachtung = '" + beobachtungsID + "'", null);
    }
    //Ende Hinzuf�gen der Koordinaten

    public void closeDB(){
        forstDB.close();
    }

    public String getIDfromTable(String tablename, String idName){
        Cursor cursor = forstDB.query(tablename, new String[] {String.valueOf(idName)}, null, null, null, null, null);
        cursor.moveToFirst();
        String id = cursor.getString(0);

        return id;
    }

    public Cursor getAllFromTable(String tablename){
        Cursor cursor = forstDB.rawQuery("SELECT * FROM " + tablename + "", null);
        cursor.moveToFirst();

        return cursor;
    }

    public String getRefFromImageTable(){
        String tablename = "tbl_Foto";
        String spaltenName = "Ref";

        Cursor cursor = forstDB.query(tablename, new String[] {String.valueOf(spaltenName)}, null, null, null, null, null);
        cursor.moveToLast();
        String imageRef = imageRef = cursor.getString(0);

        return imageRef;
    }

    public String getRefFromAudioTable(){
        String tablename = "tbl_Sprachaufnahme";
        String spaltenName = "Ref";

        Cursor cursor = forstDB.query(tablename, new String[] {String.valueOf(spaltenName)}, null, null, null, null, null);
        cursor.moveToFirst();
        String audioRef = cursor.getString(0);

        return audioRef;
    }

    public void deleteAllFromTables(){
        forstDB = getWritableDatabase();

        forstDB.rawQuery("DELETE FROM tbl_Formular", null);
        forstDB.rawQuery("DELETE FROM tbl_Holzablagerung", null);
        forstDB.rawQuery("DELETE FROM tbl_OhneBehinderung", null);
        forstDB.rawQuery("DELETE FROM tbl_SchadenAnRegulierung", null);
        forstDB.rawQuery("DELETE FROM tbl_WasserAusEinleitung", null);
        forstDB.rawQuery("DELETE FROM tbl_Abflussbehinderung", null);
        forstDB.rawQuery("DELETE FROM tbl_Ablagerung", null);
        forstDB.rawQuery("DELETE FROM tbl_Notiz", null);
        forstDB.rawQuery("DELETE FROM tbl_Foto", null);
        forstDB.rawQuery("DELETE FROM tbl_Sprachaufnahme", null);
        forstDB.rawQuery("DELETE FROM tbl_Text", null);
        forstDB.rawQuery("DELETE FROM tbl_Gps", null);
        forstDB.rawQuery("DELETE FROM tbl_Beobachtung", null);
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
                //forstDB.close();
            }
        }

    }
}
