'use strict';
import * as $i_jquery from "jquery";
import * as $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024 from "./com.brewery.web.frontend.-Help-Functions$.js";
import * as $j_com$002ebrewery$002eweb$002efrontend$002euser$002e$002dForm$002dUser from "./com.brewery.web.frontend.user.-Form-User.js";
import * as $j_com$002ebrewery$002eweb$002efrontend$002euser$002e$002dUser from "./com.brewery.web.frontend.user.-User.js";
import * as $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c from "./internal-07235935c771c9429268cb101d7f2e2e7f60988c.js";
import * as $j_internal$002d112fb39c4df84161e6d2bdc5ed3fa2667610ef32 from "./internal-112fb39c4df84161e6d2bdc5ed3fa2667610ef32.js";
function $p_Lcom_brewery_web_frontend_account_Login$__loginSuccess__sjs_js_Any__T__Lio_udash_wrappers_jquery_JQueryXHR__V($thiz, data, status, jqXHR) {
  if ($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$uZ(data.success)) {
    var userData = data.data;
    var user = new $j_com$002ebrewery$002eweb$002efrontend$002euser$002e$002dUser.$c_Lcom_brewery_web_frontend_user_User();
    var name = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_toString__T($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(userData.userId));
    var value = $j_internal$002d112fb39c4df84161e6d2bdc5ed3fa2667610ef32.$m_ju_UUID$().fromString__T__ju_UUID(name);
    user.Lcom_brewery_web_frontend_user_User__f_userId = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_s_Some(value);
    user.Lcom_brewery_web_frontend_user_User__f_username = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_toString__T($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(userData.username));
    user.Lcom_brewery_web_frontend_user_User__f_roles = userData.roles;
    window.console.log(user);
    try {
      $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024.$m_Lcom_brewery_web_frontend_HelpFunctions$().setCookie__T__sjs_js_Any__s_Option__V("User-Information", user.toJsObject__sjs_js_Dynamic(), new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_s_Some(1));
    } catch (e) {
      var e$2 = ((e instanceof $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_jl_Throwable) ? e : new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sjs_js_JavaScriptException(e));
      if ((e$2 instanceof $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sjs_js_JavaScriptException)) {
        $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_sjs_js_JavaScriptException(e$2);
      } else {
        throw e;
      }
    }
    var url = new URL($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T(window.location.href));
    window.location.href = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T(url.origin);
  }
}
export { $p_Lcom_brewery_web_frontend_account_Login$__loginSuccess__sjs_js_Any__T__Lio_udash_wrappers_jquery_JQueryXHR__V as $p_Lcom_brewery_web_frontend_account_Login$__loginSuccess__sjs_js_Any__T__Lio_udash_wrappers_jquery_JQueryXHR__V };
function $p_Lcom_brewery_web_frontend_account_Login$__loginFailure__Lio_udash_wrappers_jquery_JQueryXHR__T__T__V($thiz, jqXHR, textStatus, errorThrown) {
  var jsonResponse = jqXHR.responseJson;
  jsonResponse.data.errors;
}
export { $p_Lcom_brewery_web_frontend_account_Login$__loginFailure__Lio_udash_wrappers_jquery_JQueryXHR__T__T__V as $p_Lcom_brewery_web_frontend_account_Login$__loginFailure__Lio_udash_wrappers_jquery_JQueryXHR__T__T__V };
/** @constructor */
function $c_Lcom_brewery_web_frontend_account_Login$() {
}
export { $c_Lcom_brewery_web_frontend_account_Login$ as $c_Lcom_brewery_web_frontend_account_Login$ };
$c_Lcom_brewery_web_frontend_account_Login$.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_Lcom_brewery_web_frontend_account_Login$.prototype.constructor = $c_Lcom_brewery_web_frontend_account_Login$;
/** @constructor */
function $h_Lcom_brewery_web_frontend_account_Login$() {
}
export { $h_Lcom_brewery_web_frontend_account_Login$ as $h_Lcom_brewery_web_frontend_account_Login$ };
$h_Lcom_brewery_web_frontend_account_Login$.prototype = $c_Lcom_brewery_web_frontend_account_Login$.prototype;
$c_Lcom_brewery_web_frontend_account_Login$.prototype.loginValidation__Lorg_scalajs_dom_HTMLFormElement__V = (function(form) {
  var loginForm = (0, $i_jquery.default)(form);
  var formData = $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024.$m_Lcom_brewery_web_frontend_HelpFunctions$().getFormData__Lio_udash_wrappers_jquery_JQuery__sci_Map(loginForm);
  $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_toString__T($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(formData).apply__O__O("email")));
  $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_toString__T($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(formData).apply__O__O("password")));
  var formUser = new ($j_com$002ebrewery$002eweb$002efrontend$002euser$002e$002dForm$002dUser.$a_Lcom_brewery_web_frontend_user_FormUser())();
  formUser.username = $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024.$m_Lcom_brewery_web_frontend_HelpFunctions$().cleanseString__T__T($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_toString__T($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(formData).apply__O__O("email"))));
  formUser.password = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_toString__T($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(formData).apply__O__O("password")));
  var $x_7 = $i_jquery.default;
  var $x_6 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sr_ScalaRunTime$();
  var $x_5 = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("url", "/account/login");
  var $x_4 = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("method", "POST");
  var $x_3 = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("contentType", "application/json");
  var s = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T(JSON.stringify(formUser));
  var $x_2 = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("data", s);
  var _2 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sjs_js_Any$().fromFunction3__F3__sjs_js_Function3(new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sr_AbstractFunction3_$$Lambda$26e0a25d9b29f6b82ea50ab7badf4fb70c5c74e1(((data, status, jqXHR) => {
    var status$1 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T(status);
    $p_Lcom_brewery_web_frontend_account_Login$__loginSuccess__sjs_js_Any__T__Lio_udash_wrappers_jquery_JQueryXHR__V($m_Lcom_brewery_web_frontend_account_Login$(), data, status$1, jqXHR);
  })));
  var $x_1 = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("success", _2);
  var _2$1 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sjs_js_Any$().fromFunction3__F3__sjs_js_Function3(new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sr_AbstractFunction3_$$Lambda$26e0a25d9b29f6b82ea50ab7badf4fb70c5c74e1(((jqXHR$2, textStatus, errorThrown) => {
    var textStatus$1 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T(textStatus);
    var errorThrown$1 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T(errorThrown);
    $p_Lcom_brewery_web_frontend_account_Login$__loginFailure__Lio_udash_wrappers_jquery_JQueryXHR__T__T__V($m_Lcom_brewery_web_frontend_account_Login$(), jqXHR$2, textStatus$1, errorThrown$1);
  })));
  var fields = $x_6.wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$d_T2.getArrayOf().constr)([$x_5, $x_4, $x_3, $x_2, $x_1, new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("error", _2$1)]));
  $x_7.ajax($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sjs_js_special_package$().objectLiteral__sci_Seq__sjs_js_Object(fields));
});
var $d_Lcom_brewery_web_frontend_account_Login$ = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_Lcom_brewery_web_frontend_account_Login$, "com.brewery.web.frontend.account.Login$", ({
  Lcom_brewery_web_frontend_account_Login$: 1
}));
export { $d_Lcom_brewery_web_frontend_account_Login$ as $d_Lcom_brewery_web_frontend_account_Login$ };
var $n_Lcom_brewery_web_frontend_account_Login$;
function $m_Lcom_brewery_web_frontend_account_Login$() {
  if ((!$n_Lcom_brewery_web_frontend_account_Login$)) {
    $n_Lcom_brewery_web_frontend_account_Login$ = new $c_Lcom_brewery_web_frontend_account_Login$();
  }
  return $n_Lcom_brewery_web_frontend_account_Login$;
}
export { $m_Lcom_brewery_web_frontend_account_Login$ as $m_Lcom_brewery_web_frontend_account_Login$ };
//# sourceMappingURL=com.brewery.web.frontend.account.-Login$.js.map
