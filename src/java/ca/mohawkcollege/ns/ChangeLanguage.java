/**
 * I, Nenad Skocic, 000107650 certify that this material is my original work. 
 * No other person's work has been used without due acknowledgment.
 */
package ca.mohawkcollege.ns;

import java.io.Serializable;
import java.util.Locale;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Nenad
 */
@Named("language")
@SessionScoped
public class ChangeLanguage implements Serializable {
    // private static final long serialVersionUID = 2756934361134603857L;
   
    private Locale locale;

    public ChangeLanguage() {
      locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    } // Default constructor 
    
    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
        locale = new Locale(language);
    }

    /**
     * Sets the current {@code Locale} for each user session
     *
     * @param languageCode - ISO-639 language code
     */
    public void changeLanguage(String languageCode) {
        locale = new Locale(languageCode);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
}
