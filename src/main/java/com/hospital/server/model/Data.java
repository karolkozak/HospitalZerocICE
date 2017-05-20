package com.hospital.server.model;

import Hospital.Doc;
import Hospital.Study;
import Hospital.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karol on 02/05/2017.
 */
public class Data {

    private static List<User> patients = new ArrayList<User>();
    private static List<Doc> doctors = new ArrayList<>();
    private static List<Study> studies = new ArrayList<>();

    public static List<User> getPatients() {
        return patients;
    }

    public static List<Doc> getDoctors() {
        return doctors;
    }

    public static List<Study> getStudies() {
        return studies;
    }

    public static boolean addPatient(UserI userI) {
        User user = patients.stream().filter(patient -> patient.getName().equals(userI.getName())).findAny().orElse(null);
        if (user == null) {
            patients.add(userI);
            return true;
        }
        return false;
    }

    public static boolean addDoctor(DocI docI) {
        Doc doc = doctors.stream().filter(doctor -> doctor.getName().equals(docI.getName())).findAny().orElse(null);
        if (doc == null) {
            doctors.add(docI);
            return true;
        }
        return false;
    }

    public static void addStudy(StudyI study) {
        studies.add(study);
    }
}
