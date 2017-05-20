package com.hospital.server.model;

import Hospital.Doc;
import Ice.Current;

/**
 * Created by Karol on 02/05/2017.
 */
public class DocI extends Doc {

    public DocI(String name, String surName, int pesel, String title) {
        super(name, surName, pesel, title);
    }

    @Override
    public String getTitle(Current __current) {
        return this.title;
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
