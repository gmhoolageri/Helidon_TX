package com.servicemanager.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import com.servicemanager.util.Util;

@MappedSuperclass
public abstract class BaseDO implements Serializable {

    private static final long serialVersionUID = 6611837717088472439L;

    @JsonbTransient
    @Version
    @Column(name = "VERSION", precision = 5)
    private Integer version;

    @JsonbTransient
    @Column(name = "CREATION", nullable = false)
    private Timestamp creation;

    @PrePersist
    private void onPrePersist() {
        Date d = new Date();
        setCreation(new Timestamp(d.getTime()));
        setModified(new Timestamp(d.getTime()));
    }

    @JsonbTransient
    @Column(name = "MODIFIED", nullable = false)
    private Timestamp modified;

    @PreUpdate
    private void onPreUpdate() {
        Date d = new Date();
        setModified(new Timestamp(d.getTime()));
    }

    /**
     * Access method for version.
     *
     * @return the current value of version
     */
    @Deprecated
    public Integer getVersion() {
        return version;
    }

    /**
     * Setter method for version.
     *
     * @param aVersion the new value for version
     */
    @Deprecated
    public void setVersion(Integer aVersion) {
        version = aVersion;
    }

    /**
     * Access method for creation.
     *
     * @return the current value of creation
     */
    public String getFormattedCreation() {
        return Util.formatTimeStamp(creation, Util.YYYY_MM_DD_T_HH_MM_SS_FORMAT);
    }

    @JsonbTransient
    public Timestamp getCreation() {
        return creation;
    }

    /**
     * Setter method for creation.
     *
     * @param aCreation the new value for creation
     */
    public void setCreation(Timestamp aCreation) {
        creation = aCreation;
    }

    /**
     * Access method for modified.
     *
     * @return the current value of modified
     */
    public String getFormattedModified() {
        return Util.formatTimeStamp(modified, Util.YYYY_MM_DD_T_HH_MM_SS_FORMAT);
    }

    @JsonbTransient
    public Timestamp getModified() {
        return modified;
    }

    /**
     * Setter method for modified.
     *
     * @param aModified the new value for modified
     */
    public void setModified(Timestamp aModified) {
        modified = aModified;
    }

    @Override
    public String toString() {
        return "BaseDO [version=" + version + ", creation=" + creation + ", modified=" + modified + "]";
    }

}
