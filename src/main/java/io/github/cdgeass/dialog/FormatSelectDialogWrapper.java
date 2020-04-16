package io.github.cdgeass.dialog;

import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.ui.DialogWrapper;
import io.github.cdgeass.formatter.WithParamFormatter;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Arrays;
import java.util.Map;

/**
 * @author cdgeass
 * @since  2020-04-15
 */
public class FormatSelectDialogWrapper extends DialogWrapper {

    private final SelectionModel selectionModel;

    private JPanel centerPane;

    public FormatSelectDialogWrapper(SelectionModel selectionModel) {
        super(true);
        this.selectionModel = selectionModel;

        init();
        setTitle("Format Sql");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        var selectedText = selectionModel.getSelectedText();
        if (selectedText == null) {
            return null;
        }

        if (WithParamFormatter.canFormatter(selectedText)) {
            Map<Integer, String> format = WithParamFormatter.format(selectedText);
            return new FormatSelectionWithParamDialog(format.get(0), format.get(1)).getContentPanel();
        }

        return null;
    }
}
