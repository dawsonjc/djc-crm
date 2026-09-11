'use strict';
import * as $i_jquery from "jquery";
import * as $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024 from "./com.brewery.web.frontend.-Help-Functions$.js";
import * as $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c from "./internal-07235935c771c9429268cb101d7f2e2e7f60988c.js";
/** @constructor */
function $c_Lcom_brewery_web_frontend_admin_Admin$() {
}
export { $c_Lcom_brewery_web_frontend_admin_Admin$ as $c_Lcom_brewery_web_frontend_admin_Admin$ };
$c_Lcom_brewery_web_frontend_admin_Admin$.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_Lcom_brewery_web_frontend_admin_Admin$.prototype.constructor = $c_Lcom_brewery_web_frontend_admin_Admin$;
/** @constructor */
function $h_Lcom_brewery_web_frontend_admin_Admin$() {
}
export { $h_Lcom_brewery_web_frontend_admin_Admin$ as $h_Lcom_brewery_web_frontend_admin_Admin$ };
$h_Lcom_brewery_web_frontend_admin_Admin$.prototype = $c_Lcom_brewery_web_frontend_admin_Admin$.prototype;
$c_Lcom_brewery_web_frontend_admin_Admin$.prototype.init__V = (function() {
  $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_Lio_udash_wrappers_jquery_JQuery$();
  var jquery = (0, $i_jquery.default)(".verify-user");
  new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(jquery).on__T__F2__Lio_udash_wrappers_jquery_JQuery("click", new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element, jQueryEvent) => {
    var this$ = (0, $i_jquery.default)(element);
    var formData = $j_com$002ebrewery$002eweb$002efrontend$002e$002dHelp$002dFunctions$0024.$m_Lcom_brewery_web_frontend_HelpFunctions$().getFormData__Lio_udash_wrappers_jquery_JQuery__sci_Map(this$.parent().find("form"));
    var $x_6 = $i_jquery.default;
    var $x_5 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sr_ScalaRunTime$();
    var $x_4 = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("url", "/admin/user/verify");
    var $x_3 = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("method", "POST");
    var $x_1 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sr_ScalaRunTime$();
    var s = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_toString__T(formData.apply__O__O("userId"));
    var fields = $x_1.wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$d_T2.getArrayOf().constr)([new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("userId", s)]));
    var _2 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sjs_js_special_package$().objectLiteral__sci_Seq__sjs_js_Object(fields);
    var $x_2 = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("data", _2);
    var _2$1 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sjs_js_Any$().fromFunction3__F3__sjs_js_Function3(new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sr_AbstractFunction3_$$Lambda$26e0a25d9b29f6b82ea50ab7badf4fb70c5c74e1(((data, textStatus, jqXHR) => {
      if ((!(!data.success))) {
        var \u03b41$ = this$.parent();
        var \u03b42$ = \u03b41$.parent();
        \u03b42$.remove();
      }
    })));
    var fields$1 = $x_5.wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$d_T2.getArrayOf().constr)([$x_4, $x_3, $x_2, new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("success", _2$1)]));
    return $x_6.ajax($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sjs_js_special_package$().objectLiteral__sci_Seq__sjs_js_Object(fields$1));
  })));
});
var $d_Lcom_brewery_web_frontend_admin_Admin$ = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_Lcom_brewery_web_frontend_admin_Admin$, "com.brewery.web.frontend.admin.Admin$", ({
  Lcom_brewery_web_frontend_admin_Admin$: 1
}));
export { $d_Lcom_brewery_web_frontend_admin_Admin$ as $d_Lcom_brewery_web_frontend_admin_Admin$ };
var $n_Lcom_brewery_web_frontend_admin_Admin$;
function $m_Lcom_brewery_web_frontend_admin_Admin$() {
  if ((!$n_Lcom_brewery_web_frontend_admin_Admin$)) {
    $n_Lcom_brewery_web_frontend_admin_Admin$ = new $c_Lcom_brewery_web_frontend_admin_Admin$();
  }
  return $n_Lcom_brewery_web_frontend_admin_Admin$;
}
export { $m_Lcom_brewery_web_frontend_admin_Admin$ as $m_Lcom_brewery_web_frontend_admin_Admin$ };
//# sourceMappingURL=com.brewery.web.frontend.admin.-Admin$.js.map
