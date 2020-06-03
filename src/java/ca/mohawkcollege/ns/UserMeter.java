/**
 * I, Nenad Skocic, 000107650 certify that this material is my original work. 
 * No other person's work has been used without due acknowledgment.
 */
package ca.mohawkcollege.ns;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nenad
 */
@Entity
@Table(name = "USER_METER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserMeter.findAll", query = "SELECT u FROM UserMeter u")
    , @NamedQuery(name = "UserMeter.findByMeterId", query = "SELECT u FROM UserMeter u WHERE u.meterId = :meterId")
    , @NamedQuery(name = "UserMeter.findByMeterIdAndAddress", query = "SELECT u FROM UserMeter u WHERE u.meterId = :meterId AND u.address = :address")
    , @NamedQuery(name = "UserMeter.findByAddress", query = "SELECT u FROM UserMeter u WHERE u.address = :address")})
public class UserMeter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "METER_ID")
    private String meterId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "ADDRESS")
    private String address;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Accounts userId;
    @OneToMany(mappedBy = "meterId")
    private List<MeterReading> meterReadingList;

    public UserMeter() {
    }

    public UserMeter(String meterId) {
        this.meterId = meterId;
    }

    public UserMeter(String meterId, String address) {
        this.meterId = meterId;
        this.address = address;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Accounts getUserId() {
        return userId;
    }

    public void setUserId(Accounts userId) {
        this.userId = userId;
    }

    @XmlTransient
    public List<MeterReading> getMeterReadingList() {
        return meterReadingList;
    }

    public void setMeterReadingList(List<MeterReading> meterReadingList) {
        this.meterReadingList = meterReadingList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (meterId != null ? meterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserMeter)) {
            return false;
        }
        UserMeter other = (UserMeter) object;
        if ((this.meterId == null && other.meterId != null) || (this.meterId != null && !this.meterId.equals(other.meterId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ca.mohawkcollege.ns.UserMeter[ meterId=" + meterId + " ]";
    }
}
