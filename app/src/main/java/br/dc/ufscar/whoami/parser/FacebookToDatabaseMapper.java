//Salva no BD os dados recuperados do FB
package br.dc.ufscar.whoami.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import br.dc.ufscar.whoami.R;
import br.dc.ufscar.whoami.dao.DemographicsDAO;
import br.dc.ufscar.whoami.dao.InterestDAO;
import br.dc.ufscar.whoami.map.PreferencesInterfaceElementsMap;
import br.dc.ufscar.whoami.model.Default;

public class FacebookToDatabaseMapper {
	
	private String result;
	private final Activity context;

	private Default dadoIdade;
	private Default dadoSexo;
	private Default dadoPrimeiraLingua;
	private Default dadoSegundaLingua;
	private Default dadoEscolaridade;
	private Default dadoOcupacao;
	private Default filmes;
	private Default musicas;
	private Default jogos;
	private Default recreacao;
	private Default esportes;

	public FacebookToDatabaseMapper(String result, Activity context) {
		this.result = result;
		this.context = context;
	}

	public void map() {

		dadoIdade = new Default();
		dadoIdade.setPredicate("Age");
		dadoIdade.setRange("uninformed-years");
		dadoIdade.setAuxiliary("has");
		dadoIdade.setGroup("Demographics");
		dadoIdade.setId(R.id.idade);

		dadoSexo = new Default();
		dadoSexo.setPredicate("Gender");
		dadoSexo.setRange("uninformed-male-female");
		dadoSexo.setAuxiliary("has");
		dadoSexo.setGroup("Demographics");

		dadoPrimeiraLingua = new Default();
		dadoPrimeiraLingua.setPredicate("FirstLanguage");
		dadoPrimeiraLingua.setRange("languages");
		dadoPrimeiraLingua.setAuxiliary("hasKnowledge");
		dadoPrimeiraLingua.setGroup("Demographics");

		dadoSegundaLingua = new Default();
		dadoSegundaLingua.setPredicate("SecondLanguage");
		dadoSegundaLingua.setRange("languages");
		dadoSegundaLingua.setAuxiliary("hasKnowledge");
		dadoSegundaLingua.setGroup("Demographics");

		dadoEscolaridade = new Default();
		dadoEscolaridade.setPredicate("EducationLevel");
		dadoEscolaridade.setRange("uninformed-basic-primary-secondary-higher");
		dadoEscolaridade.setAuxiliary("has");
		dadoEscolaridade.setGroup("Demographics");

		dadoOcupacao = new Default();
		dadoOcupacao.setPredicate("Employment");
		dadoOcupacao.setRange("uninformed-jobs");
		dadoOcupacao.setAuxiliary("has");
		dadoOcupacao.setGroup("Demographics");
		dadoOcupacao.setId(R.id.ocupacao);

		filmes = new Default();
		filmes.setRange("yes-no");
		filmes.setObject("yes");
		filmes.setAuxiliary("hasInterest");
		filmes.setGroup("Interest");

		musicas = new Default();
		musicas.setRange("yes-no");
		musicas.setObject("yes");
		musicas.setAuxiliary("hasInterest");
		musicas.setSubgroup("MusicGenre");
		musicas.setGroup("Interest");

		jogos = new Default();
		jogos.setRange("yes-no");
		jogos.setObject("yes");
		jogos.setAuxiliary("hasInterest");
		jogos.setSubgroup("Games");
		jogos.setGroup("Interest");

		recreacao = new Default();
		recreacao.setRange("yes-no");
		recreacao.setObject("yes");
		recreacao.setAuxiliary("hasInterest");
		recreacao.setSubgroup("Recreation");
		recreacao.setGroup("Interest");

		esportes = new Default();
		esportes.setRange("yes-no");
		esportes.setObject("yes");
		esportes.setAuxiliary("hasInterest");
		esportes.setSubgroup("Sports");
		esportes.setGroup("Interest");

		try {
			JSONObject jObj = new JSONObject(result);

			DemographicsDAO demographicsDAO = new DemographicsDAO(context);
			InterestDAO interestDAO = new InterestDAO(context);

			PreferencesInterfaceElementsMap map = new PreferencesInterfaceElementsMap();

			// Idade
			dadoIdade.setObject(jObj.getString("Age"));
			if (!demographicsDAO.estaNaLista(dadoIdade.getPredicate())) {
				demographicsDAO.salva(dadoIdade);
			} else {
				demographicsDAO.altera(dadoIdade);
			}

			// Escolaridade
			dadoEscolaridade.setObject(jObj.getString("EducationLevel"));
			if (dadoEscolaridade.getObject().equals("basic")) {
				dadoEscolaridade.setId(R.id.sem_escolaridade);
			} else {
				if (dadoEscolaridade.getObject().equals("primary")) {
					dadoEscolaridade.setId(R.id.ensino_fundamental);
				} else {
					if (dadoEscolaridade.getObject().equals("secondary")) {
						dadoEscolaridade.setId(R.id.ensino_medio);
					} else {
						if (dadoEscolaridade.getObject().equals("higher")) {
							dadoEscolaridade.setId(R.id.ensino_superior);
						}
					}
				}
			}
			if (!demographicsDAO.estaNaLista(dadoEscolaridade.getPredicate())) {
				demographicsDAO.salva(dadoEscolaridade);
			} else {
				demographicsDAO.altera(dadoEscolaridade);
			}

			// Ocupação
			dadoOcupacao.setObject(jObj.getString("Employment"));
			if (!demographicsDAO.estaNaLista(dadoOcupacao.getPredicate())) {
				demographicsDAO.salva(dadoOcupacao);
			} else {
				demographicsDAO.altera(dadoOcupacao);
			}

			// Primeira língua
			dadoPrimeiraLingua.setObject(jObj.getString("FirstLanguage"));
			if (retornaIdIdioma(dadoPrimeiraLingua.getObject()) != -1) {
				dadoPrimeiraLingua.setId(retornaIdIdioma(dadoPrimeiraLingua
						.getObject()));
				if (!demographicsDAO.estaNaLista(dadoPrimeiraLingua
						.getPredicate())) {
					demographicsDAO.salva(dadoPrimeiraLingua);
				} else {
					demographicsDAO.altera(dadoPrimeiraLingua);
				}
			}

			// Segunda língua
			dadoSegundaLingua.setObject(jObj.getString("SecondLanguage"));
			if (retornaIdIdioma(dadoSegundaLingua.getObject()) != -1) {
				dadoSegundaLingua.setId(retornaIdIdioma(dadoSegundaLingua
						.getObject()));
				if (!demographicsDAO.estaNaLista(dadoSegundaLingua
						.getPredicate())) {
					demographicsDAO.salva(dadoSegundaLingua);
				} else {
					demographicsDAO.altera(dadoSegundaLingua);
				}
			}

			// Sexo
			dadoSexo.setObject(jObj.getString("Gender"));
			if (dadoSexo.getObject().equals("male")) {
				dadoSexo.setId(R.id.masculino);
			} else {
				if (dadoSexo.getObject().equals("female")) {
					dadoSexo.setId(R.id.feminino);
				}
			}
			if (!demographicsDAO.estaNaLista(dadoSexo.getPredicate())) {
				demographicsDAO.salva(dadoSexo);
			} else {
				demographicsDAO.altera(dadoSexo);
			}

			// Filmes
			JSONArray jArrFilmes = jObj.getJSONArray("Film");
			for (int i = 0; i < jArrFilmes.length(); i++) {
				JSONObject obj = jArrFilmes.getJSONObject(i);
				JSONArray names = obj.names();
				String predicate = (String) names.get(0);
				// String object = obj.getString(predicate);
				filmes.setPredicate(predicate);
				// filmes.setObject(object);
				filmes.setId(map.retornaIdFilme(predicate));
				if (!interestDAO.estaNaLista(filmes.getPredicate())) {
					interestDAO.salva(filmes);
				} else {
					interestDAO.altera(filmes);
				}
			}

			// Musicas
			JSONArray jArrMusicas = jObj.getJSONArray("MusicGenre");
			for (int i = 0; i < jArrMusicas.length(); i++) {
				JSONObject obj = jArrMusicas.getJSONObject(i);
				JSONArray names = obj.names();
				String predicate = (String) names.get(0);
				// String object = obj.getString(predicate);
				musicas.setPredicate(predicate);
				// musicas.setObject(object);
				musicas.setId(map.retornaIdMusica(predicate));
				if (!interestDAO.estaNaLista(musicas.getPredicate())) {
					interestDAO.salva(musicas);
				} else {
					interestDAO.altera(musicas);
				}
			}

			// Jogos
			JSONArray jArrJogos = jObj.getJSONArray("Games");
			for (int i = 0; i < jArrJogos.length(); i++) {
				JSONObject obj = jArrJogos.getJSONObject(i);
				JSONArray names = obj.names();
				String predicate = (String) names.get(0);
				// String object = obj.getString(predicate);
				jogos.setPredicate(predicate);
				// jogos.setObject(object);
				jogos.setId(map.retornaIdJogos(predicate));
				if (!interestDAO.estaNaLista(jogos.getPredicate())) {
					interestDAO.salva(jogos);
				} else {
					interestDAO.altera(jogos);
				}
			}

			// Recreacao
			JSONArray jArrRecreacao = jObj.getJSONArray("Recreation");
			for (int i = 0; i < jArrRecreacao.length(); i++) {
				JSONObject obj = jArrRecreacao.getJSONObject(i);
				JSONArray names = obj.names();
				String predicate = (String) names.get(0);
				// String object = obj.getString(predicate);
				recreacao.setPredicate(predicate);
				// recreacao.setObject(object);
				recreacao.setId(map.retornaIdRecreacao(predicate));

				if (!interestDAO.estaNaLista(recreacao.getPredicate())) {
					interestDAO.salva(recreacao);
				} else {
					interestDAO.altera(recreacao);
				}
			}

			// Esportes
			JSONArray jArrEsportes = jObj.getJSONArray("Sports");
			for (int i = 0; i < jArrEsportes.length(); i++) {
				JSONObject obj = jArrEsportes.getJSONObject(i);
				JSONArray names = obj.names();
				String predicate = (String) names.get(0);
				// String object = obj.getString(predicate);
				esportes.setPredicate(predicate);
				// esportes.setObject(object);
				esportes.setId(map.retornaIdEsportes(predicate));
				if (!interestDAO.estaNaLista(esportes.getPredicate())) {
					interestDAO.salva(esportes);
				} else {
					interestDAO.altera(esportes);
				}
			}

			demographicsDAO.close();
			interestDAO.close();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int retornaIdIdioma(String idioma) {
		String[] idiomas = context.getResources().getStringArray(
				R.array.idiomas);

		int id = -1;
		for (int i = 0; i < idiomas.length; i++) {
			if (idioma.equals(idiomas[i])) {

				id = i;

				return id;
			}
		}
		return id;
	}
}
