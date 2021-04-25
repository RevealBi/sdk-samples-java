package io.revealbi.samples.mybi.shiro;

import java.io.Serializable;

public class DisplayNamePrincipal implements Serializable {
	private static final long serialVersionUID = 1L;

	private String displayName;
	public DisplayNamePrincipal(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
}
