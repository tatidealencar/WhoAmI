//DAO para gerenciamento da tabela EmotionalState
package br.dc.ufscar.whoami.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.dc.ufscar.whoami.helper.DatabaseHelper;
import br.dc.ufscar.whoami.model.EmotionalState;

public class EstadoEmocionalDAO {

	private DatabaseHelper helper;
	private SQLiteDatabase db;

	public EstadoEmocionalDAO(Context context) {
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

	public void salva(EmotionalState estadoEmocionalAtual) {
		ContentValues values = new ContentValues();

		values.put(DatabaseHelper.EmotionaState.PREDICATE,
				estadoEmocionalAtual.getPredicate());
		values.put(DatabaseHelper.EmotionaState.START,
				estadoEmocionalAtual.getStart());
		values.put(DatabaseHelper.EmotionaState.END,
				estadoEmocionalAtual.getEnd());
		values.put(DatabaseHelper.EmotionaState.DURABILITY,
				estadoEmocionalAtual.getDurability());
		values.put(DatabaseHelper.EmotionaState.GROUP, estadoEmocionalAtual.getGroup());

		getDb().insert(DatabaseHelper.EmotionaState.TABELA, null, values);
	}

	public void altera(EmotionalState estadoEmocionalAtual) {

		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.EmotionaState.PREDICATE,
				estadoEmocionalAtual.getPredicate());
		values.put(DatabaseHelper.EmotionaState.START,
				estadoEmocionalAtual.getStart());
		values.put(DatabaseHelper.EmotionaState.END,
				estadoEmocionalAtual.getEnd());
		values.put(DatabaseHelper.EmotionaState.DURABILITY,
				estadoEmocionalAtual.getDurability());
		values.put(DatabaseHelper.EmotionaState.GROUP, estadoEmocionalAtual.getGroup());

		String[] args = { estadoEmocionalAtual.getDurability().toString() };
		getDb().update(DatabaseHelper.EmotionaState.TABELA, values,
				DatabaseHelper.EmotionaState.DURABILITY + "=?", args);
	}
	
	public EmotionalState get(String predicate) {
		Cursor query = getDb().query(DatabaseHelper.EmotionaState.TABELA,
				DatabaseHelper.EmotionaState.COLUNAS, "predicate = ?",
				new String[] { predicate }, null, null, null);

		if (query.moveToFirst()) {

			EmotionalState estadoEmocional = new EmotionalState();

			estadoEmocional.setPredicate(query.getString(0));
			estadoEmocional.setStart(query.getString(1));
			estadoEmocional.setEnd(query.getString(2));
			estadoEmocional.setDurability(query.getString(3));
			estadoEmocional.setGroup(query.getString(4));

			return estadoEmocional;
			
		} else {
			return null;
		}
	}

	public EmotionalState get() {

		EmotionalState estadoEmocional = new EmotionalState();
		Cursor query = getDb().query(DatabaseHelper.EmotionaState.TABELA,
				DatabaseHelper.EmotionaState.COLUNAS, null, null, null, null,
				null);

		if (query.moveToFirst()) {

			estadoEmocional.setPredicate(query.getString(0));
			estadoEmocional.setStart(query.getString(1));
			estadoEmocional.setEnd(query.getString(2));
			estadoEmocional.setDurability(query.getString(3));
			estadoEmocional.setGroup(query.getString(4));

			return estadoEmocional;

		} else {
			return null;
		}
	}

	public Boolean isEmpty() {
		if (get() == null) {
			return true;
		} else {
			return false;
		}
	}
}