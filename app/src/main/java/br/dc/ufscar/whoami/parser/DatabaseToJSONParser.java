//Recupera os dados do BD e salva em um JSON a ser compartilhado
package br.dc.ufscar.whoami.parser;

import java.util.ArrayList;
import java.util.ListIterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import br.dc.ufscar.whoami.dao.AbilitiesDAO;
import br.dc.ufscar.whoami.dao.CharacteristicsDAO;
import br.dc.ufscar.whoami.dao.DemographicsDAO;
import br.dc.ufscar.whoami.dao.EstadoEmocionalDAO;
import br.dc.ufscar.whoami.dao.HealthDAO;
import br.dc.ufscar.whoami.dao.InterestDAO;
import br.dc.ufscar.whoami.dao.InterfacePreferencesDAO;
import br.dc.ufscar.whoami.map.PreferencesPredicatesMap;
import br.dc.ufscar.whoami.model.Default;
import br.dc.ufscar.whoami.model.EmotionalState;

public class DatabaseToJSONParser {

	private String camposJSON;
	private final Context context;

	//Construtor recebe campos do perfil de usuário a ser buscado no BD
	public DatabaseToJSONParser(String camposJSON, Context context) {
		this.camposJSON = camposJSON;
		this.context = context;
	}

	public JSONObject parse() {

		JSONObject userProfileJSON = new JSONObject();

		try {
			JSONArray jArrayAbilities = new JSONArray();
			JSONArray jArrayDemographics = new JSONArray();
			JSONArray jArrayEmotionalState = new JSONArray();
			JSONArray jArrayHealth = new JSONArray();
			JSONArray jArrayCharacteristics = new JSONArray();
			JSONArray jArrayInterest = new JSONArray();
			JSONArray jArrayInterfacePreferences = new JSONArray();

			JSONObject jObj = new JSONObject(camposJSON);
			JSONArray jArrFields = jObj.getJSONArray("profile_fields");
			
			for (int i = 0; i < jArrFields.length(); i++) {
				PreferencesPredicatesMap map = new PreferencesPredicatesMap();
				String valueString = jArrFields.getString(i);

				//Recupera as informações relacionadas às habilidades do usuário
				if (valueString.equals("Abilities")) {
					AbilitiesDAO dao = new AbilitiesDAO(context);
					if (!dao.isEmpty()) {
						ArrayList<Default> lista = new ArrayList<Default>(
								dao.getList());
						for (ListIterator<Default> iterator = lista
								.listIterator(); iterator.hasNext();) {
							Default objeto = iterator.next();
							JSONObject temp = new JSONObject();
							temp.put("auxiliary", objeto.getAuxiliary());
							temp.put("predicate", objeto.getPredicate());
							temp.put("range", objeto.getRange());
							temp.put("object", objeto.getObject());
							temp.put("subgroup", objeto.getSubgroup());
							jArrayAbilities.put(temp);
						}
					}
					dao.close();
				}

				//Recupera as informações relacionadas aos dados demográficos do usuário
				if (valueString.equals("Demographics")) {
					DemographicsDAO dao = new DemographicsDAO(context);
					if (!dao.isEmpty()) {
						ArrayList<Default> lista = new ArrayList<Default>(
								dao.getList());
						for (ListIterator<Default> iterator = lista
								.listIterator(); iterator.hasNext();) {
							Default objeto = iterator.next();
							JSONObject temp = new JSONObject();
							temp.put("auxiliary", objeto.getAuxiliary());
							temp.put("predicate", objeto.getPredicate());
							temp.put("range", objeto.getRange());
							temp.put("object", objeto.getObject());
							jArrayDemographics.put(temp);
						}
					}
					dao.close();
				}

				//Recupera as informações relacionadas ao estado emocional do usuário
				if (valueString.equals("EmotionalState")) {
					EstadoEmocionalDAO dao = new EstadoEmocionalDAO(context);
					if (!dao.isEmpty()) {
						EmotionalState objeto = new EmotionalState();
						objeto = dao.get();
						JSONObject temp = new JSONObject();
						temp.put("predicate", objeto.getPredicate());
						temp.put("start", objeto.getStart());
						temp.put("end", objeto.getEnd());
						temp.put("durability", objeto.getDurability());
						jArrayEmotionalState.put(temp);
					}
					dao.close();
				}

				//Recupera as informações relacionadas à saúde do usuário
				if (valueString.equals("Health")) {
					HealthDAO dao = new HealthDAO(context);
					if (!dao.isEmpty()) {
						ArrayList<Default> lista = new ArrayList<Default>(
								dao.getList());
						for (ListIterator<Default> iterator = lista
								.listIterator(); iterator.hasNext();) {
							Default objeto = iterator.next();
							JSONObject temp = new JSONObject();
							temp.put("auxiliary", objeto.getAuxiliary());
							temp.put("predicate", objeto.getPredicate());
							temp.put("range", objeto.getRange());
							temp.put("object", objeto.getObject());
							jArrayHealth.put(temp);
						}
					}
					dao.close();
				}

				//Recupera as informações relacionadas às caracterísricas pessoais do usuário
				if (valueString.equals("Characteristics")) {
					CharacteristicsDAO dao = new CharacteristicsDAO(context);
					if (!dao.isEmpty()) {
						ArrayList<Default> lista = new ArrayList<Default>(
								dao.getList());
						for (ListIterator<Default> iterator = lista
								.listIterator(); iterator.hasNext();) {
							Default objeto = iterator.next();
							JSONObject temp = new JSONObject();
							temp.put("predicate", objeto.getPredicate());
							jArrayCharacteristics.put(temp);
						}
					}
					dao.close();
				}

				//Recupera as informações relacionadas aos interesses do usuário
				if (valueString.equals("Interest")) {
					InterestDAO dao = new InterestDAO(context);
					if (!dao.isEmpty()) {
						ArrayList<Default> lista = new ArrayList<Default>(
								dao.getList());
						for (ListIterator<Default> iterator = lista
								.listIterator(); iterator.hasNext();) {
							Default objeto = iterator.next();
							JSONObject temp = new JSONObject();
							temp.put("auxiliary", objeto.getAuxiliary());
							temp.put("predicate", objeto.getPredicate());
							temp.put("range", objeto.getRange());
							temp.put("object", objeto.getObject());
							temp.put("subgroup", objeto.getSubgroup());
							jArrayInterest.put(temp);
						}
					}
					dao.close();
				}

				//Recupera as informações relacionadas às preferências de interface do usuário
				if (valueString.equals("InterfacePreferences")) {
					InterfacePreferencesDAO dao = new InterfacePreferencesDAO(
							context);
					if (!dao.isEmpty()) {
						ArrayList<Default> lista = new ArrayList<Default>(
								dao.getList());
						for (ListIterator<Default> iterator = lista
								.listIterator(); iterator.hasNext();) {
							Default objeto = iterator.next();
							JSONObject temp = new JSONObject();
							temp.put("auxiliary", objeto.getAuxiliary());
							temp.put("predicate", objeto.getPredicate());
							temp.put("range", objeto.getRange());
							temp.put("object", objeto.getObject());
							temp.put("subgroup", objeto.getSubgroup());
							jArrayInterfacePreferences.put(temp);
						}
					}
					dao.close();
				}

				//Cria array JSON com as informações relacionadas às habilidades no JSON
				if (map.retornaTabela(valueString) != null) {
					if (map.retornaTabela(valueString).equals("Abilities")) {
						AbilitiesDAO dao = new AbilitiesDAO(context);
						if (!dao.isEmpty()) {
							if (dao.get(valueString) != null) {
								Default objeto = dao.get(valueString);
								JSONObject temp = new JSONObject();
								temp.put("auxiliary", objeto.getAuxiliary());
								temp.put("predicate", objeto.getPredicate());
								temp.put("range", objeto.getRange());
								temp.put("object", objeto.getObject());
								temp.put("subgroup", objeto.getSubgroup());
								jArrayAbilities.put(temp);
							}
						}
						dao.close();
					}

					//Cria array JSON com as informações relacionadas aos dados demográficos no JSON
					if (map.retornaTabela(valueString).equals("Demographics")) {
						DemographicsDAO dao = new DemographicsDAO(context);
						if (!dao.isEmpty()) {
							if (dao.get(valueString) != null) {
								Default objeto = dao.get(valueString);
								JSONObject temp = new JSONObject();
								temp.put("auxiliary", objeto.getAuxiliary());
								temp.put("predicate", objeto.getPredicate());
								temp.put("range", objeto.getRange());
								temp.put("object", objeto.getObject());
								jArrayDemographics.put(temp);
							}
						}
						dao.close();
					}

					//Cria array JSON com as informações relacionadas ao estado emocional no JSON
					if (map.retornaTabela(valueString).equals("EmotionalState")) {
						EstadoEmocionalDAO dao = new EstadoEmocionalDAO(context);
						if (!dao.isEmpty()) {
							if (dao.get(valueString) != null) {
								EmotionalState objeto = dao.get(valueString);
								JSONObject temp = new JSONObject();
								temp.put("predicate", objeto.getPredicate());
								temp.put("start", objeto.getStart());
								temp.put("end", objeto.getEnd());
								temp.put("durability", objeto.getDurability());
								jArrayEmotionalState.put(temp);
							}
						}
						dao.close();
					}

					//Cria array JSON com as informações relacionadas à saúde no JSON
					if (map.retornaTabela(valueString).equals("Health")) {
						HealthDAO dao = new HealthDAO(context);
						if (!dao.isEmpty()) {
							if (dao.get(valueString) != null) {
								Default objeto = dao.get(valueString);
								JSONObject temp = new JSONObject();
								temp.put("auxiliary", objeto.getAuxiliary());
								temp.put("predicate", objeto.getPredicate());
								temp.put("range", objeto.getRange());
								temp.put("object", objeto.getObject());
								jArrayHealth.put(temp);
							}
						}
						dao.close();
					}

					//Cria array JSON com as informações relacionadas às características pessoais no JSON
					if (map.retornaTabela(valueString)
							.equals("Characteristics")) {
						CharacteristicsDAO dao = new CharacteristicsDAO(context);
						if (!dao.isEmpty()) {
							if (dao.get(valueString) != null) {
								Default objeto = dao.get(valueString);
								JSONObject temp = new JSONObject();
								temp.put("predicate", objeto.getPredicate());
								jArrayCharacteristics.put(temp);
							}
						}
						dao.close();
					}

					//Cria array JSON com as informações relacionadas aos interesses no JSON
					if (map.retornaTabela(valueString).equals("Interest")) {
						InterestDAO dao = new InterestDAO(context);
						if (!dao.isEmpty()) {
							if (dao.get(valueString) != null) {
								Default objeto = dao.get(valueString);
								JSONObject temp = new JSONObject();
								temp.put("auxiliary", objeto.getAuxiliary());
								temp.put("predicate", objeto.getPredicate());
								temp.put("range", objeto.getRange());
								temp.put("object", objeto.getObject());
								temp.put("subgroup", objeto.getSubgroup());
								jArrayInterest.put(temp);
							}
						}
						dao.close();
					}

					//Cria array JSON com as informações relacionadas às preferências de interface no JSON
					if (map.retornaTabela(valueString).equals(
							"InterfacePreferences")) {
						InterfacePreferencesDAO dao = new InterfacePreferencesDAO(
								context);
						if (!dao.isEmpty()) {
							if (dao.get(valueString) != null) {
								Default objeto = dao.get(valueString);
								JSONObject temp = new JSONObject();
								temp.put("auxiliary", objeto.getAuxiliary());
								temp.put("predicate", objeto.getPredicate());
								temp.put("range", objeto.getRange());
								temp.put("object", objeto.getObject());
								temp.put("subgroup", objeto.getSubgroup());
								jArrayInterfacePreferences.put(temp);
							}
						}
						dao.close();
					}
				}
			}

			//Inclui as informações relacionadas às habilidades no JSON
			if (jArrayAbilities.length() > 0) {
				userProfileJSON.put("abilities", jArrayAbilities);
			}

			//Inclui as informações relacionadas às características pessoais no JSON
			if (jArrayCharacteristics.length() > 0) {
				userProfileJSON.put("characteristics", jArrayCharacteristics);
			}

			//Inclui as informações relacionadas aos dados demográficos no JSON
			if (jArrayDemographics.length() > 0) {
				userProfileJSON.put("demographics", jArrayDemographics);
			}

			//Inclui as informações relacionadas ao estado emocional no JSON
			if (jArrayEmotionalState.length() > 0) {
				userProfileJSON.put("emotional_state", jArrayEmotionalState);
			}

			//Inclui as informações relacionadas à saúde no JSON
			if (jArrayHealth.length() > 0) {
				userProfileJSON.put("health", jArrayHealth);
			}

			//Inclui as informações relacionadas aos interesses no JSON
			if (jArrayInterest.length() > 0) {
				userProfileJSON.put("interest", jArrayInterest);
			}

			//Inclui as informações relacionadas às preferências de interface no JSON
			if (jArrayInterfacePreferences.length() > 0) {
				userProfileJSON.put("interface_preferences",
						jArrayInterfacePreferences);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return userProfileJSON;
	}

}
