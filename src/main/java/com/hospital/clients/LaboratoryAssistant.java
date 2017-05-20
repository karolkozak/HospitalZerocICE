package com.hospital.clients;

import Hospital.Doc;
import Hospital.LaboratorianServantPrx;
import Hospital.LaboratorianServantPrxHelper;
import Hospital.User;
import com.hospital.clients.printer.HospitalPrinter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by Karol on 02/05/2017.
 */
public class LaboratoryAssistant {

    public void start(String[] args) {
        int status = 0;
        Ice.Communicator communicator = null;

        try {
            communicator = Ice.Util.initialize(args);
            communicator.addObjectFactory(new MyObjectFactory(), Doc.ice_staticId());
            communicator.addObjectFactory(new MyObjectFactory(), User.ice_staticId());

            //Ice.ObjectPrx base = communicator.propertyToProxy("Calc1.Proxy");
            Ice.ObjectPrx laboratorian = communicator.stringToProxy("hospital/lab1:tcp -h localhost -p 10000:udp -h localhost -p 10000");

            LaboratorianServantPrx laboratorianServantPrx = LaboratorianServantPrxHelper.checkedCast(laboratorian);
            if (laboratorianServantPrx == null) throw new Error("Invalid proxy");

            LaboratorianServantPrx objOneway = (LaboratorianServantPrx) laboratorianServantPrx.ice_oneway();
            LaboratorianServantPrx objBatchOneway = (LaboratorianServantPrx) laboratorianServantPrx.ice_batchOneway();
            LaboratorianServantPrx objDatagram = (LaboratorianServantPrx) laboratorianServantPrx.ice_datagram();
            LaboratorianServantPrx objBatchDatagram = (LaboratorianServantPrx) laboratorianServantPrx.ice_batchDatagram();


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            List<String> studyNames = new ArrayList<String>(Arrays.asList("ALT", "AST", "Cholesterol", "Triglyceride", "Potassium"));
            Random rand = new Random();

            do {
                HospitalPrinter.printLaboratorianMenu();
                System.out.println("==> ");
                System.out.flush();
                line = bufferedReader.readLine();
                if (line == null)
                    break;
                else if (line.equals("add")) {
                    System.out.println("Give name of patient: ");
                    String patientName = bufferedReader.readLine();
                    System.out.println("Give name of doctor: ");
                    String doctorName = bufferedReader.readLine();

                    List<Integer> values = new ArrayList<Integer>();
                    IntStream.range(0, 5).forEach(v -> values.add(rand.nextInt(100)));
                    String[] names = new String[studyNames.size()];
                    int[] val = new int[values.size()];
                    for (int i = 0; i < studyNames.size(); i++) {
                        val[i] = rand.nextInt(100);
                    }

                    laboratorianServantPrx.add(doctorName, patientName, studyNames.toArray(names), val);
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
        LaboratoryAssistant laboratoryAssistant = new LaboratoryAssistant();
        laboratoryAssistant.start(args);
    }
}
