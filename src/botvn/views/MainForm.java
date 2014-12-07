package botvn.views;

import botvn.botconfig.BotConfig;
import botvn.botconfig.BotConfigListener;
import botvn.botconfig.BotMessageObject;
import botvn.botconfig.BotMessageTemplate;
import botvn.libraries.MessageBox;
import botvn.botconfig.BotUrlFormatter;
import botvn.controllers.MainFormController;
import botvn.languages.LanguagesSupport;
import botvn.languages.LanguagesSupport.Languages;
import botvn.libraries.BotEventListener;
import botvn.libraries.LoggingListener;
import botvn.libraries.LoggingUtils;
import botvn.libraries.Tables;
import botvn.libraries.like.BotLikeListener;
import botvn.libraries.message.BotMessageAdsListener;
import botvn.libraries.search.BotSearchObject;
import botvn.libraries.search.BotSearchObjectType;
import botvn.libraries.search.group.BotGroupObject;
import botvn.libraries.search.group.IPostCommentListener;
import botvn.libraries.search.group.OnFetchMyGroupsListener;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author vanvo
 */
public class MainForm extends javax.swing.JFrame implements BotConfigListener {

    /**
     *
     */
    private MainFormController mMainFormController;

    /**
     *
     */
    private List<BotSearchObject> mResultsSearch;
    private List<BotGroupObject> mResultsGroups;
    private List<BotGroupObject> mMyGroups;

    /**
     *
     */
    private List<BotMessageObject> mMessages;

    /**
     *
     */
    private SplashScreen mSplashScreen;

    /**
     *
     */
    private LanguagesSupport mLanguagesSupport; // Default is Vietnamese

    /**
     *
     */
    private Languages mCurrentLanguage = LanguagesSupport.Languages.VIETNAMESE;

    /**
     *
     */
    private ResourceBundle mResouces;

    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();

        mSplashScreen = new SplashScreen(this, true);

        mMainFormController = new MainFormController(this);

