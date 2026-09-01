package com.brewery.web.frontend.entry

import org.scalajs.dom.window

object ShellMain {
    def main(): Unit = {
        Bootstrap.start {
            window.console.log("Route not found");
        }
    }
}
