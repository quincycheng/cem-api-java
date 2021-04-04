package com.quincycheng.cyberark.cem.example;

import java.util.Iterator;
import java.util.Map;

import com.quincycheng.cyberark.cem.Client;
import com.quincycheng.cyberark.cem.entities.Remediation;
import com.quincycheng.cyberark.cem.entities.RemediationPolicy;

public class cli {
    public static void main(String args[]) {

        if (args.length != 5) {
            printUsage();
        } else if (!args[0].equals("getRemediations")) {
            printUsage();
            System.out.println("ERROR: Invalid action");
        } else if (System.getenv("CEM_ORG") == null || System.getenv("CEM_ACCESS_KEY") == null) {
            printEnvVars();
        } else {

            Client cemClient = new Client(System.getenv("CEM_ORG"), System.getenv("CEM_ACCESS_KEY"));

            Remediation theResult = cemClient.getRemediation(args[1], args[2], args[3], args[4]);
            System.out.println("Command: " + theResult.getCommand());

            System.out.println("No of poliies: " + theResult.getPolicyList().size());

            for (RemediationPolicy thePolicy : theResult.getPolicyList()) {

                System.out.println("Policy name: " + thePolicy.getPolicyName());
                System.out.println("Policy content: " + thePolicy.getPolicyContent());
            }

        }

    }

    public static void printUsage() {
        System.out.println("CEM Java Library");
        System.out.println("================");
        System.out.println("Usage:   java -jar <JAR file> <action> < option> <platform> <account_id> <entity_id>");
        System.out.println("Action: getRemediations");
        System.out.println(
                "Option: LEAST_PRIVILEGE / LEAST_PRIVILEGE_KEEP_ALL_SHADOW_PERMISSIONS / LEAST_PRIVILEGE_REMOVE_SHADOW_PERMISSIONS");
        System.out.println("");

    }

    public static void printEnvVars() {
        System.out.println("Please set the following environment variables:");
        System.out.println(" - CEM_ORG : Organization");
        System.out.println(" - CEM_ACCESS_KEY: Access Key");
    }
}
