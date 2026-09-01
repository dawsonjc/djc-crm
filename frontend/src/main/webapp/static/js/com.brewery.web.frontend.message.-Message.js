'use strict';
import * as $i_jquery from "jquery";
import * as $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024 from "./com.brewery.web.frontend.-Help-Functions$.js";
import * as $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6 from "./internal-3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.js";
function $s_Lcom_brewery_web_frontend_message_Message__toJQueryElement__Lcom_brewery_web_frontend_message_Message__Lio_udash_wrappers_jquery_JQuery(this$1) {
  var $x_1 = $i_jquery.default;
  var x = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sc_StringOps$().format$extension__T__sci_Seq__T("\r\n      |            <div class=\"my-4 border border-transparent rounded-lg hover:border hover:border-black hover:rounded-lg hover:bg-darker-jakarta hover:transition hover:duration-75 relative\">\r\n      |                <form style=\"display: none\">\r\n      |                    <input type=\"hidden\" name=\"user-id\" value=\"%s\">\r\n      |                    <input type=\"hidden\" name=\"message-id\" value=\"%s\">\r\n      |                    <input type=\"hidden\" name=\"message-date\" value=\"%s\">\r\n      |                </form>\r\n      |                <div class=\"p-2 text-desert-storm information\">\r\n      |                    %s: %s\r\n      |                    <!-- Trash icon in top right -->\r\n      |\r\n      |                </div>\r\n      |                <div class=\"p-2 rounded-lg text-desert-storm break-all\">%s</div>\r\n      |             </div>\r\n           ", $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$().genericWrapArray__O__sci_ArraySeq(new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$ac_O([$j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(this$1.fromUserId), $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(this$1.messageId), $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024.$m_Lcom_brewery_web_frontend_HelpFunctions$().parseToUTCDateTime__sjs_js_Date__T(this$1.createDate), $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(this$1.fromUsername), $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024.$m_Lcom_brewery_web_frontend_HelpFunctions$().formatDate__sjs_js_Date__T(this$1.createDate), $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(this$1.content)])));
  var block = $x_1($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sc_StringOps$().stripMargin$extension__T__C__T(x, 124));
  var userInformation = $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024.$m_Lcom_brewery_web_frontend_HelpFunctions$().getCookie__T__sjs_js_Dynamic("User-Information");
  if (($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(userInformation.userId)) === $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(this$1.fromUserId))) {
    var $x_2 = true;
  } else {
    var array = userInformation.roles;
    var $x_2 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_ArrayOps$().contains$extension__sjs_js_Array__O__Z(array, "Admin");
  }
  if ($x_2) {
    var $x_3 = block.find(".information");
    var x$1 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sc_StringOps$().format$extension__T__sci_Seq__T("\r\n           |     <div class=\"absolute top-2 right-2 cursor-pointer delete-message\">\r\n           |         <i class=\"fas fa-trash-alt text-desert-storm hover:text-red-500\"></i>\r\n           |     </div>\r\n                ", $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$().genericWrapArray__O__sci_ArraySeq(new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$ac_O([])));
    $x_3.append($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sc_StringOps$().stripMargin$extension__T__C__T(x$1, 124));
  }
  return block;
}
export { $s_Lcom_brewery_web_frontend_message_Message__toJQueryElement__Lcom_brewery_web_frontend_message_Message__Lio_udash_wrappers_jquery_JQuery as $s_Lcom_brewery_web_frontend_message_Message__toJQueryElement__Lcom_brewery_web_frontend_message_Message__Lio_udash_wrappers_jquery_JQuery };
var $b_Lcom_brewery_web_frontend_message_Message;
function $a_Lcom_brewery_web_frontend_message_Message() {
  if ((!$b_Lcom_brewery_web_frontend_message_Message)) {
    $b_Lcom_brewery_web_frontend_message_Message = class $b_Lcom_brewery_web_frontend_message_Message extends Object {
    constructor(...rest) {
      var messageJson = null;
      messageJson = ((rest[0] === (void 0)) ? null : rest[0]);
      super();
      Object.defineProperty(this, "messageId", ({
        "configurable": true,
        "enumerable": true,
        "writable": true,
        "value": null
      }));
      Object.defineProperty(this, "updateDate", ({
        "configurable": true,
        "enumerable": true,
        "writable": true,
        "value": null
      }));
      Object.defineProperty(this, "createDate", ({
        "configurable": true,
        "enumerable": true,
        "writable": true,
        "value": null
      }));
      Object.defineProperty(this, "status", ({
        "configurable": true,
        "enumerable": true,
        "writable": true,
        "value": null
      }));
      Object.defineProperty(this, "conversationId", ({
        "configurable": true,
        "enumerable": true,
        "writable": true,
        "value": null
      }));
      Object.defineProperty(this, "fromUserId", ({
        "configurable": true,
        "enumerable": true,
        "writable": true,
        "value": null
      }));
      Object.defineProperty(this, "fromUsername", ({
        "configurable": true,
        "enumerable": true,
        "writable": true,
        "value": null
      }));
      Object.defineProperty(this, "toUserId", ({
        "configurable": true,
        "enumerable": true,
        "writable": true,
        "value": null
      }));
      Object.defineProperty(this, "toUsername", ({
        "configurable": true,
        "enumerable": true,
        "writable": true,
        "value": null
      }));
      Object.defineProperty(this, "content", ({
        "configurable": true,
        "enumerable": true,
        "writable": true,
        "value": null
      }));
      this.messageId = "";
      this.updateDate = null;
      this.createDate = null;
      this.status = "";
      this.conversationId = "";
      this.fromUserId = "";
      this.fromUsername = "";
      this.toUserId = "";
      this.toUsername = "";
      this.content = "";
      if ((messageJson !== null)) {
        if ((messageJson.messageId !== null)) {
          this.messageId = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(messageJson.messageId));
        }
        if ((messageJson.createDate !== null)) {
          var sCreateDate = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(messageJson.createDate));
          var this$1 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(sCreateDate);
          if (($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$uI(this$1.indexOf("Z")) !== (-1))) {
            this.createDate = new Date(sCreateDate);
          } else {
            this.createDate = new Date((sCreateDate + " UTC"));
          }
        }
        if ((messageJson.updateDate !== null)) {
          var sUpdateDate = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(messageJson.updateDate));
          var this$2 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(sUpdateDate);
          if (($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$uI(this$2.indexOf("Z")) !== (-1))) {
            this.updateDate = new Date(sUpdateDate);
          } else {
            this.updateDate = new Date((sUpdateDate + " UTC"));
          }
        }
        if ((messageJson.status !== null)) {
          this.status = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(messageJson.status));
        }
        if ((messageJson.conversationId !== null)) {
          this.conversationId = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(messageJson.conversationId));
        }
        if ((messageJson.fromUserId !== null)) {
          this.fromUserId = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(messageJson.fromUserId));
        }
        if ((messageJson.fromUsername !== null)) {
          this.fromUsername = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(messageJson.fromUsername));
        }
        if ((messageJson.toUserId !== null)) {
          this.toUserId = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(messageJson.toUserId));
        }
        if ((messageJson.toUsername !== null)) {
          this.toUsername = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(messageJson.toUsername));
        }
        if ((messageJson.content !== null)) {
          this.content = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(messageJson.content));
        }
      }
    }
    "toJQueryElement"() {
      return $s_Lcom_brewery_web_frontend_message_Message__toJQueryElement__Lcom_brewery_web_frontend_message_Message__Lio_udash_wrappers_jquery_JQuery(this);
    }
    };
  }
  return $b_Lcom_brewery_web_frontend_message_Message;
}
export { $a_Lcom_brewery_web_frontend_message_Message as $a_Lcom_brewery_web_frontend_message_Message };
//# sourceMappingURL=com.brewery.web.frontend.message.-Message.js.map
