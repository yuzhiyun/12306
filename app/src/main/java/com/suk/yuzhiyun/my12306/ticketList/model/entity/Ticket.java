package com.suk.yuzhiyun.my12306.ticketList.model.entity;

/**
 * 一张火车票
 * Created by yuzhiyun on 2016-09-17.
 */
public class Ticket {

    /**
     * 起始站
     */
    private String mStartStation;
    /**
     * 终点站
     */
    private String mEndStation;
    /**
     * 车次号码
     */
    private String mTrainNum;
    /**
     * 出发时间（小时与分钟）
     * 示例：09:45
     */
    private String mStartTime;
    /**
     * 结束时间
     */
    private String mEndTime;
    /**
     * 坐车时长
     */
    private String mDuration;

    public Ticket(String mStartStation, String mEndStation, String mTrainNum, String mStartTime, String mEndTime, String mDuration) {
        this.mStartStation = mStartStation;
        this.mEndStation = mEndStation;
        this.mTrainNum = mTrainNum;
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        this.mDuration = mDuration;
    }

    public String getmDuration() {
        return mDuration;
    }

    public void setmDuration(String mDuration) {
        this.mDuration = mDuration;
    }

    public String getmStartStation() {
        return mStartStation;
    }

    public void setmStartStation(String mStartStation) {
        this.mStartStation = mStartStation;
    }

    public String getmEndStation() {
        return mEndStation;
    }

    public void setmEndStation(String mEndStation) {
        this.mEndStation = mEndStation;
    }

    public String getmTrainNum() {
        return mTrainNum;
    }

    public void setmTrainNum(String mTrainNum) {
        this.mTrainNum = mTrainNum;
    }

    public String getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public String getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }
}
