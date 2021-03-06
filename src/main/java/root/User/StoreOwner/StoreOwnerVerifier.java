package root.User.StoreOwner;

import root.User.Verifier;

import java.sql.SQLException;

public class StoreOwnerVerifier {
    private Verifier verifier;

    StoreOwnerVerifier(){
        verifier = new Verifier();
    }

    private String verifySocialID(String socialID){
        if (socialID == null || socialID.isEmpty()) {
            return "Error: Social ID cannot be empty";
        }
        return "OK";
    }

    private Boolean usernameExists(String username) throws SQLException {
        return StoreOwnerMapper.select(username) != null;
    }

    private Boolean emailExists(String email) throws SQLException {
        return StoreOwnerMapper.selectByEmail(email) != null;
    }

    public String verify(StoreOwner storeOwner) throws SQLException {
        String status = verifier.verifyUserInfo(storeOwner.getUserInfo());
        if (!status.equals("OK")) {
            return status;
        }
        if (usernameExists(storeOwner.getUsername())) {
            return "Error: Username already exists";
        }
        if (emailExists(storeOwner.getEmail())) {
            return "Error: e-mail already exists";
        }
        return verifySocialID(storeOwner.getSocialID());
    }
    // TODO should exists() function(s) that checks whether the socialID exist in the database be added here?
    // No, Imagine if one person has multiple accounts. Only username and email should be unique
}
