import java.util.Vector;

import org.w3c.dom.Attr;

public class MainRelation {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        // Vector<Object> ens = new Vector<>();
        // ens.add("Archi");
        // ens.add("Algo");
        // ens.add("Syst");

        // Element element = new Element(ens);
        // // for (Object a : element.getElements()) {
        // // System.out.println(a);
        // // }
        // // CJH
        // Relation CJH = new Relation();
        // // Attribut att1 = new Attribut("IdCours", element);
        // Attribut att1 = new Attribut("IdCours",String.class);
        // Attribut att2 = new Attribut("Jour", String.class);
        // Attribut att3 = new Attribut("Heure", String.class);
        // CJH.addAttribut(att1);
        // CJH.addAttribut(att2);
        // CJH.addAttribut(att3);
        // @SuppressWarnings("rawtypes")
        // Vector CJH1 = new Vector<>();
        // CJH1.add("Archi");
        // CJH1.add("Lu");
        // CJH1.add("9h");
        // @SuppressWarnings("rawtypes")
        // Vector CJH2 = new Vector<>();
        // CJH2.add("Algo");
        // CJH2.add("Ma");
        // CJH2.add("9h");
        // @SuppressWarnings("rawtypes")
        // Vector CJH3 = new Vector<>();
        // CJH3.add("Algo");
        // CJH3.add("Ve");
        // CJH3.add("9h");
        // Vector CJH4 = new Vector<>();
        // CJH4.add("Syst");
        // CJH4.add("Ma");
        // CJH4.add("14h");
        // CJH.addNUplet(CJH1);
        // CJH.addNUplet(CJH2);
        // CJH.addNUplet(CJH3);
        // CJH.addNUplet(CJH4);
        // System.out.println(CJH.nUplet.size());

        // // CS
        // Relation CS = new Relation();
        // Attribut CSatt1 = new Attribut("IdCours", String.class);
        // Attribut CSatt2 = new Attribut("IdSalle", String.class);
        // CS.addAttribut(CSatt1);
        // CS.addAttribut(CSatt2);
        // @SuppressWarnings("rawtypes")
        // Vector CS1 = new Vector<>();
        // CS1.add("Archi");
        // CS1.add("S1");
        // @SuppressWarnings("rawtypes")
        // Vector CS2 = new Vector<>();
        // CS2.add("Algo");
        // CS2.add("S2");
        // @SuppressWarnings("rawtypes")
        // Vector CS3 = new Vector<>();
        // CS3.add("Syst");
        // CS3.add("S3");
        // CS.addNUplet(CS1);
        // CS.addNUplet(CS2);
        // CS.addNUplet(CS3);
        // // System.out.println(CS.nUplet.size());

        // // ENA
        // Vector<Object> ens1 = new Vector<>();
        // ens1.add(100);
        // ens1.add(200);
        // ens1.add(300);

        // Element element1 = new Element(ens1);
        // Relation ENA = new Relation();
        // // Attribut ENAatt1 = new Attribut("IdEtudiant",Integer.class);
        // Attribut ENAatt1 = new Attribut("IdEtudiant", element1);
        // Attribut ENAatt2 = new Attribut("Nom", String.class);
        // Attribut ENAatt3 = new Attribut("Adresse", String.class);
        // ENA.addAttribut(ENAatt1);
        // ENA.addAttribut(ENAatt2);
        // ENA.addAttribut(ENAatt3);
        // @SuppressWarnings("rawtypes")
        // Vector ENA1 = new Vector<>();
        // ENA1.add(100); // Integer
        // ENA1.add("Toto");
        // ENA1.add("Nice");

        // Vector ENA2 = new Vector<>();
        // ENA2.add(200); // Integer
        // ENA2.add("Tata");
        // ENA2.add("Paris");

        // Vector ENA3 = new Vector<>();
        // ENA3.add(300); // Integer
        // ENA3.add("Titi");
        // ENA3.add("Rome");
        // ENA.addNUplet(ENA1);
        // ENA.addNUplet(ENA2);
        // ENA.addNUplet(ENA3);
        // System.out.println(ENA.getnUplet());

