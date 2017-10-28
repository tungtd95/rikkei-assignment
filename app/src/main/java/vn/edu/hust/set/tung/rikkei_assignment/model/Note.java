package vn.edu.hust.set.tung.rikkei_assignment.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by tungt on 10/17/17.
 */

public class Note{
    private int id;
    private String name;
    private String content;
    private String time;
    private ArrayList<String> listImage;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<String> getListImage() {
        return listImage;
    }

    public void setListImage(ArrayList<String> listImage) {
        this.listImage = listImage;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

//    @Override
//    public int compareTo(@NonNull Note otherNote) {
//        if (this.id != otherNote.id) {
//            return -1;
//        }
//        if (this.name != null && !this.name.equals(otherNote.name)) {
//            return -1;
//        }
//        if (this.name != null && !this.content.equals(otherNote.content)) {
//            return -1;
//        }
//        if (this.time != null && !this.time.equals(otherNote.time)) {
//            return -1;
//        }
//        if (this.color != otherNote.color) {
//            return -1;
//        }
//        if (this.listImage != null && otherNote.listImage != null) {
//            for (int)
//        }
//        if ((this.listImage == null && otherNote.listImage != null) ||
//                (this.listImage != null && otherNote.listImage == null)) {
//            return -1;
//        }
//        return 0;
//    }
}
