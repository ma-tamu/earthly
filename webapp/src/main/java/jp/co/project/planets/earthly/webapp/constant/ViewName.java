package jp.co.project.planets.earthly.webapp.constant;

/**
 * view名
 */
public final class ViewName {

    public static final String ALERT_SUCCESS = "parts/alert::success";
    public static final String ALERT_DANGER = "parts/alert::danger";

    public static final String REDIRECT_USER_DETAIL = "redirect:/users/%s";

    public static final String FORGOT_PASSWORD = "forgets/passwords/index";

    public static final String REDIRECT_CLIENT_ENTRY = "redirect:/clients/entry";
    public static final String REDIRECT_CLIENT_DETAIL = "redirect:/clients/%s";
    public static final String CLIENT_DETAIL_REDIRECT_URL_PAGE = "clients/detail::redirectUrlPage";
    public static final String CLIENT_DETAIL_REDIRECT_ENTRY_CONTENT = "clients/assign::redirectUrlEntryContent";
    public static final String CLIENT_DETAIL_LOGOUT_REDIRECT_URL_PAGE = "clients/detail::logoutRedirectUrlPage";
    public static final String CLIENT_DETAIL_MANAGEMENT_USER_PAGE = "clients/detail::managementUserPage";

    private ViewName() {
    }

}
