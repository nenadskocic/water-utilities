/**
 * I, Nenad Skocic, 000107650 certify that this material is my original work. 
 * No other person's work has been used without due acknowledgment.
 */
package ca.mohawkcollege.ns;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Nenad
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    @PersistenceContext(unitName = "WaterUtilitiesPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    RegisterBean registerBean = new RegisterBean();

    private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }

    public String login() throws NoSuchAlgorithmException {
        Accounts accs = null;
        FacesContext context = FacesContext.getCurrentInstance();

        String passwordToHash = password;
        //byte[] salt = registerBean.getSalt();
        String securePassword = registerBean.getSHA1(passwordToHash);

        try {
            accs = em.createNamedQuery("Accounts.findByUsernameAndPassHash", Accounts.class)
                    .setParameter("username", username).setParameter("passwordHash", securePassword).getSingleResult();
            this.setUsername(accs.getUsername());
        } catch (Exception e) {

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error logging in!",
                    "Invalid User Name or Password entered ");
            context.addMessage("loginForm:login", message);
            setUsername("");
            setPassword("");
            return "login";
        }
        if (accs != null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            session.setAttribute("username", username);
        } else {
            return "login";
        }

        if (accs.getIsAdmin()) {
            return "admin_index?faces-redirect=true";
        } else {
            return "user_index?faces-redirect=true";
        }
    }

    public String logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.invalidate();
        setUsername("");
        setPassword("");
        return "login";
    }

    public boolean isUserLoggedIn() {
        boolean isLogged;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        isLogged = session.getAttribute("username") != null;
        return isLogged;
    }

    public boolean isUserNotLoggedIn() {
        return !this.isUserLoggedIn();
    }

    public String getUsernameSession() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        return session.getAttribute("username").toString();
    }
    
    public boolean isUserNotAdmin() {
        boolean isAdmin;
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        
        isAdmin = session.getAttribute("username") != "ADMIN";
        return isAdmin;
    }

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
