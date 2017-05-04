//DAO para gerenciamento da tabela Characteristics
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

public class CharacteristicsDAO {

	private DatabaseHelper helper;
	private SQLiteDatabase db;

	public CharacteristicsDAO(Context context) {
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

	public void salva(Default caracteristica) {
		ContentValues values = new ContentValues();

		values.put(DatabaseHelper.Characteristics.PREDICATE,
				caracteristica.getPredicate());
		values.put(DatabaseHelper.Characteristics.GROUP,
				caracteristica.getGroup());
		values.put(DatabaseHelper.Characteristics.ID, caracteristica.getId());

		getDb().insert(DatabaseHelper.Characteristics.TABELA, null, values);
	}

	public void altera(Default caracteristica) {
		ContentValues values = new ContentValues();

		values.put(DatabaseHelper.Characteristics.PREDICATE,
				caracteristica.getPredicate());
		values.put(DatabaseHelper.Characteristics.GROUP,
				caracteristica.getGroup());
		values.put(DatabaseHelper.Characteristics.ID, caracteristica.getId());

		String[] args = { caracteristica.getPredicate().toString() };
		getDb().update(DatabaseHelper.Characteristics.TABELA, values,
				DatabaseHelper.Characteristics.PREDICATE + "=?", args);
	}

	public void exclui(Default caracteristica) {
		String[] args = { caracteristica.getPredicate().toString() };
		getDb().delete(DatabaseHelper.Characteristics.TABELA,
				DatabaseHelper.Characteristics.PREDICATE + "=?", args);
	}

	public Default get(String predicate) {
		Cursor query = getDb().query(DatabaseHelper.Characteristics.TABELA,
				DatabaseHelper.Characteristics.COLUNAS, "predicate = ?",
				new String[] { predicate }, null, null, null);

		if (query.moveToFirst()) {

			Default caracteristica = new Default();

			caracteristica.setPredicate(query.getString(0));
			caracteristica.setObject(query.getString(1));
			caracteristica.setId(query.getInt(2));

			return caracteristica;
		} else {
			return null;
		}
	}

	public List<Default> getList() {
		Cursor query = getDb().query(DatabaseHelper.Characteristics.TABELA,
				DatabaseHelper.Characteristics.COLUNAS, null, null, null, null,
				null);

		ArrayList<Default> listaCaracteristicas = new ArrayList<Default>();

		while (query.moveToNext()) {
			Default caracteristica = new Default();

			caracteristica.setPredicate(query.getString(0));
			caracteristica.setObject(query.getString(1));
			caracteristica.setId(query.getInt(2));

			listaCaracteristicas.add(caracteristica);
		}

		return listaCaracteristicas;
	}

	public Boolean estaNaLista(String caracteristicaAnalisada) {

		ArrayList<Default> listaCaracteristicas = new ArrayList<Default>(
				getList());

		for (ListIterator<Default> iterator = listaCaracteristicas
				.listIterator(); iterator.hasNext();) {
			Default dado = iterator.next();
			if (dado.getPredicate().equals(caracteristicaAnalisada)) {
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