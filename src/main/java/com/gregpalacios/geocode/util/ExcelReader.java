package com.gregpalacios.geocode.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gregpalacios.geocode.model.Contacto;

public class ExcelReader {

	@SuppressWarnings("resource")
	public static List<Contacto> leerContactos(String rutaArchivo) throws IOException {
		
		File file = new File(rutaArchivo);
    	InputStream inputStream = new FileInputStream(file.getPath());
    	Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
        DataFormatter formatter = new DataFormatter();
        List<Contacto> lista = new ArrayList<Contacto>();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            int rowIndex = nextRow.getRowNum();
            if(rowIndex > 0) {
            	Contacto contacto = new Contacto();
            	Iterator<Cell> cellIterator = nextRow.cellIterator();
                while(cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int cellIndex = cell.getColumnIndex();
                    String contenidoCelda = formatter.formatCellValue(cell);
                    if(cellIndex == 0) {
                    	contacto.setNombres(contenidoCelda);
                    } else if(cellIndex == 1) {
                    	contacto.setApellidos(contenidoCelda);
                    } else if(cellIndex == 2) {
                    	contacto.setCelular(contenidoCelda);
                    } else if(cellIndex == 3) {
                    	contacto.setCorreo(contenidoCelda);
                    }
                }
                
                int error = 0;
                
                if(contacto.getNombres() == null || contacto.getNombres() == "") { error = error + 1; }
                if(contacto.getNombres().length() < 3) { error = error + 1; }
                
                if(contacto.getApellidos() == null || contacto.getApellidos() == "") { error = error + 1; }
                if(contacto.getApellidos().length() < 3) { error = error + 1; }
                
                if(contacto.getCelular() == null || contacto.getCelular() == "") { error = error + 1; }
                if(! validaCelular(contacto.getCelular())) { error = error + 1; }
                
                if(contacto.getCorreo() == null || contacto.getCorreo() == "") { error = error + 1; }
                if(! validaCorreo(contacto.getCorreo())) { error = error + 1; }
                
                if(error == 0) { lista.add(contacto); }
            }
        }
        
        return lista;
    }
	
	public static Boolean validaCelular(String celular) {
		
		Pattern pattern = Pattern.compile("[0-9]{9,9}$");
		Matcher matcher = pattern.matcher(celular);
		
		return matcher.matches();
	}
	
	public static Boolean validaCorreo(String correo) {
		
		Pattern pattern = Pattern.compile("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$");
		Matcher matcher = pattern.matcher(correo);
		
		return matcher.matches();
	}
}
