package vn.edu.hust.set.tung.rikkei_assignment.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by tungt on 10/17/17.
 */

public class Note implements Parcelable{
    private int id;
    private String name;
    private String content;
    private String time;
    private ArrayList<String> listImage;
    boolean isAlarm;

    protected Note(Parcel in) {
        id = in.readInt();
        name = in.readString();
        content = in.readString();
        time = in.readString();
        listImage = in.createStringArrayList();
        isAlarm = in.readByte() != 0;
    }

    public Note(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(content);
        parcel.writeString(time);
        parcel.writeStringList(listImage);
        parcel.writeByte((byte) (isAlarm ? 1 : 0));
    }
}
