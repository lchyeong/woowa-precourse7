package store;

import store.config.AppConfig;

public class Application {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();

        appConfig.getDataInitializer().init();
        appConfig.getStoreController().start();
    }
}
