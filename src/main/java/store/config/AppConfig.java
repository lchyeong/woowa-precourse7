package store.config;

import store.controller.StoreController;
import store.file.ResourceFileReader;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.service.InputViewService;
import store.service.MembershipService;
import store.service.ProductService;
import store.service.StoreService;
import store.util.PurchaseProductParser;
import store.validator.PurchaseValidator;
import store.validator.YesNoValidator;
import store.view.OutputView;

public class AppConfig {
    private final ResourceFileReader resourceFileReader;
    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;
    private final DataInitializer dataInitializer;
    private final PurchaseValidator purchaseValidator;
    private final YesNoValidator yesNoValidator;
    private final ProductService productService;
    private final InputViewService inputViewService;
    private final PurchaseProductParser purchaseProductParser;
    private final StoreService storeService;
    private final StoreController storeController;
    private final OutputView outputView;
    private final MembershipService membershipService;

    public AppConfig() {
        this.membershipService = new MembershipService();
        this.resourceFileReader = new ResourceFileReader();
        this.promotionRepository = new PromotionRepository();
        this.productRepository = new ProductRepository();
        this.dataInitializer = new DataInitializer(productRepository, promotionRepository, resourceFileReader);
        this.purchaseValidator = new PurchaseValidator(productRepository);
        this.yesNoValidator = new YesNoValidator();
        this.productService = new ProductService(productRepository);
        this.outputView = new OutputView(productRepository, productService);
        this.inputViewService = new InputViewService(purchaseValidator, productRepository, yesNoValidator,
                productService);
        this.purchaseProductParser = new PurchaseProductParser();
        this.storeService = new StoreService(inputViewService, productRepository, purchaseValidator, productService,
                membershipService, purchaseProductParser, outputView);
        this.storeController = new StoreController(storeService);
    }

    public StoreController getStoreController() {
        return storeController;
    }

    public DataInitializer getDataInitializer() {
        return dataInitializer;
    }
}
