package com.suk.yuzhiyun.my12306.ticketList.model.entity;

/**
 * 一张火车票
 * Created by yuzhiyun on 2016-09-17.
 */
public class Ticket {

    /**
     * 各座位类型余票数量
     * 6种座位，所以数组长度为6
     */
    public int[] mTicketNum = {0, 0, 0, 0, 0, 0};
    /**
     * 各座位类型票价
     * 6种座位，所以数组长度为6
     */
    public int[] mTicketPrice = {0, 0, 0, 0, 0, 0};

    /**
     * 出发日期
     */
    private String mDate;
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
    /**
     * 票价
     */
    private String mPrice;

    public Ticket(String mDate,String mStartStation, String mEndStation, String mTrainNum, String mStartTime, String mEndTime, String mDuration, String mPrice) {
        this.mDate = mDate;
        this.mStartStation = mStartStation;
        this.mEndStation = mEndStation;
        this.mTrainNum = mTrainNum;
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        this.mDuration = mDuration;
        this.mPrice = mPrice;
    }

    public String getmDuration() {
        return mDuration;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
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

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }
}
