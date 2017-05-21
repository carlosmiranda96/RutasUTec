package com.example.alexander.rutasutec;

/**
 * Created by Alexander on 20/05/2017.
 */

public class AccesoInternet {
    public Boolean verificar() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
