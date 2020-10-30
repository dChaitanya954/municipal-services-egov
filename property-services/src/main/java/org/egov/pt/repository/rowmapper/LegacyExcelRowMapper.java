package org.egov.pt.repository.rowmapper;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.egov.pt.models.excel.CellAnnotation;
import org.egov.pt.models.excel.LegacyRow;
import org.egov.pt.models.excel.RowExcel;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Component
public class LegacyExcelRowMapper {


	public LegacyRow map(RowExcel source) throws Exception {
		LegacyRow row = new LegacyRow();
		Map<Integer, Cell> cells = source.getCells();
		for (Field f: LegacyRow.class.getDeclaredFields()) {
			CellAnnotation column = f.getAnnotation(CellAnnotation.class);
			Cell cell  = cells.get(column.index());
			if(cell != null && cell.getCellType().equals(CellType.STRING)){
				PropertyUtils.setProperty(row, f.getName(), cell.getStringCellValue());
			} else if(cell != null && cell.getCellType().equals(CellType.NUMERIC)){
				PropertyUtils.setProperty(row, f.getName(), String.valueOf(cell.getNumericCellValue()));
			}

		}
		return row;
	}

}