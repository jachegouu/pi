package com.example.ray.jachegou.DAOS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.ray.jachegou.MODELS.UsuarioBean;

/**
 * Created by Ray on 07/03/2016.
 */
public class UsuarioDAO extends SQLiteOpenHelper {
    private static final int VERSAO=1;
    private static final String TABELA="usuario";
    private static final String DATABASE = "jachegou";
    private static final  String TAG ="CADASTRO_USUARIO";

    public UsuarioDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE "+ TABELA + " ( id INTEGER , "+
                " email TEXT, senha TEXT );";
                db.execSQL(sql);
        Log.i(TAG, "Tabela Criada Com Sucesso: " + TABELA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="DROP TABLE IF EXISTS "+ TABELA;
        db.execSQL(sql);
        Log.i(TAG, "Atualizando tabela");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="DROP TABLE IF EXISTS "+ TABELA;
        db.execSQL(sql);
        Log.i(TAG, "Atualizando tabela");
        onCreate(db);
    }

    public void atualizarUltimoUsuario(UsuarioBean usuario){

        String sql = " select * from " + TABELA;
        Cursor cursor=getReadableDatabase().rawQuery(sql,null);

        ContentValues values = new ContentValues();
        values.put("email", usuario.getEmail());
        values.put("senha", usuario.getSenha());
        usuario.setId(1);

        if(cursor.getCount()==1){
            String[] args ={usuario.getId().toString()};
            getWritableDatabase().update(TABELA, values, "id=?", args);
            System.out.print("Atuliazaod com sucesoo!");
        }else{
            values.put("id", usuario.getId());
            getWritableDatabase().insert(TABELA, null, values);
            System.out.print("Primeiro aceso");
        }

    }
    public void alterar(UsuarioBean usuario){
        ContentValues values = new ContentValues();
        usuario.setId(1);
        values.put("email", usuario.getEmail());
        values.put("senha", usuario.getSenha());

        String[] args ={usuario.getId().toString()};
        getWritableDatabase().update(TABELA, values, "id=?", args);

    }
    public void delete(UsuarioBean usuario){
            String[] args ={usuario.getId().toString()};
            getWritableDatabase().delete(TABELA, "id=?", args);
            Log.i(TAG, "Delete Cliente: " + usuario.getNome());
    }
    /*public List<Cliente> lista(){
        List<Cliente> lista = new ArrayList<>();
        String sql = " select * from cliente order by nome";

        Cursor cursor=getReadableDatabase().rawQuery(sql,null);
        try {
            while (cursor.moveToNext()){
                Cliente cliente = new Cliente();
                cliente.setId(cursor.getLong(0));
                cliente.setNome(cursor.getString(1));
                cliente.setTelefone(cursor.getString(2));
                cliente.setEndereco(cursor.getString(3));
                cliente.setSite(cursor.getString(4));
                cliente.setEmail(cursor.getString(5));
                cliente.setFoto(cursor.getString(6));
                cliente.setRegiao(cursor.getInt(7));
                lista.add(cliente);
            }
            return lista;
        }catch (android.database.SQLException e){
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
            return null;
        }finally {
            cursor.close();
        }
    }*/
    public UsuarioBean pegarUltimoUsuarioLogado(){

        String sql = " select * from usuario where id=1";

        Cursor cursor=getReadableDatabase().rawQuery(sql,null);
        try {
            if (cursor.moveToNext()){
                UsuarioBean usuario = new UsuarioBean();
                usuario.setEmail(cursor.getString(1));
                usuario.setSenha(cursor.getString(2));
                return usuario;
            }else{
                return null;
            }
        }catch (android.database.SQLException e){
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
            return null;
        }finally {
            cursor.close();
        }
    }

}

