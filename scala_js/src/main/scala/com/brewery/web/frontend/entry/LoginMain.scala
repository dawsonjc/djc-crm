package com.brewery.web.frontend.entry

import com.brewery.web.frontend.account.LoginPage
import org.scalajs.dom.window

object LoginMain {
    def main(): Unit = {
        Bootstrap.start {
            LoginPage.initialize();
            window.console.log("Page Initialized");
        }
    }
}
