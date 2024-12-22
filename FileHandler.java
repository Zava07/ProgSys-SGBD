import java.io.*;
import java.nio.*;
import java.util.*;

public class FileHandler {

    File tableFile;

    public FileHandler(String name) {
        this.tableFile = new File("Database/" + name + ".txt");
    }

    public void createTable() throws Exception {
        if (tableFile.exists()) {
            throw new Exception("Table existant.");
        }
        tableFile.createNewFile();
    }

    public List<String> listTables() {
        File databaseDir = new File("Database");
        if (!databaseDir.exists() || !databaseDir.isDirectory()) {
            System.out.println("Le dossier 'Database' n'existe pas ou n'est pas un répertoire.");
            return Collections.emptyList();
        }

        String[] files = databaseDir.list((dir, name) -> name.endsWith(".txt"));
        if (files == null || files.length == 0) {
            System.out.println("Aucun fichier trouvé dans le dossier 'Database'.");
            return Collections.emptyList();
        }
        return Arrays.stream(files)
                .map(name -> name.replaceAll("\\.txt$", "")) // Supprimer .txt à la fin
                .toList();
    }

    public void deleteTable() {
        if (tableFile.exists()) {
            boolean deleted = tableFile.delete();
            if (deleted) {
                System.out.println("Le fichier a été supprimé avec succès.");
            } else {
                System.out.println("La suppression du fichier a échoué.");
            }
        } else {
            System.out.println("Le fichier n'existe pas.");
        }
    }

