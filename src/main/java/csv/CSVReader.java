/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author jbannon
 */
public final class CSVReader
{
    private static Plugin plugin = null;
    
    private final File file;
    private final ArrayList<ArrayList<String>> data;
    private final int rowCount;
    private final int columnCount;
    
    /**
     * Initializes the plugin which is needed to traverse the directories to
     * read the any CSVs.
     * @param pl CrackshotEnhanced plugin found in Main.
     */
    static public void initializePlugin(final Plugin pl)
    {
        plugin = pl;
        if (!pl.getDataFolder().exists())
            pl.getDataFolder().mkdir();
    }
    
    /**
     * Reads a CSV in the plugin data folder column-wise.
     * @param fileName Name of the file.
     * @param columnValues Name of each column.
     */
    public CSVReader(final String fileName,
                     final String[] columnValues)
    {
        this.file = new File(plugin.getDataFolder(), fileName);
        this.data = readData(true, 1, columnValues);
        if (data != null && data.get(0) != null)
        {
            rowCount = data.get(0).size();
            columnCount = columnValues.length;
        }
        else
        {
            writeBlankCSV(columnValues);
            rowCount = 0;
            columnCount = 0;
        }
    }
    
    /**
     * Reads a CSV in the plugin data folder row-wise.
     * @param fileName Name of the file.
     * @param linesToSkip Number of lines to skip when reading the CSV.
     * @param template If the file doesn't exist, use template to write the default CSV.
     */
    public CSVReader(final String fileName,
                     final int linesToSkip,
                     final String template)
    {
        this.file = new File(plugin.getDataFolder(), fileName);
        this.data = readData(false, linesToSkip, null);
        this.columnCount = -1;
        if (data != null && !data.isEmpty() && data.get(0) != null && !data.get(0).isEmpty())
        {
            this.rowCount = data.size();
        }
        else
        {
            writeBlankCSV(template);
            this.rowCount = 0;
        }
    }
    
    /**
     * @return Row count of the CSV.
     */
    public int getRowCount()
    {
        return rowCount;
    }
    
