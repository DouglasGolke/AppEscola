package br.com.appescola.appescola.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import br.com.appescola.appescola.DAO.ConfiguracaoFirebase;
import br.com.appescola.appescola.Entidades.Professores;
import br.com.appescola.appescola.Entidades.Usuarios;
import br.com.appescola.appescola.Helper.Base64Custom;
import br.com.appescola.appescola.Helper.PreferenciasAndroid;
import br.com.appescola.appescola.R;

public class CadastroProfessorActivity extends AppCompatActivity {

    private EditText edtCadEmail;
    private EditText edtCadMatricula;
    private EditText edtCadNome;
    private EditText edtCadSobrenome;
    private EditText edtCadSenha;
    private EditText edtCadConfirmaSenha;
    private EditText edtCadAnivesario;
    private CheckBox edtCadHistoria;
    private CheckBox edtCadMatematica;
    private CheckBox edtCadPortugues;
    private CheckBox edtCadGeografia;
    private CheckBox edtCadFisica;
    private RadioButton rbSim;
    private RadioButton rbNao;
    private Button btnGravar;
    private Professores professores;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_professor);

        edtCadMatematica = (CheckBox) findViewById(R.id.edtCadMatematica);
        edtCadFisica = (CheckBox) findViewById(R.id.edtCadFisica);
        edtCadPortugues = (CheckBox) findViewById(R.id.edtCadPortugues);
        edtCadGeografia = (CheckBox) findViewById(R.id.edtCadGeografia);
        edtCadHistoria = (CheckBox) findViewById(R.id.edtCadHistoria);
        edtCadMatricula = (EditText) findViewById(R.id.edtCadMatricula);
        edtCadEmail = (EditText) findViewById(R.id.edtCadEmail);
        edtCadNome = (EditText) findViewById(R.id.edtCadNome);
        edtCadSobrenome = (EditText) findViewById(R.id.edtCadSobrenome);
        edtCadSenha = (EditText) findViewById(R.id.edtCadSenha);
        edtCadConfirmaSenha = (EditText) findViewById(R.id.edtCadConfirmarSenha);
        edtCadAnivesario = (EditText) findViewById(R.id.edtCadAniversario);
        rbSim = (RadioButton) findViewById(R.id.rbSim);
        rbNao = (RadioButton) findViewById(R.id.rbNao);
        btnGravar = (Button) findViewById(R.id.btnGravar);


        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtCadSenha.getText().toString().equals(edtCadConfirmaSenha.getText().toString())) {

                    boolean valido = true;
                    professores = new Professores();

                    professores.setMatricula(edtCadMatricula.getText().toString());
                    professores.setNome(edtCadNome.getText().toString());
                    professores.setEmail(edtCadEmail.getText().toString());
                    professores.setSenha(edtCadSenha.getText().toString());
                    professores.setAniversario(edtCadAnivesario.getText().toString());
                    professores.setSobrenome(edtCadSobrenome.getText().toString());

                    if (rbSim.isChecked()) {
                        professores.setMaisMateria("Sim, leciona mais disciplinas");
                    } else {
                        professores.setMaisMateria("Não, leciona uma disciplina");
                    }

                    if (edtCadMatematica.isChecked()) {
                        professores.setMatematica("Matematica");
                    } else {
                        professores.setMatematica("");
                    }

                    if (edtCadPortugues.isChecked()) {
                        professores.setPortugues("Portugues");
                    } else {
                        professores.setPortugues("");
                    }

                    if (edtCadFisica.isChecked()) {
                        professores.setFisica("Fisica");
                    } else {
                        professores.setFisica("");
                    }

                    if (edtCadGeografia.isChecked()) {
                        professores.setGeografia("Geografia");
                    } else {
                        professores.setGeografia("");
                    }

                    if (edtCadHistoria.isChecked()) {
                        professores.setHistoria("Historia");
                    } else {
                        professores.setHistoria("");
                    }

                    if (professores.getEmail().equals("")){
                        edtCadEmail.setError(getString(R.string.obrigatorio));
                        valido = false;
                    }

                    if (professores.getNome().equals("")){
                        edtCadNome.setError(getString(R.string.obrigatorio));
                        valido = false;
                    }

                    if (professores.getMatricula().equals("")){
                        edtCadMatricula.setError(getString(R.string.obrigatorio));
                        valido = false;
                    }

                    if (professores.getSenha().equals("")){
                        edtCadSenha.setError(getString(R.string.obrigatorio));
                        valido = false;
                    }

                    if (professores.getAniversario().equals("")){
                        edtCadAnivesario.setError(getString(R.string.obrigatorio));
                        valido = false;
                    }

                    if (professores.getSobrenome().equals("")){
                        edtCadSobrenome.setError(getString(R.string.obrigatorio));
                        valido = false;
                    }

                    if (valido){
                        cadastrarUsuario();
                    }


                } else {
                    Toast.makeText(CadastroProfessorActivity.this, "As senhas não são correspondentes", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private void cadastrarUsuario() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                professores.getEmail(),
                professores.getSenha()
        ).addOnCompleteListener(CadastroProfessorActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CadastroProfessorActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();

                    String idenficadorUsuario = Base64Custom.codificarBase64(professores.getEmail());
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    professores.setId(idenficadorUsuario);
                    professores.salvar();

                    PreferenciasAndroid preferenciasAndroid = new PreferenciasAndroid(CadastroProfessorActivity.this);
                    preferenciasAndroid.salvarUsuarioPrefencias(idenficadorUsuario, professores.getNome());

                    abrirLoginUsuario();
                } else {
                    String erroExcecao = "";

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = " Digite uma senha mais forte, contendo no mínimo 8 caracteres de letras e números";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = " O e-mail digitado é inválido, digite um novo e-mail";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "Esse e-mail já está cadastrado no sistema";
                    } catch (Exception e) {
                        erroExcecao = "Erro ao efetuar o cadastro!";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroProfessorActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void abrirLoginUsuario() {
        Intent intent = new Intent(CadastroProfessorActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
