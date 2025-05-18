package api.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class XLUtility {

	public FileInputStream fi;
	public FileOutputStream fo;
	public  XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	String path;
	
	public XLUtility(String   path)
	{
		this.path=path;
	}
	
	//get the no of rows 
	public int getRowCount(String sheetname) throws IOException
	{
		fi= new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetname);
		int rowCount=sheet.getLastRowNum();
		workbook.close();
		fi.close();
		return rowCount;
	}
	//get the no of cell in the row  
	public int getCellCount(String sheetName,int rownum) throws IOException
	{
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		row =sheet.getRow(rownum);
		int Cellcount = row.getLastCellNum();
		workbook.close();
		fi.close();
		return Cellcount;	
	}
	//get the cell data
	public String getCellData(String sheetName,int rowCount,int colnum) throws IOException
	{
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		row =sheet.getRow(rowCount);
		cell= row.getCell(colnum);
		
		DataFormatter formatter = new DataFormatter();
		String data;
		try {
			data=formatter.formatCellValue(cell); // return the formatted value of the cell
			
		}catch(Exception e)
		{
			data="";
		}
		workbook.close();
		fi.close();
		return data;
	}
	//it will go and write the data in the excell sheet
	public void setCellData(String sheetName,int rownum,int colnum,String data) throws IOException
	{
		File xlfile=new File(path);
		if(!xlfile.exists())  //is the file is not exist,then create a new file
		{
			workbook = new XSSFWorkbook();
			fo=new FileOutputStream(path);
			workbook.write(fo);	
		}
		
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		
		if(workbook.getSheetIndex(sheetName)==-1)  // if the sheet is not exist,then create a new sheet
			workbook.createSheet(sheetName);
	    sheet=workbook.getSheet(sheetName);
	    
	    if(sheet.getRow(rownum)==null) // if row not exist,then create new Row
	    	sheet.createRow(rownum);
	    row=sheet.getRow(rownum);
	    
	    cell=row.createCell(colnum);
	    cell.setCellValue(data);
	    fo=new FileOutputStream(path);
	    workbook.write(fo);
	    fi.close();
	    fo.close();
	}
	
	
	
	
	
}
