package personalfinance.gui.dialog;

import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import personalfinance.exception.ModelException;
import personalfinance.gui.MainButton;
import personalfinance.gui.MainFrame;
import personalfinance.model.Common;
import personalfinance.settings.HandlerCode;
import personalfinance.settings.Style;
import personalfinance.settings.Text;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

abstract public class AddEditDialog extends JDialog {
    protected LinkedHashMap<String, JComponent> components = new LinkedHashMap<>();
    protected LinkedHashMap<String, ImageIcon> icons = new LinkedHashMap<>();
    protected LinkedHashMap<String, Object> values = new LinkedHashMap<>();
    protected Common common;

    public AddEditDialog(MainFrame frame) {
        super(frame, Text.get("ADD"), true);
        setResizable(false);
    }

    public Common getCommon() {
        return common;
    }

    public void setCommon(Common common) {
        this.common = common;
    }

    public final void showDialog(){
        setDialog();
    }

    public final void closeDialog(){
        setVisible(false);
        this.common = null;
        components.clear();
        icons.clear();
        values.clear();
        dispose();
    }

    public boolean isAdd(){
        return common == null;
    }

    abstract protected void init();
    abstract protected void setValues();
    abstract protected Common getComandFromForm() throws ModelException;

    private void setDialog() {
        init();
        if (isAdd()){
            setTitle(Text.get("ADD"));
            setIconImage(Style.ICON_ADD.getImage());
        }else {
            setTitle(Text.get("EDIT"));
            setIconImage(Style.ICON_EDIT.getImage());
            setValues();
        }
        getContentPane().removeAll();
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        ((JPanel)getContentPane()).setBorder(Style.BORDER_DIALOG);
        for (Map.Entry<String, JComponent> entry : components.entrySet()) {
            String key = entry.getKey();
            JLabel label = new JLabel(Text.get(key));
            label.setIcon(icons.get(key));
            label.setFont(Style.FONT_DIALOG_LABEL);

            JComponent component = entry.getValue();
            if (component instanceof JTextField){
                component.setPreferredSize(Style.DIMENSION_DIALOG_TEXTFIELD_SIZE);
                if (values.containsValue(key)){
                    ((JTextField) component).setText("" + values.get(key));
                }
            }
            if (component instanceof JComboBox){
//                component.setPreferredSize(Style.DIMENSION_DIALOG_TEXTFIELD_SIZE);
                if (values.containsValue(key)){
                    ((JComboBox) component).setSelectedItem("" + values.get(key));
                }
            }
            if (component instanceof JDatePickerImpl){
//                component.setPreferredSize(Style.DIMENSION_DIALOG_TEXTFIELD_SIZE);
                if (values.containsValue(key)){
                    ((UtilDateModel)((JDatePickerImpl) component).getModel()).setValue((Date) values.get(key));
                }
            }
            component.setAlignmentX(JComponent.LEFT_ALIGNMENT);
            add(label);
            add(Box.createVerticalStrut(Style.PADDING_DIALOG));
            add(component);
            add(Box.createVerticalStrut(Style.PADDING_DIALOG));
        }

        MainButton ok = new MainButton(Text.get("ADD"), Style.ICON_OK, null, HandlerCode.ADD);
        if (!isAdd()){
            ok.setActionCommand(HandlerCode.EDIT);
            ok.setText(Text.get("EDIT"));
        }

        MainButton cancel = new MainButton(Text.get("CANCEL"), Style.ICON_CANCEL, null, HandlerCode.CANCEL);

        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new BorderLayout());
        panelButtons.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        panelButtons.add(ok, BorderLayout.WEST);
        panelButtons.add(Box.createRigidArea(Style.DIMENSION_DIALOG_PADDING_BUTTON), BorderLayout.CENTER);
        panelButtons.add(cancel, BorderLayout.EAST);

        add(panelButtons);
        pack();
        setLocationRelativeTo(null);
    }

    protected class CommonComboBox extends JComboBox{

        public CommonComboBox(Object[] obj) {
            super(obj);
            setRenderer(new DefaultListCellRenderer(){
                @Override
                public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    DefaultListCellRenderer renderer = (DefaultListCellRenderer) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    Common commonValue = (Common) value;
                    if (commonValue != null){
                        renderer.setText(commonValue.getValueForComboBox());
                    }
                    return renderer;
                }
            });
        }
    }
}
