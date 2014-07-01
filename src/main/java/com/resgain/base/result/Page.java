package com.resgain.base.result;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.resgain.base.abs.bean.Breadcrumb;
import com.resgain.base.util.ViewUtil;
import com.resgain.dragon.filter.ActionContext;
import com.resgain.dragon.filter.result.Template;

public class Page extends Template
{
	private static Logger logger = LoggerFactory.getLogger(Page.class);

	private static String WEB_ROOT = ActionContext.getHttpServletRequest().getServletContext().getRealPath(String.valueOf("/"));
	private static Map<String, List<String>> expectMap = getExceptMap();

	public Page(String tname) {
		super(tname);
	}

	public static Page getInstance() {
		return getInstance(null);
	}

	/**
	 * 获取输出实例，指定模板文件名
	 * @return
	 */
	public static Page getInstance(String tname) {
		Page ret = new Page(tname);
		ret.put("webapp", ActionContext.getHttpServletRequest().getContextPath());
		ret.put("action", ActionContext.getHttpServletRequest().getRequestURI());
		ret.put("vt", ViewUtil.getInstance());
		return ret;
	}

	/**
	 * 获取输出实例，指定模板文件名
	 * @return
	 */
	public static Page getInstance(String tname, String breadName, boolean root) {
		Page ret = getInstance(tname);
		Breadcrumb bread = Breadcrumb.getInstance();
		if (root)
			bread.creatCrumb(breadName);
		else
			bread.addCrumb(breadName);
		ret.put("breadcrumb", bread);
		return ret;
	}

	@Override
	public Page put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	@Override
	public String getTemplate(String path) {
		String fname = super.getTemplate(path);
		String tname = fname.replaceFirst("views", "WEB-INF/vtemp");
		File of = new File(fname);
		File tf = new File(tname);

		if (true || !tf.exists() || tf.lastModified() < of.lastModified()) {
			try {
				String style = getStyleFile(of.getParent());
				if (style == null || (expectMap.containsKey(of.getParent()) && expectMap.get(of.getParent()).contains(of.getName()))) {
					if (!of.getPath().equalsIgnoreCase(tf.getPath()))
						FileUtils.copyFile(of, tf);
				} else {
					File pf = new File(of.getParent() + File.separatorChar + "parent_.vm");
					if (pf.exists()) {
						String ts1 = FileUtils.readFileToString(pf, "UTF-8").replace("{MAIN_BODY}", FileUtils.readFileToString(of, "UTF-8"));
						FileUtils.writeStringToFile(tf, FileUtils.readFileToString(new File(style), "UTF-8").replace("{MAIN_BODY}", ts1), "UTF-8");
					} else {
						FileUtils.writeStringToFile(tf, FileUtils.readFileToString(new File(style), "UTF-8").replace("{MAIN_BODY}", FileUtils.readFileToString(of, "UTF-8")), "UTF-8");
					}
				}
			} catch (Exception e) {
				logger.error("生成目标模板出错:", e);
			}
		}
		return tname;
	}

	private static Map<String, List<String>> getExceptMap() {
		Map<String, List<String>> ret = new HashMap<String, List<String>>();
		Collection<File> files = FileUtils.listFiles(new File(WEB_ROOT), FileFilterUtils.nameFileFilter("except.txt"), TrueFileFilter.INSTANCE);
		for (File file : files) {
			try {
				List<String> lines = FileUtils.readLines(file);
				ret.put(file.getParent(), lines);
				logger.debug("文件{}不采用父框架", lines);
			} catch (IOException e) {
				logger.warn("文件{}读取错误", file.getPath(), e);
			}
		}
		return ret;
	}

	private static String getStyleFile(String name) {
		File ret = new File(name + File.separatorChar + "style_.vm");
		if (ret.exists())
			return ret.getPath();
		if (name.startsWith(WEB_ROOT))
			return getStyleFile(ret.getParentFile().getParent());
		return null;
	}
}