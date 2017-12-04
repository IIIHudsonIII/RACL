package com.example.hudso.racl.bean;

/**
 * Created by hudso on 18/11/2017.
 */

public class ScheduleBean {
    private String id;
    private String week_day;
    private String initial_hour;
    private String final_hour;
    private String information;
    private String id_device;

    public ScheduleBean(String id, String week_day, String initial_hour, String final_hour, String information, String id_device) {
        this.id = id;
        this.week_day = week_day;
        this.initial_hour = initial_hour;
        this.final_hour = final_hour;
        this.information = information;
        this.id_device = id_device;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeek_day() {
        return week_day;
    }

    public String getFormattedWeek_Day() {
        return WeekDay.valueOf(getWeek_day().toUpperCase()).getDescription();
    }

    public void setWeek_day(String week_day) {
        this.week_day = week_day;
    }

    public String getInitial_hour() {
        return initial_hour;
    }

    public void setInitial_hour(String initial_hour) {
        this.initial_hour = initial_hour;
    }

    public String getFinal_hour() {
        return final_hour;
    }

    public void setFinal_hour(String final_hour) {
        this.final_hour = final_hour;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getId_device() {
        return id_device;
    }

    public void setId_device(String id_device) {
        this.id_device = id_device;
    }

    public String getFormattedSchedule() {
        return getFormattedWeek_Day() + ", das " + getInitial_hour() + "h às " + getFinal_hour() + "h";
    }

    public enum WeekDay {
        SUNDAY("Domingo"),
        MONDAY("Segunda-feira"),
        TUESDAY("Terça-feira"),
        WEDNESDAY("Quarta-feira"),
        THURSDAY("Quinta-feira"),
        FRIDAY("Sexta-feira"),
        SATURDAY("Sábado");

        private String description;

        private WeekDay(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
