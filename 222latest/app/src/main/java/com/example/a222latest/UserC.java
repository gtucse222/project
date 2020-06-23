package com.example.a222latest;

public class UserC implements Comparable<UserC> {
    private String name;
    private String eMail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }



    @Override
    public int compareTo(UserC o) {
        return name.compareTo(((UserC)o).getName());
    }
}
