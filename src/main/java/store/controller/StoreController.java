package store.controller;

import store.service.StoreService;

public class StoreController {
    public final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    public void start() {
        storeService.welcome();
    }
}
