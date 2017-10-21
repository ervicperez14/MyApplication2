package nexura.mac.ervic.webservice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by ervic on 23/08/17.
 */

public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder>  implements Filterable{
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<ProfileData> mDataOriginal = new ArrayList<>();
    private ArrayList<ProfileData> mDataFilter = new ArrayList<>();
    ItemFilter itemFilter = new ItemFilter();
    private ArrayList<ProfileData> filterproyectos = new ArrayList<>();
    private static MyClickListener myClickListener;

    @Override
    public Filter getFilter() {
        return itemFilter;
    }


    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView id_user, name, lastname, coment;


        public DataObjectHolder(View itemView) {
            super(itemView);
            id_user = (TextView) itemView.findViewById(R.id.id_user);
            name = (TextView) itemView.findViewById(R.id.name);
            lastname = (TextView) itemView.findViewById(R.id.lastname);
            coment = (TextView) itemView.findViewById(R.id.coment);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            
        }

    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter(ArrayList<ProfileData> myDataset) {
        this.mDataOriginal = myDataset;
        this.mDataFilter = myDataset;

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);

        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        holder.id_user.setText(mDataFilter.get(position).getId_user());
        holder.name.setText(mDataFilter.get(position).getName());
        holder.lastname.setText(mDataFilter.get(position).getLastname());
        holder.coment.setText(mDataFilter.get(position).getComent());

    }

    public void addItem(ProfileData dataObj, int index) {
        mDataOriginal.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataOriginal.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    private Bitmap get_imagen(String url) {
        Bitmap bm = null;
        try {
            URL _url = new URL(url);
            URLConnection con = _url.openConnection();
            con.connect();
            InputStream is = con.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {

        }
        return bm;
    }

    private class ItemFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String query = charSequence.toString().toLowerCase();
            FilterResults results = new FilterResults();
            final ArrayList<ProfileData> lista = mDataOriginal;
            final ArrayList<ProfileData> result_lista = new ArrayList<>(lista.size());
            for (int i = 0; i < lista.size(); i++){

                String str_nombre = lista.get(i).getName();
                if(str_nombre.toLowerCase().contains(query)){
                    result_lista.add(lista.get(i));

                }
            }
            results.values = result_lista;
            results.count = result_lista.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            mDataFilter = (ArrayList<ProfileData>) filterResults.values;
            notifyDataSetChanged();
        }
    }


}