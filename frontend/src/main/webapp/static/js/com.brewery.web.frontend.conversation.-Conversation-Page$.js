'use strict';
import * as $i_jquery from "jquery";
import * as $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024 from "./com.brewery.web.frontend.-Help-Functions$.js";
import * as $j_com$002ebrewery$002eweb$002efrontend$002econversation$002e$002dConversation$002dBox$0024 from "./com.brewery.web.frontend.conversation.-Conversation-Box$.js";
import * as $j_com$002ebrewery$002eweb$002efrontend$002emessage$002e$002dMessage$002dEvent$0024 from "./com.brewery.web.frontend.message.-Message-Event$.js";
import * as $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c from "./internal-07235935c771c9429268cb101d7f2e2e7f60988c.js";
/** @constructor */
function $c_Lcom_brewery_web_frontend_conversation_ConversationPage$() {
}
export { $c_Lcom_brewery_web_frontend_conversation_ConversationPage$ as $c_Lcom_brewery_web_frontend_conversation_ConversationPage$ };
$c_Lcom_brewery_web_frontend_conversation_ConversationPage$.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_Lcom_brewery_web_frontend_conversation_ConversationPage$.prototype.constructor = $c_Lcom_brewery_web_frontend_conversation_ConversationPage$;
/** @constructor */
function $h_Lcom_brewery_web_frontend_conversation_ConversationPage$() {
}
export { $h_Lcom_brewery_web_frontend_conversation_ConversationPage$ as $h_Lcom_brewery_web_frontend_conversation_ConversationPage$ };
$h_Lcom_brewery_web_frontend_conversation_ConversationPage$.prototype = $c_Lcom_brewery_web_frontend_conversation_ConversationPage$.prototype;
$c_Lcom_brewery_web_frontend_conversation_ConversationPage$.prototype.initialize__V = (function() {
  var messages = (0, $i_jquery.default)("#chat-messages");
  var x = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$uI($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_Lio_udash_wrappers_jquery_JQuery$(), new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(messages)).get__I__s_Option(0)).get__O().scrollHeight);
  messages.scrollTop(x);
  var conversationId = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T(new URLSearchParams($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T(window.location.search)).get("conversationId"));
  var webSocket = new WebSocket(((("/communication?conversationId=" + conversationId) + "&userId=") + $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024.$m_Lcom_brewery_web_frontend_HelpFunctions$().getCookie__T__sjs_js_Dynamic("User-Information").userId));
  webSocket.onmessage = ((messageEvent) => {
    $j_com$002ebrewery$002eweb$002efrontend$002emessage$002e$002dMessage$002dEvent$0024.$m_Lcom_brewery_web_frontend_message_MessageEvent$().buildMessageWebsocket__Lorg_scalajs_dom_MessageEvent__V(messageEvent);
  });
  var sendMessageCallback = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element, jQueryEvent) => {
    jQueryEvent.preventDefault();
    $j_com$002ebrewery$002eweb$002efrontend$002emessage$002e$002dMessage$002dEvent$0024.$m_Lcom_brewery_web_frontend_message_MessageEvent$().submitMessage__Lorg_scalajs_dom_HTMLFormElement__Lorg_scalajs_dom_WebSocket__V(element, webSocket);
  }));
  $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_Lio_udash_wrappers_jquery_JQuery$();
  var jquery = (0, $i_jquery.default)("#send-message");
  new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(jquery).on__T__F2__Lio_udash_wrappers_jquery_JQuery("submit", new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element$2, jQueryEvent$2) => sendMessageCallback.apply__O__O__O(element$2, jQueryEvent$2))));
  $j_com$002ebrewery$002eweb$002efrontend$002econversation$002e$002dConversation$002dBox$0024.$m_Lcom_brewery_web_frontend_conversation_ConversationBox$().initialize__V();
});
var $d_Lcom_brewery_web_frontend_conversation_ConversationPage$ = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_Lcom_brewery_web_frontend_conversation_ConversationPage$, "com.brewery.web.frontend.conversation.ConversationPage$", ({
  Lcom_brewery_web_frontend_conversation_ConversationPage$: 1
}));
export { $d_Lcom_brewery_web_frontend_conversation_ConversationPage$ as $d_Lcom_brewery_web_frontend_conversation_ConversationPage$ };
var $n_Lcom_brewery_web_frontend_conversation_ConversationPage$;
function $m_Lcom_brewery_web_frontend_conversation_ConversationPage$() {
  if ((!$n_Lcom_brewery_web_frontend_conversation_ConversationPage$)) {
    $n_Lcom_brewery_web_frontend_conversation_ConversationPage$ = new $c_Lcom_brewery_web_frontend_conversation_ConversationPage$();
  }
  return $n_Lcom_brewery_web_frontend_conversation_ConversationPage$;
}
export { $m_Lcom_brewery_web_frontend_conversation_ConversationPage$ as $m_Lcom_brewery_web_frontend_conversation_ConversationPage$ };
//# sourceMappingURL=com.brewery.web.frontend.conversation.-Conversation-Page$.js.map
