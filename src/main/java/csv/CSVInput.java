/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv;

import java.util.ArrayList;

/**
 *
 * @author Jesse Bannon
 * @param <T> The CSVValue type read from a CSV.
 */
public abstract class CSVInput<T extends CSVValue>
{
    private final String CSVName;
    private final String[] columnNames;
    private final String template;
    private final T[] readValues;
    
    public CSVInput(final String CSVName,
                    final T[] readValues,
                    final String[] columnNames)
    {
        this.CSVName = CSVName;
        this.readValues = readValues;
        this.columnNames = columnNames;
        this.template = null;
    }
    
    public String getCSVName()
    {
        return CSVName;
    }
    
    /**
     * CSVInput constructor for row-wise spreadsheets.
     * @param CSVName Name of the CSV file.
     * @param readValues Values read from CSV.
     * @param template Top line(s) of the CSV to write to if it doesn't exist.
     */
    public CSVInput(final String CSVName,
                    final T[] readValues,
                    final String template)
    {
        this.CSVName = CSVName;
        this.readValues = readValues;
        this.columnNames = null;
        this.template = template;
    }
    
    /**
     * @return Returns all initialized CSVValues.
     */
    public T[] getAll()
    {
        return readValues;
    }
    
    /**
     * @return Returns the null value of the CSVValue. More-so for gun modifiers to represent the empty
     * element in the set.
     */
    public T getNullValue()
    {
        return null;
    }
    
    /**
     * @param uniqueID ID or index of the element.
     * @return Element with the array at index of uniqueID.
     */
    public T get(final int uniqueID)
    {
        if (uniqueID >= 0 && uniqueID < readValues.length)
            return readValues[uniqueID];
        else
            return null;
    }
    
    /**
     * @param name Name of the CSVValue.
     * @return The element if it has a similar name.
     */
    public T get(final String name)
    {
        for (T temp : readValues)
        {
            if (temp.getName().equalsIgnoreCase(name))
            {
                return temp;
            }
        }
        return null;
    }
    
    /**
     * @param names Array of names of the CSVValues.
     * @param includeNull Whether or not to include the null element in the returned set.
     * @return An ArrayList of CSVValues that any of the names given.
     */
    public ArrayList<T> get(final String[] names,
                            final boolean includeNull)
    {
        final ArrayList<T> matchValues = new ArrayList<>();
        String tempName;
        
        if (includeNull)
            matchValues.add(getNullValue());
        
        if (names == null || names.length == 0)
            return matchValues;
        
        for (String name : names)
        {
            for (T temp : readValues)
            {
                if (temp == null)
                {
                    if (name == null || name.isEmpty() || name.equalsIgnoreCase("null"))
                    {
                        matchValues.add(temp);
                        break;
                    }
                }
                else
                {
                    tempName = temp.getName();
                    if (tempName != null && tempName.equalsIgnoreCase(name))
                    {
                        matchValues.add(temp);
                        break;
                    }
                }
            }
        }
        
        return matchValues;
    }

    /**
     * @return The length of the initialized array of elements. -1 if null.
     */
    public int initialize()
    {
        if (readValues == null)
        {
            return -1;
        }
        else
        {
            return readValues.length;
        }
    }

    
}
