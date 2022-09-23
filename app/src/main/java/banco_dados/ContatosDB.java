package banco_dados;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.listatelefonica.Contatos;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.logging.SimpleFormatter;


public class ContatosDB {
    DBHelper db;
    private SQLiteDatabase conexao;

    public ContatosDB(DBHelper db){
        this.db = db;
    }
    public  void inserir(Contatos contatos){
        conexao = db.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nome", contatos.getNome());
        valores.put("telefone", contatos.getTelefone());
        valores.put("datanasc", contatos.getDatanasc());
        conexao.insertOrThrow("contatos", null, valores);
        conexao.close();
    }

    public void atualizar(Contatos contatos){
        conexao = db.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nome", contatos.getNome());
        valores.put("telefone", contatos.getTelefone());
        valores.put("datanasc", contatos.getDatanasc());
        conexao.update("contatos", valores, "id = ?", new String[]{contatos.getId().toString()});
        conexao.close();
    }
    public void remover(Integer id){
        conexao = db.getWritableDatabase();
        conexao.delete("contatos", "id = ?", new String[]{id+""});
        conexao.close();
    }
    public void listar(List dados) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            dados.clear();
            conexao = db.getReadableDatabase();
            String names[] = {"id", "telefone", "datanasc"};
            Cursor query = conexao.query("contatos", names, null, null,null, null, "nome");
            while (query.moveToNext()){
                Contatos contatos = new Contatos();
                contatos.setId(Integer.parseInt(query.getString(0)));
                contatos.setNome(query.getString(1));
                contatos.setTelefone(query.getString(2));
                contatos.setDatanasc(formatter.parse(query.getString(3)));
                dados.add(contatos);
            }
            conexao.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
