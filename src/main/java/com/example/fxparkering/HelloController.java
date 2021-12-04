package com.example.fxparkering;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class Bil{
    public String bilNummer;
    private Date startTid;
    private boolean korttid; //true er korttid

    public Bil(String bilNummer, Date startTid, boolean korttid){
        this.bilNummer = bilNummer;
        this.startTid = startTid;
        this.korttid = korttid;
    }

    public int getTimer(){
       //int timer =(int) (new Date().getTime() - startTid.getTime()) / 3_600_000;
       //return timer;
       int sek =(int) (new Date().getTime() - startTid.getTime()) / 1000;  //for å teste
       return sek;
    }
    public double getPris(){
        if(korttid){
            return 20 * getTimer();
        }
        else return 10 * getTimer();
    }

    public String getKvittering(){
        String ut = "Kvittering for bilnr " + bilNummer + "\n";
        String startDateTid = new SimpleDateFormat("HH:mm:ss dd-MM-YYYY").format(startTid);
        String nå = new SimpleDateFormat("HH:mm:ss dd-MM-YYYY").format(new Date());
        ut += "Start tid: " + startDateTid + " til " + nå + "\n";
        ut += "Betalt " + getPris() + " kr";
        return ut;
    }
}

class Parkeringshus{
    ArrayList<Bil> Biler = new ArrayList<>();

    public void reserverplass(Bil enBil){
        Biler.add(enBil);
    }

    public String frigjørplass(String bilNummer){
        for(Bil enBil : Biler){
            if(enBil.bilNummer.equals(bilNummer)){
                Biler.remove(enBil);
                return enBil.getKvittering();
            }
        }
        return bilNummer + " finnes ikke.";
    }
}

public class HelloController {
    Parkeringshus phus = new Parkeringshus();

    @FXML
    private Label lblAvgift;

    @FXML
    private TextField txtBilnr;

    @FXML
    void btnKortid(ActionEvent event) {
        Bil enBil = new Bil(txtBilnr.getText(), new Date(), true);
        phus.reserverplass(enBil);
    }

    @FXML
    void btnLangtid(ActionEvent event) {
        Bil enBil = new Bil(txtBilnr.getText(), new Date(), false);
        phus.reserverplass(enBil);
    }

    @FXML
    void kjørUt(ActionEvent event) {
        lblAvgift.setText(phus.frigjørplass(txtBilnr.getText()));
    }
}
