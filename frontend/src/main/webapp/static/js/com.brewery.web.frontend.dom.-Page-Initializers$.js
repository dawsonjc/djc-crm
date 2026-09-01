'use strict';
import * as $i_jquery from "jquery";
import * as $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024 from "./com.brewery.web.frontend.-Help-Functions$.js";
import * as $j_com$002ebrewery$002eweb$002efrontend$002eaccount$002e$002dLogin$0024 from "./com.brewery.web.frontend.account.-Login$.js";
import * as $j_com$002ebrewery$002eweb$002efrontend$002eaccount$002e$002dRegistration$0024 from "./com.brewery.web.frontend.account.-Registration$.js";
import * as $j_com$002ebrewery$002eweb$002efrontend$002eadmin$002e$002dAdmin$0024 from "./com.brewery.web.frontend.admin.-Admin$.js";
import * as $j_com$002ebrewery$002eweb$002efrontend$002econversation$002e$002dConversation$002dBox$0024 from "./com.brewery.web.frontend.conversation.-Conversation-Box$.js";
import * as $j_com$002ebrewery$002eweb$002efrontend$002emessage$002e$002dMessage$002dEvent$0024 from "./com.brewery.web.frontend.message.-Message-Event$.js";
import * as $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6 from "./internal-3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.js";
/** @constructor */
function $c_Lcom_brewery_web_frontend_dom_PageInitializers$() {
}
export { $c_Lcom_brewery_web_frontend_dom_PageInitializers$ as $c_Lcom_brewery_web_frontend_dom_PageInitializers$ };
$c_Lcom_brewery_web_frontend_dom_PageInitializers$.prototype = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$h_O();
$c_Lcom_brewery_web_frontend_dom_PageInitializers$.prototype.constructor = $c_Lcom_brewery_web_frontend_dom_PageInitializers$;
/** @constructor */
function $h_Lcom_brewery_web_frontend_dom_PageInitializers$() {
}
export { $h_Lcom_brewery_web_frontend_dom_PageInitializers$ as $h_Lcom_brewery_web_frontend_dom_PageInitializers$ };
$h_Lcom_brewery_web_frontend_dom_PageInitializers$.prototype = $c_Lcom_brewery_web_frontend_dom_PageInitializers$.prototype;
$c_Lcom_brewery_web_frontend_dom_PageInitializers$.prototype.initializeHeader__V = (function() {
  $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$();
  var jquery = (0, $i_jquery.default)("#logout");
  new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(jquery).on__T__F2__Lio_udash_wrappers_jquery_JQuery("click", new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element, jQueryEvent) => {
    jQueryEvent.preventDefault();
    var $x_4 = $i_jquery.default;
    var $x_3 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$();
    var $x_2 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("url", "/account/logout");
    var $x_1 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("method", "POST");
    var _2 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_Any$().fromFunction3__F3__sjs_js_Function3(new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction3_$$Lambda$26e0a25d9b29f6b82ea50ab7badf4fb70c5c74e1(((data, textStatus, jqXHR) => {
      $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(textStatus);
      window.location.href = "/";
    })));
    var fields = $x_3.wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$d_T2.getArrayOf().constr)([$x_2, $x_1, new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("success", _2)]));
    return $x_4.ajax($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_special_package$().objectLiteral__sci_Seq__sjs_js_Object(fields));
  })));
});
$c_Lcom_brewery_web_frontend_dom_PageInitializers$.prototype.conversations__V = (function() {
  var messages = (0, $i_jquery.default)("#chat-messages");
  var x = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$uI($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$(), new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(messages)).get__I__s_Option(0)).get__O().scrollHeight);
  messages.scrollTop(x);
  var conversationId = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(new URLSearchParams($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(window.location.search)).get("conversationId"));
  var webSocket = new WebSocket(((("/communication?conversationId=" + conversationId) + "&userId=") + $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024.$m_Lcom_brewery_web_frontend_HelpFunctions$().getCookie__T__sjs_js_Dynamic("User-Information").userId));
  webSocket.onmessage = ((messageEvent) => {
    $j_com$002ebrewery$002eweb$002efrontend$002emessage$002e$002dMessage$002dEvent$0024.$m_Lcom_brewery_web_frontend_message_MessageEvent$().buildMessageWebsocket__Lorg_scalajs_dom_MessageEvent__V(messageEvent);
  });
  var sendMessageCallback = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element, jQueryEvent) => {
    jQueryEvent.preventDefault();
    $j_com$002ebrewery$002eweb$002efrontend$002emessage$002e$002dMessage$002dEvent$0024.$m_Lcom_brewery_web_frontend_message_MessageEvent$().submitMessage__Lorg_scalajs_dom_HTMLFormElement__Lorg_scalajs_dom_WebSocket__V(element, webSocket);
  }));
  $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$();
  var jquery = (0, $i_jquery.default)("#send-message");
  new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(jquery).on__T__F2__Lio_udash_wrappers_jquery_JQuery("submit", new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element$2, jQueryEvent$2) => sendMessageCallback.apply__O__O__O(element$2, jQueryEvent$2))));
  $j_com$002ebrewery$002eweb$002efrontend$002econversation$002e$002dConversation$002dBox$0024.$m_Lcom_brewery_web_frontend_conversation_ConversationBox$().initialize__V();
});
$c_Lcom_brewery_web_frontend_dom_PageInitializers$.prototype.login__V = (function() {
  var loginFormValidation = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element, jQueryEvent) => {
    jQueryEvent.preventDefault();
    $j_com$002ebrewery$002eweb$002efrontend$002eaccount$002e$002dLogin$0024.$m_Lcom_brewery_web_frontend_account_Login$().loginValidation__Lorg_scalajs_dom_HTMLFormElement__V(element);
  }));
  $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$();
  var jquery = (0, $i_jquery.default)("#login-form");
  new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(jquery).on__T__F2__Lio_udash_wrappers_jquery_JQuery("submit", new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element$2, jQueryEvent$2) => loginFormValidation.apply__O__O__O(element$2, jQueryEvent$2))));
});
$c_Lcom_brewery_web_frontend_dom_PageInitializers$.prototype.register__V = (function() {
  var registerFormValidation = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element, jQueryEvent) => {
    jQueryEvent.preventDefault();
    $j_com$002ebrewery$002eweb$002efrontend$002eaccount$002e$002dRegistration$0024.$m_Lcom_brewery_web_frontend_account_Registration$().register__Lorg_scalajs_dom_HTMLFormElement__V(element);
  }));
  $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$();
  var jquery = (0, $i_jquery.default)("#register-form");
  new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(jquery).on__T__F2__Lio_udash_wrappers_jquery_JQuery("submit", new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element$2, jQueryEvent$2) => registerFormValidation.apply__O__O__O(element$2, jQueryEvent$2))));
});
$c_Lcom_brewery_web_frontend_dom_PageInitializers$.prototype.admin__V = (function() {
  var $x_4 = $i_jquery.default;
  var $x_3 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$();
  var $x_2 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("url", "/account/roles");
  var $x_1 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("method", "POST");
  var _2 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_Any$().fromFunction3__F3__sjs_js_Function3(new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction3_$$Lambda$26e0a25d9b29f6b82ea50ab7badf4fb70c5c74e1(((data, textStatus, jqXHR) => {
    $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(textStatus);
    if ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$uZ(data.success)) {
      var roles = data.data;
      if ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_ArrayOps$().contains$extension__sjs_js_Array__O__Z(roles, "Admin")) {
        $j_com$002ebrewery$002eweb$002efrontend$002eadmin$002e$002dAdmin$0024.$m_Lcom_brewery_web_frontend_admin_Admin$().init__V();
      }
    }
  })));
  var fields = $x_3.wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$d_T2.getArrayOf().constr)([$x_2, $x_1, new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("success", _2)]));
  $x_4.ajax($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_special_package$().objectLiteral__sci_Seq__sjs_js_Object(fields));
});
var $d_Lcom_brewery_web_frontend_dom_PageInitializers$ = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$TypeData().initClass($c_Lcom_brewery_web_frontend_dom_PageInitializers$, "com.brewery.web.frontend.dom.PageInitializers$", ({
  Lcom_brewery_web_frontend_dom_PageInitializers$: 1
}));
export { $d_Lcom_brewery_web_frontend_dom_PageInitializers$ as $d_Lcom_brewery_web_frontend_dom_PageInitializers$ };
var $n_Lcom_brewery_web_frontend_dom_PageInitializers$;
function $m_Lcom_brewery_web_frontend_dom_PageInitializers$() {
  if ((!$n_Lcom_brewery_web_frontend_dom_PageInitializers$)) {
    $n_Lcom_brewery_web_frontend_dom_PageInitializers$ = new $c_Lcom_brewery_web_frontend_dom_PageInitializers$();
  }
  return $n_Lcom_brewery_web_frontend_dom_PageInitializers$;
}
export { $m_Lcom_brewery_web_frontend_dom_PageInitializers$ as $m_Lcom_brewery_web_frontend_dom_PageInitializers$ };
//# sourceMappingURL=com.brewery.web.frontend.dom.-Page-Initializers$.js.map
