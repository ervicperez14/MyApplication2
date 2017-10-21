package nexura.mac.ervic.webservice;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by ervic on 20/10/17.
 */

public class EnviaPeticion {

    private String peticion;


    public EnviaPeticion(String peticion){

        this.peticion = peticion;
    }

    public StringRequest getRequest (Response.Listener<String> responseListener, Response.ErrorListener errorListener){
        final HashMap<String,String> credenciales =  new HashMap<>();
        credenciales.put("peticion",peticion);

        String url = "http://fablab.techadvancemx.com/getComent.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, responseListener, errorListener){
            @Override
            public String getBodyContentType(){
                return "application/json charset"+getParamsEncoding();
            }

            @Override
            public byte[] getBody(){

                try{
                    return new JSONObject(credenciales).toString().getBytes(getParamsEncoding());

                }catch (UnsupportedEncodingException e){


                }
                return  null;
            }
        };
        request.setRetryPolicy(new LongTimeoutAndTryRetryPolicy(LongTimeoutAndTryRetryPolicy.RETRIES_PHONE_ISP));
        return request;
    }
}
