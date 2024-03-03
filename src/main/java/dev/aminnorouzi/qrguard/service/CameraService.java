package dev.aminnorouzi.qrguard.service;

import com.google.zxing.Result;
import dev.aminnorouzi.qrguard.util.CameraUtil;
import dev.aminnorouzi.qrguard.util.DependencyInjector;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CameraService {

    private final QrCodeService qrcode = DependencyInjector.getInstance(QrCodeService.class);
    private final CameraUtil util = DependencyInjector.getInstance(CameraUtil.class);

    private VideoCapture capture;
    private ScheduledExecutorService timer;

    static {
        OpenCV.loadLocally();
    }

    public CompletableFuture<String> open(ImageView view) {
        CompletableFuture<String> future = new CompletableFuture<>();

        capture = new VideoCapture();
        capture.open(0);

        if (!isOpened()) {
            future.completeExceptionally(new RuntimeException("Impossible to open the camera connection..."));
            return future;
        }

        Runnable grabber = () -> {
            Mat frame = grab();

            Image image = util.matToImage(frame);
            refresh(view, image);

            Result result = qrcode.detect(frame);
            if (result != null) {
                future.complete(result.getText());
                stop();
            }
        };

        timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(grabber, 0, 33, TimeUnit.MILLISECONDS);
        return future;
    }

    public void stop() {
        if (!timer.isShutdown()) {
            try {
                timer.shutdown();
                timer.awaitTermination(33, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
            }
        }

        if (isOpened()) {
            capture.release();
        }
    }

    public boolean isOpened() {
        return capture.isOpened();
    }

    public Mat grab() {
        Mat frame = new Mat();
        if (isOpened()) {
            try {
                capture.read(frame);
                Core.flip(frame, frame, 1);
            } catch (Exception e) {
                System.err.println("Exception during the image elaboration: " + e);
            }
        }

        return frame;
    }

    private void refresh(ImageView view, Image image) {
        util.onFXThread(view.imageProperty(), image);
    }
}
