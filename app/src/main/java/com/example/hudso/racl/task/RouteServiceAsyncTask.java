package com.example.hudso.racl.task;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.example.hudso.racl.bean.PointBean;
import com.example.hudso.racl.bean.RouteBean;
import com.example.hudso.racl.outro.RouteServices;
import com.example.hudso.racl.singleton.SingletonMaps;

/**
 * @author hudso
 * @since 03/12/2017
 */

public final class RouteServiceAsyncTask extends AsyncTask<Pair<RouteServiceAsyncTask.ActionEnum, PointBean>, Void, RouteBean> {

    public enum ActionEnum {
        FIND
    }

    private Behaviour behaviour;

    public RouteServiceAsyncTask(Behaviour behaviour) {
        this.behaviour = behaviour;
    }

    public interface Behaviour {
        void success();

        void failed();
    }

    public void find(PointBean pointBean) {
        execute(ActionEnum.FIND, pointBean);
    }

    protected void execute(@NonNull RouteServiceAsyncTask.ActionEnum action, PointBean pointBean) {
        if (pointBean != null) {
            System.out.println("Hudson - Informações do ponto a pesquisar: "
                    + pointBean.getName() + " - " + pointBean.getLatLng());
            super.execute(new Pair<>(action, pointBean));
        } else {
            this.behaviour.failed();
        }
    }

//    private ProgressDialog load;
//
//    @Override
//    protected void onPreExecute() {
//        load = ProgressDialog.show(behaviour.getApplicationContext(), "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
//    }


    @Override
    protected RouteBean doInBackground(Pair<RouteServiceAsyncTask.ActionEnum, PointBean>... params) {
        RouteServices rs = new RouteServices();
        switch (params[0].first) {
            case FIND:
                return rs.findByLatLng(params[0].second);
            default:
        }
        return null;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onPostExecute(RouteBean routeBean) {
//        if (load != null) {
//            load.dismiss();
//            load = null;
//        }

        System.out.println("Hudson.RouteServiceAsyncTask - Rota localizada: " + routeBean);
        SingletonMaps.getInstance().setRoute(routeBean);

        if (routeBean != null) {
            behaviour.success();
        } else {
            behaviour.failed();
        }
    }
}
