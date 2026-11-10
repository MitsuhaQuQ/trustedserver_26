package com.mtmrails.trustedserver.fabric.client;

import java.io.File;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/** SDL3 版本不再携带 LWJGL TinyFD，使用 JDK 文件选择器完成本地备份导入导出。 */
final class ClientFileChooser {
    private ClientFileChooser() {
    }

    static Path chooseSave(String defaultName) {
        JFileChooser chooser = chooser();
        chooser.setSelectedFile(new File(defaultName));
        return chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION
                ? chooser.getSelectedFile().toPath() : null;
    }

    static Path chooseOpen() {
        JFileChooser chooser = chooser();
        return chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION
                ? chooser.getSelectedFile().toPath() : null;
    }

    private static JFileChooser chooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("TrustedServer key backup (*.tskb)", "tskb"));
        return chooser;
    }
}
