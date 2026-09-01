package com.brewery.web.frontend.entry

import com.brewery.web.frontend.conversation.ConversationPage
import org.scalajs.dom.window

object ConversationsMain {
    def main(): Unit = {
        Bootstrap.start {
            ConversationPage.initialize();
            window.console.log("Page Initialized");
        }
    }
}
