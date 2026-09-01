package com.brewery.web.frontend.account

import io.udash.wrappers.jquery.{EventName, JQueryEvent, jQ}
import org.scalajs.dom.{Element, HTMLFormElement}

object RegistrationPage {
    def initialize(): Unit = {
        val registerFormValidation: (Element, JQueryEvent) => scala.Any = (element: Element, jQueryEvent: JQueryEvent) => {
            jQueryEvent.preventDefault()
            Registration.register(element.asInstanceOf[HTMLFormElement]);
        }

        jQ("#register-form").on(EventName.submit, (element: Element, jQueryEvent: JQueryEvent) => {
            registerFormValidation(element, jQueryEvent);
        });
    }
}
