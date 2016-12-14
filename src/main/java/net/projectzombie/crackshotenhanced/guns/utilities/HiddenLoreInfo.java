/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.utilities;

import net.projectzombie.crackshotenhanced.main.Main;

import java.util.Arrays;

/**
 *
 * @author jb
 */
public abstract class HiddenLoreInfo
{
    public static final String VERIFY = "PZ";
    public static final String SEP = "`";
    
    private final String[] info;
    private final boolean isHiddenLore;
    
    /**
     * Creates hidden lore from a string array. Puts verification in 0th index.
     * @param info 
     */
    public HiddenLoreInfo(final String[] info)
    {
        this.isHiddenLore = info != null;
        if (isHiddenLore) {
            this.info = new String[info.length + 1];
            this.info[0] = VERIFY;
            System.arraycopy(info, 0, this.info, 1, info.length);
        } else {
            this.info = null;
        }
    }
    
    public HiddenLoreInfo(final String encodedString)
    {
        if (encodedString != null && HiddenStringUtils.hasHiddenString(encodedString))
        {
            this.info = HiddenStringUtils.extractHiddenString(encodedString)
                .replaceFirst(SEP, "")
                .split(SEP);
            this.isHiddenLore = info.length >= 1 && info[0].equals(VERIFY);
        } 
        else
        {
            this.info = null;
            this.isHiddenLore = false;
        }
    }
    
    public boolean isValid()
    {
        return isHiddenLore;
    }
    
    public String getInfoStr(final int idx)
    {
        if (isValidIndex(idx))
            return info[idx];
        else
            return null;
    }
    
    public int getInfoInt(final int idx)
    {
        if (isValidIndex(idx))
            return Integer.valueOf(info[idx]);
        else
            return -1;
    }
    
    public int getLength()
    {
        return info.length;
    }
    
    public void setInfoStr(final int idx,
                           final String str)
    {
        if (isValidIndex(idx))
            info[idx] = str;
    }
    
    public void setInfoInt(final int idx,
                           final int infoInt)
    {
        if (isValidIndex(idx))
            info[idx] = String.valueOf(infoInt);
    }
    
    public String getHiddenInfo()
    {
        final StringBuilder stb = new StringBuilder();
        for (String inf : info)
        {
            stb.append(SEP);
            stb.append(inf);
        }
        return HiddenStringUtils.encodeString(stb.toString());
    }
    
    private boolean isValidIndex(final int idx)
    {
        return info != null && idx >= 0 && idx < info.length;
    }
}
