package com.reserva;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Actividad2 extends Activity {

	String nombre = "", fecha = "", hora = "";
	int personas = 0;
	TextView muestraDatos;
	Spinner formasPago;
	Spinner acciones;
	String pago;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividad2);

		acciones=(Spinner)findViewById(R.id.acciones);
		formasPago=(Spinner)findViewById(R.id.modoPago);
		muestraDatos = (TextView) findViewById(R.id.muestraDatos);

		Bundle recibe = new Bundle();
		recibe = this.getIntent().getExtras();

		nombre = recibe.getString("nombre");
		personas = recibe.getInt("personas");
		fecha = recibe.getString("fecha");
		hora = recibe.getString("hora");

		muestraDatos.setText("Reservacion a nombre de:\n" + nombre + "\n" + personas
				+ " personas\nFecha: " + fecha + "\nHora: " + hora + "\n");
		formasPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				if(i!=0){
					pago=adapterView.getItemAtPosition(i).toString();
				}else
				{
					Toast.makeText(getApplicationContext(),"Selecciona una opción de pago",Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

		acciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				if(i>0){
					switch(i){
						case 1:
							mandarCorreo();
							break;
						case 2:
							googleMaps();
							break;
						case 3:
							abrirPaginaWeb();
							break;
						case 4:
							llamadaTelefono();
							break;
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

	}


	public void abrirPaginaWeb()
	{
		Intent intent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://hooters.com.mx/"));
		startActivity(intent);
	}

	public void llamadaTelefono()
	{
		Intent intent = new Intent(Intent.ACTION_CALL,
				Uri.parse("tel:56012627"));
		startActivity(intent);
	}
	public void mandarCorreo()
	{
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "Asunto: Reserva");
		intent.putExtra(Intent.EXTRA_TEXT, "Contenido del correo: Reservación en Hooters.");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "hootersuniv@gmail.com"} );
		startActivity(intent);
	}

	public void googleMaps()
	{
		Intent intent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("geo:19.37468,-99.16218"));
		startActivity(intent);
	}

    public void hacerOtraReserva(View v) {
        Intent envia = new Intent(this, MainActivity.class);
		Bundle datosRegreso=new Bundle();
		datosRegreso.putString("FormaPago",pago);
		envia.putExtras(datosRegreso);
        finish();
        startActivity(envia);
    }

}
