package net.projectzombie.crackshotenhanced.yaml;

import net.projectzombie.crackshotenhanced.main.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;
import java.util.stream.Stream;

abstract public class ModifierConfig<T extends ModifierValue> {

    static protected final String NULL_CONFIG_VALUE = "NULL";
    static protected final String SEP = "`-_`";

    private final File file;
    private final YamlConfiguration yml;

    private final String ymlName;
    private final String moduleName;

    private final ModifierMap defaultValues;
    private final String[] necessaryValues;

    private final Set<String> keys;
    private final ArrayList<T> readValues;

    public ModifierConfig(final String ymlName,
                          final String moduleName,
                          final String[] necessaryValues,
                          final ModifierMap defaultValues) {
        this.ymlName = ymlName;
        this.moduleName = moduleName;

        this.defaultValues = defaultValues;
        this.necessaryValues = necessaryValues;

        this.file = new File(Main.getPlugin().getDataFolder(), ymlName);
        this.yml = YamlConfiguration.loadConfiguration(this.file);
        this.keys = this.yml.getKeys(false);

        this.readValues = new ArrayList<>();
    }


    public String getModuleName() { return moduleName; }

    public void postInitialize() {
        buildModules();
        writeExampleYml();
        writeInfoYml();
    }

    private void writeExampleYml() {
        try {
            final File exampleFile = new File(Main.getPlugin().getDataFolder(), "example." + ymlName);
            if (exampleFile.exists()) {
                exampleFile.delete();
                exampleFile.createNewFile();
            }

            final YamlConfiguration exampleYml = YamlConfiguration.loadConfiguration(exampleFile);
            final String fieldPath = "test_module";

            for (String necessaryValue: necessaryValues) {
                exampleYml.set(fieldPath + "." + necessaryValue, "REQUIRED");
                exampleYml.save(exampleFile);
            }
            for (String otherFields: new TreeSet<>(defaultValues.getKeySet())) {
                defaultValues.writeDefaultToYaml(otherFields, fieldPath + "." + otherFields, exampleYml);
                exampleYml.save(exampleFile);
            }
        } catch (Exception e) {
            Main.warning("Failed to write example config for " + moduleName);
        }
    }

    private void writeInfoYml() {
        try {
            final File infoFile = new File(Main.getPlugin().getDataFolder(), "info." + ymlName);
            if (infoFile.exists()) {
                infoFile.delete();
                infoFile.createNewFile();
            }

            final YamlConfiguration infoYml = YamlConfiguration.loadConfiguration(infoFile);
            this.getStream().filter(x -> x != null).forEach(x -> x.serializeInfoToYaml(infoYml));
            infoYml.save(infoFile);
        } catch (Exception e) {
            Main.warning("Failed to write example config for " + moduleName);
        }
    }

    private boolean isValidKey(final String entry, final String key) {
        for (String str: necessaryValues) {
            if (key.equals(str)) {
                return true;
            }
        }
        if (defaultValues.contains(key)) {
            return true;
        } else {
            Main.warning("Module " + this.moduleName + ": Entry " + entry + " unknown value '" + key + '\'');
            return false;
        }
    }

    private final String separateStringList(final List<String> list) {
        final StringBuilder stb = new StringBuilder();

        for (int i = 0 ; i < list.size() - 1; i++) {
            stb.append(list.get(i));
            stb.append(SEP);
        }
        if (!list.isEmpty()) {
            stb.append(list.get(list.size() - 1));
        }
        return stb.toString();
    }

    private void buildModules() {
        int uniqueID = 0;
        if (getNullValue() != null) {
            readValues.add(getNullValue());
            ++uniqueID;
        }
        for (String key: keys) {
            final ConfigurationSection valueConfig = this.yml.getConfigurationSection(key);
            final Set<String> values = valueConfig.getKeys(false);
            final ModifierMap moduleValues = new ModifierMap(defaultValues);
            values.stream()
                    .filter(x -> isValidKey(key, x))
                    .forEach(x -> moduleValues.put(x, valueConfig.isList(x)
                            ? separateStringList(valueConfig.getStringList(x)) : valueConfig.getString(x)));

            boolean hasNecessary = true;
            for (String necessary: necessaryValues) {
                if (!moduleValues.contains(necessary)) {
                    Main.warning(key + " is missing value " + necessary);
                    hasNecessary = false;
                    break;
                }
            }

            if (hasNecessary) {
                final T toAdd = buildModule(key, uniqueID++, moduleValues);
                if (toAdd != null) {
                    readValues.add(toAdd);
                } else {
                    Main.warning("Attempted to build " + key + " from " + moduleName + " but it is invalid.");
                }
            }
        }
    }

    public abstract T buildModule(final String key, final int uniqueID, final ModifierMap values);

    /** @return All initialized CSVValues. */
    public ArrayList<T> getAll()
    {
        return readValues;
    }

    /** @return All initialized CSVValues as a Stream. */
    public Stream<T> getStream() {
        return readValues.stream();
    }

    /**
     * @return Returns the null value of the ModifierValue. More-so for gun modifiers to represent the empty
     * element in the set.
     */
    public T getNullValue() {
        return null;
    }

    /**
     * @param uniqueID ID or index of the element.
     * @return Element with the array at index of uniqueID.
     */
    public T get(final int uniqueID)
    {
        if (uniqueID >= 0 && uniqueID < readValues.size())
            return readValues.get(uniqueID);
        else
            return null;
    }

    /**
     * @param name Name of the ModifierValue.
     * @return The element if it has a similar name.
     */
    public T get(final String name)
    {
        if (name == null || name.equalsIgnoreCase(NULL_CONFIG_VALUE)) {
            return getNullValue();
        }
        for (T temp : readValues) {
            if (name.equalsIgnoreCase(temp.getName())) {
                return temp;
            }
        }
        Main.warning("Attempted to retrieve '" + name + "' from " + moduleName + " but it does not exist.");
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
        return get(Arrays.asList(names), includeNull);
    }

    /**
     * @param names Array of names of the CSVValues.
     * @param includeNull Whether or not to include the null element in the returned set.
     * @return An ArrayList of CSVValues that any of the names given.
     */
    public ArrayList<T> get(final List<String> names,
                            final boolean includeNull)
    {
        final ArrayList<T> matchValues = new ArrayList<>();
        T tmpValue;

        if (includeNull)
            matchValues.add(getNullValue());

        if (names == null || names.size() == 0)
            return matchValues;

        for (String name : names) {
            tmpValue = this.get(name);
            if (tmpValue != null) {
                matchValues.add(tmpValue);
            }
        }

        return matchValues;
    }

    /** @return The length of the initialized array of elements. -1 if null. */
    public int size() { return readValues == null ? -1 : readValues.size(); }
}
