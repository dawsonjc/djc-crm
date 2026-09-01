'use strict';
import * as $i_jquery from "jquery";
import * as $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c from "./internal-07235935c771c9429268cb101d7f2e2e7f60988c.js";
/** @constructor */
function $c_Lcom_brewery_web_frontend_dom_HeaderInitializer$() {
}
export { $c_Lcom_brewery_web_frontend_dom_HeaderInitializer$ as $c_Lcom_brewery_web_frontend_dom_HeaderInitializer$ };
$c_Lcom_brewery_web_frontend_dom_HeaderInitializer$.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_Lcom_brewery_web_frontend_dom_HeaderInitializer$.prototype.constructor = $c_Lcom_brewery_web_frontend_dom_HeaderInitializer$;
/** @constructor */
function $h_Lcom_brewery_web_frontend_dom_HeaderInitializer$() {
}
export { $h_Lcom_brewery_web_frontend_dom_HeaderInitializer$ as $h_Lcom_brewery_web_frontend_dom_HeaderInitializer$ };
$h_Lcom_brewery_web_frontend_dom_HeaderInitializer$.prototype = $c_Lcom_brewery_web_frontend_dom_HeaderInitializer$.prototype;
$c_Lcom_brewery_web_frontend_dom_HeaderInitializer$.prototype.initialize__V = (function() {
  $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_Lio_udash_wrappers_jquery_JQuery$();
  var jquery = (0, $i_jquery.default)("#logout");
  new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(jquery).on__T__F2__Lio_udash_wrappers_jquery_JQuery("click", new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element, jQueryEvent) => {
    jQueryEvent.preventDefault();
    var $x_4 = $i_jquery.default;
    var $x_3 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sr_ScalaRunTime$();
    var $x_2 = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("url", "/account/logout");
    var $x_1 = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("method", "POST");
    var _2 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sjs_js_Any$().fromFunction3__F3__sjs_js_Function3(new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sr_AbstractFunction3_$$Lambda$26e0a25d9b29f6b82ea50ab7badf4fb70c5c74e1(((data, textStatus, jqXHR) => {
      $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T(textStatus);
      window.location.href = "/";
    })));
    var fields = $x_3.wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$d_T2.getArrayOf().constr)([$x_2, $x_1, new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("success", _2)]));
    return $x_4.ajax($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sjs_js_special_package$().objectLiteral__sci_Seq__sjs_js_Object(fields));
  })));
});
var $d_Lcom_brewery_web_frontend_dom_HeaderInitializer$ = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_Lcom_brewery_web_frontend_dom_HeaderInitializer$, "com.brewery.web.frontend.dom.HeaderInitializer$", ({
  Lcom_brewery_web_frontend_dom_HeaderInitializer$: 1
}));
export { $d_Lcom_brewery_web_frontend_dom_HeaderInitializer$ as $d_Lcom_brewery_web_frontend_dom_HeaderInitializer$ };
var $n_Lcom_brewery_web_frontend_dom_HeaderInitializer$;
function $m_Lcom_brewery_web_frontend_dom_HeaderInitializer$() {
  if ((!$n_Lcom_brewery_web_frontend_dom_HeaderInitializer$)) {
    $n_Lcom_brewery_web_frontend_dom_HeaderInitializer$ = new $c_Lcom_brewery_web_frontend_dom_HeaderInitializer$();
  }
  return $n_Lcom_brewery_web_frontend_dom_HeaderInitializer$;
}
export { $m_Lcom_brewery_web_frontend_dom_HeaderInitializer$ as $m_Lcom_brewery_web_frontend_dom_HeaderInitializer$ };
//# sourceMappingURL=com.brewery.web.frontend.dom.-Header-Initializer$.js.map
