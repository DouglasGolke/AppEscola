package br.com.appescola.appescola.Activity;

import android.app.UiAutomation;
import android.os.health.UidHealthStats;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.util.UidVerifier;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
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

    private String email;
    private FirebaseAuth mAuth;
    private ListView listDados;
    private DatabaseReference databasereference;
    private ArrayList<Professores> listProfessor;
    private ArrayAdapter<Professores> arrayAdapterProfessor;
    private ValueEventListener eventListener;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        listDados = (ListView) findViewById(R.id.listViewUsuarios);
        listProfessor = new ArrayList<>();
        arrayAdapterProfessor = new ProfessoresAdapter(this, listProfessor);
        listDados.setAdapter(arrayAdapterProfessor);
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        email = autenticacao.getCurrentUser().getEmail();

        databasereference = ConfiguracaoFirebase.getFirebase().child("professor");
//        databasereference.child("professor").child(mAuth.getCurrentUser().getUid()).getDatabase();

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listProfessor.clear();
                for(DataSnapshot objSnapShot:dataSnapshot.getChildren()){
//                    if(dataSnapshot.getChildren().equals(mAuth.getUid())){
//                    Professores p = objSnapShot.getValue(Professores.class);
//                    listProfessor.add(p);}
//                }
//                arrayAdapterProfessor.notifyDataSetChanged();
                if (Objects.equal(objSnapShot.child("email").getValue(), email)) {
                    Professores p = objSnapShot.getValue(Professores.class);
                    listProfessor.add(p);
                }
                    arrayAdapterProfessor.notifyDataSetChanged();
            }

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
