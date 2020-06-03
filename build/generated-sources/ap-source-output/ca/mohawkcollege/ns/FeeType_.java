package ca.mohawkcollege.ns;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-08T23:54:21")
@StaticMetamodel(FeeType.class)
public class FeeType_ { 

    public static volatile SingularAttribute<FeeType, BigDecimal> mediumRate;
    public static volatile SingularAttribute<FeeType, BigDecimal> highRate;
    public static volatile SingularAttribute<FeeType, BigDecimal> lowRate;
    public static volatile SingularAttribute<FeeType, Integer> id;
    public static volatile SingularAttribute<FeeType, BigDecimal> adminFee;

}