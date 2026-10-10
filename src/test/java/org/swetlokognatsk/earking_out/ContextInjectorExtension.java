package org.swetlokognatsk.earking_out;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

public class ContextInjectorExtension implements BeforeEachCallback {

    public void beforeEach(ExtensionContext context) throws Exception {
        var springContext = SpringExtension.getApplicationContext(context);
        
        DI.setContext(springContext, true);
    }

}
