package br.gov.ma.detran.examespraticosmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import br.gov.ma.detran.examespraticosmobile.R;
import br.gov.ma.detran.examespraticosmobile.adapter.holder.ListViewFaltasHolder;
import br.gov.ma.detran.examespraticosmobile.modeloEspecializada.AgendamentoLocalView;

public class AgendamentoCardViewAdapter extends BaseAdapter {
    Context c;
    List<AgendamentoLocalView> lista;

    public AgendamentoCardViewAdapter(Context context, List<AgendamentoLocalView> lista){
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
        ListViewFaltasHolder holder = null;

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) c
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.simplerow_faltas, viewGroup, false);
            holder = new ListViewFaltasHolder(row);
            row.setTag(holder);
        }else{
            holder = (ListViewFaltasHolder) row.getTag();
        }

        //holder.mDescricao.setText(lista.get(position).getDescricao());
        //holder.mQuantidade.setText(lista.get(position).getQuantidade());

        return row;
    }
}
