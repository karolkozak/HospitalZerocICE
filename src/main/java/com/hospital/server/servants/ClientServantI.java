package com.hospital.server.servants;

import Hospital.Study;
import Hospital._ClientServantDisp;
import Ice.Current;
import com.hospital.server.model.Data;
import com.hospital.server.model.StudyI;
import com.hospital.server.model.UserI;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Karol on 02/05/2017.
 */
public class ClientServantI extends _ClientServantDisp {
    @Override
    public boolean register(String name, String surName, int pesel, Current __current) {
        UserI userI = new UserI(name, surName, pesel);
        Data.addPatient(userI);
        return true;
    }

    @Override
    public Study[] getResults(String name, Current __current) {
        List<Study> selected = Data.getStudies()
                .stream()
                .filter(study -> study.getUser().getName().equals(name))
                .collect(Collectors.toList());
        Study[] studies = new Study[selected.size()];

        return selected.toArray(studies);
    }

    @Override
    public Study getResultByDate(String name, String date, Current __current) {
        Optional<Study> selected = null;
        Optional<Study> result = Data.getStudies()
                .stream()
                .filter(study -> study.getUser().getName().equals(name))
                .filter(study -> study.getDate().equals(date))
                .findFirst();
        return result.orElse(null);
    }
}
