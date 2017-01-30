package net.projectzombie.crackshotenhanced.resources.sounds;

import net.projectzombie.crackshotenhanced.main.Main;
import net.projectzombie.crackshotenhanced.yaml.ModifierConfig;
import net.projectzombie.crackshotenhanced.yaml.ModifierMap;
import net.projectzombie.crackshotenhanced.yaml.ModifierValue;

public class GlobalConfiguration extends ModifierConfig<GlobalConfiguration.GlobalConfig> {

    static private final String GLOBAL_SOUNDS_KEY = "GlobalSounds";
    static private final int GLOBAL_SOUND_UNIQUE_ID = 0;

    static private GlobalConfiguration singleton = null;
    static public void initialize()
    {
        if (singleton == null) {
            singleton = new GlobalConfiguration();
            singleton.postInitialize();
        }
    }

    static public GlobalConfig config() { return singleton.get(GLOBAL_SOUND_UNIQUE_ID); }

    static private final String YML_NAME = "GlobalSounds.yml";
    static private final String MODULE_NAME = "GlobalSounds";
    static private final String[] NECESSARY_VALUES = new String[] {
            "Sound Acquired",
            "Sound Toggle Zoom",
            "Sound Out Of Ammo",
            "Sound Headshot Victim"
    };
    static private final ModifierMap DEFAULT_VALUES = buildDefaultValues();

    static private ModifierMap buildDefaultValues() {
        final ModifierMap defaults = new ModifierMap(MODULE_NAME);
        defaults.put("Projectile Type", "snowball");
        return defaults;
    }

    private GlobalConfiguration() { super(YML_NAME, MODULE_NAME, NECESSARY_VALUES, DEFAULT_VALUES); }

    /** Builds the first module only. */
    public GlobalConfig buildModule(final String key, final int uniqueID, final ModifierMap values) {
        if (uniqueID > 0) {
            return null;
        }
        try {
            return new GlobalConfig(
                    values.getString("Projectile Type"),
                    SoundAliases.getInstance().get(values.getString("Sound Acquired")),
                    SoundAliases.getInstance().get(values.getString("Sound Toggle Zoom")),
                    SoundAliases.getInstance().get(values.getString("Sound Out Of Ammo")),
                    SoundAliases.getInstance().get(values.getString("Sound Headshot Victim"))
            );
        } catch (Exception e) {
            Main.warning("Invalid configuration in " + MODULE_NAME);
            return null;
        }
    }

    public class GlobalConfig extends ModifierValue {

        private final String projectileType;
        private final SoundAliases.SoundAlias soundAcquired;
        private final SoundAliases.SoundAlias soundToggleZoom;
        private final SoundAliases.SoundAlias soundOutOfAmmo;
        private final SoundAliases.SoundAlias headshotSoundVictim;


        private GlobalConfig(final String projectileType,
                             final SoundAliases.SoundAlias soundAcquired,
                             final SoundAliases.SoundAlias soundToggleZoom,
                             final SoundAliases.SoundAlias soundOutOfAmmo,
                             final SoundAliases.SoundAlias headshotSoundVictim) {

            super(GLOBAL_SOUNDS_KEY, GLOBAL_SOUND_UNIQUE_ID, GLOBAL_SOUNDS_KEY);
            this.projectileType = projectileType;
            this.soundAcquired = soundAcquired;
            this.soundToggleZoom = soundToggleZoom;
            this.soundOutOfAmmo = soundOutOfAmmo;
            this.headshotSoundVictim = headshotSoundVictim;
        }

        public String getProjectileType() { return projectileType; }
        public SoundAliases.SoundAlias getSoundAcquired() { return soundAcquired; }
        public SoundAliases.SoundAlias getSoundToggleZoom() { return soundToggleZoom; }
        public SoundAliases.SoundAlias getSoundOutOfAmmo() { return soundOutOfAmmo; }
        public SoundAliases.SoundAlias getHeadshotSoundVictim() { return headshotSoundVictim; }

    }
}
