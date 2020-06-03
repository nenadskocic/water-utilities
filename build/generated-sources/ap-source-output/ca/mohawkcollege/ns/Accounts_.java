package ca.mohawkcollege.ns;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-08T23:54:21")
@StaticMetamodel(Accounts.class)
public class Accounts_ { 

    public static volatile SingularAttribute<Accounts, Boolean> isAdmin;
    public static volatile SingularAttribute<Accounts, Integer> userId;
    public static volatile SingularAttribute<Accounts, String> passwordHash;
    public static volatile SingularAttribute<Accounts, String> username;

}