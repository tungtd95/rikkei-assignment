package vn.edu.hust.set.tung.rikkei_assignment.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import vn.edu.hust.set.tung.rikkei_assignment.util.Echo;

/**
 * Created by tungt on 10/17/17.
 */

public class Note{
    private long id;
    private String name;
    private String content;
    private String time;
    private String timeRemind;
    private ArrayList<Image> listImage;
    private int color;

    private int remindHour;
    private int remindMinute;
    private int remindDay;
    private int remindMonth;
    private int remindYear;

    public Note(String name, String content) {
        this.name = name;
        this.content = content;
        timeRemind = "";
    }

    public String getTimeRemind() {
        return timeRemind;
    }

    public void setTimeRemind(String timeRemind) {
        this.timeRemind = timeRemind;
        try {
            String[] t = timeRemind.split(" ");
            remindHour = Integer.valueOf(t[0]);
            remindMinute = Integer.valueOf(t[1]);
            remindDay = Integer.valueOf(t[2]);
            remindMonth = Integer.valueOf(t[3]);
            remindYear = Integer.valueOf(t[4]);
        }catch (Exception e) {
            Echo.echo("time remind: " + e.toString());
        }
    }

    public int getRemindHour() {
        return remindHour;
    }

    public void setRemindHour(int remindHour) {
        this.remindHour = remindHour;
    }

    public int getRemindMinute() {
        return remindMinute;
    }

    public void setRemindMinute(int remindMinute) {
        this.remindMinute = remindMinute;
    }

    public int getRemindDay() {
        return remindDay;
    }

    public void setRemindDay(int remindDay) {
        this.remindDay = remindDay;
    }

    public int getRemindMonth() {
        return remindMonth;
    }

    public void setRemindMonth(int remindMonth) {
        this.remindMonth = remindMonth;
    }

    public int getRemindYear() {
        return remindYear;
    }

    public void setRemindYear(int remindYear) {
        this.remindYear = remindYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<Image> getListImage() {
        return listImage;
    }

    public void setListImage(ArrayList<Image> listImage) {
        this.listImage = listImage;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
