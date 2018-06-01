package br.com.appescola.appescola.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.appescola.appescola.Entidades.Professores;
import br.com.appescola.appescola.R;

class ProfessoresAdapter extends ArrayAdapter<Professores> {

    private ArrayList<Professores> professores;
    private Context context;

    public ProfessoresAdapter(Context c, ArrayList<Professores> objects) {
        super(c,0,objects);
        this.context = c;
        this.professores = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if (professores != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.aux_principal_layout, parent, false);

            TextView textViewNome = (TextView) view.findViewById(R.id.textViewNome);
            TextView textViewEmail = (TextView) view.findViewById(R.id.textViewEmail);
            TextView textViewHistoria = (TextView) view.findViewById(R.id.textViewHistoria);
            TextView textViewMatematica = (TextView) view.findViewById(R.id.textViewMatematica);
            TextView textViewGeografia = (TextView) view.findViewById(R.id.textViewGeografia);
            TextView textViewPortugues = (TextView) view.findViewById(R.id.textViewPortugues);
            TextView textViewFisica = (TextView) view.findViewById(R.id.textViewFisica);

            Professores professor = professores.get(position);
            textViewNome.setText(professor.getNome());
            textViewEmail.setText(professor.getEmail().toString());
//            textViewHistoria.setText(professor.getHistoria().toString());
//            textViewMatematica.setText(professor.getMatematica().toString());
//            textViewGeografia.setText(professor.getGeografia().toString());
//            textViewPortugues.setText(professor.getPortugues().toString());
//            textViewFisica.setText(professor.getFisica().toString());


            if(!professor.getHistoria().toString().isEmpty() || !professor.getHistoria().toString().contains("")){
                textViewHistoria.setText(professor.getHistoria().toString());
            }else{
                textViewHistoria.setVisibility(View.GONE);
            }

            if(!professor.getMatematica().toString().isEmpty() || !professor.getMatematica().toString().contains("") ){
                textViewMatematica.setText(professor.getMatematica().toString());
            }else{
                textViewMatematica.setVisibility(View.GONE);
            }

            if(!professor.getGeografia().toString().isEmpty()|| !professor.getGeografia().toString().contains("") ){
                textViewGeografia.setText(professor.getGeografia().toString());
            }else{
                textViewGeografia.setVisibility(View.GONE);
            }

            if(!professor.getPortugues().toString().isEmpty() || !professor.getPortugues().toString().contains("") ){
                textViewPortugues.setText(professor.getPortugues().toString());
            }else{
                textViewPortugues.setVisibility(View.GONE);
            }

            if(!professor.getFisica().toString().isEmpty() ||!professor.getFisica().toString().contains("")){
                textViewFisica.setText(professor.getFisica().toString());
            }else{
                textViewFisica.setVisibility(View.GONE);
            }

        }

        return view;
    }
}
