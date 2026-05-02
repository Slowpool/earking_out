package org.swetlokognatsk.earking_out.test_fakes.vibisility_modifiers;

public class TargetSubclass extends Target {
    private void samePackageSubclassTest() {
        publicTargetMethod();
        protectedTargetMethod();
        // privateTargetMethod(); // not visible
        modifierlessTargetMethod();
    }
}