    public void writeColumn(String[] data) { // ecriture colonne pour la creation de table
        try (FileWriter writer = new FileWriter(tableFile, true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            for (int i = 0; i < data.length; i++) {
                String[] row = data[i].split(" ");
                bufferedWriter.write(row[0] + "(" + row[1] + ")" + "    ");
            }
            bufferedWriter.write("\n");
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }

    public Relation createRelation(String[] column) { // relation pour verif l'insertion
        Relation relation = new Relation();
        for (int i = 0; i < column.length; i++) {
            String[] row = column[i].split(" ");
            Class cl = setDomaine(row[1]);
            Attribut att = new Attribut(row[0], cl);
            relation.addAttribut(att);
        }
        return relation;
    }

    public Class<?> setDomaine(String attr) { // change le domaine en class compatible au java
        Class<?> result = null;
        if ("int".equalsIgnoreCase(attr)) {
            result = Integer.class;
        } else if ("boolean".equalsIgnoreCase(attr)) {
            result = Boolean.class;
        } else if ("double".equalsIgnoreCase(attr)) {
            result = Double.class;
        } else if ("float".equalsIgnoreCase(attr)) {
            result = Float.class;
        } else if ("long".equalsIgnoreCase(attr)) {
            result = Long.class;
        } else if ("string".equalsIgnoreCase(attr) || "date".equalsIgnoreCase(attr)) {
            result = String.class;
        } else {
            throw new IllegalArgumentException("Type non pris en charge : " + attr);
        }
        return result;
    }
    // create table

    // relation constructor
    public Relation getFile() { // construction du file en relation avec nuplet
        Relation relation = new Relation();
        if (!tableFile.exists()) {
            System.out.println("Le fichier n'existe pas.");
            return null;
        }
        try (Scanner scanner = new Scanner(tableFile)) {
            if (scanner.hasNextLine()) {
                String firstLine = scanner.nextLine();
                String[] columns = firstLine.split("    ");

                for (String column : columns) {
                    String[] col = column.replace(")", "").split("\\(");
                    if (col.length == 2) {
                        String columnName = col[0].trim();
                        String columnType = col[1].trim();
                        Class<?> type = setDomaine(columnType);
                        Attribut attribut = new Attribut(columnName, type);
                        relation.addAttribut(attribut);
                        // System.out.println(attribut.getName()+" : "+attribut.getDomaine()+"ok");
                    }
                }
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split("\\s+");

                Vector<Object> nuplet = new Vector<>();

                for (int i = 0; i < values.length; i++) {
                    Class<?> type = relation.getAttribut().get(i).getDomaine();
                    Object value = castValue(values[i], type);
                    nuplet.add(value);
                }
                relation.addNUplet(nuplet);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement : " + e.getMessage());
        }
        // System.out.println(relation.getAttribut()+"ok");
        // System.out.println(relation.getnUplet()+"ok");
        return relation;
    }

    public Relation getRelation() { // construction du file en relation sans nuplet
        Relation relation = new Relation();

        if (!tableFile.exists()) {
            System.out.println("Le fichier n'existe pas.");
            return null;
        }

        try (Scanner scanner = new Scanner(tableFile)) {
            if (scanner.hasNextLine()) {
                String firstLine = scanner.nextLine();
                String[] columns = firstLine.split("    ");

                for (String column : columns) {
                    String[] col = column.replace(")", "").split("\\(");
                    if (col.length == 2) {
                        String columnName = col[0].trim();
                        String columnType = col[1].trim();
                        Class<?> type = setDomaine(columnType);
                        Attribut attribut = new Attribut(columnName, type);
                        relation.addAttribut(attribut);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement : " + e.getMessage());
            e.printStackTrace();
        }

        return relation;
    }

    public Object castValue(String value, Class<?> type) {
        if (type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == Double.class) {
            return Double.parseDouble(value);
        } else if (type == Boolean.class) {
            return Boolean.parseBoolean(value); // Convertit en boolean
        } else if (type == String.class) {
            return value.trim(); // Supprime les espaces inutiles pour les chaînes
        } else {
            throw new IllegalArgumentException("Type non pris en charge : " + type);
        }
    }

    // insert
    public void insertNUplet(Vector data) { // ecriture colonne pour la creation de table
        try (FileWriter writer = new FileWriter(tableFile, true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            for (int i = 0; i < data.size(); i++) {
                bufferedWriter.write(data.get(i) + "    ");
            }
            // bufferedWriter.write("\n");
            bufferedWriter.newLine();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }

    // delete et update
    public void deleteAllRows() throws IOException {
        File tempFile = new File(tableFile.getAbsolutePath() + "_temp");
        try (BufferedReader reader = new BufferedReader(new FileReader(tableFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String header = reader.readLine();
            if (header != null) {
                writer.write(header);
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println("Erreur pendant la lecture/écriture : " + e.getMessage());
        }
        if (!tableFile.delete()) {
            throw new IOException("Impossible de supprimer le fichier original.");
        }
        if (!tempFile.renameTo(tableFile)) {
            throw new IOException("Impossible de renommer le fichier temporaire.");
        }
    }

    public void deleteWithCondition(String columnName, String conditionValue) throws IOException {
        File tempFile = new File(tableFile.getAbsolutePath() + "_temp");

        try (BufferedReader reader = new BufferedReader(new FileReader(tableFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String header = reader.readLine();
            if (header != null) {
                writer.write(header);
                writer.newLine();
            }

            // Récupérer l'index de la colonne
            String[] columns = header.split("\\s+");
            int columnIndex = -1;
            for (int i = 0; i < columns.length; i++) {
                String[] columnDetails = columns[i].split("[()]");
                String columnNameFromHeader = columnDetails[0].trim(); // Nom de la colonne sans type
                if (columnNameFromHeader.equalsIgnoreCase(columnName)) {
                    columnIndex = i;
                    break;
                }
            }

            if (columnIndex == -1) {
                throw new IllegalArgumentException("La colonne spécifiée n'existe pas : " + columnName);
            }

            // Parcourir les lignes et supp (where column = conditionValue)
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("\\s+");
                if (!values[columnIndex].equals(conditionValue)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        if (!tableFile.delete()) {
            throw new IOException("Impossible de supprimer le fichier original.");
        }
        if (!tempFile.renameTo(tableFile)) {
            throw new IOException("Impossible de renommer le fichier temporaire.");
        }
    }

    public void updateAllRows(String tableName, Map<String, String> updateValues) throws IOException {
        File tempFile = new File(tableFile.getAbsolutePath() + "_temp");

        try (BufferedReader reader = new BufferedReader(new FileReader(tableFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String header = reader.readLine();
            writer.write(header);
            writer.newLine();

            String[] columns = header.split("\\s+");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("\\s+");

                // Update toutes les lignes
                for (Map.Entry<String, String> update : updateValues.entrySet()) {
                    String updateColumn = update.getKey();
                    String updateValue = update.getValue();
                    for (int i = 0; i < columns.length; i++) {
                        String[] columnDetails = columns[i].split("[()]");
                        String columnNameFromHeader = columnDetails[0].trim();
                        if (columnNameFromHeader.equalsIgnoreCase(updateColumn)) {
                            values[i] = String.format("%-10s", updateValue);
                            break;
                        }
                    }
                }
                writer.write(String.join("  ", values));
                writer.newLine();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        if (!tableFile.delete()) {
            throw new IOException("Impossible de supprimer le fichier original.");
        }
        if (!tempFile.renameTo(tableFile)) {
            tempFile.delete();
            throw new IOException("Impossible de renommer le fichier temporaire.");
        }
        System.out.println("Mise à jour de toutes les lignes effectuée.");
    }

    public void updateWithCondition(String tableName, Map<String, String> updateValues,
            String conditionColumn, String conditionValue) throws IOException {
        File tempFile = new File(tableFile.getAbsolutePath() + "_temp");

        try (BufferedReader reader = new BufferedReader(new FileReader(tableFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String header = reader.readLine();
            writer.write(header);
            writer.newLine();

            String[] columns = header.split("\\s+");
            int conditionColumnIndex = -1;
            int[] columnWidths = new int[columns.length];

            for (int i = 0; i < columns.length; i++) {
                columnWidths[i] = columns[i].length();
            }
            for (int i = 0; i < columns.length; i++) {
                String[] columnDetails = columns[i].split("[()]");
                String columnNameFromHeader = columnDetails[0].trim();
                if (columnNameFromHeader.equalsIgnoreCase(conditionColumn)) {
                    conditionColumnIndex = i;
                    break;
                }
            }

            if (conditionColumnIndex == -1) {
                throw new IllegalArgumentException("Colonne de condition non trouvée : " + conditionColumn);
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("\\s+");

                // Verif condition
                if (values[conditionColumnIndex].equals(conditionValue)) {
                    for (Map.Entry<String, String> update : updateValues.entrySet()) {
                        String updateColumn = update.getKey();
                        String updateValue = update.getValue();
                        // colonne à upd
                        for (int i = 0; i < columns.length; i++) {
                            String[] columnDetails = columns[i].split("[()]");
                            String columnNameFromHeader = columnDetails[0].trim();
                            if (columnNameFromHeader.equalsIgnoreCase(updateColumn)) {
                                values[i] = String.format("%-" + columnWidths[i] + "s", updateValue);
                                break;
                            }
                        }
                    }
                }

                String formattedLine = formatLine(values, columnWidths);
                writer.write(formattedLine);
                writer.newLine();
            }
            writer.close();
            reader.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

        if (tempFile.exists()) {
            if (tableFile.delete()) {
                if (!tempFile.renameTo(tableFile)) {
                    throw new IOException("Échec du renommage du fichier temporaire.");
                }
            } else {
                if (tempFile.renameTo(tableFile)) {
                    throw new IOException("Renommage du fichier temporaire.");
                }
                tempFile.delete();
                throw new IOException("Échec de la suppression du fichier original.");
            }
        }
        System.out.println("Update réussi");
    }

    public String formatLine(String[] values, int[] columnWidths) {
        StringBuilder formattedLine = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            formattedLine.append(String.format("%-" + columnWidths[i] + "s", values[i]));
        }
        return formattedLine.toString();
    }

}