package com.hospital.clients;

import Hospital.*;
import Ice.Object;
import Ice.ObjectFactory;
import com.hospital.clients.printer.HospitalPrinter;
import com.hospital.server.model.StudyI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Karol on 02/05/2017.
 */
public class Patient {

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
            Ice.ObjectPrx client = communicator.stringToProxy("hospital/cli1:tcp -h localhost -p 10000:udp -h localhost -p 10000");

            ClientServantPrx clientServantPrx = ClientServantPrxHelper.checkedCast(client);
            if (clientServantPrx == null) throw new Error("Invalid proxy");

            ClientServantPrx objOneway = (ClientServantPrx) clientServantPrx.ice_oneway();
            ClientServantPrx objBatchOneway = (ClientServantPrx) clientServantPrx.ice_batchOneway();
            ClientServantPrx objDatagram = (ClientServantPrx) clientServantPrx.ice_datagram();
            ClientServantPrx objBatchDatagram = (ClientServantPrx) clientServantPrx.ice_batchDatagram();


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            Random rand = new Random();
            int pesel = rand.nextInt(999999999);

            String name;
            System.out.println("Give your name:");
            name = bufferedReader.readLine();

            boolean cc = clientServantPrx.register(name, name.toUpperCase(), pesel);         //register client
            System.out.println(cc);
            String line = null;

            do {
                HospitalPrinter.printPatientMenu();
                System.out.println("==> ");
                System.out.flush();
                line = bufferedReader.readLine();
                if (line == null)
                    break;
                else if (line.equals("results")) {
                    Study[] studies = clientServantPrx.getResults(name);
                    Arrays.asList(studies).forEach(study -> {
                        HospitalPrinter.printStudy(study);
                    });
                } else if (line.equals("one")) {
                    System.out.println("Give date (yyyy-mm-dd): ");
                    line = bufferedReader.readLine();
                    Study study = clientServantPrx.getResultByDate(name, line);
                    if (study != null)
                        HospitalPrinter.printStudy(study);
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
        Patient patient = new Patient();
        patient.start(args);
    }
}
