package nexura.mac.ervic.webservice;

/**
 * Created by ervic on 20/10/17.
 */

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