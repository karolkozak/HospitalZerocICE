package com.hospital.server.servants;

import Hospital.Study;
import Hospital._DoctorServantDisp;
import Ice.Current;
import com.hospital.server.model.Data;
import com.hospital.server.model.DocI;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Karol on 02/05/2017.
 */
public class DoctorServantI extends _DoctorServantDisp {
    @Override
    public Study[] get(String name, Current __current) {
        List<Study> selected = Data.getStudies()
                .stream()
                .filter(study -> study.getUser().getName().equals(name))
                .collect(Collectors.toList());
        Study[] studies = new Study[selected.size()];
        return selected.toArray(studies);
    }

    @Override
    public Study[] getAll(Current __current) {
        Study[] studies = new Study[Data.getStudies().size()];
        return Data.getStudies().toArray(studies);
    }

    @Override
    public Study getOneByDate(String name, String date, Current __current) {
        Optional<Study> selected = null;
        Optional<Study> result = Data.getStudies()
                .stream()
                .filter(study -> study.getUser().getName().equals(name))
                .filter(study -> study.getDate().equals(date))
                .findFirst();
        return result.orElse(null);
    }

    @Override
    public boolean register(String name, String surName, int pesel, String title, Current __current) {
        DocI docI = new DocI(name, surName, pesel, title);
        Data.addDoctor(docI);
        return true;
    }
}
