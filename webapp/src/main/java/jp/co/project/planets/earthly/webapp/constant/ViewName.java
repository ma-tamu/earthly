package jp.co.project.planets.earthly.webapp.constant;

/**
 * view名
 */
public final class ViewName {

    public static final String TOAST_SUCCESS = "parts/toast::success";
    public static final String TOAST_DANGER = "parts/toast::danger";

    public static final String REDIRECT_USER_DETAIL = "redirect:/users/%s";

    public static final String FORGOT_PASSWORD = "forgets/passwords/index";

    public static final String REDIRECT_COMPANY_ENTRY = "redirect:/companies/entries";
    public static final String REDIRECT_COMPANY_LIST = "redirect:/companies";

    public static final String REDIRECT_CLIENT_ENTRY = "redirect:/clients/entries";
    public static final String REDIRECT_CLIENT_DETAIL = "redirect:/clients/%s";
    public static final String CLIENT_DETAIL_REDIRECT_URL_PAGE = "clients/detail::redirectUriPage";
    public static final String CLIENT_DETAIL_REDIRECT_ENTRY_CONTENT = "clients/assign::redirectUrlEntryContent";
    public static final String CLIENT_DETAIL_LOGOUT_REDIRECT_URL_PAGE = "clients/detail::logoutRedirectUriPage";
    public static final String CLIENT_DETAIL_MANAGEMENT_USER_PAGE = "clients/detail::managementUserPage";
    public static final String CLIENT_DETAIL_UNASSIGNED_MANAGEMENT_USER_PAGE = "clients/modal::unassignedManagementUserPage";

    private ViewName() {
    }

}
