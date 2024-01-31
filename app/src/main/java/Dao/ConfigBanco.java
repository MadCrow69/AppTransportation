package Dao;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ConfigBanco extends SQLiteOpenHelper {

    //para subir na maquina do Diego
    public static final String TITULO = "titulo";
    public static final String PONTOA = "pontoa";
    public static final String PONTOB = "pontob";
    public static final String PONTOC = "pontoc";
    public static final String PONTOD = "pontod";
    public static final String TABELA = "tabela";
    public static final String ID = "_id";


    public static final String NOME_BANCO = "banco.db";
    //dados para GRUPO ROTAS
    public static final String GRUPOROTAS = "gruporotas";
    public static final String ID_GRUPOROTAS = "_idgruporotas";
    public static final String NOME_GRUPO = "nome_grupo";
    public static final String DATA_EXECUCAO  = "data_execucao";
    public static final String ID_FGN_ROTAS = "_idfgnrotas";
    //dados para ROTAS
    public static final String ROTAS = "rotas";
    public static final String ID_ROTAS = "_idrotas";
    public static final String ID_FGN_ENDERECOS = "_idfgnenderecos";
    //dados para ENDEREÇOS
    public static final String ENDERECOS = "enderecos";
    public static final String ID_ENDERECOS = "_idenderecos";
    public static final String ENDERECO_COMPLETO = "endereco_completo";
    public static final String TELEFONE_CONTATO = "telefone_contato";
    //dados para LOG ROTAS
    public static final String LOGROTAS = "logrotas";
    public static final String ID_LOGROTAS = "_idlogrotas";
    //dados para USUARIO
    public static final String USUARIO = "usuario";
    public static final String ID_USER = "_idusuario";
    //dados para LOG USUARIO
    public static final String LOGUSUARIO = "logusuario";
    public static final String ID_LOGUSER = "_idloguser";
    public static final String DATA_ACESSO = "data_acesso";

    public static final int VERSAO = 1;

    public ConfigBanco(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    public ConfigBanco(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+GRUPOROTAS+"("
                + ID_GRUPOROTAS + " integer primary key autoincrement,"
                + NOME_GRUPO + " text,"
                + DATA_EXECUCAO + " data,"
                + "FOREIGN KEY ("+ID_FGN_ROTAS+") REFERENCES "+ROTAS+"("+ID_ROTAS+"))");

        db.execSQL("CREATE TABLE "+ROTAS+"("
                +ID_ROTAS+" integer primary key autoincrement,"
                +"FOREIGN KEY ("+ID_FGN_ENDERECOS+") REFERENCES "+ENDERECOS+"("+ID_ENDERECOS+"))");

        db.execSQL("CREATE TABLE "+ENDERECOS+"("
                + ID_ENDERECOS + " integer primary key autoincrement,"
                + ENDERECO_COMPLETO + " text,"
                + TELEFONE_CONTATO + " text)");

       /* db.execSQL("CREATE TABLE "+LOGROTAS+"("
                + ID_LOGROTAS +" integer primary key autoincrement,"
                + DATA_ROTA +" data,"
                + "FOREIGN KEY (" +ID_FGN_GRUPOROTAS +") REFERENCES "+GRUPOROTAS+"("+ID_GRUPOROTAS+"))");*/

        //FALTANDO IDENTIFICAR CORRETAMENTE OS CAMPOS A ADERIREM O USUARIO CONFORME RETORNO DA API DE LOGIN
        db.execSQL("CREATE TABLE "+USUARIO+"("
                +ID_USER+" integer primary key autoincrement)");

        db.execSQL("CREATE TABLE "+LOGUSUARIO+"("
                +ID_LOGUSER+" integer primary key autoincrement,"
                +DATA_ACESSO+" data)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GRUPOROTAS);
        onCreate(db);
    }

 /*   public void deletaRegistro(int id){
        String where = ConfigBanco.ID + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(ConfigBanco.TABELA,where,null);
        db.close();
    }*/
}