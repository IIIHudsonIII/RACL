package com.example.hudso.racl.bean;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hudso on 16/10/2017.
 */

public class RouteBean {
    private String id;
    private String name;
    private double latitude_min;
    private double latitude_max;
    private double longitude_min;
    private double longitude_max;
    private String week_days;
    private List<PointBean> points;
    private List<LatLng> drawPoints;
    private List<ScheduleBean> schedules;

    public RouteBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public RouteBean(String id, String name, String week_days) {
        this.id = id;
        this.name = name;
        this.week_days = week_days;
    }

    public RouteBean(String id, String name, double latitude_min, double latitude_max, double longitude_min, double longitude_max, String week_days) {
        this.id = id;
        this.name = name;
        this.latitude_min = latitude_min;
        this.latitude_max = latitude_max;
        this.longitude_min = longitude_min;
        this.longitude_max = longitude_max;
        this.week_days = week_days;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name == null || "".equals(name) ? "(Não informado)" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude_min() {
        return latitude_min;
    }

    public void setLatitude_min(double latitude_min) {
        this.latitude_min = latitude_min;
    }

    public double getLatitude_max() {
        return latitude_max;
    }

    public void setLatitude_max(double latitude_max) {
        this.latitude_max = latitude_max;
    }

    public double getLongitude_min() {
        return longitude_min;
    }

    public void setLongitude_min(double longitude_min) {
        this.longitude_min = longitude_min;
    }

    public double getLongitude_max() {
        return longitude_max;
    }

    public void setLongitude_max(double longitude_max) {
        this.longitude_max = longitude_max;
    }

    public String getWeek_days() {
        return week_days;
    }

    public void setWeek_days(String week_days) {
        this.week_days = week_days;
    }

    public List<ScheduleBean> getSchedules() {
        if (schedules == null) {
            schedules = new ArrayList<ScheduleBean>();
        }
        return schedules;
    }

    public void setSchedules(List<ScheduleBean> schedules) {
        this.schedules = schedules;
    }

    public List<PointBean> getPoints() {
        if (points == null) {
            points = new ArrayList<PointBean>();
        }
        return points;
    }

    public List<LatLng> getDrawPoints() {
        if (drawPoints == null) {
            drawPoints = new ArrayList<LatLng>();
        }
        return drawPoints;
    }

    public void setDrawPoints(List<LatLng> drawPoints) {
        this.drawPoints = drawPoints;
    }

    public void addPoint(String id, String name, double latitude, double longitude) {
        PointBean pointBean = new PointBean(id, name, latitude, longitude);
        getPoints().add(pointBean);
    }

    public void addSchedule(String id, String week_day, String initial_hour, String final_hour, String information, String id_device) {
        ScheduleBean scheduleBean = new ScheduleBean(id, week_day, initial_hour, final_hour, information, id_device);
        getSchedules().add(scheduleBean);
    }

    public void addPoint(PointBean pointBean) {
        getPoints().add(pointBean);
    }

    @Deprecated
    public void setPoints(List<PointBean> points) {
        this.points = points;
    }

    @Override
    public String toString() {
        StringBuffer description = new StringBuffer(100);
        description
                .append("Rota: ").append(getName())/*.append("\nDias de coleta: ").append(getWeek_days())*/;

        if (getPoints().size() > 0) {
            description.append("\n\nPontos referenciais:");

            for (PointBean point : getPoints()) {
                if (point.getName() != null && !point.getName().isEmpty()) {
                    description.append("\n - ").append(point.getName());
                }
            }
        }

        if (getPoints().size() > 0) {
            description.append("\n\nAgendamentos:");

            for (ScheduleBean schedule : getSchedules()) {
                description.append("\n - ")
                        .append(schedule.getWeek_day())
                        .append(", das ")
                        .append(schedule.getInitial_hour())
                        .append(" às ")
                        .append(schedule.getFinal_hour());
            }
        }

        return description.toString();
    }
}
