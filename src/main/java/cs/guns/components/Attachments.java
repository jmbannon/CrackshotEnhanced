/*
 * Copyright (C) 2016 jesse
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cs.guns.components;

import csv.CSVInput;
import cs.guns.components.AOEAttachments.AOEAttachment;
import cs.guns.components.Attachments.Attachment;
import cs.guns.components.ProjectileAttachments.ProjectileAttachment;

/**
 *
 * @author jesse
 */
public class Attachments extends CSVInput<Attachment>
{
    static private Attachments singleton = null;
            
    static public Attachments getInstance()
    {
        if (singleton == null)
            singleton = new Attachments();
        return singleton;
    }
    
    private Attachments()
    {
        super(null, buildAttatchments(), new String[] {});
    }
    
    @Override
    public Attachment getNullValue()
    {
        return ProjectileAttachments.getInstance().getNullValue();
    }
    
    static private Attachment[] buildAttatchments()
    {
        final ProjectileAttachment[] projAtts = ProjectileAttachments.getInstance().getAll();
        final Attachment[] toRet = new Attachment[projAtts.length];
        
        int i = 0;
        for (ProjectileAttachment projAtt : projAtts)
        {
            toRet[i++] = projAtt;
        }
        
        return toRet;
    }

    static public abstract class Attachment extends GunModifier
    {

        public Attachment(final int uniqueID,
                           final String name,
                           final String material,
                           final int materialData,
                           final int price,
                           final String color)
        {
            super(uniqueID, name, material, materialData, price, color);
        }
        
        abstract public boolean isAOE();
    }
}
