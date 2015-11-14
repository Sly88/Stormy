package com.sylwestermichalowski.stormy.wather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sylwestermichalowski on 11/11/15.
 */
public class Hour implements Parcelable
{
    private  long mTime;
    private  String mSummary;
    private double mTeperature;
    private  String mIcon;
    private  String mTimezone;

    public Hour(){}

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public int getTeperature() {
        return (int) Math.round(mTeperature);
    }

    public int getIconId(){
        return Forecast.getIconId(mIcon);
    }

    public void setTeperature(double teperature) {
        mTeperature = teperature;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }

    public  String getHour(){
        SimpleDateFormat forrmater = new SimpleDateFormat("h a");
        Date date = new Date(mTime * 1000);
        return forrmater.format(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mTime);
        parcel.writeString(mSummary);
        parcel.writeDouble(mTeperature);
        parcel.writeString(mIcon);
        parcel.writeString(mTimezone);

    }

    private Hour(Parcel in){
        mTime = in.readLong();
        mSummary = in.readString();
        mTeperature = in.readDouble();
        mIcon = in.readString();
        mTimezone = in.readString();
    }

    public static final Creator<Hour> CREATOR = new Creator<Hour>() {
        @Override
        public Hour createFromParcel(Parcel parcel) {
            return new Hour(parcel);
        }

        @Override
        public Hour[] newArray(int i) {
            return new Hour[i];
        }
    };
}
