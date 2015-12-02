package util;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFXInitializer extends Application {

    private static Object barrier = new Object();

    @Override
    public void start(Stage primaryStage) throws Exception {
        synchronized(barrier) {
            barrier.notify();
        }
    }

    public static void initialize() throws InterruptedException {
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(JavaFXInitializer.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
        synchronized(barrier) {
            barrier.wait();
        }
    }
}
