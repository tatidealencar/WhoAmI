//DAO para gerenciamento da tabela User
package br.dc.ufscar.whoami.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.dc.ufscar.whoami.helper.DatabaseHelper;
import br.dc.ufscar.whoami.model.Usuario;

public class UserDAO {

	private DatabaseHelper helper;
	private SQLiteDatabase db;

	public UserDAO(Context context) {
		helper = new DatabaseHelper(context);
	}

	private SQLiteDatabase getDb() {
		if (db == null) {
			db = helper.getWritableDatabase();
		}
		return db;
	}

	public void close() {
		helper.close();
	}

	public void salvaUsuario(Usuario usuario) {
		ContentValues values = new ContentValues();

		values.put(DatabaseHelper.User.EMAIL, usuario.getEmail());
		values.put(DatabaseHelper.User.SENHA, usuario.getSenha());

		getDb().insert(DatabaseHelper.User.TABELA, null, values);
	}

	public void alteraUsuario(Usuario usuario) {
		ContentValues values = new ContentValues();

		values.put(DatabaseHelper.User.EMAIL, usuario.getEmail());
		values.put(DatabaseHelper.User.SENHA, usuario.getSenha());

		String[] args = {};
		getDb().update(DatabaseHelper.User.TABELA, values, "", args);
	}
	
	public void exclui() {
		String[] args = { getUsuario().getEmail().toString() };
		getDb().delete(DatabaseHelper.User.TABELA,
				DatabaseHelper.User.EMAIL + "=?", args);
	}

	public void alteraSenha(String senha) {

		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.User.SENHA, senha);

		String[] args = {};
		getDb().update(DatabaseHelper.User.TABELA, values, "", args);
	}

	public Usuario getUsuario() {
		Usuario usuario = new Usuario();
		Cursor query = getDb().query(DatabaseHelper.User.TABELA,
				DatabaseHelper.User.COLUNAS, null, null, null, null, null);

		if (query.moveToFirst()) {

			usuario.setEmail(query.getString(0));
			usuario.setSenha(query.getString(1));

			return usuario;

		} else {
			return null;
		}
	}

	public String getSenha() {

		Usuario usuario = new Usuario();
		Cursor query = getDb().query(DatabaseHelper.User.TABELA,
				DatabaseHelper.User.COLUNAS, null, null, null, null, null);

		if (query.moveToFirst()) {

			usuario.setSenha(query.getString(1));

			return usuario.getSenha();

		} else {
			return null;
		}
	}

	public void alteraEmail(Usuario usuario) {

		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.User.EMAIL, usuario.getEmail());

		String[] args = { "" };
		getDb().update(DatabaseHelper.User.TABELA, values, "", args);
	}

	public String getEmail() {

		Usuario usuario = new Usuario();
		Cursor query = getDb().query(DatabaseHelper.User.TABELA,
				DatabaseHelper.User.COLUNAS, null, null, null, null, null);

		if (query.moveToFirst()) {

			usuario.setEmail(query.getString(0));

			return usuario.getEmail();

		} else {
			return null;
		}
	}

	public Boolean isEmpty() {
		if (getSenha() == null) {
			return true;
		} else {
			return false;
		}
	}

}
