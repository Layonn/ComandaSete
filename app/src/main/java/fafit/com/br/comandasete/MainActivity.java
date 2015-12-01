package fafit.com.br.comandasete;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends Activity {
    // Declare Variables
    private static String url = "http://192.168.0.15:8080/br.com.comandaquatro/rest/hello/client";

    private static ArrayList<Category> categoryList;

    // Nome de atributos JSON
    private static final String TAG_CATEGORIAS = "categorias";
    private static final String TAG_ID = "idCategoria";
    private static final String TAG_NOME = "nomeCategoria";

    JSONArray categorias = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_main.xml
        setContentView(R.layout.activity_main);
        // Execute DownloadJSON AsyncTask
        //new DownloadJSON().execute();
    }

    class JSONAsyncTask extends AsyncTask<Void, Void, Boolean>{

        ProgressDialog pDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute(){
            pDialog.setMessage("Aguarde...");
            pDialog.show();
        }

        //##########DoInBackground##################
        @Override
        protected Boolean doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Recebido: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Obtendo o início o JSON
                    categorias = jsonObj.getJSONArray(TAG_CATEGORIAS);

                    // looping para obter todas as categorias
                    for (int i = 0; i < categorias.length(); i++) {
                        JSONObject c = categorias.getJSONObject(i);

                        Category category = new Category();

                        category.setId(c.getInt(TAG_ID));
                        category.setNome(c.getString(TAG_NOME));

                        //String id = c.getString(TAG_ID);
                        //String nome = c.getString(TAG_NOME);

                        // hashmap temporário para salvar uma instância
                        //HashMap<String, String> categoria = new HashMap<String, String>();

                        //Adicionando elementos ao hashmap
                        //categoria.put(TAG_ID, id);
                        //categoria.put(TAG_NOME, nome);

                        // Adicionando categoria à lista de categorias
                        categoryList.add(category);
                    }

                    return true;

                } catch (ParseException e1) {
                    e1.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Não foi possível obter nenhum dado da url");
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result){
            if(result == false){
                Toast.makeText(getApplicationContext(), "Não foi possível obter nenhum dado do servidor",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

}