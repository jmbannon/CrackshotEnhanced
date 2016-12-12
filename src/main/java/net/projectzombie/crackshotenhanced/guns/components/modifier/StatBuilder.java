package net.projectzombie.crackshotenhanced.guns.components.modifier;

import net.projectzombie.crackshotenhanced.main.Main;
import org.bukkit.ChatColor;

import java.util.ArrayList;

import static net.projectzombie.crackshotenhanced.guns.utilities.Constants.FORMATTER;

/**
 * Created by jb on 11/25/16.
 */
public class StatBuilder {
    public static final String STAT_COLOR = ChatColor.GRAY.toString() + ChatColor.ITALIC.toString();
    public static final String TITLE_COLOR = ChatColor.RED.toString();
    public static final String STAT_TYPE_SEPERATOR = ChatColor.GRAY + "--------------------------------";
    public static final String STAT_SEPERATOR = ChatColor.DARK_GRAY + "---------------";

    private final ArrayList<String> lore;

    public StatBuilder() {
        lore = new ArrayList<>();
    }


    private boolean hasMultiplierEffect(final double multiplier) {
        return multiplier != 0.0 && multiplier != 1.0;
    }

    private boolean hasPercentageEffect(final double multiplier) {
        return multiplier != 0.0;
    }

    private boolean hasValueEffect(final int value) {
        return value != 0;
    }

    private boolean hasValueEffect(final double value) {
        return value != 0.0;
    }

    public void addMultiplierStat(final double multiplier,
                                  final String description) {
        lore.add(STAT_COLOR + "  " + numPercentage(multiplier) + " " + description);
    }

//    public void addMultiplierStat(final double multiplier,
//                                  final double offset,
//                                  final String description) {
//        addMultiplierStat(multiplier - offset, description);
//    }

    public void addPercentageStat(final double percentage,
                                  final String description) {
        lore.add(STAT_COLOR + "  " + numPercentage(percentage) + " " + description);
    }

    public void addValueStat(final double value,
                             final String description)
    {
        lore.add(STAT_COLOR + "  " + numValue(value) + " " + description);
    }

    public void addValueStat(final int value,
                             final String description) {
        lore.add(STAT_COLOR + "  " + numValue(value) + " " + description);
    }

    public void addBooleanStatIfTrue(final boolean value,
                                     final String description)
    {
        if (value)
            lore.add(STAT_COLOR + "  " + description);
    }


    public void addMultiplierStatIfValid(final double multiplier,
                                         final String description)
    {
        if (hasMultiplierEffect(multiplier))
            addMultiplierStat(multiplier, description);
    }

//    public void addMultiplierStatIfValid(final double multiplier,
//                                         final double offset,
//                                         final String description)
//    {
//        if (hasMultiplierEffect(multiplier))
//            addMultiplierStat(multiplier, offset, description);
//    }

    public void addPercentageStatIfValid(final double percentage,
                                         final String description)
    {
        if (hasPercentageEffect(percentage))
            addMultiplierStat(percentage, description);
    }

//    public void addPercentageStatIfValid(final double percentage,
//                                         final double offset,
//                                         final String description)
//    {
//        Main.info(description + " " + percentage);
//        if (hasPercentageEffect(percentage))
//            addMultiplierStat(percentage, offset, description);
//    }

    public void addValueStatIfValid(final double value,
                                    final String description) {
        if (hasValueEffect(value))
            addValueStat(value, description);
    }

    public void addValueStatIfValid(final int value,
                                    final String description) {
        if (hasValueEffect(value))
            addValueStat(value, description);
    }



    private String numPercentage(final double num)
    {
        return getSign(num - 1.0) + FORMATTER.format(100 * (num - 1.0)) + "%";
    }

    private String numValue(final double num) { return getSign(num) + FORMATTER.format(num); }

    private String numValue(final int num) { return getSign(num) + num; }


    /** @return '+' if positive, '' otherwise because negative values will include the negative symbol when printed. */
    static private String getSign(final double num)
    {
        if (num > 0.0)
            return "+";
        else
            return "";
    }

    static
    private String getSign(final int num)
    {
        if (num > 0)
            return "+";
        else if (num == 0)
            return "";
        else
            return "-";
    }

    public ArrayList<String> toArrayList() {
        return lore;
    }
}
