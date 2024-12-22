import java.io.*;
import java.util.*;
// import java.util.stream.Collectors;
// import org.w3c.dom.Attr;
// import org.w3c.dom.Attr;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class RequestReader {
    String requete = "";
    FileHandler fileHandler;

    public RequestReader(String s) {
        this.requete = s;
        this.fileHandler = new FileHandler(requete);
    }

    public String translate() throws Exception {
        String result = "";

        // Commande SHOW TABLE
        if (requete.equalsIgnoreCase("show table")) {
            result += "+-------------------+\n";
            result += "|      TABLES       |\n";
            result += "+-------------------+\n";
            // System.out.println("+-------------------+");
            // System.out.println("| TABLES |");
            // System.out.println("+-------------------+");

            List<String> tables = fileHandler.listTables();
            for (String table : tables) {
                // System.out.printf("| %-17s |\n", table);
                result += String.format("| %-17s |\n", table);
            }
            result += "+-------------------+\n";
            // System.out.println("+-------------------+");
            return result;
        }

        // Commande CREATE TABLE ou DROP TABLE
        String[] inputCreateTable = CreateSplit();
        String[] tab = Split(inputCreateTable[0]);
        String command = tab[0];
        String tableName = tab[tab.length - 1];

        if (command.equalsIgnoreCase("create")) {
            fileHandler = new FileHandler(tableName);
            fileHandler.createTable();
            fileHandler.writeColumn(splitRow());
            result += "CREATION " + tableName + " (DONE)";
            return result;
            // System.out.println("CREATION " + tableName + " (DONE)");
            // System.out.println("Query OK, 0 rows affected (0.01 sec)");
        } else if (command.equalsIgnoreCase("drop")) {
            fileHandler = new FileHandler(tableName);
            fileHandler.deleteTable();
            result += "DROP " + tableName + " (DONE)";
            return result;
            // System.out.println("DROP " + tableName + " (DONE)");
            // System.out.println("Query OK, 0 rows affected (0.01 sec)");
        }

        // Commande SELECT
        String[] splitRequest = Split(requete);
        if (splitRequest[0].equalsIgnoreCase("select")) {
            String[] where = splitWhere();
            String[] partOne = splitSelect();
            String table = partOne[1];
            String[] condition = splitProjection();

            fileHandler = new FileHandler(table);
            Relation relation = fileHandler.getFile();
            relation = condition.length == 1 && condition[0].equalsIgnoreCase("*")
                    ? relation
                    : relation.projection(condition);

            if (where.length == 2) {
                relation = relation.selection(where[1].trim());
            }

            result += "";
            result += afficherRelation(table, relation);
            return result;
            // System.out.println("");
        }

        // Commande INSERT INTO
        else if (requete.toLowerCase().startsWith("insert into")) {
            String[] tabInsert = splitInsert(requete);
            String attributes = tabInsert[0];
            String values = tabInsert[1];
            String[] splitAttributes = SplitVirgule(attributes);
            String[] splitValues = SplitVirgule(values);
            Vector<Object> data = new Vector<>();
            fileHandler = new FileHandler(tableName);
            for (String value : splitValues) {
                data.add(value);
            }
            if (splitAttributes.length != splitValues.length) {
                result += "Erreur dans l'input";
                return result;
                // System.out.println("Erreur dans l'input");
            } else {
                Relation relation = fileHandler.getRelation();
                for (int i = 0; i < relation.getAttribut().size(); i++) {
                    try {
                        Attribut attribute = relation.getAttribut().get(i);
                        Object value = data.get(i);
                        if (attribute.getDomaine().equals(Integer.class)) {
                            data.set(i, Integer.parseInt(value.toString()));
                        } else if (attribute.getDomaine().equals(Boolean.class)) {
                            data.set(i, Boolean.parseBoolean(value.toString()));
                        } else if (attribute.getDomaine().equals(Double.class)) {
                            data.set(i, Double.parseDouble(value.toString()));
                        } else if (attribute.getDomaine().equals(Float.class)) {
                            data.set(i, Float.parseFloat(value.toString()));
                        } else if (attribute.getDomaine().equals(String.class)) {
                            try {
                                data.set(i, Integer.parseInt(value.toString()));
                            } catch (Exception e) {
                                data.set(i, value.toString());
                            }
                            if (data.get(i).getClass().equals(Integer.class)) {
                                result += "Erreur de conversion pour l'attribut " + relation.getAttribut().get(i);
                                return result;
                            }
                        }
                    } catch (Exception e) {
                        // System.out.println("Erreur de conversion pour l'attribut " +
                        // relation.getAttribut().get(i)
                        // + ": " + e.getMessage());
                        result += "Erreur de conversion pour l'attribut " + relation.getAttribut().get(i);
                        return result;
                    }
                }

                if (relation.addNUplet(data)) {
                    fileHandler.insertNUplet(data);
                    // System.out.println("Operation réussie");
                } else {
                    // System.out.println("Erreur de correspondance dans les données");
                    result += "Erreur de correspondance dans les données";
                    return result;
                }
                result += "Operation réussie";
                return result;
            }
        }

        // Commande DELETE ou UPDATE
        else if (requete.toLowerCase().startsWith("delete from")) {
            String[] tabDelete = splitDelete(requete);
            String table = tabDelete[2];
            fileHandler = new FileHandler(table);

            if (requete.toLowerCase().contains("where")) {
                String[] whereClause = requete.toLowerCase().split("where", 2)[1].trim().split("=");
                String column = whereClause[0].trim();
                String value = whereClause[1].trim();
                fileHandler.deleteWithCondition(column, value);
                result += "Suppression effectuée avec la condition WHERE.";
                return result;
                // System.out.println("Suppression effectuée avec la condition WHERE.");

            } else {
                fileHandler.deleteAllRows();
                // System.out.println("Toutes les lignes ont été supprimées.");
                result += "Toutes les lignes ont été supprimées.";
                return result;
            }
        } else if (requete.toLowerCase().startsWith("update")) {
            String[] tabUpdate = splitUpdate(requete);
            String table = tabUpdate[1];
            String setClause = tabUpdate[2];
            String whereClause = tabUpdate[3];
            fileHandler = new FileHandler(table);
            Relation relation = fileHandler.getRelation();
            String[] setColumns = setClause.split(",");
            Map<String, String> updateValues = new HashMap<>();
            for (String set : setColumns) {
                String[] parts = set.trim().split("=");
                // System.out.println(parts[0]);
                // System.out.println(parts[1]);
                Vector<Object> data = new Vector<>();
                data.add(parts[1]);
                Attribut attribute = null;
                for (Attribut att : relation.getAttribut()) {
                    if (att.getName().equals(parts[0].trim())) {
                        attribute = att;
                    }
                }
                if (attribute == null) {
                    System.out.println("Attribut non trouvé.");
                    result += "Attribut non trouvé.";
                    return result;
                }
                try {
                    Object value = parts[1];
                    // System.out.println(attribute.getDomaine());
                    if (attribute.getDomaine().equals(Integer.class)) {
                        data.set(0, Integer.parseInt(value.toString()));
                    } else if (attribute.getDomaine().equals(Boolean.class)) {
                        data.set(0, Boolean.parseBoolean(value.toString()));
                    } else if (attribute.getDomaine().equals(Double.class)) {
                        data.set(0, Double.parseDouble(value.toString()));
                    } else if (attribute.getDomaine().equals(Float.class)) {
                        data.set(0, Float.parseFloat(value.toString()));
                    } else if (attribute.getDomaine().equals(String.class)) {
                        try {
                            data.set(0, Integer.parseInt(value.toString()));
                        } catch (Exception e) {
                            data.set(0, value.toString());
                        }
                        if (data.get(0).getClass().equals(Integer.class)) {
                            result += "Erreur de conversion pour l'attribut " + relation.getAttribut().get(0);
                            return result;
                        }
                    }
                } catch (Exception e) {
                    // System.out.println("Erreur de correspondance pour l'attribut " +
                    // relation.getAttribut().get(0)
                    // + ": " + e.getMessage());
                    result += "Erreur de correspondance pour l'attribut " +
                            relation.getAttribut().get(0);
                    return result;
                }
                if (parts.length == 2) {
                    updateValues.put(parts[0].trim(), parts[1].trim().replace("'", ""));
                }
            }

            if (whereClause != null) {
                String[] whereCondition = whereClause.split("=");
                if (whereCondition.length == 2) {
                    fileHandler.updateWithCondition(table, updateValues, whereCondition[0].trim(),
                            whereCondition[1].trim().replace("'", ""));
                }
            } else {
                fileHandler.updateAllRows(table, updateValues);
            }
            result += "Mise à jour effectuée.";
            return result;
        }

        return result;
    }

    // outils

    public String[] Split(String e) { // split pour insert
        if (e == null || e.trim().isEmpty()) {
            return new String[0];
        }
        String[] tab = e.trim().split("\\s+");
        return tab;
    }

    public String[] splitWhere() { // split pour select
        String[] result = this.requete.toLowerCase().split("where");
        return result;
    }

    public String[] CreateSplit() { // split pour insert
        if (this.requete == null || this.requete.trim().isEmpty()) {
            return new String[0];
        }
        String cleanedRequete = this.requete.trim();
        if (!cleanedRequete.contains("(")) {
            return new String[] { cleanedRequete };
        }
        int parenthesisIndex = cleanedRequete.indexOf("(");
        if (parenthesisIndex == -1) {
            return new String[0];
        }
        String[] result = new String[2];
        result[0] = cleanedRequete.substring(0, parenthesisIndex).trim();
        result[1] = cleanedRequete.substring(parenthesisIndex + 1, cleanedRequete.lastIndexOf(")")).trim();
        return result;
    }

    public String[] splitRow() { // return les lignes de colonnes de la table
        String[] inputCreateTable = CreateSplit();
        String column = inputCreateTable[1];
        String[] row = column.split(",");
        return row;
    }

    public String[] splitDelete(String requete) {
        requete = requete.trim();
        String[] parts = requete.split("\\s+");
        if (parts.length < 3 || !parts[0].equalsIgnoreCase("delete") || !parts[1].equalsIgnoreCase("from")) {
            throw new IllegalArgumentException("Requête non valide : doit être au format 'DELETE FROM nomTable ...'");
        }
        return parts;
    }

    public String[] splitUpdate(String requete) {
        requete = requete.trim();
        if (!requete.toLowerCase().startsWith("update")) {
            throw new IllegalArgumentException("Requête non valide : doit commencer par 'UPDATE'");
        }
        String[] result = new String[4];
        int setIndex = requete.toLowerCase().indexOf(" set ");
        if (setIndex == -1) {
            throw new IllegalArgumentException("Requête non valide : clause SET manquante");
        }
        result[0] = "UPDATE";
        result[1] = requete.substring(6, setIndex).trim(); // table name
        int whereIndex = requete.toLowerCase().indexOf(" where ");

        if (whereIndex != -1) {
            result[2] = requete.substring(setIndex + 5, whereIndex).trim();
            result[3] = requete.substring(whereIndex + 7).trim();
        } else {
            result[2] = requete.substring(setIndex + 5).trim();
            result[3] = null;
        }
        return result;
    }

    // select
    public String[] splitSelectCondition() {
        String[] delSelect = this.requete.split("where");
        return delSelect;
    }

    public String[] splitSelect() {
        String requete = splitSelectCondition()[0];
        requete = requete.replaceAll("(?i)select", "").replaceAll("(?i)from", "").trim();
        String[] delSelect = requete.split("\\s+");
        return delSelect;
    }

    public String[] splitProjection() {
        String requete = splitSelect()[0];
        requete = requete.replace("(", "").replace(")", "").trim();
        String[] result = requete.split(",");
        return result;
    }
    // select

    // insert
    public String[] SplitVirgule(String input) {
        return input.split("\\s*,\\s*");
    }

    public String extractParenthese(String data) {
        String one = data.split("\\(")[1].split("\\)")[0];
        // System.out.println(one);
        return one;
    }

    public String[] splitInsert(String requete) {
        String[] result = new String[2];
        String[] split = requete.split("(?i)values");
        result[0] = extractParenthese(split[0]).trim();
        result[1] = extractParenthese(split[1]).trim();
        return result;
    }

    // insert

    // affichage
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String afficherRelation(String tableName, Relation relation) {
        StringBuilder sb = new StringBuilder();

        // Ajouter le nom de la table
        sb.append("TABLE: ").append(tableName).append("\n");

        // Récupérer les noms des colonnes
        List<Attribut> attributs = relation.getAttribut();
        List<String> colonnes = new ArrayList<>();
        for (Attribut attr : attributs) {
            colonnes.add(attr.getName());
        }

        // Déterminer la largeur de chaque colonne
        int[] columnWidths = new int[colonnes.size()];
        for (int i = 0; i < colonnes.size(); i++) {
            columnWidths[i] = colonnes.get(i).length(); // La largeur initiale est la longueur du nom de la colonne
        }

        // Calculer la largeur maximale pour chaque colonne
        for (Vector nuplet : relation.getnUplet()) {
            for (int i = 0; i < nuplet.size(); i++) {
                int length = nuplet.get(i).toString().length();
                if (length > columnWidths[i]) {
                    columnWidths[i] = length; // Ajuster la largeur de la colonne si nécessaire
                }
            }
        }

        // Construire l'en-tête
        String header = formatRow(colonnes, columnWidths);
        String separator = "-".repeat(header.length());

        // Ajouter l'en-tête et le séparateur au StringBuilder
        sb.append(separator).append("\n");
        sb.append(header).append("\n");
        sb.append(separator).append("\n");

        // Ajouter les lignes des n-uplets
        for (Vector<Vector> nuplet : relation.getnUplet()) {
            String row = formatRow(nuplet, columnWidths);
            sb.append(row).append("\n");
        }

        // Ajouter la ligne de fin de tableau
        sb.append(separator).append("\n");

        return sb.toString();
    }

    public String formatRow(List<?> values, int[] columnWidths) {
        StringBuilder formattedRow = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            String value = values.get(i).toString();
            formattedRow.append(String.format("%-" + columnWidths[i] + "s", value)); // Alignement à gauche
            if (i < values.size() - 1) {
                formattedRow.append(" | "); // Ajouter séparateur entre les colonnes
            }
        }
        return formattedRow.toString();
    }

    public String formatRow(Vector<Object> values, int[] columnWidths) {
        return formatRow(new ArrayList<>(values), columnWidths);
    }

}