package org.swetlokognatsk.earking_out.core.domain.model.solutions.sound;

import org.swetlokognatsk.earking_out.core.domain.model.music.sounds.Sound;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;

/*
 There's no any `Hint` domain object because solution is hint itself. Reminder: the purpose of app is to train perfect pitch, so when training on puzzles the user gets the solution right at the beginning of the puzzle - the only thing is that user gets the solution in different format than it's coded in domain - user gets either mp3 sound or some notes on music staff, so there's no sense in creating `Hint` object because from the point of domain model it'll be the same as it's corresponding `Solution`. `Hint` would merely clutter the code base with copy-past classes of corresponding solutions. technically, hint is infrastructure concern, so that it's impossible to encapsulate any business logic in hint like `it's sound of C1 note` or `it's a C1 note on music staff` and even though it's possible to create these classes, they're redundant and worthless from the point of domain. "domain model must be useful over correct"
 */
public abstract class SoundSolution<S extends Sound> extends Solution {
    public final S sound;

    public SoundSolution(final S sound) {
        this.sound = sound;
    }
}
