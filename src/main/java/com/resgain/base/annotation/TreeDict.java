package com.resgain.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.resgain.base.abs.ITreeDictService;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TreeDict
{
	String category();
	Class<? extends ITreeDictService> impl();
}
