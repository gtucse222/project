package com.example.a222latest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ModelPost implements Comparable<ModelPost> {
    //use same name given while uploading post
    String pId, pTitle, pDescr, pLikes, pTime, uid, uEmail, uName, uDp, uSurname, membership;
    final int priorityEf = 10000000;
    public ModelPost() {

    }

    public String getpLikes() {
        return pLikes;
    }

    public void setpLikes(String pLikes) {
        this.pLikes = pLikes;
    }

    public ModelPost(String pId, String pTitle, String pDescr, String pTime, String uid, String uEmail, String uName, String uDp, String pLikes, String uSurname, String membership) {
        this.pId = pId;
        this.pTitle = pTitle;
        this.pDescr = pDescr;
        this.pLikes = pLikes;
        this.pTime = pTime;
        this.uid = uid;
        this.uEmail = uEmail;
        this.uName = uName;
        this.uDp = uDp;
        this.uSurname = uSurname;
        this.membership = membership;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getuSurname() {
        return uSurname;
    }

    public void setuSurname(String uSurname) {
        this.uSurname = uSurname;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getpDescr() {
        return pDescr;
    }

    public void setpDescr(String pDescr) {
        this.pDescr = pDescr;
    }

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuDp() {
        return uDp;
    }

    public void setuDp(String uDp) {
        this.uDp = uDp;
    }

    @NonNull
    @Override
    public String toString() {
        return pLikes;
    }


    @Override
    public int compareTo(ModelPost o) {
      Long timeVal = Long.parseLong(this.getpId());
      Long likes = Long.parseLong(this.getpLikes());

      Long timeVal2 = Long.parseLong(o.getpId());
      Long likes2 = Long.parseLong(o.getpLikes());
      Long result = ((likes+1) * priorityEf)/(timeVal+1);
      Long result2 = ((likes2+1) * priorityEf) / (timeVal2+1);

      return (int) (result-result2);

      /*  int first= 0;int second=0;
        if(Long.parseDouble(this.pId)-Long.parseDouble(o.pId)>)
            first++;
        else
            second++;

        first=first+Integer.parseInt(this.pLikes);
        second=second+Integer.parseInt(o.pLikes);
        if (this.membership.equalsIgnoreCase("teacher"))
            first=first+10;

        if (o.membership.equalsIgnoreCase("teacher"))
            second=second+10;
        if (first<second)
            return 1;
        else if(second>first)
            return -1;
        else
            return 0;
    }*/
        //int time = Long.parseLong(this.pId)-Long.parseDouble(o.pId)

       /* if (this.pId.equalsIgnoreCase(o.pId)) {
            if(Double.parseDouble(this.pId)>=Double.parseDouble(o.pId))
                return 1;
            else
                return 0;

        }
           return  this.getpId().compareTo(o.getpId());
*/

    }

    @Override
    public boolean equals(@Nullable Object obj) {
    ModelPost o =(ModelPost) obj;
        return o.getpId().equals(this.getpId());
    }

}
