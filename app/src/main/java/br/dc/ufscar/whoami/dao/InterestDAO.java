//DAO para gerenciamento da tabela Interest
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

public class InterestDAO {

	private DatabaseHelper helper;
	private SQLiteDatabase db;

	public InterestDAO(Context context) {
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

	public void salva(Default interesse) {
		ContentValues values = new ContentValues();

		values.put(DatabaseHelper.Interest.PREDICATE, interesse.getPredicate());
		values.put(DatabaseHelper.Interest.RANGE, interesse.getRange());
		values.put(DatabaseHelper.Interest.OBJECT, interesse.getObject());
		values.put(DatabaseHelper.Interest.AUXILIARY, interesse.getAuxiliary());
		values.put(DatabaseHelper.Interest.SUBGROUP, interesse.getSubgroup());
		values.put(DatabaseHelper.Interest.GROUP, interesse.getGroup());
		values.put(DatabaseHelper.Interest.ID, interesse.getId());

		getDb().insert(DatabaseHelper.Interest.TABELA, null, values);
	}

	public void altera(Default interesse) {
		ContentValues values = new ContentValues();

		values.put(DatabaseHelper.Interest.PREDICATE, interesse.getPredicate());
		values.put(DatabaseHelper.Interest.RANGE, interesse.getRange());
		values.put(DatabaseHelper.Interest.OBJECT, interesse.getObject());
		values.put(DatabaseHelper.Interest.AUXILIARY, interesse.getAuxiliary());
		values.put(DatabaseHelper.Interest.SUBGROUP, interesse.getSubgroup());
		values.put(DatabaseHelper.Interest.GROUP, interesse.getGroup());
		values.put(DatabaseHelper.Interest.ID, interesse.getId());

		String[] args = { interesse.getPredicate().toString() };
		getDb().update(DatabaseHelper.Interest.TABELA, values,
				DatabaseHelper.Interest.PREDICATE + "=?", args);
	}
	
	public void exclui (Default interesse) {
		String[] args = {interesse.getPredicate().toString() };
		getDb().delete(DatabaseHelper.Interest.TABELA, DatabaseHelper.Interest.PREDICATE + "=?", args);
	}
	
	public Default get(String predicate) {
		Cursor query = getDb().query(DatabaseHelper.Interest.TABELA,
				DatabaseHelper.Interest.COLUNAS, "predicate = ?",
				new String[] { predicate }, null, null, null);

		if (query.moveToFirst()) {

			Default interesse = new Default();

			interesse.setPredicate(query.getString(0));
			interesse.setRange(query.getString(1));
			interesse.setObject(query.getString(2));
			interesse.setAuxiliary(query.getString(3));
			interesse.setSubgroup(query.getString(4));
			interesse.setGroup(query.getString(5));
			interesse.setId(query.getInt(6));
			
			return interesse;
		} else {
			return null;
		}
	}

	public List<Default> getList() {
		Cursor query = getDb().query(DatabaseHelper.Interest.TABELA,
				DatabaseHelper.Interest.COLUNAS, null, null, null, null,
				null);

		ArrayList<Default> listaInteresses = new ArrayList<Default>();

		while (query.moveToNext()) {
			Default interesse = new Default();

			interesse.setPredicate(query.getString(0));
			interesse.setRange(query.getString(1));
			interesse.setObject(query.getString(2));
			interesse.setAuxiliary(query.getString(3));
			interesse.setSubgroup(query.getString(4));
			interesse.setGroup(query.getString(5));
			interesse.setId(query.getInt(6));

			listaInteresses.add(interesse);
		}

		return listaInteresses;
	}

	public Boolean estaNaLista(String interesseAnalisado) {

		ArrayList<Default> listaInteresses = new ArrayList<Default>(
				getList());

		for (ListIterator<Default> iterator = listaInteresses
				.listIterator(); iterator.hasNext();) {
			Default dado = iterator.next();
			if (dado.getPredicate().equals(interesseAnalisado)) {
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