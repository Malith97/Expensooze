package com.synnlabz.expensooze;

public class Bill {
    String RID,CID,yr,mon,mday,amount,remark;

    public Bill(String RID, String CID, String yr, String mon, String mday, String amount, String remark) {
        this.RID = RID;
        this.CID = CID;
        this.yr = yr;
        this.mon = mon;
        this.mday = mday;
        this.amount = amount;
        this.remark = remark;
    }

    public String getRID() {
        return RID;
    }

    public void setRID(String RID) {
        this.RID = RID;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getYr() {
        return yr;
    }

    public void setYr(String yr) {
        this.yr = yr;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getMday() {
        return mday;
    }

    public void setMday(String mday) {
        this.mday = mday;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
