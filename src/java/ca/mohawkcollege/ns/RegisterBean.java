/**
 * I, Nenad Skocic, 000107650 certify that this material is my original work. 
 * No other person's work has been used without due acknowledgment.
 */
package ca.mohawkcollege.ns;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.validation.constraints.Size;

/**
 *
 * @author Nenad
 */
@Named(value = "registerBean")
@SessionScoped
public class RegisterBean implements Serializable {

    @Size(min = 1, max = 32)
    protected String username;

    @Size(min = 1, max = 64)
    protected String address;

    protected String meterID, password, confirmPassword;

    UserMeter userMeter = new UserMeter();

    @ManagedProperty(value = "#{language}")
    private ChangeLanguage lang;
    @PersistenceContext(unitName = "WaterUtilitiesPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    public void setLang(ChangeLanguage l) {
        lang = l;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pw) {
        this.password = pw;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMeterID() {
        return meterID;
    }

    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String pw) {
        confirmPassword = pw;
    }

    /**
     * Creates a new instance of RegisterBean
     */
    public RegisterBean() {
    }

    public void validatePassword(FacesContext context, UIComponent ui, Object value) {
        String pw = (String) value;
        String specialChars = "!@#$%^&*";

        boolean hasDigit = false;
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean containsSC = false;
        boolean lengthValid = pw.length() >= 8;

        for (int i = 0; i < pw.length(); i++) {
            char c = pw.charAt(i);
            hasLower |= Character.isLowerCase(c);
            hasUpper |= Character.isUpperCase(c);
            hasDigit |= Character.isDigit(c);
            containsSC = specialChars.contains(pw.substring(i, pw.length() - 1));
        }

        if ((hasDigit && hasUpper && hasLower && lengthValid && containsSC) == false) {
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");
            String message = bundle.getString("errorPassword");
            throw new ValidatorException(new FacesMessage(message));
        }
    }

    public void validateConfirm(FacesContext context, UIComponent c, Object value) {
        UIInput passwordField = (UIInput) context.getViewRoot().findComponent("regForm:password");
        String pw = (String) passwordField.getValue();

        if (pw != null && !pw.equals((String) value)) {
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");
            String message = bundle.getString("errorConfirm");
            throw new ValidatorException(new FacesMessage(message));
        }
    }

    public String registerUser() throws NoSuchAlgorithmException, NotSupportedException, SystemException {
        String passwordToHash = password;
        //byte[] salt = getSalt();
        String securePassword = getSHA1(passwordToHash);

        Accounts acc = new Accounts();
        acc.setUsername(username);
        acc.setPasswordHash(securePassword);
        acc.setIsAdmin(Boolean.FALSE);

        userMeter = em.createNamedQuery("UserMeter.findByMeterIdAndAddress", UserMeter.class)
                .setParameter("address", address).setParameter("meterId", meterID).getSingleResult();
        userMeter.setUserId(acc);

        if (userMeter.getUserId() != null) {
            persist(acc);
        }

        try {
            utx.begin();
            userMeter = em.merge(userMeter);
            em.persist(userMeter);
            utx.commit();

        } catch (Exception e) {
            return "login";
        }

        if (acc.getIsAdmin() == true) {
            return "login";
        } else {
            return "login";

        } 
    }

    public String getSHA1(String passwordToHash) {
        String generatedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            //md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

//    public byte[] getSalt() throws NoSuchAlgorithmException {
//        SecureRandom secureRand = SecureRandom.getInstance("SHA1PRNG");
//        byte[] salt = new byte[16];
//        secureRand.nextBytes(salt);
//        return salt;
//    }
    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
}
