/**
 * I, Nenad Skocic, 000107650 certify that this material is my original work. 
 * No other person's work has been used without due acknowledgment.
 */
package ca.mohawkcollege.ns;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nenad
 */
@Entity
@Table(name = "ACCOUNTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accounts.findAll", query = "SELECT a FROM Accounts a")
    , @NamedQuery(name = "Accounts.findByUserId", query = "SELECT a FROM Accounts a WHERE a.userId = :userId")
    , @NamedQuery(name = "Accounts.findByUsername", query = "SELECT a FROM Accounts a WHERE a.username = :username")
    , @NamedQuery(name = "Accounts.findByUsernameAndPassHash", query = "SELECT a FROM Accounts a WHERE a.username = :username AND a.passwordHash = :passwordHash")
    , @NamedQuery(name = "Accounts.findByIsAdmin", query = "SELECT a FROM Accounts a WHERE a.isAdmin = :isAdmin")
    , @NamedQuery(name = "Accounts.findByPasswordHash", query = "SELECT a FROM Accounts a WHERE a.passwordHash = :passwordHash")})
public class Accounts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USER_ID")
    private Integer userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_ADMIN")
    private Boolean isAdmin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "PASSWORD_HASH")
    private String passwordHash;

    public Accounts() {
    }

    public Accounts(Integer userId) {
        this.userId = userId;
    }

    public Accounts(Integer userId, String username, Boolean isAdmin, String passwordHash) {
        this.userId = userId;
        this.username = username;
        this.isAdmin = isAdmin;
        this.passwordHash = passwordHash;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accounts)) {
            return false;
        }
        Accounts other = (Accounts) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ca.mohawkcollege.ns.Accounts[ userId=" + userId + " ]";
    }
    
}
