package com.example.KimManUserManager.Model;

public class User {
    private int mId;
    private String mUsername;
    private String mAddress;
    private String mPhone;
    public User()
    {}
    public User(int mId, String mUsername, String mAddress, String mPhone) {
        this.mId = mId;
        this.mUsername = mUsername;
        this.mAddress = mAddress;
        this.mPhone = mPhone;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }
}
