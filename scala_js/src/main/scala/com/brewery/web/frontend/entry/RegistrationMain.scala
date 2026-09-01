package com.brewery.web.frontend.entry

import com.brewery.web.frontend.account.RegistrationPage
import org.scalajs.dom.window

object RegistrationMain {
    def main(): Unit = {
        Bootstrap.start {
            RegistrationPage.initialize();
            window.console.log("Page Initialized");
        }
    }
}
