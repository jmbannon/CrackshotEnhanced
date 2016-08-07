/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv;

/**
 *
 * @author Jesse Bannon
 */
public abstract class CSVValue
{
    private final String name;
    private final int index;
    
    public CSVValue(final int index,
                    final String name)
    {
        this.name = name;
        this.index = index;
    }
    
    public int getIndex()
    {
        return index;
    }
    
    public String getName()
    {
        return name;
    }
    
    @Override
    public String toString()
    {
        return name;
    }
}
