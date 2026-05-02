package org.swetlokognatsk.earking_out.test_fakes.vibisility_modifiers;

public class Target {
    public void publicTargetMethod() {

    }

    protected void protectedTargetMethod() {

    }

    private void privateTargetMethod() {

    }

    void modifierlessTargetMethod() {

    }


    
    private void modifierlessSiblingVisibiltyTest() {
        var modifierlessSibling = new ModifierlessSibling();
        modifierlessSibling.publicSiblingMethod();
        modifierlessSibling.protectedSiblingMethod();
        // modifierlessSibling.privateSiblingMethod(); // not visible
        modifierlessSibling.modifierlessSiblingMethod();
    }

    private void childTest() {
        var modifierlessSibling = new TargetSubclass();
        modifierlessSibling.publicTargetMethod();
        modifierlessSibling.protectedTargetMethod();
        // modifierlessSibling.privateTargetMethod(); // not visible
        modifierlessSibling.modifierlessTargetMethod();
    }
}
