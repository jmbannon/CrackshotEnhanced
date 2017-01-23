package net.projectzombie.crackshotenhanced.resources.sounds;

import net.projectzombie.crackshotenhanced.main.Main;
import net.projectzombie.crackshotenhanced.yaml.ModifierConfig;
import net.projectzombie.crackshotenhanced.yaml.ModifierMap;
import net.projectzombie.crackshotenhanced.yaml.ModifierValue;
import org.bukkit.Sound;

import java.util.EnumSet;

/**
 * Created by jb on 1/22/17.
 */
public class SoundAliases extends ModifierConfig<SoundAliases.SoundAlias> {

    static private SoundAliases singleton = null;
    static public SoundAliases getInstance()
    {
        if (singleton == null) {
            singleton = new SoundAliases();
            singleton.postInitialize();
        }
        return singleton;
    }

    static private ModifierMap buildDefaultValues() {
        final ModifierMap defaultValues = new ModifierMap(MODULE_NAME);
        defaultValues.put("Volume", 1);
        defaultValues.put("Pitch", 0);
        defaultValues.put("Delay", 0);
        return defaultValues;
    }

    public SoundAlias buildModule(final String key, final int uniqueID, final ModifierMap values) {
        try {
            if (EnumSet.allOf(Sound.class).stream().filter(s -> s.name().equals(key)).count() == 1) {
                return new SoundAlias(
                        key,
                        uniqueID,
                        values.getString("Alias"),
                        values.getInt("Volume"),
                        values.getInt("Pitch"),
                        values.getInt("Delay")
                );
            } else {
                return null;
            }
        } catch (Exception e) {
            Main.getPlugin().getLogger().warning("Cannot add SoundAlias " + key + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    static private final String YML_NAME = "SoundAliases.yml";
    static private final String MODULE_NAME = "SoundAliases";
    static private final String[] NECESSARY_VALUES = new String[] { "Alias" };
    static private final ModifierMap DEFAULT_VALUES = buildDefaultValues();

    private SoundAliases() { super(YML_NAME, MODULE_NAME, NECESSARY_VALUES, DEFAULT_VALUES); }

    public class SoundAlias extends ModifierValue {

        private final int volume;
        private final int pitch;
        private final int delay;

        public SoundAlias(final String key,
                          final int index,
                          final String name,
                          final int volume,
                          final int pitch,
                          final int delay) {
            super(key, index, name);
            this.volume = volume;
            this.pitch = pitch;
            this.delay = delay;
        }

        public int getVolume() { return volume; }
        public int getPitch()  { return pitch;  }
        public int getDelay()  { return delay;  }

        public String getCrackShotConfigString() {
            final StringBuilder stb = new StringBuilder();
            stb.append(this.getKey());
            stb.append('-');
            stb.append(volume);
            stb.append('-');
            stb.append(pitch);
            stb.append('-');
            stb.append(delay);
            return stb.toString();
        }
    }

}
