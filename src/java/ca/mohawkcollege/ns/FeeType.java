/**
 * I, Nenad Skocic, 000107650 certify that this material is my original work. 
 * No other person's work has been used without due acknowledgment.
 */
package ca.mohawkcollege.ns;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nenad
 */
@Entity
@Table(name = "FEE_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FeeType.findAll", query = "SELECT f FROM FeeType f")
    , @NamedQuery(name = "FeeType.findById", query = "SELECT f FROM FeeType f WHERE f.id = :id")
    , @NamedQuery(name = "FeeType.findByLowRate", query = "SELECT f FROM FeeType f WHERE f.lowRate = :lowRate")
    , @NamedQuery(name = "FeeType.findByMediumRate", query = "SELECT f FROM FeeType f WHERE f.mediumRate = :mediumRate")
    , @NamedQuery(name = "FeeType.findByHighRate", query = "SELECT f FROM FeeType f WHERE f.highRate = :highRate")
    , @NamedQuery(name = "FeeType.findByAdminFee", query = "SELECT f FROM FeeType f WHERE f.adminFee = :adminFee")})
public class FeeType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOW_RATE")
    private BigDecimal lowRate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MEDIUM_RATE")
    private BigDecimal mediumRate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HIGH_RATE")
    private BigDecimal highRate;
    @Column(name = "ADMIN_FEE")
    private BigDecimal adminFee;

    public FeeType() {
    }

    public FeeType(Integer id) {
        this.id = id;
    }

    public FeeType(Integer id, BigDecimal lowRate, BigDecimal mediumRate, BigDecimal highRate) {
        this.id = id;
        this.lowRate = lowRate;
        this.mediumRate = mediumRate;
        this.highRate = highRate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getLowRate() {
        return lowRate;
    }

    public void setLowRate(BigDecimal lowRate) {
        this.lowRate = lowRate;
    }

    public BigDecimal getMediumRate() {
        return mediumRate;
    }

    public void setMediumRate(BigDecimal mediumRate) {
        this.mediumRate = mediumRate;
    }

    public BigDecimal getHighRate() {
        return highRate;
    }

    public void setHighRate(BigDecimal highRate) {
        this.highRate = highRate;
    }

    public BigDecimal getAdminFee() {
        return adminFee;
    }

    public void setAdminFee(BigDecimal adminFee) {
        this.adminFee = adminFee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FeeType)) {
            return false;
        }
        FeeType other = (FeeType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ca.mohawkcollege.ns.FeeType[ id=" + id + " ]";
    }
    
}
