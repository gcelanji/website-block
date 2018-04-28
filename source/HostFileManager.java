package application;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class HostFileManager {

    public static void AddURL(String s) {
        String FileName = "C:/Windows/System32/drivers/etc/hosts";
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            File file = new File(FileName);
            if (!file.exists()) {
                file.createNewFile();
                fw = new FileWriter(file.getAbsoluteFile(), true);
                bw = new BufferedWriter(fw);
                String stableText = "# Copyright (c) 1993-2009 Microsoft Corp.\n"
                        + "#\n"
                        + "# This is a sample HOSTS file used by Microsoft TCP/IP for Windows.\n"
                        + "#\n"
                        + "# This file contains the mappings of IP addresses to host names. Each\n"
                        + "# entry should be kept on an individual line. The IP address should\n"
                        + "# be placed in the first column followed by the corresponding host name.\n"
                        + "# The IP address and the host name should be separated by at least one\n"
                        + "# space.\n"
                        + "#\n"
                        + "# Additionally, comments (such as these) may be inserted on individual\n"
                        + "# lines or following the machine name denoted by a '#' symbol.\n"
                        + "#\n"
                        + "# For example:\n"
                        + "#\n"
                        + "#      102.54.94.97     rhino.acme.com          # source server\n"
                        + "#       38.25.63.10     x.acme.com              # x client host\n"
                        + "\n"
                        + "# localhost name resolution is handled within DNS itself.\n"
                        + "#	127.0.0.1       localhost\n"
                        + "#	::1             localhost\n";
                bw.write(stableText);
                bw.write(s);
            } else {
                fw = new FileWriter(file.getAbsoluteFile(), true);
                bw = new BufferedWriter(fw);
                bw.write(s);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Access is denied. You need administrator previleges to run this application.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public static void deleteURL(String s) {
        ArrayList<String> items = new ArrayList<String>();
        String data = "";

        //File Connection Start
        File hosts = new File("C:/Windows/System32/drivers/etc/hosts");
        Scanner fileReader = null;

        try {
            fileReader = new Scanner(hosts);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        while (fileReader.hasNextLine()) {
            data = fileReader.nextLine();
            if (!data.equals(s)) {
                items.add(data);
            }

        }
        fileReader.close();
        //File Connection Close

        //File Connection Start
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileOutputStream("C:/Windows/System32/drivers/etc/hostsCopy"));
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error opening the file hostsCopy", "Error", JOptionPane.ERROR_MESSAGE);
        }
        for (int i = 0; i < items.size(); i++) {
            out.println(items.get(i));
        }
        out.close();
        //File Connection Ends

        hosts.delete();
        File hostsCopy = new File("C:/Windows/System32/drivers/etc/hostsCopy");
        hostsCopy.renameTo(hosts);
    }

}
