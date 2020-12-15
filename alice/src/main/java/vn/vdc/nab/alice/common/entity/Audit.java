package vn.vdc.nab.alice.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class Audit {

    /**
     * Assign creationTimestamp attribute
     */
    //@CreatedDate
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTimestamp;

    /**
     * Assign updateTimestamp attribute
     */
    //@LastModifiedDate
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTimestamp;

    /**
     * @return the creationTimestamp
     */
    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    /**
     * @param creationTimestamp the creationTimestamp to set
     */
    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * @return the updateTimestamp
     */
    public Date getUpdateTimestamp() {
        return updateTimestamp;
    }

    /**
     * @param updateTimestamp the updateTimestamp to set
     */
    public void setUpdateTimestamp(Date updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
}
