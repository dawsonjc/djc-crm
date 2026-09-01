package com.brewery.web.frontend.conversation

import com.brewery.web.frontend.HelpFunctions
import com.brewery.web.frontend.message.MessageEvent
import com.brewery.web.frontend.message.MessageEvent.buildMessageWebsocket
import io.udash.wrappers.jquery.{EventName, JQuery, JQueryEvent, jQ}
import org.scalajs.dom.{Element, HTMLFormElement, URLSearchParams, WebSocket, window}

object ConversationPage {
    def initialize(): Unit = {
        val messages: JQuery = jQ("#chat-messages");
        messages.scrollTop(messages.get(0).get.scrollHeight);

        val conversationId: String = (new URLSearchParams(window.location.search)).get("conversationId");
        val webSocket: WebSocket = new WebSocket(s"/communication?conversationId=${conversationId}&userId=${HelpFunctions.getCookie("User-Information").userId}");
        webSocket.onmessage = buildMessageWebsocket
        val sendMessageCallback: (Element, JQueryEvent) => scala.Any = (element: Element, jQueryEvent: JQueryEvent) => {
            jQueryEvent.preventDefault();

            MessageEvent.submitMessage(element.asInstanceOf[HTMLFormElement], webSocket);
        };
        jQ("#send-message").on(EventName.submit, (element: Element, jQueryEvent: JQueryEvent) => {
            sendMessageCallback(element, jQueryEvent);
        });

        ConversationBox.initialize();
    }
}
