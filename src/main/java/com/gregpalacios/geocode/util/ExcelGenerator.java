package com.gregpalacios.geocode.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import com.gregpalacios.geocode.model.Contacto;
import com.gregpalacios.geocode.model.Usuario;

public class ExcelGenerator {

	public static ByteArrayInputStream usuariosToExcel(List<Usuario> usuarios) throws IOException {
		String[] COLUMNs = { "ID", "Nombres", "Apellidos", "Correo", "Usuario", "Estado" };
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet("Usuarios");
			
            File file = new ClassPathResource("static/img/logo.png").getFile();
            InputStream inputStream = new FileInputStream(file);
            byte[] bytes = IOUtils.toByteArray(inputStream);
            int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();
            
            CreationHelper helper = workbook.getCreationHelper();
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(0);
            anchor.setRow1(0);
          
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            pict.resize(2,1);
			
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setColor(IndexedColors.WHITE.getIndex());
            titleFont.setFontHeightInPoints((short) 14);
            
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.WHITE.getIndex());
			headerFont.setFontHeightInPoints((short) 12);
			
			Font bodyFont = workbook.createFont(); 
			bodyFont.setBold(false);
			bodyFont.setColor(IndexedColors.BLACK.getIndex());
			bodyFont.setFontHeightInPoints((short) 10);
			
			CellStyle imageCellStyle = workbook.createCellStyle();
			imageCellStyle.setFont(titleFont);
			imageCellStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
			imageCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			imageCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			imageCellStyle.setAlignment(HorizontalAlignment.LEFT);
			
