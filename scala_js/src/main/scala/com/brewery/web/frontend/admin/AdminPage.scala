package com.brewery.web.frontend.admin

import io.udash.wrappers.jquery.{JQueryAjaxSettings, JQueryXHR, jQ}

import scala.scalajs.js

object AdminPage {
    def initialize(): Unit = {
        jQ.ajax(js.Dynamic.literal(
            url = "/account/roles",
            method = "POST",
            success = (data: js.Any, textStatus: String, jqXHR: JQueryXHR) => {
                val response: js.Dynamic = data.asInstanceOf[js.Dynamic];

                if(response.success.asInstanceOf[Boolean]) {
                    val roles: js.Array[String] = response.data.asInstanceOf[js.Array[String]];

                    if(roles.contains("Admin")) {
                        Admin.init();
                    }
                }
            }
        ).asInstanceOf[JQueryAjaxSettings]);
    }
}
