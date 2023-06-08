import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelReader {

    private static final ArrayList<Result> results;

    static {
        try {
            results = readFromExcel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Result> getResults() {
        return results;
    }

    /**
     * Метод, записывающий в excel файл отсортированные по убыванию результаты
     * @param results - отсортированная коллекция из объектов result, включающих в себя имя и очки игрока
     * @throws IOException вызывается при невозможности найти excel файл
     */
    public static void writeToExcel(ArrayList<Result> results) throws IOException {
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("Результаты ТОП 10");
        XSSFRow r = sheet.createRow(0);
        r.createCell(0).setCellValue("№");
        r.createCell(1).setCellValue("Имя");
        r.createCell(2).setCellValue("Количество баллов");
        for (int i = 0; i < results.size(); i++) {
            if (i < 10) {
                XSSFRow r2 = sheet.createRow(i + 1);
                r2.createCell(0).setCellValue(i + 1);
                r2.createCell(1).setCellValue(results.get(i).getName());
                 r2.createCell(2).setCellValue(results.get(i).getPoints());
            }
        }
        File f = new File("./Results.xlsx");
        book.write(new FileOutputStream(f));
        book.close();
    }

    /**
     * Метод, считывающий данные из excel файла и записывающий эти данные в коллекцию объектов result
     * @return коллекция объектов result
     * @throws IOException при невозможности найти excel файл
     */
    public static ArrayList<Result> readFromExcel() throws IOException {
        try (XSSFWorkbook book = new XSSFWorkbook("src/main/resources/Results.xlsx")) {
            XSSFSheet sheet = book.getSheetAt(0);
            ArrayList<Result> results = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                results.add(new Result(sheet.getRow(i).getCell(1).getStringCellValue(),
                        (int) sheet.getRow(i).getCell(2).getNumericCellValue()));
            }
            return results;
        } catch (NullPointerException e) {
            System.out.println("Empty file of records");
        }
        return null;
    }
}