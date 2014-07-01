package com.resgain.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.resgain.base.abs.AbstractService;
import com.resgain.base.entity.Config;

/**
 * 字典业务方法类
 * @author gyl
 */
@Service
public class ConfigService extends AbstractService
{

	public Map<String, String> getSysConfigs(String category) {
		List<Config> t = find(Config.class, null, "a.category=?", category);
		Map<String, String> ret = new HashMap<String, String>();
		for (Config sys : t) {
			ret.put(sys.getKey(), sys.getValue());
		}
		return ret;
	}

	public Config getSysConfigByKey(String category, String key) {
		Config tmp = get(Config.class, null, "a.key=?", key);
		if (tmp == null)
			tmp = new Config(category, key, null, null);
		return tmp;
	}

	public void save(String category, Map<String, String> config) {
		for (Entry<String, String> entry : config.entrySet()) {
			Config tmp = getSysConfigByKey(category, entry.getKey());
			tmp.setValue(entry.getValue());
			save(tmp);
		}
	}

	public String getValue(String category, String key) {
		return getSysConfigByKey(category, key).getValue();
	}

	public long getLongValue(String category, String key) {
		String t = getSysConfigByKey(category, key).getValue();
		if (isNullOrSpace(t)){
			setValue(category, key, "0");
			return 0;
		}
		return new Long(t).longValue();
	}

	public void setValue(String category, String key, String value) {
			Config tmp = getSysConfigByKey(category, key);
			tmp.setValue(value);
			save(tmp);
	}

	public void list(){
		List<Config> t = find(Config.class, null, "category='ddd'");
		for (Config config : t) {
			System.out.println(config.getValue());
		}
	}
}