'use strict';
import * as $i_jquery from "jquery";
import * as $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024 from "./com.brewery.web.frontend.-Help-Functions$.js";
import * as $j_com$002ebrewery$002eweb$002efrontend$002emessage$002e$002dMessage from "./com.brewery.web.frontend.message.-Message.js";
import * as $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6 from "./internal-3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.js";
function $p_Lcom_brewery_web_frontend_conversation_ConversationBox$__initToolTip__V($thiz) {
  var \u03b4messages = (0, $i_jquery.default)("#chat-messages");
  var this$15 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sci_Map$();
  var elems = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$().wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$d_T2.getArrayOf().constr)([new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("position", "absolute"), new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("z-index", 1000), new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("background", "#fff"), new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("border", "1px solid #ccc"), new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("box-shadow", "0 2px 5px rgba(0,0,0,0.2)"), new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("padding", "10px"), new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("border-radius", "4px")]));
  var customTooltipCss = this$15.from__sc_IterableOnce__sci_Map(elems);
  var this$22 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sci_Map$();
  var elems$1 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$().wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$d_T2.getArrayOf().constr)([new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("list-style", "none"), new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("padding", 0), new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("margin", 0)]));
  var ulcss = this$22.from__sc_IterableOnce__sci_Map(elems$1);
  var this$27 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sci_Map$();
  var elems$2 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$().wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$d_T2.getArrayOf().constr)([new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("padding", "5px 10px"), new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("cursor", "pointer")]));
  var liCss = this$27.from__sc_IterableOnce__sci_Map(elems$2);
  var this$30 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sci_Map$();
  var elems$3 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$().wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$d_T2.getArrayOf().constr)([new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("background-color", "#f8f9fa")]));
  var liHover = this$30.from__sc_IterableOnce__sci_Map(elems$3);
  ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$(), new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(\u03b4messages)).on__T__T__F2__Lio_udash_wrappers_jquery_JQuery("contextmenu", ".user-message-box", new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element, event) => {
    event.preventDefault();
    var this$ = (0, $i_jquery.default)(element);
    var \u03b41$ = (0, $i_jquery.default)("#tool-tip");
    \u03b41$.remove();
    var \u03b4toolTip = (0, $i_jquery.default)($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sc_StringOps$().stripMargin$extension__T__C__T("<div id=\"tool-tip\">\r\n                  | <ul>\r\n                  |     <li data-action=\"reply\">Reply</li>\r\n                  |     <li data-action=\"add-friend\">Add Friend</li>\r\n                  | </ul>\r\n                  |</div>", 124));
    var this$38 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sci_Map$();
    var $x_2 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$();
    var y = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sc_StringOps$().format$extension__T__sci_Seq__T("%spx", $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$().genericWrapArray__O__sci_ArraySeq(new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$ac_O([$j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$uD(event.pageY)])));
    var $x_1 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("top", y);
    var y$1 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sc_StringOps$().format$extension__T__sci_Seq__T("%spx", $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$().genericWrapArray__O__sci_ArraySeq(new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$ac_O([$j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$uD(event.pageX)])));
    var elems$4 = $x_2.wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$d_T2.getArrayOf().constr)([$x_1, new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("left", y$1)]));
    var positionCss = this$38.from__sc_IterableOnce__sci_Map(elems$4);
    var this$39 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(customTooltipCss);
    var toolTipCss = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_sci_Map(this$39.concat__sc_IterableOnce__sc_IterableOps(positionCss));
    ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$(), new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(\u03b4toolTip)).css__sci_Map__Lio_udash_wrappers_jquery_JQuery(toolTipCss);
    $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$();
    var jquery = \u03b4toolTip.find("ul");
    new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(jquery).css__sci_Map__Lio_udash_wrappers_jquery_JQuery(ulcss);
    $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$();
    var jquery$1 = \u03b4toolTip.find("li");
    new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(jquery$1).css__sci_Map__Lio_udash_wrappers_jquery_JQuery(liCss);
    $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$();
    $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$();
    var jquery$2 = \u03b4toolTip.find("li");
    var jquery$3 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(jquery$2).on__T__F2__Lio_udash_wrappers_jquery_JQuery("mouseenter", new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element$2, event$2) => {
      var this$$1 = (0, $i_jquery.default)(element$2);
      var this$44 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(liCss);
      var newCss = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_sci_Map(this$44.concat__sc_IterableOnce__sc_IterableOps(liHover));
      return ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$(), new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(this$$1)).css__sci_Map__Lio_udash_wrappers_jquery_JQuery(newCss);
    })));
    new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(jquery$3).on__T__F2__Lio_udash_wrappers_jquery_JQuery("mouseleave", new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element$3, event$3) => {
      var this$$2 = (0, $i_jquery.default)(element$3);
      this$$2.removeAttr("style");
      return ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$(), new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(this$$2)).css__sci_Map__Lio_udash_wrappers_jquery_JQuery(liCss);
    })));
    $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$();
    var jquery$4 = \u03b4toolTip.find("li[data-action='add-friend']");
    new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(jquery$4).on__T__F2__Lio_udash_wrappers_jquery_JQuery("click", new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element$4, event$4) => {
      $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_s_concurrent_ExecutionContext$().global__s_concurrent_ExecutionContextExecutor();
      var userId = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(this$.find("input[name='user-id']").val());
      var $x_8 = $i_jquery.default;
      var $x_7 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$();
      var $x_6 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("url", "/add-friend");
      var $x_5 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("method", "POST");
      var fields = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$().wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$d_T2.getArrayOf().constr)([new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("userId", userId)]));
      var _2 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_special_package$().objectLiteral__sci_Seq__sjs_js_Object(fields);
      var $x_4 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("data", _2);
      var _2$1 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_Any$().fromFunction3__F3__sjs_js_Function3(new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction3_$$Lambda$26e0a25d9b29f6b82ea50ab7badf4fb70c5c74e1(((data, textStatus, jqXHR) => {
        $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(textStatus);
      })));
      var $x_3 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("success", _2$1);
      var _2$2 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_Any$().fromFunction3__F3__sjs_js_Function3(new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction3_$$Lambda$26e0a25d9b29f6b82ea50ab7badf4fb70c5c74e1(((jqXHR$2, textStatus$2, errorThrown) => {
        $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(textStatus$2);
        $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(errorThrown);
      })));
      var fields$1 = $x_7.wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$d_T2.getArrayOf().constr)([$x_6, $x_5, $x_4, $x_3, new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("failure", _2$2)]));
      $x_8.ajax($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_special_package$().objectLiteral__sci_Seq__sjs_js_Object(fields$1));
    })));
    (0, $i_jquery.default)("body").append(\u03b4toolTip);
    $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$();
    var jquery$5 = (0, $i_jquery.default)(window.document);
    return new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(jquery$5).one__T__F2__Lio_udash_wrappers_jquery_JQuery("click", new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element$5, event$5) => \u03b4toolTip.remove())));
  })));
}
export { $p_Lcom_brewery_web_frontend_conversation_ConversationBox$__initToolTip__V as $p_Lcom_brewery_web_frontend_conversation_ConversationBox$__initToolTip__V };
function $p_Lcom_brewery_web_frontend_conversation_ConversationBox$__prependMessages__sjs_js_Array__Lio_udash_wrappers_jquery_JQuery__V($thiz, messages, messageBox) {
  var len = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$uI(messages.length);
  var i = 0;
  while ((i < len)) {
    var x0 = messages[i];
    var m = new ($j_com$002ebrewery$002eweb$002efrontend$002emessage$002e$002dMessage.$a_Lcom_brewery_web_frontend_message_Message())(x0);
    var \u03b4html = m.toJQueryElement();
    $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$();
    var jquery = \u03b4html.find(".delete-message");
    new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(jquery).on__T__F2__Lio_udash_wrappers_jquery_JQuery("click", new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element, jQueryEvent) => {
      $p_Lcom_brewery_web_frontend_conversation_ConversationBox$__deleteMessage__Lorg_scalajs_dom_Element__Lio_udash_wrappers_jquery_JQueryEvent__V($m_Lcom_brewery_web_frontend_conversation_ConversationBox$(), element, jQueryEvent);
    })));
    messageBox.prepend(\u03b4html);
    i = ((1 + i) | 0);
  }
}
export { $p_Lcom_brewery_web_frontend_conversation_ConversationBox$__prependMessages__sjs_js_Array__Lio_udash_wrappers_jquery_JQuery__V as $p_Lcom_brewery_web_frontend_conversation_ConversationBox$__prependMessages__sjs_js_Array__Lio_udash_wrappers_jquery_JQuery__V };
function $p_Lcom_brewery_web_frontend_conversation_ConversationBox$__deleteMessage__Lorg_scalajs_dom_Element__Lio_udash_wrappers_jquery_JQueryEvent__V($thiz, element, jQueryEvent) {
  var this$ = (0, $i_jquery.default)(element);
  var $x_1 = $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024.$m_Lcom_brewery_web_frontend_HelpFunctions$();
  var \u03b42$ = this$.parent();
  var formData = $x_1.getFormData__Lio_udash_wrappers_jquery_JQuery__sci_Map(\u03b42$.parent().find("form"));
  var $x_8 = $i_jquery.default;
  var $x_7 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$();
  var $x_6 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("url", "/message/delete");
  var $x_5 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("method", "DELETE");
  var $x_3 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$();
  var s = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(formData).apply__O__O("message-id")));
  var $x_2 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("messageId", s);
  var s$1 = $thiz.Lcom_brewery_web_frontend_conversation_ConversationBox$__f_conversationId;
  var fields = $x_3.wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$d_T2.getArrayOf().constr)([$x_2, new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("conversationId", s$1)]));
  var _2 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_special_package$().objectLiteral__sci_Seq__sjs_js_Object(fields);
  var $x_4 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("data", _2);
  var _2$1 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_Any$().fromFunction3__F3__sjs_js_Function3(new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction3_$$Lambda$26e0a25d9b29f6b82ea50ab7badf4fb70c5c74e1(((data, textStatus, jqXHR) => {
    $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(textStatus);
    var \u03b43$ = this$.parent();
    var \u03b44$ = \u03b43$.parent();
    return \u03b44$.remove();
  })));
  var fields$1 = $x_7.wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$d_T2.getArrayOf().constr)([$x_6, $x_5, $x_4, new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("success", _2$1)]));
  $x_8.ajax($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_special_package$().objectLiteral__sci_Seq__sjs_js_Object(fields$1));
}
export { $p_Lcom_brewery_web_frontend_conversation_ConversationBox$__deleteMessage__Lorg_scalajs_dom_Element__Lio_udash_wrappers_jquery_JQueryEvent__V as $p_Lcom_brewery_web_frontend_conversation_ConversationBox$__deleteMessage__Lorg_scalajs_dom_Element__Lio_udash_wrappers_jquery_JQueryEvent__V };
/** @constructor */
function $c_Lcom_brewery_web_frontend_conversation_ConversationBox$() {
  this.Lcom_brewery_web_frontend_conversation_ConversationBox$__f_conversationId = null;
  $n_Lcom_brewery_web_frontend_conversation_ConversationBox$ = this;
  this.Lcom_brewery_web_frontend_conversation_ConversationBox$__f_conversationId = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(new URLSearchParams($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(window.location.search)).get("conversationId"));
}
export { $c_Lcom_brewery_web_frontend_conversation_ConversationBox$ as $c_Lcom_brewery_web_frontend_conversation_ConversationBox$ };
$c_Lcom_brewery_web_frontend_conversation_ConversationBox$.prototype = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$h_O();
$c_Lcom_brewery_web_frontend_conversation_ConversationBox$.prototype.constructor = $c_Lcom_brewery_web_frontend_conversation_ConversationBox$;
/** @constructor */
function $h_Lcom_brewery_web_frontend_conversation_ConversationBox$() {
}
export { $h_Lcom_brewery_web_frontend_conversation_ConversationBox$ as $h_Lcom_brewery_web_frontend_conversation_ConversationBox$ };
$h_Lcom_brewery_web_frontend_conversation_ConversationBox$.prototype = $c_Lcom_brewery_web_frontend_conversation_ConversationBox$.prototype;
$c_Lcom_brewery_web_frontend_conversation_ConversationBox$.prototype.initialize__V = (function() {
  var \u03b4messages = (0, $i_jquery.default)("#chat-messages");
  ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$(), new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(\u03b4messages)).on__T__F2__Lio_udash_wrappers_jquery_JQuery("scroll", new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element, jQueryEvent) => {
    if (($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$uD(\u03b4messages.scrollTop()) === 0.0)) {
      var $x_7 = $i_jquery.default;
      var $x_6 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$();
      var $x_5 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("url", "/message");
      var $x_4 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("method", "GET");
      var _2 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_Any$().fromFunction3__F3__sjs_js_Function3(new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction3_$$Lambda$26e0a25d9b29f6b82ea50ab7badf4fb70c5c74e1(((data, textStatus, jqXHR) => {
        $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(textStatus);
        $p_Lcom_brewery_web_frontend_conversation_ConversationBox$__prependMessages__sjs_js_Array__Lio_udash_wrappers_jquery_JQuery__V($m_Lcom_brewery_web_frontend_conversation_ConversationBox$(), data.data, \u03b4messages);
      })));
      var $x_3 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("success", _2);
      var $x_2 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$();
      var s = $m_Lcom_brewery_web_frontend_conversation_ConversationBox$().Lcom_brewery_web_frontend_conversation_ConversationBox$__f_conversationId;
      var $x_1 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("conversationId", s);
      var s$1 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(\u03b4messages.first().find("input[name='message-date']").val()));
      var fields = $x_2.wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$d_T2.getArrayOf().constr)([$x_1, new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("beforeDate", s$1)]));
      var _2$1 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_special_package$().objectLiteral__sci_Seq__sjs_js_Object(fields);
      var fields$1 = $x_6.wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$d_T2.getArrayOf().constr)([$x_5, $x_4, $x_3, new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("data", _2$1)]));
      $x_7.ajax($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_special_package$().objectLiteral__sci_Seq__sjs_js_Object(fields$1));
      return (void 0);
    } else {
      return (void 0);
    }
  })));
  var \u03b4deleteMessage = (0, $i_jquery.default)(".delete-message");
  ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$(), new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(\u03b4deleteMessage)).on__T__F2__Lio_udash_wrappers_jquery_JQuery("click", new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element$2, jQueryEvent$2) => {
    $p_Lcom_brewery_web_frontend_conversation_ConversationBox$__deleteMessage__Lorg_scalajs_dom_Element__Lio_udash_wrappers_jquery_JQueryEvent__V($m_Lcom_brewery_web_frontend_conversation_ConversationBox$(), element$2, jQueryEvent$2);
  })));
  $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_Lio_udash_wrappers_jquery_JQuery$();
  var jquery = \u03b4messages.find(".message-date");
  new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(jquery).each__F2__Lio_udash_wrappers_jquery_JQuery(new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element$3, index) => {
    $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$uI(index);
    var this$ = (0, $i_jquery.default)(element$3);
    var date = ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(this$.html()) + " UTC");
    return this$.html($j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024.$m_Lcom_brewery_web_frontend_HelpFunctions$().formatDate__sjs_js_Date__T(new Date(date)));
  })));
  $p_Lcom_brewery_web_frontend_conversation_ConversationBox$__initToolTip__V(this);
});
var $d_Lcom_brewery_web_frontend_conversation_ConversationBox$ = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$TypeData().initClass($c_Lcom_brewery_web_frontend_conversation_ConversationBox$, "com.brewery.web.frontend.conversation.ConversationBox$", ({
  Lcom_brewery_web_frontend_conversation_ConversationBox$: 1
}));
export { $d_Lcom_brewery_web_frontend_conversation_ConversationBox$ as $d_Lcom_brewery_web_frontend_conversation_ConversationBox$ };
var $n_Lcom_brewery_web_frontend_conversation_ConversationBox$;
function $m_Lcom_brewery_web_frontend_conversation_ConversationBox$() {
  if ((!$n_Lcom_brewery_web_frontend_conversation_ConversationBox$)) {
    $n_Lcom_brewery_web_frontend_conversation_ConversationBox$ = new $c_Lcom_brewery_web_frontend_conversation_ConversationBox$();
  }
  return $n_Lcom_brewery_web_frontend_conversation_ConversationBox$;
}
export { $m_Lcom_brewery_web_frontend_conversation_ConversationBox$ as $m_Lcom_brewery_web_frontend_conversation_ConversationBox$ };
//# sourceMappingURL=com.brewery.web.frontend.conversation.-Conversation-Box$.js.map
