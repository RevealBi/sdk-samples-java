package io.revealbi.samples.mybi.dashboards;

public class CredentialRepositoryFactory {
	private static ICredentialRepository instance;
	
	public static ICredentialRepository getInstance() {
		return instance;
	}
	
	public static void setInstance(ICredentialRepository instance) {
		CredentialRepositoryFactory.instance = instance;
	}
}
