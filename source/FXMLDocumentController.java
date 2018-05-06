/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websiteblocker;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author Gerald
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TableView blockedSitesTable;

    @FXML
    private final TableColumn blockedSites = new TableColumn("Blocked Sites");

    @FXML
    private Label label;

    @FXML
    private TextField URLTextField;

    public void blockButtonAction(ActionEvent event) {
        int emptySpacesCounter = 0;
        String URL = URLTextField.getText();
        for (int i = 0; i < URL.length(); i++) {
            if (URL.charAt(i) == ' ') {
                emptySpacesCounter++;
            }
        }

        if (URL.length() == 0 || URL.charAt(0) == ' ' || emptySpacesCounter != 0) {
            JOptionPane.showMessageDialog(null, "Empty spaces are not allowed. Please enter URL again.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int choiceBlock = JOptionPane.showConfirmDialog(null, "Are you sure you want to block this webpage? If you block this you can't visit it anymore.", "Block", JOptionPane.YES_NO_OPTION);

            if (choiceBlock == 0) {
                String IP = "127.0.0.1 ";
                Website ws = new Website(URL);
                HostFileManager.AddURL(IP + ws.toString() + "\n");
                ArrayList<Website> list = new ArrayList<Website>();

                //File Connection Start
                File hosts = new File("C:/Windows/System32/drivers/etc/hosts");
                Scanner fileReader = null;
                try {
                    fileReader = new Scanner(hosts);
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                for (int i = 0; i < 21; i++) {
                    fileReader.nextLine();
                }

                while (fileReader.hasNextLine()) {
                    URL = fileReader.nextLine();
                    list.add(new Website(URL.substring(10)));
                }
                fileReader.close();
                //File Connection Close

                ObservableList<Website> data = FXCollections.observableArrayList(list);
                blockedSites.setCellValueFactory(new PropertyValueFactory<Website, String>("URL"));
                blockedSitesTable.setItems(data);
                URLTextField.setText(null);
            }

        }
    }

    public void deleButtonAction(ActionEvent event) {
        String selectedRow = "";
        selectedRow = blockedSitesTable.getSelectionModel().getSelectedItem().toString();
        int choiceDelete = -1;

        if (!selectedRow.equals("")) {
            choiceDelete = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected webpage?", "Delete", JOptionPane.YES_NO_OPTION);
        }

        if (choiceDelete == 0) {
            String IP = "127.0.0.1 ";

            HostFileManager.deleteURL(IP + selectedRow);

            String URL = "";
            ArrayList<Website> list = new ArrayList<Website>();

            //File Connection Start
            File hosts = new File("C:/Windows/System32/drivers/etc/hosts");
            Scanner fileReader = null;
            try {
                fileReader = new Scanner(hosts);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            for (int i = 0; i < 21; i++) {
                fileReader.nextLine();
            }

            while (fileReader.hasNextLine()) {
                URL = fileReader.nextLine();
                list.add(new Website(URL.substring(10)));
            }
            fileReader.close();
            //File Connection End

            ObservableList<Website> data = FXCollections.observableArrayList(list);
            blockedSites.setCellValueFactory(new PropertyValueFactory<Website, String>("URL"));
            blockedSitesTable.setItems(data);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        blockedSitesTable.getColumns().addAll(blockedSites);
        String URL = "";
        ArrayList<Website> list = new ArrayList<Website>();

        //File Connection Start
        File hosts = new File("C:/Windows/System32/drivers/etc/hosts");
        Scanner fileReader = null;
        try {
            fileReader = new Scanner(hosts);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        for (int i = 0; i < 21; i++) {
            fileReader.nextLine();
        }

        while (fileReader.hasNextLine()) {
            URL = fileReader.nextLine();
            list.add(new Website(URL.substring(10)));
        }
        fileReader.close();
        //File Connection End

        ObservableList<Website> data = FXCollections.observableArrayList(list);
        blockedSites.setCellValueFactory(new PropertyValueFactory<Website, String>("URL"));
        blockedSitesTable.setItems(data);

    }

}
