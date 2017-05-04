//Utilizado para criptografar a senha do usuário
package br.dc.ufscar.whoami.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ComputeHashMD5 {

	public static String compute(String password) {

		String hash = new String();
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			digest.update(password.getBytes());
			byte messageDigest[] = digest.digest();

			StringBuffer MD5Hash = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String h = Integer.toHexString(0xFF & messageDigest[i]);
				while (h.length() < 2)
					h = "0" + h;
				MD5Hash.append(h);
			}

			hash = MD5Hash.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hash;

	}
}
