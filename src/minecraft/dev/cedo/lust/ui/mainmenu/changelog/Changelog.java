package dev.cedo.lust.ui.mainmenu.changelog;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cedo
 * @since 05/19/2022
 */
public class Changelog {
    public static final List<Change> changelog = new ArrayList<>();

    public static void collectChangelog() {
        if(changelog.size() != 0) return;
        changelog.add(new Change(ChangeType.NEW, "Added Clickgui"));
        changelog.add(new Change(ChangeType.NEW, "Added 2DESP"));
        changelog.add(new Change(ChangeType.DELETE, "Removed cedo"));
        changelog.add(new Change(ChangeType.UPDATE, "Lifted 335 pounds"));
        changelog.add(new Change(ChangeType.FIX, "Fixed erectile dysfunction"));
    }

}
