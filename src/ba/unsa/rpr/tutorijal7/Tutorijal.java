package ba.unsa.rpr.tutorijal7;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Tutorijal {

    static ArrayList<Grad> ucitajGradove(String naziv){
        Scanner input;
        try {
            input = new Scanner(new FileReader(naziv));
        }catch(FileNotFoundException ex){
            System.out.println("Greska" + ex);
            return null;
        }
        ArrayList<String> tmp = new ArrayList<String>();

        while(input.hasNext()){
            tmp.add(input.nextLine());
        }
        ArrayList<Grad> gradovi = new ArrayList<Grad>();
        for(int i=0; i<tmp.size(); i++){
            Grad grad = new Grad();
            double[] temp = new double[1000];
            String[] nazivGrada = tmp.get(i).split(",");
            if(nazivGrada.length >= 1000){
                System.out.println("Prekoracen limit niza temperature!");
                return null;
            }
            grad.setNaziv(nazivGrada[0]);
            for (int j = 1; j < nazivGrada.length; j++) {
                try {
                    temp[j - 1] = Double.valueOf(nazivGrada[j]);
                } catch (ArrayIndexOutOfBoundsException e){
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
        try {
            XMLDecoder ulaz = new XMLDecoder(new FileInputStream("drzave.xml"));
            drzave = (UN) ulaz.readObject();
            ulaz.close();
        } catch(Exception e) {
            System.out.println("Greška: "+e);
        }
        return drzave;

    }
    static void zapisiXml(UN drzave){

    }
    public static void main(String[] args) {

    }

}
