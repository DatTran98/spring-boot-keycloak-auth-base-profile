package vn.dattb.auth.service;

public class AuthorizationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private RoleResourceActionRepository roleResourceActionRepository;

    @Autowired
    private OrganizationRolesRepository organizationRolesRepository;

    @Autowired
    private RoleInheritanceRepository roleInheritanceRepository;

    public boolean hasAccess(String username, String resourceName, String actionName, Long organizationId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Resource resource = resourceRepository.findByName(resourceName)
                .orElseThrow(() -> new IllegalArgumentException("Resource not found"));

        Action action = actionRepository.findByName(actionName)
                .orElseThrow(() -> new IllegalArgumentException("Action not found"));

        if (resource.getDefaultAccess()) {
            return true;
        }

        return user.getRoles().stream()
                .anyMatch(role -> hasOrganizationRoleAccess(role, resource, action, organizationId));
    }

    private boolean hasOrganizationRoleAccess(Role role, Resource resource, Action action, Long organizationId) {
        List<Role> inheritedRoles = getInheritedRoles(role);

        for (Role inheritedRole : inheritedRoles) {
            boolean hasAccess = roleResourceActionRepository.existsByRoleAndResourceAndAction(inheritedRole, resource, action);
            boolean organizationHasRole = organizationRolesRepository.existsByOrganizationAndRole(organizationId, inheritedRole);

            if (hasAccess && organizationHasRole) {
                return true;
            }
        }

        return false;
    }

    private List<Role> getInheritedRoles(Role role) {
        List<Role> inheritedRoles = new ArrayList<>();
        inheritedRoles.add(role);
        List<RoleInheritance> roleInheritances = roleInheritanceRepository.findByParentRole(role);
        for (RoleInheritance roleInheritance : roleInheritances) {
            inheritedRoles.addAll(getInheritedRoles(roleInheritance.getChildRole()));
        }
        return inheritedRoles;
    }
}
}
