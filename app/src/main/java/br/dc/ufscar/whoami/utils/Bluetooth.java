//Utilidades para uso do Bluetooth no app
package br.dc.ufscar.whoami.utils;

import android.bluetooth.BluetoothAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import br.dc.ufscar.whoami.fragment.ActivateBluetoothDialogFragment;

public class Bluetooth {

	//retorna o endereço MAC do Bluetooth do dispositivo móvel
	public static String getMacAddress() {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();

		if (mBluetoothAdapter == null) {
			Log.d("TAG", "device does not support bluetooth");
			return null;
		}

		return mBluetoothAdapter.getAddress();
	}

	//Verifica se o Bluetooth está ativo e, caso não esteja, cria caixa de diálogo para usuário autorizar sua ativação
	public void verificaBluetooth(FragmentActivity context) {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		if (!mBluetoothAdapter.isEnabled()) {
			FragmentManager fm = context.getSupportFragmentManager();
			ActivateBluetoothDialogFragment alertaBluetooth = new ActivateBluetoothDialogFragment();
			alertaBluetooth.show(fm, "Alerta_Bluetooth");
		}
	}

}
