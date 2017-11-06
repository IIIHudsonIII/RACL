package com.example.hudso.racl.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hudso on 16/10/2017.
 */

public class RouteBean {
    private String name;
    private String workDay;
    private String initialHour;
    private String finalHour;
    private List<PointBean> points;

    public RouteBean(String name, String workDay, String initialHour, String finalHour) {
        this.name = name;
        this.workDay = workDay;
        this.initialHour = initialHour;
        this.finalHour = finalHour;
    }

    public String getName() {
        return name == null || "".equals(name) ? "(Não informado)" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public String getInitialHour() {
        return initialHour;
    }

    public void setInitialHour(String initialHour) {
        this.initialHour = initialHour;
    }

    public String getFinalHour() {
        return finalHour;
    }

    public void setFinalHour(String finalHour) {
        this.finalHour = finalHour;
    }

    public List<PointBean> getPoints() {
        if (points == null) {
            points = new ArrayList<PointBean>();
        }
        return points;
    }

    public void addPoint(double lat, double lng, String description) {
        PointBean pointBean = new PointBean(lat, lng, description);
        getPoints().add(pointBean);
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
                .append("Nome: ").append(getName())
                .append("\nDia da semana: ").append(getWorkDay())
                .append("\nHorário: ").append(getInitialHour()).append(" - ").append(getFinalHour());

        if (getPoints().size() > 0) {
            description.append("\n\nPercurso:");

            for (PointBean point : getPoints()) {
                description.append("\n - ").append(point.getDescription());
            }
        }

        return description.toString();
    }
}
