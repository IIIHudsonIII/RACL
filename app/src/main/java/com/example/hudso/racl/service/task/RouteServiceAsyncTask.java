package com.example.hudso.racl.service.task;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.example.hudso.racl.bean.PointBean;
import com.example.hudso.racl.bean.RouteBean;
import com.example.hudso.racl.service.RouteServices;
import com.example.hudso.racl.singleton.SingletonMaps;

/**
 * Class to execute device services.
 * Use services defined on class <code>{@link RouteServices}</code>.
 *
 * @author Hudson Henrique Lopes
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

    /**
     * Inteface to callback methods.
     */
    public interface Behaviour {
        void success();

        void failed();
    }

    /**
     * Search route.
     *
     * @param pointBean
     */
    public void find(PointBean pointBean) {
        execute(ActionEnum.FIND, pointBean);
    }

    protected void execute(@NonNull RouteServiceAsyncTask.ActionEnum action, PointBean pointBean) {
        if (pointBean != null) {
            System.out.println("RACL.LOG - (" + action.name() + ") Route by address: " + pointBean.getName() + " (" + pointBean.getLatLng() + ")");
            super.execute(new Pair<>(action, pointBean));
        } else {
            this.behaviour.failed();
        }
    }

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
        System.out.println("RACL.LOG - Route found on services: " + routeBean);
        SingletonMaps.getInstance().setRoute(routeBean);

        if (routeBean != null) {
            behaviour.success();
        } else {
            behaviour.failed();
        }
    }
}
