package br.com.appescola.appescola.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.appescola.appescola.DAO.ConfiguracaoFirebase;
import br.com.appescola.appescola.Entidades.Professores;
import br.com.appescola.appescola.R;

public class PrincipalActivity extends AppCompatActivity {

    private ListView listDados;
    private DatabaseReference databasereference;
    private ArrayList<Professores> listProfessor;
    private ArrayAdapter<Professores> arrayAdapterProfessor;
    private ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        listDados = (ListView) findViewById(R.id.listViewUsuarios);
        listProfessor = new ArrayList<>();
        arrayAdapterProfessor = new ProfessoresAdapter(this, listProfessor);
        listDados.setAdapter(arrayAdapterProfessor);

        databasereference = ConfiguracaoFirebase.getFirebase().child("professor");

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listProfessor.clear();
                for(DataSnapshot objSnapShot:dataSnapshot.getChildren()){
                    Professores p = objSnapShot.getValue(Professores.class);
                    listProfessor.add(p);
                }
                arrayAdapterProfessor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        databasereference.addValueEventListener(eventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        databasereference.removeEventListener(eventListener);
    }

}
