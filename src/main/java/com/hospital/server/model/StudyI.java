package com.hospital.server.model;

import Hospital.Doc;
import Hospital.Result;
import Hospital.Study;
import Hospital.User;
import Ice.Current;

/**
 * Created by Karol on 02/05/2017.
 */
public class StudyI extends Study {

    public StudyI(Doc doctor, User user, String date, Result[] studies) {
        super(doctor, user, date, studies);
    }

    @Override
    public Doc getDoctor(Current __current) {
        return this.doctor;
    }

    @Override
    public User getUser(Current __current) {
        return this.user;
    }

    @Override
    public String getDate(Current __current) {
        return this.date;
    }

    @Override
    public Result[] getStudies(Current __current) {
        return this.studies;
    }
}
