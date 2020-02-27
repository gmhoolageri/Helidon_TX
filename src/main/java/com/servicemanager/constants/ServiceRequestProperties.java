package com.servicemanager.constants;

public enum ServiceRequestProperties {

    SERVICE_NAME_SUFFIX(true, true), //
    START_DATE, //
    END_DATE, //
    POD_SIZE(true, false), //
    DATACENTER_REGION(true, false), //
    DATABASE_TYPE, //
    IMAGE_VERSION(true, false), //
    OTM_VERSION(true, false), //
    AVAILABILITY_DOMAIN_NAME, //
    SUBNET_NAME, //
    REHYDRATION_PARAMS, //
    REHYDRATION_ISPREFLIGHTONLY, //
    IS_DISASTER_RECOVERY;

    private boolean required;
    private boolean allowNull;

    public boolean isRequired() {
        return this.required;

    }

    public boolean isAllowNull() {
        return this.allowNull;
    }

    private ServiceRequestProperties() {
        required = false;
        allowNull = false;
    }

    private ServiceRequestProperties(boolean isReq, boolean isAllowNull) {
        required = isReq;
        allowNull = isAllowNull;
    }
}
