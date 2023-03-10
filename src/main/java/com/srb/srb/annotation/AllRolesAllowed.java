package com.srb.srb.annotation;

import jakarta.annotation.security.RolesAllowed;

import java.lang.annotation.*;

import static com.srb.srb.enums.RoleEnum.ROLES.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@RolesAllowed({관리자, 일반, 소셜})
public @interface AllRolesAllowed {
}
