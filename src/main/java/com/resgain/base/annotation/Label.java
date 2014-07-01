package com.resgain.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Label
{
	public String name();	 //名称
	public String desc() default ""; //说明
	public String type() default ""; //类型
	public int minLength() default 0; //前台校验：最小长度
	public int maxLength() default 0; //前台校验：最大长度
	public boolean nullFlag() default true; //前台校验：是否可以为空
	public String g_options() default ""; // 其他选项

	public String vtype() default ""; // 前台校验类型
	public String xtype() default "textfield";

	public String value() default ""; // 默认值
	public boolean sortable() default false; // 是否可以排序
	public String f_options() default ""; // form字段的其他选项

	public String cshow() default ""; // 对于list类型的显示list中对象的那个属性

}