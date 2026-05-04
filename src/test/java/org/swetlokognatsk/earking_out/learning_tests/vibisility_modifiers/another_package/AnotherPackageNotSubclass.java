package org.swetlokognatsk.earking_out.learning_tests.vibisility_modifiers.another_package;

import org.swetlokognatsk.earking_out.learning_tests.vibisility_modifiers.Target;

public class AnotherPackageNotSubclass {
    private void anotherPackageNotSubclassTest() {
        var modifierlessSibling = new Target();
        modifierlessSibling.publicTargetMethod();
        // modifierlessSibling.protectedTargetMethod(); // not visible
        // modifierlessSibling.privateSiblingMethod(); // not visible
        // modifierlessSibling.modifierlessTargetMethod(); // not visible
    }

    private void modifierlessSiblingVisibiltyTest() {
        // var modifierlessSibling = new ModifierlessSibling(); // not visible
    }

    
}