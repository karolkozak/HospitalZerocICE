package com.hospital.server.model;

import Hospital.Result;
import Ice.Current;

/**
 * Created by Karol on 02/05/2017.
 */
public class ResultI extends Result {

    public ResultI(String studyName, int studyValue) {
        super(studyName, studyValue);
    }

    @Override
    public String getStudyName(Current __current) {
        return this.studyName;
    }

    @Override
    public int getstudyValue(Current __current) {
        return this.studyValue;
    }
}
