//DAO para gerenciamento da tabela Abilities
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

public class AbilitiesDAO {

	private DatabaseHelper helper;
	private SQLiteDatabase db;

	public AbilitiesDAO(Context context) {
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

	public void salva(Default habilidade) {
		ContentValues values = new ContentValues();

		values.put(DatabaseHelper.Abilities.PREDICATE,
				habilidade.getPredicate());
		values.put(DatabaseHelper.Abilities.RANGE, habilidade.getRange());
		values.put(DatabaseHelper.Abilities.OBJECT, habilidade.getObject());
		values.put(DatabaseHelper.Abilities.AUXILIARY,
				habilidade.getAuxiliary());
		values.put(DatabaseHelper.Abilities.SUBGROUP, habilidade.getSubgroup());
		values.put(DatabaseHelper.Abilities.GROUP, habilidade.getGroup());
		values.put(DatabaseHelper.Abilities.ID, habilidade.getId());

		getDb().insert(DatabaseHelper.Abilities.TABELA, null, values);
	}

	public void altera(Default habilidade) {
		ContentValues values = new ContentValues();

		values.put(DatabaseHelper.Abilities.PREDICATE,
				habilidade.getPredicate());
		values.put(DatabaseHelper.Abilities.RANGE, habilidade.getRange());
		values.put(DatabaseHelper.Abilities.OBJECT, habilidade.getObject());
		values.put(DatabaseHelper.Abilities.AUXILIARY,
				habilidade.getAuxiliary());
		values.put(DatabaseHelper.Abilities.SUBGROUP, habilidade.getSubgroup());
		values.put(DatabaseHelper.Abilities.GROUP, habilidade.getGroup());
		values.put(DatabaseHelper.Abilities.ID, habilidade.getId());

		String[] args = { habilidade.getPredicate().toString() };
		getDb().update(DatabaseHelper.Abilities.TABELA, values,
				DatabaseHelper.Abilities.PREDICATE + "=?", args);
	}

	public void exclui(Default habilidade) {
		String[] args = { habilidade.getPredicate().toString() };
		getDb().delete(DatabaseHelper.Abilities.TABELA,
				DatabaseHelper.Abilities.PREDICATE + "=?", args);
	}

	public Default get(String predicate) {
		Cursor query = getDb().query(DatabaseHelper.Abilities.TABELA,
				DatabaseHelper.Abilities.COLUNAS, "predicate = ?",
				new String[] { predicate }, null, null, null);

		if (query.moveToFirst()) {

			Default habilidade = new Default();

			habilidade.setPredicate(query.getString(0));
			habilidade.setRange(query.getString(1));
			habilidade.setObject(query.getString(2));
			habilidade.setAuxiliary(query.getString(3));
			habilidade.setSubgroup(query.getString(4));
			habilidade.setGroup(query.getString(5));
			habilidade.setId(query.getInt(6));

			return habilidade;
		} else {
			return null;
		}
	}

	public List<Default> getList() {
		Cursor query = getDb().query(DatabaseHelper.Abilities.TABELA,
				DatabaseHelper.Abilities.COLUNAS, null, null, null, null, null);

		ArrayList<Default> listaHabilidades = new ArrayList<Default>();

		while (query.moveToNext()) {
			Default habilidade = new Default();

			habilidade.setPredicate(query.getString(0));
			habilidade.setRange(query.getString(1));
			habilidade.setObject(query.getString(2));
			habilidade.setAuxiliary(query.getString(3));
			habilidade.setSubgroup(query.getString(4));
			habilidade.setGroup(query.getString(5));
			habilidade.setId(query.getInt(6));

			listaHabilidades.add(habilidade);
		}

		return listaHabilidades;
	}

	public Boolean estaNaLista(String habilidadesAnalisada) {

		ArrayList<Default> listaHabilidades = new ArrayList<Default>(getList());

		for (ListIterator<Default> iterator = listaHabilidades.listIterator(); iterator
				.hasNext();) {
			Default habilidade = iterator.next();
			if (habilidade.getPredicate().equals(habilidadesAnalisada)) {
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