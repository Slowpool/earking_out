package org.swetlokognatsk.earking_out.test_fakes.vibisility_modifiers.another_package;

import org.swetlokognatsk.earking_out.test_fakes.vibisility_modifiers.Target;

public class AnotherPackageSubclass extends Target {
    private void anotherPackageSubclassTest() {
        publicTargetMethod();
        protectedTargetMethod();
        // privateTargetMethod(); // not visible
        // modifierlessTargetMethod(); // not visible
    }
}
