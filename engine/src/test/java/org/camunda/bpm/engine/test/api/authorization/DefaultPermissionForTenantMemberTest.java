package org.camunda.bpm.engine.test.api.authorization;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.Tenant;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.util.ProcessEngineTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;

public class DefaultPermissionForTenantMemberTest {

  protected static final String TENANT_ONE = "tenant1";
  protected static final String TENANT_TWO = "tenant2";
  protected static final String USER_ID = "user";
  protected static final String GROUP_ID = "group";

  protected ProcessEngineRule engineRule = new ProcessEngineRule(true);

  protected ProcessEngineTestRule testRule = new ProcessEngineTestRule(engineRule);

  protected AuthorizationService authorizationService;
  protected IdentityService identityService;

  @Rule
  public RuleChain ruleChain = RuleChain.outerRule(engineRule).around(testRule);

  @Before
  public void init() {
    identityService = engineRule.getIdentityService();
    authorizationService = engineRule.getAuthorizationService();

    createTenant(TENANT_ONE);

    User user = identityService.newUser(USER_ID);
    identityService.saveUser(user);

    Group group = identityService.newGroup(GROUP_ID);
    identityService.saveGroup(group);

    engineRule.getProcessEngineConfiguration().setAuthorizationEnabled(true);
  }

  @After
  public void tearDown() {
    identityService.clearAuthentication();

    identityService.deleteUser(USER_ID);
    identityService.deleteGroup(GROUP_ID);
    identityService.deleteTenant(TENANT_ONE);
    identityService.deleteTenant(TENANT_TWO);

    engineRule.getProcessEngineConfiguration().setAuthorizationEnabled(false);
  }

  @Test
  public void testCreateTenantUserMembership() {

    identityService.createTenantUserMembership(TENANT_ONE, USER_ID);

    assertEquals(1, authorizationService.createAuthorizationQuery()
      .userIdIn(USER_ID)
      .resourceType(Resources.TENANT)
      .resourceId(TENANT_ONE)
      .hasPermission(Permissions.READ).count());

    identityService.setAuthenticatedUserId(USER_ID);

    assertEquals(TENANT_ONE,identityService.createTenantQuery()
     .singleResult()
     .getId());
  }

  @Test
  public void testCreateAndDeleteTenantUserMembership() {

    identityService.createTenantUserMembership(TENANT_ONE, USER_ID);

    identityService.deleteTenantUserMembership(TENANT_ONE, USER_ID);

    assertEquals(0, authorizationService.createAuthorizationQuery()
      .userIdIn(USER_ID)
      .resourceType(Resources.TENANT)
      .hasPermission(Permissions.READ).count());

    identityService.setAuthenticatedUserId(USER_ID);

    assertEquals(0,identityService.createTenantQuery()
     .count());
  }

  @Test
  public void testCreateAndDeleteTenantUserMembershipForMultipleTenants() {

    createTenant(TENANT_TWO);

    identityService.createTenantUserMembership(TENANT_ONE, USER_ID);
    identityService.createTenantUserMembership(TENANT_TWO, USER_ID);

    assertEquals(2, authorizationService.createAuthorizationQuery()
      .userIdIn(USER_ID)
      .resourceType(Resources.TENANT)
      .hasPermission(Permissions.READ).count());

    identityService.deleteTenantUserMembership(TENANT_ONE, USER_ID);

    assertEquals(1, authorizationService.createAuthorizationQuery()
      .userIdIn(USER_ID)
      .resourceType(Resources.TENANT)
      .hasPermission(Permissions.READ).count());
  }

  @Test
  public void testCreateTenantGroupMembership() {

    identityService.createTenantGroupMembership(TENANT_ONE, GROUP_ID);

    assertEquals(1, authorizationService.createAuthorizationQuery()
      .groupIdIn(GROUP_ID)
      .resourceType(Resources.TENANT)
      .resourceId(TENANT_ONE)
      .hasPermission(Permissions.READ).count());

    identityService.setAuthentication(USER_ID, Collections.singletonList(GROUP_ID));

    assertEquals(TENANT_ONE,identityService.createTenantQuery()
      .singleResult()
      .getId());
  }

  @Test
  public void testCreateAndDeleteTenantGroupMembership() {

    identityService.createTenantGroupMembership(TENANT_ONE, GROUP_ID);

    identityService.deleteTenantGroupMembership(TENANT_ONE, GROUP_ID);

    assertEquals(0, authorizationService.createAuthorizationQuery()
      .groupIdIn(GROUP_ID)
      .resourceType(Resources.TENANT)
      .hasPermission(Permissions.READ).count());

    identityService.setAuthentication(USER_ID, Collections.singletonList(GROUP_ID));

    assertEquals(0,identityService.createTenantQuery()
     .count());
  }

  @Test
  public void testCreateAndDeleteTenantGroupMembershipForMultipleTenants() {

    createTenant(TENANT_TWO);

    identityService.createTenantGroupMembership(TENANT_ONE, GROUP_ID);
    identityService.createTenantGroupMembership(TENANT_TWO, GROUP_ID);

    assertEquals(2, authorizationService.createAuthorizationQuery()
      .groupIdIn(GROUP_ID)
      .resourceType(Resources.TENANT)
      .hasPermission(Permissions.READ).count());

    identityService.deleteTenantGroupMembership(TENANT_ONE, GROUP_ID);

    assertEquals(1, authorizationService.createAuthorizationQuery()
      .groupIdIn(GROUP_ID)
      .resourceType(Resources.TENANT)
      .hasPermission(Permissions.READ).count());
  }

  protected Tenant createTenant(String tenantId) {
    Tenant newTenant = identityService.newTenant(tenantId);
    identityService.saveTenant(newTenant);
    return newTenant;
  }
}
