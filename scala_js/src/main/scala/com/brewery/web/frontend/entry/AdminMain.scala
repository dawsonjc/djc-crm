package com.brewery.web.frontend.entry

import com.brewery.web.frontend.admin.AdminPage
import org.scalajs.dom.window

object AdminMain {
    def main(): Unit = {
        Bootstrap.start {
            AdminPage.initialize();
            window.console.log("Page Initialized");
        }
    }
}
