//DAO para gerenciamento da tabela Demographics
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

public class DemographicsDAO {

	private DatabaseHelper helper;
	private SQLiteDatabase db;

	public DemographicsDAO(Context context) {
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

	public void salva(Default dado) {
		ContentValues values = new ContentValues();

		values.put(DatabaseHelper.Demographics.PREDICATE, dado.getPredicate());
		values.put(DatabaseHelper.Demographics.RANGE, dado.getRange());
		values.put(DatabaseHelper.Demographics.OBJECT, dado.getObject());
		values.put(DatabaseHelper.Demographics.AUXILIARY, dado.getAuxiliary());
		values.put(DatabaseHelper.Demographics.GROUP, dado.getGroup());
		values.put(DatabaseHelper.Demographics.ID, dado.getId());

		getDb().insert(DatabaseHelper.Demographics.TABELA, null, values);
	}

	public void altera(Default dado) {
		ContentValues values = new ContentValues();

		values.put(DatabaseHelper.Demographics.PREDICATE, dado.getPredicate());
		values.put(DatabaseHelper.Demographics.RANGE, dado.getRange());
		values.put(DatabaseHelper.Demographics.OBJECT, dado.getObject());
		values.put(DatabaseHelper.Demographics.AUXILIARY, dado.getAuxiliary());
		values.put(DatabaseHelper.Demographics.GROUP, dado.getGroup());
		values.put(DatabaseHelper.Demographics.ID, dado.getId());


		String[] args = { dado.getPredicate().toString() };
		getDb().update(DatabaseHelper.Demographics.TABELA, values,
				DatabaseHelper.Demographics.PREDICATE + "=?", args);
	}

	public Default get(String predicate) {
		Cursor query = getDb().query(DatabaseHelper.Demographics.TABELA,
				DatabaseHelper.Demographics.COLUNAS, "predicate = ?",
				new String[] { predicate }, null, null, null);

		if (query.moveToFirst()) {

			Default dado = new Default();

			dado.setPredicate(query.getString(0));
			dado.setRange(query.getString(1));
			dado.setObject(query.getString(2));
			dado.setAuxiliary(query.getString(3));
			dado.setGroup(query.getString(4));
			dado.setId(query.getInt(5));

			return dado;
		} else {
			return null;
		}
	}
	
	public List<Default> getList() {
		Cursor query = getDb().query(DatabaseHelper.Demographics.TABELA,
				DatabaseHelper.Demographics.COLUNAS, null, null, null, null,
				null);

		ArrayList<Default> listaDados = new ArrayList<Default>();

		while (query.moveToNext()) {
			Default dado = new Default();

			dado.setPredicate(query.getString(0));
			dado.setRange(query.getString(1));
			dado.setObject(query.getString(2));
			dado.setAuxiliary(query.getString(3));
			dado.setGroup(query.getString(4));
			dado.setId(query.getInt(5));

			listaDados.add(dado);
		}

		return listaDados;
	}

	public Boolean estaNaLista(String dadoAnalisado) {

		ArrayList<Default> listaDados = new ArrayList<Default>(getList());

		for (ListIterator<Default> iterator = listaDados.listIterator(); iterator
				.hasNext();) {
			Default dado = iterator.next();
			if (dado.getPredicate().equals(dadoAnalisado)) {
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