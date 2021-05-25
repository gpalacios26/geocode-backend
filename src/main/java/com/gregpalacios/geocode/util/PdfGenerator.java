package com.gregpalacios.geocode.util;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gregpalacios.geocode.model.Incidencia;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PdfGenerator {

	private static final Logger logger = LoggerFactory.getLogger(PdfGenerator.class);

	public static ByteArrayInputStream incidenciaToPdf(Incidencia incidencia) throws IOException {
		Document document = new Document(PageSize.A4, 36, 36, 90, 36);
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			PdfWriter writer = PdfWriter.getInstance(document, out);

			HeaderFooterPdf event = new HeaderFooterPdf();
			writer.setPageEvent(event);

			Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			titleFont.setSize(18);
			titleFont.setColor(Color.WHITE);

			PdfPTable titulo = new PdfPTable(1);
			titulo.setWidthPercentage(90);

			PdfPCell cell_0;
			cell_0 = new PdfPCell(new Phrase(""));
			cell_0.setMinimumHeight(20);
			cell_0.setBorder(0);

			PdfPCell cell;
			cell = new PdfPCell(new Phrase("REPORTE DE INCIDENCIA", titleFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingLeft(5);
			cell.setBackgroundColor(Color.RED);
			cell.setMinimumHeight(40);
			cell.setBorder(0);

			titulo.addCell(cell_0);
			titulo.addCell(cell);
			titulo.addCell(cell_0);

			Font tableHeadFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			PdfPTable contenido = new PdfPTable(2);
			contenido.setWidthPercentage(90);
			contenido.setWidths(new int[] { 2, 3 });

			PdfPCell cell_1;
			cell_1 = new PdfPCell(new Phrase("Código", tableHeadFont));
			cell_1.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell_1.setPaddingLeft(5);
			cell_1.setBackgroundColor(Color.LIGHT_GRAY);
			contenido.addCell(cell_1);

			cell_1 = new PdfPCell(new Phrase(incidencia.getIdIncidencia().toString()));
			cell_1.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell_1.setPaddingLeft(5);
			contenido.addCell(cell_1);

			PdfPCell cell_2;
			cell_2 = new PdfPCell(new Phrase("Evento", tableHeadFont));
			cell_2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell_2.setPaddingLeft(5);
			cell_2.setBackgroundColor(Color.LIGHT_GRAY);
			contenido.addCell(cell_2);

			cell_2 = new PdfPCell(new Phrase(incidencia.getEvento().getDescripcion()));
			cell_2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell_2.setPaddingLeft(5);
			contenido.addCell(cell_2);

			PdfPCell cell_3;
			cell_3 = new PdfPCell(new Phrase("Descripción", tableHeadFont));
			cell_3.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell_3.setPaddingLeft(5);
			cell_3.setBackgroundColor(Color.LIGHT_GRAY);
			contenido.addCell(cell_3);

			cell_3 = new PdfPCell(new Phrase(incidencia.getDescripcion()));
			cell_3.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell_3.setPaddingLeft(5);
			contenido.addCell(cell_3);

			PdfPCell cell_4;
			cell_4 = new PdfPCell(new Phrase("Dirección", tableHeadFont));
			cell_4.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell_4.setPaddingLeft(5);
			cell_4.setBackgroundColor(Color.LIGHT_GRAY);
			contenido.addCell(cell_4);

			cell_4 = new PdfPCell(new Phrase(incidencia.getDireccion()));
			cell_4.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell_4.setPaddingLeft(5);
			contenido.addCell(cell_4);

			document.open();
			document.add(titulo);
			document.add(contenido);
			document.close();

		} catch (DocumentException ex) {

			logger.error("Error occurred: {0}", ex);
		}

		return new ByteArrayInputStream(out.toByteArray());
	}
}
