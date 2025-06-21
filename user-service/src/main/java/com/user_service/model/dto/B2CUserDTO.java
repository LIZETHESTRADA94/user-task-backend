package com.user_service.model.dto;

import java.util.List;
import java.util.Map;

public class B2CUserDTO {
    public String accountEnabled = "true";
    public Map<String, Object> passwordProfile;
    public List<Map<String, Object>> identities;
    public String displayName;
    public String mailNickname;
    public String userPrincipalName;

    public B2CUserDTO(String displayName, String email, String password) {
        this.displayName = displayName;
        this.mailNickname = email.replace("@","_");
        this.userPrincipalName = this.mailNickname + "@appfm.onmicrosoft.com";
        this.passwordProfile = Map.of(
            "forceChangePasswordNextSignIn", false,
            "password", password
        );

        this.identities = List.of(
            Map.of(
                "signInType", "emailAddress",
                "issuer", "appfm.onmicrosoft.com",
                "issuerAssignedId", email
            )
        );
    }
}
