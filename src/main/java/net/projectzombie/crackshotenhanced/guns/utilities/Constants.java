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
package net.projectzombie.crackshotenhanced.guns.utilities;

import com.shampaggon.crackshot.CSUtility;
import java.text.DecimalFormat;
import java.util.Random;

/**
 *
 * @author jesse
 */
public class Constants
{
    static public final double TPS = 19.00;
    static public final double DEFAULT_PLAYER_SPEED = 0.2;
    static public final Random RANDOM = new Random();

    static public final DecimalFormat FORMATTER = new DecimalFormat("#0.00");
    public static CSUtility CRACKSHOT = new CSUtility();
}
