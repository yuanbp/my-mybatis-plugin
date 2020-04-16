package io.github.cdgeass.action.io.github.cdgeass.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import io.github.cdgeass.dialog.FormatSelectDialogWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * @author cdgeass
 * @since  2020-04-15
 */
public class FormatSelectionAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        var editor = e.getData(PlatformDataKeys.EDITOR);
        if (editor == null) {
            return;
        }

        new FormatSelectDialogWrapper(editor.getSelectionModel()).show();
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        var editor = e.getData(PlatformDataKeys.EDITOR);
        if (editor == null) {
            return;
        }

        String selectedText = editor.getSelectionModel().getSelectedText();
        if (selectedText == null || selectedText.length() == 0) {
            e.getPresentation().setEnabledAndVisible(false);
        }
    }
}
