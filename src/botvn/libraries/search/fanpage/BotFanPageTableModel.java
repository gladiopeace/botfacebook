package botvn.libraries.search.fanpage;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author vanvo
 */
public class BotFanPageTableModel extends DefaultTableModel{

    /**
     * 
     */
    private List<BotFanPageObject> pages;

    /**
     * 
     * @param pages 
     */
    public BotFanPageTableModel(List<BotFanPageObject> pages){
        this.pages = new ArrayList<>(pages);
    }
    
    @Override
    public int getRowCount() {
        return pages.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = "??";
        BotFanPageObject user = pages.get(rowIndex);
        switch (columnIndex) {
            case 0:
                value = user.Name;
                break;
            case 1:
                value = user.Likes;
                break;
        }

        return value;
    }
    
    public BotFanPageObject getFanPageAt(int index){
        return pages.get(index);
    }
    
}
