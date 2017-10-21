package nexura.mac.ervic.webservice;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Muestra extends AppCompatActivity implements Response.ErrorListener,Response.Listener<String>{
    public static ArrayList<ProfileData> lista, aux = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private ImageButton buscar_proyecto;
    private EditText edit_busqueda_proyectos;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button btn_mostrar;
    private SwipeRefreshLayout refreshLayout;
    private static final int CANTIDAD_ITEMS = 8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_proyectos);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

        btn_mostrar = (Button)findViewById(R.id.btn_mostrar);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new MyRecyclerViewAdapter(lista);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.invalidate();
        getData();
        // Iniciar la tarea asíncrona al revelar el indicador
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new HackingBackgroundTask().execute();
                    }
                }
        );
        refreshLayout.setColorSchemeResources(
                R.color.s1,
                R.color.s2,
                R.color.s3,
                R.color.s4
        );
        //lista = getDataSet();

    }

    private void getData(){
        EnviaPeticion enviaPeticion = new EnviaPeticion("1");
        Request<?> request = enviaPeticion.getRequest(this, this);
        AppController.getInstance().addToRequestQueue(request);

    }



    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

        GsonBuilder builder = new GsonBuilder();
        builder.setExclusionStrategies(new DefaultExclusionStrategy());
        Gson gson = builder.create();

        final Type collectionType = new TypeToken<List<ProfileData>>() {
        }.getType();
        final List<ProfileData> cursosGson = gson.fromJson(response.toString(), collectionType);

        for (int index = 0; index < cursosGson.size(); index++) {
            ProfileData obj = new ProfileData(cursosGson.get(index).getId_user(), cursosGson.get(index).getName(), cursosGson.get(index).getLastname(), cursosGson.get(index).getComent());
            aux.add(index, obj);
            Log.e("ERROR:I",cursosGson.get(index).getName());
        }


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class HackingBackgroundTask extends AsyncTask<Void, Void, List<Lista>> {

        static final int DURACION = 3 * 1000; // 3 segundos de carga

        @Override
        protected List doInBackground(Void... params) {
            // Simulación de la carga de items
            try {
                Thread.sleep(DURACION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Retornar en nuevos elementos para el adaptador
            return ConjuntoListas.randomList(CANTIDAD_ITEMS);
        }

        @Override
        protected void onPostExecute(List result) {
            super.onPostExecute(result);

            // Limpiar elementos antiguos
            adapter.clear();

            // Añadir elementos nuevos
            adapter.addAll(result);

            // Parar la animación del indicador
            refreshLayout.setRefreshing(false);
        }

    }
}
}
