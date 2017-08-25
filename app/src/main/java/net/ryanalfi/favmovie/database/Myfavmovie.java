package net.ryanalfi.favmovie.database;

/**
 * Created by imac on 8/24/17.
 */

public class Myfavmovie {
    private int idmovie;
    private String statemovie;

    public Myfavmovie(){

    }

    public Myfavmovie(int idmovie, String statemovie) {
        this.idmovie = idmovie;
        this.statemovie = statemovie;
    }

    public String getStatemovie() {
        return statemovie;
    }

    public void setStatemovie(String statemovie) {
        this.statemovie = statemovie;
    }


    public int getIdmovie() {
        return idmovie;
    }

    public void setIdmovie(int idmovie) {
        this.idmovie = idmovie;
    }
}
