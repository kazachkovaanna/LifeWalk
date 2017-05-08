package spblife.walk.settings;

import android.content.SharedPreferences;

/**
 * Класс настроек.
 * Хранит настройки приложения.
 */
public class Settings {
    private static Settings settings =new Settings();
    public static Integer DISTANCE = 1000;
    public static Boolean NOTIFICATION = true;
    public static Boolean NEARMEMODE = true;

    public Boolean nearMeMode=true;

    SharedPreferences preferences;
    protected Integer distance;
    protected Boolean notification;

    public static Settings getSettings() {
        return settings;
    }

    public static Settings getSettings(SharedPreferences preferences){
        setSettings(preferences);
        return settings;
    }

    public static void setSettings(SharedPreferences preferences){
        settings.preferences = preferences;

        settings.load();
        settings.save();
    }
    /**
     * Конструктор по-умолчанию.
     */
    private Settings() {
        distance = DISTANCE;
        notification = NOTIFICATION;
    }

    /**
     * Конструктор инициализации из файла настроек.
     * @param preferences объект-фаил настроек.
     */
    private Settings(SharedPreferences preferences) {
        this.preferences = preferences;

        load();
    }

    /**
     * Конструктор копирования.
     * @param settings объект для копирования.
     */
    public Settings(final Settings settings) {
        distance = settings.getDistance();
        notification = settings.getNotification();
    }

    /**
     * Получить максимальный радиус поиска объектов просмотра.
     * @return максимальный радиус поиска объектов.
     */
    public Integer getDistance() {
        return distance;
    }

    /**
     * Установить максимальный радиус поиска объектов просмотра.
     * @param distance максимальный радиус поиска объектов просмотра.
     */
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    /**
     * Получить состояние уведомлений.
     * <ul>
     *     <li>true - уведомления включены;</li>
     *     <li>false - уведомления отключены.</li>
     * </ul>
     * @return состояние уведомлений.
     */
    public Boolean getNotification() {
        return notification;
    }

    /**
     * Установить состояние уведомлений.
     * <ul>
     *     <li>true - уведомления включены;</li>
     *     <li>false - уведомления отключены.</li>
     * </ul>
     * @param notification состояние уведомлений.
     */
    public void setNotification(Boolean notification) {
        this.notification = notification;
    }

    /**
     * Загрузка настроек.
     */
    public void load() {
        distance = preferences.getInt("distance", DISTANCE);
        notification = preferences.getBoolean("notification", NOTIFICATION);
        nearMeMode = preferences.getBoolean("nearMeMode", NEARMEMODE);
    }

    public Boolean getNearMeMode() {
        return nearMeMode;
    }

    public void setNearMeMode(Boolean nearMeMode) {
        this.nearMeMode = nearMeMode;
    }

    /**
     * Сохранение настроек.
     */
    public void save() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("distance", distance);
        editor.putBoolean("notification", notification);
        editor.putBoolean("nearMeMode", nearMeMode);
        editor.apply();
    }

    public void setDefault() {
        preferences.edit().clear().apply();
        load();
    }
}
