'use strict';
import * as $i_jquery from "jquery";
import * as $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024 from "./com.brewery.web.frontend.-Help-Functions$.js";
import * as $j_com$002ebrewery$002eweb$002efrontend$002econversation$002e$002dConversation$002dBox$0024 from "./com.brewery.web.frontend.conversation.-Conversation-Box$.js";
import * as $j_com$002ebrewery$002eweb$002efrontend$002emessage$002e$002dMessage from "./com.brewery.web.frontend.message.-Message.js";
import * as $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6 from "./internal-3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.js";
/** @constructor */
function $c_Lcom_brewery_web_frontend_message_MessageEvent$() {
}
export { $c_Lcom_brewery_web_frontend_message_MessageEvent$ as $c_Lcom_brewery_web_frontend_message_MessageEvent$ };
$c_Lcom_brewery_web_frontend_message_MessageEvent$.prototype = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$h_O();
$c_Lcom_brewery_web_frontend_message_MessageEvent$.prototype.constructor = $c_Lcom_brewery_web_frontend_message_MessageEvent$;
/** @constructor */
function $h_Lcom_brewery_web_frontend_message_MessageEvent$() {
}
export { $h_Lcom_brewery_web_frontend_message_MessageEvent$ as $h_Lcom_brewery_web_frontend_message_MessageEvent$ };
$h_Lcom_brewery_web_frontend_message_MessageEvent$.prototype = $c_Lcom_brewery_web_frontend_message_MessageEvent$.prototype;
$c_Lcom_brewery_web_frontend_message_MessageEvent$.prototype.submitMessage__Lorg_scalajs_dom_HTMLFormElement__Lorg_scalajs_dom_WebSocket__V = (function(form, webSocket) {
  var formData = $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024.$m_Lcom_brewery_web_frontend_HelpFunctions$().getFormData__Lio_udash_wrappers_jquery_JQuery__sci_Map((0, $i_jquery.default)(form));
  var inputString = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$f_T__trim__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(formData).apply__O__O("message-content")))));
  if (((inputString === null) || $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$f_T__isBlank__Z($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(inputString)))) {
    return (void 0);
  }
  var message = new ($j_com$002ebrewery$002eweb$002efrontend$002emessage$002e$002dMessage.$a_Lcom_brewery_web_frontend_message_Message())();
  message.conversationId = $j_com$002ebrewery$002eweb$002efrontend$002econversation$002e$002dConversation$002dBox$0024.$m_Lcom_brewery_web_frontend_conversation_ConversationBox$().Lcom_brewery_web_frontend_conversation_ConversationBox$__f_conversationId;
  if ((($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(formData).apply__O__O("to-user-id"))) !== "") || ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(formData).apply__O__O("to-username"))) !== ""))) {
    message.toUserId = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(formData).apply__O__O("to-user-id")));
    message.toUsername = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(formData).apply__O__O("to-username")));
  }
  message.fromUserId = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(formData).apply__O__O("user-id")));
  message.content = $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024.$m_Lcom_brewery_web_frontend_HelpFunctions$().cleanseString__T__T(inputString);
  webSocket.send($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(JSON.stringify(message)));
  (0, $i_jquery.default)("#message-input").val("");
});
$c_Lcom_brewery_web_frontend_message_MessageEvent$.prototype.buildMessageWebsocket__Lorg_scalajs_dom_MessageEvent__V = (function(messageEvent) {
  var messages = (0, $i_jquery.default)("#chat-messages");
  var response = JSON.parse($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(messageEvent.data));
  if (($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(response.message)) === "deleted")) {
    var \u03b41$ = messages.find($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sc_StringOps$().format$extension__T__sci_Seq__T("input[value='%s']", $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$().genericWrapArray__O__sci_ArraySeq(new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$ac_O([response.data.messageId]))));
    var \u03b42$ = \u03b41$.parent();
    var \u03b43$ = \u03b42$.parent();
    \u03b43$.remove();
    return (void 0);
  }
  var message = new ($j_com$002ebrewery$002eweb$002efrontend$002emessage$002e$002dMessage.$a_Lcom_brewery_web_frontend_message_Message())(response.data);
  var \u03b4message = message.toJQueryElement();
  \u03b4message.find(".message-content").html($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(message.content));
  $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$();
  var jquery = \u03b4message.find(".delete-message");
  new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(jquery).on__T__F2__Lio_udash_wrappers_jquery_JQuery("click", new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element, jQueryEvent) => {
    var this$ = (0, $i_jquery.default)(element);
    var $x_1 = $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024.$m_Lcom_brewery_web_frontend_HelpFunctions$();
    var \u03b44$ = this$.parent();
    var formData = $x_1.getFormData__Lio_udash_wrappers_jquery_JQuery__sci_Map(\u03b44$.parent().find("form"));
    var $x_7 = $i_jquery.default;
    var $x_6 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$();
    var $x_5 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("url", "/message/delete");
    var $x_4 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("method", "DELETE");
    var $x_2 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$();
    var s = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(formData).apply__O__O("message-id")));
    var fields = $x_2.wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$d_T2.getArrayOf().constr)([new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("messageId", s)]));
    var _2 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_special_package$().objectLiteral__sci_Seq__sjs_js_Object(fields);
    var $x_3 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("data", _2);
    var _2$1 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_Any$().fromFunction3__F3__sjs_js_Function3(new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction3_$$Lambda$26e0a25d9b29f6b82ea50ab7badf4fb70c5c74e1(((data, textStatus, jqXHR) => {
      $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(textStatus);
      var \u03b45$ = this$.parent();
      var \u03b46$ = \u03b45$.parent();
      return \u03b46$.remove();
    })));
    var fields$1 = $x_6.wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$d_T2.getArrayOf().constr)([$x_5, $x_4, $x_3, new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("success", _2$1)]));
    return $x_7.ajax($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_special_package$().objectLiteral__sci_Seq__sjs_js_Object(fields$1));
  })));
  messages.append(\u03b4message);
  var this$12 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sci_Map$();
  var elems = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$().wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$d_T2.getArrayOf().constr)([]));
  var animation = this$12.from__sc_IterableOnce__sci_Map(elems);
  var this$15 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(animation);
  var y = messages.prop("scrollHeight");
  animation = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_sci_Map(this$15.updated__O__O__sci_MapOps("scrollTop", y));
  $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$();
  var \u03b47$ = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(messages);
  \u03b47$.animate__sci_Map__I__T__F1__Lio_udash_wrappers_jquery_JQuery(animation, 50, "swing", \u03b47$.animate$default$4__F1());
});
var $d_Lcom_brewery_web_frontend_message_MessageEvent$ = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$TypeData().initClass($c_Lcom_brewery_web_frontend_message_MessageEvent$, "com.brewery.web.frontend.message.MessageEvent$", ({
  Lcom_brewery_web_frontend_message_MessageEvent$: 1
}));
export { $d_Lcom_brewery_web_frontend_message_MessageEvent$ as $d_Lcom_brewery_web_frontend_message_MessageEvent$ };
var $n_Lcom_brewery_web_frontend_message_MessageEvent$;
function $m_Lcom_brewery_web_frontend_message_MessageEvent$() {
  if ((!$n_Lcom_brewery_web_frontend_message_MessageEvent$)) {
    $n_Lcom_brewery_web_frontend_message_MessageEvent$ = new $c_Lcom_brewery_web_frontend_message_MessageEvent$();
  }
  return $n_Lcom_brewery_web_frontend_message_MessageEvent$;
}
export { $m_Lcom_brewery_web_frontend_message_MessageEvent$ as $m_Lcom_brewery_web_frontend_message_MessageEvent$ };
//# sourceMappingURL=com.brewery.web.frontend.message.-Message-Event$.js.map
