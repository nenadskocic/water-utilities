package ca.mohawkcollege.ns;

import ca.mohawkcollege.ns.Accounts;
import ca.mohawkcollege.ns.MeterReading;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-08T23:54:21")
@StaticMetamodel(UserMeter.class)
public class UserMeter_ { 

    public static volatile SingularAttribute<UserMeter, String> address;
    public static volatile SingularAttribute<UserMeter, String> meterId;
    public static volatile ListAttribute<UserMeter, MeterReading> meterReadingList;
    public static volatile SingularAttribute<UserMeter, Accounts> userId;

}