package com.shhxzq.fin.simulator.web.shiro;

import freemarker.template.SimpleHash;
import org.springframework.stereotype.Component;

/**
 * Shortcut for injecting the tags into Freemarker
 * <p>
 * <p>Usage: cfg.setSharedVeriable("shiro", new ShiroTags());</p>
 */
@Component("shiroTags")
public class ShiroTags extends SimpleHash {
    public ShiroTags() {
        put("authenticated", new AuthenticatedTag());
        put("guest", new GuestTag());
        put("hasAnyRoles", new HasAnyRolesTag());
        put("hasPermission", new HasPermissionTag());
        put("hasRole", new HasRoleTag());
        put("lacksPermission", new LacksPermissionTag());
        put("lacksRole", new LacksRoleTag());
        put("notAuthenticated", new NotAuthenticatedTag());
        put("principal", new PrincipalTag());
        put("user", new UserTag());
    }
}