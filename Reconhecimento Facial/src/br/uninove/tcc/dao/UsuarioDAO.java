package br.uninove.tcc.dao;

import java.util.ArrayList;
import java.util.List;

import br.uninove.tcc.model.Usuario;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class UsuarioDAO extends SQLiteOpenHelper {
	
	public UsuarioDAO(Context context, String name, CursorFactory factory, int version) {
		super(context, TABELA, null, VERSION);
	}
	
	public UsuarioDAO(Context context){
		super(context, TABELA, null, VERSION);
	}

	private static int VERSION = 1;
	private static String TABELA = "Usuario";
	private static String [] COLS = {"id","nome","caracteristicas"};


	@Override
	public void onCreate(SQLiteDatabase db) {
		String criarBanco = "CREATE TABLE " + TABELA +
		"(id INTEGER PRIMARY KEY, nome TEXT, caracteristicas TEXT);";
		db.execSQL(criarBanco);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABELA);
		this.onCreate(db);
		
	}
	
	public void adicionarUsuario (Usuario usuario){
		getWritableDatabase().insert(TABELA, null, usuario.getValues());
		
	}
	
	public List < Usuario > obterUsuarios () {
		List < Usuario > retorno = new ArrayList<Usuario>();
		Cursor c = getWritableDatabase().query(TABELA, COLS, null, null, null, null, null);
		
		while(c.moveToNext()) {
			Usuario item = new Usuario(c.getString(1), c.getString(2));
			retorno.add(item);
		}
		c.close();
		return retorno;
	}

}
