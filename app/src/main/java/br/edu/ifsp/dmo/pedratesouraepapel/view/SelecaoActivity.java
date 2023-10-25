package br.edu.ifsp.dmo.pedratesouraepapel.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pedratesouraepapel.R;

import java.util.Random;

import br.edu.ifsp.dmo.pedratesouraepapel.Constantes;
import br.edu.ifsp.dmo.pedratesouraepapel.model.Coisa;
import br.edu.ifsp.dmo.pedratesouraepapel.model.Papel;
import br.edu.ifsp.dmo.pedratesouraepapel.model.Pedra;
import br.edu.ifsp.dmo.pedratesouraepapel.model.Tesoura;

public class SelecaoActivity extends AppCompatActivity implements View.OnClickListener {

    private String nome;
    private int numero;

    private TextView textView;
    private Button pedraButton;
    private Button tesouraButton;
    private Button papelButton;
    private String modoJogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecao);

        Intent intent = getIntent();
        if (intent != null){
            nome = intent.getStringExtra(Constantes.KEY_NOME);
            numero = intent.getIntExtra(Constantes.KEY_NRO_JOGADOR, 0);
            if (intent.getStringExtra(Constantes.KEY_MODO_JOGO) != null){
                modoJogo = intent.getStringExtra(Constantes.KEY_MODO_JOGO);
            }
        }

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            getSupportActionBar().hide();
        }

        textView = findViewById(R.id.textview_mensagem);
        pedraButton = findViewById(R.id.buttonPedra);
        tesouraButton = findViewById(R.id.buttonTesoura);
        papelButton = findViewById(R.id.buttonPapel);

        textView.setText(nome + getString(R.string.escolha_arma));
        pedraButton.setOnClickListener(this);
        tesouraButton.setOnClickListener(this);
        papelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Coisa coisa = null;
        if (view == pedraButton){
            coisa = new Pedra();
        }else if (view == papelButton){
            coisa = new Papel();
        } else if (view == tesouraButton) {
            coisa = new Tesoura();
        }
        retornarResultado(coisa);
    }

    private void retornarResultado(Coisa coisa) {
        if (coisa == null){
            setResult(RESULT_CANCELED);
            finish();
        }else {
            if (modoJogo.equals("singlePlayer")){
                Coisa coisaRandom = sortearCoisa();
                Intent intent = new Intent();
                intent.putExtra(Constantes.KEY_NRO_JOGADOR, numero);
                intent.putExtra(Constantes.KEY_COISA, coisa);
                intent.putExtra("computer_result",coisaRandom);
                setResult(RESULT_OK, intent);
                finish();
            }else {
                Intent intent = new Intent();
                intent.putExtra(Constantes.KEY_NRO_JOGADOR, numero);
                intent.putExtra(Constantes.KEY_COISA, coisa);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    private Coisa sortearCoisa() {
        Random random = new Random();
        int numero = random.nextInt(3) + 1;

        if (numero == 1){
            return new Pedra();
        }else if (numero == 2){
            return new Papel();
        } else {
            return new Tesoura();
        }
    }
}