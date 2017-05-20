
#ifndef HOSPITAL_ICE
#define HOSPITAL_ICE

module Hospital {

  class User {
    string name;
    string surName;
    int pesel;

    string getName();
    string getSurName();
    int getPesel();
  };

  class Doc extends User {
    string title;

    string getTitle();
  };

  class Result {
    string studyName;
    int studyValue;

    string getStudyName();
    int getstudyValue();
  };

  sequence<Result> Studies;

  class Study {
      Doc doctor;
      User user;
      string date;
      Studies studies;

      Doc getDoctor();
      User getUser();
      string getDate();
      Studies getStudies();
  };

  sequence<Study> Results;
  sequence<string> Names;
  sequence<int> Values;

  interface ClientServant {
    bool register(string name, string surName, int pesel);
    Results getResults(string name);
    Study getResultByDate(string name, string date);
  };

  interface DoctorServant {
    Results get(string name);
    Results getAll();
    Study getOneByDate(string name, string date);
    bool register(string name, string surName, int pesel, string title);
  };

  interface LaboratorianServant {
    bool add(string doctorName, string userName, Names names, Values values);
    User getUser(string name);
    Doc getDoctor(string name);
  };
};

#endif
