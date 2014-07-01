package com.resgain.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.resgain.base.abs.AbstractService;
import com.resgain.base.abs.IDictService;
import com.resgain.base.entity.DictCategory;
import com.resgain.base.entity.DictCode;
import com.resgain.dragon.util.JSONUtil;

/**
 * 字典业务方法类
 * @author gyl
 */
@Service
public class DictService extends AbstractService  implements IDictService
{
	public void saveDictCategory(DictCategory dc) {
		super.save(dc);
	}

	public List<DictCategory> getCateList() {
		return find(DictCategory.class, null, null);
	}

	public DictCategory getDictCategoryByCode(String category) {
		return get(DictCategory.class, null, "a.code=?", category);
	}

	@Cacheable(value = "dictCache")
	public DictCode getDictCode(String id) {
		return get(DictCode.class, id);
	}

	public DictCode getDictCode11(String id) {
		return getDictCode(id);
	}

	@Cacheable(value = "dictCache")
	public List<DictCode> getDictList(String cate) {
		return find(DictCode.class, null, "a.category=?", cate);
	}

	@Cacheable(value = "dictCache")
	@Override
	public List<DictCode> getDataList(String category, String pcode, String keyword) // TODO 增加其他条件处理
	{
		return find(DictCode.class, null, "a.category=?", category);
	}

	@CacheEvict(value = "dictCache", allEntries = true)
	public void saveDictCode(DictCode dict, String prefix) { // TODO 字典条件清除
		super.smartSave(dict, prefix);
	}

	@CacheEvict(value = "dictCache", allEntries = true)
	public void delDictCode(String id) { // TODO 字典条件清除
		delete(DictCode.class, id);
	}

	@SuppressWarnings("unchecked")
	public String getDicJsons(String...categories){
		if(categories==null || categories.length<1)
			return null;
		String hql = "select new list(a.code, a.label) "+getHql(DictCode.class, null, "a.category=?");
		Map<String, List<List<String>>> ret = new HashMap<String, List<List<String>>>();
		for (String cate : categories) {
			ret.put(cate, query(hql, cate).list());
		}
		return JSONUtil.toJson(ret);
	}

}