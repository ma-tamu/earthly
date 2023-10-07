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

    private ViewName() {
    }

}
