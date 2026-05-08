package org.swetlokognatsk.earking_out.learning_tests.vibisility_modifiers.another_package;

import org.swetlokognatsk.earking_out.learning_tests.vibisility_modifiers.Target;

public class AnotherPackageSubclass extends Target {
    private void anotherPackageSubclassTest() {
        publicTargetMethod();
        protectedTargetMethod();
        // privateTargetMethod(); // not visible
        // modifierlessTargetMethod(); // not visible
    }
}
