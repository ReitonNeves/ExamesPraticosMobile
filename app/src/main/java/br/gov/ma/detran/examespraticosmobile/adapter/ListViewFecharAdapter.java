package br.gov.ma.detran.examespraticosmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import br.gov.ma.detran.examespraticosmobile.R;
import br.gov.ma.detran.examespraticosmobile.adapter.holder.ListViewFecharHolder;
import br.gov.ma.detran.examespraticosmobile.modeloEspecializada.ListViewFechar;

public class ListViewFecharAdapter  extends BaseAdapter {
    Context c;
    List<ListViewFechar> lista;

    public ListViewFecharAdapter(Context context, List<ListViewFechar> lista){
        this.c = context;
        this.lista = lista;
    }
    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = view;
        ListViewFecharHolder holder = null;
        if(row == null){
            LayoutInflater inflater = (LayoutInflater) c
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.simplerow_fechar, viewGroup, false);
            holder = new ListViewFecharHolder(row);
            row.setTag(holder);
        }else{
            holder = (ListViewFecharHolder) row.getTag();
        }

        holder.mNome.setText(lista.get(position).getNomeCandidato());
        holder.mResultado.setText(lista.get(position).getResultado());

        return row;
    }
}