    /**
     * Reads in data from a CSV and stores it in a nested ArrayList of Strings.
     * Each inner ArrayList represents either a column (if column-wise) or represents
     * a row (if row-wise).
     * @param columnWise Whether to read the data column-wise.
     * @param linesToSkip Lines to skip in the CSV before reading data.
     * @param columnValues
     * @return Nested ArrayList of data in String format.
     */
    private ArrayList<ArrayList<String>> readData(final boolean columnWise,
                                                  final int linesToSkip,
                                                  final String[] columnValues)
    {
        if (file == null || !file.exists() || !file.canRead())
            return null;
        
        try
        {
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            final ArrayList<String> lines = new ArrayList<>();
            int lineCount = 0;
            String line;
            
            while ((line = reader.readLine()) != null)
            {
                if (lineCount++ >= linesToSkip)
                    lines.add(line);
            }
            reader.close();
            
            if (columnWise)
                return getColumnData(columnValues, lines);
            else
                return getRowData(lines);
        }
        catch (Exception ex)
        {
            Logger.getLogger(CSVReader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    }
    
    private ArrayList<ArrayList<String>> getColumnData(final String[] columnValues,
                                                       final ArrayList<String> lines)
    {   
        final ArrayList<ArrayList<String>> toRet = new ArrayList<>();
        String lineValues[];
        
        for (int i = 0; i < columnValues.length; i++)
        {
            toRet.add(new ArrayList<String>());
        }
        
        for (String line : lines)
        {
            lineValues = line.split(",");
            for (int i = 0; i < columnValues.length; i++)
            {
                if (i < lineValues.length)
                    toRet.get(i).add(lineValues[i]);
                else
                    toRet.get(i).add(null);
            }
        }
        return toRet;
    }
    
    private ArrayList<ArrayList<String>> getRowData(final ArrayList<String> lines)
    {
        final ArrayList<ArrayList<String>> toRet = new ArrayList<>();
        String lineValues[];
        
        for (int i = 0; i < lines.size(); i++)
        {
            toRet.add(new ArrayList<String>());
            
            lineValues = lines.get(i).split(",");
            toRet.get(i).addAll(Arrays.asList(lineValues));
        }
        
        return toRet;
    }
    
    public String getString(final int rowValue,
                             final int colValue)
    {
        final String toRet = getValue(rowValue, colValue);
        if (toRet != null)
            return toRet;
        else
            return null;
    }
    
    public Double getDouble(final int rowValue,
                             final int colValue)
    {
        final String toRet = getValue(rowValue, colValue);
        if (toRet != null)
            return Double.valueOf(toRet);
        else
            return null;
    }
    
    public Integer getInt(final int rowValue,
                           final int colValue)
    {
        final String toRet = getValue(rowValue, colValue);
        if (toRet != null)
            return Integer.valueOf(toRet);
        else
            return null;
    }
    
    public Boolean getBool(final int rowValue,
                            final int colValue)
    {
        final String toRet = getValue(rowValue, colValue);
        if (toRet != null)
            return Boolean.valueOf(toRet);
        else
            return null;
    }
    
    private String getValue(final int rowValue,
                            final int colValue)
    {
        if (!isValidColumn(colValue))
            return null;
        
        return data.get(colValue).get(rowValue);
    }
    
    public String[] getColumnString(final int columnNumber)
    {
        String temp;
        if (!isValidColumn(columnNumber))
            return null;
        
        final String[] toRet = new String[rowCount];
        for (int i = 0; i < rowCount; i++)
        {
            temp = data.get(columnNumber).get(i);
            if (temp.isEmpty() || temp.equalsIgnoreCase("null") || temp.equalsIgnoreCase("n/a") || temp.equalsIgnoreCase("na"))
                toRet[i] = null;
            else
                toRet[i] = temp;
        }
        return toRet;
    }
    
    public int[] getColumnInt(final int columnNumber)
    {
        if (!isValidColumn(columnNumber))
            return null;
        
        final int[] toRet = new int[rowCount];
        for (int i = 0; i < rowCount; i++)
        {
            toRet[i] = Integer.valueOf(data.get(columnNumber).get(i));
        }
        return toRet;
    }
    
    public double[] getColumnDouble(final int columnNumber)
    {
        if (!isValidColumn(columnNumber))
            return null;
        
        final double[] toRet = new double[rowCount];
        for (int i = 0; i < rowCount; i++)
        {
            toRet[i] = Double.valueOf(data.get(columnNumber).get(i));
        }
        return toRet;
    }
    
    public boolean[] getColumnBoolean(final int columnNumber)
    {
        if (!isValidColumn(columnNumber))
            return null;
        
        final boolean[] toRet = new boolean[rowCount];
        for (int i = 0; i < rowCount; i++)
        {
            toRet[i] = Boolean.valueOf(data.get(columnNumber).get(i));
        }
        return toRet;
    }
    
    public String[] getRowString(final int rowNumber)
    {
        if (!isValidRow(rowNumber))
            return null;
        
        final ArrayList<String> rowData = data.get(rowNumber);
        final String[] toRet = new String[rowData.size() - 1];
        for (int i = 0; i < rowData.size() - 1; i++)
        {
            toRet[i] = rowData.get(i + 1);
        }
        return toRet;
    }
    
    private boolean isValidColumn(final int columnNumber)
    {
        return data != null
                && columnNumber >= 0 
                && columnNumber < columnCount
                && data.get(columnNumber) != null;
    }
    
    private boolean isValidRow(final int rowNumber)
    {
        return data != null
                && rowNumber >= 0
                && rowNumber < rowCount
                && data.get(rowNumber) != null
                && data.get(rowNumber).size() >= 1;
    }
    
    public boolean writeBlankCSV(final String[] values)
    {
        try {
            final FileWriter fw = new FileWriter(file);
            for (String str : values)
            {
                fw.write(str);
                fw.write(",");
            }
            fw.flush();
            fw.close();
            return true;
        }
        catch (IOException ex)
        {
            Logger.getLogger(CSVReader.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean writeBlankCSV(final String template)
    {
        try {
            final FileWriter fw = new FileWriter(file);
            fw.write(template);
            fw.flush();
            fw.close();
            return true;
        }
        catch (IOException ex)
        {
            Logger.getLogger(CSVReader.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
        
}