        // // CEN
        // Relation CEN = new Relation();
        // Attribut CENatt1 = new Attribut("IdCours", String.class);
        // Attribut CENatt2 = new Attribut("IdEtudiant", Integer.class);
        // Attribut CENatt3 = new Attribut("Note", String.class);
        // CEN.addAttribut(CENatt1);
        // CEN.addAttribut(CENatt2);
        // CEN.addAttribut(CENatt3);
        // Vector CEN1 = new Vector<>();
        // CEN1.add("Archi");
        // CEN1.add(100);
        // CEN1.add("A");
        // Vector CEN2 = new Vector<>();
        // CEN2.add("Archi");
        // CEN2.add(300);
        // CEN2.add("A");
        // Vector CEN3 = new Vector<>();
        // CEN3.add("Syst");
        // CEN3.add(100);
        // CEN3.add("B");
        // Vector CEN4 = new Vector<>();
        // CEN4.add("Syst");
        // CEN4.add(200);
        // CEN4.add("A");
        // Vector CEN5 = new Vector<>();
        // CEN5.add("Syst");
        // CEN5.add(300);
        // CEN5.add("B");
        // Vector CEN6 = new Vector<>();
        // CEN6.add("Algo");
        // CEN6.add(100);
        // CEN6.add("C");
        // Vector CEN7 = new Vector<>();
        // CEN7.add("Algo");
        // CEN7.add(200);
        // CEN7.add("A");
        // CEN.addNUplet(CEN1);
        // CEN.addNUplet(CEN2);
        // CEN.addNUplet(CEN3);
        // CEN.addNUplet(CEN4);
        // CEN.addNUplet(CEN5);
        // CEN.addNUplet(CEN6);
        // CEN.addNUplet(CEN7);

        // // ici

        // System.out.println(CEN.nUplet.size());
        // String[] r1 = new String[1];
        // r1[0] = "IdCours";
        // Relation R1 = CJH.projection(r1);
        // System.out.println(R1.getAttribut());
        // System.out.println(R1.getnUplet());

        // String[] r2 = new String[1];
        // r2[0] = "IdEtudiant";
        // Relation R2 = ENA.projection(r2); //jsp trop
        // // System.out.println(R2.getAttribut().size());

        // String[] r3 = new String[2];
        // r3[0] = "IdEtudiant";
        // r3[1] = "IdCours";
        // Relation R3 = CEN.projection(r3);
        // // System.out.println(R3.getAttribut().size());
        // // System.out.println(R3.getnUplet());
        // // System.out.println();

        // Relation R7 = R2.cartesien(R1);
        // // for (int index = 0; index < R7.getnUplet().size(); index++) {
        // System.out.println(R7.getAttribut());
        // System.out.println(R7.getnUplet());
        // // }
        // // // System.out.println();

        // Relation t1 = R7.difference(R3);
        // // System.out.println(t1.getAttribut());
        // // System.out.println(t1.getnUplet());

        // Relation R8 = R7.difference(R3);
        // for (int index = 0; index < R8.getnUplet().size(); index++) {
        // System.out.println(R8.getAttribut());
        // System.out.println(R8.getnUplet());
        // }

        // String[] r9 = new String[1];
        // r9[0] = "IdEtudiant";
        // Relation R9 = R3.projection(r9);
        // System.out.println(R9.getnUplet());

        // String[] r10 = new String[1];
        // r10[0] = "IdEtudiant";
        // Relation R10 = R8.projection(r10);
        // System.out.println(R10.getnUplet());

        // Relation a=CEN.selection("IdEtudiant = 100");
        // try {
        // Relation a = ENA.union(CJH);
        // System.out.println(a.getAttribut());
        // for (Attribut ab : a.getAttribut()) {
        // System.out.println(ab.getDomaine());
        // System.out.println(ab.ensDomaine);
        // if (ab.ensDomaine != null) {
        // System.out.println(ab.ensDomaine.elements);
        // }
        // }
        // System.out.println(a.getnUplet());
        // } catch (Exception e) {
        // // TODO: handle exception
        // System.out.println(e.getMessage());
        // }
        // Relation a=CJH.difference(ENA);
        // Relation a=ENA.intersection(ENA);
        // String[] leo=new String[2];
        // leo[0]="IdEtudiant = 100";
        // leo[1]="IdCours = Algo";
        // Relation a=CEN.selectionMultiple(leo);
        // System.out.println(a.getnUplet());
        // Relation a=CS.jointureN(CJH);
        // Relation ou = ENA.selection("IdEtudiant = 300");
        // System.out.println(ou.getAttribut());
        // System.out.println(ou.getnUplet());
        // Relation ou=ENA.selectionOU("(IdEtudiant = 100) or (Nom = Tata) or
        // (IdEtudiant = 300)");
        // System.out.println(ou.getnUplet());
        // Relation et=ENA.selectionET("(Nom = Titi) and (IdEtudiant = 300) and (Adresse
        // = Rome)");
        // System.out.println(et.getnUplet());

        // SELECT (IdEt) FROM Etudiant
        // select * from Etudiant
        // RequestReader rr = new RequestReader(
        // "DELETE FROM etudiant");
        RequestReader rr = new RequestReader(
                "UPDATE Test set IdSem=okok WHERE IdSem=1");
        try {
            // System.out.println(rr.requete);
            rr.translate();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
            // (idEt,nomEt)
        }
    }

}