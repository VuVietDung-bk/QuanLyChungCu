package application;

import database.DatabaseConnecter;

public class Account {
	private String accountUsername;
	private String accountPassword;
	private String accountOwnerID;
	private String accountType;
	
	public Account(String accountUsername, String accountPassword, String accountOwnerID, String accountType) {
		this.accountUsername = accountUsername;
		this.accountPassword = accountPassword;
		this.accountOwnerID = accountOwnerID;
		this.accountType = accountType;
	}
	
	public String getAccountUsername() {
		return accountUsername;
	}
	public void setAccountUsername(String accountUsername) {
		this.accountUsername = accountUsername;
		DatabaseConnecter.setUsername(accountOwnerID, accountUsername);
	}
	public String getAccountPassword() {
		return accountPassword;
	}
	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
		DatabaseConnecter.setPassword(accountOwnerID, accountPassword);
	}
	public String getAccountOwnerID() {
		return accountOwnerID;
	}
	public void setAccountOwnerID(String accountOwnerID) {
		this.accountOwnerID = accountOwnerID;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "Account [accountUsername=" + accountUsername + ", accountPassword=" + accountPassword
				+ ", accountOwnerID=" + accountOwnerID + ", accountType=" + accountType + "]";
	}
	
}
