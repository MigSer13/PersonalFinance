package personalfinance.gui.toolbar;

import personalfinance.gui.EnableEditDelete;
import personalfinance.gui.MainButton;
import personalfinance.settings.HandlerCode;
import personalfinance.settings.Style;
import personalfinance.settings.Text;

public class FunctionsToolbar extends AbstractToolbar implements EnableEditDelete {
    private MainButton editButton;
    private MainButton deleteButton;

    public FunctionsToolbar() {
        super(Style.BORDER_FUCTIONS_TOOLBAR);
        init();
    }

    @Override
    protected void init() {
        addButton(Text.get("ADD"), Style.ICON_ADD, HandlerCode.ADD, false);
        editButton = addButton(Text.get("EDIT"), Style.ICON_EDIT, HandlerCode.EDIT, false);
        deleteButton = addButton(Text.get("DELETE"), Style.ICON_DELETE, HandlerCode.DELETE, false);
    }

    @Override
    public void setEnableEditDelete(boolean enable) {
        editButton.setEnabled(enable);
        deleteButton.setEnabled(enable);
    }
}
