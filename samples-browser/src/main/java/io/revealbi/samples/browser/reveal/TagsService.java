package io.revealbi.samples.browser.reveal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

import io.revealbi.sdk.ext.api.AuthorizationProviderFactory;
import io.revealbi.sdk.ext.api.DashboardInfo;
import io.revealbi.sdk.ext.api.DashboardRepositoryFactory;
import io.revealbi.sdk.ext.api.IAuthorizationProvider;
import io.revealbi.sdk.ext.api.IAuthorizationProvider.DashboardActionType;

public class TagsService {
	private static Logger log = Logger.getLogger(TagsService.class.getName());
	private static TagsService instance;
	
	private String filePath;
	private TagsModel tags;
	
	public static TagsService getInstance() {
		return instance;
	}
	
	public static void setInstance(TagsService srv) {
		instance = srv;
	}
	
	public TagsService(String filePath) {
		this.filePath = filePath;
		this.loadTags();
	}
	
	public List<TagDefinition> getTags() {
		return tags.tags;
	}
	
	public DashboardInfo[] getDashboardsWithTag(String userId, String tag) throws IOException {
		Set<String> dashboardIds = tags.getDashboardsWithTag(tag);
		if (dashboardIds.isEmpty()) {
			return new DashboardInfo[0];
		}
		IAuthorizationProvider auth = AuthorizationProviderFactory.getInstance();
		SamplesDashboardRepository repo = (SamplesDashboardRepository)DashboardRepositoryFactory.getInstance();
		List<DashboardInfo> result = new ArrayList<DashboardInfo>();
		for (String dashboardId : dashboardIds) {
			DashboardInfo d = repo.getDashboardInfo(null, dashboardId);
			if (d != null && (auth == null || auth.hasDashboardPermission(userId, dashboardId, DashboardActionType.READ))) {
				result.add(d);
			}
		}
		result.sort(new Comparator<DashboardInfo>() {
			@Override
			public int compare(DashboardInfo d1, DashboardInfo d2) {
				return d1.getDisplayName().compareToIgnoreCase(d2.getDisplayName());
			}
		});
		return result.toArray(new DashboardInfo[result.size()]);
	}
	
	public void setDashboardTags(String dashboardId, Set<String> tags) throws IOException {
		if (tags == null) {
			this.tags.deleteDashboard(dashboardId);
		} else {
			this.tags.setDashboardTags(dashboardId, new ArrayList<String>(tags));
		}
		saveTags();
	}
	
	public Set<String> getDashboardTags(String dashboardId) throws IOException {
		List<String> values = tags.getDashboardTags(dashboardId);
		return values == null ? new HashSet<String>() : new HashSet<String>(values);
	}

	public void dashboardDeleted(String dashboardId) {
		tags.deleteDashboard(dashboardId);
		saveTags();
	}
	
	private void loadTags() {
		File jsonFile = new File(filePath);
		if (!jsonFile.exists() || jsonFile.isDirectory() || !jsonFile.canRead()) {
			tags = new TagsModel();
			return;
		}
		Jsonb jsonb = JsonbBuilder.create();
		try {
			tags = jsonb.fromJson(new FileInputStream(filePath), TagsModel.class);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Failed to load tags.json file", e);
			tags = new TagsModel();
		}		
	}
	
	private synchronized void saveTags() {
		JsonbConfig config = new JsonbConfig();
		config.setProperty(JsonbConfig.FORMATTING, true);		
		Jsonb jsonb = JsonbBuilder.create(config);
		try {
			jsonb.toJson(tags, new FileOutputStream(filePath));
		} catch (Exception e) {
			log.log(Level.SEVERE, "Failed to save tags.json file", e);
		}		
	}
	
	public static class TagDefinition {
		private String id;
		private String label;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}		
	}
	
	public static class TagsModel {
		public List<TagDefinition> tags;
		public Map<String, List<String>> dashboardTags;
		
		public TagsModel() {
			tags = new ArrayList<TagDefinition>();
			dashboardTags = new HashMap<String, List<String>>();
		}

		public synchronized Set<String> getDashboardsWithTag(String tag) {
			Set<String> result = new HashSet<String>();
			for (String dashboardId : dashboardTags.keySet()) {
				List<String> dTags = dashboardTags.get(dashboardId);
				if (dTags.contains(tag)) {
					result.add(dashboardId);
				}
			}
			return result;
		}

		public synchronized List<String> getDashboardTags(String dashboardId) {
			return dashboardTags.get(dashboardId);
		}
		
		public synchronized void setDashboardTags(String dashboardId, List<String> tags) {
			dashboardTags.put(dashboardId, tags);
		}

		public synchronized void deleteDashboard(String dashboardId) {
			dashboardTags.remove(dashboardId);
		}
	}
}
