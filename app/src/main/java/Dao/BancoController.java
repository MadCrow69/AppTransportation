package Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BancoController {

    private SQLiteDatabase db;
    private ConfigBanco banco;

    public BancoController(Context context){
        banco = new ConfigBanco(context);
    }

    public String inserirD(String titulo, String pontoa, String pontob, String pontoc, String pontod){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(ConfigBanco.TITULO, titulo);
        valores.put(ConfigBanco.PONTOA, pontoa);
        valores.put(ConfigBanco.PONTOB, pontob);
        valores.put(ConfigBanco.PONTOC, pontoc);
        valores.put(ConfigBanco.PONTOD, pontod);

        resultado = db.insert(ConfigBanco.TABELA, null, valores);
        db.close();

        if (resultado ==-1) {
            return "Erro ao inserir registro";
        }
        else {
            return "Registro Inserido com sucesso";

        }
    }

    public String removeDado(String titulo, String pontoa, String pontob, String pontoc, String pontod){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(ConfigBanco.TITULO, titulo);
        valores.put(ConfigBanco.PONTOA, pontoa);
        valores.put(ConfigBanco.PONTOB, pontob);
        valores.put(ConfigBanco.PONTOC, pontoc);
        valores.put(ConfigBanco.PONTOD, pontod);

        resultado = db.insert(ConfigBanco.TABELA, null, valores);
        db.close();

        if (resultado ==-1) {
            return "Erro ao remover";
        }
        else {
            return "Registro Removido com sucesso";

        }
    }

    public Cursor carregaDados(){
        Cursor cursor;
        String[] campos =  {banco.ID,banco.TITULO};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
}