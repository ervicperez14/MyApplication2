package nexura.mac.ervic.webservice;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ervic on 20/10/17.
 */

public class ProfileData {
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("name")
    private String name;
    @SerializedName("lastname")
    private String lastname;
    @SerializedName("coment")
    private String coment;

    public ProfileData(String id_user, String name, String lastname, String coment) {
        this.id_user = id_user;
        this.name = name;
        this.lastname = lastname;
        this.coment = coment;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }
}
