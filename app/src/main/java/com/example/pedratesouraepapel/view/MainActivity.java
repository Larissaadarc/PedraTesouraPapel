package com.example.pedratesouraepapel.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.provider.SyncStateContract;

import com.example.pedratesouraepapel.R;

import br.edu.ifsp.dmo.pedratesouraepapel.Constantes;
import br.edu.ifsp.dmo.pedratesouraepapel.view.ConfrontoActivity;

    public class MainActivity extends AppCompatActivity {

        private Spinner mSpinner;
        private Button mButton;
        private EditText nome1EditText;
        private EditText nome2EditText;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            ActionBar toolbar = getSupportActionBar();
            if(toolbar != null){
                toolbar.hide();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    getResources().getStringArray(R.array.tipos_jogos));

            mSpinner = findViewById(R.id.spinner_jogadas);
            mSpinner.setAdapter(adapter);

            nome1EditText = findViewById(R.id.edittext_jogador1);
            nome2EditText = findViewById(R.id.edittext_jogador2);

            mButton = findViewById(R.id.button_iniciar);
            mButton.setOnClickListener(view -> iniciarJogo());
        }

        private void iniciarJogo(){
            String player1, player2;
            int batalhas = 1;

            player1 = nome1EditText.getText().toString();
            player2 = nome2EditText.getText().toString();

            switch (mSpinner.getSelectedItemPosition()){
                case 0:
                    batalhas = 1;
                    break;
                case 1:
                    batalhas = 3;
                    break;
                case 2:
                    batalhas = 5;
                    break;
            }

            Intent intent = new Intent(this, ConfrontoActivity.class);
            Bundle args = new Bundle();
            args.putString(br.edu.ifsp.dmo.pedratesouraepapel.Constantes.KEY_JOGADOR_1, player1);
            args.putString(br.edu.ifsp.dmo.pedratesouraepapel.Constantes.KEY_JOGADOR_2, player2);
            args.putInt(Constantes.KEY_RODADAS, batalhas);
            intent.putExtras(args);
            startActivity(intent);
        }
    }