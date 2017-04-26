package spblife.walk.settings;

import android.content.SharedPreferences;

/**
 * Класс настроек.
 * Хранит настройки приложения.
 */
public class Settings {
    public static Integer DISTANCE = 100;
    public static Boolean NOTIFICATION = true;

    SharedPreferences preferences;
    protected Integer distance;
    protected Boolean notification;

    /**
     * Конструктор по-умолчанию.
     */
    public Settings() {
        distance = DISTANCE;
        notification = NOTIFICATION;
    }

    /**
     * Конструктор инициализации из файла настроек.
     * @param preferences объект-фаил настроек.
     */
    public Settings(SharedPreferences preferences) {
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
    }

    /**
     * Сохранение настроек.
     */
    public void save() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("distance", distance);
        editor.putBoolean("notification", notification);
        editor.apply();
    }

    public void setDefault() {
        preferences.edit().clear().apply();
        load();
    }
}
