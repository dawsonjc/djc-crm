package com.brewery.web.frontend.dom

import io.udash.wrappers.jquery.{EventName, JQueryAjaxSettings, JQueryEvent, JQueryXHR, jQ}
import org.scalajs.dom.{Element, window}

import scala.scalajs.js

object HeaderInitializer {
    def initialize(): Unit = {
        jQ("#logout").on(EventName.click, (element: Element, jQueryEvent: JQueryEvent) => {
            jQueryEvent.preventDefault();

            jQ.ajax(js.Dynamic.literal(
                url = "/account/logout",
                method = "POST",
                success = (data: js.Any, textStatus: String, jqXHR: JQueryXHR) => {
                    window.location.href = "/";
                }
            ).asInstanceOf[JQueryAjaxSettings])
        })
    }
}
