package net.projectzombie.crackshotenhanced.yaml;

import net.projectzombie.crackshotenhanced.main.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

/**
 * Created by jb on 11/12/16.
 */
abstract public class ModifierConfig<T extends ModifierValue> {

    static protected final String SEP = "`-_`";

    private final File file;
    private final YamlConfiguration yml;
    private final String ymlName;
    private final ModifierMap defaultValues;
    private final Set<String> items;
    private final ArrayList<T> readValues;
    private final String[] necessaryValues;
    private final String moduleName;

    public ModifierConfig(final String ymlName,
                          final String moduleName,
                          final String[] necessaryValues,
                          final ModifierMap defaultValues) {
        this.ymlName = ymlName;
        this.defaultValues = defaultValues;
        this.file = new File(Main.getPlugin().getDataFolder(), ymlName);
        this.yml = YamlConfiguration.loadConfiguration(this.file);
        this.moduleName = moduleName;
        this.necessaryValues = necessaryValues;
        writeExampleYml();
//        this.items = new TreeSet<String>();
//        this.readValues = new ArrayList<T>();
        this.items = this.yml.getKeys(false);
        this.readValues = getModules();
    }

    public String getModuleName() { return moduleName; }

    private void writeExampleYml() {
        try {
            final File exampleFile = new File(Main.getPlugin().getDataFolder(), "example." + ymlName);
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
            Main.getPlugin().getLogger().warning("Failed to write example config for " + moduleName);
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
            Main.getPlugin().getLogger().warning("Module " + this.moduleName + ": Entry " + entry + " unknown value '" + key + '\'');
        }
        return false;
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

    private ArrayList<T> getModules() {
        final ArrayList<T> toRet = new ArrayList<>();
        int uniqueID = 0;
        if (getNullValue() != null) {
            toRet.add(getNullValue());
            ++uniqueID;
        }
        for (String module: items) {
            final ConfigurationSection moduleConfig = this.yml.getConfigurationSection(module);
            final Set<String> values = moduleConfig.getKeys(false);
            final ModifierMap moduleValues = new ModifierMap(defaultValues);
            for (String key: values) {
                if (isValidKey(module, key)) {
                    if (moduleConfig.isList(key)) {
                        moduleValues.put(key, separateStringList(moduleConfig.getStringList(key)));
                    } else {
                        moduleValues.put(key, moduleConfig.getString(key));
                    }
                }
            }

            boolean hasNecessary = true;
            for (String necessary: necessaryValues) {
                if (!moduleValues.contains(necessary)) {
                    Main.getPlugin().getLogger().warning(module + " is missing value " + necessary);
                    hasNecessary = false;
                    break;
                }
            }

            if (hasNecessary) {
                final T toAdd = buildModule(uniqueID++, moduleValues);
                if (toAdd != null) {
                    toRet.add(toAdd);
                }
            }
        }
        return toRet;
    }

    public abstract T buildModule(final int uniqueID, final ModifierMap values);

    /**
     * @return Returns all initialized CSVValues.
     */
    public ArrayList<T> getAll()
    {
        return readValues;
    }

    /**
     * @return Returns the null value of the ModifierValue. More-so for gun modifiers to represent the empty
     * element in the set.
     */
    public abstract T getNullValue();

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
        if (name == null) {
            return getNullValue();
        }
        for (T temp : readValues)
        {
            if (name.equalsIgnoreCase(temp.getName()))
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
        String tempName;

        if (includeNull)
            matchValues.add(getNullValue());

        if (names == null || names.size() == 0)
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
    public int size()
    {
        if (readValues == null)
        {
            return -1;
        }
        else
        {
            return readValues.size();
        }
    }


}
