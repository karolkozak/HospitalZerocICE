package com.hospital.clients;

import Hospital.*;
import com.hospital.clients.printer.HospitalPrinter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Karol on 02/05/2017.
 */
public class Doctor {

    public void start(String[] args) {
        int status = 0;
        Ice.Communicator communicator = null;

        try {
            communicator = Ice.Util.initialize(args);
            communicator.addObjectFactory(new MyObjectFactory(), Study.ice_staticId());
            communicator.addObjectFactory(new MyObjectFactory(), Doc.ice_staticId());
            communicator.addObjectFactory(new MyObjectFactory(), User.ice_staticId());
            communicator.addObjectFactory(new MyObjectFactory(), Result.ice_staticId());

            //Ice.ObjectPrx base = communicator.propertyToProxy("Calc1.Proxy");
            Ice.ObjectPrx doctor = communicator.stringToProxy("hospital/doc1:tcp -h localhost -p 10000:udp -h localhost -p 10000");

            DoctorServantPrx doctorServantPrx = DoctorServantPrxHelper.checkedCast(doctor);
            if (doctorServantPrx == null) throw new Error("Invalid proxy");

            DoctorServantPrx objOneway = (DoctorServantPrx) doctorServantPrx.ice_oneway();
            DoctorServantPrx objBatchOneway = (DoctorServantPrx) doctorServantPrx.ice_batchOneway();
            DoctorServantPrx objDatagram = (DoctorServantPrx) doctorServantPrx.ice_datagram();
            DoctorServantPrx objBatchDatagram = (DoctorServantPrx) doctorServantPrx.ice_batchDatagram();


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            Random rand = new Random();
            int pesel = rand.nextInt(999999999);

            String name;
            System.out.println("Give your name:");
            name = bufferedReader.readLine();

            doctorServantPrx.register(name, name.toUpperCase(), pesel, "dr");         //register client
            String line = null;

            do {
                HospitalPrinter.printDoctorMenu();
                System.out.println("==> ");
                System.out.flush();
                line = bufferedReader.readLine();
                if (line == null)
                    break;
                else if (line.equals("results")) {
                    Study[] studies = doctorServantPrx.getAll();
                    Arrays.asList(studies).forEach(study -> {
                        HospitalPrinter.printStudy(study);
                    });
                } else if (line.equals("one")) {
                    System.out.println("Give name of patient: ");
                    String patientName = bufferedReader.readLine();
                    System.out.println("Give date (yyyy-mm-dd): ");
                    String date = bufferedReader.readLine();
                    Study study = doctorServantPrx.getOneByDate(patientName, date);
                    if (study != null)
                        HospitalPrinter.printStudy(study);
                } else if (line.equals("patient")) {
                    System.out.println("Give name of patient: ");
                    String patientName = bufferedReader.readLine();
                    Study[] studies = doctorServantPrx.get(patientName);
                    Arrays.asList(studies).forEach(study -> {
                        HospitalPrinter.printStudy(study);
                    });
                }
            } while (!line.equals("x"));

        } catch (Ice.LocalException e) {
            e.printStackTrace();
            status = 1;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            status = 1;
        }
        if (communicator != null) {
            try {
                communicator.destroy();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                status = 1;
            }
        }
        System.exit(status);
    }

    public static void main(String[] args) {
        Doctor doctor = new Doctor();
        doctor.start(args);
    }
}
