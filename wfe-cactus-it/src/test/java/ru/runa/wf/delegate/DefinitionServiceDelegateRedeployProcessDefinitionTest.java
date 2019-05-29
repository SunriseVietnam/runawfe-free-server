package ru.runa.wf.delegate;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import org.apache.cactus.ServletTestCase;
import ru.runa.wf.service.WfServiceTestHelper;
import ru.runa.wfe.definition.DefinitionArchiveFormatException;
import ru.runa.wfe.definition.DefinitionDoesNotExistException;
import ru.runa.wfe.definition.dto.WfDefinition;
import ru.runa.wfe.security.AuthenticationException;
import ru.runa.wfe.security.AuthorizationException;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.service.DefinitionService;
import ru.runa.wfe.service.delegate.Delegates;
import ru.runa.wfe.user.User;

/**
 * Created on 20.04.2005
 * 
 * @author Gritsenko_S
 */
public class DefinitionServiceDelegateRedeployProcessDefinitionTest extends ServletTestCase {

    private DefinitionService definitionService;

    private WfServiceTestHelper helper = null;

    private long processDefinitionId;

    @Override
    protected void setUp() throws Exception {
        helper = new WfServiceTestHelper(getClass().getName());
        definitionService = Delegates.getDefinitionService();

        helper.deployValidProcessDefinition();

        processDefinitionId = definitionService.getLatestProcessDefinition(helper.getAdminUser(), WfServiceTestHelper.VALID_PROCESS_NAME)
                .getVersionId();

        Collection<Permission> redeployPermissions = Lists.newArrayList(Permission.UPDATE);
        helper.setPermissionsToAuthorizedPerformerOnDefinitionByName(redeployPermissions, WfServiceTestHelper.VALID_PROCESS_NAME);

        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        helper.undeployValidProcessDefinition();

        helper.releaseResources();
        definitionService = null;
        super.tearDown();
    }

    public void testRedeployProcessByAuthorizedPerformer() throws Exception {
        definitionService.redeployProcessDefinition(helper.getAuthorizedPerformerUser(), processDefinitionId, helper.getValidProcessDefinition(),
                Lists.newArrayList("testProcess"), null);
        List<WfDefinition> deployedProcesses = definitionService.getProcessDefinitions(helper.getAuthorizedPerformerUser(),
                helper.getProcessDefinitionBatchPresentation(), false);
        if (deployedProcesses.size() != 1) {
            assertTrue("testRedeployProcessByAuthorizedPerformer wrongNumberOfProcessDefinitions", false);
        }
        if (!deployedProcesses.get(0).getName().equals(WfServiceTestHelper.VALID_PROCESS_NAME)) {
            assertTrue("testRedeployProcessByAuthorizedPerformer wrongNameOfDeployedProcessDefinitions", false);
        }
    }

    public void testRedeployProcessByAuthorizedPerformerWithoutREDEPLOYPermission() throws Exception {
        Collection<Permission> nullPermissions = Lists.newArrayList();
        helper.setPermissionsToAuthorizedPerformerOnDefinitionByName(nullPermissions, WfServiceTestHelper.VALID_PROCESS_NAME);

        try {
            definitionService.redeployProcessDefinition(helper.getAuthorizedPerformerUser(), processDefinitionId, helper.getValidProcessDefinition(),
                    Lists.newArrayList("testProcess"), null);
            assertTrue("definitionDelegate.redeployProcessByAuthorizedPerformer() no AuthorizationException", false);
        } catch (AuthorizationException e) {
        }
    }

    public void testRedeployProcessByUnauthorizedPerformer() throws Exception {
        try {
            definitionService.redeployProcessDefinition(helper.getUnauthorizedPerformerUser(), processDefinitionId,
                    helper.getValidProcessDefinition(), Lists.newArrayList("testProcess"), null);
            assertTrue("definitionDelegate.redeployProcessByUnauthorizedPerformer() no AuthorizationException", false);
        } catch (AuthorizationException e) {
        }
    }

    public void testRedeployProcessWithFakeSubject() throws Exception {
        try {
            User fakeUser = helper.getFakeUser();
            definitionService.redeployProcessDefinition(fakeUser, processDefinitionId, helper.getValidProcessDefinition(),
                    Lists.newArrayList("testProcess"), null);
            assertTrue("testRedeployProcessWithFakeSubject no AuthenticationException", false);
        } catch (AuthenticationException e) {
        }
    }

    public void testRedeployInvalidProcessByAuthorizedPerformer() throws Exception {
        try {
            definitionService.redeployProcessDefinition(helper.getAuthorizedPerformerUser(), processDefinitionId,
                    helper.getInValidProcessDefinition(), Lists.newArrayList("testProcess"), null);
            assertTrue("definitionDelegate.deployProcessByAuthorizedPerformer() no DefinitionParsingException", false);
        } catch (DefinitionArchiveFormatException e) {
        }
    }

    public void testRedeployWithInvalidProcessId() throws Exception {
        try {
            definitionService.redeployProcessDefinition(helper.getAuthorizedPerformerUser(), -1l, helper.getValidProcessDefinition(),
                    Lists.newArrayList("testProcess"), null);
            fail("testRedeployWithInvalidProcessId() no Exception");
        } catch (DefinitionDoesNotExistException e) {
        }
    }

    public void testRedeployInvalidProcess() throws Exception {
        try {
            definitionService.redeployProcessDefinition(helper.getAuthorizedPerformerUser(), processDefinitionId,
                    helper.getInValidProcessDefinition(), Lists.newArrayList("testProcess"), null);
            fail("testRedeployInvalidProcess() no Exception");
        } catch (DefinitionArchiveFormatException e) {
        }
    }

}
