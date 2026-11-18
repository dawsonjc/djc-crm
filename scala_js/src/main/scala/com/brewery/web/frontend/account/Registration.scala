package com.brewery.web.frontend.account

import io.udash.wrappers.jquery.{JQueryAjaxSettings, JQueryXHR, jQ}
import org.scalajs.dom.{HTMLFormElement, HTMLInputElement, window}

import scala.scalajs.js

object Registration {
    def register(form: HTMLFormElement): Unit = {
        val registerForm = jQ(form)
        val error = registerForm.find("#registration-error")
        error.text("")

        val password = form.querySelector("#password").asInstanceOf[HTMLInputElement].value
        if (password.codePointCount(0, password.length) < 8 || password.getBytes("UTF-8").length > 72) {
            error.text("Password must be at least 8 characters and at most 72 UTF-8 bytes")
            return
        }

        jQ.ajax(js.Dynamic.literal(
            url = form.action,
            method = "POST",
            data = registerForm.serialize(),
            success = registerSuccess,
            error = registerFailure
        ).asInstanceOf[JQueryAjaxSettings])
    }

    private def registerSuccess(data: js.Any, status: String, jqXHR: JQueryXHR): Unit = {
        window.location.href = "login"
    }

    private def registerFailure(jqXHR: JQueryXHR, textStatus: String, errorThrown: String): Unit = {
        val xhr = jqXHR.asInstanceOf[js.Dynamic]
        val response = xhr.responseJSON
        val message =
            if (xhr.status.asInstanceOf[Int] == 409) "This verification link is no longer valid."
            else if (!js.isUndefined(response) && response != null &&
                js.typeOf(response.message) == "string" && response.message.asInstanceOf[String].nonEmpty)
                response.message.asInstanceOf[String]
            else "Registration could not be completed. Check your details and try again."
        jQ("#registration-error").text(message)
    }
}
