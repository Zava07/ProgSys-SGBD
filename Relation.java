import java.util.*;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Relation {

    Vector<Attribut> attributs = new Vector();
    List<Vector> nUplet;

    public Relation() {
        this.attributs = new Vector<>();
        this.nUplet = new ArrayList<>();
    }

    public Relation(Vector<Attribut> a, List<Vector> n) {
        this.attributs = a;
        this.nUplet = n;
    }

    public List<Attribut> getAttribut() {
        return this.attributs;
    }

    public List<Vector> getnUplet() {
        return this.nUplet;
    }

    public void addAttribut(Attribut attr) {
        attributs.add(attr);
    }

    public void deleteAttribut(Attribut attr) {
        attributs.remove(attr);
    }

    public boolean checkNUpletSize(Vector ligne) {
        return ligne.size() == attributs.size();
    }

    public boolean checkNUplet(Vector ligne) {
        if (!checkNUpletSize(ligne)) {
            return false;
        }
        for (int i = 0; i < ligne.size(); i++) {
            // System.out.println(ligne.get(i).getClass().getName());
            if (attributs.get(i).ensDomaine != null) {
                if (attributs.get(i).ensDomaine.Appartenance(ligne.get(i))) {
                    // System.out.println(ligne.get(i).getClass().getName());
                    continue;
                } else if (!attributs.get(i).ensDomaine.Appartenance(ligne.get(i))) {
                    return false;
                }
            } else if (attributs.get(i).ensDomaine == null) {
                if (ligne.get(i).getClass().equals(attributs.get(i).domaine)) {
                    continue;
                } else if (!ligne.get(i).getClass().equals(attributs.get(i).domaine)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean addNUplet(Vector ligne) {
        try {
            if (checkNUplet(ligne)) {
                nUplet.add(ligne);
                // System.out.println("Correspondant");
                return true;
            } else if (!checkNUplet(ligne)) {
                // System.out.println("Non correspondant");
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return true;
    }

    public void addVecNUplet(Relation rel, List<Vector> ligne) throws Exception {
        try {
            for (Vector vector : ligne) {
                if (addNUplet(vector) == false) {
                    throw new Exception("Erreur de correspondance du nUplet");
                }
            }
            for (Vector vector : ligne) {
                rel.nUplet.add(vector);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public Relation intersection(Relation relation) { // checked
        Relation result = new Relation();
        List<Vector> nU = new ArrayList<>();
        Set<Vector> list = new HashSet<>();
        if (this.attributs.size() == relation.attributs.size()) {
            result.attributs = this.attributs;
            for (int i = 0; i < this.attributs.size(); i++) {
                Attribut relAtt = (Attribut) result.attributs.get(i);
                Attribut att1 = (Attribut) this.attributs.get(i);
                Attribut att2 = (Attribut) relation.attributs.get(i);
                if (att1.getDomaine() != null || att2.getDomaine() != null) {
                    if ((att1.getDomaine() != null && att1.getDomaine().equals(String.class))
                            || (att2.getDomaine() != null && att2.getDomaine().equals(String.class))) {
                        relAtt.setDomaine(String.class);
                    }
                }
                if (att1.ensDomaine != null && att2.ensDomaine == null) {
                    Element el = att1.ensDomaine;
                    el.elements.add(att2.domaine);
                    relAtt.setDomaine(el);
                }
                if (att1.ensDomaine == null && att2.ensDomaine != null) {
                    Element el = att2.ensDomaine;
                    el.elements.add(att1.domaine);
                    relAtt.setDomaine(el);
                } else if (att1.ensDomaine != null && att2.ensDomaine != null) {
                    Element el = att1.ensDomaine;
                    for (int j = 0; j < att2.ensDomaine.elements.size(); j++) {
                        // System.out.println("okpl");
                        el.elements.add(att2.ensDomaine.elements.get(j));
                    }
                    relAtt.setDomaine(el);
                }
            }
            for (int i = 0; i < this.nUplet.size(); i++) {
                Vector vec1 = (Vector) this.nUplet.get(i);
                if (relation.nUplet.contains(vec1)) {
                    list.add(vec1);
                }
            }
            for (int i = 0; i < relation.nUplet.size(); i++) {
                Vector vec2 = (Vector) relation.nUplet.get(i);
                if (this.nUplet.contains(vec2)) {
                    list.add(vec2);
                }
            }
            nU = new ArrayList<>(list);
            // result.nUplet = nU;
            try {
                addVecNUplet(result,nU);
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            // System.out.println(result.nUplet.size());
            return result;
        } else {
            System.out.println("Non correspondant");
            return result;
        }
    }

    public Vector sepET(String string) {
        Vector result = new Vector<>();
        String[] or = string.split("and");
        for (int i = 0; i < or.length; i++) {
            String[] parenth1 = or[i].split("\\(");
            String[] parenth2 = parenth1[1].split("\\)");
            result.add(parenth2[0]);
        }
        return result;
    }

    public Relation selectionET(String sql) {
        Vector<String> requete = sepET(sql);
        Relation result = selection(requete.get(0));
        for (int i = 1; i < requete.size(); i++) {
            result = result.intersection(selection(requete.get(i)));
        }
        return result;
    }

    // modif de domaine d'attribut
    public Relation union(Relation relation) throws Exception { // checked
        Relation result = new Relation();
        List<Vector> nU = new ArrayList<>();
        Set<Vector> list = new HashSet<>();
        if (this.attributs.size() == relation.attributs.size()) {
            result.attributs = this.attributs;
            for (int i = 0; i < this.attributs.size(); i++) {
                Attribut relAtt = (Attribut) result.attributs.get(i);
                Attribut att1 = (Attribut) this.attributs.get(i);
                Attribut att2 = (Attribut) relation.attributs.get(i);
                if (att1.getDomaine() != null || att2.getDomaine() != null) {
                    if ((att1.getDomaine() != null && att1.getDomaine().equals(String.class))
                            || (att2.getDomaine() != null && att2.getDomaine().equals(String.class))) {
                        relAtt.setDomaine(String.class);
                    }
                }
                if (att1.ensDomaine != null && att2.ensDomaine == null) {
                    Element el = att1.ensDomaine;
                    el.elements.add(att2.domaine);
                    relAtt.setDomaine(el);
                }
                if (att1.ensDomaine == null && att2.ensDomaine != null) {
                    Element el = att2.ensDomaine;
                    el.elements.add(att1.domaine);
                    relAtt.setDomaine(el);
                } else if (att1.ensDomaine != null && att2.ensDomaine != null) {
                    Element el = att1.ensDomaine;
                    for (int j = 0; j < att2.ensDomaine.elements.size(); j++) {
                        // System.out.println("okpl");
                        el.elements.add(att2.ensDomaine.elements.get(j));
                    }
                    relAtt.setDomaine(el);
                }

            }
            list.addAll(this.nUplet);
            list.addAll(relation.nUplet);
            nU = new ArrayList<>(list);
            // result.nUplet = nU;
            try {
                addVecNUplet(result,nU);
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            return result;
        } else {
            System.out.println("Non correspondant");
            return result;
        }
    }

    public Relation difference(Relation relation) { // cheked but idk
        Relation result = new Relation();
        List<Vector> nU = new ArrayList<>();
        Set<Vector> list = new HashSet<>();
        Vector attribut = new Vector<>();
        if (this.attributs.size() == relation.attributs.size()) {
            result.attributs = this.attributs;
            for (int i = 0; i < this.attributs.size(); i++) {
                Attribut relAtt = (Attribut) result.attributs.get(i);
                Attribut att1 = (Attribut) this.attributs.get(i);
                Attribut att2 = (Attribut) relation.attributs.get(i);
                if (att1.getDomaine() != null || att2.getDomaine() != null) {
                    if ((att1.getDomaine() != null && att1.getDomaine().equals(String.class))
                            || (att2.getDomaine() != null && att2.getDomaine().equals(String.class))) {
                        relAtt.setDomaine(String.class);
                    }
                }
                if (att1.ensDomaine != null && att2.ensDomaine == null) {
                    Element el = att1.ensDomaine;
                    el.elements.add(att2.domaine);
                    relAtt.setDomaine(el);
                }
                if (att1.ensDomaine == null && att2.ensDomaine != null) {
                    Element el = att2.ensDomaine;
                    el.elements.add(att1.domaine);
                    relAtt.setDomaine(el);
                } else if (att1.ensDomaine != null && att2.ensDomaine != null) {
                    Element el = att1.ensDomaine;
                    for (int j = 0; j < att2.ensDomaine.elements.size(); j++) {
                        // System.out.println("okpl");
                        el.elements.add(att2.ensDomaine.elements.get(j));
                    }
                    relAtt.setDomaine(el);
                }

            }

            for (int i = 0; i < this.attributs.size(); i++) {
                Attribut relAtt = (Attribut) result.attributs.get(i);
                Attribut att1 = (Attribut) this.attributs.get(i);
                Attribut att2 = (Attribut) relation.attributs.get(i);
                if (att1.getDomaine() != att2.getDomaine()) {
                    relAtt.setDomaine(String.class);
                }
            }
            for (int i = 0; i < relation.nUplet.size(); i++) {
                Vector vec2 = (Vector) relation.nUplet.get(i);
                if (!this.nUplet.contains(vec2)) {
                    list.add(vec2);
                }
            }
            for (int i = 0; i < this.nUplet.size(); i++) {
                Vector vec1 = (Vector) this.nUplet.get(i);
                if (!relation.nUplet.contains(vec1)) {
                    list.add(vec1);
                }
            }
            nU = new ArrayList<>(list);
            // System.out.println(list.size());
            // result.nUplet = nU;
            try {
                addVecNUplet(result,nU);
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            return result;
        } else {
            System.out.println("Non correspondant");
            return result;
        }
    }

    public String[] separer(String string) {
        String[] resultat = string.split(" ", 3);
        return resultat;
    }

    public Relation selection(String requete) { // checked
        String[] sep = separer(requete);
        String colonne = sep[0];
        String condition = sep[1];
        String comp = sep[2];
        int place = 0;

        Relation rel = new Relation();
        rel.attributs = this.attributs;
        List<Vector> nU = new ArrayList<>();

        for (int j = 0; j < this.attributs.size(); j++) {
            Attribut attr = this.attributs.get(j);
            if (colonne.equals(attr.getName())) {
                place = j;
            }
        }
        for (int i = 0; i < this.nUplet.size(); i++) {
            Vector vec = (Vector) this.nUplet.get(i);
            Attribut attr = (Attribut) this.attributs.get(place);
            if (attr.getDomaine().equals(String.class)) {
                if (condition.equals("=") && vec.get(place).equals(comp)) {
                    nU.add(vec);
                } else if (condition.equals("!=") && !vec.get(place).equals(comp)) {
                    nU.add(vec);
                }
            }
            if (attr.getDomaine().equals(Integer.class)) {
                int comparaison = Integer.parseInt(comp);
                int valeur = (int) vec.get(place);
                // System.out.println(vec.size());
                if (condition.equals("=") && valeur == comparaison) {
                    nU.add(vec);
                } else if (condition.equals("!=") && valeur != comparaison) {
                    nU.add(vec);
                } else if (condition.equals(">") && valeur > comparaison) {
                    nU.add(vec);
                } else if (condition.equals(">=") && valeur >= comparaison) {
                    nU.add(vec);
                } else if (condition.equals("<") && valeur < comparaison) {
                    nU.add(vec);
                } else if (condition.equals("<=") && valeur <= comparaison) {
                    nU.add(vec);
                }
            }
        }
        rel.nUplet = nU;
        // System.out.println(rel.nUplet);
        return rel;
    }

    public Vector sepOU(String string) {
        Vector result = new Vector<>();
        String[] or = string.split("or");
        for (int i = 0; i < or.length; i++) {
            String[] parenth1 = or[i].split("\\(");
            String[] parenth2 = parenth1[1].split("\\)");
            result.add(parenth2[0]);
        }
        return result;
    }

    public Relation selectionOU(String sql) {
        Vector<String> requete = sepOU(sql);
        Relation result = selection(requete.get(0));
        for (int i = 1; i < requete.size(); i++) {
            try {
                result = result.union(selection(requete.get(i)));
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

    public Relation selectionSpe(Relation ok, String requete) {
        String[] sep = separer(requete);
        String colonne = sep[0];
        String condition = sep[1];
        String comp = sep[2];
        int place = 0;

        Relation rel = new Relation();
        rel.attributs = ok.attributs;
        List<Vector> nU = new ArrayList<>();

        for (int j = 0; j < ok.attributs.size(); j++) {
            Attribut attr = ok.attributs.get(j);
            if (colonne.equals(attr.getName())) {
                place = j;
            }
        }
        for (int i = 0; i < ok.nUplet.size(); i++) {
            Vector vec = (Vector) ok.nUplet.get(i);
            Attribut attr = (Attribut) ok.attributs.get(place);
            if (attr.getDomaine().equals(String.class)) {
                if (condition.equals("=") && vec.get(place).equals(comp)) {
                    nU.add(vec);
                } else if (condition.equals("!=") && !vec.get(place).equals(comp)) {
                    nU.add(vec);
                }
            } else if (attr.getDomaine().equals(Integer.class)) {
                int comparaison = Integer.parseInt(comp);
                int valeur = (int) vec.get(place);
                if (condition.equals("=") && valeur == comparaison) {
                    nU.add(vec);
                } else if (condition.equals("!=") && valeur != comparaison) {
                    nU.add(vec);
                } else if (condition.equals(">") && valeur > comparaison) {
                    nU.add(vec);
                } else if (condition.equals(">=") && valeur >= comparaison) {
                    nU.add(vec);
                } else if (condition.equals("<") && valeur < comparaison) {
                    nU.add(vec);
                } else if (condition.equals("<=") && valeur <= comparaison) {
                    nU.add(vec);
                }
            }
        }
        rel.nUplet = nU;
        // System.out.println(rel.nUplet);
        return rel;
    }

    public Relation selectionMultiple(String[] requete) {
        Relation result = selectionSpe(selection(requete[0]), requete[0]);
        for (int index = 0; index < requete.length; index++) {
            result = selectionSpe(result, requete[index]);

        }
        // System.out.println(result.nUplet);
        return result;
    }

    public Relation projection(String[] colonne) { // checked attribut
        Relation rel = new Relation();
        List<Vector> nU = new ArrayList<>();
        Set<Vector> list = new HashSet<>();
        Vector<Attribut> attributs = new Vector();
        int[] nbre = new int[colonne.length];
        int init = 0;
        for (int i = 0; i < this.attributs.size(); i++) {
            for (int j = 0; j < colonne.length; j++) {
                Attribut attr = this.attributs.get(i);
                if (colonne[j].equals(attr.getName())) {
                    nbre[init] = i;
                    init++;
                    attributs.add(attr);
                //  System.out.println(attr+"projection");
                }
            }
        }
        for (int i = 0; i < this.nUplet.size(); i++) {
            Vector uplet = this.nUplet.get(i);
            Vector upl = new Vector<>();
            for (int j = 0; j < uplet.size(); j++) {
                for (int index = 0; index < nbre.length; index++) {
                    if (j == nbre[index]) {
                        upl.add(uplet.get(j));
                    }
                }
            }
            list.add(upl);
        }
        rel.attributs = attributs;
        nU = new ArrayList<>(list);
        rel.nUplet = nU;
        return rel;
    }

    public Relation cartesien(Relation rel) { // checked
        Relation relation = new Relation();
        relation.attributs.addAll(rel.attributs);
        relation.attributs.addAll(this.attributs);
        List<Vector> nU = new ArrayList<>();
        for (int i = 0; i < this.nUplet.size(); i++) {
            Vector vec1 = this.nUplet.get(i);
            for (int j = 0; j < rel.nUplet.size(); j++) {
                Vector vec2 = rel.nUplet.get(j);
                Vector all = new Vector<>(vec2);
                all.addAll(vec1);
                nU.add(all);
            }
        }
        relation.nUplet = nU;
        return relation;
    }

    public Relation jointureN(Relation relation) { // checked
        Relation rel = new Relation();
        List<Vector> nU = new ArrayList<>();
        Vector<String> temp = new Vector<>();
        Vector attribut = new Vector<>();
        Vector attr1Nbre = new Vector<>();
        Vector attr2Nbre = new Vector<>();
        for (int i = 0; i < this.attributs.size(); i++) {
            for (int j = 0; j < relation.attributs.size(); j++) {
                Attribut att1 = (Attribut) this.attributs.get(i);
                Attribut att2 = (Attribut) relation.attributs.get(j);
                if (att1.getName().equals(att2.getName())) {
                    temp.add(att1.getName());
                    attr1Nbre.add(i);
                    attr2Nbre.add(j);
                    // System.out.println(attr1Nbre);
                    // System.out.println(attr2Nbre);
                }
            }
        }
        String[] memeAttr = temp.toArray(new String[0]);
        for (int i = 0; i < this.nUplet.size(); i++) {
            for (int j = 0; j < relation.nUplet.size(); j++) {
                Vector attr1 = (Vector) this.attributs;
                Vector attr2 = (Vector) relation.attributs;
                Vector vec1 = (Vector) this.nUplet.get(i);
                Vector vec2 = (Vector) relation.nUplet.get(j);
                for (int k = 0; k < attr1Nbre.size(); k++) {
                    int ind1 = (int) attr1Nbre.get(k);
                    int ind2 = (int) attr2Nbre.get(k);
                    if (vec1.get(ind1).equals(vec2.get(ind2))) {
                        if (k == (attr1Nbre.size() - 1)) {
                            attribut = new Vector<>(attr1);
                            Vector combinedVector = new Vector<>(vec1);
                            for (int l = 0; l < vec2.size(); l++) {
                                if (!combinedVector.contains(vec2.get(l))) {
                                    combinedVector.add(vec2.get(l));
                                    attribut.add(attr2.get(l));
                                }
                            }
                            nU.add(combinedVector);
                        }
                    }
                }
            }
        }
        rel.attributs = attribut;
        rel.nUplet = nU;
        // System.out.println(attribut);
        // System.out.println(nU);
        return rel;
    }

    public Relation tetaJointure(Relation one, String[] requete) { // checked
        Relation relation = this.cartesien(one);
        Relation result = relation.selectionMultiple(requete);
        return result;
    }
}