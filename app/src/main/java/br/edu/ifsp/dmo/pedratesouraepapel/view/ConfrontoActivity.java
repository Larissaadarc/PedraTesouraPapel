package br.edu.ifsp.dmo.pedratesouraepapel.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import br.edu.ifsp.dmo.pedratesouraepapel.Constantes;
import br.edu.ifsp.dmo.pedratesouraepapel.R;
import br.edu.ifsp.dmo.pedratesouraepapel.model.Coisa;
import br.edu.ifsp.dmo.pedratesouraepapel.model.Confronto;
import br.edu.ifsp.dmo.pedratesouraepapel.model.Jogador;

public class ConfrontoActivity extends AppCompatActivity {

    private Confronto confronto;
    private Coisa coisaPlayer1;
    private Coisa coisaPlayer2;
    private ActivityResultLauncher<Intent> resultLauncher;
    private Button coisa1Button;
    private Button coisa2Button;
    private Button lutarButton;
    private Button fecharButton;
    private TextView labelP1TextView;
    private TextView labelP2TextView;
    private TextView resultadoP1TextView;
    private TextView resultadoP2TextView;
    private TextView anuncioTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confronto);

        Intent intent = getIntent();
        if(intent != null){
            Bundle args = intent.getExtras();
            if(args != null){
                String n1 = args.getString(Constantes.KEY_JOGADOR_1);
                String n2 = args.getString(Constantes.KEY_JOGADOR_2);
                int nro = args.getInt(Constantes.KEY_RODADAS);
                confronto = new Confronto(nro,n1,n2);
            }
        }

        coisaPlayer1 = null;
        coisaPlayer2 = null;

        findById();
        updateUI();
        setClickListener();

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
          if(result.getResultCode() == RESULT_OK){
              Intent intentResultado = result.getData();
              int jogador = intentResultado.getIntExtra(Constantes.KEY_NRO_JOGADOR,0);
              Coisa coisa = (Coisa) intentResultado.getSerializableExtra(Constantes.KEY_COISA);
              if(jogador == 1){
                  coisaPlayer1 = coisa;
              }
              if(jogador == 2){
                  coisaPlayer2 = coisa;
              }
          }
        });
    }

    private void abrirSelecao(int i){
        Intent intent = new Intent(this, SelecaoActivity.class);
        intent.putExtra(Constantes.KEY_NRO_JOGADOR, i);
        if(i == 1){
            intent.putExtra(Constantes.KEY_NOME, confronto.getJogador1().getNome());
        }else {
            intent.putExtra(Constantes.KEY_NOME, confronto.getJogador2().getNome());
        }
        resultLauncher.launch(intent);
    }

    public void executarConfronto(){
        Jogador vencedor;
        if(coisaPlayer1 != null && coisaPlayer2 != null){
            vencedor = confronto.novoConfronto(coisaPlayer1, coisaPlayer2);
            if(vencedor != null){
                Toast.makeText(this, getString(R.string.winner)+vencedor.getNome(), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, getString(R.string.empate), Toast.LENGTH_SHORT).show();
            }
            coisaPlayer1 = null;
            coisaPlayer2 = null;
            atualizarPlacar();
            if(!confronto.tem_batalha()){
                anuciarVencedor(confronto.getVencedor());
            }else {
                String jogador;
                if(coisaPlayer1 == null){
                    jogador = confronto.getJogador1().getNome();
                }else {
                    jogador = confronto.getJogador2().getNome();
                }
                Toast.makeText(this, jogador + getString(R.string.choose_gum), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void anuciarVencedor(Jogador vencedor){
        String str = confronto.getVencedor().getNome() + getString(R.string.venceu_o_confronto);
        coisa1Button.setVisibility(View.GONE);
        coisa2Button.setVisibility(View.GONE);
        lutarButton.setVisibility(View.GONE);
        fecharButton.setVisibility(View.VISIBLE);
        anuncioTextView.setVisibility(View.VISIBLE);
        anuncioTextView.setText(str);
    }

    private void atualizarPlacar(){
        resultadoP1TextView.setText(String.valueOf(confronto.getJogador1().getPontos()));
        resultadoP2TextView.setText(String.valueOf(confronto.getJogador2().getPontos()));
    }

    private void findById(){
        coisa1Button = findViewById(R.id.button_coisa1);
        coisa2Button = findViewById(R.id.button_coisa2);
        lutarButton = findViewById(R.id.button_lutar);
        fecharButton = findViewById(R.id.button_fechar);
        labelP1TextView = findViewById(R.id.label_jogador1);
        labelP2TextView = findViewById(R.id.label_jogador2);
        resultadoP1TextView = findViewById(R.id.textview_resultado1);
        resultadoP2TextView = findViewById(R.id.textview_resultado2);
        anuncioTextView = findViewById(R.id.textview_anuncio);
    }

    private void updateUI(){}

    private void setClickListener(){}
}