			CellStyle titleCellStyle = workbook.createCellStyle();
			titleCellStyle.setFont(headerFont);
			titleCellStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
			titleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
            
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setBorderLeft(BorderStyle.THIN);
            headerCellStyle.setBorderRight(BorderStyle.THIN);
            headerCellStyle.setBorderTop(BorderStyle.THIN);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);
            headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
            headerCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
            headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            
            CellStyle BodyCellStyle = workbook.createCellStyle();
            BodyCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);
            BodyCellStyle.setAlignment(HorizontalAlignment.JUSTIFY);
            BodyCellStyle.setFont(bodyFont);
            BodyCellStyle.setBorderLeft(BorderStyle.THIN);
            BodyCellStyle.setBorderRight(BorderStyle.THIN);
            BodyCellStyle.setBorderTop(BorderStyle.THIN);
            BodyCellStyle.setBorderBottom(BorderStyle.THIN);
            BodyCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            BodyCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
            BodyCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
            BodyCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            
            Row rowImagen = sheet.createRow(0);
            rowImagen.setHeightInPoints(30);
            Cell cell_Imagen = rowImagen.createCell(0);
            cell_Imagen.setCellStyle(imageCellStyle);
            
            Cell cell_App = rowImagen.createCell(2);
            cell_App.setCellValue("Sistema GeoCode");
            cell_App.setCellStyle(imageCellStyle);
            
            Row rowTitulo = sheet.createRow(1);
            rowTitulo.setHeightInPoints(30);
            Cell cell_Title = rowTitulo.createCell(0);
            cell_Title.setCellValue("LISTADO DE USUARIOS");
            cell_Title.setCellStyle(titleCellStyle);
            
            sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$B$1"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$C$1:$F$1"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$A$2:$F$2"));

			Row headerRow = sheet.createRow(3);

			// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

			// Content
			int rowIdx = 4;
			for (Usuario usuario : usuarios) {
				Row row = sheet.createRow(rowIdx++);

				Cell cell_1 = row.createCell(0);
				cell_1.setCellValue(usuario.getIdUsuario());
				cell_1.setCellStyle(BodyCellStyle);
				
				Cell cell_2 = row.createCell(1);
				cell_2.setCellValue(usuario.getNombres());
				cell_2.setCellStyle(BodyCellStyle);
				
				Cell cell_3 = row.createCell(2);
				cell_3.setCellValue(usuario.getApellidos());
				cell_3.setCellStyle(BodyCellStyle);
				
				Cell cell_4 = row.createCell(3);
				cell_4.setCellValue(usuario.getCorreo());
				cell_4.setCellStyle(BodyCellStyle);
				
				Cell cell_5 = row.createCell(4);
				cell_5.setCellValue(usuario.getUsername());
				cell_5.setCellStyle(BodyCellStyle);
				
				String estado = (usuario.isEnabled()) ? "Activo" : "Inactivo";
				Cell cell_6 = row.createCell(5);
				cell_6.setCellValue(estado);
				cell_6.setCellStyle(BodyCellStyle);
			}

			// Resize all columns to fit the content size
			for (int i = 0; i < COLUMNs.length; i++) {
				sheet.autoSizeColumn(i);
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}
	
	public static ByteArrayInputStream contactosToExcel(List<Contacto> contactos) throws IOException {
		String[] COLUMNs = { "ID", "Nombres", "Apellidos", "Celular", "Correo" };
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet("Contactos");
			
			File file = new ClassPathResource("static/img/logo.png").getFile();
            InputStream inputStream = new FileInputStream(file);
            byte[] bytes = IOUtils.toByteArray(inputStream);
            int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();
            
            CreationHelper helper = workbook.getCreationHelper();
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(0);
            anchor.setRow1(0);
          
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            pict.resize(2,1);
			
			Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setColor(IndexedColors.WHITE.getIndex());
            titleFont.setFontHeightInPoints((short) 14);
            
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.WHITE.getIndex());
			headerFont.setFontHeightInPoints((short) 12);
			
			Font bodyFont = workbook.createFont(); 
			bodyFont.setBold(false);
			bodyFont.setColor(IndexedColors.BLACK.getIndex());
			bodyFont.setFontHeightInPoints((short) 10);
			
			CellStyle imageCellStyle = workbook.createCellStyle();
			imageCellStyle.setFont(titleFont);
			imageCellStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
			imageCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			imageCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			imageCellStyle.setAlignment(HorizontalAlignment.LEFT);
			
			CellStyle titleCellStyle = workbook.createCellStyle();
			titleCellStyle.setFont(headerFont);
			titleCellStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
			titleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
            
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setBorderLeft(BorderStyle.THIN);
            headerCellStyle.setBorderRight(BorderStyle.THIN);
            headerCellStyle.setBorderTop(BorderStyle.THIN);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);
            headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
            headerCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
            headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            
            CellStyle BodyCellStyle = workbook.createCellStyle();
            BodyCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);
            BodyCellStyle.setAlignment(HorizontalAlignment.JUSTIFY);
            BodyCellStyle.setFont(bodyFont);
            BodyCellStyle.setBorderLeft(BorderStyle.THIN);
            BodyCellStyle.setBorderRight(BorderStyle.THIN);
            BodyCellStyle.setBorderTop(BorderStyle.THIN);
            BodyCellStyle.setBorderBottom(BorderStyle.THIN);
            BodyCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            BodyCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
            BodyCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
            BodyCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            
            Row rowImagen = sheet.createRow(0);
            rowImagen.setHeightInPoints(30);
            Cell cell_Imagen = rowImagen.createCell(0);
            cell_Imagen.setCellStyle(imageCellStyle);
            
            Cell cell_App = rowImagen.createCell(2);
            cell_App.setCellValue("Sistema GeoCode");
            cell_App.setCellStyle(imageCellStyle);
            
            Row rowTitulo = sheet.createRow(1);
            rowTitulo.setHeightInPoints(30);
            Cell cell_Title = rowTitulo.createCell(0);
            cell_Title.setCellValue("LISTADO DE CONTACTOS DE EMERGENCIA");
            cell_Title.setCellStyle(titleCellStyle);
            
            sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$B$1"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$C$1:$E$1"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$A$2:$E$2"));

			Row headerRow = sheet.createRow(3);

			// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

			// Content
			int rowIdx = 4;
			for (Contacto contacto : contactos) {
				Row row = sheet.createRow(rowIdx++);

				Cell cell_1 = row.createCell(0);
				cell_1.setCellValue(contacto.getIdContacto());
				cell_1.setCellStyle(BodyCellStyle);
				
				Cell cell_2 = row.createCell(1);
				cell_2.setCellValue(contacto.getNombres());
				cell_2.setCellStyle(BodyCellStyle);
				
				Cell cell_3 = row.createCell(2);
				cell_3.setCellValue(contacto.getApellidos());
				cell_3.setCellStyle(BodyCellStyle);
				
				Cell cell_4 = row.createCell(3);
				cell_4.setCellValue(contacto.getCelular());
				cell_4.setCellStyle(BodyCellStyle);
				
				Cell cell_5 = row.createCell(4);
				cell_5.setCellValue(contacto.getCorreo());
				cell_5.setCellStyle(BodyCellStyle);
			}

			// Resize all columns to fit the content size
			for (int i = 0; i < COLUMNs.length; i++) {
				sheet.autoSizeColumn(i);
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}
}