        /// Template code, 
        jTableResultSearch.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                JTable table = (JTable) e.getSource();
                if (e.getClickCount() == 2) { // double click
                    int selectedIndex = table.getSelectedRow();
                    if (selectedIndex > -1) {
                        BotSearchObject fanpage = mResultsSearch.get(selectedIndex);
                        Desktop d = Desktop.getDesktop();
                        try {
                            d.browse(new URI(BotUrlFormatter.getShortProfileUrl(fanpage.ID)));
                        } catch (IOException | URISyntaxException ex) {
                            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    int selectedRow = table.getSelectedRow();
                    LoggingUtils.print("selected: " + selectedRow);
                    int selectedColumn = table.getSelectedColumn();
                    if (selectedRow > -1 && selectedColumn == Tables.SEARCH_RESULTS.SELECTION) {
                        boolean selected = (boolean) table.getValueAt(selectedRow, Tables.SEARCH_RESULTS.SELECTION);
                        mResultsSearch.get(selectedRow).IsSelected = !selected;
                    }
                    updateSelection();
                }
            }
        });

        jTableMyGroups.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                JTable table = (JTable) e.getSource();
                int selectedRow = table.getSelectedRow();
                LoggingUtils.print("selected: " + selectedRow);
                int selectedColumn = table.getSelectedColumn();
                if (selectedRow > -1 && selectedColumn == Tables.MY_GROUPS.SELECTION) {
                    boolean selected = (boolean) table.getValueAt(selectedRow, Tables.MY_GROUPS.SELECTION);
                    mMyGroups.get(selectedRow).IsSelected = !selected;
                }
            }

        });

        mSplashScreen.setVisible(true);
        mResouces = LanguagesSupport.setLanguage(mCurrentLanguage);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        MainTab = new javax.swing.JTabbedPane();
        TabLogin = new javax.swing.JPanel();
        jPanelLogin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jEmail = new javax.swing.JTextField();
        jPassword = new javax.swing.JPasswordField();
        jDangNhapButton = new javax.swing.JButton();
        TabTimKiem = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jKeywordTimKiem = new javax.swing.JTextField();
        jButtonTimKiem = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jSearchByUser = new javax.swing.JCheckBox();
        jSearchByFanPage = new javax.swing.JCheckBox();
        jSearchByEvents = new javax.swing.JCheckBox();
        jSearchByGroup = new javax.swing.JCheckBox();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTableResultSearch = new javax.swing.JTable();
        jTimKiemStatus = new javax.swing.JLabel();
        jSelectAll = new javax.swing.JButton();
        jUnSelectAll = new javax.swing.JButton();
        jInvertSelection = new javax.swing.JButton();
        jOpenFBPage = new javax.swing.JButton();
        jLabelCountSelected = new javax.swing.JLabel();
        TabAutoLike = new javax.swing.JPanel();
        jCheckBox3 = new javax.swing.JCheckBox();
        jButtonLikeFanPage = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTableLike = new javax.swing.JTable();
        jLabelLikeStatus = new javax.swing.JLabel();
        jLabelLikeCount = new javax.swing.JLabel();
        TabAutoMessage = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTableMessageTemplate = new javax.swing.JTable();
        jDelMessage = new javax.swing.JButton();
        jAddMessage = new javax.swing.JButton();
        jUpdateMessage = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTableReceiver = new javax.swing.JTable();
        jButtonAutoMessage = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabelMessageStatus = new javax.swing.JLabel();
        jLabelSendMsgCount = new javax.swing.JLabel();
        TabChamSocTaiKhoan = new javax.swing.JTabbedPane();
        TabThamGiaNhom = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSearchGroup = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jMembersLimit = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableGroupSearch = new javax.swing.JTable();
        jSearchGroupStatus = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jSearchGroupLog = new javax.swing.JTextArea();
        TabTuongTacNhom = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableMyGroups = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jCommentTemplate = new javax.swing.JTextArea();
        jButton5 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jLogTuongTacNhom = new javax.swing.JTextArea();
        jLikeCommentStatus = new javax.swing.JLabel();
        TabContact = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLanguageVietnamese = new javax.swing.JRadioButton();
        jLanguageEnglish = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("botvn/languages/language_vi_VN"); // NOI18N
        setTitle(bundle.getString("MainForm.title")); // NOI18N
        setBackground(new java.awt.Color(153, 153, 153));
        setName("Form"); // NOI18N
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        MainTab.setName("MainTab"); // NOI18N

        TabLogin.setName("TabLogin"); // NOI18N

        jPanelLogin.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("MainForm.jPanelLogin.border.title"))); // NOI18N
        jPanelLogin.setName(""); // NOI18N

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText(bundle.getString("MainForm.jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText(bundle.getString("MainForm.jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jEmail.setName("jEmail"); // NOI18N

        jPassword.setName("jPassword"); // NOI18N

        jDangNhapButton.setLabel(bundle.getString("MainForm.jDangNhapButton.label")); // NOI18N
        jDangNhapButton.setName("jDangNhapButton"); // NOI18N
        jDangNhapButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDangNhapButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelLoginLayout = new org.jdesktop.layout.GroupLayout(jPanelLogin);
        jPanelLogin.setLayout(jPanelLoginLayout);
        jPanelLoginLayout.setHorizontalGroup(
            jPanelLoginLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelLoginLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanelLoginLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelLoginLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPassword, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                    .add(jEmail)))
            .add(jDangNhapButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
        );
        jPanelLoginLayout.setVerticalGroup(
            jPanelLoginLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelLoginLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelLoginLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jEmail, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelLoginLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(jPassword, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jDangNhapButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout TabLoginLayout = new org.jdesktop.layout.GroupLayout(TabLogin);
        TabLogin.setLayout(TabLoginLayout);
        TabLoginLayout.setHorizontalGroup(
            TabLoginLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, TabLoginLayout.createSequentialGroup()
                .addContainerGap(574, Short.MAX_VALUE)
                .add(jPanelLogin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(317, 317, 317))
        );
        TabLoginLayout.setVerticalGroup(
            TabLoginLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(TabLoginLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelLogin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(344, Short.MAX_VALUE))
        );

        MainTab.addTab(bundle.getString("MainForm.TabLogin.TabConstraints.tabTitle"), TabLogin); // NOI18N

        TabTimKiem.setName("TabTimKiem"); // NOI18N

        jLabel8.setText(bundle.getString("MainForm.jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jKeywordTimKiem.setName("jKeywordTimKiem"); // NOI18N

        jButtonTimKiem.setText(bundle.getString("MainForm.jButtonTimKiem.text")); // NOI18N
        jButtonTimKiem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonTimKiem.setName("jButtonTimKiem"); // NOI18N
        jButtonTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTimKiemActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("MainForm.jPanel1.border.title"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jSearchByUser.setSelected(true);
        jSearchByUser.setText(bundle.getString("MainForm.jSearchByUser.text")); // NOI18N
        jSearchByUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jSearchByUser.setName("jSearchByUser"); // NOI18N

        jSearchByFanPage.setText(bundle.getString("MainForm.jSearchByFanPage.text")); // NOI18N
        jSearchByFanPage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jSearchByFanPage.setName("jSearchByFanPage"); // NOI18N

        jSearchByEvents.setText(bundle.getString("MainForm.jSearchByEvents.text")); // NOI18N
        jSearchByEvents.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jSearchByEvents.setName("jSearchByEvents"); // NOI18N

        jSearchByGroup.setText(bundle.getString("MainForm.jSearchByGroup.text")); // NOI18N
        jSearchByGroup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jSearchByGroup.setName("jSearchByGroup"); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jSearchByUser)
                    .add(jSearchByFanPage)
                    .add(jSearchByEvents)
                    .add(jSearchByGroup))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jSearchByUser)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSearchByFanPage)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSearchByEvents)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSearchByGroup)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane7.setName("jScrollPane7"); // NOI18N

        jTableResultSearch.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Chọn", "Tên", "Kiểu"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Boolean.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableResultSearch.setColumnSelectionAllowed(true);
        jTableResultSearch.setName("jTableResultSearch"); // NOI18N
        jScrollPane7.setViewportView(jTableResultSearch);
        jTableResultSearch.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (jTableResultSearch.getColumnModel().getColumnCount() > 0) {
            jTableResultSearch.getColumnModel().getColumn(0).setResizable(false);
            jTableResultSearch.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("MainForm.jTableResultSearch.columnModel.title0_1")); // NOI18N
            jTableResultSearch.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("MainForm.jTableResultSearch.columnModel.title1_1")); // NOI18N
            jTableResultSearch.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("MainForm.jTableResultSearch.columnModel.title2_1")); // NOI18N
            jTableResultSearch.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("MainForm.jTableResultSearch.columnModel.title3")); // NOI18N
        }

        jTimKiemStatus.setName("jTimKiemStatus"); // NOI18N

        jSelectAll.setText(bundle.getString("MainForm.jSelectAll.text")); // NOI18N
        jSelectAll.setEnabled(false);
        jSelectAll.setName("jSelectAll"); // NOI18N
        jSelectAll.setPreferredSize(new java.awt.Dimension(141, 29));
        jSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSelectAllActionPerformed(evt);
            }
        });

        jUnSelectAll.setText(bundle.getString("MainForm.jUnSelectAll.text")); // NOI18N
        jUnSelectAll.setEnabled(false);
        jUnSelectAll.setName("jUnSelectAll"); // NOI18N
        jUnSelectAll.setPreferredSize(new java.awt.Dimension(141, 29));
        jUnSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUnSelectAllActionPerformed(evt);
            }
        });

        jInvertSelection.setText(bundle.getString("MainForm.jInvertSelection.text")); // NOI18N
        jInvertSelection.setEnabled(false);
        jInvertSelection.setName("jInvertSelection"); // NOI18N
        jInvertSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInvertSelectionActionPerformed(evt);
            }
        });

        jOpenFBPage.setText(bundle.getString("MainForm.jOpenFBPage.text")); // NOI18N
        jOpenFBPage.setEnabled(false);
        jOpenFBPage.setName("jOpenFBPage"); // NOI18N
        jOpenFBPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jOpenFBPageActionPerformed(evt);
            }
        });

        jLabelCountSelected.setText(bundle.getString("MainForm.jLabelCountSelected.text")); // NOI18N
        jLabelCountSelected.setName("jLabelCountSelected"); // NOI18N

        org.jdesktop.layout.GroupLayout TabTimKiemLayout = new org.jdesktop.layout.GroupLayout(TabTimKiem);
        TabTimKiem.setLayout(TabTimKiemLayout);
        TabTimKiemLayout.setHorizontalGroup(
            TabTimKiemLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, TabTimKiemLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jLabel8)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jKeywordTimKiem, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 360, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButtonTimKiem, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 112, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jTimKiemStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 292, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(31, 31, 31))
            .add(TabTimKiemLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(TabTimKiemLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(TabTimKiemLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                        .add(TabTimKiemLayout.createSequentialGroup()
                            .add(jSelectAll, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                            .add(jUnSelectAll, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                            .add(jInvertSelection)
                            .add(51, 51, 51)
                            .add(jOpenFBPage))
                        .add(jScrollPane7))
                    .add(jLabelCountSelected, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 329, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(242, Short.MAX_VALUE))
        );
        TabTimKiemLayout.setVerticalGroup(
            TabTimKiemLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(TabTimKiemLayout.createSequentialGroup()
                .addContainerGap()
                .add(TabTimKiemLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel8)
                    .add(jKeywordTimKiem, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButtonTimKiem)
                    .add(jTimKiemStatus))
                .add(30, 30, 30)
                .add(TabTimKiemLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(TabTimKiemLayout.createSequentialGroup()
                        .add(TabTimKiemLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jSelectAll, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jUnSelectAll, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jInvertSelection)
                            .add(jOpenFBPage))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 291, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabelCountSelected)
                .addContainerGap(91, Short.MAX_VALUE))
        );

        MainTab.addTab(bundle.getString("MainForm.TabTimKiem.TabConstraints.tabTitle"), TabTimKiem); // NOI18N

        TabAutoLike.setName("TabAutoLike"); // NOI18N

        jCheckBox3.setSelected(true);
        jCheckBox3.setText(bundle.getString("MainForm.jCheckBox3.text")); // NOI18N
        jCheckBox3.setEnabled(false);
        jCheckBox3.setName("jCheckBox3"); // NOI18N

        jButtonLikeFanPage.setText(bundle.getString("MainForm.jButtonLikeFanPage.text")); // NOI18N
        jButtonLikeFanPage.setName("jButtonLikeFanPage"); // NOI18N
        jButtonLikeFanPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLikeFanPageActionPerformed(evt);
            }
        });

        jScrollPane8.setName("jScrollPane8"); // NOI18N

        jTableLike.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên FanPage", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableLike.setName("jTableLike"); // NOI18N
        jScrollPane8.setViewportView(jTableLike);
        if (jTableLike.getColumnModel().getColumnCount() > 0) {
            jTableLike.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("MainForm.jTableLike.columnModel.title0")); // NOI18N
            jTableLike.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("MainForm.jTableLike.columnModel.title1")); // NOI18N
            jTableLike.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("MainForm.jTableLike.columnModel.title2")); // NOI18N
        }

        jLabelLikeStatus.setName("jLabelLikeStatus"); // NOI18N

        jLabelLikeCount.setText(bundle.getString("MainForm.jLabelLikeCount.text")); // NOI18N
        jLabelLikeCount.setName("jLabelLikeCount"); // NOI18N

        org.jdesktop.layout.GroupLayout TabAutoLikeLayout = new org.jdesktop.layout.GroupLayout(TabAutoLike);
        TabAutoLike.setLayout(TabAutoLikeLayout);
        TabAutoLikeLayout.setHorizontalGroup(
            TabAutoLikeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(TabAutoLikeLayout.createSequentialGroup()
                .addContainerGap()
                .add(TabAutoLikeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(TabAutoLikeLayout.createSequentialGroup()
                        .add(jCheckBox3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButtonLikeFanPage)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(jLabelLikeStatus, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jLabelLikeCount, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 619, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(260, 260, 260))
        );
        TabAutoLikeLayout.setVerticalGroup(
            TabAutoLikeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(TabAutoLikeLayout.createSequentialGroup()
                .add(8, 8, 8)
                .add(TabAutoLikeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(TabAutoLikeLayout.createSequentialGroup()
                        .add(TabAutoLikeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jButtonLikeFanPage)
                            .add(jCheckBox3))
                        .add(18, 18, 18)
                        .add(jLabelLikeStatus)
                        .add(59, 59, 59)
                        .add(jLabelLikeCount)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(jScrollPane8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE))
                .addContainerGap())
        );

        MainTab.addTab(bundle.getString("MainForm.TabAutoLike.TabConstraints.tabTitle"), TabAutoLike); // NOI18N

        TabAutoMessage.setName("TabAutoMessage"); // NOI18N

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("MainForm.jPanel10.border.title"))); // NOI18N
        jPanel10.setName("jPanel10"); // NOI18N

        jScrollPane9.setName("jScrollPane9"); // NOI18N

        jTableMessageTemplate.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tin nhắn sẽ gởi", "Nội dung"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableMessageTemplate.setName("jTableMessageTemplate"); // NOI18N
        jScrollPane9.setViewportView(jTableMessageTemplate);
        if (jTableMessageTemplate.getColumnModel().getColumnCount() > 0) {
            jTableMessageTemplate.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("MainForm.jTableMessageTemplate.columnModel.title0")); // NOI18N
            jTableMessageTemplate.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("MainForm.jTableMessageTemplate.columnModel.title1")); // NOI18N
            jTableMessageTemplate.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("MainForm.jTableMessageTemplate.columnModel.title2")); // NOI18N
        }

        jDelMessage.setText(bundle.getString("MainForm.jDelMessage.text")); // NOI18N
        jDelMessage.setName("jDelMessage"); // NOI18N
        jDelMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDelMessageActionPerformed(evt);
            }
        });

        jAddMessage.setText(bundle.getString("MainForm.jAddMessage.text")); // NOI18N
        jAddMessage.setName("jAddMessage"); // NOI18N
        jAddMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAddMessageActionPerformed(evt);
            }
        });

        jUpdateMessage.setText(bundle.getString("MainForm.jUpdateMessage.text")); // NOI18N
        jUpdateMessage.setName("jUpdateMessage"); // NOI18N
        jUpdateMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUpdateMessageActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel10Layout = new org.jdesktop.layout.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel10Layout.createSequentialGroup()
                        .add(jDelMessage)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jUpdateMessage)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jAddMessage))
                    .add(jScrollPane9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel10Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jScrollPane9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 190, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jDelMessage)
                    .add(jAddMessage)
                    .add(jUpdateMessage)))
        );

        jScrollPane10.setName("jScrollPane10"); // NOI18N

        jTableReceiver.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên", "Trạng thái", "Thời gian"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableReceiver.setName("jTableReceiver"); // NOI18N
        jScrollPane10.setViewportView(jTableReceiver);
        if (jTableReceiver.getColumnModel().getColumnCount() > 0) {
            jTableReceiver.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("MainForm.jTableReceiver.columnModel.title0")); // NOI18N
            jTableReceiver.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("MainForm.jTableReceiver.columnModel.title1")); // NOI18N
            jTableReceiver.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("MainForm.jTableReceiver.columnModel.title2")); // NOI18N
            jTableReceiver.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("MainForm.jTableReceiver.columnModel.title3")); // NOI18N
        }

        jButtonAutoMessage.setText(bundle.getString("MainForm.jButtonAutoMessage.text")); // NOI18N
        jButtonAutoMessage.setName("jButtonAutoMessage"); // NOI18N
        jButtonAutoMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAutoMessageActionPerformed(evt);
            }
        });

        jLabel9.setText(bundle.getString("MainForm.jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jLabelMessageStatus.setName("jLabelMessageStatus"); // NOI18N

        jLabelSendMsgCount.setText(bundle.getString("MainForm.jLabelSendMsgCount.text")); // NOI18N
        jLabelSendMsgCount.setName("jLabelSendMsgCount"); // NOI18N

        org.jdesktop.layout.GroupLayout TabAutoMessageLayout = new org.jdesktop.layout.GroupLayout(TabAutoMessage);
        TabAutoMessage.setLayout(TabAutoMessageLayout);
        TabAutoMessageLayout.setHorizontalGroup(
            TabAutoMessageLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(TabAutoMessageLayout.createSequentialGroup()
                .addContainerGap()
                .add(TabAutoMessageLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel10, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jButtonAutoMessage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 198, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelMessageStatus, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(TabAutoMessageLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jLabel9)
                    .add(jScrollPane10, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                    .add(jLabelSendMsgCount, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(251, Short.MAX_VALUE))
        );
        TabAutoMessageLayout.setVerticalGroup(
            TabAutoMessageLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, TabAutoMessageLayout.createSequentialGroup()
                .addContainerGap(71, Short.MAX_VALUE)
                .add(TabAutoMessageLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(TabAutoMessageLayout.createSequentialGroup()
                        .add(jLabel9)
                        .add(11, 11, 11)
                        .add(jScrollPane10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 336, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(TabAutoMessageLayout.createSequentialGroup()
                        .add(jPanel10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jButtonAutoMessage)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabelMessageStatus)
                        .add(61, 61, 61)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabelSendMsgCount)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        MainTab.addTab(bundle.getString("MainForm.TabAutoMessage.TabConstraints.tabTitle"), TabAutoMessage); // NOI18N

        TabChamSocTaiKhoan.setName("TabChamSocTaiKhoan"); // NOI18N

        TabThamGiaNhom.setName("TabThamGiaNhom"); // NOI18N

        jLabel3.setText(bundle.getString("MainForm.jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jSearchGroup.setText(bundle.getString("MainForm.jSearchGroup.text")); // NOI18N
        jSearchGroup.setName("jSearchGroup"); // NOI18N

        jLabel4.setText(bundle.getString("MainForm.jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jMembersLimit.setName("jMembersLimit"); // NOI18N
        jMembersLimit.setValue(2000);

        jButton1.setText(bundle.getString("MainForm.jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTableGroupSearch.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Chọn", "Tên nhóm", "Số thành viên"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableGroupSearch.setName("jTableGroupSearch"); // NOI18N
        jScrollPane1.setViewportView(jTableGroupSearch);
        if (jTableGroupSearch.getColumnModel().getColumnCount() > 0) {
            jTableGroupSearch.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("MainForm.jTableGroupSearch.columnModel.title0")); // NOI18N
            jTableGroupSearch.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("MainForm.jTableGroupSearch.columnModel.title3_1")); // NOI18N
            jTableGroupSearch.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("MainForm.jTableGroupSearch.columnModel.title1")); // NOI18N
            jTableGroupSearch.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("MainForm.jTableGroupSearch.columnModel.title2")); // NOI18N
        }

        jSearchGroupStatus.setText(bundle.getString("MainForm.jSearchGroupStatus.text")); // NOI18N
        jSearchGroupStatus.setName("jSearchGroupStatus"); // NOI18N

        jButton2.setText(bundle.getString("MainForm.jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jSearchGroupLog.setColumns(20);
        jSearchGroupLog.setRows(5);
        jSearchGroupLog.setName("jSearchGroupLog"); // NOI18N
        jScrollPane2.setViewportView(jSearchGroupLog);

        org.jdesktop.layout.GroupLayout TabThamGiaNhomLayout = new org.jdesktop.layout.GroupLayout(TabThamGiaNhom);
        TabThamGiaNhom.setLayout(TabThamGiaNhomLayout);
        TabThamGiaNhomLayout.setHorizontalGroup(
            TabThamGiaNhomLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(TabThamGiaNhomLayout.createSequentialGroup()
                .addContainerGap()
                .add(TabThamGiaNhomLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(TabThamGiaNhomLayout.createSequentialGroup()
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jMembersLimit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(133, 133, 133)
                        .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 483, Short.MAX_VALUE)
                        .add(jButton2))
                    .add(TabThamGiaNhomLayout.createSequentialGroup()
                        .add(TabThamGiaNhomLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(TabThamGiaNhomLayout.createSequentialGroup()
                                .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 81, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jSearchGroup, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 412, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jScrollPane1)
                            .add(jSearchGroupStatus, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane2)))
                .addContainerGap())
        );
        TabThamGiaNhomLayout.setVerticalGroup(
            TabThamGiaNhomLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(TabThamGiaNhomLayout.createSequentialGroup()
                .add(9, 9, 9)
                .add(TabThamGiaNhomLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jSearchGroup, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(TabThamGiaNhomLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(jMembersLimit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton1)
                    .add(jButton2))
                .add(18, 18, 18)
                .add(jSearchGroupStatus)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(TabThamGiaNhomLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jScrollPane2)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
                .add(35, 35, 35))
        );

        TabChamSocTaiKhoan.addTab(bundle.getString("MainForm.TabThamGiaNhom.TabConstraints.tabTitle"), TabThamGiaNhom); // NOI18N

        TabTuongTacNhom.setName("TabTuongTacNhom"); // NOI18N

        jButton3.setText(bundle.getString("MainForm.jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTableMyGroups.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Chọn", "Tên nhóm"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableMyGroups.setName("jTableMyGroups"); // NOI18N
        jScrollPane3.setViewportView(jTableMyGroups);
        if (jTableMyGroups.getColumnModel().getColumnCount() > 0) {
            jTableMyGroups.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("MainForm.jTableMyGroups.columnModel.title0")); // NOI18N
            jTableMyGroups.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("MainForm.jTableMyGroups.columnModel.title1")); // NOI18N
            jTableMyGroups.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("MainForm.jTableMyGroups.columnModel.title2")); // NOI18N
        }

        jButton4.setText(bundle.getString("MainForm.jButton4.text")); // NOI18N
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("MainForm.jPanel4.border.title"))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        jCommentTemplate.setColumns(20);
        jCommentTemplate.setRows(5);
        jCommentTemplate.setName("jCommentTemplate"); // NOI18N
        jScrollPane5.setViewportView(jCommentTemplate);

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane5)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton5.setText(bundle.getString("MainForm.jButtonCommentLike.text")); // NOI18N
        jButton5.setName("jButtonCommentLike"); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        jLogTuongTacNhom.setColumns(20);
        jLogTuongTacNhom.setRows(5);
        jLogTuongTacNhom.setName("jLogTuongTacNhom"); // NOI18N
        jScrollPane4.setViewportView(jLogTuongTacNhom);

        jLikeCommentStatus.setText(bundle.getString("MainForm.jLikeCommentStatus.text")); // NOI18N
        jLikeCommentStatus.setName("jLikeCommentStatus"); // NOI18N

        org.jdesktop.layout.GroupLayout TabTuongTacNhomLayout = new org.jdesktop.layout.GroupLayout(TabTuongTacNhom);
        TabTuongTacNhom.setLayout(TabTuongTacNhomLayout);
        TabTuongTacNhomLayout.setHorizontalGroup(
            TabTuongTacNhomLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(TabTuongTacNhomLayout.createSequentialGroup()
                .add(TabTuongTacNhomLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jButton4)
                    .add(TabTuongTacNhomLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(TabTuongTacNhomLayout.createSequentialGroup()
                            .addContainerGap()
                            .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .add(jButton3)))
                .add(18, 18, 18)
                .add(TabTuongTacNhomLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                    .add(TabTuongTacNhomLayout.createSequentialGroup()
                        .add(jButton5)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jLikeCommentStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 265, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        TabTuongTacNhomLayout.setVerticalGroup(
            TabTuongTacNhomLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(TabTuongTacNhomLayout.createSequentialGroup()
                .addContainerGap()
                .add(TabTuongTacNhomLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, TabTuongTacNhomLayout.createSequentialGroup()
                        .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(TabTuongTacNhomLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jButton5)
                            .add(jLikeCommentStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane4))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, TabTuongTacNhomLayout.createSequentialGroup()
                        .add(jButton3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 303, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton4)
                        .add(0, 34, Short.MAX_VALUE)))
                .addContainerGap())
        );

        TabChamSocTaiKhoan.addTab(bundle.getString("MainForm.TabTuongTacNhom.TabConstraints.tabTitle"), TabTuongTacNhom); // NOI18N

        MainTab.addTab(bundle.getString("MainForm.TabChamSocTaiKhoan.TabConstraints.tabTitle"), TabChamSocTaiKhoan); // NOI18N

        TabContact.setBackground(new java.awt.Color(255, 255, 255));
        TabContact.setName("TabContact"); // NOI18N

        jLabel11.setText(bundle.getString("MainForm.jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(java.awt.Color.blue);
        jLabel12.setText(bundle.getString("MainForm.jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/avatar_logo.png"))); // NOI18N
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel14.setText(bundle.getString("MainForm.jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N
        //jLabel12.setText(LbPhone);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(java.awt.Color.blue);
        jLabel15.setText(bundle.getString("MainForm.jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jLabel16.setText(bundle.getString("MainForm.jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(java.awt.Color.blue);
        jLabel17.setText(bundle.getString("MainForm.jLabel17.text")); // NOI18N
        jLabel17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });

        jLabel18.setText(bundle.getString("MainForm.jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(java.awt.Color.blue);
        jLabel19.setText(bundle.getString("MainForm.jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setForeground(java.awt.Color.blue);
        jLabel20.setText(bundle.getString("MainForm.jLabel20.text")); // NOI18N
        jLabel20.setName("jLabel20"); // NOI18N

        org.jdesktop.layout.GroupLayout TabContactLayout = new org.jdesktop.layout.GroupLayout(TabContact);
        TabContact.setLayout(TabContactLayout);
        TabContactLayout.setHorizontalGroup(
            TabContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(TabContactLayout.createSequentialGroup()
                .add(263, 263, 263)
                .add(TabContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, TabContactLayout.createSequentialGroup()
                        .add(TabContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jLabel16, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jLabel18, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(TabContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel17, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(TabContactLayout.createSequentialGroup()
                                .add(jLabel19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 212, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(40, 40, 40)
                                .add(jLabel20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 350, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, TabContactLayout.createSequentialGroup()
                        .add(TabContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(18, 18, 18)
                        .add(TabContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jLabel15, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jLabel12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 412, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(433, Short.MAX_VALUE))
        );
        TabContactLayout.setVerticalGroup(
            TabContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(TabContactLayout.createSequentialGroup()
                .add(16, 16, 16)
                .add(jLabel13)
                .add(18, 18, 18)
                .add(TabContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel14)
                    .add(jLabel15))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(TabContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel11)
                    .add(jLabel12))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(TabContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel16)
                    .add(jLabel17))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(TabContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(TabContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel18)
                        .add(jLabel19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(221, Short.MAX_VALUE))
        );

        MainTab.addTab(bundle.getString("MainForm.TabContact.TabConstraints.tabTitle"), TabContact); // NOI18N

        jPanel2.setBackground(new java.awt.Color(0, 194, 185));
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(291, 35));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText(bundle.getString("MainForm.jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jLanguageVietnamese.setBackground(new java.awt.Color(0, 194, 185));
        buttonGroup2.add(jLanguageVietnamese);
        jLanguageVietnamese.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLanguageVietnamese.setForeground(new java.awt.Color(255, 255, 255));
        jLanguageVietnamese.setSelected(true);
        jLanguageVietnamese.setText(bundle.getString("MainForm.jLanguageVietnamese.text")); // NOI18N
        jLanguageVietnamese.setName("jLanguageVietnamese"); // NOI18N
        jLanguageVietnamese.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLanguageVietnameseActionPerformed(evt);
            }
        });

        jLanguageEnglish.setBackground(new java.awt.Color(0, 194, 185));
        buttonGroup2.add(jLanguageEnglish);
        jLanguageEnglish.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLanguageEnglish.setForeground(new java.awt.Color(255, 255, 255));
        jLanguageEnglish.setText(bundle.getString("MainForm.jLanguageEnglish.text")); // NOI18N
        jLanguageEnglish.setName("jLanguageEnglish"); // NOI18N
        jLanguageEnglish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLanguageEnglishActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel10)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 663, Short.MAX_VALUE)
                .add(jLanguageEnglish)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLanguageVietnamese))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLanguageVietnamese)
                    .add(jLanguageEnglish)
                    .add(jLabel10))
                .add(0, 6, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 964, Short.MAX_VALUE)
            .add(MainTab, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(MainTab, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 511, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        MainTab.getAccessibleContext().setAccessibleName(bundle.getString("MainForm.MainTab.AccessibleContext.accessibleName")); // NOI18N
        MainTab.getAccessibleContext().setAccessibleDescription(bundle.getString("MainForm.MainTab.AccessibleContext.accessibleDescription")); // NOI18N

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jDangNhapButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDangNhapButtonActionPerformed
        if (!mMainFormController.IsLogged) {
            //jLogging.append(LoggingUtils.print("Tien hanh dang nhap..."));
            String email = jEmail.getText();
            String passwd = String.valueOf(jPassword.getPassword());
            if (email.length() == 0) {

                MessageBox.Show(this, "Bạn chưa nhập email", "BOTVN", MessageBox.Buttons.OK);
                return;
            }

            if (passwd.isEmpty()) {
                MessageBox.Show(this, "Bạn chưa nhập mật khẩu", "BOTVN", MessageBox.Buttons.OK);
                return;
            }

            try {
                boolean success = mMainFormController.Login(email, passwd);
                if (success) {
                    LoggingUtils.print("Dang nhap thanh cong.");
                    jDangNhapButton.setText("Đăng xuất");
                    jEmail.setEnabled(false);
                    jPassword.setEnabled(false);
                    jPassword.setText("");
                    MessageBox.Show(this, "Đăng nhập thành công", "BOTVN", MessageBox.Buttons.OK);
                } else {
                    MessageBox.Show(this, "Đăng nhập thất bại. Email hoặc mật khẩu không đúng", "BOTVN", MessageBox.Buttons.OK);
                    //jLogging.append(LoggingUtils.print("Dang nhap that bai. Email hoac mat khau khong dung."));
                }
            } catch (IOException ex) {
                //jLogging.append(LoggingUtils.print("Loi trong qua trinh dang nhap: " + ex.getMessage()));
                MessageBox.Show(this, "Lỗi trong quá trình đăng nhập", "BOTVN", MessageBox.Buttons.OK);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //jLogging.append(LoggingUtils.print("LOGOUT"));
            mMainFormController.Logout();
            jDangNhapButton.setText("Đăng nhập");
            jEmail.setEnabled(true);
            jPassword.setEnabled(true);
        }
    }//GEN-LAST:event_jDangNhapButtonActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        jEmail.setDragEnabled(true);
    }//GEN-LAST:event_formComponentShown

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    private void jButtonTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTimKiemActionPerformed
        String keyword = jKeywordTimKiem.getText();
        if (keyword.length() == 0) {
            MessageBox.Show(this, "Vui lòng nhập từ khóa!", "BOTVN", MessageBox.Buttons.OK);
            return;
        }

        int searchByUser = jSearchByUser.isSelected() ? BotSearchObjectType.USER.getValue() : 0;
        int searchByPage = jSearchByFanPage.isSelected() ? BotSearchObjectType.PAGE.getValue() : 0;
        int searchByEvent = jSearchByEvents.isSelected() ? BotSearchObjectType.EVENT.getValue() : 0;
        int searchByGroup = jSearchByGroup.isSelected() ? BotSearchObjectType.GROUP.getValue() : 0;
        int type = searchByUser | searchByPage | searchByEvent | searchByGroup;
        if (type == 0) {
            MessageBox.Show(this, "Tùy chọn tìm kiếm chưa được chọn", "BOTVN", MessageBox.Buttons.OK);
        } else {
            jTimKiemStatus.setText("Đang tiến hành tìm kiếm...");
            clearSearchResults(jTableResultSearch);
            mMainFormController.Search(new SearchAll(), keyword, type);
        }
    }//GEN-LAST:event_jButtonTimKiemActionPerformed

    private void jSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSelectAllActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTableResultSearch.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(true, i, Tables.SEARCH_RESULTS.SELECTION);
            mResultsSearch.get(i).IsSelected = true;
        }
        updateSelection();
    }//GEN-LAST:event_jSelectAllActionPerformed

    private void jUnSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUnSelectAllActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTableResultSearch.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(false, i, Tables.SEARCH_RESULTS.SELECTION);
            mResultsSearch.get(i).IsSelected = false;
        }
        updateSelection();
    }//GEN-LAST:event_jUnSelectAllActionPerformed

    private void jInvertSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInvertSelectionActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTableResultSearch.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            boolean selection = (boolean) model.getValueAt(i, Tables.SEARCH_RESULTS.SELECTION);
            model.setValueAt(!selection, i, Tables.SEARCH_RESULTS.SELECTION);
            mResultsSearch.get(i).IsSelected = !selection;
        }
        updateSelection();
    }//GEN-LAST:event_jInvertSelectionActionPerformed

    private void jOpenFBPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jOpenFBPageActionPerformed
        int selectedIndex = jTableResultSearch.getSelectedRow();
        if (selectedIndex > -1) {
            BotSearchObject fanpage = mResultsSearch.get(selectedIndex);
            Desktop d = Desktop.getDesktop();
            try {
                d.browse(new URI(BotUrlFormatter.getShortProfileUrl(fanpage.ID)));
            } catch (IOException | URISyntaxException ex) {
                Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            MessageBox.Show(this, "Chưa có trang nào được chọn", "BOTVN", MessageBox.Buttons.OK);
        }
    }//GEN-LAST:event_jOpenFBPageActionPerformed

    private void jAddMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAddMessageActionPerformed
        FormAddMessage addMessage = new FormAddMessage(this, true, mCurrentLanguage);
        addMessage.setVisible(true);
        BotMessageObject result = addMessage.getValue();
        if (result != null && result.toString().length() > 0) {
            // Display new message into table
            DefaultTableModel model = (DefaultTableModel) jTableMessageTemplate.getModel();
            int latestRow = model.getRowCount();
            model.addRow(new Object[]{++latestRow, true, result});
            resizeColumnWidth(jTableMessageTemplate);
            if (!jDelMessage.isEnabled()) {
                jDelMessage.setEnabled(true);
            }
            mMessages = mMainFormController.MessagesToList();
        }
    }//GEN-LAST:event_jAddMessageActionPerformed

    private void jDelMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDelMessageActionPerformed
        int selectedIndex = jTableMessageTemplate.getSelectedRow();
        if (selectedIndex != -1) {
            DefaultTableModel model = (DefaultTableModel) jTableMessageTemplate.getModel();
            BotMessageObject r = (BotMessageObject) model.getValueAt(selectedIndex, Tables.MESSAGE_TEMP.CONTENT);
            BotMessageTemplate.RemoveMessage(r.getKey());
            model.removeRow(selectedIndex);
            if (model.getRowCount() == 0) {
                jDelMessage.setEnabled(false);
            }
            // update number order
            for (int i = 1; i <= model.getRowCount(); i++) {
                model.setValueAt(i, i - 1, Tables.MESSAGE_TEMP.NO);
            }
        } else {
            MessageBox.Show(this, "Chưa chọn tin nhắn để xóa", "BOTVN", MessageBox.Buttons.OK);
        }
    }//GEN-LAST:event_jDelMessageActionPerformed

    private void jUpdateMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUpdateMessageActionPerformed
        int selectedIndex = jTableMessageTemplate.getSelectedRow();
        if (selectedIndex != -1) {
            DefaultTableModel model = (DefaultTableModel) jTableMessageTemplate.getModel();
            BotMessageObject r = (BotMessageObject) model.getValueAt(selectedIndex, Tables.MESSAGE_TEMP.CONTENT);
            FormAddMessage addMessage = new FormAddMessage(this, true, mCurrentLanguage, r);
            addMessage.setVisible(true);
            BotMessageObject result = addMessage.getValue();
            if (result != null && result.toString().length() > 0) {
                // Display new message into table
                model.setValueAt(result, selectedIndex, Tables.MESSAGE_TEMP.CONTENT);
                resizeColumnWidth(jTableMessageTemplate);
            }
        } else {
            MessageBox.Show(this, "Chưa chọn tin nhắn để sửa", "BOTVN", MessageBox.Buttons.OK);
        }
    }//GEN-LAST:event_jUpdateMessageActionPerformed

    private void jButtonAutoMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAutoMessageActionPerformed
        if (mResultsSearch == null || mResultsSearch.isEmpty()) {
            MessageBox.Show(this, "Bạn phải tìm kiếm trước khi thực hiện chức năng này", "BOTVN", MessageBox.Buttons.OK);
            return;
        }

        if (mMessages != null && mMessages.size() < 5) {
            MessageBox.Show(this, "Bạn phải thêm ít nhất 5 tin nhắn để gởi", "BOTVN", MessageBox.Buttons.OK);
        }

        /*
         try {
         mMainFormController.SendMessage("test", "538048492978010");
         } catch (IOException ex) {
         Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
         }
         */
        clearSearchResults(jTableReceiver);
        try {
            jLabelMessageStatus.setText("Đang tiến hành gởi tin nhắn...");
            mMainFormController.SendMessageToMultiUser(mMessages, mResultsSearch, new SendMessageListener());
        } catch (InterruptedException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonAutoMessageActionPerformed

    private void jButtonLikeFanPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLikeFanPageActionPerformed
        if (mResultsSearch == null || mResultsSearch.isEmpty()) {
            MessageBox.Show(this, "Bạn phải tìm kiếm trước khi thực hiện chức năng này", "BOTVN", MessageBox.Buttons.OK);
            return;
        }

        if (!mMainFormController.CheckIsResutlsSelected(mResultsSearch)) {
            MessageBox.Show(this, "Chưa chọn kết quả tìm kiếm nào để tiến hành", "BOTVN", MessageBox.Buttons.OK);
            return;
        }

        jLabelLikeStatus.setText("Đang xử lý các fanpage...");
        clearSearchResults(jTableLike);
        mMainFormController.AutoLike(mResultsSearch, new LikeListener());
    }//GEN-LAST:event_jButtonLikeFanPageActionPerformed

    private void jLanguageVietnameseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLanguageVietnameseActionPerformed
        mCurrentLanguage = Languages.VIETNAMESE;
        ResourceBundle vietnamese = LanguagesSupport.setLanguage(LanguagesSupport.Languages.VIETNAMESE);
        UpdateLanguage(vietnamese);
    }//GEN-LAST:event_jLanguageVietnameseActionPerformed

    private void jLanguageEnglishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLanguageEnglishActionPerformed
        mCurrentLanguage = Languages.ENGLISH;
        ResourceBundle english = LanguagesSupport.setLanguage(LanguagesSupport.Languages.ENGLISH);
        UpdateLanguage(english);
    }//GEN-LAST:event_jLanguageEnglishActionPerformed

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked

        //OpenBrowser.openWebpage("http://" + jLabel17.getText());
    }//GEN-LAST:event_jLabel17MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String keyword = jSearchGroup.getText();
        if (keyword.length() == 0) {
            MessageBox.Show(this, "Vui lòng nhập từ khóa!", "BOTVN", MessageBox.Buttons.OK);
            return;
        }

        clearSearchResults(jTableGroupSearch);
        int limit = Integer.parseInt(jMembersLimit.getValue().toString());
        jSearchGroupStatus.setText("Đang tìm kiếm nhóm...");
        mMainFormController.SearchGroup(new SearchGroup(), keyword, limit);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        mMainFormController.JoinGroup(new SearchGroupLog());
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        clearSearchResults(jTableMyGroups);
        mMainFormController.FetchMyGroup(new FetchMyGroupListener());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String comments = jCommentTemplate.getText();
        if (comments.length() == 0) {
            MessageBox.Show(this, "Bạn chưa nhập nội dung bình luận", "BOTVN", MessageBox.Buttons.OK);
            return;
        }
        if(mMyGroups.isEmpty()){
            MessageBox.Show(this, "Bạn chưa nằm trong nhóm nào", "BOTVN", MessageBox.Buttons.OK);
            return;
        }
        jLikeCommentStatus.setText("Đang tiến hành...");
        mMainFormController.groupLikePost(mMyGroups, comments, new OnPostComment());
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("MacOSX".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane MainTab;
    private javax.swing.JPanel TabAutoLike;
    private javax.swing.JPanel TabAutoMessage;
    private javax.swing.JTabbedPane TabChamSocTaiKhoan;
    private javax.swing.JPanel TabContact;
    private javax.swing.JPanel TabLogin;
    private javax.swing.JPanel TabThamGiaNhom;
    private javax.swing.JPanel TabTimKiem;
    private javax.swing.JPanel TabTuongTacNhom;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jAddMessage;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonAutoMessage;
    private javax.swing.JButton jButtonLikeFanPage;
    private javax.swing.JButton jButtonTimKiem;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JTextArea jCommentTemplate;
    private javax.swing.JButton jDangNhapButton;
    private javax.swing.JButton jDelMessage;
    private javax.swing.JTextField jEmail;
    private javax.swing.JButton jInvertSelection;
    private javax.swing.JTextField jKeywordTimKiem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelCountSelected;
    private javax.swing.JLabel jLabelLikeCount;
    private javax.swing.JLabel jLabelLikeStatus;
    private javax.swing.JLabel jLabelMessageStatus;
    private javax.swing.JLabel jLabelSendMsgCount;
    private javax.swing.JRadioButton jLanguageEnglish;
    private javax.swing.JRadioButton jLanguageVietnamese;
    private javax.swing.JLabel jLikeCommentStatus;
    private javax.swing.JTextArea jLogTuongTacNhom;
    private javax.swing.JSpinner jMembersLimit;
    private javax.swing.JButton jOpenFBPage;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelLogin;
    private javax.swing.JPasswordField jPassword;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JCheckBox jSearchByEvents;
    private javax.swing.JCheckBox jSearchByFanPage;
    private javax.swing.JCheckBox jSearchByGroup;
    private javax.swing.JCheckBox jSearchByUser;
    private javax.swing.JTextField jSearchGroup;
    private javax.swing.JTextArea jSearchGroupLog;
    private javax.swing.JLabel jSearchGroupStatus;
    private javax.swing.JButton jSelectAll;
    private javax.swing.JTable jTableGroupSearch;
    private javax.swing.JTable jTableLike;
    private javax.swing.JTable jTableMessageTemplate;
    private javax.swing.JTable jTableMyGroups;
    private javax.swing.JTable jTableReceiver;
    private javax.swing.JTable jTableResultSearch;
    private javax.swing.JLabel jTimKiemStatus;
    private javax.swing.JButton jUnSelectAll;
    private javax.swing.JButton jUpdateMessage;
    // End of variables declaration//GEN-END:variables

    /**
     * Clear all results
     */
    private void clearSearchResults() {
        DefaultTableModel model = (DefaultTableModel) jTableResultSearch.getModel();
        model.setRowCount(0);
    }

    private void clearSearchResults(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }

    /**
     *
     * @param table
     */
    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 50; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width, width);
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    @Override
    public void OnInitSuccess() {
        if (mSplashScreen != null) {
            mSplashScreen.Dispose();
        }
        if (LoggingUtils.DEBUG) {
            jEmail.setText(BotConfig.Account.Email);
            jPassword.setText(BotConfig.Account.Pwd);
        }

        mMessages = mMainFormController.MessagesToList();
        if (mMessages != null && mMessages.size() > 0) {
            jDelMessage.setEnabled(true);
            DefaultTableModel model = (DefaultTableModel) jTableMessageTemplate.getModel();
            int count = 1;
            for (BotMessageObject message : mMessages) {
                model.addRow(new Object[]{count++, true, message});
            }
            resizeColumnWidth(jTableMessageTemplate);
        } else {
            jDelMessage.setEnabled(false);
        }
        resizeColumnWidth(jTableMessageTemplate);
    }

    @Override
    public void OnInitError() {
        //MessageBox.Show(this, "Error when init the system, try open again!", "BOTVN", MessageBox.Buttons.OK);
        jTimKiemStatus.setText("");
    }

    /**
     *
     */
    private class SearchGroup implements BotEventListener<BotGroupObject> {

        private int count = 1;

        @Override
        public void OnReceiving(BotGroupObject object) {
            DefaultTableModel model = (DefaultTableModel) jTableGroupSearch.getModel();
            model.addRow(new Object[]{count++, object.IsSelected, object, object.Members});
        }

        @Override
        public void OnReceveFinished(List<BotGroupObject> results) {
            mResultsGroups = results;
            jSearchGroupStatus.setText(String.format("Hoàn thành, có %d nhóm", mResultsGroups.size()));
            resizeColumnWidth(jTableGroupSearch);
        }
    }

    private class SearchGroupLog implements LoggingListener {

        @Override
        public void OnLog(String log) {
            jSearchGroupLog.append(log);
        }

    }

    /**
     * Received result from search
     */
    private class SearchAll implements BotEventListener<BotSearchObject> {

        private int count = 1;

        @Override
        public void OnReceiving(BotSearchObject object) {
            DefaultTableModel model = (DefaultTableModel) jTableResultSearch.getModel();
            model.addRow(new Object[]{count++, object.IsSelected, object, object.Type});
        }

        @Override
        public void OnReceveFinished(List<BotSearchObject> results) {
            mResultsSearch = results;
            jTimKiemStatus.setText(String.format("Hoàn thành, có %d kết quả", mResultsSearch.size()));

            //
            if (results.size() > 0) {
                jSelectAll.setEnabled(true);
                jUnSelectAll.setEnabled(true);
                jInvertSelection.setEnabled(true);
                jOpenFBPage.setEnabled(true);
            } else {
                jSelectAll.setEnabled(false);
                jUnSelectAll.setEnabled(false);
                jInvertSelection.setEnabled(false);
                jOpenFBPage.setEnabled(false);
            }

            // Select all as default
            DefaultTableModel model = (DefaultTableModel) jTableResultSearch.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                model.setValueAt(true, i, Tables.SEARCH_RESULTS.SELECTION);
                mResultsSearch.get(i).IsSelected = true;
            }
            resizeColumnWidth(jTableResultSearch);
            updateSelection();
        }

    }

    /**
     * Send message listener
     */
    private class SendMessageListener implements BotMessageAdsListener {

        private int count = 0;

        @Override
        public void OnMessageProcessing(String receiver) {
            DefaultTableModel model = (DefaultTableModel) jTableReceiver.getModel();
            model.addRow(new Object[]{++count, receiver, "Đang xử lý..."});

            jLabelSendMsgCount.setText(String.format("Đã gởi tin nhắn %s/%s", count, mMainFormController.countSelected(mResultsSearch)));
        }

        @Override
        public void OnMessageSend(boolean isSuccess, String received) {
            DefaultTableModel model = (DefaultTableModel) jTableReceiver.getModel();
            int latestRow = model.getRowCount() - 1;
            int h = Calendar.getInstance(Locale.getDefault()).get(Calendar.HOUR_OF_DAY);
            int m = Calendar.getInstance(Locale.getDefault()).get(Calendar.MINUTE);
            int s = Calendar.getInstance(Locale.getDefault()).get(Calendar.SECOND);
            String dt = String.format("%d:%d:%d", h, m, s);
            model.setValueAt(dt, latestRow, Tables.MESSAGE_SENT.TIME);
            if (isSuccess) {
                model.setValueAt("Đã gởi tin nhắn", latestRow, Tables.MESSAGE_SENT.STATUS);
            } else {
                model.setValueAt("Không thể gởi tin nhắn", latestRow, Tables.MESSAGE_SENT.STATUS);
            }
            resizeColumnWidth(jTableReceiver);
        }

        @Override
        public void OnMessageSendFinished() {
            jLabelMessageStatus.setText("Các tin nhắn đã được gởi xong");
        }
    }

    /**
     * Like listener
     */
    private class LikeListener implements BotLikeListener {

        private int count = 0;

        @Override
        public void OnLikeProcessing(String name) {
            DefaultTableModel model = (DefaultTableModel) jTableLike.getModel();
            model.addRow(new Object[]{++count, name, "Đang xử lý..."});

            jLabelLikeCount.setText(String.format("Đã like %s/%s", count, mMainFormController.countSelected(mResultsSearch)));
        }

        @Override
        public void OnLiked(boolean success) {
            DefaultTableModel model = (DefaultTableModel) jTableLike.getModel();
            int latestRow = model.getRowCount() - 1;
            if (success) {
                model.setValueAt("Đã Like", latestRow, Tables.AUTOLIKE.STATUS);
            } else {
                model.setValueAt("Không thể Like", latestRow, Tables.AUTOLIKE.STATUS);
            }
            resizeColumnWidth(jTableLike);
        }

        @Override
        public void OnLikeFinished() {
            jLabelLikeStatus.setText("Đã like xong");
        }
    }

    /**
     *
     */
    private class FetchMyGroupListener implements OnFetchMyGroupsListener {

        private int count = 0;

        @Override
        public void OnFetchGroup(BotGroupObject obj) {
            LoggingUtils.print("href:" + obj.URL + ",name: " + obj.Name);
            DefaultTableModel model = (DefaultTableModel) jTableMyGroups.getModel();
            model.addRow(new Object[]{++count, obj.IsSelected, obj});
        }

        @Override
        public void OnFetchGroupError() {
            LoggingUtils.print("ERROR----------");
        }

        @Override
        public void OnFetchFinished(List<BotGroupObject> list) {
            resizeColumnWidth(jTableMyGroups);
            if(mMyGroups == null){
                mMyGroups = new ArrayList<>();
            }
            mMyGroups.addAll(list);
        }

    }

    private class OnPostComment implements IPostCommentListener {

        @Override
        public void OnComment(String post_url) {
            jLogTuongTacNhom.append("\n- " + post_url);
        }

        @Override
        public void OnFinished() {
            jLikeCommentStatus.setText("Hoàn thành");
        }
    }

    /**
     *
     * @param bundle
     */
    private void UpdateLanguage(ResourceBundle bundle) {
        jTableResultSearch.getColumnModel().getColumn(Tables.SEARCH_RESULTS.NO).setHeaderValue(bundle.getString("MainForm.columnModel.title_no")); // NOI18N
        jTableResultSearch.getColumnModel().getColumn(Tables.SEARCH_RESULTS.SELECTION).setHeaderValue(bundle.getString("MainForm.jTableResultSearch.columnModel.title0_1")); // NOI18N
        jTableResultSearch.getColumnModel().getColumn(Tables.SEARCH_RESULTS.NAME).setHeaderValue(bundle.getString("MainForm.jTableResultSearch.columnModel.title1_1")); // NOI18N
        jTableResultSearch.getColumnModel().getColumn(Tables.SEARCH_RESULTS.TYPE).setHeaderValue(bundle.getString("MainForm.jTableResultSearch.columnModel.title2_1")); // NOI18N
        MainTab.setTabComponentAt(2, new JLabel(bundle.getString("MainForm.TabAutoLike.TabConstraints.tabTitle")));
        jLabel2.setText(bundle.getString("MainForm.jLabel2.text"));
        jOpenFBPage.setText(bundle.getString("MainForm.jOpenFBPage.text"));
        jLabel1.setText(bundle.getString("MainForm.jLabel1.text"));
        jInvertSelection.setText(bundle.getString("MainForm.jInvertSelection.text"));
        jPanelLogin.setBorder(BorderFactory.createTitledBorder(bundle.getString("MainForm.jPanelLogin.border.title")));
        jUnSelectAll.setText(bundle.getString("MainForm.jUnSelectAll.text"));
        MainTab.setTabComponentAt(0, new JLabel(bundle.getString("MainForm.TabLogin.TabConstraints.tabTitle")));
        jSelectAll.setText(bundle.getString("MainForm.jSelectAll.text"));
        jLabel9.setText(bundle.getString("MainForm.jLabel9.text"));
        jButtonAutoMessage.setText(bundle.getString("MainForm.jButtonAutoMessage.text"));
        jSearchByGroup.setText(bundle.getString("MainForm.jSearchByGroup.text"));
        jSearchByEvents.setText(bundle.getString("MainForm.jSearchByEvents.text"));
        jUpdateMessage.setText(bundle.getString("MainForm.jUpdateMessage.text"));
        jAddMessage.setText(bundle.getString("MainForm.jAddMessage.text"));
        jSearchByFanPage.setText(bundle.getString("MainForm.jSearchByFanPage.text"));
        jSearchByUser.setText(bundle.getString("MainForm.jSearchByUser.text"));
        jDelMessage.setText(bundle.getString("MainForm.jDelMessage.text"));
        jPanel1.setBorder(BorderFactory.createTitledBorder(bundle.getString("MainForm.jPanel1.border.title")));
        jButtonTimKiem.setText(bundle.getString("MainForm.jButtonTimKiem.text"));
        jPanel10.setBorder(BorderFactory.createTitledBorder(bundle.getString("MainForm.jPanel10.border.title")));
        jLabel8.setText(bundle.getString("MainForm.jLabel8.text"));
        MainTab.setTabComponentAt(3, new JLabel(bundle.getString("MainForm.TabAutoMessage.TabConstraints.tabTitle")));
        MainTab.setTabComponentAt(1, new JLabel(bundle.getString("MainForm.TabTimKiem.TabConstraints.tabTitle")));
        jDangNhapButton.setText(bundle.getString("MainForm.jDangNhapButton.label"));
        jButtonLikeFanPage.setText(bundle.getString("MainForm.jButtonLikeFanPage.text"));
        jCheckBox3.setText(bundle.getString("MainForm.jCheckBox3.text"));
        jLanguageVietnamese.setText(bundle.getString("MainForm.jLanguageVietnamese.text"));

        jTableMessageTemplate.getColumnModel().getColumn(Tables.MESSAGE_TEMP.NO).setHeaderValue(bundle.getString("MainForm.columnModel.title_no"));
        jTableMessageTemplate.getColumnModel().getColumn(Tables.MESSAGE_TEMP.SELECTION).setHeaderValue(bundle.getString("MainForm.jTableMessageTemplate.columnModel.title_1"));
        jTableMessageTemplate.getColumnModel().getColumn(Tables.MESSAGE_TEMP.CONTENT).setHeaderValue(bundle.getString("MainForm.jTableMessageTemplate.columnModel.title_2"));

        jTableReceiver.getColumnModel().getColumn(Tables.MESSAGE_SENT.NO).setHeaderValue(bundle.getString("MainForm.columnModel.title_no"));
        jTableReceiver.getColumnModel().getColumn(Tables.MESSAGE_SENT.NAME).setHeaderValue(bundle.getString("MainForm.jTableReceiver.columnModel.title_1"));
        jTableReceiver.getColumnModel().getColumn(Tables.MESSAGE_SENT.STATUS).setHeaderValue(bundle.getString("MainForm.jTableReceiver.columnModel.title_2"));

        jTableLike.getColumnModel().getColumn(Tables.AUTOLIKE.NO).setHeaderValue(bundle.getString("MainForm.columnModel.title_no"));
        jTableLike.getColumnModel().getColumn(Tables.AUTOLIKE.NAME).setHeaderValue(bundle.getString("MainForm.jTableLike.columnModel.title_1"));
        jTableLike.getColumnModel().getColumn(Tables.AUTOLIKE.STATUS).setHeaderValue(bundle.getString("MainForm.jTableLike.columnModel.title_2"));
    }

    /**
     * Count all selected
     */
    private void updateSelection() {
        String countMsg = String.format("Đã chọn: %d", mMainFormController.countSelected(mResultsSearch));
        jLabelCountSelected.setText(countMsg);
    }
}
