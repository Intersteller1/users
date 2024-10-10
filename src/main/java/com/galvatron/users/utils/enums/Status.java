package com.galvatron.users.utils.enums;

/**
 * Created by Shashank on 10/10/24 at 21:29 PM.
 */
public enum Status {

    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    SUSPEND("SUSPEND"),
    DELETED("DELETED"),
    EXPIRED("EXPIRED"),
    BLOCKED("BLOCKED"),
    UNBLOCK("UNBLOCK"),
    UNSETTLED("UNSETTLED"),
    SETTLED("SETTLED"),
    SUCCESS("SUCCESS"),

    VALIDATED("VALIDATED"),
    INITIATED("INITIATED"),
    REJECTED("REJECTED"),
    OK("OK"),
    OFFERED("OFFERED"),
    DISBURSED("DISBURSED"),
    SUCCESSFUL("SUCCESSFUL"),
    KYC_ON_HOLD("KYC_ON_HOLD"),
    PARTIALLY_REPAID("partially_repaid"),
    OPEN("OPEN"),
    CLOSED("CLOSED"),
    FAILURE("FAILURE"),
    UNDER_MAINTENANCE("UNDERMAINTENANCE"),
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    REVALIDATE("REVALIDATE"),
    CONTACT_DETAIL_ENTERED("CONTACTDETAILENTERED"),
    OTP_VERIFICATION("OTPVERIFICATION"),
    BASIC_D_ENTERED("BASICDENTERED"),
    ADDRESS_ENTERED("ADDRESSENTERED"),
    IDENTIFY_DONE("IDENTIFYDONE"),
    BANK_ENTERED("BANKENTERED"),
    DUPLICATE("DUPLICATE")
    ;

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static Status fromString(String status){
        for(Status value : values()) {
            if(value.getStatus().equals(status)) {
                return value;
            }
        }
        return null;
    }
}

