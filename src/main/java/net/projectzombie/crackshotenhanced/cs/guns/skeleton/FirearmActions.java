/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.skeleton;

import net.projectzombie.crackshotenhanced.main.Main;
import net.projectzombie.crackshotenhanced.yaml.ModifierConfig;
import net.projectzombie.crackshotenhanced.yaml.ModifierMap;
import net.projectzombie.crackshotenhanced.yaml.ModifierValue;
import net.projectzombie.crackshotenhanced.cs.guns.skeleton.FirearmActions.FirearmAction;

import java.util.HashMap;


/**
 *
 * @author Jesse Bannon
 * Singleton CSV loader that contains all the FirearmActions specified in the
 * CSV.
 */
public class FirearmActions extends ModifierConfig<FirearmAction>
{
    static private FirearmActions singleton = null;
    static public FirearmActions getInstance()
    {
        if (singleton == null)
            singleton = new FirearmActions();
        return singleton;
    }

    static private final ModifierMap buildDefaultValues() {
        final ModifierMap defaultValues = new ModifierMap(MODULE_NAME);
        defaultValues.put("Sound Open", "NULL");
        defaultValues.put("Sound Close", "NULL");
        return defaultValues;
    }

    static private final String YML_NAME = "FirearmActions.yml";
    static private final String MODULE_NAME = "FirearmActions";
    static private final String[] NECESSARY_VALUES = new String[] { "Display Name", "CS Firearm Action Type", "Duration Open Ticks", "Duration Close Ticks", "Duration Close Shoot Delay Ticks"};
    static private final ModifierMap DEFAULT_VALUES = buildDefaultValues();

    private FirearmActions() { super(YML_NAME, MODULE_NAME, NECESSARY_VALUES, DEFAULT_VALUES); }

    public FirearmAction buildModule(final int uniqueID, final ModifierMap values) {
        try {
            return new FirearmAction(
                    uniqueID,
                    values.getString("Display Name"),
                    values.getString("CS Firearm Action Type"),
                    values.getString("Sound Open"),
                    values.getString("Sound Close"),
                    values.getInt("Duration Open Ticks"),
                    values.getInt("Duration Close Ticks"),
                    values.getInt("Duration Close Shoot Delay Ticks")
            );
        } catch (Exception e) {
            Main.getPlugin().getLogger().warning("Cannot add firearm action " + values.getString("Display Name"));
            return null;
        }
    }

    @Override
    public FirearmAction getNullValue() {
        return new FirearmAction();
    }

    static public class FirearmAction extends ModifierValue
    {
        private final String type;
        private final String soundOpen;
        private final String soundClose;

        private final int openDuration;
        private final int closeDuration;
        private final int closeShootDelay;

        private FirearmAction(final int index,
                              final String displayName,
                              final String type,
                              final String soundOpen,
                              final String soundClose,
                              final int openDuration,
                              final int closeDuration,
                              final int closeShootDelay)
        {
            super(index, displayName);
            this.type = type;
            this.soundOpen = soundOpen;
            this.soundClose = soundClose;
            this.openDuration = openDuration;
            this.closeDuration = closeDuration;
            this.closeShootDelay = closeShootDelay;
        }

        private FirearmAction()
        {
            this(0, null, null, null, null, 0, 0, 0);
        }

        public String  getSoundOpen()         { return soundOpen;         } 
        public String  getSoundClose()        { return soundClose;        }
        public int     getOpenDuration()      { return openDuration;      }
        public int     getCloseDuration()     { return closeDuration;     }
        public int     getCloseShootDelay()   { return closeShootDelay;   }
        public String  getType()              { return type;              }
        
        public int getActionDurationInTicks()
        {
            if (type == null) {
                return 0;
            } else if (type.equalsIgnoreCase("pump")
                    || type.equalsIgnoreCase("bolt")
                    || type.equalsIgnoreCase("lever")) {
                return openDuration + closeDuration + closeShootDelay;
            } else {
                return 0;
            }
        }

    }
}