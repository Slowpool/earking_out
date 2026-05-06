package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch;

import org.swetlokognatsk.earking_out.core.domain.model.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.UsualHint;

public class VisualPerfectPitchHints {
    public static <T extends Hint> T find(Solution solution) {
        var hint = switch (solution.value){
            case "4" -> "hint4";
            default -> null;
        };
        return (T)new UsualHint(hint);
    }

}
