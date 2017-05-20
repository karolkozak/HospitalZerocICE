package com.hospital.server;

import Hospital.Study;
import Ice.Identity;
import Ice.Object;
import Ice.ObjectFactory;
import com.hospital.server.model.*;
import com.hospital.server.servants.ClientServantI;
import com.hospital.server.servants.DoctorServantI;
import com.hospital.server.servants.LaboratorianServantI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by Karol on 02/05/2017.
 */
public class Server {

    public void t1(String[] args) {
        int status = 0;
        Ice.Communicator communicator = null;

        initializeData();

        try {
            communicator = Ice.Util.initialize(args);

//            Ice.ObjectAdapter adapter = communicator.createObjectAdapter("Adapter1");
            Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter1", "tcp -h localhost -p 10000:udp -h localhost -p 10000");

            // 3. Stworzenie serwanta/serwantów
            DoctorServantI doctorServantI = new DoctorServantI();
            ClientServantI clientServantI = new ClientServantI();
            LaboratorianServantI laboratorianServantI = new LaboratorianServantI();

            // 4. Dodanie wpisów do tablicy ASM
            adapter.add(doctorServantI, new Identity("doc1", "hospital"));
            adapter.add(clientServantI, new Identity("cli1", "hospital"));
            adapter.add(laboratorianServantI, new Identity("lab1", "hospital"));

            // 5. Aktywacja adaptera i przejście w pętlę przetwarzania żądań
            adapter.activate();

            System.out.println("Entering event processing loop...");

            communicator.waitForShutdown();

        } catch (Exception e) {
            System.err.println(e);
            status = 1;
        }
        if (communicator != null) {
            try {
                communicator.destroy();
            } catch (Exception e) {
                System.err.println(e);
                status = 1;
            }
        }

        System.exit(status);
    }

    private void initializeData() {
        Random rand = new Random();
        List<String> patientNames = new ArrayList<String>(Arrays.asList("Smith", "Murray", "Paul"));
        List<String> doctorNames = new ArrayList<String>(Arrays.asList("Faison", "Braff", "Chalke"));

        patientNames.forEach(patient -> {
            UserI userI = new UserI(patient, patient.toUpperCase(), rand.nextInt(999999999));
            Data.addPatient(userI);
        });

        doctorNames.forEach(doctor -> {
            DocI docI = new DocI(doctor, doctor.toUpperCase(), rand.nextInt(999999999), "dr");
            Data.addDoctor(docI);
        });

        List<String> studyNames = new ArrayList<String>(Arrays.asList("ALT", "AST", "Cholesterol", "Triglyceride", "Potassium"));

        for (int i = 0; i < 3; i++) {
            List<ResultI> results = new ArrayList<>();

            IntStream.range(0, 5).forEach(v -> results.add(new ResultI(studyNames.get(v), rand.nextInt(100))));
            ResultI[] res = new ResultI[results.size()];

            StudyI study = new StudyI(Data.getDoctors().get(i), Data.getPatients().get(i), LocalDate.now().toString(), results.toArray(res));
            Data.addStudy(study);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.t1(args);
    }
}
