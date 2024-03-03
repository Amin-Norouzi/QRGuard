package dev.aminnorouzi.qrguard.util;

import dev.aminnorouzi.qrguard.repository.UserRepository;
import dev.aminnorouzi.qrguard.service.CameraService;
import dev.aminnorouzi.qrguard.service.QrCodeService;
import dev.aminnorouzi.qrguard.service.UserService;

import java.util.Map;
import java.util.function.Supplier;

public class DependencyInjector {

    private static final UserRepository repository = new UserRepository();

    private static final Map<Class<?>, Supplier<Object>> dependencies = Map.of(
            UserRepository.class, () -> repository,
//            ViewSwitcher.class, ViewSwitcher::new,
            UserService.class, UserService::new,
            CameraUtil.class, CameraUtil::new,
            CameraService.class, CameraService::new,
            QrCodeService.class, QrCodeService::new
    );

    public static <T> T getInstance(Class<T> clazz) {
        Supplier<Object> supplier = dependencies.get(clazz);
        if (supplier == null) {
            throw new IllegalArgumentException("Dependency not found for class: " + clazz);
        }
        return clazz.cast(supplier.get());
    }
}
