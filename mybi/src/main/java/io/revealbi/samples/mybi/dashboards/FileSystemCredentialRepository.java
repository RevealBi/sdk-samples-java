package io.revealbi.samples.mybi.dashboards;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import com.infragistics.reveal.sdk.api.IRVDataSourceCredential;
import com.infragistics.reveal.sdk.api.RVUsernamePasswordDataSourceCredential;
import com.infragistics.reveal.sdk.api.model.RVDashboardDataSource;

public class FileSystemCredentialRepository implements ICredentialRepository {
	private static Logger log = Logger.getLogger(FileSystemCredentialRepository.class.getName());
	
	private String filePath;
	private Map<String, IRVDataSourceCredential> credentials;
	private long cacheTimestamp;
	
	public FileSystemCredentialRepository(String filePath) {
		this.filePath = filePath;
	}
	
	@Override
	public IRVDataSourceCredential resolveCredentials(String userId, RVDashboardDataSource ds) {
		return getDataSourceCredential(ds);
	}

	private synchronized IRVDataSourceCredential getDataSourceCredential(RVDashboardDataSource ds) {
		ensureCredentials();
		return credentials.get(ds.getId());
	}
	
	private synchronized void ensureCredentials() {
		File jsonFile = new File(filePath);
		if (!jsonFile.exists() || jsonFile.isDirectory() || !jsonFile.canRead()) {
			credentials = new HashMap<String, IRVDataSourceCredential>();
			return;
		}
		
		if (cacheTimestamp != jsonFile.lastModified()) {
			if (cacheTimestamp > 0) {
				log.info("Detected changes in credentials.json, loading again");
			}
			credentials = loadFromJson(filePath);
			if (credentials == null) { //load failed
				credentials = new HashMap<String, IRVDataSourceCredential>();
			} else {				
				cacheTimestamp = jsonFile.lastModified();
				
				log.info("Loaded credentials: " + credentials.keySet());
			}
		}
	}
	
	private static Map<String, IRVDataSourceCredential> loadFromJson(String filePath) {
		Jsonb jsonb = JsonbBuilder.create();
		try {
			CredentialsConfig config = jsonb.fromJson(new FileInputStream(filePath), CredentialsConfig.class);
			return config.getCredentialsMap();
		} catch (Exception e) {
			log.log(Level.SEVERE, "Failed to load credentials.json file", e);
			return null;
		}
	}

	public static class CredentialsConfig {
		public Credential[] credentials;
		
		public Map<String, IRVDataSourceCredential> getCredentialsMap() {
			Map<String, IRVDataSourceCredential> map = new HashMap<String, IRVDataSourceCredential>();
			if (credentials != null) {
				for (Credential c : credentials) {
					if (c.getDataSourceId() == null) {
						continue;
					}
					map.put(c.getDataSourceId(), c.getDataSourceCredential());
				}
			}
			
			return map;
		}
	}
	
	public static class Credential {
		private String dataSourceId;
		private String userName;
		private String domain;
		private String password;		

		public String getDataSourceId() {
			return dataSourceId;
		}
		public void setDataSourceId(String dataSourceId) {
			this.dataSourceId = dataSourceId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getDomain() {
			return domain;
		}
		public void setDomain(String domain) {
			this.domain = domain;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
		public IRVDataSourceCredential getDataSourceCredential() {
			return new RVUsernamePasswordDataSourceCredential(userName, password, domain);
		}
	}
}
