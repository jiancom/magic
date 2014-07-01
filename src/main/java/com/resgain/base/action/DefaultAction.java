package com.resgain.base.action;

import com.resgain.base.result.Page;

public class DefaultAction
{
	public Page exec(){
		return Page.getInstance(null);
	}
}
