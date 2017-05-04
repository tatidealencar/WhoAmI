//Mapeamento das bibliotecas do BD
package br.dc.ufscar.whoami.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE = "Usuario";
	private static final int VERSAO = 1;

	public DatabaseHelper(Context context) {
		super(context, DATABASE, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE User (email TEXT, senha TEXT);");
		db.execSQL("CREATE TABLE EmotionalState (predicate TEXT NOT NULL, start TEXT NOT NULL, end TEXT NOT NULL,"
				+ "durability INT NOT NULL, _group TEXT NOT NULL);");

		db.execSQL("CREATE TABLE Abilities (predicate TEXT NOT NULL, range TEXT, object TEXT, auxiliary TEXT NOT NULL, subgroup TEXT, _group TEXT NOT NULL, _id INTEGER);");

		db.execSQL("CREATE TABLE Demographics (predicate TEXT NOT NULL, range TEXT, object TEXT NOT NULL, auxiliary TEXT NOT NULL, _group TEXT NOT NULL, _id INTEGER);");

		db.execSQL("CREATE TABLE Characteristics (predicate TEXT NOT NULL, _group TEXT NOT NULL, _id INTEGER);");

		db.execSQL("CREATE TABLE InterfacePreferences (predicate TEXT NOT NULL, range TEXT, object TEXT, auxiliary TEXT NOT NULL, subgroup TEXT, _group TEXT NOT NULL, _id INTEGER);");

		db.execSQL("CREATE TABLE Health (predicate TEXT NOT NULL, range TEXT, object TEXT, auxiliary TEXT NOT NULL, subgroup TEXT, _group TEXT NOT NULL, _id INTEGER);");

		db.execSQL("CREATE TABLE Interest (predicate TEXT NOT NULL, range TEXT, object TEXT, auxiliary TEXT NOT NULL, subgroup TEXT NOT NULL, _group TEXT NOT NULL, _id INTEGER);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

	public static class User {
		public static final String TABELA = "User";
		public static final String EMAIL = "email";
		public static final String SENHA = "senha";
		public static final String[] COLUNAS = new String[] { EMAIL, SENHA };
	}

	public static class EmotionaState {
		public static final String TABELA = "EmotionalState";
		public static final String PREDICATE = "predicate";
		public static final String START = "start";
		public static final String END = "end";
		public static final String DURABILITY = "durability";
		public static final String GROUP = "_group";
		public static final String[] COLUNAS = new String[] { PREDICATE, START,
				END, DURABILITY, GROUP };
	}

	public static class Abilities {
		public static final String TABELA = "Abilities";
		public static final String PREDICATE = "predicate";
		public static final String RANGE = "range";
		public static final String OBJECT = "object";
		public static final String AUXILIARY = " auxiliary";
		public static final String SUBGROUP = "subgroup";
		public static final String GROUP = "_group";
		public static final String ID = "_id";
		public static final String[] COLUNAS = new String[] { PREDICATE, RANGE,
				OBJECT, AUXILIARY, SUBGROUP, GROUP, ID };
	}

	public static class Demographics {
		public static final String TABELA = "Demographics";
		public static final String PREDICATE = "predicate";
		public static final String RANGE = "range";
		public static final String OBJECT = "object";
		public static final String AUXILIARY = " auxiliary";
		public static final String GROUP = "_group";
		public static final String ID = "_id";
		public static final String[] COLUNAS = new String[] { PREDICATE, RANGE,
				OBJECT, AUXILIARY, GROUP, ID };
	}

	public static class Characteristics {
		public static final String TABELA = "Characteristics";
		public static final String PREDICATE = "predicate";
		public static final String GROUP = "_group";
		public static final String ID = "_id";
		public static final String[] COLUNAS = new String[] { PREDICATE, GROUP,
				ID };
	}

	public static class InterfacePreferences {
		public static final String TABELA = "InterfacePreferences";
		public static final String PREDICATE = "predicate";
		public static final String RANGE = "range";
		public static final String OBJECT = "object";
		public static final String AUXILIARY = " auxiliary";
		public static final String SUBGROUP = "subgroup";
		public static final String GROUP = "_group";
		public static final String ID = "_id";
		public static final String[] COLUNAS = new String[] { PREDICATE, RANGE,
				OBJECT, AUXILIARY, SUBGROUP, GROUP, ID };
	}

	public static class Health {
		public static final String TABELA = "Health";
		public static final String PREDICATE = "predicate";
		public static final String RANGE = "range";
		public static final String OBJECT = "object";
		public static final String AUXILIARY = "auxiliary";
		public static final String SUBGROUP = "subgroup";
		public static final String GROUP = "_group";
		public static final String ID = "_id";
		public static final String[] COLUNAS = new String[] { PREDICATE, RANGE,
				OBJECT, AUXILIARY, SUBGROUP, GROUP, ID };
	}

	public static class Interest {
		public static final String TABELA = "Interest";
		public static final String PREDICATE = "predicate";
		public static final String RANGE = "range";
		public static final String OBJECT = "object";
		public static final String AUXILIARY = " auxiliary";
		public static final String SUBGROUP = "subgroup";
		public static final String GROUP = "_group";
		public static final String ID = "_id";
		public static final String[] COLUNAS = new String[] { PREDICATE, RANGE,
				OBJECT, AUXILIARY, SUBGROUP, GROUP, ID };
	}
}