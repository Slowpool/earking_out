package org.swetlokognatsk.earking_out.learning_tests.vibisility_modifiers;

public class TargetSubclass extends Target {
    private void samePackageSubclassTest() {
        publicTargetMethod();
        protectedTargetMethod();
        // privateTargetMethod(); // not visible
        modifierlessTargetMethod();
    }
}
