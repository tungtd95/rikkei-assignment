package vn.edu.hust.set.tung.rikkei_assignment.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by tungt on 10/17/17.
 */

public class Note{
    private long id;
    private String name;
    private String content;
    private String time;
    private ArrayList<Image> listImage;
    boolean isAlarm;
    private int color;

    public Note(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public boolean isAlarm() {
        return isAlarm;
    }

    public void setAlarm(boolean alarm) {
        isAlarm = alarm;
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
