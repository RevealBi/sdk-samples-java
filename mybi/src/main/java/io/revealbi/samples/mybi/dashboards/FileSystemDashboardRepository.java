package io.revealbi.samples.mybi.dashboards;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.infragistics.controls.IOUtils;

public class FileSystemDashboardRepository implements IDashboardRepository {
	private String rootDir;
	
	public FileSystemDashboardRepository(String rootDir) {
		this.rootDir = rootDir;
	}
	
	@Override
	public InputStream getDashboard(String userId, String dashboardId) throws IOException {
		String path = getDashboardPath(userId, dashboardId);
		File file = new File(path);
		if (!file.exists() || !file.canRead() || !file.isFile()) {
			path = getDashboardPath(null, dashboardId);
		}
		file = new File(path);
		if (!file.exists() || !file.canRead() || !file.isFile()) {
			return null;
		}
		return new FileInputStream(path);
	}	

	@Override
	public void saveDashboard(String userId, String dashboardId, InputStream dashboardStream) throws IOException {
		String path = getDashboardPath(userId, dashboardId);
		File file = new File(path);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try (FileOutputStream out = new FileOutputStream(file)) {
			IOUtils.copy(dashboardStream, out);
		}
	}
	
	@Override
	public DashboardInfo[] getUserDashboards(String userId) throws IOException {
		String path = getDashboardPath(userId, null);
		File userDir = new File(path);
		if (!userDir.exists() || !userDir.isDirectory()) {
			return new DashboardInfo[0];
		}
		List<DashboardInfo> items = new ArrayList<DashboardInfo>();
		for (File f : userDir.listFiles()) {
			if (f.isDirectory() || !f.canRead() || !f.getName().endsWith(".rdash")) {
				continue;
			}
			items.add(new DashboardInfo(getDashboardName(f.getName()), null));
		}
		items.sort(new Comparator<DashboardInfo>() {
			@Override
			public int compare(DashboardInfo d1, DashboardInfo d2) {
				return d1.getId().compareToIgnoreCase(d2.getId());
			}
		});
		return items.toArray(new DashboardInfo[items.size()]);
	}
	
	private static String getDashboardName(String fileName) {
		if (fileName.endsWith(".rdash")) {
			return fileName.substring(0, fileName.length() - ".rdash".length());
		}
		return fileName;
	}

	private String getDashboardPath(String userId, String dashboardId) {
		File userDir = userId == null ? new File(rootDir) : new File(rootDir, userId);
		if (dashboardId == null) {
			return userDir.getAbsolutePath();
		}
		return new File(userDir, dashboardId + ".rdash").getAbsolutePath();
	}
}
