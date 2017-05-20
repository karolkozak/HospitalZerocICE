package com.hospital.clients;

import Hospital.Doc;
import Hospital.Result;
import Hospital.Study;
import Hospital.User;
import Ice.Current;
import Ice.Object;

/**
 * Created by Karol on 05/05/2017.
 */
public class MyObjectFactory implements Ice.ObjectFactory {
    @Override
    public Object create(String type) {
        if(type.equals(Study.ice_staticId())) {
            return new Study() {
                @Override
                public Doc getDoctor(Current __current) {
                    return this.doctor;
                }

                @Override
                public User getUser(Current __current) {
                    return this.user;
                }

                @Override
                public String getDate(Current __current) {
                    return this.date;
                }

                @Override
                public Result[] getStudies(Current __current) {
                    return this.studies;
                }
            };
        } else if(type.equals(Doc.ice_staticId())) {
            return new Doc() {
                @Override
                public String getName(Current __current) {
                    return this.name;
                }

                @Override
                public String getSurName(Current __current) {
                    return this.surName;
                }

                @Override
                public int getPesel(Current __current) {
                    return this.pesel;
                }

                @Override
                public String getTitle(Current __current) {
                    return this.title;
                }
            };
        } else if (type.equals(User.ice_staticId())) {
            return new User() {
                @Override
                public String getName(Current __current) {
                    return this.name;
                }

                @Override
                public String getSurName(Current __current) {
                    return this.surName;
                }

                @Override
                public int getPesel(Current __current) {
                    return this.pesel;
                }
            };
        } else if (type.equals(Result.ice_staticId())) {
            return new Result() {
                @Override
                public String getStudyName(Current __current) {
                    return this.studyName;
                }

                @Override
                public int getstudyValue(Current __current) {
                    return this.studyValue;
                }
            };
        }
        return null;
    }

    @Override
    public void destroy() {

    }
}
