package ba.unsa.rpr.tutorijal7;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Tutorijal {

    static ArrayList<Grad> ucitajGradove(){
        Scanner input;
        try {
            input = new Scanner(new FileReader("mjerenja.txt"));
        }catch(FileNotFoundException ex){
            System.out.println("Greska: " + ex);
            return null;
        }
        ArrayList<String> tmp = new ArrayList<String>();

        while(input.hasNext()){
            tmp.add(input.nextLine());
        }
        ArrayList<Grad> gradovi = new ArrayList<Grad>();
        for (String aTmp : tmp) {
            Grad grad = new Grad();
            double[] temp = new double[1000];
            String[] nazivGrada = aTmp.split(",");
            if (nazivGrada.length >= 1000) {
                System.out.println("Prekoracen limit niza temperature!");
                return null;
            }
            grad.setNaziv(nazivGrada[0]);
            for (int j = 1; j < nazivGrada.length; j++) {
                try {
                    temp[j - 1] = Double.valueOf(nazivGrada[j]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    return null;
                }
            }

            grad.setTemperature(temp);
            gradovi.add(grad);
        }
        input.close();
        return gradovi;
    }
    static UN ucitajXml(ArrayList<Grad> gradovi){

        UN drzave = new UN ();
        ArrayList<Drzava> listaDrzava = new ArrayList<Drzava>();

        Document dokument = null;
        try {
            DocumentBuilder docReader = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            dokument = docReader.parse(new File("drzave.xml"));
        } catch (Exception e) {
            System.out.println("drzave.xml nije validan XML dokument");
        }

        NodeList drzaveUXmlu = dokument.getChildNodes();
        for(int i=0; i<drzaveUXmlu.getLength(); i++){
            Node temp = drzaveUXmlu.item(i);
            if(temp instanceof Element){

                String stanovnici = ((Element) temp).getAttribute("stanovnika");
                String nazivDrzave = ((Element) temp).getAttribute("naziv");
                //String povrs = ((Element) temp).getAttribute("povrsina");
                String jedZaPovrs = ((Element) temp).getAttribute("jedinicaZaPovrsinu");

                Grad glavni = new Grad();
                Drzava dr = new Drzava();

                dr.setBrojStanovnika(Integer.parseInt(stanovnici));
                dr.setNaziv(nazivDrzave);
                dr.setGlavniGrad(glavni);
                dr.setJedinicaZaPovrsinu(jedZaPovrs);
                //dr.setPovrsina(Double.parseDouble(povrs));

                listaDrzava.add(dr);
            }
        }
        drzave.setDrzave(listaDrzava);
        return drzave;

    }
    static void zapisiXml(UN drzave){
        try {
            XMLEncoder e = new XMLEncoder(
                    new BufferedOutputStream(
                            new FileOutputStream("un.xml")));
            e.writeObject(drzave);
            e.close();
        } catch(Exception e) {
            System.out.println("Greška: " + e);
        }
    }

    public static void main(String[] args) {
        ArrayList<Grad> gradovi = ucitajGradove();

        for(Grad g : gradovi) {
            System.out.print("Naziv grada: " + g.getNaziv() + ", Broj stanovnika: " + g.getBrojStanovnika() + ", Temperature: ");
            for(double x: g.getTemperature()){
                if(x!=0) System.out.print(x + " ");
            }
            System.out.print("\n");
        }
//        UN drzave = new UN();
//        drzave = ucitajXml(gradovi);
//        for(Drzava d : drzave.getDrzave()) {
//            System.out.print("Naziv drzave: " + d.getNaziv() + ", Broj stanovnika: " + d.getBrojStanovnika() + " Glavni grad: " + d.getGlavniGrad());
//            System.out.print("\n");
//        }
    }

}
