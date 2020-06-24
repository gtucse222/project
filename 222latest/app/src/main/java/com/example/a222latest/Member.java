package com.example.a222latest;

class Member {

    /**
     * Name of member
     */
    private String name;
    /**
     * Surname of member
     */
    private String surname;
    /**
     * mail address of member
     */
    private String mailAddress;
    /**
     * password of member
     */
    private String password;
    /**
     * membership of member
     */
    private String membership;

     /**
     * That method returns the name.
     * @return name
     */
    public String getName() {
        return name;
    }


    /**
     * That methods set the name.
     * @param name member's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * That method returns the surname.
     * @return surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * That method set the surname.
     * @param surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * That method returns the mail address.
     * @return mail address of member.
     */
    public String getMailAddress() {
        return mailAddress;
    }

    /**
     *  That method sets the mail address.
     * @param mailAddress member's mail address.
     */
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    /**
     * That method returns the password.
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * That method sets the password.
     * @param password member's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * That method returns the membership.
     * @return membership.
     */
    public String getMembership() {
        return membership;
    }

    /**
     * that method sets the membership.
     * @param membership member's membershp.
     */
    public void setMembership(String membership) {
        this.membership = membership;
    }


}
