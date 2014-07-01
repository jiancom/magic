package com.resgain.base.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.resgain.base.abs.AbstractService;
import com.resgain.base.entity.pdm.PdmCategory;
import com.resgain.base.entity.pdm.PdmColumn;
import com.resgain.base.entity.pdm.PdmDomain;
import com.resgain.base.entity.pdm.PdmTable;
import com.resgain.dragon.exception.KnowException;
import com.resgain.sparrow.pdm.PDMParser;
import com.resgain.sparrow.pdm.bean.Category;
import com.resgain.sparrow.pdm.bean.Column;
import com.resgain.sparrow.pdm.bean.Domain;
import com.resgain.sparrow.pdm.bean.Project;
import com.resgain.sparrow.pdm.bean.Table;

@Service
public class PdmService extends AbstractService
{
	/**
	 * 保存pdm里的表、字段信息到数据库
	 * @param is
	 */
	public void savePdms(InputStream is)
	{
		jdbcTemplate.execute("delete from PDM_COLUMN");
		jdbcTemplate.execute("delete from PDM_TABLE");
		jdbcTemplate.execute("delete from PDM_DOMAIN");
		jdbcTemplate.execute("delete from PDM_CATEGORY");

		Map<String, PdmTableCache> cache = new HashMap<String, PdmTableCache>();
		Map<String, PdmDomain> domainCache = new HashMap<String, PdmDomain>();
		List<PdmColumn> rcolumns = new ArrayList<PdmColumn>();

		try {
			Project proj = PDMParser.parse(is);
			for (Domain domain: proj.getDomains().values()) {
				PdmDomain pd = new PdmDomain(domain.getCode(), domain.getName(), domain.getType(), domain.getComment());
				super.save(pd);
				domainCache.put(domain.getId(), pd);
			}

			List<Category> cates = proj.getCategoryList();
			for (Category cate : cates) {
				PdmCategory pcate = new PdmCategory(cate.getCode(), cate.getName());
				super.save(pcate);
				for (Table table : cate.getTableList()) {
					PdmTable ptable = new PdmTable(table.getCode(), table.getName(), pcate.getId());
					super.save(ptable);
					cache.put(table.getId(), new PdmTableCache(ptable.getId()));
					for (Column column : table.getColumns()) {
						PdmColumn pcolumn = new PdmColumn(ptable.getId(), column);
						if(!isNullOrSpace(column.getDomainId()) && domainCache.get(column.getDomainId())!=null)
							pcolumn.setDomainId(domainCache.get(column.getDomainId()).getId());
						super.save(pcolumn);
						cache.get(table.getId()).columns.put(column.getId(), pcolumn.getId());
						if(column.getRef()!=null){
							rcolumns.add(pcolumn);
						}
					}
				}
			}
			for (PdmColumn pc : rcolumns) {
				pc.setRefColumnId(cache.get(pc.getRefTableId()).columns.get(pc.getRefColumnId()));
				pc.setRefTableId(cache.get(pc.getRefTableId()).tableId);
			}
		} catch (IOException e) {
			throw new KnowException("PDM文件解析错误："+e.getLocalizedMessage());
		}
	}

	/**
	 * 获取定义的表信息列表
	 * @return
	 */
	public List<PdmTable> getTableList(){
		return find(PdmTable.class, null, null);
	}

	/**
	 * 获取表信息
	 * @param id
	 * @return
	 */
	public PdmTable getTable(String id){
		return get(PdmTable.class, id);
	}

	/**
	 * 获取指定表的字段信息列表
	 * @param tableId
	 * @return
	 */
	public List<PdmColumn> getColumnList(String tableId){
		return find(PdmColumn.class, null, "a.tableId=?", tableId);
	}

	class PdmTableCache {
		String tableId;
		Map<String, String> columns;

		public PdmTableCache(String tableId) {
			super();
			this.tableId = tableId;
			this.columns = new HashMap<String, String>();
		}
	}
}
