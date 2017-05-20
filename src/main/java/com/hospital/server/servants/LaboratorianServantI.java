package com.hospital.server.servants;

import Hospital.Doc;
import Hospital.Result;
import Hospital.User;
import Hospital._LaboratorianServantDisp;
import Ice.Current;
import com.hospital.server.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Created by Karol on 02/05/2017.
 */
public class LaboratorianServantI extends _LaboratorianServantDisp {

    @Override
    public boolean add(String doctorName, String userName, String[] names, int[] values, Current __current) {
        Optional<User> user = Data.getPatients()
                .stream()
                .filter(patient -> patient.getName().equals(userName))
                .findFirst();
        Optional<Doc> doc = Data.getDoctors()
                .stream()
                .filter(doctor -> doctor.getName().equals(doctorName))
                .findFirst();
        List<ResultI> results = new ArrayList<>();
        IntStream.range(0, 5).forEach(v -> results.add(new ResultI(names[v], values[v])));
        ResultI[] res = new ResultI[results.size()];
        for (int i = 0; i < results.size(); i++) {
            res[i] = results.get(i);
        }
        StudyI studyI = new StudyI(doc.orElse(null), user.orElse(null), LocalDate.now().toString(), res);
        Data.addStudy(studyI);
        return true;
    }

    @Override
    public User getUser(String name, Current __current) {
        Optional<User> user = Data.getPatients()
                .stream()
                .filter(patient -> patient.getName().equals(name))
                .findFirst();
        return user.orElse(null);
    }

    @Override
    public Doc getDoctor(String name, Current __current) {
        Optional<Doc> doc = Data.getDoctors()
                .stream()
                .filter(doctor -> doctor.getName().equals(name))
                .findFirst();
        return doc.orElse(null);
    }
}
