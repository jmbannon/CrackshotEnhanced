package net.projectzombie.crackshotenhanced.guns.qualities;

import net.projectzombie.crackshotenhanced.guns.utilities.GunUtils;
import net.projectzombie.crackshotenhanced.main.Main;
import net.projectzombie.crackshotenhanced.yaml.ModifierConfig;
import net.projectzombie.crackshotenhanced.yaml.ModifierMap;
import net.projectzombie.crackshotenhanced.yaml.ModifierValue;
import org.bukkit.ChatColor;

/**
 * Created by jb on 11/17/16.
 */
public class Qualities extends ModifierConfig<Qualities.Quality> {


    private static String DEFAULT_COLOR = ChatColor.AQUA.name();

    static private Qualities singleton = null;
    static public Qualities getInstance()
    {
        if (singleton == null)
            singleton = new Qualities();
        return singleton;
    }

    static private ModifierMap buildDefaultValues() {
        final ModifierMap defaultValues = new ModifierMap(MODULE_NAME);
        defaultValues.put("Color", "AQUA");
        return defaultValues;
    }

    static private final String YML_NAME = "Qualities.yml";
    static private final String MODULE_NAME = "Qualities";
    static private final String[] NECESSARY_VALUES = new String[] { "Display Name", "Probability" };
    static private final ModifierMap DEFAULT_VALUES = buildDefaultValues();

    public String getTitle() {
        return "Quality: ";
    }

    private Qualities() { super(YML_NAME, MODULE_NAME, NECESSARY_VALUES, DEFAULT_VALUES); }

    public Qualities.Quality buildModule(final int uniqueID, final ModifierMap values) {
        try {
            return new Qualities.Quality(
                    uniqueID,
                    values.getString("Display Name"),
                    values.getString("Color"),
                    values.getDouble("Probability")
            );
        } catch (Exception e) {
            Main.getPlugin().getLogger().warning("Cannot add quality " + values.getString("Display Name"));
            return null;
        }
    }

    private double getProbabilitySum() { return super.getAll().stream().mapToDouble(Quality::getSetProbability).sum(); }

    @Override
    public Qualities.Quality getNullValue()
    {
        return null;
    }

    public class Quality extends ModifierValue {

        private final ChatColor color;
        private final double probability;

        public Quality(final int index,
                       final String name,
                       final String color,
                       final double probability)
        {
            super(index, name);
            this.color = GunUtils.matchChatColor(color);
            this.probability = probability;
        }

        /** @return Probability set in the config. */
        private double getSetProbability() { return this.probability; }

        /** @return Probability between [0, 1] of this Quality scaled with respect to all other Qualities. */
        public double getProbability() { return this.probability / Qualities.getInstance().getProbabilitySum(); }

        public String getDisplayName(final boolean italics)
        {
            final String chatColor;
            if (color == null)
                chatColor = DEFAULT_COLOR;
            else
                chatColor = color.toString();

            if (super.getName() != null)
            {
                if (italics)
                    return chatColor + ChatColor.ITALIC.toString() + super.getName();
                else
                    return chatColor + super.getName();
            }
            else
            {
                if (italics)
                    return ChatColor.DARK_RED + ChatColor.ITALIC.toString() + "n/a";
                else
                    return ChatColor.DARK_RED + "n/a";
            }
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Quality && ((Quality)obj).getIndex() == this.getIndex();
        }
    }
}
