//Fragment para exibição de caixa de diálogo ao usuário para ativação do Bluetooth
package br.dc.ufscar.whoami.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import br.dc.ufscar.whoami.R;

public class ActivateBluetoothDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		builder.setView(
				inflater.inflate(R.layout.activate_bluetooth_dialog, null)).setPositiveButton(R.string.ativar_bluetooth,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
										.getDefaultAdapter();
								mBluetoothAdapter.enable();
							}
						})
				.setNegativeButton(R.string.cancelar,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User cancelled the dialog
							}
						});
		return builder.create();
	}

}
