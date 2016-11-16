package net.projectzombie.crackshotenhanced.yaml;

import net.projectzombie.crackshotenhanced.main.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.*;

import static net.projectzombie.crackshotenhanced.yaml.ModifierConfig.SEP;

/**
 * Created by jb on 11/12/16.
 */
public class ModifierMap {

    private enum ModifierMapType {

        STRING("String"),
        DOUBLE("Double"),
        INT("Integer"),
        BOOL("Boolean");

        private final String name;

        ModifierMapType(final String name) {
            this.name = name;
        }

        @Override public String toString() {
            return name;
        }
    }

    private class ModifierMapValue {
        private final String value;
        private final ModifierMapType type;

        public ModifierMapValue(final String string,
                                final ModifierMapType type) {
            this.value = string;
            this.type = type;
        }

        public String val() {
            return value;
        }

        public String getType() {
            return type.toString();
        }

        public void writeDefaultToYaml(final String path, final YamlConfiguration yaml) {
            switch (type) {
                case STRING: if (value != null) yaml.set(path, value); else yaml.set(path, "NULL"); break;
                case DOUBLE: yaml.set(path, Double.valueOf(value)); break;
                case INT: yaml.set(path, Integer.valueOf(value)); break;
                case BOOL: yaml.set(path, Boolean.valueOf(value)); break;
                default:
            }
        }
    }

    private final HashMap<String, ModifierMapValue> values;
    private final String modifierMapName;

    public ModifierMap(final String modifierMapName) {
        this.values = new HashMap<>();
        this.modifierMapName = modifierMapName;
    }

    public ModifierMap(final ModifierMap map) {
        this.values = new HashMap<>(map.values);
        this.modifierMapName = map.modifierMapName;
    }

    public Set<String> getKeySet() { return this.values.keySet(); }

    public void put(final String key, final String value) {
        this.values.put(key, new ModifierMapValue(value, ModifierMapType.STRING));
    }

    public void put(final String key, final double value) {
        this.values.put(key, new ModifierMapValue(String.valueOf(value), ModifierMapType.DOUBLE));
    }

    public void put(final String key, final int value) {
        this.values.put(key, new ModifierMapValue(String.valueOf(value), ModifierMapType.INT));
    }

    public void put(final String key, final boolean value) {
        this.values.put(key, new ModifierMapValue(String.valueOf(value), ModifierMapType.BOOL));
    }

    public void writeDefaultToYaml(final String key, final String path, final YamlConfiguration yaml) {
        if (values.containsKey(key)) {
            values.get(key).writeDefaultToYaml(path, yaml);
        }
    }

    private ModifierMapValue get(final String key) {
        if (!this.values.containsKey(key)) {
            Main.getPlugin().getLogger().severe("Module " + modifierMapName + ": Invalid key '" + key + '\'');
            return null;
        } else {
            return this.values.get(key);
        }
    }

    public int getInt(final String key) {
        try {
            return Integer.valueOf(this.get(key).value.replace("\"", ""));
        } catch (Exception e) {
            Main.getPlugin().getLogger().warning(key + " must be an integer");
            throw e;
        }
    }

    public double getDouble(final String key) {
        try {
            return Double.valueOf(this.get(key).value.replace("\"", ""));
        } catch (Exception e) {
            Main.getPlugin().getLogger().warning(key + " must be a double");
            throw e;
        }
    }

    public boolean getBoolean(final String key) {
        try {
            return Boolean.valueOf(this.get(key).value.replace("\"", ""));
        } catch (Exception e) {
            Main.getPlugin().getLogger().warning(key + " must be true or false");
            throw e;
        }
    }

    public String getString(final String key) {
        return this.get(key).value;
    }

    public List<String> getStringList(final String key) {
        ModifierMapValue tmp = this.get(key);
        if (tmp != null && tmp.value != null) {
            return Arrays.asList(tmp.value.split(SEP));
        } else {
            return new ArrayList<>(0);
        }
    }

    public boolean contains(final String key) {
        return this.values.containsKey(key);
    }
}
