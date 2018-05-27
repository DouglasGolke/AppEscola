package br.com.appescola.appescola.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import br.com.appescola.appescola.Entidades.Usuarios;
import br.com.appescola.appescola.Helper.Base64Custom;
import br.com.appescola.appescola.Helper.PreferenciasAndroid;
import br.com.appescola.appescola.R;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtCadMatricula;
    private EditText edtCadEmail;
    private EditText edtCadNome;
    private EditText edtCadSobrenome;
    private EditText edtCadSenha;
    private EditText edtCadConfirmaSenha;
    private EditText edtCadAnivesario;
    private RadioButton rbMasculino;
    private RadioButton rbFeminino;
    private Button btnGravar;
    private Usuarios usuarios;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtCadMatricula = (EditText) findViewById(R.id.edtCadMatricula);
        edtCadEmail = (EditText) findViewById(R.id.edtCadEmail);
        edtCadNome = (EditText) findViewById(R.id.edtCadNome);
        edtCadSobrenome = (EditText) findViewById(R.id.edtCadSobrenome);
        edtCadSenha = (EditText) findViewById(R.id.edtCadSenha);
        edtCadConfirmaSenha = (EditText) findViewById(R.id.edtCadConfirmarSenha);
        edtCadAnivesario = (EditText) findViewById(R.id.edtCadAniversario);
        rbFeminino = (RadioButton) findViewById(R.id.rbFeminino);
        rbMasculino = (RadioButton) findViewById(R.id.rbMasculino);
        btnGravar = (Button) findViewById(R.id.btnGravar);


        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtCadSenha.getText().toString().equals(edtCadConfirmaSenha.getText().toString())) {

                    boolean valido = true;
                    usuarios = new Usuarios();

                    usuarios.setMatricula(edtCadMatricula.getText().toString());
                    usuarios.setNome(edtCadNome.getText().toString());
                    usuarios.setEmail(edtCadEmail.getText().toString());
                    usuarios.setSenha(edtCadSenha.getText().toString());
                    usuarios.setAniversario(edtCadAnivesario.getText().toString());
                    usuarios.setSobrenome(edtCadSobrenome.getText().toString());

                    if (rbFeminino.isChecked()) {
                        usuarios.setSexo("Feminino");
                    } else {
                        usuarios.setSexo("Masculino");
                    }

                    if (usuarios.getEmail().equals("")){
                        edtCadEmail.setError(getString(R.string.obrigatorio));
                        valido = false;
                    }

                    if (usuarios.getNome().equals("")){
                        edtCadNome.setError(getString(R.string.obrigatorio));
                        valido = false;
                    }

                    if (usuarios.getMatricula().equals("")){
                        edtCadMatricula.setError(getString(R.string.obrigatorio));
                        valido = false;
                    }

                    if (usuarios.getSenha().equals("")){
                        edtCadSenha.setError(getString(R.string.obrigatorio));
                        valido = false;
                    }

                    if (usuarios.getAniversario().equals("")){
                        edtCadAnivesario.setError(getString(R.string.obrigatorio));
                        valido = false;
                    }

                    if (usuarios.getSobrenome().equals("")){
                        edtCadSobrenome.setError(getString(R.string.obrigatorio));
                        valido = false;
                    }

                    if (valido){
                        cadastrarUsuario();
                    }


                } else {
                    Toast.makeText(CadastroActivity.this, "As senhas não são correspondentes", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private void cadastrarUsuario() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuarios.getEmail(),
                usuarios.getSenha()
        ).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CadastroActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();

                    String idenficadorUsuario = Base64Custom.codificarBase64(usuarios.getEmail());
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuarios.setId(idenficadorUsuario);
                    usuarios.salvar();

                    PreferenciasAndroid preferenciasAndroid = new PreferenciasAndroid(CadastroActivity.this);
                    preferenciasAndroid.salvarUsuarioPrefencias(idenficadorUsuario, usuarios.getNome());

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
                    Toast.makeText(CadastroActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void abrirLoginUsuario() {
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
