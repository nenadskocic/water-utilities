package ca.mohawkcollege.ns;

import ca.mohawkcollege.ns.UserMeter;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-08T23:54:21")
@StaticMetamodel(MeterReading.class)
public class MeterReading_ { 

    public static volatile SingularAttribute<MeterReading, UserMeter> meterId;
    public static volatile SingularAttribute<MeterReading, Integer> reading;
    public static volatile SingularAttribute<MeterReading, Integer> readingId;
    public static volatile SingularAttribute<MeterReading, Date> readingDate;

}