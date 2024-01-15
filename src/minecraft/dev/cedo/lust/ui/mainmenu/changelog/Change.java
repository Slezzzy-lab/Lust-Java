package dev.cedo.lust.ui.mainmenu.changelog;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cedo
 * @since 05/19/2022
 */
@Getter
@AllArgsConstructor
public class Change {
    private final ChangeType type;
    private final String description;
}
