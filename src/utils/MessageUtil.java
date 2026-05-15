/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import javax.swing.JOptionPane;

public class MessageUtil {
    public static void showInfo(String mess){
        JOptionPane.showMessageDialog(null, mess);
    }
    public static int showConfirmOK(String mess,String title){
        Object[] options = {"OK"};
        int n = JOptionPane.showOptionDialog(null,
                   mess,title,
                   JOptionPane.PLAIN_MESSAGE,
                   JOptionPane.QUESTION_MESSAGE,
                   null,
                   options,
                   options[0]);
        return n;
    }
    
    public static int showError(String mess){
        Object[] options = {"OK"};
        int n = JOptionPane.showOptionDialog(null,
                   mess,"Error",
                   JOptionPane.PLAIN_MESSAGE,
                   JOptionPane.ERROR_MESSAGE,
                   null,
                   options,
                   options[0]);
        return n;
    }
    
    public static int showWarning(String mess){
        Object[] options = {"OK"};
        int n = JOptionPane.showOptionDialog(null,
                   mess,"Warning",
                   JOptionPane.PLAIN_MESSAGE,
                   JOptionPane.WARNING_MESSAGE,
                   null,
                   options,
                   options[0]);
        return n;
    }
}
