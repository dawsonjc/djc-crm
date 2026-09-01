package com.brewery.web.frontend.entry

import com.brewery.web.frontend.dom.HeaderInitializer
import io.udash.wrappers.jquery.jQ

object Bootstrap {
    def start(pageInitializer: => Unit): Unit = {
        jQ(() => {
            HeaderInitializer.initialize();
            pageInitializer;
        });
    }
}
