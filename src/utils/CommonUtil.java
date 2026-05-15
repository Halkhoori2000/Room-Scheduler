/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class CommonUtil {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private static final DateFormat timestampFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

    public static void selectUpRowTable(JTable table, int currentRow) {
        int upRow = currentRow - 1;
        if (upRow < 0) {
            upRow = 0;
        }
        if (table.getRowCount() > 0) {
            table.getSelectionModel().setSelectionInterval(upRow, upRow);
        }
    }

    public static void setLocationCenter(Component widow) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        widow.setLocation(dim.width / 2 - widow.getSize().width / 2, dim.height / 2 - widow.getSize().height / 2);
    }

    public static void logLookAndFeelList() {
        UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo look : looks) {
            System.out.println(look.getClassName());
        }
    }

    public static void setLookAndFeel(LAK m) {
        if (lookAndFeels.containsKey(m)) {
            try {
                UIManager.setLookAndFeel(lookAndFeels.get(m));
            } catch (Exception ex) {
                Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static HashMap<Object, String> lookAndFeels = new HashMap<Object, String>();

    static {
        lookAndFeels.put(LAK.Metal, "javax.swing.plaf.metal.MetalLookAndFeel");
        lookAndFeels.put(LAK.Nimbus, "javax.swing.plaf.nimbus.NimbusLookAndFeel");
        lookAndFeels.put(LAK.Motif, "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        lookAndFeels.put(LAK.Windows, "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        lookAndFeels.put(LAK.WindowsClassic, "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
    }

    public static enum LAK {
        Metal,
        Nimbus,
        Motif,
        Windows,
        WindowsClassic
    }

    private static final KeyStroke escapeStroke
            = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

    public static final String dispatchWindowClosingActionMapKey
            = "com.spodding.tackline.dispatch:WINDOW_CLOSING";

    public static void installEscapeCloseOperation(final JDialog dialog) {
        Action dispatchClosing = new AbstractAction() {
            public void actionPerformed(ActionEvent event) {
                dialog.dispatchEvent(new WindowEvent(
                        dialog, WindowEvent.WINDOW_CLOSING
                ));
            }
        };
        JRootPane root = dialog.getRootPane();
        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                escapeStroke, dispatchWindowClosingActionMapKey
        );
        root.getActionMap().put(dispatchWindowClosingActionMapKey, dispatchClosing
        );
    }

    public static File getCurrentDir() throws IOException {
        File file = new File(".");
        String path = file.getAbsolutePath();
        File currentPath = new File(path.substring(0, path.length() - file.getCanonicalPath().length()));
        return currentPath;
    }

    public static void disableEditAndPasteTextField(JTextField txt) {
        if (txt != null) {
            txt.setTransferHandler(null);
            txt.setEditable(false);
        }
    }

    public static void setTextAndTooltip(JTextField txt, String text) {
        if (txt != null && text != null) {
            txt.setText(text);
            txt.setToolTipText(text);
        }
    }

    public static String formatTenDaiMoi(String fileName) {
        if (!fileName.endsWith(".gde")) {
            fileName = fileName + ".gde";
        }
        return fileName;
    }

    public static void installNumberSoTextField(JTextField txt) {
        if (txt != null) {
            txt.setTransferHandler(null);
            txt.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    char c = evt.getKeyChar();
                    if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE
                            || c == KeyEvent.VK_SPACE || c == KeyEvent.VK_DELETE)) {
                        evt.consume();
                    }
                }
            });

        }
    }

    public static void installNumberSoTextPanel(JTextPane txt) {
        if (txt != null) {
            txt.setTransferHandler(null);
            txt.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    char c = evt.getKeyChar();
                    if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE
                            || c == KeyEvent.VK_SPACE || c == KeyEvent.VK_DELETE)) {
                        evt.consume();
                    }
                }
            });

        }
    }

    public static String getDateStringFileSave() {
        SimpleDateFormat fm = new SimpleDateFormat("ddMMyyyy");
        return fm.format(new Date());
    }

    public static String generateFileName(String prefix, String suffix) {
        String fileName = getDateStringFileSave();
        if (prefix != null) {
            fileName = prefix + fileName;
        }
        if (suffix != null) {
            fileName += suffix;
        }
        return fileName;
    }

    public static ImageIcon createApplicationImage() {
        String path = "/images/gde64.png";
        return new ImageIcon(Toolkit.getDefaultToolkit().getClass().getResource(path));
    }

    public static void setErrorState(JTextField txt) {
        if (txt != null) {
            txt.setBackground(Color.red);
            txt.setForeground(Color.white);
        }
    }

    public static void setDefaultState(JTextField txt) {
        if (txt != null) {
            txt.setBackground(Color.white);
            txt.setForeground(Color.black);
        }
    }

    public static void installSelectAllWhenFocus(JTextField txt) {
        if (txt != null) {
            txt.addFocusListener(new FocusListener() {
                @Override
                public void focusLost(final FocusEvent pE) {
                }

                @Override
                public void focusGained(final FocusEvent pE) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            if (!isSelectAll(txt)) {
                                txt.selectAll();
                            }
                        }
                    });
                }
            });
        }
    }

    public static boolean isSelectAll(JTextField txt) {
        if (txt != null) {
            String text = txt.getText();
            String selectText = txt.getSelectedText();
            if (StringUtils.isNotEmpty(text) && StringUtils.isNotEmpty(selectText)) {
                return text.equals(selectText);
            }
        }
        return false;
    }

    public static void installSorterTable(JTable table) {
        table.setAutoCreateRowSorter(true);
    }

    public static List<Integer> asListInt(int[] arrays) {
        List<Integer> lst = new ArrayList<>();
        int size = arrays.length;
        for (int i = 0; i < size; i++) {
            lst.add(arrays[i]);
        }
        return lst;
    }

    public static int[] toArraysInt(List<Integer> lst) {
        int size = lst.size();
        int[] arrays = new int[size];
        for (int i = 0; i < size; i++) {
            arrays[i] = lst.get(i);
        }
        return arrays;
    }

    public static String generateKeyScreeningTime(int year, int month, int day, int time) {
        return year + "_" + month + "_" + day + "_" + time;
    }

    public static boolean compareCalendarDay(Calendar cal1, Calendar cal2) {
        int year1 = cal1.get(Calendar.YEAR);
        int month1 = cal1.get(Calendar.MONTH);
        int day1 = cal1.get(Calendar.DAY_OF_MONTH);

        int year2 = cal2.get(Calendar.YEAR);
        int month2 = cal2.get(Calendar.MONTH);
        int day2 = cal2.get(Calendar.DAY_OF_MONTH);

        return year1 == year2 && month1 == month2 && day1 == day2;
    }

    private static final SimpleDateFormat SDF = new SimpleDateFormat("HH:MM:ss.SSS");

    public static String getCurrentTime() {
        return SDF.format(Calendar.getInstance().getTime());
    }

    public static String logWithTime(String text) {
        return getCurrentTime() + " " + text;
    }

    public static void showItem(JComponent parent, JComponent child) {
        parent.removeAll();
        parent.add(child);
        child.setVisible(true);
        parent.validate();
        parent.repaint();
    }

    public static String getDateFormat(java.sql.Date date) {
        if (date == null) {
            return "invalid date";
        }
        return dateFormat.format(date);
    }

    public static String getTimeStampFormat(java.sql.Timestamp timestamp) {
        if (timestamp == null) {
            return "invalid date";
        }
        return timestampFormat.format(timestamp);
    }

    public static void hideFirstColumn(JTable table) {
        table.removeColumn(table.getColumnModel().getColumn(0));
    }

    public static int compareDateOnly(java.util.Date date1, java.util.Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        int year1 = cal1.get(Calendar.YEAR);
        int month1 = cal1.get(Calendar.MONTH);
        int day1 = cal1.get(Calendar.DAY_OF_MONTH);

        int year2 = cal2.get(Calendar.YEAR);
        int month2 = cal2.get(Calendar.MONTH);
        int day2 = cal2.get(Calendar.DAY_OF_MONTH);

        if (year1 != year2) {
            return year1 > year2 ? 1 : -1;
        } else if (month1 != month2) {
            return month1 > month2 ? 1 : -1;
        } else if (day1 != day2) {
            return day1 > day2 ? 1 : -1;
        }
        
        return 0;
    }
}
