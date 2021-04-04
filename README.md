# cem-api-java
Java library for CyberArk Cloud Entitlements Manager

## Progress
The following API are implementation, as of 2021 March 22
 - Login
 - Get Remediations

## Build
`./gradlew clean build shadowjar`

## Test
```
export CEM_ORG=<Organization>
export CEM_ACCESS_KEY=<Access Key>

java -jar build/libs/com.quincycheng.cyberark.cem.jar \
     getRemediations [LEAST_PRIVILEGE,LEAST_PRIVILEGE_KEEP_ALL_SHADOW_PERMISSIONS,LEAST_PRIVILEGE_REMOVE_SHADOW_PERMISSIONS] \
     <platform> <account_id> <entity_id> 
```

