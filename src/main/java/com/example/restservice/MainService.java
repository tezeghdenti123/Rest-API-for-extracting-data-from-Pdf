package com.example.restservice;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class MainService {
    @Autowired
    private MainRepositry mainRepositry;
    private String path;


    public ArrayList<Mois> extractFile(MultipartFile multipartFiles) throws IOException {
        //File pdfFile= new File(path);

        String fileName= multipartFiles.getOriginalFilename();
        ArrayList<Mois> list ;
        ArrayList<Mois> list1 ;
        File convFile = new File( multipartFiles.getOriginalFilename() );
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( multipartFiles.getBytes() );
        PDDocument pdDocument = PDDocument.load(convFile);
        Splitter splitter = new Splitter();
        List<PDDocument> splitpages = splitter.split(pdDocument);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        boolean test = false;
        String moisprec = "";
        String aux = "";
        String name="";
        list = new ArrayList<Mois>();

        Mois mois = new Mois("****","***");
        for (PDDocument mydoc : splitpages) {//boucle sur les page de pdf
            String text = pdfTextStripper.getText(mydoc);
            String[] arofstr = text.split("\n");
            Operation op = new Operation("00/00/0000", "0", "0");
            for (int i = 0; i < arofstr.length; i++) {//boucle sur les lignes d'une seule page
                String[] arr = arofstr[i].split(" ");
                if (arofstr[i].contains("RELEVE ET INFORMATIONS")) {
                    aux = arofstr[i+1];
                }
                if(arofstr[i].contains("CHEZ")){
                    name=arofstr[i-1];
                }
                if (aux.equals(moisprec) == false) {
                    if((mois.getMois().equals("****")==false)){
                        boolean v = false;
                        list1 = mainRepositry.findMoisByName(mois.getName());
                        for (Mois moisn : list1) {
                            if (moisn.getMois().contains(mois.getMois())) {
                                v = true;
                                break;
                            }
                        }
                        if (v == false) {
                            list.add(mois);
                        }

                    //mainRepositry.insert(mois);
                    }
                    mois = new Mois(aux,name);
                    moisprec = aux;
                }
                boolean testdate = arofstr[i].contains("/01/2") || arofstr[i].contains("/02/2") || arofstr[i].contains("/03/2") || arofstr[i].contains("/04/2") || arofstr[i].contains("/05/2") || arofstr[i].contains("/06/2") || arofstr[i].contains("/07/2") || arofstr[i].contains("/08/2") || arofstr[i].contains("/09/2") || arofstr[i].contains("/10/2") || arofstr[i].contains("/11/2") || arofstr[i].contains("/12/2");
                if (testdate) {//indique le debut de tableau
                    test = true;
                }
                if (arofstr[i].contains("Information sur la protection des comptes")) {//indique la fin du tableau
                    test = false;
                }
                if((arofstr[i].contains("SOLDE CREDITEUR"))){
                    test=false;

                }
                if (arr.length == 1) {//indique la fin du tableau
                    if (arofstr[i].contains("K")) {
                        test = false;
                    }
                }

                if (test) {//s'execute si on est dans un tableau
                    String ch = "";
                    if (testdate) {//indique qu'il ya une date
                        if((op.getDate().equals("00/00/0000"))==false) {
                            mois.addoperation(op);
                        }
                        if((arofstr[i].contains("  1"))||(arofstr[i].contains("  2"))||(arofstr[i].contains("  3"))||(arofstr[i].contains("  4"))||(arofstr[i].contains("  5"))||(arofstr[i].contains("  6"))||(arofstr[i].contains("  7"))||(arofstr[i].contains("  8"))||(arofstr[i].contains("  9"))||(arofstr[i].contains("  0"))) {
                            op = new Operation(arr[0], "0", arr[arr.length - 1]);
                        }
                        else{
                            op = new Operation(arr[0], arr[arr.length - 1], "0");
                        }
                        for (int j = 2; j < arr.length - 1; j++) {//obtenir le nom de l'operation
                            ch = ch + " " + arr[j];
                        }

                    }else {
                        for (int j = 2; j < arr.length; j++) {//obtenir le nom de l'operation
                            ch = ch+" "+arr[j];
                        }
                    }
                    op.addoperation(ch);
                    if(arofstr[i].contains("Total des mouvements")==true){
                        mois.addoperation(op);
                        String finmois=op.getDate();
                        op = new Operation(finmois, arr[arr.length - 2], arr[arr.length - 1]);
                        op.addoperation("Total des mouvements");
                    }

                }
            }
            if((op.getDate().equals("00/00/0000"))==false) {
                mois.addoperation(op);
            }

        }

        boolean v=false;
        list1=mainRepositry.findMoisByName(mois.getName());
        for(Mois moisn:list1){
            if(moisn.getMois().contains(mois.getMois())){
                v=true;
                break;
            }
        }
        if(v==false) {
            list.add(mois);
        }
       //mainRepositry.insert(mois);
        return (list);
        }

    public List<Mois> findAll() {
        List<Mois> list=new ArrayList<>();
        List<Mois> list1=new ArrayList<>();
        list=mainRepositry.findAll();
        if(list.size()!=0) {
            list1.add(list.get(list.size() - 1));
        }
        return list1;

    }
    public List<Mois> findmois(String moisn){
        List<Mois> list;
        List<Mois> list1;
        list = new ArrayList<Mois>();
        list=mainRepositry.findAll();
        list1 = new ArrayList<Mois>();
        for(Mois mois:list){
            if(mois.getMois().contains(moisn)){
                list1.add(mois);
            }
        }
        return list1;
    }
    public ArrayList<Mois> save(ArrayList<Mois> list){
        for(Mois mois:list){
            mainRepositry.insert(mois);
        }
        return list;
    }
    public void delete(String id) {
        mainRepositry.deleteById(id);
    }
    public void deleteAll(){
        mainRepositry.deleteAll();
    }
}
