package com.resgain.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.resgain.base.abs.IDictService;
import com.resgain.base.service.DictService;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict
{
	String category();
	Class<? extends IDictService> impl() default DictService.class;
}
