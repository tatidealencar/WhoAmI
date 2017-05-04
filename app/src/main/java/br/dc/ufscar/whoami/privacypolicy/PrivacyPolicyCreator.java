//Mpnta política de privacidade a ser compartilhada com os sistemas que fizerem uso do aplicativo
package br.dc.ufscar.whoami.privacypolicy;

import java.util.HashMap;
import java.util.Map;

import br.dc.ufscar.whoami.model.policyprivacy.Expiry;
import br.dc.ufscar.whoami.model.policyprivacy.Purpose;
import br.dc.ufscar.whoami.model.policyprivacy.Recipient;
import br.dc.ufscar.whoami.model.policyprivacy.Retention;

public class PrivacyPolicyCreator {

	private Expiry expiry;
	private Purpose purpose;
	private Recipient recipient;
	private Retention retention;

	public PrivacyPolicyCreator() {
		expiry = new Expiry();
		purpose = new Purpose();
		recipient = new Recipient();
		retention = new Retention();

        /* Valores definidos com base na especificação do P3P
        http://www.w3.org/TR/P3P/
        Itens 2.3.2.3.2, 3.3.4, 3.3.5 e 3.3.6 */

		expiry.setType("reldate");
		expiry.setTime("604800");
		expiry.setUnitTime("second");
		purpose.setType("tailoring");
		purpose.setRequired("always");
		recipient.setType("same");
		recipient.setRequired("always");
		retention.setType("stated-purpose");
		retention.setTime("1200");
		retention.setUnitTime("second");
	}

	public Map<String, Object> getPrivacyPolicy() {

		Map<String, String> expiryMap = new HashMap<String, String>();
		expiryMap.put("type", expiry.getType());
		expiryMap.put("time", expiry.getTime());
		expiryMap.put("unit_time", expiry.getUnitTime());

		Map<String, String> purposeMap = new HashMap<String, String>();
		purposeMap.put("type", purpose.getType());
		purposeMap.put("required", purpose.getRequired());

		Map<String, String> recipientMap = new HashMap<String, String>();
		recipientMap.put("type", recipient.getType());
		recipientMap.put("required", recipient.getRequired());

		Map<String, String> retentionMap = new HashMap<String, String>();
		retentionMap.put("type", retention.getType());
		retentionMap.put("time", retention.getTime());
		retentionMap.put("unit_time", retention.getUnitTime());

		Map<String, Object> privacyPolicyItems = new HashMap<String, Object>();
		privacyPolicyItems.put("expiry", expiryMap);
		privacyPolicyItems.put("purpose", purposeMap);
		privacyPolicyItems.put("recipient", recipientMap);
		privacyPolicyItems.put("retention", retentionMap);

		Map<String, Object> privacyPolicy = new HashMap<String, Object>();
		privacyPolicy.put("privacy_policy", privacyPolicyItems);

		return privacyPolicy;
	}
}
