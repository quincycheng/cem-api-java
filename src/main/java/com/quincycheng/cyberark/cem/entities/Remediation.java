package com.quincycheng.cyberark.cem.entities;

import java.util.ArrayList; 

public class Remediation {
    private String command = "";
    private ArrayList<RemediationPolicy> policyList = new ArrayList<RemediationPolicy>();

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void addPolicy(String policyName, String policyContent) {
        this.policyList.add(new RemediationPolicy(policyName, policyContent));
    }

    public ArrayList<RemediationPolicy> getPolicyList() {
        return policyList;
    }

    public void setPolicyList(ArrayList<RemediationPolicy> policyList) {
        this.policyList = policyList;
    }

    

}
