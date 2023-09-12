module com.fuzzy.l2launcherguard {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.github.kwhat.jnativehook;

    opens com.fuzzy.l2launcherguard to javafx.fxml;
    exports com.fuzzy.l2launcherguard;
}