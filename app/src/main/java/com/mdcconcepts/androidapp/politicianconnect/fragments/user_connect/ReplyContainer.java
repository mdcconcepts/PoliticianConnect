package com.mdcconcepts.androidapp.politicianconnect.fragments.user_connect;

/**
 * {@link com.mdcconcepts.androidapp.politicianconnect.fragments.user_connect.ReplyContainer}
 * Created by CODELORD on 2/11/2015.
 *
 * @author CODELORD
 */
public class ReplyContainer {

    private String reply;
    private String date;
    private int reply_by;

    public ReplyContainer(String reply, String date, int reply_by) {
        this.reply = reply;
        this.date = date;
        this.reply_by = reply_by;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getReply_by() {
        return reply_by;
    }

    public void setReply_by(int reply_by) {
        this.reply_by = reply_by;
    }
}
