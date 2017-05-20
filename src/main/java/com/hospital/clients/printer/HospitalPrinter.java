package com.hospital.clients.printer;

import Hospital.Study;

import java.util.Arrays;

/**
 * Created by Karol on 04/05/2017.
 */
public class HospitalPrinter {

    public static void printDoctorMenu() {
        System.out.println("Type one from list below: ");
        System.out.println("results - to get all studies.");
        System.out.println("patient - to get studies of one patient. You have to give name of patient later.");
        System.out.println("one - to get one study of one patient. You have to give name of patient and date later.");
    }

    public static void printPatientMenu() {
        System.out.println("Type one from list below: ");
        System.out.println("results - to get all your studies.");
        System.out.println("one - to get one of your studies. You have to give date later.");
    }

    public static void printLaboratorianMenu() {
        System.out.println("Type one from list below: ");
        System.out.println("add - to add studies. You have to give patient name and doctor name later");
    }

    public static void printStudy(Study study) {
        System.out.println("Doctor: " + study.doctor.getName());
        System.out.println(">> " + study.user.getName() + " " + study.date);
        Arrays.asList(study.studies).forEach(result -> {
            System.out.println(">>>> " + result.getStudyName() + ": " + result.getstudyValue());
        });
    }
}
