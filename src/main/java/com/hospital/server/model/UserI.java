package com.hospital.server.model;

import Hospital.User;
import Ice.Current;

/**
 * Created by Karol on 02/05/2017.
 */
public class UserI extends User {

    public UserI(String name, String surName, int pesel) {
        super(name, surName, pesel);
    }

    @Override
    public String getName(Current __current) {
        return this.name;
    }

    @Override
    public String getSurName(Current __current) {
        return this.surName;
    }

    @Override
    public int getPesel(Current __current) {
        return this.pesel;
    }
}
