package ru.runa.af.web.action;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.runa.common.web.TabHttpSessionHelper;
import ru.runa.common.web.action.ActionBase;
import ru.runa.wfe.security.AuthorizationException;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.security.SecuredObject;
import ru.runa.wfe.security.SecuredObjectType;
import ru.runa.wfe.service.delegate.Delegates;
import ru.runa.wfe.user.User;

import static ru.runa.wfe.security.SecuredObjectType.ERRORS;
import static ru.runa.wfe.security.SecuredObjectType.FROZEN_PROCESSES;
import static ru.runa.wfe.security.SecuredObjectType.SYSTEM;

public class CheckSingletonPermissionAndForwardAction extends ActionBase {

    private static class Config {
        final Permission permission;
        final String path;
        final boolean forAdministratorOnly;

        Config(Permission permission, String path, boolean forAdministratorOnly) {
            this.permission = permission;
            this.path = path;
            this.forAdministratorOnly = forAdministratorOnly;
        }
    }

    private static final String TAB_FORWARD_NAME_PARAMETER_NAME = "tabForwardName";
    private static final HashMap<SecuredObjectType, Config> configs = new HashMap<SecuredObjectType, Config>() {{
        put(SYSTEM, new Config(Permission.READ, "/WEB-INF/af/manage_system.jsp", false));
        put(ERRORS, new Config(Permission.READ, "/WEB-INF/af/manage_errors.jsp", true));
        put(FROZEN_PROCESSES, new Config(Permission.READ, "/WEB-INF/af/manage_frozen_processes.jsp", true));
    }};

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String tabForwardName = request.getParameter(TAB_FORWARD_NAME_PARAMETER_NAME);
        if (tabForwardName != null) {
            TabHttpSessionHelper.setTabForwardName(tabForwardName, request.getSession());
        }

        try {
            User user = getLoggedUser(request);
            SecuredObjectType securedObjectType = SecuredObjectType.valueOf(mapping.getParameter());
            Config config = configs.get(securedObjectType);
            if (config == null) {
                throw new RuntimeException("No config for securedObjectType = " + securedObjectType);
            }
            if (config.forAdministratorOnly && !Delegates.getExecutorService().isAdministrator(user)) {
                throw new AuthorizationException("Only administrator can access " + request.getRequestURI());
            }
            SecuredObject securedObject = Delegates.getAuthorizationService().findSecuredObject(securedObjectType, 0L);
            Delegates.getAuthorizationService().checkAllowed(user, config.permission, securedObject);
            return new ActionForward(config.path);
        } catch (Exception e) {
            addError(request, e);
            return new ActionForward("/messages_page.do");
        }
    }
}
