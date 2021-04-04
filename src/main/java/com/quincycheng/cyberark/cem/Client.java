package com.quincycheng.cyberark.cem;

import java.util.*;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.quincycheng.cyberark.cem.entities.Remediation;
import com.quincycheng.cyberark.cem.utility.HTTPClient;

public class Client {
    private String cemUrl = "https://api.cem.cyberark.com";
    private String cemAccessKey;
    private String cemOrg;
    private String cemToken;

    public Client(String organization, String accessKey) {

        this.cemAccessKey = accessKey;
        this.cemOrg = organization;
    }

    // Authenicate to CEM to get the
    private String authenticate() {
        String requestBody = " { \"organization\": \"" + cemOrg + "\", \"accessKey\":\"" + cemAccessKey + "\" }";
        String rawResponse = HTTPClient.doPost("https://api.cem.cyberark.com/apis/login", null, requestBody);

        ReadContext ctxPayload = JsonPath.parse(rawResponse);
        return ctxPayload.read("$.token").toString();
    }

    public Remediation getRemediation(String leastPrivilegeOption, String platform, String accountId, String entityId) {
        Remediation result = new Remediation();

        String requestUrl = "https://api.cem.cyberark.com/recommendations/remediations?" + "platform=" + platform
                + "&account_id=" + accountId + "&entity_id=" + entityId;

        String rawResponse = HTTPClient.doGet(requestUrl, this.authenticate());

//        System.out.println("=====");
//        System.out.println(rawResponse);

        ReadContext ctxPayload = JsonPath.parse(rawResponse);
        String encodedCommand = ctxPayload
                .read("$.remediations[0].UN_USED_PERMISSIONS." + leastPrivilegeOption + ".data.commands").toString();
        byte[] decodedBytes = Base64.getDecoder().decode(encodedCommand);

        result.setCommand(new String(decodedBytes));

        int noOfPolicies = 0;
        try {
            noOfPolicies = (int) ctxPayload
                    .read("$.remediations[0].UN_USED_PERMISSIONS." + leastPrivilegeOption + ".data.policies.length()");

            if (noOfPolicies != 0) {

                LinkedHashMap<String, String> keyList = ctxPayload
                        .read("$.remediations[0].UN_USED_PERMISSIONS." + leastPrivilegeOption + ".data.policies");

                for (Map.Entry<String, String> entry : keyList.entrySet()) {
                    String policyName = entry.getKey();
                    byte[] decodedBytesPolicyContent = Base64.getDecoder().decode(entry.getValue());
                    result.addPolicy(policyName, new String(decodedBytesPolicyContent));

                }

                // for (String theKey : keyList) {
                // result.addPolicy(theKey,
                // (String)ctxPayload.read("$.remediations[0].UN_USED_PERMISSIONS."+leastPrivilegeOption+".data.policies."+
                // theKey ));
                // }
            }
        } catch (Exception e) {
           // e.printStackTrace();
        }
        return result;
    }
}
