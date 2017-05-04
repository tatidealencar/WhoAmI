//DAO para gerenciamento da tabela Health
package br.dc.ufscar.whoami.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.dc.ufscar.whoami.helper.DatabaseHelper;
import br.dc.ufscar.whoami.model.Default;

public class HealthDAO {

	private DatabaseHelper helper;
	private SQLiteDatabase db;

	public HealthDAO(Context context) {
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

	public void salva(Default nutricao) {
		ContentValues values = new ContentValues();

		values.put(DatabaseHelper.Health.PREDICATE, nutricao.getPredicate());
		values.put(DatabaseHelper.Health.RANGE, nutricao.getRange());
		values.put(DatabaseHelper.Health.OBJECT, nutricao.getObject());
		values.put(DatabaseHelper.Health.AUXILIARY, nutricao.getAuxiliary());
		values.put(DatabaseHelper.Health.SUBGROUP, nutricao.getSubgroup());
		values.put(DatabaseHelper.Health.GROUP, nutricao.getGroup());
		values.put(DatabaseHelper.Health.ID, nutricao.getId());

		getDb().insert(DatabaseHelper.Health.TABELA, null, values);
	}

	public void altera(Default nutricao) {
		ContentValues values = new ContentValues();

		values.put(DatabaseHelper.Health.PREDICATE, nutricao.getPredicate());
		values.put(DatabaseHelper.Health.RANGE, nutricao.getRange());
		values.put(DatabaseHelper.Health.OBJECT, nutricao.getObject());
		values.put(DatabaseHelper.Health.AUXILIARY, nutricao.getAuxiliary());
		values.put(DatabaseHelper.Health.SUBGROUP, nutricao.getSubgroup());
		values.put(DatabaseHelper.Health.GROUP, nutricao.getGroup());
		values.put(DatabaseHelper.Health.ID, nutricao.getId());

		String[] args = { nutricao.getPredicate().toString() };
		getDb().update(DatabaseHelper.Health.TABELA, values,
				DatabaseHelper.Health.PREDICATE + "=?", args);
	}

	public Default get(String predicate) {
		Cursor query = getDb().query(DatabaseHelper.Health.TABELA,
				DatabaseHelper.Health.COLUNAS, "predicate = ?",
				new String[] { predicate }, null, null, null);

		if (query.moveToFirst()) {

			Default nutricao = new Default();

			nutricao.setPredicate(query.getString(0));
			nutricao.setRange(query.getString(1));
			nutricao.setObject(query.getString(2));
			nutricao.setAuxiliary(query.getString(3));
			nutricao.setSubgroup(query.getString(4));
			nutricao.setGroup(query.getString(5));
			nutricao.setId(query.getInt(6));

			return nutricao;
		} else {
			return null;
		}
	}

	public List<Default> getList() {
		Cursor query = getDb().query(DatabaseHelper.Health.TABELA,
				DatabaseHelper.Health.COLUNAS, null, null, null, null, null);

		ArrayList<Default> listaNutricao = new ArrayList<Default>();

		while (query.moveToNext()) {
			Default nutricao = new Default();

			nutricao.setPredicate(query.getString(0));
			nutricao.setRange(query.getString(1));
			nutricao.setObject(query.getString(2));
			nutricao.setAuxiliary(query.getString(3));
			nutricao.setSubgroup(query.getString(4));
			nutricao.setGroup(query.getString(5));
			nutricao.setId(query.getInt(6));

			listaNutricao.add(nutricao);
		}

		return listaNutricao;
	}

	public Boolean estaNaLista(String nutricaoAnalisada) {

		ArrayList<Default> listaNutricao = new ArrayList<Default>(getList());

		for (ListIterator<Default> iterator = listaNutricao.listIterator(); iterator
				.hasNext();) {
			Default nutricao = iterator.next();
			if (nutricao.getPredicate().equals(nutricaoAnalisada)) {
				return true;
			}
		}
		return false;
	}

	public Boolean isEmpty() {
		if (getList() == null) {
			return true;
		} else {
			return false;
		}
	}
}