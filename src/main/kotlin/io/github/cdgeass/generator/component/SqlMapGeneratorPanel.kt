package io.github.cdgeass.generator.component

import com.intellij.ui.TitledSeparator
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.table.TableView
import com.intellij.util.ui.FormBuilder
import org.apache.commons.lang3.tuple.MutablePair
import java.awt.BorderLayout
import javax.swing.JPanel

/**
 * @author cdgeass
 * @since 2020-11-06
 */
class SqlMapGeneratorPanel : JPanel(BorderLayout()) {

    private val properties = linkedMapOf(
            Pair("enableSubPackages", Boolean::class.java)
    )

    private val propertiesTableModel = PropertiesTableModel(properties)

    init {
        val propertiesTable = TableView(propertiesTableModel)
        val propertiesToolbarDecorator = ToolbarDecorator.createDecorator(propertiesTable)
                .setAddAction {
                    propertiesTableModel.addRow()
                }
                .setRemoveAction {
                    val selectedRows = propertiesTable.selectedRows
                    if (selectedRows.isNotEmpty()) {
                        propertiesTableModel.removeRow(selectedRows[selectedRows.size - 1])
                    }
                }
        this.add(
                FormBuilder.createFormBuilder()
                        .addComponent(TitledSeparator("Properties"))
                        .addComponent(propertiesToolbarDecorator.createPanel())
                        .addComponentFillVertically(JPanel(), 0)
                        .panel
        )
    }

    fun getProperties(): Map<String, String> {
        return propertiesTableModel.items.associateBy({ it.left }, { it.right })
    }

    fun setProperties(properties: Map<String, String>): SqlMapGeneratorPanel {
        if (properties.isNotEmpty()) {
            propertiesTableModel.addRows(properties.map { (property, value) -> MutablePair.of(property, value) })
        }
        return this
    }

}