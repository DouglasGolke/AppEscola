package br.com.appescola.appescola.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.appescola.appescola.DAO.ConfiguracaoFirebase;
import br.com.appescola.appescola.Entidades.Usuarios;
import br.com.appescola.appescola.R;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnLogar;
    private TextView tvAbreCadastro;
    private FirebaseAuth autenticacao;
    private Usuarios usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnLogar = (Button) findViewById(R.id.btnLogar);
        tvAbreCadastro = (TextView) findViewById(R.id.tvAbreCadastro);

        tvAbreCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreCadastroUsuario();
            }
        });


        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtEmail.getText().toString().equals("") && !edtSenha.getText().toString().equals("")){

                    usuarios = new Usuarios();
                    usuarios.setEmail(edtEmail.getText().toString());
                    usuarios.setSenha(edtSenha.getText().toString());
                    validarLogin();

                }else{
                    Toast.makeText(LoginActivity.this, "Preencha os campos de email e senha", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void validarLogin(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuarios.getEmail(), usuarios.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirTelaPrincipal();
                    Toast.makeText(LoginActivity.this, "Login Efetuado com Sucesso", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Usuários e/ou senha inválidos", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void abrirTelaPrincipal() {
        Intent intentAbrirTelaPrincipal = new Intent(LoginActivity.this, PrincipalActivity.class);
        startActivity(intentAbrirTelaPrincipal);
    }


    public void abreCadastroUsuario() {
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }

}
