package com.brewery.web.frontend.account

import io.udash.wrappers.jquery.{EventName, JQueryEvent, jQ}
import org.scalajs.dom.{Element, HTMLFormElement}

object LoginPage {
    def initialize(): Unit = {
        jQ("#login-form").on(EventName.submit, (element: Element, jQueryEvent: JQueryEvent) => {
            jQueryEvent.preventDefault();
            Login.loginValidation(element.asInstanceOf[HTMLFormElement]);
        });
    }
}
