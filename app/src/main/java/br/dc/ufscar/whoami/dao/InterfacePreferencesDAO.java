//DAO para gerenciamento da tabela InterfacePreferences
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

public class InterfacePreferencesDAO {

	private DatabaseHelper helper;
	private SQLiteDatabase db;

	public InterfacePreferencesDAO(Context context) {
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

	public void salva(Default preferencia) {
		ContentValues values = new ContentValues();

		values.put(DatabaseHelper.InterfacePreferences.PREDICATE,
				preferencia.getPredicate());
		values.put(DatabaseHelper.InterfacePreferences.RANGE, preferencia.getRange());
		values.put(DatabaseHelper.InterfacePreferences.OBJECT,
				preferencia.getObject());
		values.put(DatabaseHelper.InterfacePreferences.AUXILIARY,
				preferencia.getAuxiliary());
		values.put(DatabaseHelper.InterfacePreferences.SUBGROUP,
				preferencia.getSubgroup());
		values.put(DatabaseHelper.InterfacePreferences.GROUP,
				preferencia.getGroup());
		values.put(DatabaseHelper.InterfacePreferences.ID, preferencia.getId());

		getDb().insert(DatabaseHelper.InterfacePreferences.TABELA, null, values);

	}

	public void altera(Default preferencia) {
		ContentValues values = new ContentValues();

		values.put(DatabaseHelper.InterfacePreferences.PREDICATE,
				preferencia.getPredicate());
		values.put(DatabaseHelper.InterfacePreferences.RANGE, preferencia.getRange());
		values.put(DatabaseHelper.InterfacePreferences.OBJECT,
				preferencia.getObject());
		values.put(DatabaseHelper.InterfacePreferences.AUXILIARY,
				preferencia.getAuxiliary());
		values.put(DatabaseHelper.InterfacePreferences.SUBGROUP,
				preferencia.getSubgroup());
		values.put(DatabaseHelper.InterfacePreferences.GROUP,
				preferencia.getGroup());
		values.put(DatabaseHelper.InterfacePreferences.ID, preferencia.getId());

		String[] args = { preferencia.getPredicate().toString() };
		getDb().update(DatabaseHelper.InterfacePreferences.TABELA, values,
				DatabaseHelper.InterfacePreferences.PREDICATE + "=?", args);
	}

	public void exclui(Default preferencia) {
		String[] args = { preferencia.getPredicate().toString() };
		getDb().delete(DatabaseHelper.InterfacePreferences.TABELA,
				DatabaseHelper.InterfacePreferences.PREDICATE + "=?", args);
	}
	
	public Default get(String predicate) {
		Cursor query = getDb().query(DatabaseHelper.InterfacePreferences.TABELA,
				DatabaseHelper.InterfacePreferences.COLUNAS, "predicate = ?",
				new String[] { predicate }, null, null, null);

		if (query.moveToFirst()) {

			Default preferencia = new Default();

			preferencia.setPredicate(query.getString(0));
			preferencia.setRange(query.getString(1));
			preferencia.setObject(query.getString(2));
			preferencia.setAuxiliary(query.getString(3));
			preferencia.setSubgroup(query.getString(4));
			preferencia.setGroup(query.getString(5));
			preferencia.setId(query.getInt(6));

			return preferencia;
		} else {
			return null;
		}
	}

	public List<Default> getList() {
		Cursor query = getDb().query(
				DatabaseHelper.InterfacePreferences.TABELA,
				DatabaseHelper.InterfacePreferences.COLUNAS, null, null, null,
				null, null);

		ArrayList<Default> listaPreferencias = new ArrayList<Default>();

		while (query.moveToNext()) {
			Default preferencia = new Default();

			preferencia.setPredicate(query.getString(0));
			preferencia.setRange(query.getString(1));
			preferencia.setObject(query.getString(2));
			preferencia.setAuxiliary(query.getString(3));
			preferencia.setSubgroup(query.getString(4));
			preferencia.setGroup(query.getString(5));
			preferencia.setId(query.getInt(6));
			
			listaPreferencias.add(preferencia);
		}

		return listaPreferencias;
	}

	public Boolean estaNaLista(Default preferenciaAnalisada) {

		ArrayList<Default> listaPreferencias = new ArrayList<Default>(
				getList());

		for (ListIterator<Default> iterator = listaPreferencias.listIterator(); iterator
				.hasNext();) {
			Default preferencia = iterator.next();
			if (preferencia.getPredicate().equals(preferenciaAnalisada.getPredicate())
					&& preferencia.getSubgroup().equals(preferenciaAnalisada.getSubgroup())) {
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