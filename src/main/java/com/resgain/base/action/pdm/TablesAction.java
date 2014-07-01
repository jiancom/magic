package com.resgain.base.action.pdm;

import org.springframework.beans.factory.annotation.Autowired;

import com.resgain.base.result.Page;
import com.resgain.base.service.PdmService;
import com.resgain.dragon.exception.KnowException;
import com.resgain.dragon.util.bean.UploadBean;

public class TablesAction
{
	@Autowired PdmService pdmService;

	public Page exec(){
		return Page.getInstance(null).put("tableList", pdmService.getTableList());
	}

	public void upload(UploadBean pdm){
		if(pdm==null || !".pdm".equalsIgnoreCase(pdm.getExtName()))
			throw new KnowException("文件不对，请上传Powerdesigner设计的PDM文件。");
		pdmService.savePdms(pdm.getLsInputStream());
	}
}