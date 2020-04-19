package com.example.appclubes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appclubes.ATLETA.Atleta;

import java.util.ArrayList;
import java.util.List;

public class MyArrayAdapterAtletas extends ArrayAdapter<Atleta> {
    private List<Atleta> atletaListFull;

    public MyArrayAdapterAtletas(@NonNull Context context,@NonNull List<Atleta> atletaList)
    {
        super(context,0,atletaList);
        atletaListFull = new ArrayList<>(atletaList);
    }

    @NonNull
    @Override
    public Filter getFilter()
    {
        return atletaFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lista_row2, parent, false);
        }

        TextView id = convertView.findViewById(R.id.id);
        TextView NomeAtleta = convertView.findViewById(R.id.NomeAtleta);
        TextView EscalaoAtleta = convertView.findViewById(R.id.EscalaoAtleta);
        Atleta atletaItem = getItem(position);

        if (atletaItem != null){
            id.setText(atletaItem.getId());
            NomeAtleta.setText(atletaItem.getNome());
            EscalaoAtleta.setText(atletaItem.getEscalao());
        }
        return convertView;
    }

    private Filter atletaFilter = new Filter()
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Atleta> sugestao = new ArrayList<>();

            if (constraint == null || constraint.length() == 0)
            {
                sugestao.addAll(atletaListFull);
            }   else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Atleta item : atletaListFull)
                {
                    if (item.getNome().toLowerCase().contains(filterPattern)){
                        sugestao.add(item);
                    }
                }
            }
            results.values = sugestao;
            results.count = sugestao.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }
        @Override
        public CharSequence convertResultToString(Object resultValue)
        {
            return ((Atleta) resultValue).getNome();
        }
    };
}
