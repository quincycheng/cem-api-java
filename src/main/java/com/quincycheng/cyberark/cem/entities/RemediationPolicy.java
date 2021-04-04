package com.quincycheng.cyberark.cem.entities;

public class RemediationPolicy {

    private String policyName;
    private String policyContent;

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getPolicyContent() {
        return policyContent;
    }

    public void setPolicyContent(String policyContent) {
        this.policyContent = policyContent;
    }

    public RemediationPolicy(String policyName, String policyContent) {
        this.policyName = policyName;
        this.policyContent = policyContent;
    }

}
