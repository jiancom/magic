@TypeDefs( {
	@TypeDef(
		    name="encryptedString",
		    typeClass=EncryptedStringType.class,
		    parameters={@org.hibernate.annotations.Parameter(name="encryptorRegisteredName", value="strongHibernateStringEncryptor")}
		) })

package com.resgain.base.entity;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jasypt.hibernate4.type.EncryptedStringType;

