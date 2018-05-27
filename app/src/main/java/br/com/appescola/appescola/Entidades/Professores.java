package br.com.appescola.appescola.Entidades;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import br.com.appescola.appescola.DAO.ConfiguracaoFirebase;

public class Professores {

    private String id;
    private String matricula;
    private String email;
    private String senha;
    private String nome;
    private String sobrenome;
    private String aniversario;
    private String maisMateria;
    private String disciplinas;

    public Professores() {
    }

    public void salvar() {
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("professor").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("id", getId());
        hashMapUsuario.put("matricula", getMatricula());
        hashMapUsuario.put("email", getEmail());
        hashMapUsuario.put("senha", getSenha());
        hashMapUsuario.put("nome", getNome());
        hashMapUsuario.put("sobrenome", getSobrenome());
        hashMapUsuario.put("aniversario", getAniversario());
        hashMapUsuario.put("maisMateria", getMaisMateria());
        hashMapUsuario.put("disciplinas", getDisciplinas());

        return hashMapUsuario;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getAniversario() {
        return aniversario;
    }

    public void setAniversario(String aniversario) {
        this.aniversario = aniversario;
    }

    public String getMaisMateria() {
        return maisMateria;
    }

    public void setMaisMateria(String maisMateria) {
        this.maisMateria = maisMateria;
    }

    public String getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(String disciplinas) {
        this.disciplinas = disciplinas;
    }
}
