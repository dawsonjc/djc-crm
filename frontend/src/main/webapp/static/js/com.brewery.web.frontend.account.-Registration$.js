'use strict';
import * as $i_jquery from "jquery";
import * as $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024 from "./com.brewery.web.frontend.-Help-Functions$.js";
import * as $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c from "./internal-07235935c771c9429268cb101d7f2e2e7f60988c.js";
import * as $j_internal$002d1daf9a19dee7083d9e201a92e94b1040bb961d99 from "./internal-1daf9a19dee7083d9e201a92e94b1040bb961d99.js";
function $p_Lcom_brewery_web_frontend_account_Registration$__registerSuccess__sjs_js_Any__T__Lio_udash_wrappers_jquery_JQueryXHR__V($thiz, data, status, jqXHR) {
  var url = new URL(window.location.href);
  window.location.href = (url.origin + "/account/login");
}
export { $p_Lcom_brewery_web_frontend_account_Registration$__registerSuccess__sjs_js_Any__T__Lio_udash_wrappers_jquery_JQueryXHR__V as $p_Lcom_brewery_web_frontend_account_Registration$__registerSuccess__sjs_js_Any__T__Lio_udash_wrappers_jquery_JQueryXHR__V };
/** @constructor */
function $c_Lcom_brewery_web_frontend_account_Registration$() {
}
export { $c_Lcom_brewery_web_frontend_account_Registration$ as $c_Lcom_brewery_web_frontend_account_Registration$ };
$c_Lcom_brewery_web_frontend_account_Registration$.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_Lcom_brewery_web_frontend_account_Registration$.prototype.constructor = $c_Lcom_brewery_web_frontend_account_Registration$;
/** @constructor */
function $h_Lcom_brewery_web_frontend_account_Registration$() {
}
export { $h_Lcom_brewery_web_frontend_account_Registration$ as $h_Lcom_brewery_web_frontend_account_Registration$ };
$h_Lcom_brewery_web_frontend_account_Registration$.prototype = $c_Lcom_brewery_web_frontend_account_Registration$.prototype;
$c_Lcom_brewery_web_frontend_account_Registration$.prototype.register__Lorg_scalajs_dom_HTMLFormElement__V = (function(form) {
  var \u03b41$ = (0, $i_jquery.default)("#email-error");
  \u03b41$.remove();
  var \u03b42$ = (0, $i_jquery.default)("#password-error");
  \u03b42$.remove();
  var registerForm = (0, $i_jquery.default)(form);
  var emailElement = registerForm.find("#email");
  var passwordElement = registerForm.find("#password");
  var formData = $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024.$m_Lcom_brewery_web_frontend_HelpFunctions$().getFormData__Lio_udash_wrappers_jquery_JQuery__sci_Map(registerForm);
  var email = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_toString__T(formData.apply__O__O("email"));
  var password = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_toString__T(formData.apply__O__O("password"));
  var EmailPattern = $j_internal$002d1daf9a19dee7083d9e201a92e94b1040bb961d99.$ct_s_util_matching_Regex__T__sci_Seq__(new $j_internal$002d1daf9a19dee7083d9e201a92e94b1040bb961d99.$c_s_util_matching_Regex(), "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sci_Nil$());
  if (EmailPattern.findFirstIn__jl_CharSequence__s_Option(email).isEmpty__Z()) {
    var element = (0, $i_jquery.default)("<div>");
    element.attr("id", "email-error");
    element.addClass("mb-2 rounded-md border border-red-300 bg-red-50 px-3 py-2 text-sm font-medium text-red-700 shadow-sm");
    element.text("Please enter a valid email address");
    emailElement.removeClass("border-gray-300 focus:ring-indigo-500 focus:border-indigo-500").addClass("border-red-500 text-red-900 placeholder-red-300 ring-1 ring-red-500 focus:ring-2 focus:ring-red-500 focus:border-red-500 shadow-[0_0_10px_rgba(239,68,68,0.45)]");
    emailElement.before(element);
    return (void 0);
  } else {
    emailElement.removeClass("border-red-500 text-red-900 placeholder-red-300 ring-1 ring-red-500 focus:ring-2 focus:ring-red-500 focus:border-red-500 shadow-[0_0_10px_rgba(239,68,68,0.45)]").addClass("border-gray-300 text-gray-900 placeholder-gray-500 focus:ring-indigo-500 focus:border-indigo-500");
  }
  var this$6 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sci_Map$();
  var $x_1 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sr_ScalaRunTime$();
  var y = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sr_AbstractFunction1_$$Lambda$70e1780b84463d18653aacefee3ab989ac625f28(((pass) => {
    var length = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$f_T__codePointCount__I__I__I(pass, 0, pass.length);
    var message = "";
    var check = ((length >= 8) && (length <= 128));
    if ((!check)) {
      message = "Password must be between 8 and 128 characters";
    }
    var _2 = message;
    return new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2(check, _2);
  }));
  var elems = $x_1.wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$d_T2.getArrayOf().constr)([new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("lengthCheck", y)]));
  var validatePassword = this$6.from__sc_IterableOnce__sci_Map(elems);
  var messages = validatePassword.map__F1__O(new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sr_AbstractFunction1_$$Lambda$70e1780b84463d18653aacefee3ab989ac625f28(((entry) => {
    var values = entry.T2__f__2.apply__O__O(password);
    var message$1 = "";
    if ((!(!(!values.T2__f__1)))) {
      message$1 = values.T2__f__2;
    }
    return message$1;
  }))).toList__sci_List();
  _return$1: {
    var these = messages;
    while ((!these.isEmpty__Z())) {
      var x0 = these.head__O();
      if ((x0 !== "")) {
        var $x_2 = true;
        break _return$1;
      }
      these = these.tail__O();
    }
    var $x_2 = false;
  }
  if ($x_2) {
    var element$2 = (0, $i_jquery.default)("<div>");
    element$2.attr("id", "password-error");
    element$2.addClass("mb-2 rounded-md border border-red-300 bg-red-50 px-3 py-2 text-sm font-medium text-red-700 shadow-sm");
    element$2.text($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$f_sc_IterableOnceOps__mkString__T__T__T__T(messages, "", ", ", ""));
    passwordElement.removeClass("border-gray-300 focus:ring-indigo-500 focus:border-indigo-500").addClass("border-red-500 text-red-900 placeholder-red-300 ring-1 ring-red-500 focus:ring-2 focus:ring-red-500 focus:border-red-500 shadow-[0_0_10px_rgba(239,68,68,0.45)]");
    passwordElement.before(element$2);
    return (void 0);
  } else {
    passwordElement.removeClass("border-red-500 text-red-900 placeholder-red-300 ring-1 ring-red-500 focus:ring-2 focus:ring-red-500 focus:border-red-500 shadow-[0_0_10px_rgba(239,68,68,0.45)]").addClass("border-gray-300 text-gray-900 placeholder-gray-500 focus:ring-indigo-500 focus:border-indigo-500");
  }
  var $x_8 = $i_jquery.default;
  var $x_7 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sr_ScalaRunTime$();
  var $x_6 = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("url", "/account/register");
  var $x_5 = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("method", "POST");
  var s = registerForm.serialize();
  var $x_4 = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("data", s);
  var _2$1 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sjs_js_Any$().fromFunction3__F3__sjs_js_Function3(new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sr_AbstractFunction3_$$Lambda$26e0a25d9b29f6b82ea50ab7badf4fb70c5c74e1(((data, status, jqXHR) => {
    $p_Lcom_brewery_web_frontend_account_Registration$__registerSuccess__sjs_js_Any__T__Lio_udash_wrappers_jquery_JQueryXHR__V($m_Lcom_brewery_web_frontend_account_Registration$(), data, status, jqXHR);
  })));
  var $x_3 = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("success", _2$1);
  var _2$2 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sjs_js_Any$().fromFunction3__F3__sjs_js_Function3(new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sr_AbstractFunction3_$$Lambda$26e0a25d9b29f6b82ea50ab7badf4fb70c5c74e1(((jqXHR$2, textStatus, errorThrown) => (void 0))));
  var fields = $x_7.wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$d_T2.getArrayOf().constr)([$x_6, $x_5, $x_4, $x_3, new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("error", _2$2)]));
  $x_8.ajax($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sjs_js_special_package$().objectLiteral__sci_Seq__sjs_js_Object(fields));
});
var $d_Lcom_brewery_web_frontend_account_Registration$ = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_Lcom_brewery_web_frontend_account_Registration$, "com.brewery.web.frontend.account.Registration$", ({
  Lcom_brewery_web_frontend_account_Registration$: 1
}));
export { $d_Lcom_brewery_web_frontend_account_Registration$ as $d_Lcom_brewery_web_frontend_account_Registration$ };
var $n_Lcom_brewery_web_frontend_account_Registration$;
function $m_Lcom_brewery_web_frontend_account_Registration$() {
  if ((!$n_Lcom_brewery_web_frontend_account_Registration$)) {
    $n_Lcom_brewery_web_frontend_account_Registration$ = new $c_Lcom_brewery_web_frontend_account_Registration$();
  }
  return $n_Lcom_brewery_web_frontend_account_Registration$;
}
export { $m_Lcom_brewery_web_frontend_account_Registration$ as $m_Lcom_brewery_web_frontend_account_Registration$ };
//# sourceMappingURL=com.brewery.web.frontend.account.-Registration$.js.map
