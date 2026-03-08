package vip.cdms.maimaihelper;

import android.content.SharedPreferences;

public class ServerConfig {
    private final SharedPreferences preferences;
    public ServerConfig(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    private static final String KEY_URL = "url";
    public String getUrl() {
        return preferences.getString(KEY_URL, "https://sumerumaimai.loophole.site/maimaihelper");
    }
    public void setUrl(String url) {
        preferences.edit().putString(KEY_URL, url).apply();
    }

    private static final String KEY_TOKEN = "token";
    public String getToken() {
        return preferences.getString(KEY_TOKEN, "DO_NOT_USE_DEFAULT_TOKEN");
    }
    public void setToken(String token) {
        preferences.edit().putString(KEY_TOKEN, token).apply();
    }

    private static final String KEY_ROUND_SCREEN = "round_screen";
    public boolean isRoundScreen() {
        return preferences.getBoolean(KEY_ROUND_SCREEN, false);
    }
    public void setRoundScreen(boolean roundScreen) {
        preferences.edit().putBoolean(KEY_ROUND_SCREEN, roundScreen).apply();
    }
}
