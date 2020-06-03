/**
 * I, Nenad Skocic, 000107650 certify that this material is my original work. 
 * No other person's work has been used without due acknowledgment.
 */
package ca.mohawkcollege.ns;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nenad
 */
@Entity
@Table(name = "METER_READING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MeterReading.findAll", query = "SELECT m FROM MeterReading m")
    , @NamedQuery(name = "MeterReading.findByReadingId", query = "SELECT m FROM MeterReading m WHERE m.readingId = :readingId")
    , @NamedQuery(name = "MeterReading.findByReadingDate", query = "SELECT m FROM MeterReading m WHERE m.readingDate = :readingDate")
    , @NamedQuery(name = "MeterReading.findByMeterId", query = "SELECT m FROM MeterReading m WHERE m.meterId = :meterId")
    , @NamedQuery(name = "MeterReading.findByReadingDateAndMeterId", query = "SELECT m FROM MeterReading m WHERE m.readingDate = :readingDate AND m.meterId = :meterId")
    , @NamedQuery(name = "MeterReading.findByReading", query = "SELECT m FROM MeterReading m WHERE m.reading = :reading")})
public class MeterReading implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "READING_ID")
    private Integer readingId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "READING_DATE")
    @Temporal(TemporalType.DATE)
    private Date readingDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "READING")
    private int reading;
    @JoinColumn(name = "METER_ID", referencedColumnName = "METER_ID")
    @ManyToOne
    private UserMeter meterId;

    public MeterReading() {
    }

    public MeterReading(Integer readingId) {
        this.readingId = readingId;
    }

    public MeterReading(Integer readingId, Date readingDate, int reading) {
        this.readingId = readingId;
        this.readingDate = readingDate;
        this.reading = reading;
    }

    public Integer getReadingId() {
        return readingId;
    }

    public void setReadingId(Integer readingId) {
        this.readingId = readingId;
    }

    public Date getReadingDate() {
        return readingDate;
    }

    public void setReadingDate(Date readingDate) {
        this.readingDate = readingDate;
    }

    public int getReading() {
        return reading;
    }

    public void setReading(int reading) {
        this.reading = reading;
    }

    public UserMeter getMeterId() {
        return meterId;
    }

    public void setMeterId(UserMeter meterId) {
        this.meterId = meterId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (readingId != null ? readingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MeterReading)) {
            return false;
        }
        MeterReading other = (MeterReading) object;
        if ((this.readingId == null && other.readingId != null) || (this.readingId != null && !this.readingId.equals(other.readingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ca.mohawkcollege.ns.MeterReading[ readingId=" + readingId + " ]";
    }
}
