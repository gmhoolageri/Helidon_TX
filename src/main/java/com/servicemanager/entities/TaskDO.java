package com.servicemanager.entities;

import java.math.BigDecimal;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import com.servicemanager.constants.TaskState;
import com.servicemanager.repositories.TaskRepository;

@Entity(name = "TASK")
@NamedQueries({ @NamedQuery(name = TaskRepository.findTasksByState_Name, query = TaskRepository.findTasksByState_Query),
        @NamedQuery(name = TaskRepository.findTasksByStateAndServiceRequestId_Name, query = TaskRepository.findTasksByStateAndServiceRequestId_Query) })
public class TaskDO extends BaseDO {

    private static final long serialVersionUID = -1180362179745937346L;

    @Id
    @Column(name = "ID", unique = true, nullable = false, precision = 30)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "TASK_ID_GENERATOR")
    @SequenceGenerator(name = "TASK_ID_GENERATOR", sequenceName = "TASK_ID", allocationSize = 1)
    private BigDecimal id;

    @Column(name = "INVOCATION_CLASS", length = 500)
    private String invocationClass;

    @Column(name = "INVOCATION_URI", length = 500)
    private String invocationURI;

    @Column(name = "STATE", length = 255)
    @Enumerated(EnumType.STRING)
    private TaskState state;

    @Column(name = "EXECUTION_SEQUENCE", precision = 5)
    private BigDecimal executionSequence;

    @JsonbTransient
    @ManyToOne(optional = false)
    @JoinColumn(name = "SERVICE_REQUEST_ID_FK", nullable = false)
    private ServiceRequestDO serviceRequest;

    /** Default constructor. */
    public TaskDO() {
        super();
    }

    /**
     * Access method for id.
     *
     * @return the current value of id
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * Setter method for id.
     *
     * @param aId the new value for id
     */
    public void setId(BigDecimal aId) {
        id = aId;
    }

    /**
     * Access method for invocationClass.
     *
     * @return the current value of invocationClass
     */
    public String getInvocationClass() {
        return invocationClass;
    }

    /**
     * Setter method for invocationUriClass.
     *
     * @param aInvocationUriClass the new value for invocationUriClass
     */
    public void setInvocationClass(String aInvocationClass) {
        invocationClass = aInvocationClass;
    }

    /**
     * Access method for invocationUri.
     *
     * @return the current value of invocationUri
     */
    public String getInvocationURI() {
        return invocationURI;
    }

    /**
     * Setter method for invocationUri.
     *
     * @param aInvocationUri the new value for invocationUri
     */
    public void setInvocationURI(String invocationURI) {
        this.invocationURI = invocationURI;
    }

    /**
     * Access method for state.
     *
     * @return the current value of state
     */
    public TaskState getState() {
        return state;
    }

    /**
     * Setter method for state.
     *
     * @param aState the new value for state
     */
    public void setState(TaskState aState) {
        state = aState;
    }

    /**
     * Access method for executionSequence.
     *
     * @return the current value of executionSequence
     */
    public BigDecimal getExecutionSequence() {
        return executionSequence;
    }

    /**
     * Setter method for executionSequence.
     *
     * @param aExecutionSequence the new value for executionSequence
     */
    public void setExecutionSequence(BigDecimal aExecutionSequence) {
        executionSequence = aExecutionSequence;
    }

    /**
     * Access method for serviceRequest.
     *
     * @return the current value of serviceRequest
     */
    public ServiceRequestDO getServiceRequest() {
        return serviceRequest;
    }

    /**
     * Setter method for serviceRequest.
     *
     * @param aServiceRequest the new value for serviceRequest
     */
    public void setServiceRequest(ServiceRequestDO aServiceRequest) {
        serviceRequest = aServiceRequest;
    }

    /**
     * Returns a debug-friendly String representation of this instance.
     *
     * @return String representation of this instance
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[Task |");
        sb.append(" id=").append(getId());
        sb.append(" invocationClass=").append(invocationClass);
        sb.append(" invocationURI=").append(invocationURI);
        sb.append(" executionSequence=").append(executionSequence);
        sb.append("]");
        return sb.toString();
    }
}
