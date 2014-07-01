package com.resgain.base.action.pdm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.resgain.base.entity.pdm.PdmTable;
import com.resgain.base.result.Page;
import com.resgain.base.service.PdmService;

public class ColumnsAction
{
	@Autowired PdmService pdmService;

	public Page exec(String tableId){
		Page page = Page.getInstance(null);
		List<PdmTable> list = pdmService.getTableList();
		Map<String, PdmTable> tables = new HashMap<String, PdmTable>();
		for (PdmTable pt : list) {
			tables.put(pt.getId(), pt);
		}
		return page.put("columnList", pdmService.getColumnList(tableId)).put("table", pdmService.getTable(tableId)).put("tables", tables);
	}
}